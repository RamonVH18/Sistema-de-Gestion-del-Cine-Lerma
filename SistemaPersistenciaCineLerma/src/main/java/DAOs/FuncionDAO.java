/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Conexion.MongoConexion;
import Excepciones.Funciones.FuncionSalaVaciaException;
import Excepciones.Funciones.FuncionNoEncontradaException;
import Excepciones.Funciones.FuncionSalaOcupadaException;
import Interfaces.IFuncionDAO;
import Interfaces.IPeliculaDAO;
import Interfaces.ObservadorFuncion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import entidades.Funcion;
import enums.EstadoSala;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

/**
 *
 * @author Abraham Coronel Bringas
 */
public class FuncionDAO implements IFuncionDAO {

    private static FuncionDAO instance;
    private final IPeliculaDAO peliculaDAO = PeliculaDAO.getInstanceDAO();
    private final MongoConexion conexion = new MongoConexion();
    private final String nombreColeccion = "Funciones";
    private Map<String, List<ObservadorFuncion>> observadores;

    private FuncionDAO() {
        this.observadores = new HashMap();
    }

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
            Bson filtro = Filters.and(
                    Filters.eq("sala.numSala", funcion.getSala().getNumSala()),
                    Filters.eq("fechaHora", funcion.getFechaHora())
            );
            Long contador = coleccionFunciones.countDocuments(filtro);
            if (contador > 0) {
                throw new FuncionSalaOcupadaException("La sala ya esta ocupada en la fecha.");
            }

            //Verificar si la sala no esta vacia
            if (funcion.getSala() == null) {
                throw new FuncionSalaVaciaException("La sala no puede estar vacia cuando creas una funcion");
            }

            if (funcion.getSala().getEstado() == EstadoSala.INACTIVA) {
                throw new FuncionSalaVaciaException("La sala no puede estar vacia o inactiva para registrar una funcion");
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

            if (buscarFuncionPorId(funcion.getIdFuncion()) == null) {
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

    @Override
    public Funcion buscarFuncionPorId(ObjectId idFuncion) {
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
            Bson filtro = Filters.eq("pelicula.titulo", nombrePelicula);
            return coleccionFunciones.find(filtro).into(new ArrayList<>());
        } finally {
            conexion.cerrarConexion(clienteMongo);

        }
    }

}
