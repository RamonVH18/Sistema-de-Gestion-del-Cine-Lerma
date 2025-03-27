/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gestionPagos;

import DTOs.CuentaMercadoDTO;
import DTOs.PagoDTO;
import DTOs.PaypalDTO;
import DTOs.TarjetaDTO;
import Excepciones.AgregarCuentaException;
import Excepciones.PagoException;
import Excepciones.ValidarCuentaException;
import java.util.List;

/**
 *
 * @author Ramon Valencia
 */

//Interfaz para todos los metodos de GestionPagos 
public interface IGestionPagos {
    
    public boolean procesarPagoPaypal(PaypalDTO paypal, PagoDTO pago) throws PagoException, ValidarCuentaException;
    
    public boolean procesarPagoMercado(CuentaMercadoDTO mercadoPago, PagoDTO pago) throws PagoException, ValidarCuentaException;
    
    public boolean procesarPagoTarjeta(TarjetaDTO paypal, PagoDTO pago) throws PagoException, ValidarCuentaException;
    
    public PaypalDTO validarCuentaPaypal(PaypalDTO paypal) throws ValidarCuentaException;
    
    public CuentaMercadoDTO validarMercado(CuentaMercadoDTO mercadoPago) throws ValidarCuentaException;
    
    public TarjetaDTO validarTarjeta(TarjetaDTO tarjeta) throws ValidarCuentaException;
    
    public boolean consultarEstadoPago(PagoDTO pago) throws PagoException;
    
    public List<CuentaMercadoDTO> agregarCuentasMercado() throws AgregarCuentaException;
    
    public List<PaypalDTO> agregarCuentasPaypal() throws AgregarCuentaException;
    
    public List<TarjetaDTO> agregarCuentasTarjeta() throws AgregarCuentaException;
    
    public void actualizarSaldoMercado(CuentaMercadoDTO mercadoPago, PagoDTO pago) ;
    
    public void actualizarSaldoPaypal(PaypalDTO paypal, PagoDTO pago) ;
    
    public void actualizarSaldoTarjeta(TarjetaDTO tarjeta, PagoDTO pago) ;
        
}
