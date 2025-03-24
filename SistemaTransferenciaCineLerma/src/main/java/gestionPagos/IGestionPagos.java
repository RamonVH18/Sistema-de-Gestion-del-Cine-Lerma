/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gestionPagos;

import DTOs.CuentaMercadoDTO;
import DTOs.PagoDTO;
import DTOs.PaypalDTO;
import DTOs.TarjetaDTO;
import Excepciones.excepcionTransferencia;

/**
 *
 * @author Ramon Valencia
 */
public interface IGestionPagos {
    
    public void procesarPagoPaypal(PaypalDTO paypal, PagoDTO pago) throws excepcionTransferencia;
    
    public void procesarPagoMercado(CuentaMercadoDTO mercadoPago, PagoDTO pago) throws excepcionTransferencia;
    
    public void procesarPagoTarjeta(TarjetaDTO paypal, PagoDTO pago) throws excepcionTransferencia;
    
    public boolean validarPaypal(PaypalDTO paypal);
    
    public boolean validarMercado(CuentaMercadoDTO mercadoPago);
    
    public boolean validarTarjeta(TarjetaDTO tarjeta);
    
    public boolean consultarEstadoPago(PagoDTO pago);
        
    }
