/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gestionPagos;

import DTOs.PagoDTO;
import Excepciones.PagoException;
import Excepciones.ValidarCuentaException;

/**
 *
 * @author Abraham Coronel Bringas
 */
public interface IEstrategiaPago {

    public Boolean procesarPago(PagoDTO pago) throws PagoException, ValidarCuentaException;

    public void actualizarSaldo(PagoDTO pago);

}
