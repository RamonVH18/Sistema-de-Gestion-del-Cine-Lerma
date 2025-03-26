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
public class TransferenciaException extends Exception {

    public TransferenciaException() {
    }

    public TransferenciaException(String message) {
        super(message);
    }

    public TransferenciaException(String message, Throwable cause) {
        super(message, cause);
    }

}
