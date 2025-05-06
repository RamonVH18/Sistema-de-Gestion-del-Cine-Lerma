/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;

import DTOs.CompraDTO;
import Interfaces.mappers.ICompraMapper;
import entidades.Compra;

/**
 *
 * @author sonic
 */
public class CompraMapper implements ICompraMapper{

    @Override
    public CompraDTO toCompraDTO(Compra compra) {
        if (compra == null) {
            return null;
        }

        CompraDTO dto = new CompraDTO();
        
        dto.setMetodoPago(compra.getMetodoPago());
        dto.setPago(PagoMapper.toPagoDTO(compra.getPago()));
        dto.setUsuarioCliente(compra.getUsuarioCliente());
        
        return dto;
    }

    @Override
    public Compra toCompraEntidad(CompraDTO compradto) {
        if (compradto == null) {
            return null;
        }

        Compra compra = new Compra();
        
        compra.setMetodoPago(compradto.getMetodoPago());
        compra.setUsuarioCliente(compradto.getUsuarioCliente());
        compra.setFecha(compradto.getFecha());
        compra.setPago(PagoMapper.toPagoEntidad(compradto.getPago()));
        
        return compra;
    }
    
}
