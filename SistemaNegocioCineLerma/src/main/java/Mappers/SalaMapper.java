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
 * Clase mapeadora de salas
 * @author Ramon Valencia
 */
public class SalaMapper implements ISalaMapper{
    /**
     * Metodo para convertir de un objeto Sala a un objeto SalaViejaDTO
     * @param sala
     * @return 
     */
    @Override
    public SalaViejaDTO toSalaViejaDTO(Sala sala) {
        SalaViejaDTO salaVieja = new SalaViejaDTO();
        
        salaVieja.setNumSala(sala.getNumSala());
        salaVieja.setNumAsientos(sala.getNumAsientos());
        salaVieja.setEstado(sala.getEstado());
        
        return salaVieja;
    }
    /**
     * Metodo para convertir de un objeto SalaViejaDTO a un objeto Sala
     * @param dto
     * @return 
     */
    @Override
    public Sala toSalaViejaEntidad(SalaViejaDTO dto) {
        Sala sala = new Sala(
                dto.getNumAsientos(),
                dto.getNumSala(),
                dto.getEstado()
        );
        
        return sala;
    }
    /**
     * Metodo para convertir de un objeto Sala a un objeto SalaNuevaDTO
     * @param sala
     * @return 
     */
    @Override
    public SalaNuevaDTO toSalaNuevaDTO(Sala sala) {
        SalaNuevaDTO salaNueva = new SalaNuevaDTO();
        
        salaNueva.setNumSala(sala.getNumSala());
        salaNueva.setNumAsientos(sala.getNumAsientos());
        salaNueva.setEstado(sala.getEstado());
        
        return salaNueva;
    }
    /**
     * Metodo para convertir de un objeto SalaNuevaDTO a un objeto Sala
     * @param dto
     * @return 
     */
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
