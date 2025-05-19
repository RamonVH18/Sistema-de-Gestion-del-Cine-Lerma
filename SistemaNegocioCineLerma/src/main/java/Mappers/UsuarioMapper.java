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
public class UsuarioMapper implements IUsuarioMapper {

    @Override
    public UsuarioDTO toUsuarioDTO(Usuario usuario) {
        //Primero se valida si el pago recibido es null, entonces se retornara un null
        if (usuario == null) {
            return null;
        }

        UsuarioDTO dto = new UsuarioDTO();
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

        return dto;
    }

    @Override
    public Usuario toUsuarioEntidad(UsuarioDTO usuariodto) {
        if (usuariodto == null) {
            return null;
        }

        Usuario usuario = new Usuario();
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

        return usuario;
    }
}
