/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import DTOs.UsuarioDTO;
import Excepciones.Usuarios.ActualizarUsuarioExceptionBO;
import Excepciones.Usuarios.ObtenerUsuariosExceptionBO;
import Excepciones.usuarios.ObtenerUsuariosException;
import enums.EstadoUsuario;
import enums.Rol;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author sonic
 */
/**
 * Interfaz que define las operaciones de negocio relacionadas con los usuarios.
 * Esta interfaz proporciona métodos para gestionar usuarios, incluyendo la
 * obtención, bloqueo, desbloqueo y filtrado de usuarios.
 */
public interface IUsuarioBO {

    /**
     * Obtiene una lista de todos los usuarios, retorna una lista de usuarioDTO que se mapea.
     *
     * @return Una lista de objetos UsuarioDTO que representan a todos los
     * usuarios.
     * @throws ObtenerUsuariosExceptionBO Si ocurre un error al intentar obtener
     * la lista de usuarios.
     */
    public List<UsuarioDTO> mostrarListaUsuariosBO() throws ObtenerUsuariosExceptionBO;

     /**
     * Bloquea un usuario específico.
     *
     * @param usuario El objeto UsuarioDTO que representa al usuario a bloquear.
     * @return true si el usuario fue bloqueado exitosamente, false en caso contrario.
     * @throws ActualizarUsuarioExceptionBO Si ocurre un error al intentar bloquear el usuario.
     */
    public Boolean bloquearUsuarioBO(UsuarioDTO usuario) throws ActualizarUsuarioExceptionBO;

    /**
     * Desbloquea un usuario específico.
     *
     * @param usuario El objeto UsuarioDTO que representa al usuario a desbloquear.
     * @return true si el usuario fue desbloqueado exitosamente, false en caso contrario.
     * @throws ActualizarUsuarioExceptionBO Si ocurre un error al intentar desbloquear el usuario.
     */
    public Boolean desbloquearUsuarioBO(UsuarioDTO usuario) throws ActualizarUsuarioExceptionBO;

    /**
     * Obtiene una lista de usuarios filtrada según los criterios especificados.
     *
     * @param estado El estado del usuario (activo, inactivo, etc.) por el cual se desea filtrar.
     * @param rol El rol del usuario (administrador, cliente, etc.) por el cual se desea filtrar.
     * @param fechaInicio La fecha de inicio para filtrar usuarios por su fecha de creación o modificación.
     * @param fechaFin La fecha de fin para filtrar usuarios por su fecha de creación o modificación.
     * @param nombre El nombre del usuario que se desea buscar.
     * @return Una lista de objetos UsuarioDTO que cumplen con los criterios de filtrado.
     * @throws ObtenerUsuariosExceptionBO Si ocurre un error al intentar obtener la lista de usuarios filtrada.
     */
    public List<UsuarioDTO> mostrarListaUsuariosFiltradaBO(EstadoUsuario estado, Rol rol, LocalDateTime fechaInicio, LocalDateTime fechaFin, String nombre) throws ObtenerUsuariosExceptionBO;

    /**
     * Obtiene un usuario específico basado en su nombre de usuario y contraseña.
     *
     * @param nombreUsuario El nombre de usuario del usuario que se desea obtener.
     * @param contrasena La contraseña del usuario que se desea obtener.
     * @return Un objeto UsuarioDTO que representa al usuario, o null si no se encuentra.
     * @throws ObtenerUsuariosExceptionBO Si ocurre un error al intentar obtener el usuario.
     */
    public UsuarioDTO obtenerUsuarioBO(String nombreUsuario, String contrasena) throws ObtenerUsuariosExceptionBO;
}
