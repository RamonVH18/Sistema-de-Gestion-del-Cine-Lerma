/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces.mappers;

import DTOs.AdministradorDTO;
import entidades.Administrador;

/**
 *
 * @author sonic
 */
/**
 * Interfaz que define los métodos para mapear entre la entidad Administrador y el DTO AdministradorDTO.
 */
public interface IAdministradorMapper {
    
    /**
     * Convierte un objeto Administrador en un objeto AdministradorDTO.
     *
     * @param admin El objeto Administrador que se va a convertir.
     * @return Un objeto AdministradorDTO que representa al administrador.
     */
    public AdministradorDTO toAdministradorDTO(Administrador admin);

    /**
     * Convierte un objeto AdministradorDTO en un objeto Administrador.
     *
     * @param admindto El objeto AdministradorDTO que se va a convertir.
     * @return Un objeto Administrador que representa al administrador.
     */
    public Administrador toAdministradorEntidad(AdministradorDTO admindto);
}
