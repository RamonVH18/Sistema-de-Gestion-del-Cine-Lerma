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
public class ClienteMapper implements IClienteMapper {

    @Override
    public ClienteDTO toClienteDTO(Cliente cliente) {
        //Primero se valida si el cliente recibido es null, entonces se retornara un null
        if (cliente == null) {
            return null;
        }

        ClienteDTO dto = new ClienteDTO();

        // Mapeo de campos heredados de Usuario
        dto.setNombreUsuario(cliente.getNombreDeUsuario());
        dto.setContraseña(cliente.getContrasenia());
        dto.setNombre(cliente.getNombre());
        dto.setApellidoPaterno(cliente.getApellidoPaterno());
        dto.setApellidoMaterno(cliente.getApellidoMaterno());
        dto.setCorreoElectronico(cliente.getCorreoElectronico());
        dto.setFechaNacimiento(cliente.getFechaNacimiento());
        dto.setTelefono(cliente.getTelefono());
        dto.setRol(cliente.getRol());
        dto.setEstado(cliente.getEstado());

        // Mapeo de campos específicos de Cliente
        dto.setCalle(cliente.getCalle());
        dto.setCP(cliente.getCP());
        dto.setNumero(cliente.getNumero());

        return dto;
    }

    @Override
    public Cliente toClienteEntidad(ClienteDTO clientedto) {
        //Primero se valida si el cliente recibido es null, entonces se retornara un null
        if (clientedto == null) {
            return null;
        }

        Cliente cliente = new Cliente();

        // Campos heredados de Usuario
        cliente.setNombreDeUsuario(clientedto.getNombreUsuario());
        cliente.setContrasenia(clientedto.getContraseña());
        cliente.setNombre(clientedto.getNombre());
        cliente.setApellidoPaterno(clientedto.getApellidoPaterno());
        cliente.setApellidoMaterno(clientedto.getApellidoMaterno());
        cliente.setCorreoElectronico(clientedto.getCorreoElectronico());
        cliente.setFechaNacimiento(clientedto.getFechaNacimiento());
        cliente.setTelefono(clientedto.getTelefono());
        cliente.setRol(clientedto.getRol());
        cliente.setEstado(clientedto.getEstado());

        // Campos específicos de Cliente
        cliente.setCalle(clientedto.getCalle());
        cliente.setCP(clientedto.getCP());
        cliente.setNumero(clientedto.getNumero());

        return cliente;
    }

}
