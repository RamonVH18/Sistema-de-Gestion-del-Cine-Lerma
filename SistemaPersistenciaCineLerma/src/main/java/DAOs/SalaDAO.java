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
import com.mongodb.MongoClient;
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

    private final Bson projectionNormal = Projections.fields(
            Projections.include("_id", "numeroSala", "numeroAsientos", "estado", "asientos")
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
        MongoClient clienteMongo = new MongoClient();

        try {
            List<Asiento> asientosSala = new ArrayList();
            List<Document> listaAsientos = new ArrayList<>();

            creacionAsientos(sala, asientosSala, listaAsientos);

            sala.setAsientos(asientosSala);

            ObjectId salaId = new ObjectId();
            Document salaDocument = new Document("_id", salaId)
                    .append("numeroSala", sala.getNumSala())
                    .append("numeroAsientos", sala.getNumAsientos())
                    .append("estado", sala.getEstado().name())
                    .append("asientos", listaAsientos);

            MongoCollection coleccionSalas = obtenerColeccionSalas(clienteMongo);

            coleccionSalas.insertOne(salaDocument);

            return sala;
        } catch (MongoException | BuscarSalaException e) {
            throw new CreacionSalaException("Hubo un problema al generar la base de datos: " + e.getMessage());
        } finally {
            conexion.cerrarConexion(clienteMongo);
        }
    }

    @Override
    public Sala buscarSala(String numSala) throws BuscarSalaException {
        MongoClient clienteMongo = new MongoClient() {};
        try {
            MongoCollection<Document> coleccionSalas = obtenerColeccionSalas(clienteMongo);

            Document salaDoc = coleccionSalas.find(eq("numeroSala", numSala)).projection(projectionNormal).first();

            if (salaDoc == null) {
                throw new BuscarSalaException("No se encontro la sala con numero: " + numSala);
            }

            Sala sala = obtenerSalaConAsientos(salaDoc, numSala);

            return sala;

        } catch (MongoException e) {
            throw new BuscarSalaException("Error al buscar las salas en la base de datos: " + e.getMessage());
        } finally {
            conexion.cerrarConexion(clienteMongo);
        }
    }

    @Override
    public List<Sala> buscarTodasLasSalas() throws BuscarSalaException {
        MongoClient clienteMongo = new MongoClient();
        try {
            MongoCollection<Document> coleccionSalas = obtenerColeccionSalas(clienteMongo);

            FindIterable<Document> iterador = coleccionSalas.find();
            List<Sala> salas = new ArrayList<>();
            for (Document salaDoc : iterador) {
                String numSala = salaDoc.get("numeroSala", String.class);
                Sala sala = obtenerSalaConAsientos(salaDoc, numSala);
                salas.add(sala);
            }
            return salas;
        } catch (BuscarSalaException | MongoException e) {
            throw new BuscarSalaException("Hubo un error al buscar las salas: " + e.getMessage());
        } finally {
            conexion.cerrarConexion(clienteMongo);
        }
    }

    private Sala obtenerSalaConAsientos(Document salaDoc, String numSala) throws BuscarSalaException {
        try {
            List<Document> asientosDoc = salaDoc.getList("asientos", Document.class);
            List<Asiento> asientos = new ArrayList<>();

            Sala sala = new Sala(
                    salaDoc.get("_id", ObjectId.class),
                    asientosDoc.size(),
                    numSala,
                    EstadoSalaFactory.obtenerEstadoSala(salaDoc.get("estado", String.class))
            );

            for (Document asientoDoc : asientosDoc) {
                asientos.add(new Asiento(asientoDoc.get("_id", ObjectId.class), asientoDoc.getString("numeroAsiento"), sala));
            }

            sala.setAsientos(asientos);
            return sala;
        } catch (MongoException e) {
            throw new BuscarSalaException("Error al obtener los asientos de la sala " + numSala + ": " + e.getMessage());
        }
    }

    @Override
    public Boolean modificarEstadoSala(Sala sala) throws ModificarSalaException {
        MongoClient clienteMongo = new MongoClient();
        try {
            MongoCollection<Document> coleccionSalas = obtenerColeccionSalas(clienteMongo);

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

    @Override
    public List<Sala> buscarSalasFiltradas(String filtro) throws BuscarSalaException {
        MongoClient clienteMongo = new MongoClient();
        try {
            MongoCollection<Document> coleccionSalas = obtenerColeccionSalas(clienteMongo);

            Iterable<Bson> filtradores = Arrays.asList(
                    Filters.regex("estado", Pattern.compile("^" + filtro, Pattern.CASE_INSENSITIVE)),
                    Filters.regex("estado", Pattern.compile(".*" + filtro + "$", Pattern.CASE_INSENSITIVE)),
                    Filters.regex("numeroSala", Pattern.compile("^" + filtro, Pattern.CASE_INSENSITIVE)),
                    Filters.regex("numeroSala", Pattern.compile(".*" + filtro + "$", Pattern.CASE_INSENSITIVE))
            );
            Bson filtrador = Filters.or(filtradores);
            
            FindIterable<Document> iterador = coleccionSalas.find(filtrador);
            
            List<Sala> salas = new ArrayList<>();
            for (Document salaDoc : iterador) {
                String numSala = salaDoc.get("numeroSala", String.class);
                Sala sala = obtenerSalaConAsientos(salaDoc, numSala);
                salas.add(sala);
            }
            
            return salas;
            
        } catch (BuscarSalaException e) {
            throw new BuscarSalaException("Hubo un error al buscar las salas: " + e.getMessage());
        } finally {
            conexion.cerrarConexion(clienteMongo);
        }
    }
    
    
    private void creacionAsientos(Sala sala, List<Asiento> asientosSala, List<Document> listaAsientos) {
        for (int i = 1; i <= sala.getNumAsientos(); i++) {
            String numero = String.valueOf(i);
            ObjectId asientoId = new ObjectId();
            Asiento asiento = new Asiento(asientoId, numero, sala);

            asientosSala.add(asiento);
            Document asientoDocument = new Document("_id", asientoId).append("numeroAsiento", numero);
            listaAsientos.add(asientoDocument);
        }
    }

    private MongoCollection<Document> obtenerColeccionSalas(MongoClient clienteMongo) throws BuscarSalaException {
        try {
            clienteMongo = conexion.crearConexion();

            MongoDatabase baseDatos = conexion.obtenerBaseDatos(clienteMongo);

            MongoCollection<Document> coleccionSalas = baseDatos.getCollection(nombreColeccion);

            if (coleccionSalas.countDocuments() == 0) {
                throw new BuscarSalaException("No se encontro ninguna sala registrada en la base de datos");
            }
            return coleccionSalas;
        } catch (MongoException e) {
            throw new BuscarSalaException("Error al realizar la conexion: " + e.getMessage());
        }
    }
    
}
