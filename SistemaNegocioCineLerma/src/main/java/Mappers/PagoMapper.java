/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;

import DTOs.PagoDTO;
import entidades.Pago;

/**
 *
 * @author sonic
 */
public class PagoMapper {
    
    public static PagoDTO toPagoDTO(Pago pago) {
        //Primero se valida si el pago recibido es null, entonces se retornara un null
        if (pago == null) {
            return null;
        }
        
        //Se crea el objeto de pagoDTO
        PagoDTO pagodto = new PagoDTO();
        
        //Se le setea al DTO sus datos segun los de la entidad recibida
        pagodto.setEstado(pago.getEstado());
        pagodto.setFechaHora(pago.getFechaHora());
        pagodto.setMonto(pago.getMonto());
        

        return pagodto;

    }
    
    public static Pago toPagoEntidad(PagoDTO pagodto) {
        if (pagodto == null) {
            return null;
        }

        Pago pago = new Pago();
        pago.setMonto(pagodto.getMonto());
        pago.setFechaHora(pagodto.getFechaHora());
        pago.setEstado(pagodto.getEstado());

        return pago;
    }
    
}
