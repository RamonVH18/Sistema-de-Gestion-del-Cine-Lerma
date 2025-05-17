/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import DTOs.GananciaSalaDTO;
import Excepciones.salas.BuscarSalaException;
import Excepciones.salas.CreacionSalaException;
import Excepciones.salas.ErrorCalculoEstadisticasSalaException;
import Excepciones.salas.ModificarSalaException;
import entidades.Sala;
import enums.EstadoSala;
import java.util.List;

/**
 *
 * @author Ramon Valencia
 */
public interface ISalaDAO {
    
    public Sala agregarSala(Sala sala) throws CreacionSalaException;
    
    public Sala buscarSala(String numSala) throws BuscarSalaException;
    
    public List<Sala> buscarSalas(String filtroNombre) throws BuscarSalaException;
    
    public Boolean modificarEstadoSala(String numSala, EstadoSala estadoNuevo) throws ModificarSalaException;
    
    public List<GananciaSalaDTO> obtenerEstadisticasDeSalas() throws ErrorCalculoEstadisticasSalaException;
    
//    public List<Sala> buscarSalasFiltradas(String filtro) throws BuscarSalaException;
    
}
