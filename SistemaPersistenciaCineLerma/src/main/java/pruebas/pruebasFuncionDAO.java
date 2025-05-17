/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pruebas;

import Conexion.MongoConexion;
import DAOs.FuncionDAO;
import DAOs.SalaDAO;
import Excepciones.Funciones.FuncionDuracionIncorrectaException;
import Excepciones.Funciones.FuncionSalaOcupadaException;
import Excepciones.Funciones.FuncionSalaVaciaException;
import Excepciones.salas.BuscarSalaException;
import Excepciones.salas.CreacionSalaException;
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

    public static void main(String[] args) throws BuscarSalaException, FuncionSalaOcupadaException, FuncionSalaVaciaException, CreacionSalaException, FuncionDuracionIncorrectaException {
        IFuncionDAO funcionDAO = FuncionDAO.getInstanceDAO();
        ISalaDAO salaDAO = SalaDAO.getInstanceDAO();

    }

}
