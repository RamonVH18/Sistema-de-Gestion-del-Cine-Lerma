/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;

import DTOs.SalaNuevaDTO;
import DTOs.SalaViejaDTO;
import Interfaces.mappers.ISalaMapper;
import entidades.Sala;

/**
 *
 * @author Ramon Valencia
 */
public class SalaMapper implements ISalaMapper{

    @Override
    public SalaViejaDTO toSalaViejaDTO(Sala sala) {
        SalaViejaDTO salaVieja = new SalaViejaDTO();
        
        salaVieja.setNumSala(sala.getNumSala());
        salaVieja.setNumAsientos(sala.getNumAsientos());
        salaVieja.setEstado(sala.getEstado());
        
        return salaVieja;
    }

    @Override
    public Sala toSalaViejaEntidad(SalaViejaDTO dto) {
        Sala sala = new Sala(
                dto.getNumAsientos(),
                dto.getNumSala(),
                dto.getEstado()
        );
        
        return sala;
    }

    @Override
    public SalaNuevaDTO toSalaNuevaDTO(Sala sala) {
        SalaNuevaDTO salaNueva = new SalaNuevaDTO();
        
        salaNueva.setNumSala(sala.getNumSala());
        salaNueva.setNumAsientos(sala.getNumAsientos());
        salaNueva.setEstado(sala.getEstado());
        
        return salaNueva;
    }

    @Override
    public Sala toSalaNuevaEntidad(SalaNuevaDTO dto) {
        Sala sala = new Sala(
                dto.getNumAsientos(),
                dto.getNumSala(),
                dto.getEstado()
        );
        
        return sala;
    }
    
    
}
