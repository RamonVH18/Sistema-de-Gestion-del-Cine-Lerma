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

        //Se crea la instancia de funciondto
        FuncionDTO funcionDTO = new FuncionDTO();

        //Se le setea a la entidad sus datos segun los del dto recibido
        funcionDTO.setId(funcion.getIdFuncion());
        funcionDTO.setFechaHora(funcion.getFechaHora());
        funcionDTO.setNombre(funcion.getPelicula().getTitulo());
        funcionDTO.setPrecio(funcion.getPrecio());
        funcionDTO.setSala("SALA: " + funcion.getSala().getNumSala().toString());

        return funcionDTO;

    }

    @Override
    public Funcion toFuncionEntidad(FuncionDTO funciondto) {
        //Primero se valida si la funcion recibida es null, entonces se retornara un null
        if (funciondto == null) {
            return null;
        }
        //Se crea la instancia de funcion
        Funcion funcion = new Funcion();

        //Se le setea a la entidad sus datos segun los del dto recibido
        funcion.setIdFuncion(funciondto.getId());
        funcion.setFechaHora(funciondto.getFechaHora());
        funcion.setPelicula(null);
        funcion.setSala(null);
        funcion.setPrecio(funciondto.getPrecio());

        return funcion;

    }

}
