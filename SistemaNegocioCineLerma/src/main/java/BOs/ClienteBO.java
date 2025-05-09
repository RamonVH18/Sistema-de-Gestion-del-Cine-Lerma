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
import Excepciones.Usuarios.EliminarUsuarioExceptionBO;
import Excepciones.Usuarios.EncontrarClienteExceptionBO;
import Excepciones.Usuarios.RegistrarClienteExceptionBO;
import Excepciones.Usuarios.ValidarUsuarioExceptionBO;
import Excepciones.usuarios.ActualizarClienteException;
import Excepciones.usuarios.CargarHistorialException;
import Excepciones.usuarios.EliminarUsuarioException;
import Excepciones.usuarios.EncontrarClienteException;
import Excepciones.usuarios.RegistrarClienteException;
import Excepciones.usuarios.ValidarUsuarioException;
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
public class ClienteBO implements IClienteBO{
    
    private static ClienteBO instanceClienteBO;
    private final IClienteDAO clienteDAO = ClienteDAO.getInstance();
    private final IClienteMapper mapper = new ClienteMapper();
    private final ICompraMapper mapperCompra = new CompraMapper();

    private ClienteBO() {
    }

    public static ClienteBO getInstanceBO() {
        if (instanceClienteBO == null) { // para la primera vez que se llama
            instanceClienteBO = new ClienteBO();
        }
        return instanceClienteBO;
    }
    
    

    @Override
    public ClienteDTO registrarClienteBO(ClienteDTO cliente) throws RegistrarClienteExceptionBO {
        try {

            Cliente clienteRegistrar = mapper.toClienteEntidad(cliente);
            Cliente clienteRegistrado = clienteDAO.registrarCliente(clienteRegistrar);

            return mapper.toClienteDTO(clienteRegistrado);

        } catch (RegistrarClienteException e) {
            throw new RegistrarClienteExceptionBO("Error al registrar un cliente", e);
        }
    }

    @Override
    public ClienteDTO actualizarClienteBO(ClienteDTO cliente) throws ActualizarClienteExceptionBO {
        try {

            Cliente clienteActualizar = mapper.toClienteEntidad(cliente);
            Cliente clienteActualizado = clienteDAO.actualizarCliente(clienteActualizar);

            return mapper.toClienteDTO(clienteActualizado);

        } catch (ActualizarClienteException e) {
            throw new ActualizarClienteExceptionBO("Error al registrar el cliente", e);
        }
    }

    @Override
    public Boolean eliminarClienteBO(ClienteDTO cliente) throws EliminarUsuarioExceptionBO {
        try {

            Cliente clienteEliminar = mapper.toClienteEntidad(cliente);

            return clienteDAO.eliminarCliente(clienteEliminar);

        } catch (EliminarUsuarioException e) {
            throw new EliminarUsuarioExceptionBO("Error al eliminar el cliente", e);
        }

    }

    @Override
    public Boolean validarClienteBO(String nombreUsuario, String contrasena) throws ValidarUsuarioExceptionBO {
        try {

            return clienteDAO.validarCliente(nombreUsuario, contrasena);

        } catch (ValidarUsuarioException e) {
            throw new ValidarUsuarioExceptionBO("Error al validar el cliente", e);
        }
    }

    @Override
    public ClienteDTO obtenerClienteBO(String nombreUsuario) throws EncontrarClienteExceptionBO {
        try {

            Cliente clienteEncontrado = clienteDAO.obtenerCliente(nombreUsuario);

            return mapper.toClienteDTO(clienteEncontrado);

        } catch (EncontrarClienteException e) {
            throw new EncontrarClienteExceptionBO("Error al encontrar un usuario", e);
        }
    }

    @Override
    public List<CompraDTO> cargarHistorialComprasBO(ClienteDTO cliente) throws CargarHistorialExceptionBO {
        try {
            
            Cliente clienteEncontrado = mapper.toClienteEntidad(cliente);

            List<Compra> comprasDelCliente = clienteDAO.cargarHistorialCompras(clienteEncontrado);

            return comprasDelCliente.stream()
                    .map(mapperCompra::toCompraDTO)
                    .collect(Collectors.toList());

        } catch (CargarHistorialException e) {
            throw new CargarHistorialExceptionBO("Error al eliminar usuario", e);
        }
    }
    
}
