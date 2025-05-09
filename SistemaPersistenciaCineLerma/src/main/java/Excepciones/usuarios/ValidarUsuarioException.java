/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Excepciones.usuarios;

/**
 *
 * @author sonic
 */
public class ValidarUsuarioException extends Exception {

    public ValidarUsuarioException(String message) {
        super(message);
    }

    public ValidarUsuarioException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
