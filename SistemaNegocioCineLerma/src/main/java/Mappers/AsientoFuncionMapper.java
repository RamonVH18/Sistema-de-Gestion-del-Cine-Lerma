/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;

import DTOs.AsientoFuncionDTO;
import Interfaces.mappers.IAsientoFuncionMapper;
import entidades.AsientoFuncion;

/**
 *
 * @author Daniel M
 */
public class AsientoFuncionMapper implements IAsientoFuncionMapper {

    @Override
    public AsientoFuncionDTO toAsientoFuncionDTO(AsientoFuncion asientoFuncion) {
        if (asientoFuncion == null) {
            return null;
        }
        
        AsientoFuncionDTO asientoFuncionDTO = new AsientoFuncionDTO();
        
        asientoFuncionDTO.setIdFuncion(asientoFuncion.getFuncion().getIdString());
        asientoFuncionDTO.setAsiento(asientoFuncion.getNumAsiento());
        asientoFuncionDTO.setDisponibilidad(asientoFuncion.getDisponibilidad());
        asientoFuncionDTO.setNombreCliente(asientoFuncion.getCliente().getNombre());
        
        return asientoFuncionDTO;
    }

    @Override
    public AsientoFuncion toAsientoFuncionEntidad(AsientoFuncionDTO asientoFuncionDTO) {
        if (asientoFuncionDTO == null) {
            return null;
        }
        
        AsientoFuncion asientoFuncion = new AsientoFuncion();
        
        asientoFuncion.setNumAsiento(asientoFuncionDTO.getAsiento());
        asientoFuncion.setDisponibilidad(asientoFuncionDTO.isDisponibilidad());
        
        
        return asientoFuncion;
    }
    
}
