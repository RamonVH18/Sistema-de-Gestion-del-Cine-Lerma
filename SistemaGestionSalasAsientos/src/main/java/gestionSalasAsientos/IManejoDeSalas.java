/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gestionSalasAsientos;

import DTOs.GananciaSalaDTO;
import DTOs.SalaDTO;
import Excepciones.AgregarSalaException;
import Excepciones.BuscarSalaException;
import Excepciones.EstadisticasSalaException;
import Excepciones.ModificarSalaException;
import Excepciones.ValidacionSalaException;
import enums.EstadoSala;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Ramon Valencia
 */
public interface IManejoDeSalas {
    
    public SalaDTO agregarSala(SalaDTO sala) throws AgregarSalaException;
    
    public void validarSala(SalaDTO sala) throws ValidacionSalaException;
    
    public List<SalaDTO> cargarSalas() throws BuscarSalaException;
    
    public List<SalaDTO> cargarSalasFiltro(String filtro) throws BuscarSalaException;
    
    public List<SalaDTO> cargarSalaPorNumero(String numero) throws BuscarSalaException;
    
    public List<SalaDTO> cargarSalasPorPeriodo(LocalDate periodoInicio, LocalDate periodoFinal) throws BuscarSalaException;
    
    public List<GananciaSalaDTO> obtenerGananciaSala(List<SalaDTO> listaSalas) throws EstadisticasSalaException;
    
    public Boolean imprimirEstadisticasSala(GananciaSalaDTO gananciaSala) throws EstadisticasSalaException;
    
    public Boolean modificarEstadoSala (String numeroSala, EstadoSala estado) throws ModificarSalaException;
}
