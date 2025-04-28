/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gestionPeliculas;

import DTOs.PeliculaDTO;
import entidades.Pelicula;
import java.util.List;

/**
 *
 * @author Daniel M
 */
public interface IManejoPeliculas {
    
    public List<PeliculaDTO> cargarPeliculasActivas();
    
    public List<PeliculaDTO> cargarPeliculasInactivas();
    
    public void verificarPeliculasExistentes();
    
    public void verificarFuncionesExistentes();
    
    public void verificarCampos();
    
    public void verificarTamanioImagen();
    
    public PeliculaDTO cargarDetallesPelicula();
    
    public Pelicula registrarPelicula(PeliculaDTO peliculaDTO);
    
    public boolean eliminarPelicula(PeliculaDTO peliculaDTO);
    
    public boolean editarPelicula(PeliculaDTO peliculaDTO);
    
    public boolean darBajaPelicula(PeliculaDTO peliculaDTO);
    
    public boolean darAltaPelicula(PeliculaDTO peliculaDTO);
}
