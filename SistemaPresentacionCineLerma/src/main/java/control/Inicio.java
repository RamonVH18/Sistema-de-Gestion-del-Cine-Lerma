/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package control;

import control.ControlDeNavegacion;

/**
 *
 * @author Ramon Valencia
 */
public class Inicio {

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        ControlDeNavegacion control = ControlDeNavegacion.getInstancia();
        
        control.mostrarMenuPrincipal();
    }
    
}
