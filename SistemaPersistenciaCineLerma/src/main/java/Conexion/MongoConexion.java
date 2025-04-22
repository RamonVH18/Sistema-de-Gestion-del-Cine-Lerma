/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion;

import Excepciones.PersistenciaException;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoDatabase;
import java.util.List;

/**
 *
 * @author Ramon Valencia
 */
public class MongoConexion {

    String servidor = "localhost";
    Integer puerto = 27017;
    String nombreBase = "CineLerma";

    public MongoClient crearConexion() throws PersistenciaException {
        MongoClient conexionMongo = null;
        try {
            conexionMongo = new MongoClient(servidor, puerto);

        } catch (MongoException e) {
            throw new PersistenciaException("Hubo un error al conectarse a la base de datos: " + e.getMessage());
        }
        return conexionMongo;
    }

    public MongoDatabase obtenerBaseDatos(MongoClient conexion) throws PersistenciaException {
        MongoDatabase baseDatos = null;
        try {
            List<String> nombresBaseDatos = conexion.getDatabaseNames();

            if (nombresBaseDatos.contains(nombreBase)) {
                baseDatos = conexion.getDatabase(nombreBase);
            } else {
                throw new PersistenciaException("Hubo un error con el nombre de la base de datos");
            }

        } catch (MongoException e) {
            throw new PersistenciaException("Hubo un error al conectarse a la base de datos: " + e.getMessage());
        }
        return baseDatos;
    }

    public void cerrarConexion(MongoClient conexion) {
        if (conexion != null) {
            conexion.close();
        }
    }

}
