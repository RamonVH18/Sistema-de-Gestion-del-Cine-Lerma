/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package control;

import DTOs.BoletoDTO;
import DTOs.CuentaMercadoDTO;
import DTOs.FuncionDTO;
import DTOs.MetodoPagoDTO;
import DTOs.PagoDTO;
import DTOs.PaypalDTO;
import DTOs.PeliculaDTO;
import DTOs.TarjetaDTO;
import Excepciones.PresentacionException;
import java.util.List;

/**
 *
 * @author Ramon Valencia
 */
public interface IControl {
    
    public void mostrarMenuPrincipal();
    
    public void mostrarSeleccionarPelicula();
    
    public void mostrarSeleccionarAsientos(PeliculaDTO pelicula);
    
    public void mostrarSeleccionarMetodoPago(FuncionDTO funcion);
    
    public void mostrarPantallaPago(String tipo) throws PresentacionException;
    
    public void mostrarDetalleBoleto();
    
    public void mostrarPantallaPagoRechazado();
    
    public List<PeliculaDTO> obtenerPeliculas();
    
    public PeliculaDTO consultarPelicula();
    
    public List<FuncionDTO> obtenerFunciones(String nombrePelicula);
    
    public FuncionDTO consultarFuncion();
    
    public int obtenerAsientosDisponibles(FuncionDTO funcion);
    
    public void guardarNumeroAsientos(int numAsientos);
    
    public int consultarNumeroAsientos();
    
    public String validarCamposAsientos(String texto, FuncionDTO funcion);
    
    public List<MetodoPagoDTO> obtenerMetodosPago();
    
    public BoletoDTO cargarBoleto();
    
    public CuentaMercadoDTO verificarCuentaMercado(CuentaMercadoDTO cuentaMercado);
    
    public PaypalDTO verificarCuentaPaypal(PaypalDTO cuentaPaypal);
    
    public TarjetaDTO verificarCuentaTarjeta(TarjetaDTO cuentaTarjeta);
    
    public Double calcularCostoTotal();
    
    public void actualizarSaldoMercado(CuentaMercadoDTO mercadoPago, PagoDTO pago);
    
    public void actualizarSaldoPaypal(PaypalDTO paypal, PagoDTO pago);
    
    public void actualizarSaldoTarjeta(TarjetaDTO tarjeta, PagoDTO pago);
    
    public void procesarPagoMercado(CuentaMercadoDTO mercadoPago, PagoDTO pago);
    
    public void procesarPagoPaypal(PaypalDTO paypal, PagoDTO pago);
    
    public void procesarPagoTarjeta(TarjetaDTO tarjeta, PagoDTO pago);
    
}
