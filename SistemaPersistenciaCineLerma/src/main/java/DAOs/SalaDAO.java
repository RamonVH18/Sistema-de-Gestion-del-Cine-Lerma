/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package DAOs;

import Conexion.MongoConexion;
import DTOs.GananciaSalaDTO;
import Excepciones.salas.BuscarSalaException;
import Excepciones.salas.CreacionSalaException;
import Excepciones.salas.ErrorCalculoEstadisticasSalaException;
import Excepciones.salas.ModificarSalaException;
import Interfaces.ISalaDAO;
import com.mongodb.client.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import entidades.Asiento;
import entidades.Sala;
import enums.EstadoSala;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 * Clase SalaDAO encargada de manipular las Salas persistidas en la base de datos
 * @author Ramon Valencia
 */
public class SalaDAO implements ISalaDAO {

    private static SalaDAO instance; // Se crea un objeto de SalaDAO para poder ejecutar el patron SingleTone
    private final MongoConexion conexion = new MongoConexion(); // Se inicia una conexionMongo
    private final String nombreColeccion = "Salas"; // Nombre de la coleccion salas
    private Bson filtro;

    private final Bson projectionNormal = Projections.fields(
            Projections.include("_id", "numSala", "numAsientos", "estado", "asientos")
    ); // Proyeccion normal de una sala
    
    /**
     * Constructor vacio y privado para la ejeccucion del Singleton
     */
    private SalaDAO() {
    }
    /**
     * Metodo para regresar la instancia de esta clase y que de esa manera nomas se manipule una instancia
     * @return 
     */
    public static SalaDAO getInstanceDAO() {
        if (instance == null) {
            instance = new SalaDAO();
        }
        return instance;
    }

    /**
     * Metodo para agregar una Sala a la base de datos
     * @param sala
     * @return
     * @throws CreacionSalaException 
     */
    @Override
    public Sala agregarSala(Sala sala) throws CreacionSalaException {
        MongoClient clienteMongo = null; // Se crea el clienteMongo como null para mas adelante poder usarlo

        try {
            List<Asiento> asientosSala = new ArrayList(); // Se crea una lista para guardar aqui adentro los asientos

            creacionAsientos(sala, asientosSala); // Se llama al metodo para crear los asientos y guardarlos en la anterior lista

            sala.setAsientos(asientosSala); // Se introduce la lista de asientos en la sala

            MongoCollection coleccionSalas = obtenerColeccionSalas(clienteMongo); // Se llama al metodo para obtener la coleccion de salas de la base de datos

            coleccionSalas.insertOne(sala); // Se persiste la sala en la base de datos

            return sala;
        } catch (MongoException | BuscarSalaException e) {
            throw new CreacionSalaException("Hubo un problema al generar la base de datos: " + e.getMessage());
        } finally {
            conexion.cerrarConexion(clienteMongo);
        }
    }
    /**
     * Metodo para buscar una Sala en la base de datos usando el numero de la sala como referencia
     * @param numSala
     * @return
     * @throws BuscarSalaException 
     */
    @Override
    public Sala buscarSala(String numSala) throws BuscarSalaException {
        MongoClient clienteMongo = null; // Se crea el clienteMongo como null para mas adelante poder usarlo
        try {
            MongoCollection<Sala> coleccionSalas = obtenerColeccionSalas(clienteMongo); // Se llama al metodo para obtener la coleccion de salas de la base de datos

            Sala sala = coleccionSalas.find(
                    eq("numSala", numSala))
                    .projection(projectionNormal)
                    .first(); // Se usa este metodo para buscar una sala que coincida con ese numero de sala en especifico

            if (sala == null) {
                throw new BuscarSalaException("No se encontro la sala con numero: " + numSala);
            } // Se lanza una excepcion en caso de que no se encuentre ninguna sala con ese numero

            return sala;
        } catch (MongoException e) {
            throw new BuscarSalaException("Error al buscar las salas en la base de datos: " + e.getMessage());
        } finally {
            conexion.cerrarConexion(clienteMongo);
        }
    }
    /**
     * Metodo para buscar todas las salas
     * El parametro de filtroNombre sirve para ingresar un filtro y ver que salas coinciden con ese nombre o tienen uno similar
     * @param filtroNombre
     * @return
     * @throws BuscarSalaException 
     */
    @Override
    public List<Sala> buscarSalas(String filtroNombre) throws BuscarSalaException {
        MongoClient clienteMongo = null; // Se crea el clienteMongo como null para mas adelante poder usarlo
        try {
            MongoCollection<Sala> coleccionSalas = obtenerColeccionSalas(clienteMongo); // Se llama al metodo para obtener la coleccion de salas de la base de datos
            
            filtro = crearFiltroNombre(filtroNombre); // se llama al metodo filtroNombre para ver si se aplica un filtor

            List<Sala> salas = coleccionSalas.find(filtro)
                    .sort(Sorts.ascending("numSala"))
                    .into(new ArrayList<>()); // Se obtiene la coleccion de salas en base al filtro si es que hay o no

            return salas;
        } catch (BuscarSalaException | MongoException e) {
            throw new BuscarSalaException("Hubo un error al buscar las salas: " + e.getMessage());
        } finally {
            conexion.cerrarConexion(clienteMongo);
        }
    }
    /**
     * Metodo para modificar el estado ya existente en la base de datos
     * @param numSala
     * @param estadoNuevo
     * @return
     * @throws ModificarSalaException 
     */
    @Override
    public Boolean modificarEstadoSala(String numSala, EstadoSala estadoNuevo) throws ModificarSalaException {
        MongoClient clienteMongo = null; // Se crea el  como null para mas adelante poder usarlo
        try {
            MongoCollection<Sala> coleccionSalas = obtenerColeccionSalas(clienteMongo); // Se llama al metodo para obtener la coleccion de salas de la base de datos

            Bson filtroNumSala = Filters.eq("numSala", numSala); // Filtro para obtener la sala que coincida con el numero de la sala
            Bson update = Updates.set("estado", estadoNuevo.name()); // Se define que es lo que se le va actualizar a la base de datos

            UpdateResult resultado = coleccionSalas.updateOne(filtroNumSala, update); // Se realiza la actualizacion

            return resultado.wasAcknowledged();
        } catch (BuscarSalaException e) {
            throw new ModificarSalaException("Hubo un error al modificar la sala " + numSala + ": " + e.getMessage());
        } finally {
            conexion.cerrarConexion(clienteMongo);
        }
    }
    
    @Override
     public List<GananciaSalaDTO> obtenerEstadisticasDeSalas() throws ErrorCalculoEstadisticasSalaException {
        MongoClient clienteMongo = null;
        try {
            MongoCollection<Sala> coleccionSalas = obtenerColeccionSalas(clienteMongo);
        } catch (BuscarSalaException ex) {
            
        }
        throw new UnsupportedOperationException("jns");
    }
    
    /**
     * Metodo para crear el filtro del nombre
     * @param filtroNombre
     * @return 
     */
    private Bson crearFiltroNombre(String filtroNombre) {
        // Primero se verifica que si se haya ingresaod algo, en caso de que no se regresara un documento vacio para que se realicen las busquedas sin filtros
        if (filtroNombre != null && !filtroNombre.isBlank()) {
            //Lista de objetos Bson donde se guardan los filtros que tengan que ver con el nombre de la sala
            List<Bson> filtradores = Arrays.asList(
                    Filters.regex("numSala", Pattern.compile("^" + filtroNombre, Pattern.CASE_INSENSITIVE)), // Filtro que se encarga de checar si el texto del buscador se encuentra en el num de la Sala al inicio
                    Filters.regex("numSala", Pattern.compile(".*" + filtroNombre + "$", Pattern.CASE_INSENSITIVE)), // Filtro que se encarga de checar si el texto del buscador se encuentra en el num de la Sala al final
                    Filters.regex("numSala", Pattern.compile("(?<!^)" + filtroNombre + "(?!$)", Pattern.CASE_INSENSITIVE)) // Filtro que se encarga de checar si el texto del buscador se encuentra en el num de la Sala enmedio
            );
            // Se regresa el filtro que hace que solo necesite cumplirse una de las dos condiciones
            return Filters.or(filtradores);
        }
        return new Document(); // Si filtroNombre está vacío, no aplica filtros
    }
    /**
     * Metodo para crear una lista con todos los asientos de una sala
     * @param sala
     * @param asientosSala 
     */
    private void creacionAsientos(Sala sala, List<Asiento> asientosSala) {
        // For que dura dependiendo del numero de asiento de una sala
        for (int i = 1; i <= sala.getNumAsientos(); i++) {
            String numero = String.valueOf(i);
            Asiento asiento = new Asiento(numero); // Se crea un nuevo asiento en cada vuelta, el numero aumenta 1
            asientosSala.add(asiento);
        }
    }
    /** 
     * Metodo para obteneer la coleccion de salas de la base de datos
     * @param clienteMongo
     * @return
     * @throws BuscarSalaException 
     */
    private MongoCollection<Sala> obtenerColeccionSalas(MongoClient clienteMongo) throws BuscarSalaException {
        try {
            clienteMongo = conexion.crearConexion(); // Se llama a un metodo de la clase conexion para que se cree la conexion

            MongoDatabase baseDatos = conexion.obtenerBaseDatos(clienteMongo); // Se obtiene la base de datos a partir de la conexion

            MongoCollection<Sala> coleccionSalas = baseDatos.getCollection(nombreColeccion, Sala.class); // En base al nombre de la coleccion se obtiene la coleccion

            return coleccionSalas;
        } catch (MongoException e) {
            throw new BuscarSalaException("Error al realizar la conexion: " + e.getMessage());
        }
    }

}
