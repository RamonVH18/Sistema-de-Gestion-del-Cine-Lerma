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
public interface IClienteBO {
    
    public ClienteDTO registrarClienteBO (ClienteDTO cliente) throws RegistrarClienteExceptionBO;
    
    public ClienteDTO actualizarClienteBO (ClienteDTO cliente) throws ActualizarClienteExceptionBO;
    
    public Boolean eliminarClienteBO (ClienteDTO cliente) throws EliminarUsuarioExceptionBO;
    
    public Boolean validarClienteBO(String nombreUsuario, String contrasena) throws ValidarUsuarioExceptionBO;
    
    public ClienteDTO obtenerClienteBO(String nombreUsuario) throws EncontrarClienteExceptionBO;
    
    public List<CompraDTO> cargarHistorialComprasBO(ClienteDTO cliente) throws CargarHistorialExceptionBO;
}
