/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;

import DTOs.ClienteDTO;
import entidades.Cliente;

/**
 *
 * @author sonic
 */
public class ClienteMapper {
    
    public static ClienteDTO toClienteDTO(Cliente cliente) {
        //Primero se valida si el cliente recibido es null, entonces se retornara un null
        if (cliente == null) {
            return null;
        }
        
        //Se crea la instancia de clienteDTO
        ClienteDTO clientedto = new ClienteDTO();
        
        //Se le setea al DTO sus datos segun los de la entidad recibida
        clientedto.setCorreo(cliente.getCorreo());
        clientedto.setNombre(cliente.getNombre());
        clientedto.setContraseña(cliente.getContraseña());
        

        return clientedto;

    }
    
    public static Cliente toClienteEntidad(ClienteDTO clientedto) {
        //Primero se valida si el cliente recibido es null, entonces se retornara un null
        if (clientedto == null) {
            return null;
        }
        
        //Se crea la instancia de cliente
        Cliente cliente = new Cliente();
        
        //Se le setea a la entidad sus datos segun los del dto recibido
        cliente.setCorreo(clientedto.getCorreo());
        cliente.setContraseña(clientedto.getContraseña());
        cliente.setNombre(clientedto.getNombre());
        
        return cliente;
        
    }
}
