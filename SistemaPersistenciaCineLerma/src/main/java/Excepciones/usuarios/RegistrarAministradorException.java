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
 * o la creación de un administrador en el sistema.
 * Esta excepción es útil para manejar fallos específicos en operaciones como:
 * Inserción de un administrador en la base de datos.
 * Conflictos de unicidad (ej: RFC duplicado).
 * Errores de conexión con la base de datos.
 */
public class RegistrarAministradorException extends Exception {

    public RegistrarAministradorException(String message) {
        super(message);
    }

    public RegistrarAministradorException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
