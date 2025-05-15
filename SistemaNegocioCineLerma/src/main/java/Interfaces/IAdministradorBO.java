/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import DTOs.AdministradorDTO;
import Excepciones.Usuarios.EncontrarAdminExceptionBO;
import Excepciones.Usuarios.RegistrarAdminExceptionBO;

/**
 *
 * @author sonic
 */
public interface IAdministradorBO {
    
    public AdministradorDTO registrarAdministradorBO (AdministradorDTO administrador) throws RegistrarAdminExceptionBO;
    
    public AdministradorDTO obtenerAdministradorBO(String nombreUsuario, String contrasena) throws EncontrarAdminExceptionBO;
}
