/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;

import DTOs.AdministradorDTO;
import Interfaces.mappers.IAdministradorMapper;
import entidades.Administrador;

/**
 *
 * @author sonic
 */
public class AdministradorMapper implements IAdministradorMapper{
    @Override
    public AdministradorDTO toAdministradorDTO(Administrador admin) {
        //Primero se valida si el pago recibido es null, entonces se retornara un null
        if (admin == null) {
            return null;
        }
        
        AdministradorDTO dto = new AdministradorDTO();

        // Mapeo de campos heredados de Usuario
        dto.setNombreUsuario(admin.getNombreDeUsuario());
        dto.setContraseña(admin.getContrasenia());
        dto.setNombre(admin.getNombre());
        dto.setApellidoPaterno(admin.getApellidoPaterno());
        dto.setApellidoMaterno(admin.getApellidoMaterno());
        dto.setCorreoElectronico(admin.getCorreoElectronico());
        dto.setFechaNacimiento(admin.getFechaNacimiento());
        dto.setTelefono(admin.getTelefono());
        dto.setRol(admin.getRol());
        dto.setEstado(admin.getEstado());

        // Mapeo del campo específico de Administrador
        dto.setRFC(admin.getRFC());

        return dto;


    }
    
    @Override
    public Administrador toAdministradorEntidad(AdministradorDTO admindto) {
        if (admindto == null) {
            return null;
        }
        
        Administrador admin = new Administrador();

        // Campos heredados de Usuario
        admin.setNombreDeUsuario(admindto.getNombreUsuario());
        admin.setContrasenia(admindto.getContraseña());
        admin.setNombre(admindto.getNombre());
        admin.setApellidoPaterno(admindto.getApellidoPaterno());
        admin.setApellidoMaterno(admindto.getApellidoMaterno());
        admin.setCorreoElectronico(admindto.getCorreoElectronico());
        admin.setFechaNacimiento(admindto.getFechaNacimiento());
        admin.setTelefono(admindto.getTelefono());
        admin.setRol(admindto.getRol());
        admin.setEstado(admindto.getEstado());

        // Campo específico de Administrador
        admin.setRFC(admindto.getRFC());

        return admin;
    }
}
