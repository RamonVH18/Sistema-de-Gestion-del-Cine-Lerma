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
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.model.Projections;
import entidades.Asiento;
import entidades.Sala;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

/**
 *
 * @author Ramon Valencia
 */
public class SalaDAO implements ISalaDAO {

    private static SalaDAO instance;
    private MongoConexion conexion = new MongoConexion();
    private final String nombreColeccion = "Salas";

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
            clienteMongo = conexion.crearConexion();

            MongoDatabase baseDatos = conexion.obtenerBaseDatos(clienteMongo);

            List<Asiento> asientosSala = new ArrayList();
            List<Document> listaAsientos = new ArrayList<>();

            creacionAsientos(sala, asientosSala, listaAsientos);

            sala.setAsientos(asientosSala);

            ObjectId salaId = new ObjectId();
            Document salaDocument = new Document("_id", salaId)
                    .append("numeroSala", sala.getNumSala())
                    .append("numeroAsientos", sala.getNumAsientos())
                    .append("estado", sala.getEstado())
                    .append("asientos", listaAsientos);

            MongoCollection salas = baseDatos.getCollection(nombreColeccion);

            salas.insertOne(salaDocument);

            return sala;
        } catch (MongoException e) {
            throw new CreacionSalaException("Hubo un problema al generar la base de datos: " + e.getMessage());
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

    @Override
    public Sala buscarSala(String numSala) throws BuscarSalaException {
        MongoClient clienteMongo = new MongoClient();
        try {
            clienteMongo = conexion.crearConexion();

            MongoDatabase baseDatos = conexion.obtenerBaseDatos(clienteMongo);

            MongoCollection<Document> salas = baseDatos.getCollection(nombreColeccion);

            Bson projectionFields = Projections.fields(
                    Projections.include("_id", "numeroSala", "numeroAsientos", "estado", "asientos")
            );

            Document salaDoc = salas.find(eq("numeroSala", numSala)).projection(projectionFields).first();

            if (salaDoc == null) {
                throw new BuscarSalaException("No se encontró la sala con número: " + numSala);
            }

            List<Document> asientosDoc = salaDoc.getList("asientos", Document.class);
            List<Asiento> asientos = new ArrayList<>();
            
            Sala sala = new Sala(salaDoc.get("_id", ObjectId.class), asientosDoc.size(), numSala, salaDoc.get("estado", Boolean.class));
            
            for (Document asientoDoc : asientosDoc) {
                asientos.add(new Asiento(asientoDoc.get("_id", ObjectId.class), asientoDoc.getString("numeroAsiento"), sala));
            }
            sala.setAsientos(asientos);
            
            return sala;

        } catch (MongoException e) {
            throw new BuscarSalaException("Error al buscar las salas en la base de datos: " + e.getMessage());
        } finally {
            conexion.cerrarConexion(clienteMongo);
        }
    }

    @Override
    public List<Sala> buscarTodasLasSalas() throws BuscarSalaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Boolean modificarSala() throws ModificarSalaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Sala> buscarSalasFiltradas(String filtro) throws BuscarSalaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
