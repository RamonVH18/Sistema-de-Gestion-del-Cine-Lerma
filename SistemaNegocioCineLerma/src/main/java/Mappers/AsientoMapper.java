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
 *
 * @author Ramon Valencia
 */
public class AsientoMapper implements IAsientoMapper{

    @Override
    public List<AsientoDTO> toAsientoDTO(List<Asiento> asientos) {
        List<AsientoDTO> asientosDTO = new ArrayList<>();
        
        for (int i = 0; i < asientos.size(); i++) {
            Asiento asiento = new Asiento(
                    asientos.get(i).getNumero()
            );
        }
        
        return asientosDTO;
    }

    @Override
    public List<Asiento> toAsientoEntidad(List<AsientoDTO> asientosDTO) {
        List<Asiento> asientos = new ArrayList<>();
        
        for (int i = 0; i < asientos.size(); i++) {
            AsientoDTO asiento = new AsientoDTO(
                    asientosDTO.get(i).getNumAsiento()
            );
        }
        
        return asientos;
    }
    
}
