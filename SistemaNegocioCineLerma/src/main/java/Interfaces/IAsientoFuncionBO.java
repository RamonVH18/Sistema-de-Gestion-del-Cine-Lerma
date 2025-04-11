/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import DTOs.AsientoFuncionDTO;
import DTOs.FuncionDTO;
import java.util.List;

/**
 *
 * @author Ramon Valencia
 */
public interface IAsientoFuncionBO {
    
    public List<AsientoFuncionDTO> registrarAsientosFuncion(List<AsientoFuncionDTO> asientos);
    
    public Boolean reservarAsientosFuncion(List<AsientoFuncionDTO> asientos);
    
    public List<AsientoFuncionDTO> obtenerAsientosFuncion(FuncionDTO funcion);
    
    public List<AsientoFuncionDTO> obtenerAsientosDisponibles(FuncionDTO funcion);
    
}
