/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gestionPagos;

import DTOs.CuentaMercadoDTO;
import DTOs.PagoDTO;
import DTOs.PaypalDTO;
import DTOs.TarjetaDTO;
import Excepciones.TransferenciaException;
import java.util.List;

/**
 *
 * @author Ramon Valencia
 */
public interface IGestionPagos {
    
    public void procesarPagoPaypal(PaypalDTO paypal, PagoDTO pago) throws TransferenciaException;
    
    public void procesarPagoMercado(CuentaMercadoDTO mercadoPago, PagoDTO pago) throws TransferenciaException;
    
    public void procesarPagoTarjeta(TarjetaDTO paypal, PagoDTO pago) throws TransferenciaException;
    
    public boolean validarCuentaPaypal(PaypalDTO paypal) throws TransferenciaException;
    
    public boolean validarMercado(CuentaMercadoDTO mercadoPago);
    
    public boolean validarTarjeta(TarjetaDTO tarjeta);
    
    public boolean consultarEstadoPago(PagoDTO pago);
    
    public List<CuentaMercadoDTO> agregarCuentasMercado();
    
    public List<PaypalDTO> agregarCuentasPaypal();
    
    public List<TarjetaDTO> agregarCuentasTarjeta();
        
}
