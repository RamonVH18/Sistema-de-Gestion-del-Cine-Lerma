/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;

import DTOs.FuncionDTO;
import entidades.Funcion;

/**
 *
 * @author sonic
 */
public class FuncionMapper {

    public static FuncionDTO toFuncionDTO(Funcion funcion) {
        FuncionDTO funcionDTO = new FuncionDTO();
        funcionDTO.setId(funcion.getIdFuncion());
        funcionDTO.setFechaHora(funcion.getFechaHora());
        funcionDTO.setNombre(funcion.getPelicula().getTitulo());
        funcionDTO.setPrecio(funcion.getPrecio());
        funcionDTO.setSala("SALA: " + funcion.getSala().getNumSala().toString());

        return funcionDTO;

    }
    
    public static Funcion toFuncionEntidad(FuncionDTO funciondto) {
        Funcion funcion = new Funcion();
        funcion.setIdFuncion(funciondto.getId());
        funcion.setFechaHora(funciondto.getFechaHora());
        funcion.setPelicula(null);
        funcion.setSala(null);
        funcion.setPrecio(funciondto.getPrecio());
        
        return funcion;
        
    }
    
}
