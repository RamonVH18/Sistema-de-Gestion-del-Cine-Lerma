/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces.mappers;

import DTOs.UsuarioDTO;
import entidades.Usuario;

/**
 *
 * @author sonic
 */
/**
 * Interfaz que define los métodos para el mapeo de usuario y usuarioDTO
 */
public interface IUsuarioMapper {
    
    /**
     * Convierte un objeto Usuario en un objeto UsuarioDTO.
     *
     * @param usuario El objeto Usuario que se va a convertir.
     * @return Un objeto UsuarioDTO que representa al usuario.
     */
    public UsuarioDTO toUsuarioDTO(Usuario usuario);
        
    
    /**
     * Convierte un objeto UsuarioDTO en un objeto Usuario.
     *
     * @param usuariodto El objeto UsuarioDTO que se va a convertir.
     * @return Un objeto Usuario que representa al usuario.
     */
    public Usuario toUsuarioEntidad(UsuarioDTO usuariodto);
    
}
