/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Excepciones;

/**
 *
 * @author sonic
 */
/**
 * Clase de excepción general para la validacion de pagos. Esta excepcion se
 * utiliza para indicar errores relacionados con el procesamiento de pagos.
 */
public class PagoException extends Exception {

    /**
     * Constructor por defecto.
     */
    public PagoException() {
    }

    /**
     * Constructor que inicializa la excepcion con un mensaje especifico.
     *
     * @param message el mensaje que describe la excepción.
     */
    public PagoException(String message) {
        super(message);
    }

    /**
     * Constructor que inicializa la excepción con un mensaje especifico y una
     * causa.
     *
     * @param message el mensaje que describe la excepcion.
     * @param cause la causa de la excepción (otra excepción que causo esta).
     */
    public PagoException(String message, Throwable cause) {
        super(message, cause);
    }

}
