/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;

import DTOs.PeliculaDTO;
import Interfaces.mappers.IPeliculaMapper;
import entidades.Pelicula;

/**
 * Implementación de la interfaz IPeliculaMapper.
 *
 * Esta clase se encarga de mapear objetos entre la entidad Pelicula y su DTO
 * PeliculaDTO, permitiendo convertir datos desde la capa de datos a las
 * siguientes capas despues de la BO y viceversa.
 *
 * Es utilizada principalmente en la capa de negocio para mantener una
 * separación clara entre el modelo de datos interno y los objetos transferidos
 * al exterior.
 *
 * Cada método valida que el objeto recibido no sea nulo antes de realizar la
 * conversión. Si el objeto es nulo, retorna null para evitar errores en tiempo
 * de ejecución.
 *
 * @author Daniel M
 */
public class PeliculaMapper implements IPeliculaMapper {

    /**
     * Convierte una entidad Pelicula a su equivalente PeliculaDTO.
     *
     * @param pelicula La entidad de tipo Pelicula a convertir.
     * @return Un objeto PeliculaDTO con los datos mapeados o null si la entidad
     * es null.
     */
    @Override
    public PeliculaDTO toPeliculaDTO(Pelicula pelicula) {
        // verificamos que la pelicula recibida no sea null, si es asi se retorna null
        if (pelicula == null) {
            return null;
        }

        PeliculaDTO dto = new PeliculaDTO();

        // se setea el id del dto a String, en el metodo de la entidad se devuelve como un String
        dto.setIdPelicula(pelicula.getIdPeliculaString());
        dto.setImagen(pelicula.getImagen());
        dto.setTitulo(pelicula.getTitulo());
        dto.setGenero(pelicula.getGenero());
        dto.setDuracion(pelicula.getDuracion());
        dto.setClasificacion(pelicula.getClasificacion());
        dto.setSinopsis(pelicula.getSinopsis());
        dto.setActivo(pelicula.getActivo());

        return dto;
    }

    /**
     * Convierte un objeto PeliculaDTO a su equivalente entidad Pelicula.
     *
     * @param dto El objeto DTO que contiene los datos a convertir.
     * @return Una instancia de Pelicula con los datos del DTO o null si el DTO
     * es null.
     */
    @Override
    public Pelicula toPeliculaEntidad(PeliculaDTO dto) {
        // verificamos que la peliculaDTO recibida no sea null, si es asi se retorna null
        if (dto == null) {
            return null;
        }

        Pelicula pelicula = new Pelicula();

        // se setea el id de la pelicula, en el metodo de la entidad se convierte a ObjectId
        pelicula.setIdPeliculaString(dto.getIdPelicula());
        pelicula.setImagen(dto.getImagen());
        pelicula.setTitulo(dto.getTitulo());
        pelicula.setGenero(dto.getGenero());
        pelicula.setDuracion(dto.getDuracion());
        pelicula.setClasificacion(dto.getClasificacion());
        pelicula.setSinopsis(dto.getSinopsis());
        pelicula.setActivo(dto.getActivo());

        return pelicula;
    }

}
