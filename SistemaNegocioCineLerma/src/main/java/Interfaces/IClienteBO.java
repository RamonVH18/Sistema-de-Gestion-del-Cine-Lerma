/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import DTOs.ClienteDTO;
import DTOs.CompraDTO;
import Excepciones.Usuarios.ActualizarClienteExceptionBO;
import Excepciones.Usuarios.CargarHistorialExceptionBO;
import Excepciones.Usuarios.EliminarUsuarioExceptionBO;
import Excepciones.Usuarios.EncontrarClienteExceptionBO;
import Excepciones.Usuarios.RegistrarClienteExceptionBO;
import Excepciones.Usuarios.ValidarUsuarioExceptionBO;
import entidades.Cliente;
import entidades.Compra;
import java.util.List;

/**
 *
 * @author sonic
 */

/**
 * Interfaz que define las operaciones de negocio relacionadas con los clientes.
 * Esta interfaz proporciona métodos para registrar, actualizar, obtener clientes
 * y cargar su historial de compras.
 */
public interface IClienteBO {
    
    /**
     * Registra un nuevo cliente en el sistema.
     *
     * Este método toma un objeto ClienteDTO que contiene la información del cliente
     * a registrar, como nombre, dirección, correo electrónico, etc. Si el registro
     * es exitoso, se devuelve el ClienteDTO registrado, que puede incluir información
     * adicional generada por el sistema, como un ID de cliente.
     *
     * @param cliente El objeto ClienteDTO que contiene los datos del cliente a registrar.
     * @return El ClienteDTO registrado con los datos actualizados o generados después del registro.
     * @throws RegistrarClienteExceptionBO Si ocurre un error durante el proceso de registro del cliente,
     *                                       como datos inválidos o problemas de conexión a la base de datos.
     */
    public ClienteDTO registrarClienteBO (ClienteDTO cliente) throws RegistrarClienteExceptionBO;
    
    /**
     * Actualiza la información de un cliente existente.
     *
     * Este método permite modificar los datos de un cliente ya registrado en el sistema.
     * Se espera que el objeto ClienteDTO contenga la información actualizada del cliente.
     * Si la actualización es exitosa, se devuelve el ClienteDTO actualizado.
     *
     * @param cliente El objeto ClienteDTO que contiene los datos actualizados del cliente.
     * @return El ClienteDTO actualizado con la nueva información.
     * @throws ActualizarClienteExceptionBO Si ocurre un error durante el proceso de actualización,
     *                                       como el cliente no encontrado o problemas de conexión a la base de datos.
     */
    public ClienteDTO actualizarClienteBO (ClienteDTO cliente) throws ActualizarClienteExceptionBO;
    
    /**
     * Obtiene un cliente específico basado en su nombre de usuario y contraseña.
     *
     * Este método busca un cliente en el sistema utilizando las credenciales proporcionadas.
     * Si se encuentra un cliente que coincide con el nombre de usuario y la contraseña,
     * se devuelve el objeto ClienteDTO correspondiente. Si no se encuentra, se puede devolver null.
     *
     * @param nombreUsuario El nombre de usuario del cliente a obtener.
     * @param contrasena La contraseña asociada al cliente.
     * @return El objeto ClienteDTO que representa al cliente buscado, o null si no se encuentra.
     * @throws EncontrarClienteExceptionBO Si ocurre un error durante la búsqueda del cliente,
     *                                      como problemas de conexión a la base de datos.
     */
    public ClienteDTO obtenerClienteBO(String nombreUsuario, String contrasena) throws EncontrarClienteExceptionBO;
    
    /**
     * Carga el historial de compras de un cliente dado.
     *
     * Este método recupera todas las compras realizadas por un cliente específico.
     * Se espera que el objeto ClienteDTO contenga la información necesaria para identificar
     * al cliente. Se devuelve una lista de objetos CompraDTO que representan las compras
     * realizadas por el cliente.
     *
     * @param cliente El objeto ClienteDTO del cual se desea obtener el historial de compras.
     * @return Una lista de objetos CompraDTO representando todas las compras realizadas por el cliente.
     * @throws CargarHistorialExceptionBO Si ocurre un error durante la carga del historial de compras,
     *                                     como el cliente no encontrado o problemas de conexión a la base de datos.
     */
//    public List<CompraDTO> cargarHistorialComprasBO(ClienteDTO cliente) throws CargarHistorialExceptionBO;
}
