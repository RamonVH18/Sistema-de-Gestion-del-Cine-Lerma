/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Excepciones.peliculas;

/**
 *
 * @author Daniel M
 */
public class PeliculasActivasInactivasException extends Exception {

    public PeliculasActivasInactivasException(String message) {
        super(message);
    }

    public PeliculasActivasInactivasException(String message, Throwable cause) {
        super(message, cause);
    }

}
