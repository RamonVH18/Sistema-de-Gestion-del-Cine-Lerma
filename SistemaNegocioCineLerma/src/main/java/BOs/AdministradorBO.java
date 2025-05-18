/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BOs;

import DAOs.AdministradorDAO;
import DTOs.AdministradorDTO;
import Excepciones.Usuarios.EliminarUsuarioExceptionBO;
import Excepciones.Usuarios.EncontrarAdminExceptionBO;
import Excepciones.Usuarios.RegistrarAdminExceptionBO;
import Excepciones.usuarios.EncontrarAdministradorException;
import Excepciones.usuarios.RegistrarAministradorException;
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
            throw new RegistrarAdminExceptionBO(e.getMessage(), e);
        }
    }

        

    @Override
    public AdministradorDTO obtenerAdministradorBO(String nombreUsuario, String contrasena) throws EncontrarAdminExceptionBO {
        try {

            Administrador adminEncontrado = adminDAO.obtenerAdministrador(nombreUsuario, contrasena);

            return mapper.toAdministradorDTO(adminEncontrado);

        } catch (EncontrarAdministradorException e) {
            throw new EncontrarAdminExceptionBO("Error al encontrar un usuario", e);
        }
    }
    
}
