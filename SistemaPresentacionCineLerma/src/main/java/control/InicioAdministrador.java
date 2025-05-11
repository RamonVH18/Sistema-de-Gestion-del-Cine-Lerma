/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import DTOs.AdministradorDTO;


/**
 *
 * @author Ramon Valencia
 */
public class InicioAdministrador {
    
    public static void main(String[] args) {
        ControlDeNavegacion control = ControlDeNavegacion.getInstancia();
        
        AdministradorDTO admin = new AdministradorDTO();
        
        control.mostrarMenuAdministrador(null, admin);
    }
    
}
