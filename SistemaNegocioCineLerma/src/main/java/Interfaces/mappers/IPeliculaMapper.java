/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces.mappers;

import DTOs.PeliculaDTO;
import entidades.Pelicula;

/**
 *
 * @author sonic
 */
public interface IPeliculaMapper {
    
    public PeliculaDTO toPeliculaDTO (Pelicula pelicula);
    
    public Pelicula toPeliculaEntidad (PeliculaDTO dto);
    
}
