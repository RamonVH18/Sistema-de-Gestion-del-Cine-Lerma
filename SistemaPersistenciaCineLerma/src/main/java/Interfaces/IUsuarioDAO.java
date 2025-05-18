/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Excepciones.usuarios.ActualizarUsuarioException;
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
/**
 * Interfaz IUsuarioDAO que define las operaciones para gestionar usuarios en el sistema.
 * Esta interfaz proporciona métodos para mostrar, bloquear, desbloquear y filtrar usuarios.
 */
public interface IUsuarioDAO {
    
    /**
     * Muestra la lista de todos los usuarios en el sistema, incluyendo a los administradores y clientes
     *
     * @return una lista de objetos Usuario que representan todos los usuarios.
     * @throws ObtenerUsuariosException si ocurre un error al obtener la lista de usuarios.
     */
    public List<Usuario> mostrarListaUsuarios() throws ObtenerUsuariosException;

    /**
     * Bloquea un usuario en el sistema, si el usuario tiene como rol ADMINISTRADOR entonces no se podra bloquear, ademas verifica que el usuario no este bloqueado actualmente.
     *
     * @param usuario el objeto Usuario que se desea bloquear.
     * @return true si el usuario fue bloqueado exitosamente, false en caso contrario.
     * @throws ActualizarUsuarioException si ocurre un error al intentar bloquear el usuario.
     */
    public Boolean bloquearUsuario(Usuario usuario) throws ActualizarUsuarioException;
    
    /**
     * Desbloquea un usuario en el sistema, primero verifica que el usuario que recibe realmente este en estado bloqueado, si no no podra ejecutarse la operacion de desbloqueo.
     *
     * @param usuario el objeto Usuario que se desea desbloquear.
     * @return true si el usuario fue desbloqueado exitosamente, false en caso contrario.
     * @throws ActualizarUsuarioException si ocurre un error al intentar desbloquear el usuario.
     */
    public Boolean desbloquearUsuario(Usuario usuario) throws ActualizarUsuarioException;
    
    
    /**
     * Muestra una lista de usuarios filtrados segun los criterios especificados, puede ser por estado, rol, periodo y nombre de usuario
     *
     * @param estado el estado del usuario (ACTIVO, BLOQUEADO) para filtrar.
     * @param rol el rol del usuario (CLIENTE, ADMINISTRADOR) para filtrar.
     * @param fechaInicio la fecha de inicio para filtrar usuarios por fecha de nacimiento del usuario.
     * @param fechaFin la fecha de fin para filtrar usuarios por fecha de nacimiento del usuario.
     * @param nombre el nombre del usuario para filtrar.
     * @return una lista de objetos Usuario que cumplen con los criterios de filtrado.
     * @throws ObtenerUsuariosException si ocurre un error al obtener la lista de usuarios filtrados.
     */
    public List<Usuario> mostrarListaUsuariosFiltrada(EstadoUsuario estado, Rol rol, LocalDateTime fechaInicio, LocalDateTime fechaFin, String nombre) throws ObtenerUsuariosException;
    
    /**
     * Obtiene un usuario específico basado en su nombre de usuario y contraseña.
     *
     * @param nombreUsuario el nombre de usuario del usuario que se desea obtener.
     * @param contrasena la contraseña del usuario que se desea obtener.
     * @return el objeto Usuario correspondiente al nombre de usuario y contraseña proporcionados.
     * @throws ObtenerUsuariosException si ocurre un error al intentar obtener el usuario.
     */
    public Usuario obtenerUsuario(String nombreUsuario, String contrasena) throws ObtenerUsuariosException;
    
}
