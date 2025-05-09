/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BOs;

import DTOs.ClienteDTO;
import DTOs.CompraDTO;
import Excepciones.Usuarios.ActualizarClienteExceptionBO;
import Excepciones.Usuarios.CargarHistorialExceptionBO;
import Excepciones.Usuarios.EliminarUsuarioExceptionBO;
import Excepciones.Usuarios.EncontrarClienteExceptionBO;
import Excepciones.Usuarios.RegistrarClienteExceptionBO;
import Excepciones.Usuarios.ValidarUsuarioExceptionBO;
import Interfaces.IClienteBO;
import java.util.List;

/**
 *
 * @author sonic
 */
public class ClienteBO implements IClienteBO{

    @Override
    public ClienteDTO registrarCliente(ClienteDTO cliente) throws RegistrarClienteExceptionBO {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ClienteDTO actualizarCliente(ClienteDTO cliente) throws ActualizarClienteExceptionBO {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Boolean eliminarCliente(ClienteDTO cliente) throws EliminarUsuarioExceptionBO {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Boolean validarCliente(String nombreUsuario, String contrasena) throws ValidarUsuarioExceptionBO {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ClienteDTO obtenerCliente(String nombreUsuario) throws EncontrarClienteExceptionBO {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<CompraDTO> cargarHistorialCompras(ClienteDTO cliente) throws CargarHistorialExceptionBO {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
