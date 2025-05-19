/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces;

import Excepciones.PersistenciaException;
import Excepciones.peliculas.*;
import entidades.Pelicula;
import java.util.List;

/**
 *
 * @author Daniel M
 */
public interface IPeliculaDAO {

    public Pelicula registrarPelicula(Pelicula pelicula) throws RegistrarPeliculaException;

    public Pelicula actualizarPelicula(Pelicula pelicula) throws ActualizarPeliculaException;

    public Boolean eliminarPelicula(Pelicula pelicula) throws EliminarPeliculaException;
    
    public Boolean darAltaPelicula(Pelicula pelicula) throws DarAltaPeliculaException;
    
    public Boolean darBajaPelicula(Pelicula pelicula) throws DarBajaPeliculaException;

    public Pelicula buscarPelicula(String titulo) throws BuscarPeliculaException;

    public List<Pelicula> mostrarPeliculasFiltradas(Boolean activo, String clasificacion, String genero, String titulo) throws MostrarPeliculasException;
    
    public List<Pelicula> mostrarPeliculasActivasOInactivas(boolean activo) throws MostrarPeliculasException;

    public List<Pelicula> mostrarTodasLasPeliculas() throws MostrarPeliculasException;
}
