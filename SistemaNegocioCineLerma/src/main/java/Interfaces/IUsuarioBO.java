/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import DTOs.UsuarioDTO;
import Excepciones.Usuarios.ActualizarUsuarioExceptionBO;
import Excepciones.Usuarios.ObtenerUsuariosExceptionBO;
import Excepciones.usuarios.EliminarUsuarioException;
import Excepciones.usuarios.ObtenerUsuariosException;
import enums.EstadoUsuario;
import enums.Rol;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author sonic
 */
public interface IUsuarioBO {
    
    public List<UsuarioDTO> mostrarListaUsuariosBO() throws ObtenerUsuariosExceptionBO;

    public Boolean bloquearUsuarioBO(UsuarioDTO usuario) throws ActualizarUsuarioExceptionBO;
    
    public Boolean desbloquearUsuarioBO(UsuarioDTO usuario) throws ActualizarUsuarioExceptionBO;
    
    public List<UsuarioDTO> mostrarListaUsuariosFiltradaBO(EstadoUsuario estado, Rol rol, LocalDateTime fechaInicio, LocalDateTime fechaFin, String nombre) throws ObtenerUsuariosExceptionBO;
    
}
