/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pruebas;

import Conexion.MongoConexion;
import DAOs.FuncionDAO;
import DAOs.SalaDAO;
import Excepciones.Funciones.FuncionSalaOcupadaException;
import Excepciones.Funciones.FuncionSalaVaciaException;
import Excepciones.salas.BuscarSalaException;
import Interfaces.IFuncionDAO;
import Interfaces.ISalaDAO;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import entidades.Funcion;
import entidades.Pelicula;
import entidades.Sala;
import enums.EstadoSala;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bson.conversions.Bson;

/**
 *
 * @author Daniel M
 */
public class pruebasFuncionDAO {

    private static final MongoConexion conexion = new MongoConexion();

    public static void main(String[] args) throws BuscarSalaException, FuncionSalaOcupadaException, FuncionSalaVaciaException {
        IFuncionDAO funcionDAO = FuncionDAO.getInstanceDAO();
        ISalaDAO salaDAO = SalaDAO.getInstanceDAO();
//        List<Funcion> funciones = funcionDAO.mostrarFuncionesActivas();
//        for (Funcion funcion : funciones) {
//            System.out.println(funcion);
//        }

//        Sala sala = salaDAO.buscarSala("D1");
//        String isaac = "Isaac";
//        
//        Pelicula pelicula = new Pelicula(isaac, isaac, isaac, 15, isaac, isaac, Boolean.TRUE);
//
//        Funcion funcion0 = new Funcion(sala, pelicula, LocalDateTime.now(), 35.00);
//        Funcion funcion1 = new Funcion(sala, pelicula, LocalDateTime.now().plusHours(2), 35.00);
//        Funcion funcion2 = new Funcion(sala, pelicula, LocalDateTime.now().plusHours(3), 35.00);
//        Funcion funcion3 = new Funcion(sala, pelicula, LocalDateTime.now().plusHours(4), 35.00);
//        Funcion funcion4 = new Funcion(sala, pelicula, LocalDateTime.now().plusHours(5), 35.00);
//        Funcion funcion5 = new Funcion(sala, pelicula, LocalDateTime.now().plusHours(6), 35.00);
//        
//        funcionDAO.registrarFuncion(funcion0);
//        funcionDAO.registrarFuncion(funcion1);
//        funcionDAO.registrarFuncion(funcion2);
//        funcionDAO.registrarFuncion(funcion3);
//        funcionDAO.registrarFuncion(funcion4);
//        funcionDAO.registrarFuncion(funcion5);
//        
//        
//        MongoClient clienteMongo = null;
//        clienteMongo = conexion.crearConexion();
//        MongoDatabase database = conexion.obtenerBaseDatos(clienteMongo);
//        MongoCollection<Funcion> coleccionFunciones = database.getCollection("Funciones", Funcion.class);
//        
//        List<Bson> filtradores = Arrays.asList(
//                Filters.gte("fechaHora", LocalDateTime.now()),
//                Filters.lte("fechaHora", LocalDateTime.now().plusMinutes(duracionPelicula))
//        );
//        
//        Bson filtro = Filters.and(filtradores);
//        
//        List<Funcion> funciones = coleccionFunciones.find(filtro).into(new ArrayList<>());
//        
//        System.out.println("");
    }

}
