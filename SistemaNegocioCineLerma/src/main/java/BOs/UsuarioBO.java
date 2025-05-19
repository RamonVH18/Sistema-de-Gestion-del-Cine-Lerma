/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BOs;

import DAOs.UsuarioDAO;
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
/**
 * Clase que implementa la lógica de negocio para la gestión de usuarios.
 */
public class UsuarioBO implements IUsuarioBO {

    private static UsuarioBO instanceUsuarioBO; // Instancia única de UsuarioBO
    private final IUsuarioDAO usuarioDAO = UsuarioDAO.getInstance(); // DAO para acceder a los datos de usuario
    private final IUsuarioMapper mapper = new UsuarioMapper(); // Mapeador para convertir Usuario a UsuarioDTO

    private UsuarioBO() {
    }

    /**
     * Método para obtener la instancia única de UsuarioBO.
     *
     * @return la instancia de UsuarioBO
     */
    public static UsuarioBO getInstanceBO() {
        if (instanceUsuarioBO == null) { // para la primera vez que se llama
            instanceUsuarioBO = new UsuarioBO();
        }
        return instanceUsuarioBO;
    }

    /**
     * Muestra la lista de usuarios en el sistema.
     *
     * Este método utiliza el DAO para obtener la lista de usuarios y los mapea
     * a objetos UsuarioDTO. Si ocurre un error durante la obtención de
     * usuarios, se lanza una excepción ObtenerUsuariosExceptionBO.
     *
     * @return una lista de objetos UsuarioDTO que representan todos los
     * usuarios en el sistema.
     * @throws ObtenerUsuariosExceptionBO si ocurre un error al obtener la lista
     * de usuarios.
     */
    @Override
    public List<UsuarioDTO> mostrarListaUsuariosBO() throws ObtenerUsuariosExceptionBO {
        try {

            List<Usuario> usuarios = usuarioDAO.mostrarListaUsuarios();

            return usuarios.stream() //llama al metodo para mapear a usuarioDTO por cada objeto Usuario obtenido en la lista de usuarios 
                    .map(mapper::toUsuarioDTO)
                    .collect(Collectors.toList());

        } catch (ObtenerUsuariosException e) {
            throw new ObtenerUsuariosExceptionBO("Error al obtener lista de usuarios", e);
        }
    }

    /**
     * Bloquea un usuario en el sistema.
     *
     * Este método convierte el objeto UsuarioDTO en un objeto Usuario y utiliza
     * el DAO para bloquear al usuario en la base de datos. Si ocurre un error
     * durante el proceso, se lanza una excepción ActualizarUsuarioExceptionBO.
     *
     * @param usuario el objeto UsuarioDTO que representa al usuario a bloquear
     * @return true si el usuario fue bloqueado exitosamente, false en caso
     * contrario
     * @throws ActualizarUsuarioExceptionBO si ocurre un error al bloquear el
     * usuario
     */
    @Override
    public Boolean bloquearUsuarioBO(UsuarioDTO usuario) throws ActualizarUsuarioExceptionBO {
        try {

            // Convierte el UsuarioDTO a Usuario
            Usuario usuarioBloquear = mapper.toUsuarioEntidad(usuario);

            // Llama al DAO para bloquear al usuario y devuelve el resultado
            return usuarioDAO.bloquearUsuario(usuarioBloquear);

        } catch (ActualizarUsuarioException e) {
            // Lanza una excepción de negocio si ocurre un error al bloquear el usuario
            throw new ActualizarUsuarioExceptionBO("Error al bloquear usuario", e);
        }
    }

    /**
     * Desbloquea un usuario en el sistema.
     *
     * Este método convierte el objeto UsuarioDTO en un objeto Usuario y utiliza
     * el DAO para desbloquear al usuario en la base de datos. Si ocurre un
     * error durante el proceso, se lanza una excepción
     * ActualizarUsuarioExceptionBO.
     *
     * @param usuario el objeto UsuarioDTO que representa al usuario a
     * desbloquear
     * @return true si el usuario fue desbloqueado exitosamente, false en caso
     * contrario
     * @throws ActualizarUsuarioExceptionBO si ocurre un error al desbloquear el
     * usuario
     */
    @Override
    public Boolean desbloquearUsuarioBO(UsuarioDTO usuario) throws ActualizarUsuarioExceptionBO {
        try {

            // Convierte el UsuarioDTO a Usuario
            Usuario usuarioDesbloquear = mapper.toUsuarioEntidad(usuario);

            // Llama al DAO para desbloquear al usuario y devuelve el resultado
            return usuarioDAO.desbloquearUsuario(usuarioDesbloquear);

        } catch (ActualizarUsuarioException e) {
            throw new ActualizarUsuarioExceptionBO("Error al desbloquear usuario", e);
        }
    }

    /**
     * Muestra una lista de usuarios filtrada según los criterios especificados.
     *
     * Este método utiliza el DAO para obtener una lista de usuarios que cumplen
     * con los filtros proporcionados: estado, rol, rango de fechas y nombre.
     * Los usuarios obtenidos son mapeados a objetos UsuarioDTO antes de ser
     * devueltos. Si ocurre un error durante el proceso, se lanza una excepción
     * ObtenerUsuariosExceptionBO.
     *
     * @param estado el estado del usuario a filtrar
     * @param rol el rol del usuario a filtrar
     * @param fechaInicio la fecha de inicio del rango a filtrar
     * @param fechaFin la fecha de fin del rango a filtrar
     * @param nombre el nombre del usuario a filtrar
     * @return una lista de objetos UsuarioDTO que representan los usuarios
     * filtrados
     * @throws ObtenerUsuariosExceptionBO si ocurre un error al obtener la lista
     * de usuarios
     */
    @Override
    public List<UsuarioDTO> mostrarListaUsuariosFiltradaBO(EstadoUsuario estado, Rol rol, LocalDateTime fechaInicio, LocalDateTime fechaFin, String nombre) throws ObtenerUsuariosExceptionBO {
        try {

            // Obtiene la lista de usuarios filtrados desde el DAO
            List<Usuario> usuarios = usuarioDAO.mostrarListaUsuariosFiltrada(estado, rol, fechaInicio, fechaFin, nombre);

            // Mapea la lista de usuarios a UsuarioDTO y la devuelve
            return usuarios.stream()
                    .map(mapper::toUsuarioDTO)
                    .collect(Collectors.toList());

        } catch (ObtenerUsuariosException e) {
            throw new ObtenerUsuariosExceptionBO("Error al obtener lista de usuarios CON FILTROS", e);
        }
    }

    /**
     * Obtiene un usuario específico a partir de su nombre de usuario y contraseña.
     *
     * Este método utiliza el DAO para buscar un usuario en la base de datos
     * y lo convierte a un objeto UsuarioDTO. Si ocurre un error durante el proceso,
     * se lanza una excepción ObtenerUsuariosExceptionBO.
     *
     * @param nombreUsuario el nombre de usuario del usuario a buscar
     * @param contrasena la contraseña del usuario a buscar
     * @return el objeto UsuarioDTO que representa al usuario encontrado
     * @throws ObtenerUsuariosExceptionBO si ocurre un error al encontrar el usuario
     */
    @Override
    public UsuarioDTO obtenerUsuarioBO(String nombreUsuario, String contrasena) throws ObtenerUsuariosExceptionBO {
        try {

            // llama al metodo del DAO para encontrar el usuario con las credenciales que coincidan
            Usuario usuarioEncontrado = usuarioDAO.obtenerUsuario(nombreUsuario, contrasena);

            // Mapea el usuario encontrado a UsuarioDTO y lo devuelve
            return mapper.toUsuarioDTO(usuarioEncontrado);

        } catch (ObtenerUsuariosException e) {
            // Lanza una excepción de negocio si ocurre un error al encontrar el usuario
            throw new ObtenerUsuariosExceptionBO("Error al encontrar un usuario", e);
        }
    }

}
