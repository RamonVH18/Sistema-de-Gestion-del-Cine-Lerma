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
    
    public AdministradorDTO registrarAdministrador (AdministradorDTO administrador) throws RegistrarAdminExceptionBO;
    
    public AdministradorDTO actualizarAdministrador (AdministradorDTO administrador) throws ActualizarAdminExceptionBO;
    
    public Boolean eliminarAdministrador (AdministradorDTO administrador) throws EliminarUsuarioExceptionBO;
    
    public Boolean validarAdministrador(String nombreUsuario, String contrasena) throws ValidarUsuarioExceptionBO;
    
    public AdministradorDTO obtenerAdministrador(String nombreUsuario) throws EncontrarAdminExceptionBO;
}
