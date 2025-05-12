/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;

import DTOs.EmpleadoDTO;
import entidades.Empleado;

/**
 *
 * @author isaac
 */
public class EmpleadoMapper {
    
    
    public Empleado convertirDTOAEntidad(EmpleadoDTO dto) {
        if (dto == null) {
            return null;
        }
        Empleado entidad = new Empleado();
        // El ID se maneja con cuidado: si el DTO lo trae, se usa (para actualizaciones).
        // Si no (DTO para nuevo empleado), la entidad se creará sin ID y el DAO lo generará.
        if (dto.getId() != null) {
            entidad.setId(dto.getId());
        }
        entidad.setNombre(dto.getNombre());
        entidad.setApellidoP(dto.getApellidoP());
        entidad.setApellidoM(dto.getApellidoM());
        entidad.setCorreoE(dto.getCorreoE());
        entidad.setTelefono(dto.getTelefono());
        entidad.setFechaNacimiento(dto.getFechaNacimiento());
        entidad.setCargo(dto.getCargo());
        entidad.setSueldo(dto.getSueldo());
        entidad.setCalle(dto.getCalle());
        entidad.setColonia(dto.getColonia());
        entidad.setNumExterior(dto.getNumExterior());
        // activo y fechaRegistro son establecidos por el constructor del DTO/Entidad o manejados por el BO
        entidad.setActivo(dto.isActivo());
        entidad.setFechaRegistro(dto.getFechaRegistro());
        return entidad;
    }

    // Para actualizar una entidad existente con datos de un DTO
    public void actualizarEntidadConDTO(Empleado entidadExistente, EmpleadoDTO dtoConNuevosDatos) {
        if (entidadExistente == null || dtoConNuevosDatos == null) {
            return;
        }

        entidadExistente.setNombre(dtoConNuevosDatos.getNombre());
        entidadExistente.setApellidoP(dtoConNuevosDatos.getApellidoP());
        entidadExistente.setApellidoM(dtoConNuevosDatos.getApellidoM());
        entidadExistente.setCorreoE(dtoConNuevosDatos.getCorreoE());
        entidadExistente.setTelefono(dtoConNuevosDatos.getTelefono());
        entidadExistente.setFechaNacimiento(dtoConNuevosDatos.getFechaNacimiento());
        entidadExistente.setCargo(dtoConNuevosDatos.getCargo());
        entidadExistente.setSueldo(dtoConNuevosDatos.getSueldo());
        entidadExistente.setCalle(dtoConNuevosDatos.getCalle());
        entidadExistente.setColonia(dtoConNuevosDatos.getColonia());
        entidadExistente.setNumExterior(dtoConNuevosDatos.getNumExterior());
        // Los campos 'activo' y 'fechaRegistro' no se actualizan desde el DTO de esta forma.
        // 'activo' se maneja con despedirEmpleado, 'fechaRegistro' es inmutable tras creación.
    }

    public EmpleadoDTO convertirEntidadADTO(Empleado entidad) {
        if (entidad == null) {
            return null;
        }
        return new EmpleadoDTO(
                entidad.getId(),
                entidad.getNombre(),
                entidad.getApellidoP(),
                entidad.getApellidoM(),
                entidad.getCorreoE(),
                entidad.getTelefono(),
                entidad.getFechaNacimiento(),
                entidad.getCargo(),
                entidad.getSueldo(),
                entidad.isActivo(),
                entidad.getFechaRegistro(),
                entidad.getCalle(),
                entidad.getColonia(),
                entidad.getNumExterior()
        );
    }
    
}
