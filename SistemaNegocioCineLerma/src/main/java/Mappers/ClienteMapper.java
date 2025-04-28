/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;

import DTOs.ClienteDTO;
import Interfaces.mappers.IClienteMapper;
import entidades.Cliente;

/**
 *
 * @author sonic
 */
public class ClienteMapper implements IClienteMapper{

    @Override
    public ClienteDTO toClienteDTO(Cliente cliente) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Cliente toClienteEntidad(ClienteDTO clientedto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
//    @Override
//    public ClienteDTO toClienteDTO(Cliente cliente) {
//        //Primero se valida si el cliente recibido es null, entonces se retornara un null
//        if (cliente == null) {
//            return null;
//        }
//        
//        //Se crea la instancia de clienteDTO
//        ClienteDTO clientedto = new ClienteDTO();
//        
//        //Se le setea al DTO sus datos segun los de la entidad recibida
//        clientedto.setCorreo(cliente.getCorreo());
//        clientedto.setNombre(cliente.getNombre());
//        clientedto.setContraseña(cliente.getContrasenia());
//        
//
//        return clientedto;
//
//    }
//    
//    @Override
//    public Cliente toClienteEntidad(ClienteDTO clientedto) {
//        //Primero se valida si el cliente recibido es null, entonces se retornara un null
//        if (clientedto == null) {
//            return null;
//        }
//        
//        //Se crea la instancia de cliente
//        Cliente cliente = new Cliente();
//        
//        //Se le setea a la entidad sus datos segun los del dto recibido
//        cliente.setCorreo(clientedto.getCorreo());
//        cliente.setContrasenia(clientedto.getContraseña());
//        cliente.setNombre(clientedto.getNombre());
//        
//        return cliente;
//        
//    }
}
