/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import DTOs.AsientoFuncionDTO;
import DTOs.EstadisticaSalaDTO;
import DTOs.FuncionDTO;
import Excepciones.asientoFuncion.AsientoFuncionBusquedaException;
import Excepciones.asientoFuncion.AsientoFuncionEliminacionException;
import Excepciones.asientoFuncion.AsientoFuncionRegistroException;
import Excepciones.asientoFuncion.AsientoFuncionReservaException;
import Excepciones.asientoFuncion.ErrorAlObtenerEstadisticasException;
import java.util.List;

/**
 *
 * @author Ramon Valencia
 */
public interface IAsientoFuncionBO {
    
    public List<AsientoFuncionDTO> registrarAsientosFuncion(List<AsientoFuncionDTO> asientos) throws AsientoFuncionRegistroException;
    
    public Boolean eliminarAsientosFuncion(String idFuncion) throws AsientoFuncionEliminacionException;
    
    public Boolean reservarAsientosFuncion(List<AsientoFuncionDTO> asientos) throws AsientoFuncionReservaException;
    
    public List<AsientoFuncionDTO> obtenerAsientosFuncion(FuncionDTO funcion) throws AsientoFuncionBusquedaException;
    
    public List<AsientoFuncionDTO> obtenerAsientosDisponibles(FuncionDTO funcion) throws AsientoFuncionBusquedaException;
    
    public List<EstadisticaSalaDTO> obtenerEstadisticasSala() throws ErrorAlObtenerEstadisticasException;
    
}
