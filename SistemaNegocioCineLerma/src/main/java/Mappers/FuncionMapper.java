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
/**
 * Clase encargada de convertir entre la entidad Funcion y su representación
 * DTO. Implementa el patrón Mapper para desacoplar la estructura de datos de la
 * entidad de su representacion externa (DTO), facilitando la transferencia
 * segura entre capas.
 *
 * @author Abraham Coronel Bringas
 */
public class FuncionMapper implements IFuncionMapper {

    /**
     * Convierte una entidad Funcion a su correspondiente DTO. - Extrae datos
     * básicos y relaciones (sala/película) en formato simplificado. - No
     * incluye objetos completos de sala/película, solo identificadores clave.
     *
     * @param funcion Entidad de dominio a convertir (no debe ser null).
     * @return FuncionDTO con datos esenciales, o null si la entrada es
     * inválida.
     */
    @Override
    public FuncionDTO toFuncionDTO(Funcion funcion) {
        if (funcion == null || funcion.getSala() == null || funcion.getPelicula() == null) {
            return null; // o lanzar una excepción controlada
        }

        FuncionDTO dto = new FuncionDTO();
        dto.setIdFuncion(funcion.getIdString());
        dto.setFechaHora(funcion.getFechaHora());
        dto.setNombrePelicula(funcion.getPelicula().getTitulo());
        dto.setPrecio(funcion.getPrecio());
        dto.setNumSala(funcion.getSala().getNumSala()); // Asignar solo el número
        dto.setIdEmpleado(funcion.getIdEmpleado());
        return dto;
    }

    /**
     * Convierte un FuncionDTO a entidad Funcion (parcial). - Asigna solo campos
     * directos, no resuelve relaciones (sala/película). - Las relaciones deben
     * establecerse externamente usando DAOs correspondientes.
     *
     * @param dto Objeto de transferencia con datos a convertir.
     * @return Entidad Funcion con datos básicos inicializados.
     */
    @Override
    public Funcion toFuncionEntidad(FuncionDTO dto) {
        Funcion funcion = new Funcion();
        funcion.setIdString(dto.getIdFuncion());
        funcion.setFechaHora(dto.getFechaHora());
        funcion.setPrecio(dto.getPrecio());
        funcion.setIdEmpleado(dto.getIdEmpleado());

        return funcion;
    }

}
