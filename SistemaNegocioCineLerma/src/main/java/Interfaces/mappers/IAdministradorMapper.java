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
public interface IAdministradorMapper {
    
    public AdministradorDTO toAdministradorDTO(Administrador admin);

    public Administrador toAdministradorEntidad(AdministradorDTO admindto);
}
