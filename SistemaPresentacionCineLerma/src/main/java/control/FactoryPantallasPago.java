/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import Excepciones.GestionReservaException;
import Excepciones.PresentacionException;
import pantallas.Pagos.PantallaPago;
import pantallas.Pagos.PantallaPagoMercado;
import pantallas.Pagos.PantallaPagoPaypal;
import pantallas.Pagos.PantallaPagoTarjeta;

/**
 *
 * @author Ramon Valencia
 */
public class FactoryPantallasPago {
    /**
     * Factory para crear pantallas de pago
     * 
     */
    public static PantallaPago crearPantallaPago(String tipo) throws GestionReservaException, PresentacionException {
        switch (tipo) {
            case "Mercado" -> {
                return new PantallaPagoMercado();
            }
            case "Paypal" -> {
                return new PantallaPagoPaypal();
            }
            case "Tarjeta" -> {
                return new PantallaPagoTarjeta();
            }
            default -> throw new PresentacionException("Error: El metodo de pago es incorrecto o invalido");
        }
    }
}
