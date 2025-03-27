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
public class ValidarCuentaException extends Exception {

    public ValidarCuentaException() {
    }

    public ValidarCuentaException(String message) {
        super(message);
    }

    public ValidarCuentaException(String message, Throwable cause) {
        super(message, cause);
    }

}
