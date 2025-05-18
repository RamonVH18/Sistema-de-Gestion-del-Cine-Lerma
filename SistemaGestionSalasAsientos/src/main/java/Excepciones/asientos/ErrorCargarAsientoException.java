/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Excepciones.asientos;

/**
 *
 * @author Ramon Valencia
 */
public class ErrorCargarAsientoException extends Exception {

    public ErrorCargarAsientoException(String message) {
        super(message);
    }

    public ErrorCargarAsientoException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
