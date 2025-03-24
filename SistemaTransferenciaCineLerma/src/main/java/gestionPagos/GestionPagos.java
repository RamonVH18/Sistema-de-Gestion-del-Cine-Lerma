/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestionPagos;

import DTOs.CuentaMercadoDTO;
import DTOs.PagoDTO;
import DTOs.PaypalDTO;
import DTOs.TarjetaDTO;
import Excepciones.excepcionTransferencia;
import java.util.Date;

/**
 *
 * @author Abraham Coronel Bringas
 */
public class GestionPagos implements IGestionPagos{

    @Override
    public void procesarPagoPaypal(PaypalDTO paypal, PagoDTO pago) throws excepcionTransferencia {
        if (!validarPaypal(paypal)) {
            throw new excepcionTransferencia("Error: Los datos de la cuenta de paypal son incorrectos");
        }

        if (pago.getMonto() == null || pago.getMonto() <= 0) {
            throw new excepcionTransferencia("Error: El monto debe ser mayor a 0");
        }

        System.out.println("Procesando pago de " + pago.getMonto() + "con Paypal");
        System.out.println("Correo: " + paypal.getCorreo());

        boolean pagoExitoso = true; //Para que sea exitoso

        if (pagoExitoso) {
            pago.setEstado("EXITOSO");
            System.out.println("Pago realizando con exito, " + pago.getEstado());
        } else {
            pago.setEstado("FALLIDO");
            System.out.println("Pago no realizado, " + pago.getEstado());
//            PantallaPagoRechazado pagoRechazado = new PantallaPagoRechazado(new JFrame(), true);
//            pagoRechazado.setVisible(true);
        }

    }

    @Override
    public void procesarPagoMercado(CuentaMercadoDTO mercadoPago, PagoDTO pago) throws excepcionTransferencia{
        Date fechaHoy = new Date();
        if (!validarMercado(mercadoPago)) {
            throw new excepcionTransferencia("Error: Los datos de la cuenta de MercadoPago son incorrectos");
        }
        
        if (pago.getMonto() == null || pago.getMonto() <= 0) {
            throw new excepcionTransferencia("Error: El monto debe ser mayor a 0");
        }
        
        if (!pago.getFechaHora().equals(fechaHoy)) {
            throw new excepcionTransferencia("Error: La fecha del pago debe ser la del dia de hoy");
        }
        
        System.out.println("Procesando pago de " + pago.getMonto() + "con MercadoPago");
        System.out.println("Correo: " + mercadoPago.getCorreo());
        
        boolean pagoExitoso = true; //Para que sea exitoso siempre, si se quiere probar que el pago falle este valor debera cambiarse a false

        if (pagoExitoso) {
            pago.setEstado("EXITOSO");
            System.out.println("Pago realizando con exito, " + pago.getEstado());
        } else {
            pago.setEstado("FALLIDO");
            System.out.println("Pago no realizado, " + pago.getEstado());
        }
        

    }

    @Override
    public void procesarPagoTarjeta(TarjetaDTO paypal, PagoDTO pago) throws excepcionTransferencia{

    }

    @Override
    public boolean validarPaypal(PaypalDTO paypal) {
        if (paypal == null) {
            return false;
        }

        if (paypal.getCorreo() == null || paypal.getCorreo().isEmpty() || paypal.getContrasenia() == null || paypal.getContrasenia().isEmpty()) {
            return false;
        }

        String formatoCorreo = "^[\\w.-]+@[a-zA-Z\\d,-]+\\.[a-zA-Z]{2,6}$";

        if (!paypal.getCorreo().matches(formatoCorreo)) {
            return false;
        }

        return true;

    }

    @Override
    public boolean validarMercado(CuentaMercadoDTO mercadoPago) {
        if (mercadoPago == null) {
            return false;
        }
        if (mercadoPago.getTitular() == null || mercadoPago.getTitular().isEmpty() || mercadoPago.getTitular().isBlank()){
            return false;
        }
        String formatoCorreo = "^[\\w.-]+@[a-zA-Z\\d,-]+\\.[a-zA-Z]{2,6}$";
        if (!mercadoPago.getCorreo().matches(formatoCorreo)) {
            return false;
        }

        return true;

    }

    @Override
    public boolean validadarTarjeta(TarjetaDTO tarjeta) {

        return false;

    }

//    public boolean consultarEstadoPagoPaypal(PagoDTO pago) {
//        if (pago == null || pago.getEstado() == null) {
//            return false;
//        }
//
//        String estado = pago.getEstado().toUpperCase();
//
//        return estado.equals("EXITOSO");
//
//    }
//
//    public boolean consultarEstadoPagoTarjeta(PagoDTO pago) {
//
//        return false;
//
//    }
//
//    public boolean consultarEstadoPagoMercado(PagoDTO pago) {
//        if (pago == null || pago.getEstado() == null) {
//            return false;
//        }
//        String estado = pago.toString().toUpperCase();
//        
//        return estado.equals("EXITOSO");
//
//    }
    
    @Override
    public boolean consultarEstadoPago(PagoDTO pago) {
        if (pago == null || pago.getEstado() == null) {
            return false;
        }

        String estado = pago.getEstado().toUpperCase();

        return estado.equals("EXITOSO");
    }
}
