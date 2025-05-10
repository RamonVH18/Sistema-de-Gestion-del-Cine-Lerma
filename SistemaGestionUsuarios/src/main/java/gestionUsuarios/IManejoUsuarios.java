/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gestionUsuarios;

import DTOs.AdministradorDTO;
import DTOs.ClienteDTO;
import DTOs.CompraDTO;
import DTOs.UsuarioDTO;
import Excepciones.ActualizarUsuarioException;
import Excepciones.CargarHistorialException;
import Excepciones.EncontrarUsuarioException;
import Excepciones.RegistrarUsuarioException;
import Excepciones.Usuarios.ActualizarAdminExceptionBO;
import Excepciones.Usuarios.ActualizarClienteExceptionBO;
import Excepciones.Usuarios.ActualizarUsuarioExceptionBO;
import Excepciones.Usuarios.CargarHistorialExceptionBO;
import Excepciones.Usuarios.EliminarUsuarioExceptionBO;
import Excepciones.Usuarios.EncontrarAdminExceptionBO;
import Excepciones.Usuarios.EncontrarClienteExceptionBO;
import Excepciones.Usuarios.ObtenerUsuariosExceptionBO;
import Excepciones.Usuarios.RegistrarAdminExceptionBO;
import Excepciones.Usuarios.RegistrarClienteExceptionBO;
import Excepciones.Usuarios.ValidarUsuarioExceptionBO;
import Excepciones.ValidarUsuarioException;
import Excepciones.usuarios.EliminarUsuarioException;
import Excepciones.usuarios.ObtenerUsuariosException;
import enums.EstadoUsuario;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author sonic
 */
public interface IManejoUsuarios {
    
    //Usuarios:
    
    public List<UsuarioDTO> mostrarListaUsuarios() throws ObtenerUsuariosException;

    public Boolean eliminarUsuario(UsuarioDTO usuario) throws EliminarUsuarioException;

    public Boolean bloquearUsuario(UsuarioDTO usuario) throws ActualizarUsuarioException;
    
    public Boolean desbloquearUsuario(UsuarioDTO usuario) throws ActualizarUsuarioException;
    
    public List<UsuarioDTO> mostrarListaUsuariosPorEstado(EstadoUsuario estado) throws ObtenerUsuariosException;
    
    public List<UsuarioDTO> mostrarListaUsuariosPorPeriodo(LocalDateTime fechaInicio, LocalDateTime fechaFin) throws ObtenerUsuariosException;
    
    public List<UsuarioDTO> mostrarListaUsuariosPorCorreo(String correo) throws ObtenerUsuariosException;
    
    public List<UsuarioDTO> mostrarListaUsuariosPorNombre(String nombre) throws ObtenerUsuariosException;
    
    
    //Cliente:
    
    public ClienteDTO registrarCliente (ClienteDTO cliente) throws RegistrarUsuarioException;
    
    public ClienteDTO actualizarCliente (ClienteDTO cliente) throws ActualizarUsuarioException;
    
    public Boolean eliminarCliente (ClienteDTO cliente) throws EliminarUsuarioException;
    
    public Boolean validarCliente(String nombreUsuario, String contrasena) throws ValidarUsuarioException;
    
    public ClienteDTO obtenerCliente(String nombreUsuario) throws EncontrarUsuarioException;
    
    public List<CompraDTO> cargarHistorialCompras(ClienteDTO cliente) throws CargarHistorialException;
    
    //Administrador:
    
    public AdministradorDTO registrarAdministrador (AdministradorDTO administrador) throws RegistrarUsuarioException;
    
    public AdministradorDTO actualizarAdministrador (AdministradorDTO administrador) throws ActualizarUsuarioException;
    
    public Boolean eliminarAdministrador (AdministradorDTO administrador) throws EliminarUsuarioException;
    
    public Boolean validarAdministrador(String nombreUsuario, String contrasena) throws ValidarUsuarioException;
    
    public AdministradorDTO obtenerAdministrador(String nombreUsuario) throws EncontrarUsuarioException;
    
    
}
