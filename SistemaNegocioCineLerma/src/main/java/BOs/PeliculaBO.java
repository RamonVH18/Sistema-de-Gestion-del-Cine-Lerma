/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BOs;

import DAOs.PeliculaDAO;
import DTOs.PeliculaDTO;
import Excepciones.PersistenciaException;
import Excepciones.peliculas.PeliculaActualizacionException;
import Excepciones.peliculas.PeliculaBusquedaException;
import Excepciones.peliculas.PeliculaRegistroException;
import Interfaces.IPeliculaBO;
import Mappers.PeliculaMapper;
import entidades.Pelicula;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Ramon Valencia
 */
public class PeliculaBO {

    private static PeliculaBO instance;
    private PeliculaDAO peliculaDAO;

    private PeliculaBO() {
        // Inicializar el DAO
        peliculaDAO = PeliculaDAO.getInstanceDAO();
    }

    // Método para obtener la instancia única
    public static PeliculaBO getInstanceBO() {
        if (instance == null) {
            instance = new PeliculaBO();
        }
        return instance;
    }

    public Pelicula registrarPelicula(PeliculaDTO pelicula) throws PeliculaRegistroException, PersistenciaException {
        // Validar que la pelicula no exista ya
        if (peliculaDAO.buscarPelicula(pelicula.getNombrePelicula()).getTitulo() == pelicula.getNombrePelicula()) {
            throw new PeliculaRegistroException("Ya existe una película con este título");
        }

        if (pelicula.getPeliculaImagen() == null) {
            throw new PeliculaRegistroException("La pelicula no tiene una imagen");
        }

        Pelicula peliculaRegistrar = PeliculaMapper.toEntity(pelicula);

        return peliculaDAO.registrarPelicula(peliculaRegistrar);

    }

    public void validarPelicula(PeliculaDTO pelicula) throws PeliculaRegistroException {
        if (pelicula.getNombrePelicula() == null || pelicula.getNombrePelicula().trim().isEmpty()) {
            throw new PeliculaRegistroException("El título es requerido");
        }
        if (pelicula.getDuracion() <= 0) {
            throw new PeliculaRegistroException("Duración inválida");
        }

    }

    public Pelicula actualizarPelicula(PeliculaDTO pelicula) throws PeliculaActualizacionException, PersistenciaException {
        // PELICULA QUE NO SEA NULA
        if (pelicula == null) {
            throw new PeliculaActualizacionException("La película no puede ser nula");
        }

        // titulo es requerido
        if (pelicula.getNombrePelicula() == null || pelicula.getNombrePelicula().trim().isEmpty()) {
            throw new PeliculaActualizacionException("El título es requerido");
        }

        // La duracion debe ser mayor a 0
        if (pelicula.getDuracion() <= 0) {
            throw new PeliculaActualizacionException("Duración inválida");
        }

        Pelicula peliculaActualizar = PeliculaMapper.toEntity(pelicula);

        return peliculaDAO.actualizarPelicula(peliculaActualizar);

    }

    public void eliminarPelicula(Long id) throws PeliculaRegistroException, PersistenciaException {
        if (id == null || id <= 0) {
            throw new PeliculaRegistroException("ID inválido");
        }

        if (!peliculaDAO.eliminarPelicula(id)) {
            throw new PeliculaRegistroException("Película no encontrada para eliminar");
        }

    }
    
    //Le faltan algunos metodos

}
