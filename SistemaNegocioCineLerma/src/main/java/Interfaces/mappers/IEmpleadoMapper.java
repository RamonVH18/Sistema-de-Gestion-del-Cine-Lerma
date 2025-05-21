/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces.mappers;

import DTOs.EmpleadoDTO;
import entidades.Empleado;

/**
 *
 * @author isaac
 */
public interface IEmpleadoMapper {
    
    
    public Empleado convertirDTOAEntidad(EmpleadoDTO dto);
    
    public void actualizarEntidadConDTO(Empleado entidadExistente, EmpleadoDTO dtoConNuevosDatos);
    
    public EmpleadoDTO convertirEntidadADTO(Empleado entidad);
    
    
}
