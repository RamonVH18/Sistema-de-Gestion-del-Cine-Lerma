/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import enums.EstadoUsuario;
import enums.Rol;
import java.time.LocalDateTime;

/**
 *
 * @author sonic
 */
public class Administrador extends Usuario {
    
    private String RFC;

    public Administrador() {
    }

    public Administrador(String RFC, ObjectId idUsuario, String nombreDeUsuario, String contrasenia, String nombre, String apellidoPaterno, String apellidoMaterno, LocalDateTime fechaNacimiento, String telefono, EstadoUsuario estado, Rol rol) {
        super(idUsuario, nombreDeUsuario, contrasenia, nombre, apellidoPaterno, apellidoMaterno, fechaNacimiento, telefono, estado, rol);
        this.RFC = RFC;
    }

    public Administrador(String RFC, String nombreDeUsuario, String contrasenia, String nombre, String apellidoPaterno, String apellidoMaterno, LocalDateTime fechaNacimiento, String telefono, EstadoUsuario estado, Rol rol) {
        super(nombreDeUsuario, contrasenia, nombre, apellidoPaterno, apellidoMaterno, fechaNacimiento, telefono, estado, rol);
        this.RFC = RFC;
    }

    public String getRFC() {
        return RFC;
    }

    public void setRFC(String RFC) {
        this.RFC = RFC;
    }

    @Override
    public String toString() {
        return "Administrador{" + "RFC=" + RFC + '}';
    }
    
    
    
}
