/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import DTOs.PeliculaDTO;
import Excepciones.peliculas.*;
import java.util.List;

/**
 * Interfaz que define las operaciones de negocio relacionadas con las
 * películas.
 *
 * Esta capa se encarga de procesar la lógica del negocio antes de comunicarse
 * con la capa de datos. Maneja unicamente mapeos de entidad a DTO y viceversa,
 * asi como excepciones específicas del dominio.
 *
 * Se utiliza para registrar, actualizar, eliminar, filtrar y buscar películas
 * según distintos criterios.
 *
 * Cada método puede lanzar excepciones específicas en caso de errores o
 * condiciones no válidas.
 *
 * @author Daniel M
 */
public interface IPeliculaBO {

    /**
     * Registra una nueva película.
     *
     * @param peliculaDTO Datos de la película a registrar.
     * @return La película registrada con su ID generado.
     * @throws PeliculaRegistroException Si ocurre un error durante el registro.
     */
    public PeliculaDTO registrarPelicula(PeliculaDTO peliculaDTO) throws PeliculaRegistroException;

    /**
     * Actualiza los datos de una película existente.
     *
     * @param peliculaDTO Datos de la película con la información actualizada.
     * @return La película actualizada.
     * @throws PeliculaActualizacionException Si ocurre un error durante la
     * actualización.
     */
    public PeliculaDTO actualizarPelicula(PeliculaDTO peliculaDTO) throws PeliculaActualizacionException;

    /**
     * Elimina una película.
     *
     * @param peliculaDTO La película a eliminar.
     * @return true si la operación fue exitosa, false en caso contrario.
     * @throws PeliculaEliminarException Si ocurre un error al eliminar la
     * película.
     */
    public Boolean eliminarPelicula(PeliculaDTO peliculaDTO) throws PeliculaEliminarException;

    /**
     * Da de alta una película previamente inactiva.
     *
     * @param peliculaDTO La película a dar de alta.
     * @return true si la operación fue exitosa, false en caso contrario.
     * @throws PeliculaDarAltaException Si ocurre un error al dar de alta la
     * película.
     */
    public Boolean darAltaPelicula(PeliculaDTO peliculaDTO) throws PeliculaDarAltaException;

    /**
     * Da de baja una película activa.
     *
     * @param peliculaDTO La película a dar de baja.
     * @return true si la operación fue exitosa, false en caso contrario.
     * @throws PeliculaDarBajaException Si ocurre un error al dar de baja la
     * película.
     */
    public Boolean darBajaPelicula(PeliculaDTO peliculaDTO) throws PeliculaDarBajaException;

    /**
     * Muestra una lista de películas filtradas por diferentes criterios.
     *
     * @param activo Estado de activación de la película (true para activas,
     * false para inactivas).
     * @param clasificacion Clasificación de la película.
     * @param genero Género de la película.
     * @param titulo Título parcial o completo de la película.
     * @return Lista de películas que coinciden con los filtros.
     * @throws MostrarPeliculasFiltradasException Si ocurre un error al obtener
     * las películas.
     */
    public List<PeliculaDTO> mostrarPeliculasFiltradas(Boolean activo, String clasificacion, String genero, String titulo) throws MostrarPeliculasFiltradasException;

    /**
     * Busca una película por su título.
     *
     * @param titulo El título exacto de la película.
     * @return La película encontrada.
     * @throws PeliculaBusquedaException Si no se encuentra la película o hay un
     * error en la búsqueda.
     */
    public PeliculaDTO buscarPeliculaPorTitulo(String titulo) throws PeliculaBusquedaException;

    /**
     * Busca una película por su identificador único.
     *
     * @param idPelicula El ID de la película.
     * @return La película encontrada.
     * @throws PeliculaBusquedaException Si no se encuentra la película o hay un
     * error en la búsqueda.
     */
    public PeliculaDTO buscarPeliculaPorId(String idPelicula) throws PeliculaBusquedaException;
}
