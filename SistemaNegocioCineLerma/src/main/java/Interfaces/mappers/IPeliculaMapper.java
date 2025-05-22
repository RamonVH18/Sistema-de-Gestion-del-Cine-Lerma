/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces.mappers;

import DTOs.PeliculaDTO;
import entidades.Pelicula;

/**
 * Interfaz para mapear objetos entre la entidad Pelicula y el DTO PeliculaDTO.
 *
 * Esta interfaz define los métodos necesarios para convertir entre las
 * representaciones de dominio y de transferencia de datos de una película.
 *
 * Se utiliza principalmente en la capa de negocio para mantener una separación
 * clara entre las entidades del modelo y los datos expuestos al exterior
 * (DTOs).
 *
 * @author Daniel M
 */
public interface IPeliculaMapper {

    /**
     * Convierte una entidad de tipo Pelicula a un objeto PeliculaDTO.
     *
     * @param pelicula La entidad Pelicula que se desea convertir.
     * @return Un objeto PeliculaDTO con los datos mapeados desde la entidad.
     */
    public PeliculaDTO toPeliculaDTO(Pelicula pelicula);

    /**
     * Convierte un objeto PeliculaDTO a una entidad Pelicula.
     *
     * @param dto El DTO PeliculaDTO que se desea convertir a entidad.
     * @return Una instancia de Pelicula con los datos del DTO.
     */
    public Pelicula toPeliculaEntidad(PeliculaDTO dto);
}
