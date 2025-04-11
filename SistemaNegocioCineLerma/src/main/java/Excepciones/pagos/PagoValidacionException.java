/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Excepciones.pagos;

/**
 *
 * @author sonic
 */
public class PagoValidacionException extends Exception{
    public PagoValidacionException(String message) {
        super(message);
    }

    public PagoValidacionException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
