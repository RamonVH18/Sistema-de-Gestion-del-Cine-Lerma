/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Excepciones.peliculas;

/**
 *
 * @author Ramon Valencia
 */
public class PeliculaBusquedaException extends Exception{

    public PeliculaBusquedaException(String message) {
        super(message);
    }

    public PeliculaBusquedaException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
