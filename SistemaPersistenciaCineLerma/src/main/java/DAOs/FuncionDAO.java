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
import java.util.List;
import java.util.regex.Pattern;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

/**
 * Implementación de la interfaz IFuncionDAO para gestionar las operaciones CRUD de Funciones en MongoDB.
 * Incluye validaciones de negocio como duracion de películas, disponibilidad de salas y manejo de excepciones.
 * 
 * @author Abraham Coronel Bringas
 */
public class FuncionDAO implements IFuncionDAO {

    private static FuncionDAO instance;
    private final MongoConexion conexion = new MongoConexion();
    private final String nombreColeccion = "Funciones";

    /**
     * Obtiene la instancia única del DAO (Singleton).
     * 
     * @return Instancia única de FuncionDAO.
     */
    public static FuncionDAO getInstanceDAO() {
        if (instance == null) {
            instance = new FuncionDAO();
        }

        return instance;
    }

    /**
     * Registra una funcion validando duracion de película, disponibilidad de sala y solapamiento de horarios.
     * 
     * @param funcion Funcion a registrar.
     * @return Función registrada.
     * @throws FuncionSalaOcupadaException Si la sala está ocupada en el horario seleccionado.
     * @throws FuncionSalaVaciaException Si la sala no está activa o es nula.
     * @throws FuncionDuracionIncorrectaException Si la duración de la película es inválida (≤0 o nula).
     */
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

    /**
     * Elimina una función de la base de datos.
     * 
     * @param funcion Función a eliminar.
     * @return Función eliminada.
     * @throws FuncionNoEncontradaException Si la función no existe en la base de datos.
     */
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

    /**
     * Busca una función por su ID en la base de datos.
     * 
     * @param idFuncion ID de la funcion a buscar.
     * @return Funcion encontrada o null si no existe.
     */
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

    /**
     * Busca funciones que coincidan con el nombre de una película (busqueda insensible a mayusculas).
     * 
     * @param nombrePelicula Texto para filtrar por título de película.
     * @return Lista de funciones que coinciden con el filtro.
     */
    @Override
    public List<Funcion> buscarFuncionesPelicula(String nombrePelicula) {
        MongoClient clienteMongo = conexion.crearConexion();
        try {
            MongoDatabase database = conexion.obtenerBaseDatos(clienteMongo);
            MongoCollection<Funcion> coleccionFunciones = database.getCollection(nombreColeccion, Funcion.class);

            Bson filtro = Filters.eq("pelicula.titulo", Pattern.compile(nombrePelicula, Pattern.CASE_INSENSITIVE));

            return coleccionFunciones.find(filtro).into(new ArrayList<>());
        } finally {
            conexion.cerrarConexion(clienteMongo);
        }
    }

    /**
     * Busca funciones que inician en una fecha y hora específicas.
     * 
     * @param fechaHora Fecha y hora exacta de inicio.
     * @return Lista de funciones con la fecha de inicio especificada.
     */
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

    /**
     * Calcula la hora de término de una función sumando la duracion de la película a su hora de inicio.
     * 
     * @param idString ID de la funcion en formato String.
     * @return Fecha y hora de término calculada.
     * @throws FuncionNoEncontradaException Si el ID es inválido o la funcion no existe.
     * @throws FuncionDuracionIncorrectaException Si la duracion de la película es nula o invalida.
     */
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

    /**
     * Busca funciones filtradas por número de sala o título de película, ordenadas por numero de sala.
     * 
     * @param textoFiltro Texto para filtrar.
     * @return Lista de funciones que coinciden con el filtro.
     * @throws FuncionNoEncontradaException Si hay un error en la consulta a MongoDB.
     */
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

    /**
     * Crea un filtro combinado para búsqueda por numero de sala o título de película.
     * 
     * @param textoFiltro Texto de filtro proporcionado.
     * @return Filtro MongoDB para la consulta.
     */
    private Bson crearFiltro(String textoFiltro) {
        List<Bson> condiciones = new ArrayList<>();

        if (textoFiltro != null && !textoFiltro.isBlank()) {
            condiciones.add(crearFiltroNumeroSala(textoFiltro));
            condiciones.add(crearFiltroPelicula(textoFiltro));
        }

        return condiciones.isEmpty() ? new Document() : Filters.or(condiciones);

    }

    /**
     * Genera un filtro para buscar por número de sala usando una expresion regular.
     * 
     * @param filtroNumSala Texto para buscar en el numero de sala.
     * @return Filtro MongoDB para el numero de sala.
     */
    private Bson crearFiltroNumeroSala(String filtroNumSala) {
        Pattern patron = Pattern.compile(".*" + Pattern.quote(filtroNumSala) + ".*", Pattern.CASE_INSENSITIVE);
        return Filters.regex("sala.numSala", patron);
    }

    /**
     * Genera un filtro para buscar por título de película usando una expresión regular.
     * 
     * @param filtroPelicula Texto para buscar en el título.
     * @return Filtro MongoDB para el título de la película.
     */
    private Bson crearFiltroPelicula(String filtroPelicula) {
        Pattern patron = Pattern.compile(".*" + Pattern.quote(filtroPelicula) + ".*", Pattern.CASE_INSENSITIVE);
        return Filters.regex("pelicula.titulo", patron);
    }

}
