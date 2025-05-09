/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BOs;

import DTOs.AdministradorDTO;
import Excepciones.Usuarios.ActualizarAdminExceptionBO;
import Excepciones.Usuarios.EliminarUsuarioExceptionBO;
import Excepciones.Usuarios.EncontrarAdminExceptionBO;
import Excepciones.Usuarios.RegistrarAdminExceptionBO;
import Excepciones.Usuarios.ValidarUsuarioExceptionBO;
import Interfaces.IAdministradorBO;

/**
 *
 * @author sonic
 */
public class AdministradorBO implements IAdministradorBO{

    @Override
    public AdministradorDTO registrarAdministradorBO(AdministradorDTO administrador) throws RegistrarAdminExceptionBO {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public AdministradorDTO actualizarAdministradorBO(AdministradorDTO administrador) throws ActualizarAdminExceptionBO {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Boolean eliminarAdministradorBO(AdministradorDTO administrador) throws EliminarUsuarioExceptionBO {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Boolean validarAdministradorBO(String nombreUsuario, String contrasena) throws ValidarUsuarioExceptionBO {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public AdministradorDTO obtenerAdministradorBO(String nombreUsuario) throws EncontrarAdminExceptionBO {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
