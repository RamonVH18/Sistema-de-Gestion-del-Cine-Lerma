/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Excepciones.FuncionEliminacionException;
import DTOs.FuncionDTO;
import DTOs.FuncionesPorPeliculaDTO;
import Excepciones.FuncionBusquedaException;
import Excepciones.FuncionRegistroException;
import java.util.List;

/**
 *
 * @author Ramon Valencia
 */
public interface IFuncionBO {
    
    public FuncionDTO registraFuncion(FuncionDTO funcionDTO) throws FuncionRegistroException;
    
    public Boolean eliminarFuncion() throws FuncionEliminacionException;
    
    public FuncionDTO buscarFuncion() throws FuncionBusquedaException;
    
    public FuncionesPorPeliculaDTO buscarFuncionPelicula(String pelicula) throws FuncionBusquedaException;
    
    public List<FuncionDTO> buscarFuncionesActivas() throws FuncionBusquedaException;
}
