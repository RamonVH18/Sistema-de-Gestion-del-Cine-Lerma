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
 * Excepción personalizada que se lanza cuando ocurren errores durante el proceso de 
 * validación de credenciales de un usuario en el sistema.
 * Esta excepción puede ser utilizada para manejar situaciones como:
 * Credenciales incorrectas (usuario o contraseña inválidos)
 * Usuario no encontrado en el sistema
 * Errores de conexión con la base de datos durante la validación
 *
 */
public class ValidarUsuarioException extends Exception {

    public ValidarUsuarioException(String message) {
        super(message);
    }

    public ValidarUsuarioException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
