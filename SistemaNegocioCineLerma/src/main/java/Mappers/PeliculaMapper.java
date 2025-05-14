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
 * @author isaac
 */
public class PeliculaMapper implements IPeliculaMapper {

    @Override
    public PeliculaDTO toPeliculaDTO(Pelicula pelicula) {
        if (pelicula == null) {
            return null;
        }

        PeliculaDTO dto = new PeliculaDTO();
        dto.setNombrePelicula(pelicula.getTitulo());
        dto.setPeliculaImagen(pelicula.getImagen());
        dto.setDescripcionPelicula(pelicula.getSinopsis());

        return dto;
    }

    @Override
    public Pelicula toPeliculaEntidad(PeliculaDTO dto) {

        if (dto == null) {
            return null;
        }
        // creamos instancia de pelicula sin id
        Pelicula pelicula = new Pelicula();
        pelicula.setTitulo(dto.getNombrePelicula());
        pelicula.setImagen(dto.getPeliculaImagen());
        pelicula.setSinopsis(dto.getDescripcionPelicula());
        // establecemos valores por defecto para los campos que no estan en el DTO
        pelicula.setGenero(null);
        pelicula.setDuracion(null);
        pelicula.setActivo(true); // asumimos que la pelicula esta activa

        return pelicula;
    }

}
