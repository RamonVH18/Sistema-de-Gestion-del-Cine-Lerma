/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import DTOs.SalaNuevaDTO;
import DTOs.SalaViejaDTO;
import Excepciones.Sala.SalaBusquedaException;
import Excepciones.Sala.SalaModificacionException;
import Excepciones.Sala.SalaRegistroException;
import enums.EstadoSala;
import java.util.List;

/**
 *
 * @author Ramon Valencia
 */
public interface ISalaBO {
    
    public SalaViejaDTO agregarSala(SalaNuevaDTO sala) throws SalaRegistroException;
    
    public SalaViejaDTO buscarSala(String numSala) throws SalaBusquedaException;
    
    public List<SalaViejaDTO> buscarSalas(String filtroSalas) throws SalaBusquedaException;
    
    public Boolean modificarSala(String numSala, EstadoSala estadoNuevo) throws SalaModificacionException;
    
}
