/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Conexion.MongoConexion;
import Excepciones.Funciones.FuncionDuracionIncorrectaException;
import Excepciones.Funciones.FuncionSalaVaciaException;
import Excepciones.Funciones.FuncionNoEncontradaException;
import Excepciones.Funciones.FuncionSalaOcupadaException;
import Interfaces.IFuncionDAO;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import entidades.Funcion;
import enums.EstadoSala;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

/**
 *
 * @author Abraham Coronel Bringas
 */
public class FuncionDAO implements IFuncionDAO {

    private static FuncionDAO instance;
    private final MongoConexion conexion = new MongoConexion();
    private final String nombreColeccion = "Funciones";

    public static FuncionDAO getInstanceDAO() {
        if (instance == null) {
            instance = new FuncionDAO();
        }

        return instance;
    }

    @Override
    public Funcion registrarFuncion(Funcion funcion) throws FuncionSalaOcupadaException, FuncionSalaVaciaException, FuncionDuracionIncorrectaException {
        MongoClient clienteMongo = null;
        try {
            clienteMongo = conexion.crearConexion();
            MongoDatabase database = conexion.obtenerBaseDatos(clienteMongo);
            MongoCollection<Funcion> coleccionFunciones = database.getCollection(nombreColeccion, Funcion.class);

            // Validar duracion de la película
            if (funcion.getPelicula().getDuracion() == null || funcion.getPelicula().getDuracion() <= 0) {
                throw new FuncionDuracionIncorrectaException("La duracion de la pelicula es invalida.");
            }

            LocalDateTime nuevaFechaInicio = funcion.getFechaHora();
            LocalDateTime nuevaFechaFin = nuevaFechaInicio.plusMinutes(funcion.getPelicula().getDuracion());

            Bson filtroSala = Filters.eq("sala.numSala", funcion.getSala().getNumSala());
            Bson filtroSolapamiento = Filters.or(
                    Filters.and(
                            Filters.lte("fechaHora", nuevaFechaInicio),
                            Filters.gt("fechaHora", nuevaFechaInicio.minusMinutes(funcion.getPelicula().getDuracion()))
                    ),
                    Filters.and(
                            Filters.gte("fechaHora", nuevaFechaInicio),
                            Filters.lt("fechaHora", nuevaFechaFin)
                    )
            );

            Bson filtroCompleto = Filters.and(filtroSala, filtroSolapamiento);

            // Verificar si hay funciones que solapen
            long conteo = coleccionFunciones.countDocuments(filtroCompleto);
            if (conteo > 0) {
                throw new FuncionSalaOcupadaException("La sala esta ocupada en el horario seleccionado.");
            }

            // Validar estado de la sala
            if (funcion.getSala() == null || funcion.getSala().getEstado() != EstadoSala.ACTIVA) {
                throw new FuncionSalaVaciaException("La sala no esta disponible para funciones.");
            }

            coleccionFunciones.insertOne(funcion);
            return funcion;
        } finally {
            conexion.cerrarConexion(clienteMongo);
        }
    }

    @Override
    public Funcion eliminarFuncion(Funcion funcion) throws FuncionNoEncontradaException {
        MongoClient clienteMongo = null;
        try {
            clienteMongo = conexion.crearConexion();
            MongoDatabase database = conexion.obtenerBaseDatos(clienteMongo);
            MongoCollection<Funcion> coleccionFunciones = database.getCollection(nombreColeccion, Funcion.class);

            if (buscarFuncionId(funcion.getIdFuncion()) == null) {
                throw new FuncionNoEncontradaException("No se encontro la funcion a eliminar");
            }

            // Eliminar la función
            Bson filtro = Filters.eq("_id", funcion.getIdFuncion());
            coleccionFunciones.deleteOne(filtro);
            return funcion;
        } catch (FuncionNoEncontradaException e) {
            throw new FuncionNoEncontradaException("Error al eliminar la funcion: " + e.getMessage());
        } finally {
            conexion.cerrarConexion(clienteMongo);
        }
    }

    private Funcion buscarFuncionId(ObjectId idFuncion) {
        MongoClient clienteMongo = conexion.crearConexion();
        try {
            MongoDatabase database = conexion.obtenerBaseDatos(clienteMongo);
            MongoCollection<Funcion> coleccionFunciones = database.getCollection(nombreColeccion, Funcion.class);
            Bson filtro = Filters.eq("_id", idFuncion);
            return coleccionFunciones.find(filtro).first();
        } finally {
            conexion.cerrarConexion(clienteMongo);
        }
    }

    @Override
    public List<Funcion> buscarFuncionesPelicula(String nombrePelicula) {
        MongoClient clienteMongo = conexion.crearConexion();
        try {
            MongoDatabase database = conexion.obtenerBaseDatos(clienteMongo);
            MongoCollection<Funcion> coleccionFunciones = database.getCollection(nombreColeccion, Funcion.class);

            Bson filtro = Filters.regex("pelicula.titulo", Pattern.compile(nombrePelicula, Pattern.CASE_INSENSITIVE));

            return coleccionFunciones.find(filtro).into(new ArrayList<>());
        } finally {
            conexion.cerrarConexion(clienteMongo);
        }
    }

    @Override
    public List<Funcion> buscarFuncionFechaInicio(LocalDateTime fechaHora) {
        MongoClient clienteMongo = conexion.crearConexion();
        try {
            MongoDatabase database = conexion.obtenerBaseDatos(clienteMongo);
            MongoCollection<Funcion> coleccionFunciones = database.getCollection(nombreColeccion, Funcion.class);
            Bson filtro = Filters.eq("fechaHora", fechaHora);
            return coleccionFunciones.find(filtro).into(new ArrayList<>());
        } finally {
            conexion.cerrarConexion(clienteMongo);
        }
    }

    @Override
    public LocalDateTime calcularHoraTerminoFuncion(String idString) throws FuncionNoEncontradaException, FuncionDuracionIncorrectaException {
        MongoClient clienteMongo = conexion.crearConexion();

        if (idString == null || idString.isEmpty()) {
            throw new FuncionNoEncontradaException("Error: el id es nulo o esta vacio");
        }
        try {
            MongoDatabase database = conexion.obtenerBaseDatos(clienteMongo);
            MongoCollection<Funcion> coleccionFunciones = database.getCollection(nombreColeccion, Funcion.class);

            Bson filtro = Filters.eq("_id", new ObjectId(idString));
            Funcion funcion = coleccionFunciones.find(filtro).first();

            if (funcion == null) {
                throw new FuncionNoEncontradaException("Error: Funcion no encontrada");
            }

            Integer duracion = funcion.getPelicula().getDuracion();
            if (duracion == null) {
                throw new FuncionDuracionIncorrectaException("La duracion de la pelicula no es correcta");
            }
            return funcion.getFechaHora().plusMinutes(duracion);
        } finally {
            conexion.cerrarConexion(clienteMongo);
        }
    }

    @Override
    public List<Funcion> buscarPeliculasFiltradas(String textoFiltro) throws FuncionNoEncontradaException {
        MongoClient clienteMongo = conexion.crearConexion();
        try {
            MongoDatabase database = conexion.obtenerBaseDatos(clienteMongo);
            MongoCollection<Funcion> coleccionFunciones = database.getCollection(nombreColeccion, Funcion.class);

            Bson filtro = crearFiltro(textoFiltro);

            List<Funcion> funcionesFiltradas = coleccionFunciones.find(filtro)
                    .sort(Sorts.ascending("sala.numSala"))
                    .into(new ArrayList<>());

            return funcionesFiltradas;

        } catch (MongoException e) {
            throw new FuncionNoEncontradaException("Hubo un problema al buscar las salas filtradas: " + e.getMessage());
        }
    }

    private Bson crearFiltro(String textoFiltro) {
        List<Bson> condiciones = new ArrayList<>();

        if (textoFiltro != null && !textoFiltro.isBlank()) {
            condiciones.add(crearFiltroNumeroSala(textoFiltro));
            condiciones.add(crearFiltroPelicula(textoFiltro));
        }

        return condiciones.isEmpty() ? new Document() : Filters.or(condiciones);

    }

    private Bson crearFiltroNumeroSala(String filtroNumSala) {
        Pattern patron = Pattern.compile(".*" + Pattern.quote(filtroNumSala) + ".*", Pattern.CASE_INSENSITIVE);
        return Filters.regex("sala.numSala", patron);
    }

    private Bson crearFiltroPelicula(String filtroPelicula) {
        Pattern patron = Pattern.compile(".*" + Pattern.quote(filtroPelicula) + ".*", Pattern.CASE_INSENSITIVE);
        return Filters.regex("pelicula.titulo", patron);
    }

}
