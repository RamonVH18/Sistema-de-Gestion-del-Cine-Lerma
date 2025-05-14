/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces.mappers;

import DTOs.AsientoDTO;
import entidades.Asiento;
import java.util.List;

/**
 *
 * @author Ramon Valencia
 */
public interface IAsientoMapper {
    
    public List<AsientoDTO> toAsientoDTO(List<Asiento> asientos);
    
    public List<Asiento> toAsientoEntidad(List<AsientoDTO> asientosDTO);
}
