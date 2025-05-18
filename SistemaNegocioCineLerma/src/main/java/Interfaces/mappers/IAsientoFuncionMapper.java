/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces.mappers;

import DTOs.AsientoFuncionDTO;
import entidades.AsientoFuncion;
import java.util.List;

/**
 *
 * @author Daniel M
 */
public interface IAsientoFuncionMapper {

    public List<AsientoFuncionDTO> toAsientoFuncionDTO(List<AsientoFuncion> asientos);

    public List<AsientoFuncion> toAsientoFuncionEntidad(List<AsientoFuncionDTO> asientosDTO);

}
