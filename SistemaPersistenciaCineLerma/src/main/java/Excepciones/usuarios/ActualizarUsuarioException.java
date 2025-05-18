/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Excepciones.usuarios;

/**
 *
 * @author sonic
 */
/**
 * Excepción personalizada que se lanza cuando ocurre un error al intentar actualizar un usuario.
 */
public class ActualizarUsuarioException extends Exception {

    public ActualizarUsuarioException(String message) {
        super(message);
    }

    public ActualizarUsuarioException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
