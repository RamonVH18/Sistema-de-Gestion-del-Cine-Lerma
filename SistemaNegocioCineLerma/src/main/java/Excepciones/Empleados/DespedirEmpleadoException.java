/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Excepciones.Empleados;

/**
 *
 * @author isaac
 */
public class DespedirEmpleadoException extends Exception {
    
    public DespedirEmpleadoException(String message) {
        super(message);
    }

    public DespedirEmpleadoException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
