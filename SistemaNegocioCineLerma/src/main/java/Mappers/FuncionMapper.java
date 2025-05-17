/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;

import DTOs.FuncionDTO;
import Interfaces.mappers.IFuncionMapper;
import entidades.Funcion;

/**
 *
 * @author Abraham Coronel Bringas
 */
public class FuncionMapper implements IFuncionMapper {

    @Override
    public FuncionDTO toFuncionDTO(Funcion funcion) {
        if (funcion == null || funcion.getSala() == null || funcion.getPelicula() == null) {
            return null; // o lanzar una excepción controlada
        }

        FuncionDTO dto = new FuncionDTO();
        dto.setIdFuncion(funcion.getIdString());
        dto.setFechaHora(funcion.getFechaHora());
        dto.setNombrePelicula(funcion.getPelicula().getTitulo());
        dto.setPrecio(funcion.getPrecio());
        dto.setNumSala(funcion.getSala().getNumSala()); // Asignar solo el número
        dto.setIdEmpleado(funcion.getIdEmpleado());
        return dto;
    }

    @Override
    public Funcion toFuncionEntidad(FuncionDTO dto) {
        Funcion funcion = new Funcion();
        funcion.setIdString(dto.getIdFuncion());
        funcion.setFechaHora(dto.getFechaHora());
        funcion.setPrecio(dto.getPrecio());
        funcion.setIdEmpleado(dto.getIdEmpleado());

        return funcion;
    }

}
