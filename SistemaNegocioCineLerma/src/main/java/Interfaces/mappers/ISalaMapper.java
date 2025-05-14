/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces.mappers;

import DTOs.SalaNuevaDTO;
import DTOs.SalaViejaDTO;
import entidades.Sala;

/**
 *
 * @author Ramon Valencia
 */
public interface ISalaMapper {
    
    public SalaViejaDTO toSalaViejaDTO(Sala sala);
    
    public Sala toSalaViejaEntidad(SalaViejaDTO dto);
    
    public SalaNuevaDTO toSalaNuevaDTO(Sala sala);
    
    public Sala toSalaNuevaEntidad(SalaNuevaDTO dto);
}
