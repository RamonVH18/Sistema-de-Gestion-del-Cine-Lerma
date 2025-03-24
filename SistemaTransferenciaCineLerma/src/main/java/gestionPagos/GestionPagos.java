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
public class GestionPagos implements IGestionPagos {

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
    public void procesarPagoMercado(CuentaMercadoDTO mercadoPago, PagoDTO pago) throws excepcionTransferencia {
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
    public void procesarPagoTarjeta(TarjetaDTO tarjeta, PagoDTO pago) throws excepcionTransferencia {
        Date fechaHoy = new Date();
        if (!validarTarjeta(tarjeta)) {
            throw new excepcionTransferencia("Error: Los datos de tarjeta son incorrectos");
        }

        if (pago.getMonto() == null || pago.getMonto() <= 0) {
            throw new excepcionTransferencia("Error: El monto debe ser mayor a 0");
        }

        if (!pago.getFechaHora().equals(fechaHoy)) {
            throw new excepcionTransferencia("Error: La fecha del pago debe ser la del dia de hoy");
        }

        System.out.println("Procesando pago de: $" + pago.getMonto() + "con tarjeta");
        System.out.println("Titular: " + tarjeta.getTitular());

        boolean pagoExitoso = true;

        if (pagoExitoso) {
            pago.setEstado("EXITOSO");
            System.out.println("Pago realizando con exito, " + pago.getEstado());
        } else {
            pago.setEstado("FALLIDO");
            System.out.println("Pago no realizado, " + pago.getEstado());
        }
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
        if (mercadoPago.getTitular() == null || mercadoPago.getTitular().isEmpty() || mercadoPago.getTitular().isBlank()) {
            return false;
        }
        String formatoCorreo = "^[\\w.-]+@[a-zA-Z\\d,-]+\\.[a-zA-Z]{2,6}$";
        if (!mercadoPago.getCorreo().matches(formatoCorreo)) {
            return false;
        }
        
        if (mercadoPago.getClienteID() == null) {
            return false;
        }

        return true;

    }

    @Override
    public boolean validarTarjeta(TarjetaDTO tarjeta) {
        if (tarjeta == null) {
            return false;
        }

        if (tarjeta.getTitular() == null || tarjeta.getNumeroTarjeta() == null || tarjeta.getFechaVencimiento() == null || tarjeta.getCvv() == null) {
            return false;
        }

        if (tarjeta.getTitular().isBlank()) {
            return false;
        }

        if (tarjeta.getFechaVencimiento().before(new Date())) {
            return false;
        }

        String cvv = String.valueOf(tarjeta.getCvv());
        if (cvv.length() < 3 || cvv.length() > 4) {
            return false;
        }

        if (!tarjeta.getNumeroTarjeta().matches("\\d+")) {
            return false;
        }

        int longitudNumeroTarjeta = tarjeta.getNumeroTarjeta().length();
        if (longitudNumeroTarjeta < 13 || longitudNumeroTarjeta > 16) {
            return false;
        }

        return true;
    }

    @Override
    public boolean consultarEstadoPago(PagoDTO pago) {
        if (pago == null || pago.getEstado() == null) {
            return false;
        }

        String estado = pago.getEstado().toUpperCase();

        return estado.equals("EXITOSO");
    }
}
