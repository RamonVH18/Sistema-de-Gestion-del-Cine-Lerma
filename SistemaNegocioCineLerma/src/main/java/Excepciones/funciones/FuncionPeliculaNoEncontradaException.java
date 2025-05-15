/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Excepciones.funciones;

/**
 *
 * @author Abraham Coronel Bringas
 */
public class FuncionPeliculaNoEncontradaException extends Exception {

    public FuncionPeliculaNoEncontradaException(String message) {
        super(message);
    }

    public FuncionPeliculaNoEncontradaException(String message, Throwable cause) {
        super(message, cause);
    }

}
