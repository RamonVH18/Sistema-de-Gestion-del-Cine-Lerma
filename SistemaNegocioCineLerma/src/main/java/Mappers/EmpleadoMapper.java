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

    public EmpleadoMapper() {
    }
    
    
    
    /**
     * Convierte un objeto EmpleadoDTO a una entidad Empleado.
     * El ID (String) del DTO se utiliza para establecer el ID (ObjectId) en la entidad
     * a través del método {@code entidad.setIdString(String)}.
     * Si el ID del DTO es nulo (por ejemplo, para un nuevo empleado), el ID de la entidad también será nulo
     * y se espera que la capa DAO genere un ObjectId al persistir.
     *
     * @param dto El objeto EmpleadoDTO a convertir.
     * @return La entidad Empleado correspondiente, o null si el DTO de entrada es null.
     */
    public Empleado convertirDTOAEntidad(EmpleadoDTO dto) {
        if (dto == null) {
            return null;
        }
        Empleado entidad = new Empleado();

        entidad.setIdString((dto.getId()));

        entidad.setNombre(dto.getNombre());
        entidad.setApellidoP(dto.getApellidoP());
        entidad.setApellidoM(dto.getApellidoM());
        entidad.setCorreoE(dto.getCorreoE());
        entidad.setTelefono(dto.getTelefono());
        entidad.setFechaNacimiento(dto.getFechaNacimiento());
        entidad.setCargo(dto.getCargo());
        entidad.setSueldo(dto.getSueldo()); // Asume que el sueldo ya está en el DTO
        entidad.setCalle(dto.getCalle());
        entidad.setColonia(dto.getColonia());
        entidad.setNumExterior(dto.getNumExterior());
        entidad.setActivo(dto.isActivo()); // El DTO ya tiene el valor por defecto
        entidad.setFechaRegistro(dto.getFechaRegistro()); // El DTO ya tiene el valor por defecto

        return entidad;
    }

    // Para actualizar una entidad existente con datos de un DTO
    /**
     * Actualiza los campos de una entidad Empleado existente con los datos de un EmpleadoDTO.
     * Este método se enfoca en los campos que típicamente se modifican en una actualización de información general.
     * Campos como ID, activo, fecha de registro, y a veces cargo y sueldo (que se actualizan
     * por métodos específicos) no se modifican aquí o se manejan con cuidado.
     *
     * @param entidadExistente La entidad Empleado a actualizar (no debe ser null).
     * @param dtoConNuevosDatos El EmpleadoDTO con los nuevos datos (no debe ser null).
     */
    public void actualizarEntidadConDTO(Empleado entidadExistente, EmpleadoDTO dtoConNuevosDatos) {
        if (entidadExistente == null || dtoConNuevosDatos == null) {
            return;
        }
        // Los campos que se actualizan (nombre, apellidos, dirección)
        entidadExistente.setNombre(dtoConNuevosDatos.getNombre());
        entidadExistente.setApellidoP(dtoConNuevosDatos.getApellidoP());
        entidadExistente.setApellidoM(dtoConNuevosDatos.getApellidoM());
        entidadExistente.setCalle(dtoConNuevosDatos.getCalle());
        entidadExistente.setColonia(dtoConNuevosDatos.getColonia());
        entidadExistente.setNumExterior(dtoConNuevosDatos.getNumExterior());

        entidadExistente.setCorreoE(dtoConNuevosDatos.getCorreoE());
        entidadExistente.setTelefono(dtoConNuevosDatos.getTelefono());
        entidadExistente.setFechaNacimiento(dtoConNuevosDatos.getFechaNacimiento());
        entidadExistente.setCargo(dtoConNuevosDatos.getCargo()); // Cargo se actualiza por método específico
        entidadExistente.setSueldo(dtoConNuevosDatos.getSueldo()); // Sueldo se actualiza por método específico

    }

    // --- Mapeo Entidad -> DTO ---
    /**
     * Convierte una entidad Empleado a un objeto EmpleadoDTO.
     * El ID (ObjectId) de la entidad se convierte a String usando {@code entidad.getIdString()}.
     *
     * @param entidad La entidad Empleado a convertir.
     * @return El EmpleadoDTO correspondiente, o null si la entidad de entrada es null.
     */
    public EmpleadoDTO convertirEntidadADTO(Empleado entidad) {
        if (entidad == null) {
            return null;
        }

        return new EmpleadoDTO(
                entidad.getIdString(),
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
