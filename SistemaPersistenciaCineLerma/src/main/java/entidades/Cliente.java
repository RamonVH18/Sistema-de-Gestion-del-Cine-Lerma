/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import enums.EstadoUsuario;
import enums.Rol;
import java.time.LocalDateTime;
import org.bson.types.ObjectId;

/**
 *
 * @author Ramon Valencia
 */
public class Cliente extends Usuario{

    private String calle;

    private String CP;

    private String numero;

    public Cliente() {
    }

    public Cliente(String calle, String CP, String numero, ObjectId idUsuario, String nombreDeUsuario, String contrasenia, String nombre, String apellidoPaterno, String apellidoMaterno, String correoElectronico, LocalDateTime fechaNacimiento, String telefono, EstadoUsuario estado, Rol rol) {
        super(idUsuario, nombreDeUsuario, contrasenia, nombre, apellidoPaterno, apellidoMaterno, correoElectronico, fechaNacimiento, telefono, estado, rol);
        this.calle = calle;
        this.CP = CP;
        this.numero = numero;
    }

    public Cliente(String calle, String CP, String numero, String nombreDeUsuario, String contrasenia, String nombre, String apellidoPaterno, String apellidoMaterno, String correoElectronico, LocalDateTime fechaNacimiento, String telefono, EstadoUsuario estado, Rol rol) {
        super(nombreDeUsuario, contrasenia, nombre, apellidoPaterno, apellidoMaterno, correoElectronico, fechaNacimiento, telefono, estado, rol);
        this.calle = calle;
        this.CP = CP;
        this.numero = numero;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getCP() {
        return CP;
    }

    public void setCP(String CP) {
        this.CP = CP;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return "Cliente{" + "calle=" + calle + ", CP=" + CP + ", numero=" + numero + '}';
    }

    

    
    
    

    
    
    

    

}
