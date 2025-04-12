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
        
        asientoFuncionDTO.setFuncion(asientoFuncion.getFuncion());
        asientoFuncionDTO.setAsiento(asientoFuncion.getAsiento());
        asientoFuncionDTO.setDisponibilidad(asientoFuncion.getDisponibilidad());
        asientoFuncionDTO.setCliente(asientoFuncion.getCliente());
        
        return asientoFuncionDTO;
    }

    @Override
    public AsientoFuncion toAsientoFuncionEntidad(AsientoFuncionDTO asientoFuncionDTO) {
        if (asientoFuncionDTO == null) {
            return null;
        }
        
        AsientoFuncion asientoFuncion = new AsientoFuncion();
        
        asientoFuncion.setFuncion(asientoFuncionDTO.getFuncion());
        asientoFuncion.setAsiento(asientoFuncionDTO.getAsiento());
        asientoFuncion.setDisponibilidad(asientoFuncionDTO.isDisponibilidad());
        asientoFuncion.setCliente(asientoFuncionDTO.getCliente());
        
        return asientoFuncion;
    }

    
}
