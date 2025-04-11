/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Excepciones.funciones.FuncionEliminacionException;
import DTOs.FuncionDTO;
import DTOs.FuncionesPorPeliculaDTO;
import Excepciones.funciones.FuncionBusquedaException;
import Excepciones.funciones.FuncionRegistroException;
import java.util.List;

/**
 *
 * @author Ramon Valencia
 */
public interface IFuncionBO {
    
    public FuncionDTO registraFuncion(FuncionDTO funcionDTO) throws FuncionRegistroException;
    
    public Boolean eliminarFuncion(Long id) throws FuncionEliminacionException;
    
    public FuncionDTO buscarFuncion(Long id) throws FuncionBusquedaException;
    
    public FuncionesPorPeliculaDTO buscarFuncionPelicula(String pelicula) throws FuncionBusquedaException;
    
    public List<FuncionDTO> buscarFuncionesActivas() throws FuncionBusquedaException;
}
