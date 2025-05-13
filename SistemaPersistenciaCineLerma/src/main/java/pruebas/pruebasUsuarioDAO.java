/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pruebas;

import DAOs.ClienteDAO;
import Excepciones.usuarios.EncontrarClienteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sonic
 */
public class pruebasUsuarioDAO {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            ClienteDAO usuarioDAO = ClienteDAO.getInstance();


            System.out.println(usuarioDAO.obtenerCliente("Sebas", "pipiski"));

}       catch (EncontrarClienteException ex) {
            Logger.getLogger(pruebasUsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
}
}

