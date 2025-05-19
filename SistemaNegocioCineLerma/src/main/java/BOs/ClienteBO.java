/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BOs;

import DAOs.ClienteDAO;
import DTOs.ClienteDTO;
import DTOs.CompraDTO;
import Excepciones.Usuarios.ActualizarClienteExceptionBO;
import Excepciones.Usuarios.CargarHistorialExceptionBO;
import Excepciones.Usuarios.EncontrarClienteExceptionBO;
import Excepciones.Usuarios.RegistrarClienteExceptionBO;
import Excepciones.usuarios.ActualizarClienteException;
import Excepciones.usuarios.CargarHistorialException;
import Excepciones.usuarios.EncontrarClienteException;
import Excepciones.usuarios.RegistrarClienteException;
import Interfaces.IClienteBO;
import Interfaces.IClienteDAO;
import Interfaces.mappers.IClienteMapper;
import Interfaces.mappers.ICompraMapper;
import Mappers.ClienteMapper;
import Mappers.CompraMapper;
import entidades.Cliente;
import entidades.Compra;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author sonic
 */
/**
 * Clase que implementa la lógica de negocio para la gestión de clientes
 */
public class ClienteBO implements IClienteBO {

    private static ClienteBO instanceClienteBO;  // Instancia única de ClienteBO
    private final IClienteDAO clienteDAO = ClienteDAO.getInstance();  // DAO para acceder a los datos de cliente
    private final IClienteMapper mapper = new ClienteMapper(); // Mapeador de cliente
    private final ICompraMapper mapperCompra = new CompraMapper(); // Mapeador de compra

    private ClienteBO() {
    }

    /**
     * Método para obtener la instancia única de ClienteBO.
     *
     * @return la instancia de ClienteBO
     */
    public static ClienteBO getInstanceBO() {
        if (instanceClienteBO == null) { // para la primera vez que se llama
            instanceClienteBO = new ClienteBO();
        }
        return instanceClienteBO;
    }

    /**
     * Registra un nuevo cliente en el sistema.
     *
     * Este método convierte el objeto ClienteDTO en un objeto Cliente y utiliza
     * el DAO para registrar al cliente en la base de datos. Si ocurre un error
     * durante el proceso, se lanza una excepción RegistrarClienteExceptionBO.
     *
     * @param cliente el objeto ClienteDTO que representa al cliente a registrar
     * @return el objeto ClienteDTO que representa al cliente registrado
     * @throws RegistrarClienteExceptionBO si ocurre un error al registrar el
     * cliente
     */
    @Override
    public ClienteDTO registrarClienteBO(ClienteDTO cliente) throws RegistrarClienteExceptionBO {
        try {

            // Convierte el ClienteDTO a Cliente
            Cliente clienteRegistrar = mapper.toClienteEntidad(cliente);
            // Llama al DAO para registrar al cliente y obtiene el cliente registrado
            Cliente clienteRegistrado = clienteDAO.registrarCliente(clienteRegistrar);

            // Mapea el cliente registrado a ClienteDTO y lo devuelve
            return mapper.toClienteDTO(clienteRegistrado);

        } catch (RegistrarClienteException e) {
            // Lanza una excepción de negocio si ocurre un error al registrar el cliente
            throw new RegistrarClienteExceptionBO(e.getMessage(), e);
        }
    }

    /**
     * Actualiza la información de un cliente en el sistema.
     *
     * Este método convierte el objeto ClienteDTO en un objeto Cliente y utiliza
     * el metodo del DAO para actualizar la información del cliente en la base
     * de datos. Si ocurre un error durante el proceso, se lanza una excepción
     * ActualizarClienteExceptionBO.
     *
     * @param cliente el objeto ClienteDTO que representa al cliente a
     * actualizar
     * @return el objeto ClienteDTO que representa al cliente actualizado
     * @throws ActualizarClienteExceptionBO si ocurre un error al actualizar el
     * cliente
     */
    @Override
    public ClienteDTO actualizarClienteBO(ClienteDTO cliente) throws ActualizarClienteExceptionBO {
        try {

            // Convierte el ClienteDTO a Cliente
            Cliente clienteActualizar = mapper.toClienteEntidad(cliente);
            // Llama al DAO para actualizar al cliente y obtiene el cliente actualizado
            Cliente clienteActualizado = clienteDAO.actualizarCliente(clienteActualizar);

            // Mapea el cliente actualizado a ClienteDTO y lo devuelve
            return mapper.toClienteDTO(clienteActualizado);

        } catch (ActualizarClienteException e) {
            // Lanza una excepción de negocio si ocurre un error al actualizar el cliente
            throw new ActualizarClienteExceptionBO(e.getMessage(), e);
        }
    }

    /**
     * Obtiene un cliente específico a partir de su nombre de usuario y
     * contraseña.
     *
     * Este método utiliza el DAO para buscar un cliente en la base de datos y
     * lo convierte a un objeto ClienteDTO. Si ocurre un error durante el
     * proceso, se lanza una excepción EncontrarClienteExceptionBO.
     *
     * @param nombreUsuario el nombre de usuario del cliente a buscar
     * @param contrasena la contraseña del cliente a buscar
     * @return el objeto ClienteDTO que representa al cliente encontrado
     * @throws EncontrarClienteExceptionBO si ocurre un error al encontrar el
     * cliente
     */
    @Override
    public ClienteDTO obtenerClienteBO(String nombreUsuario, String contrasena) throws EncontrarClienteExceptionBO {
        try {

            // Llamar al metodo de clienteDAO para obtener el cliente correspondiente segun las credenciales 
            Cliente clienteEncontrado = clienteDAO.obtenerCliente(nombreUsuario, contrasena);

            // Mapea el cliente encontrado a ClienteDTO y lo devuelve
            return mapper.toClienteDTO(clienteEncontrado);

        } catch (EncontrarClienteException e) {
            throw new EncontrarClienteExceptionBO("Error al encontrar un usuario", e);
        }
    }

    /**
     * Carga el historial de compras de un cliente.
     *
     * Este método convierte el objeto ClienteDTO en un objeto Cliente y utiliza
     * el DAO para obtener el historial de compras del cliente en la base de
     * datos. Las compras obtenidas son mapeadas a objetos CompraDTO antes de
     * ser devueltas. Si ocurre un error durante el proceso, se lanza una
     * excepción CargarHistorialExceptionBO.
     *
     * @param cliente el objeto ClienteDTO que representa al cliente cuyo
     * historial de compras se desea cargar
     * @return una lista de objetos CompraDTO que representan las compras del
     * cliente
     * @throws CargarHistorialExceptionBO si ocurre un error al cargar el
     * historial de compras
     */
    @Override
    public List<CompraDTO> cargarHistorialComprasBO(ClienteDTO cliente) throws CargarHistorialExceptionBO {
        try {

            // Convierte el ClienteDTO a Cliente
            Cliente clienteEncontrado = mapper.toClienteEntidad(cliente);

            // Obtiene el historial de compras del cliente en especifico llamando al metodo de clienteDAO
            List<Compra> comprasDelCliente = clienteDAO.cargarHistorialCompras(clienteEncontrado);

            // Mapea la lista de compras a CompraDTO y la devuelve
            return comprasDelCliente.stream()
                    .map(mapperCompra::toCompraDTO)
                    .collect(Collectors.toList());

        } catch (CargarHistorialException e) {
            throw new CargarHistorialExceptionBO("Error al eliminar usuario", e);
        }
    }

}
