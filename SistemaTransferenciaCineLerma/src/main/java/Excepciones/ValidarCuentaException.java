/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Excepciones;

/**
 *
 * @author Abraham Coronel Bringas
 */
//Clase de excepcion en general para todo el subsistema
/**
 * Clase de excepción general para la validacion de cuentas Esta excepción se
 * utiliza para indicar errores relacionados con la validación de cuentas.
 */
public class ValidarCuentaException extends Exception {

    /**
     * Constructor vacio que inicializa la excepcion por defecto
     *
     */
    public ValidarCuentaException() {
    }

    /**
     * Constructor que inicializa la excepción con un mensaje específico.
     *
     * @param message el mensaje que describe la excepción.
     */
    public ValidarCuentaException(String message) {
        super(message);
    }

    /**
     * Constructor que inicializa la excepción con un mensaje especifico y una
     * causa.
     *
     * @param message el mensaje que describe la excepción.
     * @param cause la causa de la excepción (otra excepcion causo esta).
     */
    public ValidarCuentaException(String message, Throwable cause) {
        super(message, cause);
    }

}
