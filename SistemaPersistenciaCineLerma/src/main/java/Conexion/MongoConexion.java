/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion;

import com.mongodb.client.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import entidades.Administrador;
import entidades.Asiento;
import entidades.AsientoFuncion;
import entidades.Cliente;
import entidades.Compra;
import entidades.Empleado;
import entidades.Funcion;
import entidades.Pago;
import entidades.Pelicula;
import entidades.Sala;
import entidades.Usuario;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

/**
 *
 * @author Ramon Valencia
 */
public class MongoConexion {

    private final String servidor = "localhost";
    private final int puerto = 27017;
    private final String nombreBase = "CineLerma";
    private final String connectionString = String.format("mongodb://%s:%d", servidor, puerto);

    private final CodecRegistry codecRegistry;

    public MongoConexion() {
        PojoCodecProvider pojoCodecProvider = PojoCodecProvider.builder()
                .register(Usuario.class, Cliente.class, Administrador.class, Sala.class, Pelicula.class, Pago.class, Funcion.class, Empleado.class, Compra.class,
                         AsientoFuncion.class, Asiento.class)
                .build();

        this.codecRegistry = fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(pojoCodecProvider)
        );
    }


    public MongoClient crearConexion() throws MongoException {
        try {
             MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(new com.mongodb.ConnectionString(connectionString))
                    .codecRegistry(codecRegistry)
                    .build();

            return MongoClients.create(settings);

        } catch (MongoException e) {
            throw new MongoException("Hubo un error al conectarse a MongoDB: " + e.getMessage());
        }
    }

    public MongoDatabase obtenerBaseDatos(MongoClient conexion) throws MongoException {
        return conexion.getDatabase(nombreBase).withCodecRegistry(codecRegistry);

    }

    public void cerrarConexion(MongoClient conexion) {
        if (conexion != null) {
            conexion.close();
        }
    }
}