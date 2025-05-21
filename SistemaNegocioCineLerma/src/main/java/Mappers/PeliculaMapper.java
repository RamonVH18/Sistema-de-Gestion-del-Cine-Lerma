/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;

import DTOs.PeliculaDTO;
import Interfaces.mappers.IPeliculaMapper;
import entidades.Pelicula;

/**
 *
 * @author Daniel M
 */
public class PeliculaMapper implements IPeliculaMapper {

    @Override
    public PeliculaDTO toPeliculaDTO(Pelicula pelicula) {
        if (pelicula == null) {
            return null;
        }

        PeliculaDTO dto = new PeliculaDTO();

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

    @Override
    public Pelicula toPeliculaEntidad(PeliculaDTO dto) {
        if (dto == null) {
            return null;
        }

        Pelicula pelicula = new Pelicula();
        
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
