/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BOs;

import DAOs.UsuarioDAO;
import DTOs.CompraDTO;
import DTOs.UsuarioDTO;
import Excepciones.Usuarios.ActualizarUsuarioExceptionBO;
import Excepciones.Usuarios.ObtenerUsuariosExceptionBO;
import Excepciones.usuarios.ActualizarUsuarioException;
import Excepciones.usuarios.EliminarUsuarioException;
import Excepciones.usuarios.ObtenerUsuariosException;
import Interfaces.IUsuarioBO;
import Interfaces.IUsuarioDAO;
import Interfaces.mappers.ICompraMapper;
import Interfaces.mappers.IUsuarioMapper;
import Mappers.CompraMapper;
import Mappers.UsuarioMapper;
import entidades.Compra;
import entidades.Usuario;
import enums.EstadoUsuario;
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
    public List<UsuarioDTO> mostrarListaUsuariosBO() throws ObtenerUsuariosException {
        try {

            List<Usuario> usuarios = usuarioDAO.mostrarListaUsuarios();

            return usuarios.stream()
                    .map(mapper::toUsuarioDTO)
                    .collect(Collectors.toList());

        } catch (ObtenerUsuariosException e) {
            throw new ObtenerUsuariosException("Error al obtener lista de usuarios", e);
        }
    }


//    @Override
//    public Boolean eliminarUsuarioBO(UsuarioDTO usuario) throws EliminarUsuarioException {
//        try {
//
//            Usuario usuarioEliminar = mapper.toUsuarioEntidad(usuario);
//
//            return usuarioDAO.eliminarUsuario(usuarioEliminar);
//
//        } catch (EliminarUsuarioException e) {
//            throw new EliminarUsuarioException("Error al eliminar usuario", e);
//        }
//
//    }

    @Override
    public Boolean bloquearUsuarioBO(UsuarioDTO usuario) throws ActualizarUsuarioExceptionBO {
        try {

            Usuario usuarioBloquear = mapper.toUsuarioEntidad(usuario);

            return usuarioDAO.bloquearUsuario(usuarioBloquear);

        } catch (ActualizarUsuarioException e) {
            throw new ActualizarUsuarioExceptionBO("Error al eliminar usuario", e);
        }
    }

    @Override
    public Boolean desbloquearUsuarioBO(UsuarioDTO usuario) throws ActualizarUsuarioExceptionBO {
        try {

            Usuario usuarioDesbloquear = mapper.toUsuarioEntidad(usuario);

            return usuarioDAO.desbloquearUsuario(usuarioDesbloquear);

        } catch (ActualizarUsuarioException e) {
            throw new ActualizarUsuarioExceptionBO("Error al eliminar usuario", e);
        }
    }

    @Override
    public List<UsuarioDTO> mostrarListaUsuariosFiltradaBO(EstadoUsuario estado, LocalDateTime fechaInicio, LocalDateTime fechaFin, String correo, String nombre) throws ObtenerUsuariosExceptionBO {
        try {

            List<Usuario> usuarios = usuarioDAO.mostrarListaUsuariosFiltrada(estado, fechaInicio, fechaFin, correo, nombre);

            return usuarios.stream()
                    .map(mapper::toUsuarioDTO)
                    .collect(Collectors.toList());

        } catch (ObtenerUsuariosException e) {
            throw new ObtenerUsuariosExceptionBO("Error al obtener lista de usuarios CON FILTROS", e);
        }
    }

}
