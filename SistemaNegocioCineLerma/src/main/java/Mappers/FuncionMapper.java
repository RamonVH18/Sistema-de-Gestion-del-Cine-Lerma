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
        //Primero se valida si la funcion recibida es null, entonces se retornara un null
        if (funcion == null) {
            return null;
        }

        FuncionDTO funcionDTO = new FuncionDTO();

        funcionDTO.setId(funcion.getIdString());
        funcionDTO.setFechaHora(funcion.getFechaHora());
        funcionDTO.setNombre(funcion.getPelicula().getTitulo());
        funcionDTO.setPrecio(funcion.getPrecio());
        funcionDTO.setSala("SALA: " + funcion.getSala().getNumSala().toString());
        funcionDTO.setIdEmpleado(funcion.getIdEmpleado());

        return funcionDTO;

    }

    @Override
    public Funcion toFuncionEntidad(FuncionDTO funciondto) {
        Funcion funcion = new Funcion();
        
        funcion.setIdString(funciondto.getId());
        funcion.setFechaHora(funciondto.getFechaHora());
        
        funcion.setPrecio(funciondto.getPrecio());
        funcion.setIdEmpleado(funciondto.getIdEmpleado());
        return funcion;
    }

}
