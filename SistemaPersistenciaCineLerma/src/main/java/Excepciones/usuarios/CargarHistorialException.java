/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Excepciones.usuarios;

/**
 *
 * @author sonic
 */
/**
 * Excepción personalizada que se lanza cuando ocurre un error al intentar cargar el historial de compras de un cliente.
 */
public class CargarHistorialException extends Exception {

    public CargarHistorialException(String message) {
        super(message);
    }

    public CargarHistorialException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
