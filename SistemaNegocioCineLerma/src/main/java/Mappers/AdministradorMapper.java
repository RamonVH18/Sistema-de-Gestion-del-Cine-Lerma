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
/**
 * Mapper para convertir entre objetos de tipo Administrador
 * a AdministradorDTO y viceversa
 * 
 * Esta clase permite mapear los datos entre la capa de presentación y la capa de negocio
 * para la entidad Administrador.
 */
public class AdministradorMapper implements IAdministradorMapper{
    
    /**
     * Convierte un objeto Administrador en un objeto AdministradorDTO.
     *
     * @param admin El objeto Administrador que se va a convertir.
     * @return Un objeto AdministradorDTO que representa al administrador, o null si el administrador es null.
     */
    @Override
    public AdministradorDTO toAdministradorDTO(Administrador admin) {
        //Primero se valida si el pago recibido es null, entonces se retornara un null
        if (admin == null) {
            return null;
        }
        
        //Se crea un objeto AdministradorDTO
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

        return dto; //Se retorna el administradorDTO mapeado con los atributos de la entidad recibida como parametro


    }
    
    
      /**
     * Convierte un objeto AdministradorDTO en un objeto Administrador.
     *
     * @param admindto El objeto AdministradorDTO que se va a convertir.
     * @return Un objeto Administrador que representa al administrador, o null si el AdministradorDTO es null.
     */
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

        return admin; //Se retorna el administrador mapeado con los atributos de el dto recibido como parametro
    }
}
