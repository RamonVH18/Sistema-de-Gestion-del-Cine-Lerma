/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gestionPeliculas;

import DTOs.PeliculaDTO;
import Excepciones.*;
import java.util.List;

/**
 * Interfaz que define los métodos necesarios para el manejo de películas.
 *
 * Se encarga de validar los datos entrantes y salientes.
 *
 * Funciona como un puente entre la capa de negocio y la capa de presentacion.
 *
 * @author Daniel M
 */
public interface IManejoPeliculas {

    /**
     * Registra una nueva película.
     *
     * @param peliculaDTO Objeto con los datos de la película a registrar.
     * @return PeliculaDTO con los datos registrados.
     * @throws RegistrarPeliculaException Si ocurre un error al registrar.
     */
    public PeliculaDTO registrarPelicula(PeliculaDTO peliculaDTO) throws RegistrarPeliculaException;

    /**
     * Actualiza los datos de una película existente.
     *
     * @param peliculaDTO Objeto con los datos actualizados.
     * @return PeliculaDTO con la información actualizada.
     * @throws ActualizarPeliculaException Si ocurre un error durante la
     * actualización.
     */
    public PeliculaDTO actualizarPelicula(PeliculaDTO peliculaDTO) throws ActualizarPeliculaException;

    /**
     * Elimina una película si no tiene funciones asociadas.
     *
     * @param peliculaDTO Película a eliminar.
     * @return true si fue eliminada correctamente, false en caso contrario.
     * @throws EliminarPeliculaException Si ocurre un error durante la
     * eliminación.
     */
    public boolean eliminarPelicula(PeliculaDTO peliculaDTO) throws EliminarPeliculaException;

    /**
     * Busca una película por su título.
     *
     * @param nombrePelicula Título de la película.
     * @return PeliculaDTO encontrada.
     * @throws MostrarPeliculasException Si ocurre un error durante la búsqueda.
     */
    public PeliculaDTO buscarPeliculaPorTitulo(String nombrePelicula) throws MostrarPeliculasException;

    /**
     * Busca una película por su identificador único.
     *
     * @param idPelicula ID de la película.
     * @return PeliculaDTO encontrada.
     * @throws MostrarPeliculasException Si ocurre un error durante la búsqueda.
     */
    public PeliculaDTO buscarPeliculaPorId(String idPelicula) throws MostrarPeliculasException;

    /**
     * Da de alta una película previamente dada de baja.
     *
     * @param peliculaDTO Película a dar de alta.
     * @return true si fue dada de alta exitosamente.
     * @throws DarAltaPeliculaException Si ocurre un error durante el alta.
     */
    public boolean darAltaPelicula(PeliculaDTO peliculaDTO) throws DarAltaPeliculaException;

    /**
     * Da de baja una película activa (sin funciones vigentes).
     *
     * @param peliculaDTO Película a dar de baja.
     * @return true si fue dada de baja exitosamente.
     * @throws DarBajaPeliculaException Si ocurre un error durante la operación.
     */
    public boolean darBajaPelicula(PeliculaDTO peliculaDTO) throws DarBajaPeliculaException;

    /**
     * Muestra una lista de películas filtradas por estado, clasificación,
     * género y/o título.
     *
     * @param activo Estado de la película (true: activas, false: inactivas).
     * @param clasificacion Clasificación de la película (puede ser null para no
     * filtrar).
     * @param genero Género de la película (puede ser null para no filtrar).
     * @param titulo Parte del título o título completo (puede ser null para no
     * filtrar).
     * @return Lista de películas que cumplen con los filtros.
     * @throws ObtenerPeliculasFiltradasException Si ocurre un error al obtener
     * la lista.
     */
    public List<PeliculaDTO> mostrarPeliculasFiltradas(Boolean activo, String clasificacion, String genero, String titulo) throws ObtenerPeliculasFiltradasException;

    /**
     * Muestra todas las películas según su estado (activas o inactivas).
     *
     * @param activo true para activas, false para inactivas.
     * @return Lista de películas filtradas por estado.
     * @throws MostrarPeliculasException Si ocurre un error al obtener las
     * películas.
     */
    public List<PeliculaDTO> mostrarPeliculasActivasOInactivas(boolean activo) throws MostrarPeliculasException;
}
