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
/**
 * Interfaz que define las operaciones de negocio relacionadas con los administradores.
 * Proporciona métodos para registrar un administrador y obtener un administrador específico.
 */
public interface IAdministradorBO {
    
    /**
     * Registra un nuevo administrador en el sistema.
     *
     * Este método recibe un objeto AdministradorDTO con los datos del administrador a registrar.
     * Al completarse exitosamente, devuelve el AdministradorDTO registrado, que puede incluir
     * información adicional generada por el sistema, como un identificador único.
     *
     * @param administrador El objeto AdministradorDTO que contiene los datos del administrador a registrar.
     * @return El AdministradorDTO registrado con la información actualizada.
     * @throws RegistrarAdminExceptionBO Si ocurre un error durante el proceso de registro, 
     *                                  como datos inválidos o problemas en la persistencia.
     */
    public AdministradorDTO registrarAdministradorBO (AdministradorDTO administrador) throws RegistrarAdminExceptionBO;
    
    /**
     * Obtiene un administrador específico basado en su nombre de usuario y contraseña.
     *
     * Este método busca en el sistema un administrador que coincida con las credenciales proporcionadas.
     * Si se encuentra, devuelve el objeto AdministradorDTO correspondiente; de lo contrario, puede devolver null.
     *
     * @param nombreUsuario El nombre de usuario del administrador a obtener.
     * @param contrasena La contraseña asociada al administrador.
     * @return El AdministradorDTO que representa al administrador encontrado, o null si no existe.
     * @throws EncontrarAdminExceptionBO Si ocurre un error durante la búsqueda, como problemas con la base de datos.
     */
    public AdministradorDTO obtenerAdministradorBO(String nombreUsuario, String contrasena) throws EncontrarAdminExceptionBO;
}
