/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Conexion.MongoConexion;
import Excepciones.AsientoFuncion.FalloCreacionAsientosFuncionException;
import Excepciones.AsientoFuncion.FalloObtencionColeccionException;
import Excepciones.PersistenciaException;
import Interfaces.IAsientoFuncionDAO;
import Interfaces.IFuncionDAO;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import entidades.Asiento;
import entidades.AsientoFuncion;
import entidades.Funcion;
import entidades.Sala;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase AsientoFuncionDAO encargada de manipular los AsientoFuncion persistidos
 * en la base de datos
 *
 * @author Ramon Valencia
 */
public class AsientoFuncionDAO implements IAsientoFuncionDAO {

    private static AsientoFuncionDAO instance; // Se crea un objeto de AsientoFuncionDAO para poder ejecutar el Singletone
    private final MongoConexion conexion = new MongoConexion(); // Se inicia una conexionMongo
    private final IFuncionDAO funcionDAO = FuncionDAO.getInstanceDAO();
    private final String nombreColeccion = "AsientosFuncion";

    /**
     * Constructor privado y vacio para la ejecuccion del SingleTone
     */
    private AsientoFuncionDAO() {
    }

    /**
     * Metodo para regresar la instancia de esta clase y que de esa manera nomas
     * exista una instancia de clase
     *
     * @return
     */
    public static AsientoFuncionDAO getInstance() {
        if (instance == null) {
            instance = new AsientoFuncionDAO();
        }
        return instance;
    }
    /**
     * Metodo para agregar nuevos Asientos de una funcion en la base de datos
     * @param asientosFuncion
     * @return
     * @throws FalloCreacionAsientosFuncionException 
     */
    @Override
    public List<AsientoFuncion> agregarAsientosFuncion(List<AsientoFuncion> asientosFuncion) throws FalloCreacionAsientosFuncionException {
        MongoClient clienteMongo = null;
        try {
            MongoCollection colecionAF = obtenerColeccionAsientoFuncion(clienteMongo);
            
            colecionAF.insertMany(asientosFuncion);
            
            return asientosFuncion;
        } catch (FalloObtencionColeccionException e) {
            throw new FalloCreacionAsientosFuncionException("Hubo un error al insertar los asientos en la base de datos: " + e.getMessage());
        }
    }

    @Override
    public List<AsientoFuncion> mostrarAsientosFunciones(Funcion funcion) throws PersistenciaException {

//        List<AsientoFuncion> asientosFuncion = asientosHarcodeados();
//        for (int i = 1; i < asientosFuncion.size(); i++) {
//            AsientoFuncion asiento = asientosFuncion.get(i);
//            if (asiento.getFuncion() == funcion) {
//                asientosFuncion.add(asiento);
//            }
//        }
//        return asientosFuncion;
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Boolean ocuparAsiento(AsientoFuncion asiento) throws PersistenciaException {
//        asientosHarcodeados();
//        int indice = asientosFuncion.indexOf(asiento);
//
//        asiento.setDisponibilidad(Boolean.FALSE);
//
//        asientosFuncion.set(indice, asiento);
//
//        return true;
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<AsientoFuncion> mostrarAsientosDisponibles(Funcion funcion) throws PersistenciaException {
//        asientosHarcodeados();
//        List<AsientoFuncion> asientosFuncion = asientosHarcodeados();
//        List<AsientoFuncion> asientos = new ArrayList<>();
//        for (int i = 1; i < asientosFuncion.size(); i++) {
//            AsientoFuncion asiento = asientosFuncion.get(i);
//            if (asiento.getFuncion().getIdFuncion() == funcion.getIdFuncion() && asiento.getDisponibilidad() == true) {
//                asientos.add(asiento);
//            }
//        }
//        return asientos;
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private MongoCollection<AsientoFuncion> obtenerColeccionAsientoFuncion(MongoClient clienteMongo) throws FalloObtencionColeccionException {
        try {
            clienteMongo = conexion.crearConexion(); // Se llama a un metodo de la clase conexion para que se cree la conexion

            MongoDatabase baseDatos = conexion.obtenerBaseDatos(clienteMongo);

            MongoCollection<AsientoFuncion> coleccionAF = baseDatos.getCollection(nombreColeccion, AsientoFuncion.class);

            return coleccionAF;
        } catch (MongoException e) {
            throw new FalloObtencionColeccionException("Error al realizar la conexion: " + e.getMessage());
        }
    }

}
