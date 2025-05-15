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
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import entidades.Funcion;
import enums.EstadoSala;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
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
    public Funcion registrarFuncion(Funcion funcion) throws FuncionSalaOcupadaException, FuncionSalaVaciaException {
        MongoClient clienteMongo = null;
        try {
            clienteMongo = conexion.crearConexion();
            MongoDatabase database = conexion.obtenerBaseDatos(clienteMongo);
            MongoCollection<Funcion> coleccionFunciones = database.getCollection(nombreColeccion, Funcion.class);

            // Verificar si la sala esta ocupada
            List<Bson> filtradores = Arrays.asList(
                    Filters.eq("sala.numSala", funcion.getSala().getNumSala()),
                    Filters.gte("fechaHora", LocalDateTime.now()),
                    Filters.gt("fechaHora", LocalDateTime.now().plusMinutes(funcion.getPelicula().getDuracion()))
            );
            Bson filtro = Filters.and(filtradores);

            Long contador = coleccionFunciones.countDocuments(filtro);
            if (contador > 0) {
                throw new FuncionSalaOcupadaException("La sala ya esta ocupada en la fecha.");
            }

            //Verificar si la sala no esta vacia
            if (funcion.getSala() == null) {
                throw new FuncionSalaVaciaException("La sala no puede estar vacia cuando creas una funcion");
            }

            if (funcion.getSala().getEstado() != EstadoSala.ACTIVA) {
                throw new FuncionSalaVaciaException("La sala no puede estar vacia, inactiva o mantenimiento para registrar una funcion");
            }

            coleccionFunciones.insertOne(funcion);
            return funcion;
        } catch (FuncionSalaOcupadaException e) {
            throw new FuncionSalaOcupadaException("Error al registrar la funcion: " + e.getMessage());
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

            // Cadena de agregación
            List<Bson> cadena = new ArrayList<>();

            // lookup para unir Funciones con Peliculas
            cadena.add(Aggregates.lookup(
                    "Peliculas",
                    "pelicula.idPelicula",
                    "_id",
                    "peliculaInfo"
            ));

            // unwind para descomponer el arreglo
            cadena.add(Aggregates.unwind("$peliculaInfo"));

            // filtrar por titulo
            Bson filtroTitulo = Aggregates.match(
                    Filters.regex("peliculaInfo.titulo", Pattern.compile(nombrePelicula, Pattern.CASE_INSENSITIVE))
            );
            cadena.add(filtroTitulo);

            // proyector para que se vea la funcion bien
            cadena.add(Aggregates.project(Projections.fields(
                    Projections.include("idFuncion", "sala", "fechaHora", "precio"),
                    Projections.computed("pelicula", "$peliculaInfo")
            )));

            return coleccionFunciones.aggregate(cadena).into(new ArrayList<>());

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
    public LocalDateTime calcularHoraTerminoFuncion(ObjectId idFuncion) throws FuncionNoEncontradaException, FuncionDuracionIncorrectaException {
        MongoClient clienteMongo = conexion.crearConexion();
        try {
            MongoDatabase database = conexion.obtenerBaseDatos(clienteMongo);
            MongoCollection<Funcion> coleccionFunciones = database.getCollection(nombreColeccion, Funcion.class);

            Bson filtro = Filters.eq("_id", idFuncion);
            Funcion funcion = coleccionFunciones.find(filtro).first();

            if (funcion == null) {
                throw new FuncionNoEncontradaException("Rrror: Funcion no encontrada");
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

}
