/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Excepciones.empleados;

/**
 *
 * @author isaac
 */
public class ValidacionEmpleadoException extends Exception {

    public ValidacionEmpleadoException(String message) {
        super(message);
    }

    public ValidacionEmpleadoException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
