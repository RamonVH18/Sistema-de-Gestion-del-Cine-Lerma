/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;

import DTOs.AsientoDTO;
import Interfaces.mappers.IAsientoMapper;
import entidades.Asiento;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase mapeadora de asientos
 * @author Ramon Valencia
 */
public class AsientoMapper implements IAsientoMapper{
    /**
     * Mapeador de una lista de asientos a una lista de asientoDTO
     * @param asientos
     * @return 
     */
    @Override
    public List<AsientoDTO> toAsientoDTO(List<Asiento> asientos) {
        List<AsientoDTO> asientosDTO = new ArrayList<>();
        
        for (int i = 0; i < asientos.size(); i++) {
            AsientoDTO asientoDTO = new AsientoDTO(
                    asientos.get(i).getNumero()
            );
            asientosDTO.add(asientoDTO);
        }
        
        return asientosDTO;
    }
    /**
     * Mapeador de una lista de asientosDTO a una lista de asientos
     * @param asientosDTO
     * @return 
     */
    @Override
    public List<Asiento> toAsientoEntidad(List<AsientoDTO> asientosDTO) {
        List<Asiento> asientos = new ArrayList<>();
        
        for (int i = 0; i < asientos.size(); i++) {
            Asiento asiento = new Asiento(
                    asientosDTO.get(i).getNumAsiento()
            );
            asientos.add(asiento);
        }
        
        return asientos;
    }
    
}
