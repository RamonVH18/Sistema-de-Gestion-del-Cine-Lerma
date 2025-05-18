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

    private static ISalaDAO salaDAO = SalaDAO.getInstanceDAO();

    /**
     * @param args the command line arguments
     * @throws Excepciones.salas.CreacionSalaException
     * @throws Excepciones.salas.BuscarSalaException
     */
    public static void main(String[] args) throws CreacionSalaException, BuscarSalaException, ModificarSalaException {
        // TODO code application logic here

//        Sala sala0 = new Sala(20, "A1", EstadoSala.ACTIVA);
//        Sala sala1 = new Sala(15, "A2", EstadoSala.INACTIVA);
//        Sala sala2 = new Sala(30, "A3", EstadoSala.MANTENIMIENTO);
//        Sala sala3 = new Sala(25, "B1", EstadoSala.ACTIVA);
//        Sala sala4 = new Sala(18, "B2", EstadoSala.INACTIVA);
//        Sala sala5 = new Sala(22, "B3", EstadoSala.ACTIVA);
//        Sala sala6 = new Sala(28, "B4", EstadoSala.MANTENIMIENTO);
//        Sala sala7 = new Sala(35, "C1", EstadoSala.ACTIVA);
//        Sala sala8 = new Sala(26, "C2", EstadoSala.ACTIVA);
//        Sala sala9 = new Sala(40, "C3", EstadoSala.INACTIVA);
//        Sala sala10 = new Sala(32, "D1", EstadoSala.ACTIVA);
//
//        salaDAO.agregarSala(sala0);
//        salaDAO.agregarSala(sala1);
//        salaDAO.agregarSala(sala2);
//        salaDAO.agregarSala(sala3);
//        salaDAO.agregarSala(sala4);
//        salaDAO.agregarSala(sala5);
//        salaDAO.agregarSala(sala6);
//        salaDAO.agregarSala(sala7);
//        salaDAO.agregarSala(sala8);
//        salaDAO.agregarSala(sala9);
//        salaDAO.agregarSala(sala10);

//        Sala ultraSala = salaDAO.buscarSala("P4");
//
//        List<Sala> salasAlgoBien = salaDAO.buscarSalas("");
//        
//        ultraSala.setEstado(EstadoSala.MANTENIMIENTO);
//        
//        Boolean Isaac = salaDAO.modificarEstadoSala(ultraSala);
//        

        System.out.println("");
    }

}
