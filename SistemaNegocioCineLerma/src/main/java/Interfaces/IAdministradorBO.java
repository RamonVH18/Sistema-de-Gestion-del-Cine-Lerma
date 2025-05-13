/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import DTOs.AdministradorDTO;
import Excepciones.Usuarios.ActualizarAdminExceptionBO;
import Excepciones.Usuarios.EliminarUsuarioExceptionBO;
import Excepciones.Usuarios.EncontrarAdminExceptionBO;
import Excepciones.Usuarios.RegistrarAdminExceptionBO;
import Excepciones.Usuarios.ValidarUsuarioExceptionBO;

/**
 *
 * @author sonic
 */
public interface IAdministradorBO {
    
    public AdministradorDTO registrarAdministradorBO (AdministradorDTO administrador) throws RegistrarAdminExceptionBO;
    
    public AdministradorDTO actualizarAdministradorBO (AdministradorDTO administrador) throws ActualizarAdminExceptionBO;
    
    public Boolean eliminarAdministradorBO (AdministradorDTO administrador) throws EliminarUsuarioExceptionBO;
    
//    public Boolean validarAdministradorBO(String nombreUsuario, String contrasena) throws ValidarUsuarioExceptionBO;
    
    public AdministradorDTO obtenerAdministradorBO(String nombreUsuario, String contrasena) throws EncontrarAdminExceptionBO;
}
