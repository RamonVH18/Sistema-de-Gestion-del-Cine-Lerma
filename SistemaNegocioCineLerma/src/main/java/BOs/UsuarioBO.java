/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BOs;

import DAOs.UsuarioDAO;
import DTOs.ReporteUsuarioDTO;
import DTOs.UsuarioDTO;
import Excepciones.Usuarios.ActualizarUsuarioExceptionBO;
import Excepciones.Usuarios.ObtenerUsuariosExceptionBO;
import Excepciones.usuarios.ActualizarUsuarioException;
import Excepciones.usuarios.ObtenerUsuariosException;
import Interfaces.IUsuarioBO;
import Interfaces.IUsuarioDAO;
import Interfaces.mappers.ICompraMapper;
import Interfaces.mappers.IUsuarioMapper;
import Mappers.CompraMapper;
import Mappers.UsuarioMapper;
import entidades.Usuario;
import enums.EstadoUsuario;
import enums.Rol;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author sonic
 */
public class UsuarioBO implements IUsuarioBO {

    private static UsuarioBO instanceUsuarioBO;
    private final IUsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
    private final IUsuarioMapper mapper = new UsuarioMapper();
    private final ICompraMapper mapperCompra = new CompraMapper();

    private UsuarioBO() {
    }

    public static UsuarioBO getInstanceBO() {
        if (instanceUsuarioBO == null) { // para la primera vez que se llama
            instanceUsuarioBO = new UsuarioBO();
        }
        return instanceUsuarioBO;
    }

    @Override
    public List<UsuarioDTO> mostrarListaUsuariosBO() throws ObtenerUsuariosExceptionBO {
        try {

            List<Usuario> usuarios = usuarioDAO.mostrarListaUsuarios();

            return usuarios.stream()
                    .map(mapper::toUsuarioDTO)
                    .collect(Collectors.toList());

        } catch (ObtenerUsuariosException e) {
            throw new ObtenerUsuariosExceptionBO("Error al obtener lista de usuarios", e);
        }
    }


    @Override
    public Boolean bloquearUsuarioBO(UsuarioDTO usuario) throws ActualizarUsuarioExceptionBO {
        try {

            Usuario usuarioBloquear = mapper.toUsuarioEntidad(usuario);

            return usuarioDAO.bloquearUsuario(usuarioBloquear);

        } catch (ActualizarUsuarioException e) {
            throw new ActualizarUsuarioExceptionBO("Error al bloquear usuario", e);
        }
    }

    @Override
    public Boolean desbloquearUsuarioBO(UsuarioDTO usuario) throws ActualizarUsuarioExceptionBO {
        try {

            Usuario usuarioDesbloquear = mapper.toUsuarioEntidad(usuario);

            return usuarioDAO.desbloquearUsuario(usuarioDesbloquear);

        } catch (ActualizarUsuarioException e) {
            throw new ActualizarUsuarioExceptionBO("Error al desbloquear usuario", e);
        }
    }

    @Override
    public List<UsuarioDTO> mostrarListaUsuariosFiltradaBO(EstadoUsuario estado, Rol rol, LocalDateTime fechaInicio, LocalDateTime fechaFin, String nombre) throws ObtenerUsuariosExceptionBO {
        try {

            List<Usuario> usuarios = usuarioDAO.mostrarListaUsuariosFiltrada(estado, rol, fechaInicio, fechaFin, nombre);

            return usuarios.stream()
                    .map(mapper::toUsuarioDTO)
                    .collect(Collectors.toList());

        } catch (ObtenerUsuariosException e) {
            throw new ObtenerUsuariosExceptionBO("Error al obtener lista de usuarios CON FILTROS", e);
        }
    }
    
    @Override
    public UsuarioDTO obtenerUsuarioBO(String nombreUsuario, String contrasena) throws ObtenerUsuariosExceptionBO {
        try {

            Usuario usuarioEncontrado = usuarioDAO.obtenerUsuario(nombreUsuario, contrasena);

            return mapper.toUsuarioDTO(usuarioEncontrado);

        } catch (ObtenerUsuariosException e) {
            throw new ObtenerUsuariosExceptionBO("Error al encontrar un usuario", e);
        }
    }
    

}
