/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;

import DTOs.EmpleadoDTO;
import entidades.Empleado;
import org.bson.types.ObjectId;

/**
 *
 * @author isaac
 */
public class EmpleadoMapper {

    public EmpleadoMapper() {
    }

    // --- Métodos de conversión String <-> ObjectId ---
    public ObjectId toObjectId(String idHex) {
        if (idHex == null || idHex.trim().isEmpty()) {
            return null;
        }
        try {
            return new ObjectId(idHex);
        } catch (IllegalArgumentException e) {
            // Manejar el caso de un String de ID inválido si es necesario
            System.err.println("Error al convertir String a ObjectId: " + idHex + " - " + e.getMessage());
            return null; // O lanzar una excepción personalizada
        }
    }

    public String toStringId(ObjectId objectId) {
        return objectId != null ? objectId.toHexString() : null;
    }

    // --- Mapeo DTO -> Entidad ---
    public Empleado convertirDTOAEntidad(EmpleadoDTO dto) {
        if (dto == null) {
            return null;
        }
        Empleado entidad = new Empleado();

        // El ID del DTO (String) se convierte a ObjectId para la entidad.
        // Si el DTO.getId() es null (nuevo empleado), entidad.setId() recibirá null.
        // El DAO luego generará un ObjectId para la entidad si es necesario.
        entidad.setId(toObjectId(dto.getId()));

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
    public EmpleadoDTO convertirEntidadADTO(Empleado entidad) {
        if (entidad == null) {
            return null;
        }
        // Usa el constructor de EmpleadoDTO que toma String id
        return new EmpleadoDTO(
                toStringId(entidad.getId()), // Convierte ObjectId a String
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
