/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gestionSalasAsientos;

import DTOs.EstadisticaSalaDTO;
import DTOs.SalaNuevaDTO;
import DTOs.SalaViejaDTO;
import Excepciones.AgregarSalaException;
import Excepciones.BuscarSalaException;
import Excepciones.EstadisticasSalaException;
import Excepciones.ModificarSalaException;
import Excepciones.ValidacionSalaException;
import enums.EstadoSala;
import java.util.List;

/**
 *
 * @author Ramon Valencia
 */
public interface IManejoDeSalas {
    
    public SalaViejaDTO agregarSala(SalaNuevaDTO sala) throws AgregarSalaException;
    
    public void validarSala(String numSala, String numAsientos) throws ValidacionSalaException;
    
    public List<SalaViejaDTO> cargarSalas(String filtro, Boolean filtrarActivas) throws BuscarSalaException;
    
    public SalaViejaDTO cargarSalaUnica(String numSala) throws BuscarSalaException;

    public Boolean modificarEstadoSala (String numeroSala, EstadoSala estado) throws ModificarSalaException;
}
