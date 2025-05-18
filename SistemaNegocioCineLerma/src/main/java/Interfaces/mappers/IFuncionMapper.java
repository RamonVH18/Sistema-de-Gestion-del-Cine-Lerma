/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces.mappers;

import DTOs.FuncionDTO;
import entidades.Funcion;

/**
 * Interfaz que define el contrato para convertir entre la entidad Funcion y su
 * representacion DTO. Sigue el patrón Mapper para desacoplar la estructura de
 * dominio de los objetos de transferencia, permitiendo diferentes
 * implementaciones según las necesidades del sistema.
 *
 * @author Abraham Coronel Bringas
 */
public interface IFuncionMapper {

    /**
     * Convierte una entidad Funcion en un objeto de transferencia (DTO). -
     * Extrae campos clave y simplifica relaciones complejas.
     *
     * @param funcion Entidad de dominio con todos los datos persistentes.
     * @return FuncionDTO con datos esenciales para transferencia, o null si la
     * entrada es inválida.
     */
    public FuncionDTO toFuncionDTO(Funcion funcion);

    /**
     * Convierte un DTO en una entidad Funcion (estructura básica). - Asigna
     * campos directos pero no resuelve relaciones (requiere DAOs externos).
     *
     *
     * @param funciondto Objeto de transferencia con datos desde la capa
     * externa.
     * @return Entidad Funcion con campos básicos inicializados, lista para
     * operaciones de persistencia.
     */
    public Funcion toFuncionEntidad(FuncionDTO funciondto);

}
