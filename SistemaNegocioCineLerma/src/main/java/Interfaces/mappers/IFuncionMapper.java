/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces.mappers;

import DTOs.FuncionDTO;
import entidades.Funcion;

/**
 *
 * @author Abraham Coronel Bringas
 */
public interface IFuncionMapper {

    public FuncionDTO toFuncionDTO(Funcion funcion);

    public Funcion toFuncionEntidad(FuncionDTO funciondto);
}
