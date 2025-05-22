/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Excepciones.usuarios.ActualizarClienteException;
import Excepciones.usuarios.CargarHistorialException;
import Excepciones.usuarios.EncontrarClienteException;
import Excepciones.usuarios.RegistrarClienteException;
import Excepciones.usuarios.ValidarUsuarioException;
import entidades.Cliente;
import entidades.Compra;
import java.util.List;

/**
 *
 * @author sonic
 */
/**
 * Interfaz IClienteDAO que define las operaciones para gestionar clientes en el sistema.
 * Esta interfaz proporciona métodos para registrar, actualizar, obtener clientes y cargar su historial de compras.
 */
public interface IClienteDAO {
    
    //Por cuestiones de diseño se decidio que los clientes no se pueden eliminar de la base de datos, solamente podran marcarse como bloqueados y activos
    
    /**
     * Registra un nuevo cliente en el sistema, antes de registrar se verifica que el usuario a registrar no contenga credenciales que ya existen en la base de datos.
     *
     * @param cliente el objeto Cliente que se desea registrar.
     * @return el objeto Cliente registrado, normalmente con cualquier información adicional asignada (como ID).
     * @throws RegistrarClienteException si ocurre un error durante el proceso de registro del cliente.
     */
    public Cliente registrarCliente (Cliente cliente) throws RegistrarClienteException;
    
    /**
     * Actualiza la información de un cliente existente en el sistema, antes de hacer la actualizacion se verifica la unicidad de las credenciales tambien.
     * 
     *
     * @param cliente el objeto Cliente con la información actualizada.
     * @return el objeto Cliente actualizado.
     * @throws ActualizarClienteException si ocurre un error durante el proceso de actualización del cliente.
     */
    public Cliente actualizarCliente (Cliente cliente) throws ActualizarClienteException;
    
    /**
     * Obtiene un cliente basado en su nombre de usuario y contraseña, este metodo busca sobre los usuarios cuyo rol sea especificamente CLIENTE
     * ademas de buscar por su usuario y contraseña.
     *
     * @param nombreUsuario el nombre de usuario del cliente que se desea obtener.
     * @param contrasena la contraseña del cliente que se desea obtener.
     * @return el objeto Cliente correspondiente a las credenciales proporcionadas.
     * @throws EncontrarClienteException si no se encuentra al cliente o ocurre un error durante la búsqueda.
     */
    public Cliente obtenerCliente(String nombreUsuario, String contrasena) throws EncontrarClienteException;
    
    /**
     * Carga el historial de compras de un cliente específico.
     *
     * @param cliente el objeto Cliente cuyo historial de compras se desea cargar.
     * @return una lista de objetos Compra que representan el historial de compras del cliente.
     * @throws CargarHistorialException si ocurre un error al cargar el historial de compras.
     */
//    public List<Compra> cargarHistorialCompras(Cliente cliente) throws CargarHistorialException;
    
    
}
