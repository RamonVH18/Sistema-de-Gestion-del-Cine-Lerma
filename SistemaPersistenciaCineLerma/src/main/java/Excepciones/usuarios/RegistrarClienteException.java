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
 * Excepción personalizada que se lanza cuando ocurre un error durante el registro
 * o la creación de un cliente en el sistema.
 * Esta excepción es útil para manejar fallos específicos en operaciones como:
 * Inserción de un cliente en la base de datos.
 * Conflictos de unicidad (ej: Correo duplicado).
 * Errores de conexión con la base de datos.
 */
public class RegistrarClienteException extends Exception {

    public RegistrarClienteException(String message) {
        super(message);
    }

    public RegistrarClienteException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
