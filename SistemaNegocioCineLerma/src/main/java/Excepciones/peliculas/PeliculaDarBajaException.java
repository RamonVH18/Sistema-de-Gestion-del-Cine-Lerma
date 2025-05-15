/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Excepciones.peliculas;

/**
 *
 * @author Daniel M
 */
public class PeliculaDarBajaException extends Exception {

    public PeliculaDarBajaException(String message) {
        super(message);
    }

    public PeliculaDarBajaException(String message, Throwable cause) {
        super(message, cause);
    }

}
