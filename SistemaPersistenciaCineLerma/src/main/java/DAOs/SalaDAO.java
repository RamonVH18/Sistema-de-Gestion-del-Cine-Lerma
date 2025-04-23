/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package DAOs;

import Conexion.MongoConexion;
import Excepciones.PersistenciaException;
import Interfaces.ISalaDAO;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import entidades.Asiento;
import entidades.Sala;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author Ramon Valencia
 */
public class SalaDAO implements ISalaDAO{
    
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
    public Sala agregarSala(Sala sala) throws PersistenciaException {
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
                    .append("asientos", listaAsientos);
            
            
            MongoCollection salas = baseDatos.getCollection(nombreColeccion);
            
            salas.insertOne(salaDocument);
            
            return sala;
        } catch (PersistenciaException e) {
            throw new PersistenciaException("Hubo un problema al generar la base de datos: " + e.getMessage());
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
}
