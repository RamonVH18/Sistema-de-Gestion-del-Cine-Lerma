/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BOs;

import DAOs.AdministradorDAO;
import DTOs.AdministradorDTO;
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
/**
 * Clase que implementa la lógica de negocio para la gestión de administradores.
 */
public class AdministradorBO implements IAdministradorBO {

    private static AdministradorBO instanceAdministradorBO;
    private final IAdministradorDAO adminDAO = AdministradorDAO.getInstance();
    private final IAdministradorMapper mapper = new AdministradorMapper();

    private AdministradorBO() {
    }

    /**
     * Método para obtener la instancia única de AdministradorBO.
     *
     * @return la instancia de AdministradorBO
     */
    public static AdministradorBO getInstanceBO() {
        if (instanceAdministradorBO == null) { // para la primera vez que se llama
            instanceAdministradorBO = new AdministradorBO();
        }
        return instanceAdministradorBO;
    }

    /**
     * Registra un nuevo administrador en el sistema.
     *
     * Este método convierte el objeto AdministradorDTO en un objeto
     * Administrador y utiliza el metodo del DAO para registrar al administrador
     * en la base de datos. Si ocurre un error durante el proceso, se lanza una
     * excepción RegistrarAdminExceptionBO.
     *
     * @param administrador el objeto AdministradorDTO que representa al
     * administrador a registrar
     * @return el objeto AdministradorDTO que representa al administrador
     * registrado
     * @throws RegistrarAdminExceptionBO si ocurre un error al registrar el
     * administrador
     */
    @Override
    public AdministradorDTO registrarAdministradorBO(AdministradorDTO administrador) throws RegistrarAdminExceptionBO {
        try {

            // Convierte el AdministradorDTO a Administrador
            Administrador adminRegistrar = mapper.toAdministradorEntidad(administrador);

            // Llama al DAO para registrar al administrador y obtiene el administrador registrado
            Administrador adminRegistrado = adminDAO.registrarAdministrador(adminRegistrar);

            // Mapea el administrador registrado a AdministradorDTO y lo devuelve
            return mapper.toAdministradorDTO(adminRegistrado);

        } catch (RegistrarAministradorException e) {
            throw new RegistrarAdminExceptionBO(e.getMessage(), e);
        }
    }

    /**
     * Obtiene un administrador específico a partir de su nombre de usuario y
     * contraseña.
     *
     * Este método utiliza el metodo en DAO para buscar un administrador en la base de
     * datos y lo convierte a un objeto AdministradorDTO. Si ocurre un error
     * durante el proceso, se lanza una excepción EncontrarAdminExceptionBO.
     *
     * @param nombreUsuario el nombre de usuario del administrador a buscar
     * @param contrasena la contraseña del administrador a buscar
     * @return el objeto AdministradorDTO que representa al administrador
     * encontrado
     * @throws EncontrarAdminExceptionBO si ocurre un error al encontrar el
     * administrador
     */
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
