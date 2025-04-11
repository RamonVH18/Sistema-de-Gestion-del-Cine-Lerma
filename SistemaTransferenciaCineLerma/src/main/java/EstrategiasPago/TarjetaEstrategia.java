/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EstrategiasPago;

import DTOs.PagoDTO;
import DTOs.TarjetaDTO;
import Excepciones.PagoException;
import Excepciones.ValidarCuentaException;
import gestionPagos.IEstrategiaPago;
import java.util.Date;

/**
 *
 * @author Abraham Coronel Bringas
 */
public class TarjetaEstrategia implements IEstrategiaPago {

    private final TarjetaDTO tarjeta;

    public TarjetaEstrategia(TarjetaDTO tarjeta) {
        this.tarjeta = tarjeta;
    }

    @Override
    public Boolean procesarPago(PagoDTO pago) throws PagoException, ValidarCuentaException {
        if (pago.getMonto() <= 0) {
            throw new PagoException("Error: El monto es menor o igual a 0");
        }
        if (tarjeta.getFechaVencimiento().before(new Date())) {
            throw new PagoException("Error: La tarjeta esta vencida");
        }
        if (pago.getMonto() > tarjeta.getSaldo()) {
            throw new PagoException("Error: El saldo es insuficiente");
        }

        System.out.println("Procesando pago con tarjeta: " + tarjeta.getNumeroTarjeta());
        return true;
    }

    @Override
    public void actualizarSaldo(PagoDTO pago) {
        tarjeta.setSaldo(tarjeta.getSaldo() - pago.getMonto());
    }

}
