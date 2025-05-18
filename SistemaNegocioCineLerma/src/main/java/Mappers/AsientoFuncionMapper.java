/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;

import DTOs.AsientoFuncionDTO;
import Interfaces.mappers.IAsientoFuncionMapper;
import entidades.AsientoFuncion;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Daniel M
 */
public class AsientoFuncionMapper implements IAsientoFuncionMapper {

    @Override
    public List<AsientoFuncionDTO> toAsientoFuncionDTO(List<AsientoFuncion> asientos) {
        List<AsientoFuncionDTO> asientosMapeados = new ArrayList<>();
        AsientoFuncion asientoFuncion;
        for (int i = 0; i < asientos.size(); i++) {

            asientoFuncion = asientos.get(i);
            if (asientoFuncion == null) {
                return null;
            }

            AsientoFuncionDTO asientoFuncionDTO = new AsientoFuncionDTO();

            asientoFuncionDTO.setIdFuncion(asientoFuncion.getIdFuncion());
            asientoFuncionDTO.setAsiento(asientoFuncion.getNumAsiento());
            asientoFuncionDTO.setNumSala(asientoFuncion.getNumSala());
            asientoFuncionDTO.setDisponibilidad(asientoFuncion.getDisponibilidad());

            asientosMapeados.add(asientoFuncionDTO);
        }
        return asientosMapeados;
    }

    @Override
    public List<AsientoFuncion> toAsientoFuncionEntidad(List<AsientoFuncionDTO> asientosDTO) {
        List<AsientoFuncion> asientosMapeados = new ArrayList<>();
        AsientoFuncionDTO asientoDTO;
        for (int i = 0; i < asientosDTO.size(); i++) {
            asientoDTO = asientosDTO.get(i);
            if (asientoDTO == null) {
                return null;
            }

            AsientoFuncion asientoFuncion = new AsientoFuncion();
            
            asientoFuncion.setIdFuncion(asientoDTO.getIdFuncion());
            asientoFuncion.setNumAsiento(asientoDTO.getAsiento());
            asientoFuncion.setNumSala(asientoDTO.getNumSala());
            asientoFuncion.setDisponibilidad(asientoDTO.isDisponibilidad());
            
            asientosMapeados.add(asientoFuncion);
        }
        return asientosMapeados;
    }

}
