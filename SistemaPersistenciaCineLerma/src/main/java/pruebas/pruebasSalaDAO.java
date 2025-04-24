/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pruebas;

import DAOs.SalaDAO;
import Excepciones.salas.BuscarSalaException;
import Excepciones.salas.CreacionSalaException;
import Interfaces.ISalaDAO;
import entidades.Sala;

/**
 *
 * @author Ramon Valencia
 */
public class pruebasSalaDAO {
    private static ISalaDAO salaDAO = SalaDAO.getInstance();
    /**
     * @param args the command line arguments
     * @throws Excepciones.salas.CreacionSalaException
     */
    public static void main(String[] args) throws CreacionSalaException, BuscarSalaException{
        // TODO code application logic here

        
        Sala sala = new Sala(15, "J1", Boolean.TRUE);
        
        Sala superSala = salaDAO.agregarSala(sala);
        
        Sala ultraSala = salaDAO.buscarSala("J1");
        
        System.out.println("");
    }
    
}
