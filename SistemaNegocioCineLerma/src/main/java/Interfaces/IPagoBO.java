/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import DTOs.FuncionDTO;
import DTOs.PagoDTO;
import Excepciones.pagos.PagoValidacionException;
import entidades.Pago;

/**
 *
 * @author sonic
 */
public interface IPagoBO {
    
    public Double calcularMontoTotal(Integer boletos, FuncionDTO funcion) throws PagoValidacionException;
    
    public Pago registrarPagoExitoso(PagoDTO pago) throws PagoValidacionException;
    
}
