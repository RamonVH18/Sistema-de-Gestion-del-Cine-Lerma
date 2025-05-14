/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pruebas;

import DAOs.AsientoFuncionDAO;
import DAOs.FuncionDAO;
import DAOs.SalaDAO;
import Excepciones.AsientoFuncion.FalloCreacionAsientosFuncionException;
import Excepciones.Funciones.FuncionSalaOcupadaException;
import Excepciones.salas.BuscarSalaException;
import Interfaces.IAsientoFuncionDAO;
import Interfaces.IFuncionDAO;
import Interfaces.ISalaDAO;
import entidades.Asiento;
import entidades.AsientoFuncion;
import entidades.Funcion;
import entidades.Pelicula;
import entidades.Sala;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ramon Valencia
 */
public class pruebasAsientoFuncionDAO {
    private static IFuncionDAO funcionDAO = FuncionDAO.getInstanceDAO();
    private static ISalaDAO salaDAO = SalaDAO.getInstanceDAO(); 
    private static IAsientoFuncionDAO asientoFuncionDAO = AsientoFuncionDAO.getInstanceDAO();
    
    public static void main(String[] args) throws BuscarSalaException, FuncionSalaOcupadaException, FalloCreacionAsientosFuncionException {
        
//            Sala sala = salaDAO.buscarSala("C1");
//            Pelicula pelicula = new Pelicula("Cholos Empotrados", "Jaime", "Trans", 120, "El Abraham es gay", Boolean.TRUE);
//            Funcion funcion = new Funcion(sala, pelicula, LocalDateTime.now(), 120.00);
//            
//            funcion = funcionDAO.registrarFuncion(funcion);
//            
//            List<AsientoFuncion> asientosFuncion = new ArrayList<>();
//            List<Asiento> asientosSala = sala.getAsientos();
//            Asiento asiento;
//            for (int i = 0; i < sala.getNumAsientos(); i++) {
//                asiento = asientosSala.get(i);
//                AsientoFuncion nuevoAsiento = new AsientoFuncion(funcion, asiento.getNumero(), Boolean.TRUE, null);
//                asientosFuncion.add(nuevoAsiento);
//            }
//            
//            asientoFuncionDAO.agregarAsientosFuncion(asientosFuncion);
        
        
    }
    
    
}
