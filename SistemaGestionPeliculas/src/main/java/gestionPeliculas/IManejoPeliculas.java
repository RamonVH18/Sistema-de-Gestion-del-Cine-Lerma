/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gestionPeliculas;

import DTOs.PeliculaDTO;
import Excepciones.*;
import java.util.List;

/**
 *
 * @author Daniel M
 */
public interface IManejoPeliculas {

    public PeliculaDTO registrarPelicula(PeliculaDTO peliculaDTO) throws RegistrarPeliculaException;

    public PeliculaDTO actualizarPelicula(PeliculaDTO peliculaDTO) throws ActualizarPeliculaException;

    public boolean eliminarPelicula(PeliculaDTO peliculaDTO) throws EliminarPeliculaException;
    
    public PeliculaDTO buscarPelicula(String nombrePelicula) throws MostrarPeliculasException;

    public boolean darAltaPelicula(PeliculaDTO peliculaDTO) throws DarAltaPeliculaException;

    public boolean darBajaPelicula(PeliculaDTO peliculaDTO) throws DarBajaPeliculaException;

    public List<PeliculaDTO> mostrarPeliculasActivasOInactivas(boolean activo) throws MostrarPeliculasException;

}
