/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EstrategiasPago;

import DTOs.PagoDTO;
import DTOs.PaypalDTO;
import Excepciones.PagoException;
import Excepciones.ValidarCuentaException;
import gestionPagos.IEstrategiaPago;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author Abraham Coronel Bringas
 */
public class PaypalEstrategia implements IEstrategiaPago {

    private final PaypalDTO paypal;

    public PaypalEstrategia(PaypalDTO paypal) {
        this.paypal = paypal;
    }

    @Override
    public Boolean procesarPago(PagoDTO pago) throws PagoException, ValidarCuentaException {
        if (pago.getMonto() <= 0) {
            throw new PagoException("Error: El monto es menor o igual a 0");
        }
        LocalDate fechaHoy = LocalDate.now();
        if (!pago.getFechaHora().toLocalDate().isEqual(fechaHoy)) { // Usando LocalDateTime
            throw new PagoException("La fecha debe ser la actual");
        }
        if (pago.getMonto() > paypal.getSaldo()) {
            throw new PagoException("Error: El saldo es insuficiente");
        }

        System.out.println("Procesando pago Paypal: " + paypal.getCorreo());
        return true;
    }

    @Override
    public void actualizarSaldo(PagoDTO pago) {
        paypal.setSaldo(paypal.getSaldo() - pago.getMonto());
    }

}
