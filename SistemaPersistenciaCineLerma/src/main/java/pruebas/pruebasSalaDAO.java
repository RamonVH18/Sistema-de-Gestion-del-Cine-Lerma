/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pruebas;

import DAOs.SalaDAO;
import Excepciones.salas.BuscarSalaException;
import Excepciones.salas.CreacionSalaException;
import Excepciones.salas.ModificarSalaException;
import Interfaces.ISalaDAO;
import entidades.Sala;
import enums.EstadoSala;
import java.util.List;

/**
 *
 * @author Ramon Valencia
 */
public class pruebasSalaDAO {
    private static ISalaDAO salaDAO = SalaDAO.getInstance();
    /**
     * @param args the command line arguments
     * @throws Excepciones.salas.CreacionSalaException
     * @throws Excepciones.salas.BuscarSalaException
     */
    public static void main(String[] args) throws CreacionSalaException, BuscarSalaException, ModificarSalaException{
        // TODO code application logic here
        
//        Sala sala = new Sala(25, "P4", EstadoSala.ACTIVA);
//        
//        Sala superSala = salaDAO.agregarSala(sala);
//        
//        Sala ultraSala = salaDAO.buscarSala("O2");
//
//        List<Sala> salasAlgoBien = salaDAO.buscarTodasLasSalas();
//        
//        ultraSala.setEstado(EstadoSala.MANTENIMIENTO);
//        
//        Boolean Isaac = salaDAO.modificarEstadoSala(ultraSala);
//        
        List <Sala> salasCabronas = salaDAO.buscarSalasFiltradas("5");
        System.out.println("");
    }
    
}
