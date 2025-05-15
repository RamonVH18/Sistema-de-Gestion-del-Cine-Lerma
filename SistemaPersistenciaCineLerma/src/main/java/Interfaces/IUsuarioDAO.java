/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Excepciones.usuarios.ActualizarUsuarioException;
import Excepciones.usuarios.EliminarUsuarioException;
import Excepciones.usuarios.ObtenerUsuariosException;
import entidades.Usuario;
import enums.EstadoUsuario;
import enums.Rol;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author sonic
 */
public interface IUsuarioDAO {
    
    public List<Usuario> mostrarListaUsuarios() throws ObtenerUsuariosException;

    public Boolean bloquearUsuario(Usuario usuario) throws ActualizarUsuarioException;
    
    public Boolean desbloquearUsuario(Usuario usuario) throws ActualizarUsuarioException;
    
    public List<Usuario> mostrarListaUsuariosFiltrada(EstadoUsuario estado, Rol rol, LocalDateTime fechaInicio, LocalDateTime fechaFin, String nombre) throws ObtenerUsuariosException;
    
    public Usuario obtenerUsuario(String nombreUsuario, String contrasena) throws ObtenerUsuariosException;
    
}
