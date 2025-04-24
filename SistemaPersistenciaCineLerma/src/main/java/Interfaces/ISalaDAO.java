/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Excepciones.salas.BuscarSalaException;
import Excepciones.salas.CreacionSalaException;
import Excepciones.salas.ModificarSalaException;
import entidades.Sala;
import java.util.List;

/**
 *
 * @author Ramon Valencia
 */
public interface ISalaDAO {
    
    public Sala agregarSala(Sala sala) throws CreacionSalaException;
    
    public Sala buscarSala(String numSala) throws BuscarSalaException;
    
    public List<Sala> buscarTodasLasSalas() throws BuscarSalaException;
    
    public Boolean modificarSala() throws ModificarSalaException;
    
    public List<Sala> buscarSalasFiltradas(String filtro) throws BuscarSalaException;
    
}
