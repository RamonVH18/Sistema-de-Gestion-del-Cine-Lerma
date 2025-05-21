/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BOs;

import DAOs.PeliculaDAO;
import DTOs.PeliculaDTO;
import Excepciones.peliculas.*;
import Interfaces.IPeliculaBO;
import Interfaces.IPeliculaDAO;
import Interfaces.mappers.IPeliculaMapper;
import Mappers.PeliculaMapper;
import entidades.Pelicula;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase de negocio (Business Object) que implementa la lógica para gestionar
 * operaciones relacionadas con películas.
 *
 * Se utiliza el patrón Singleton para asegurar que solo exista una instancia de
 * esta clase durante la ejecución.
 *
 * @author Daniel M
 */
public class PeliculaBO implements IPeliculaBO {

    private static PeliculaBO instancePeliculaBO;
    private final IPeliculaDAO peliculaDAO = PeliculaDAO.getInstanceDAO();
    private final IPeliculaMapper peliculaMapper = new PeliculaMapper();

    /**
     * Constructor privado para aplicar el patrón Singleton.
     */
    private PeliculaBO() {
    }

    /**
     * Obtiene la única instancia de PeliculaBO.
     *
     * @return instancia única de PeliculaBO.
     */
    public static PeliculaBO getInstanceBO() {
        if (instancePeliculaBO == null) {
            instancePeliculaBO = new PeliculaBO();
        }
        return instancePeliculaBO;
    }

    /**
     * Registra una nueva película en la base de datos.
     *
     * @param peliculaDTO Objeto DTO con los datos de la película a registrar.
     * @return DTO de la película registrada.
     * @throws PeliculaRegistroException si ocurre un error al registrar la
     * película.
     */
    @Override
    public PeliculaDTO registrarPelicula(PeliculaDTO peliculaDTO) throws PeliculaRegistroException {
        try {
            // Convertimos el DTO a entidad
            Pelicula peliculaRegistrar = peliculaMapper.toPeliculaEntidad(peliculaDTO);
            // Registramos en la base de datos
            Pelicula peliculaRegistrada = peliculaDAO.registrarPelicula(peliculaRegistrar);
            // Convertimos de nuevo a DTO para retornarlo
            return peliculaMapper.toPeliculaDTO(peliculaRegistrada);
        } catch (RegistrarPeliculaException e) {
            throw new PeliculaRegistroException("Error al registrar la película.");
        }
    }

    /**
     * Actualiza los datos de una película existente.
     *
     * @param peliculaDTO Objeto DTO con los datos actualizados de la película.
     * @return DTO de la película actualizada.
     * @throws PeliculaActualizacionException si ocurre un error durante la
     * actualización.
     */
    @Override
    public PeliculaDTO actualizarPelicula(PeliculaDTO peliculaDTO) throws PeliculaActualizacionException {
        try {
            Pelicula peliculaActualizar = peliculaMapper.toPeliculaEntidad(peliculaDTO);
            Pelicula peliculaActualizada = peliculaDAO.actualizarPelicula(peliculaActualizar);
            return peliculaMapper.toPeliculaDTO(peliculaActualizada);
        } catch (ActualizarPeliculaException e) {
            throw new PeliculaActualizacionException("Error al actualizar la película." + e.getMessage());
        }
    }

    /**
     * Elimina una película que no tenga funciones existentes.
     *
     * @param peliculaDTO Película a eliminar.
     * @return true si la operación fue exitosa, false en caso contrario.
     * @throws PeliculaEliminarException si ocurre un error durante la
     * eliminación.
     */
    @Override
    public Boolean eliminarPelicula(PeliculaDTO peliculaDTO) throws PeliculaEliminarException {
        try {
            Pelicula peliculaEliminar = peliculaMapper.toPeliculaEntidad(peliculaDTO);
            boolean exito = peliculaDAO.eliminarPelicula(peliculaEliminar);
            return exito;
        } catch (EliminarPeliculaException e) {
            throw new PeliculaEliminarException("Error al eliminar la película.");
        }
    }

    /**
     * Da de alta una película previamente dada de baja.
     *
     * @param peliculaDTO Película a dar de alta.
     * @return true si la operación fue exitosa.
     * @throws PeliculaDarAltaException si ocurre un error durante la operación.
     */
    @Override
    public Boolean darAltaPelicula(PeliculaDTO peliculaDTO) throws PeliculaDarAltaException {
        try {
            Pelicula peliculaDarAlta = peliculaMapper.toPeliculaEntidad(peliculaDTO);
            boolean exito = peliculaDAO.darAltaPelicula(peliculaDarAlta);
            return exito;
        } catch (DarAltaPeliculaException e) {
            throw new PeliculaDarAltaException("Error al dar de alta la película.");
        }
    }

    /**
     * Da de baja una película activa que no tenga funciones existentes.
     *
     * @param peliculaDTO Película a dar de baja.
     * @return true si la operación fue exitosa.
     * @throws PeliculaDarBajaException si ocurre un error durante la operación.
     */
    @Override
    public Boolean darBajaPelicula(PeliculaDTO peliculaDTO) throws PeliculaDarBajaException {
        try {
            Pelicula peliculaDarBaja = peliculaMapper.toPeliculaEntidad(peliculaDTO);
            boolean exito = peliculaDAO.darBajaPelicula(peliculaDarBaja);
            return exito;
        } catch (DarBajaPeliculaException e) {
            throw new PeliculaDarBajaException("Error al dar de baja la película.");
        }
    }

    /**
     * Busca una película por su título.
     *
     * @param titulo Título de la película a buscar.
     * @return PelículaDTO encontrada.
     * @throws PeliculaBusquedaException si ocurre un error durante la búsqueda.
     */
    @Override
    public PeliculaDTO buscarPeliculaPorTitulo(String titulo) throws PeliculaBusquedaException {
        try {
            Pelicula peliculaBuscar = peliculaDAO.buscarPeliculaPorTitulo(titulo);
            return peliculaMapper.toPeliculaDTO(peliculaBuscar);
        } catch (BuscarPeliculaException e) {
            throw new PeliculaBusquedaException("Error al buscar la película.");
        }
    }
    
    /**
     * Busca una película por su id.
     *
     * @param idPelicula Id de la película a buscar.
     * @return PelículaDTO encontrada.
     * @throws PeliculaBusquedaException si ocurre un error durante la búsqueda.
     */
    @Override
    public PeliculaDTO buscarPeliculaPorId(String idPelicula) throws PeliculaBusquedaException {
        try {
            Pelicula peliculaBuscar = peliculaDAO.buscarPeliculaPorId(idPelicula);
            return peliculaMapper.toPeliculaDTO(peliculaBuscar);
        } catch (BuscarPeliculaException e) {
            throw new PeliculaBusquedaException("Error al buscar la película.");
        }
    }

    @Override
    public List<PeliculaDTO> mostrarPeliculasFiltradas(Boolean activo, String clasificacion, String genero, String titulo) throws MostrarPeliculasFiltradasException {
        try {
            List<PeliculaDTO> peliculasDTO = new ArrayList<>();
            List<Pelicula> peliculasFiltradasEntidad = peliculaDAO.mostrarPeliculasFiltradas(activo, clasificacion, genero, titulo);

            for (Pelicula pelicula : peliculasFiltradasEntidad) {
                peliculasDTO.add(peliculaMapper.toPeliculaDTO(pelicula));
            }

            return peliculasDTO;
        } catch (MostrarPeliculasException e) {
            throw new MostrarPeliculasFiltradasException("Error al mostrar las peliculas filtradas.");
        }
    }

    /**
     * Muestra las películas activas o inactivas según el parámetro recibido.
     *
     * @param activo true para mostrar activas, false para inactivas.
     * @return Lista de PeliculaDTO filtradas por estado.
     * @throws PeliculasActivasInactivasException si ocurre un error al obtener
     * las películas.
     */
    @Override
    public List<PeliculaDTO> mostrarPeliculasActivasOInactivas(boolean activo) throws PeliculasActivasInactivasException {
        try {
            List<PeliculaDTO> peliculasDTO = new ArrayList<>();
            List<Pelicula> peliculasEntidad = peliculaDAO.mostrarPeliculasActivasOInactivas(activo);
            // Convertimos cada entidad a DTO
            for (Pelicula pelicula : peliculasEntidad) {
                peliculasDTO.add(peliculaMapper.toPeliculaDTO(pelicula));
            }

            return peliculasDTO;
        } catch (MostrarPeliculasException e) {
            throw new PeliculasActivasInactivasException("Error al mostrar películas activas o inactivas.");
        }
    }
}
