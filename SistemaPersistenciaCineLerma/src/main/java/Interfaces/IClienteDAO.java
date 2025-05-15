/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Excepciones.usuarios.ActualizarClienteException;
import Excepciones.usuarios.CargarHistorialException;
import Excepciones.usuarios.EliminarUsuarioException;
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
public interface IClienteDAO {
    
    public Cliente registrarCliente (Cliente cliente) throws RegistrarClienteException;
    
    public Cliente actualizarCliente (Cliente cliente) throws ActualizarClienteException;
    
    public Cliente obtenerCliente(String nombreUsuario, String contrasena) throws EncontrarClienteException;
    
    public List<Compra> cargarHistorialCompras(Cliente cliente) throws CargarHistorialException;
    
    
}
