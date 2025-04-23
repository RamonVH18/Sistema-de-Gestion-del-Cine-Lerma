/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pruebas;

import DAOs.SalaDAO;
import Excepciones.PersistenciaException;
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
     */
    public static void main(String[] args) throws PersistenciaException{
        // TODO code application logic here

        
        Sala sala = new Sala(10, "Y4", Boolean.TRUE);
        
        Sala superSala = salaDAO.agregarSala(sala);
        
        return;
    }
    
}
