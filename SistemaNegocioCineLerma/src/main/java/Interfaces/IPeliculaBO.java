/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import DTOs.PeliculaDTO;
import Excepciones.peliculas.*;
import java.util.List;

/**
 *
 * @author Ramon Valencia
 */
public interface IPeliculaBO {
    
    public PeliculaDTO registrarPelicula(PeliculaDTO peliculaDTO) throws PeliculaRegistroException;
    
    public PeliculaDTO actualizarPelicula(PeliculaDTO peliculaDTO) throws PeliculaActualizacionException;
    
    public Boolean eliminarPelicula(PeliculaDTO peliculaDTO) throws PeliculaEliminarException;
    
    public Boolean darAltaPelicula(PeliculaDTO peliculaDTO) throws PeliculaDarAltaException;
    
    public Boolean darBajaPelicula(PeliculaDTO peliculaDTO) throws PeliculaDarBajaException;
    
    public PeliculaDTO buscarPelicula(String titulo) throws PeliculaBusquedaException;
    
    public List<PeliculaDTO> mostrarPeliculasActivasOInactivas(boolean activo) throws PeliculasActivasInactivasException;
    
    public List<PeliculaDTO> mostrarTodasLasPeliculas() throws PeliculasMostrarTodasException;
}
