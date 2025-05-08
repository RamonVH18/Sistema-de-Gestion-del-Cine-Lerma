/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import DTOs.SalaDTO;
import Excepciones.Sala.SalaBusquedaException;
import Excepciones.Sala.SalaModificacionException;
import Excepciones.Sala.SalaRegistroException;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Ramon Valencia
 */
public interface ISalaBO {
    
    public SalaDTO agregarSala(SalaDTO sala) throws SalaRegistroException;
    
    public SalaDTO buscarSala() throws SalaBusquedaException;
    
    public List<SalaDTO> buscarTodasSalas() throws SalaBusquedaException;
    
    public Boolean modificarSala() throws SalaModificacionException;
    
    public List<SalaDTO> buscarSalasFiltro() throws SalaBusquedaException;
    
}
