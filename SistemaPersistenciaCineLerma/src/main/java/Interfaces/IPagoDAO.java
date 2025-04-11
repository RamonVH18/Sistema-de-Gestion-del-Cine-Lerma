/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import entidades.Funcion;
import entidades.Pago;

/**
 *
 * @author sonic
 */
public interface IPagoDAO {
    
    public Pago registrarPagoExitoso(Pago pago);
    
    public Double calcularMontoTotal(Integer boletos, Funcion funcion);
    
    
}
