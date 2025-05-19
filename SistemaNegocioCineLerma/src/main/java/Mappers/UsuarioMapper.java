/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;

import DTOs.UsuarioDTO;
import Interfaces.mappers.IUsuarioMapper;
import entidades.Usuario;

/**
 *
 * @author sonic
 */
/**
 * Clase mapeadora que implementa la interfaz IUsuarioMapper. Esta clase se
 * encarga de mapear entre la entidad Usuario y el DTO UsuarioDTO y de dto a
 * entidad.
 */
public class UsuarioMapper implements IUsuarioMapper {

    /**
     * Convierte un objeto Usuario en un objeto UsuarioDTO.
     *
     * @param usuario El objeto Usuario que se va a convertir.
     * @return Un objeto UsuarioDTO que representa al usuario, o null si el
     * usuario es null.
     */
    @Override
    public UsuarioDTO toUsuarioDTO(Usuario usuario) {
        //Primero se valida si el usuario recibido es null, entonces se retornara un null
        if (usuario == null) {
            return null;
        }

        //Si el usuario entidad no es null se crea un usuarioDTO
        UsuarioDTO dto = new UsuarioDTO();
        //Al objeto UsuarioDTO se le iran seteando los atributos de la entidad usuario recibida por el metodo
        dto.setNombreUsuario(usuario.getNombreDeUsuario());
        dto.setContraseña(usuario.getContrasenia());
        dto.setNombre(usuario.getNombre());
        dto.setApellidoPaterno(usuario.getApellidoPaterno());
        dto.setApellidoMaterno(usuario.getApellidoMaterno());
        dto.setCorreoElectronico(usuario.getCorreoElectronico());
        dto.setFechaNacimiento(usuario.getFechaNacimiento());
        dto.setTelefono(usuario.getTelefono());
        dto.setRol(usuario.getRol());
        dto.setEstado(usuario.getEstado());

        //se retorna el usuariodto mapeado
        return dto;
    }

    /**
     * Convierte un objeto UsuarioDTO en un objeto Usuario.
     *
     * @param usuariodto El objeto UsuarioDTO que se va a convertir.
     * @return Un objeto Usuario que representa al usuario, o null si el
     * UsuarioDTO es null.
     */
    @Override
    public Usuario toUsuarioEntidad(UsuarioDTO usuariodto) {
        //Primero se valida si el usuarioDTO recibido es null, entonces se retornara un null
        if (usuariodto == null) {
            return null;
        }
        
        
        //Si el usuario entidad no es null se crea un usuario
        Usuario usuario = new Usuario();
        //Al objeto Usuario se le iran seteando los atributos de usuarioDTO recibido por el metodo
        usuario.setNombreDeUsuario(usuariodto.getNombreUsuario());
        usuario.setContrasenia(usuariodto.getContraseña());
        usuario.setNombre(usuariodto.getNombre());
        usuario.setApellidoPaterno(usuariodto.getApellidoPaterno());
        usuario.setApellidoMaterno(usuariodto.getApellidoMaterno());
        usuario.setCorreoElectronico(usuariodto.getCorreoElectronico());
        usuario.setFechaNacimiento(usuariodto.getFechaNacimiento());
        usuario.setTelefono(usuariodto.getTelefono());
        usuario.setRol(usuariodto.getRol());
        usuario.setEstado(usuariodto.getEstado());

        //se retorna el usuario mapeado
        return usuario; 
    }
}
