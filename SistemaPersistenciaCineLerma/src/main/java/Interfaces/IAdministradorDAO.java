/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Excepciones.usuarios.ActualizarAdministradorException;
import Excepciones.usuarios.EliminarUsuarioException;
import Excepciones.usuarios.EncontrarAdministradorException;
import Excepciones.usuarios.RegistrarAministradorException;
import Excepciones.usuarios.ValidarUsuarioException;
import entidades.Administrador;

/**
 *
 * @author sonic
 */
public interface IAdministradorDAO {
    
    public Administrador registrarAdministrador (Administrador administrador) throws RegistrarAministradorException;
    
    public Administrador actualizarAdministrador (Administrador administrador) throws ActualizarAdministradorException;
    
    public Boolean eliminarAdministrador (Administrador administrador) throws EliminarUsuarioException;
    
//    public Boolean validarAdministrador(String nombreUsuario, String contrasena) throws ValidarUsuarioException;
    
     public Administrador obtenerAdministrador(String nombreUsuario, String contrasena) throws EncontrarAdministradorException;
    
}
