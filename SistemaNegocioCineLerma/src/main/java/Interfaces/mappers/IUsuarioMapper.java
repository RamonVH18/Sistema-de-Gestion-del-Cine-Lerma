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
public interface IUsuarioMapper {
    public UsuarioDTO toUsuarioDTO(Usuario usuario);
        
    public Usuario toUsuarioEntidad(UsuarioDTO usuariodto);
    
}
