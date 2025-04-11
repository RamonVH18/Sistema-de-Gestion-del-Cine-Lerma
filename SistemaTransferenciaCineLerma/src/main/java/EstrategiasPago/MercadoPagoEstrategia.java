/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EstrategiasPago;

import DTOs.CuentaMercadoDTO;
import DTOs.PagoDTO;
import Excepciones.PagoException;
import Excepciones.ValidarCuentaException;
import gestionPagos.IEstrategiaPago;

/**
 *
 * @author Abraham Coronel Bringas
 */
public class MercadoPagoEstrategia implements IEstrategiaPago {

    private final CuentaMercadoDTO cuenta;

    public MercadoPagoEstrategia(CuentaMercadoDTO cuenta) {
        this.cuenta = cuenta;
    }

    @Override
    public Boolean procesarPago(PagoDTO pago) throws PagoException, ValidarCuentaException {
        if (pago.getMonto() <= 0) {
            throw new PagoException("Error: El monto es menor o igual a 0");
        }
        if (pago.getMonto() > cuenta.getSaldo()) {
            throw new PagoException("Error: El saldo es insuficiente");
        }

        System.out.println("Procesando pago MercadoPago: " + cuenta.getCorreo());
        return true;
    }

    @Override
    public void actualizarSaldo(PagoDTO pago) {
        cuenta.setSaldo(cuenta.getSaldo() - pago.getMonto());
    }

}
