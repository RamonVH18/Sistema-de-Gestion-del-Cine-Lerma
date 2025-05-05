/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.List;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

/**
 *
 * @author sonic
 */
public class ConexionMejor {

    private final String servidor = "localhost";
    private final int puerto = 27017;
    private final String nombreBase = "CineLerma";
    private final String connectionString = String.format("mongodb://%s:%d", servidor, puerto);

    private final CodecRegistry codec = fromRegistries(
            MongoClientSettings.getDefaultCodecRegistry(),
            fromProviders(PojoCodecProvider.builder().automatic(true).build())
    );

    public MongoClient crearConexion() throws MongoException {
        try {
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(new com.mongodb.ConnectionString(connectionString))
                    .codecRegistry(codec)
                    .build();
            
            return MongoClients.create(settings);
            
        } catch (MongoException e) {
            throw new MongoException("Hubo un error al conectarse a MongoDB: " + e.getMessage());
        }
    }

    public MongoDatabase obtenerBaseDatos(MongoClient conexion) throws MongoException {
        return conexion.getDatabase(nombreBase).withCodecRegistry(codec);
        
    }

    public void cerrarConexion(MongoClient conexion) {
        if (conexion != null) {
            conexion.close();
        }
    }
}
