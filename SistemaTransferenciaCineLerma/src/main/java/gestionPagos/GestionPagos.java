/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestionPagos;

import DTOs.PagoDTO;
import DTOs.PaypalDTO;
import DTOs.TarjetaDTO;
import Excepciones.excepcionTransferencia;

/**
 *
 * @author Abraham Coronel Bringas
 */
public class GestionPagos {

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

    public void procesarPagoMercado() {

    }

    public void procesarPagoTarjeta(TarjetaDTO paypal, PagoDTO pago) {

    }

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

    public boolean validadarMercado() {

        return false;

    }

    public boolean validadarTarjeta(TarjetaDTO tarjeta) {

        return false;

    }

    public boolean consultarEstadoPagoPaypal(PagoDTO pago) {
        if (pago == null || pago.getEstado() == null) {
            return false;
        }

        String estado = pago.getEstado().toUpperCase();

        return estado.equals("EXITOSO");

    }

    public boolean consultarEstadoPagoTarjeta(PagoDTO pago) {

        return false;

    }

    public boolean consultarEstadoPagoMercado(PagoDTO pago) {

        return false;

    }
}
