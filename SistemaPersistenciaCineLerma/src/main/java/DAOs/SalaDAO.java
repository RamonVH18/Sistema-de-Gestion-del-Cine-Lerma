/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package DAOs;

import Conexion.MongoConexion;
import Excepciones.salas.BuscarSalaException;
import Excepciones.salas.CreacionSalaException;
import Excepciones.salas.ModificarSalaException;
import Interfaces.ISalaDAO;
import com.mongodb.client.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import entidades.Asiento;
import entidades.Sala;
import enums.EstadoSalaFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

/**
 *
 * @author Ramon Valencia
 */
public class SalaDAO implements ISalaDAO {

    private static SalaDAO instance;
    private final MongoConexion conexion = new MongoConexion();
    private final String nombreColeccion = "Salas";
    private Bson filtro;

    private final Bson projectionNormal = Projections.fields(
            Projections.include("_id", "numSala", "numAsientos", "estado", "asientos")
    );

    private SalaDAO() {

    }

    public static SalaDAO getInstance() {
        if (instance == null) {
            instance = new SalaDAO();
        }
        return instance;
    }

    @Override
    public Sala agregarSala(Sala sala) throws CreacionSalaException {
        MongoClient clienteMongo = null;

        try {
            List<Asiento> asientosSala = new ArrayList();

            creacionAsientos(sala, asientosSala);

            sala.setAsientos(asientosSala);

            MongoCollection coleccionSalas = obtenerColeccionSalas(clienteMongo);

            coleccionSalas.insertOne(sala);

            return sala;
        } catch (MongoException | BuscarSalaException e) {
            throw new CreacionSalaException("Hubo un problema al generar la base de datos: " + e.getMessage());
        } finally {
            conexion.cerrarConexion(clienteMongo);
        }
    }

    @Override
    public Sala buscarSala(String numSala) throws BuscarSalaException {
        MongoClient clienteMongo = null;
        try {
            MongoCollection<Sala> coleccionSalas = obtenerColeccionSalas(clienteMongo);

            Sala sala = coleccionSalas.find(
                    eq("numSala", numSala))
                    .projection(projectionNormal)
                    .first();

            if (sala == null) {
                throw new BuscarSalaException("No se encontro la sala con numero: " + numSala);
            }

            return sala;
        } catch (MongoException e) {
            throw new BuscarSalaException("Error al buscar las salas en la base de datos: " + e.getMessage());
        } finally {
            conexion.cerrarConexion(clienteMongo);
        }
    }

    @Override
    public List<Sala> buscarSalas(String filtroNombre) throws BuscarSalaException {
        MongoClient clienteMongo = null;
        try {
            MongoCollection<Sala> coleccionSalas = obtenerColeccionSalas(clienteMongo);
            
            filtro = filtroNombre(filtroNombre);

            List<Sala> salas = coleccionSalas.find(filtro).into(new ArrayList<>());

            return salas;
        } catch (BuscarSalaException | MongoException e) {
            throw new BuscarSalaException("Hubo un error al buscar las salas: " + e.getMessage());
        } finally {
            conexion.cerrarConexion(clienteMongo);
        }
    }

    @Override
    public Boolean modificarEstadoSala(Sala sala) throws ModificarSalaException {
        MongoClient clienteMongo = null;
        try {
            MongoCollection<Sala> coleccionSalas = obtenerColeccionSalas(clienteMongo);

            Bson filtro = Filters.eq("numeroSala", sala.getNumSala());
            Bson update = Updates.set("estado", sala.getEstado().name());

            UpdateResult resultado = coleccionSalas.updateOne(filtro, update);

            return resultado.wasAcknowledged();
        } catch (BuscarSalaException e) {
            throw new ModificarSalaException("Hubo un error al modificar la sala " + sala.getNumSala() + ": " + e.getMessage());
        } finally {
            conexion.cerrarConexion(clienteMongo);
        }
    }

//    @Override
//    public List<Sala> buscarSalasFiltradas(String filtro) throws BuscarSalaException {
//        MongoClient clienteMongo = null;
//        try {
//            MongoCollection<Sala> coleccionSalas = obtenerColeccionSalas(clienteMongo);
//
//            Iterable<Bson> filtradores = Arrays.asList(
//                    Filters.regex("estado", Pattern.compile("^" + filtro, Pattern.CASE_INSENSITIVE)),
//                    Filters.regex("estado", Pattern.compile(".*" + filtro + "$", Pattern.CASE_INSENSITIVE)),
//                    Filters.regex("numeroSala", Pattern.compile("^" + filtro, Pattern.CASE_INSENSITIVE)),
//                    Filters.regex("numeroSala", Pattern.compile(".*" + filtro + "$", Pattern.CASE_INSENSITIVE))
//            );
//            Bson filtrador = Filters.or(filtradores);
//
//            FindIterable<Sala> iterador = coleccionSalas.find(filtrador);
//
//            List<Sala> salas = new ArrayList<>();
////            for (Sala salaDoc : iterador) {
////                String numSala = salaDoc.get("numeroSala", String.class);
////                Sala sala = obtenerSalaConAsientos(salaDoc, numSala);
////                salas.add(sala);
////            }
//
//            return salas;
//
//        } catch (BuscarSalaException e) {
//            throw new BuscarSalaException("Hubo un error al buscar las salas: " + e.getMessage());
//        } finally {
//            conexion.cerrarConexion(clienteMongo);
//        }
//    }
    
    private Bson filtroNombre(String filtroNombre) {
        if (filtroNombre != null && !filtroNombre.isBlank()) {
            List<Bson> filtradores = Arrays.asList(
                    Filters.regex("numSala", Pattern.compile("^" + filtroNombre, Pattern.CASE_INSENSITIVE)),
                    Filters.regex("numSala", Pattern.compile(".*" + filtroNombre + "$", Pattern.CASE_INSENSITIVE))
            );

            return Filters.or(filtradores);
        }
        return new Document(); // Si filtroNombre está vacío, no aplica filtros
    }

    private void creacionAsientos(Sala sala, List<Asiento> asientosSala) {
        for (int i = 1; i <= sala.getNumAsientos(); i++) {
            String numero = String.valueOf(i);
            Asiento asiento = new Asiento(numero);
            asientosSala.add(asiento);
        }
    }

    private MongoCollection<Sala> obtenerColeccionSalas(MongoClient clienteMongo) throws BuscarSalaException {
        try {
            clienteMongo = conexion.crearConexion();

            MongoDatabase baseDatos = conexion.obtenerBaseDatos(clienteMongo);

            MongoCollection<Sala> coleccionSalas = baseDatos.getCollection(nombreColeccion, Sala.class);

            return coleccionSalas;
        } catch (MongoException e) {
            throw new BuscarSalaException("Error al realizar la conexion: " + e.getMessage());
        }
    }

}
