/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Excepciones;

/**
 *
 * @author Daniel M
 */
public class BajaPeliculaException extends Exception {

    public BajaPeliculaException(String message) {
        super(message);
    }

    public BajaPeliculaException(String message, Throwable cause) {
        super(message, cause);
    }
}
