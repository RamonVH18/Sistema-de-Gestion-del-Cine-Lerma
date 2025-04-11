/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import DTOs.PeliculaDTO;
import Excepciones.peliculas.PeliculaActualizacionException;
import Excepciones.peliculas.PeliculaBusquedaException;
import Excepciones.peliculas.PeliculaRegistroException;
import java.util.List;

/**
 *
 * @author Ramon Valencia
 */
public interface IPeliculaBO {
    
    public PeliculaDTO registrarPelicula(PeliculaDTO peliculaDTO) throws PeliculaRegistroException;
    
    public Boolean actualizarPelicula(PeliculaDTO peliculaDTO) throws PeliculaActualizacionException;
    
    //Este metodo puede quedarse como DAO y no como BO
    public PeliculaDTO buscarPelicula(String nombrePelicula) throws PeliculaBusquedaException;
    
    public PeliculaDTO buscarTodasPeliculas() throws PeliculaBusquedaException;
    
    public List<PeliculaDTO> buscarTodasPeliculasActivas() throws PeliculaBusquedaException;
    
    public List<PeliculaDTO> buscarConFiltros(String filtro) throws PeliculaBusquedaException;
    
}
