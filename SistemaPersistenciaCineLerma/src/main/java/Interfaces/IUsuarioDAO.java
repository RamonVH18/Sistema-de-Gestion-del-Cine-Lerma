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
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author sonic
 */
public interface IUsuarioDAO {
    
    public List<Usuario> mostrarListaUsuarios() throws ObtenerUsuariosException;

    public Boolean eliminarUsuario(Usuario usuario) throws EliminarUsuarioException;

    public Boolean bloquearUsuario(Usuario usuario) throws ActualizarUsuarioException;
    
    public Boolean desbloquearUsuario(Usuario usuario) throws ActualizarUsuarioException;
    
    public List<Usuario> mostrarListaUsuariosFiltrada(EstadoUsuario estado, LocalDateTime fechaInicio, LocalDateTime fechaFin, String correo, String nombre) throws ObtenerUsuariosException;
    
    
}
