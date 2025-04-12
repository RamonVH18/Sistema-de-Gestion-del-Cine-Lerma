/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces.mappers;

import DTOs.AsientoFuncionDTO;
import entidades.AsientoFuncion;

/**
 *
 * @author Daniel M
 */
public interface IAsientoFuncionMapper {

    public AsientoFuncionDTO toAsientoFuncionDTO(AsientoFuncion asientoFuncion);

    public AsientoFuncion toAsientoFuncionEntidad(AsientoFuncionDTO asientoFuncionDTO);

}
