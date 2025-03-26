/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Excepciones;

/**
 *
 * @author Ramon Valencia
 */
public class PeliculasCargaException extends Exception {

    public PeliculasCargaException(String message) {
        super(message);
    }

    public PeliculasCargaException(String message, Throwable cause) {
        super(message, cause);
    }
    
}