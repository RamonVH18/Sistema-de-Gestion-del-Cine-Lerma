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
 * Excepción personalizada que se lanza cuando ocurre un error al intentar buscar u obtener un usuario
 * esta excepcion puede ocurrir cuando un usuario no se encuentra o cuando hay errores con la conexion a mongo
 */
public class ObtenerUsuariosException extends Exception {

    public ObtenerUsuariosException(String message) {
        super(message);
    }

    public ObtenerUsuariosException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
