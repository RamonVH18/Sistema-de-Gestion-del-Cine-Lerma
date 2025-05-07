/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Excepciones.funciones;

/**
 *
 * @author Ramon Valencia
 */
public class FuncionDatosIncorrectosException extends Exception{

    public FuncionDatosIncorrectosException(String message) {
        super(message);
    }

    public FuncionDatosIncorrectosException(String message, Throwable cause) {
        super(message, cause);
    }

}
