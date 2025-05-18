/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Excepciones.usuarios.EncontrarAdministradorException;
import Excepciones.usuarios.RegistrarAministradorException;
import Excepciones.usuarios.ValidarUsuarioException;
import entidades.Administrador;

/**
 *
 * @author sonic
 */
/**
 * Interfaz IAdministradorDAO que define las operaciones para gestionar administradores en el sistema.
 * Proporciona métodos para registrar y obtener administradores.
 */
public interface IAdministradorDAO {
    
    //Toda la gestion de clientes y administradores se lleva a cabo por un administrador que haya ingresado al sistema,
    //por cuestiones de diseño se decidio que los administradores no podrian bloquearse por otros administradores, tampoco pueden eliminarse
    //ademas los administradores no cuentan con un historial de compras por que no son clientes
    
    /**
     * Registra un nuevo administrador en el sistema, antes de registrar verifica la unicidad.
     *
     * @param administrador el objeto Administrador que se desea registrar.
     * @return el objeto Administrador registrado, con información actualizada como identificadores.
     * @throws RegistrarAministradorException si ocurre un error durante el proceso de registro del administrador.
     */
    public Administrador registrarAdministrador (Administrador administrador) throws RegistrarAministradorException;
    
     /**
     * Obtiene un administrador basado en su nombre de usuario y contraseña, busca tambien solo a usuarios cuyo rol sea ADMINISTRADOR.
     *
     * @param nombreUsuario el nombre de usuario del administrador que se desea obtener.
     * @param contrasena la contraseña del administrador que se desea obtener.
     * @return el objeto Administrador correspondiente a las credenciales proporcionadas.
     * @throws EncontrarAdministradorException si no se encuentra al administrador o ocurre un error durante la búsqueda.
     */
    public Administrador obtenerAdministrador(String nombreUsuario, String contrasena) throws EncontrarAdministradorException;
    
}
