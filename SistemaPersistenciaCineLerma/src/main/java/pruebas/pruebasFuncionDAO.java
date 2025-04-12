/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pruebas;

import DAOs.FuncionDAO;
import Excepciones.PersistenciaException;
import Interfaces.IFuncionDAO;
import entidades.Funcion;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daniel M
 */
public class pruebasFuncionDAO {

    
    public static void main(String[] args) {
        IFuncionDAO funcionDAO = FuncionDAO.getInstanceDAO();
        try {
            List<Funcion> funciones = funcionDAO.mostrarFuncionesActivas();
            
            for (Funcion funcion : funciones) {
                System.out.println(funcion);
            }
        } catch (PersistenciaException ex) {
            Logger.getLogger(pruebasFuncionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
