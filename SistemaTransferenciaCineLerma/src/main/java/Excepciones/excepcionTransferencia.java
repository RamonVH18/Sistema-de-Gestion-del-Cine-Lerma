/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Excepciones;

/**
 *
 * @author Abraham Coronel Bringas
 */
public class excepcionTransferencia extends Exception {

    public excepcionTransferencia() {
    }

    public excepcionTransferencia(String message) {
        super(message);
    }

    public excepcionTransferencia(String message, Throwable cause) {
        super(message, cause);
    }

}
