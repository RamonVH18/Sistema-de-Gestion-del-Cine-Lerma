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
 * Excepción personalizada que se lanza cuando ocurre un error al intentar buscar u obtener un administrador
 * esta excepcion puede ocurrir cuando un administrador no se encuentra o cuando hay errores con la conexion a mongo
 */
public class EncontrarAdministradorException extends Exception {

    public EncontrarAdministradorException(String message) {
        super(message);
    }

    public EncontrarAdministradorException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
