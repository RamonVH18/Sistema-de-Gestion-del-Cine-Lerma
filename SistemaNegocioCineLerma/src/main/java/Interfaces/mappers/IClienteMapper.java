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
public interface IClienteMapper {
    
    public ClienteDTO toClienteDTO(Cliente cliente);
    
    public Cliente toClienteEntidad(ClienteDTO clientedto);
    
}
