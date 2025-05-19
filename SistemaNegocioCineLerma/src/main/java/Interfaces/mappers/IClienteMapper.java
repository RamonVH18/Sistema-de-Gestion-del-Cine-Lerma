/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces.mappers;

import DTOs.ClienteDTO;
import entidades.Cliente;

/**
 *
 * @author Ramon Valencia
 */
/**
 * Interfaz para el mapeo de objetos entre la entidad Cliente
 * y el objeto clienteDTO
 *
 * Esta interfaz define las operaciones necesarias para convertir una entidad a dto y viceversa
 *
 */
public interface IClienteMapper {
    
    /**
     * Convierte un objeto Cliente en un objeto ClienteDTO.
     *
     * @param cliente El objeto Cliente que se va a convertir.
     * @return Un objeto ClienteDTO que representa al cliente.
     */
    public ClienteDTO toClienteDTO(Cliente cliente);
    
    /**
     * Convierte un objeto ClienteDTO en un objeto Cliente.
     *
     * @param clientedto El objeto ClienteDTO que se va a convertir.
     * @return Un objeto Cliente que representa al cliente.
     */
    public Cliente toClienteEntidad(ClienteDTO clientedto);
    
}
