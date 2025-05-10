/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BOs;

import DAOs.AdministradorDAO;
import DTOs.AdministradorDTO;
import Excepciones.Usuarios.ActualizarAdminExceptionBO;
import Excepciones.Usuarios.EliminarUsuarioExceptionBO;
import Excepciones.Usuarios.EncontrarAdminExceptionBO;
import Excepciones.Usuarios.RegistrarAdminExceptionBO;
import Excepciones.Usuarios.ValidarUsuarioExceptionBO;
import Excepciones.usuarios.ActualizarAdministradorException;
import Excepciones.usuarios.EliminarUsuarioException;
import Excepciones.usuarios.EncontrarAdministradorException;
import Excepciones.usuarios.RegistrarAministradorException;
import Excepciones.usuarios.ValidarUsuarioException;
import Interfaces.IAdministradorBO;
import Interfaces.IAdministradorDAO;
import Interfaces.mappers.IAdministradorMapper;
import Mappers.AdministradorMapper;
import entidades.Administrador;

/**
 *
 * @author sonic
 */
public class AdministradorBO implements IAdministradorBO{
    
    private static AdministradorBO instanceAdministradorBO;
    private final IAdministradorDAO adminDAO = AdministradorDAO.getInstance();
    private final IAdministradorMapper mapper = new AdministradorMapper();
    
    private AdministradorBO() {
    }

    public static AdministradorBO getInstanceBO() {
        if (instanceAdministradorBO == null) { // para la primera vez que se llama
            instanceAdministradorBO = new AdministradorBO();
        }
        return instanceAdministradorBO;
    }

    @Override
    public AdministradorDTO registrarAdministradorBO(AdministradorDTO administrador) throws RegistrarAdminExceptionBO {
        try {

            Administrador adminRegistrar = mapper.toAdministradorEntidad(administrador);
            Administrador adminRegistrado = adminDAO.registrarAdministrador(adminRegistrar);

            return mapper.toAdministradorDTO(adminRegistrado);

        } catch (RegistrarAministradorException e) {
            throw new RegistrarAdminExceptionBO("Error al registrar un administrador", e);
        }
    }

    @Override
    public AdministradorDTO actualizarAdministradorBO(AdministradorDTO administrador) throws ActualizarAdminExceptionBO {
        try {

            Administrador adminActualizar = mapper.toAdministradorEntidad(administrador);
            Administrador adminActualizado = adminDAO.actualizarAdministrador(adminActualizar);

            return mapper.toAdministradorDTO(adminActualizado);

        } catch (ActualizarAdministradorException e) {
            throw new ActualizarAdminExceptionBO("Error al actualizar el administrador", e);
        }
    }

    @Override
    public Boolean eliminarAdministradorBO(AdministradorDTO administrador) throws EliminarUsuarioExceptionBO {
        try {

            Administrador adminEliminar = mapper.toAdministradorEntidad(administrador);

            return adminDAO.eliminarAdministrador(adminEliminar);

        } catch (EliminarUsuarioException e) {
            throw new EliminarUsuarioExceptionBO("Error al eliminar el cliente", e);
        }     
    }

    @Override
    public Boolean validarAdministradorBO(String nombreUsuario, String contrasena) throws ValidarUsuarioExceptionBO {
        try {

            return adminDAO.validarAdministrador(nombreUsuario, contrasena);

        } catch (ValidarUsuarioException e) {
            throw new ValidarUsuarioExceptionBO("Error al validar el cliente", e);
        }
    }
        

    @Override
    public AdministradorDTO obtenerAdministradorBO(String nombreUsuario) throws EncontrarAdminExceptionBO {
        try {

            Administrador adminEncontrado = adminDAO.obtenerAdministrador(nombreUsuario);

            return mapper.toAdministradorDTO(adminEncontrado);

        } catch (EncontrarAdministradorException e) {
            throw new EncontrarAdminExceptionBO("Error al encontrar un usuario", e);
        }
    }
    
}
