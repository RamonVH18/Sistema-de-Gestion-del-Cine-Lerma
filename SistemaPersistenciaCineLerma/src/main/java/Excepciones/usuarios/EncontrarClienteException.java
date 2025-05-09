/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Excepciones.usuarios;

/**
 *
 * @author sonic
 */
public class EncontrarClienteException extends Exception {

    public EncontrarClienteException(String message) {
        super(message);
    }

    public EncontrarClienteException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
