/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import DTOs.CompraDTO;
import DTOs.UsuarioDTO;
import Excepciones.usuarios.EditarUsuarioException;
import Excepciones.usuarios.EliminarUsuarioException;
import Excepciones.usuarios.EncontrarUsuarioException;
import Excepciones.usuarios.ObtenerUsuariosException;
import Excepciones.usuarios.RegistrarUsuarioException;
import entidades.Compra;
import entidades.Usuario;
import enums.EstadoUsuario;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author sonic
 */
public interface IUsuarioBO {
    
    public List<UsuarioDTO> mostrarListaUsuariosBO() throws ObtenerUsuariosException;
    
    public UsuarioDTO registrarUsuarioBO(UsuarioDTO usuario) throws RegistrarUsuarioException;

    public UsuarioDTO actualizarUsuarioBO(UsuarioDTO usuario) throws EditarUsuarioException;

    public Boolean eliminarUsuarioBO(UsuarioDTO usuario) throws EliminarUsuarioException;

    public Boolean bloquearUsuarioBO(UsuarioDTO usuario) throws EditarUsuarioException;
    
    public Boolean desbloquearUsuarioBO(UsuarioDTO usuario) throws EditarUsuarioException;

    public List<CompraDTO> cargarHistorialComprasBO(String nombreDeUsuario) throws EncontrarUsuarioException;
    
    public Boolean validarUsuarioBO(String nombreUsuario, String contrasena) throws EncontrarUsuarioException;
    
    public List<UsuarioDTO> mostrarListaUsuariosFiltradaBO(EstadoUsuario estado, LocalDateTime fechaInicio, LocalDateTime fechaFin, String correo, String nombre) throws ObtenerUsuariosException;
    
    public UsuarioDTO obtenerUsuarioBO(String nombreUsuario) throws EncontrarUsuarioException; //Este metodo probablemente se use para verificar si existe un usuario concreto en la base y otras operaciones
}
