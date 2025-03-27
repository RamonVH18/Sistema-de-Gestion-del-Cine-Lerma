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
 * Clase de excepción general para la adicion de cuentas. Esta excepción se
 * utiliza para indicar errores relacionados con la adicion de cuentas.
 */
public class AgregarCuentaException extends Exception {

    /**
     * Constructor por defecto.
     */
    public AgregarCuentaException() {
    }

    /**
     * Constructor que inicializa la excepcion con un mensaje especifico.
     *
     * @param message el mensaje que describe la excepción.
     */
    public AgregarCuentaException(String message) {
        super(message);
    }

    /**
     * Constructor que inicializa la excepcion con un mensaje especifico y una
     * causa.
     *
     * @param message el mensaje que describe la excepcion.
     * @param cause la causa de la excepción (otra excepción que causo esta).
     */
    public AgregarCuentaException(String message, Throwable cause) {
        super(message, cause);
    }

}
