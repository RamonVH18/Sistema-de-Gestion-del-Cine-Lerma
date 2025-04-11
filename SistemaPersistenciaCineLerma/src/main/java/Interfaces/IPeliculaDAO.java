/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces;

import Excepciones.PersistenciaException;
import entidades.Pelicula;
import java.util.List;

/**
 *
 * @author Abraham Coronel Bringas
 */
public interface IPeliculaDAO {

    public Pelicula registrarPelicula(Pelicula pelicula) throws PersistenciaException;

    public Pelicula actualizarPelicula(Pelicula peliculaActualizar) throws PersistenciaException;

    public Boolean eliminarPelicula(Long id) throws PersistenciaException;

    public List<Pelicula> mostrarListaPelicula() throws PersistenciaException;

    public Boolean actualizarEstado(Long id) throws PersistenciaException;

    public Pelicula buscarPelicula(String titulo) throws PersistenciaException;

}
