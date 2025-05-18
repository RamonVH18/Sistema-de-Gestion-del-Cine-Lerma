/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Excepciones.salas.BuscarSalaException;
import Excepciones.salas.CreacionSalaException;
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
    
    public List<Sala> buscarSalas(String filtroNombre, Boolean filtroActivas) throws BuscarSalaException;
    
    public Boolean modificarEstadoSala(String numSala, EstadoSala estadoNuevo) throws ModificarSalaException;
    
    
}
