/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import enums.EstadoUsuario;
import enums.Rol;
import java.time.LocalDateTime;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.types.ObjectId;

/**
 *
 * @author Ramon Valencia
 */


// La anotación @BsonDiscriminator sirve para indicar a MongoDB cómo distinguir esta subclase en la base de datos.
// key = "rol": el campo del documento BSON que almacenará el tipo de documento (en este caso, "rol").
// value = "CLIENTE": el valor asignado a ese campo para identificar los documentos que son de la clase Cliente.
@BsonDiscriminator(key = "rol", value = "CLIENTE") //BsonDiscriminator, 
public class Cliente extends Usuario { //Cliente extiende de la entidad Usuario, ademas tiene sus atributos propios, en este caso un cliente tiene los datos de su direccion

    //Atributo para el nombre de la calle del domicilio del cliente
    private String calle;

    //Codigo postal del cliente
    private String CP;

    //Numero de domicilio exterior del cliente
    private String numero;

    //Constructor vacio para mongoDB
    public Cliente() {
    }

    //Super constructor que incluye tanto los atributos de Usuario como los de cliente mas el id de usuario
    public Cliente(String calle, String CP, String numero, ObjectId idUsuario, String nombreDeUsuario, String contrasenia, String nombre, String apellidoPaterno, String apellidoMaterno, String correoElectronico, LocalDateTime fechaNacimiento, String telefono, EstadoUsuario estado, Rol rol) {
        super(idUsuario, nombreDeUsuario, contrasenia, nombre, apellidoPaterno, apellidoMaterno, correoElectronico, fechaNacimiento, telefono, estado, rol);
        this.calle = calle;
        this.CP = CP;
        this.numero = numero;
    }
    
    //Super constructor que incluye los atributos de Usuario y los de cliente sin el id de usuario

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
        return "Cliente{"
                + "idUsuario=" + getIdUsuario()
                + ", nombreDeUsuario=" + getNombreDeUsuario()
                + ", contrasenia=" + getContrasenia()
                + ", nombre=" + getNombre()
                + ", apellidoPaterno=" + getApellidoPaterno()
                + ", apellidoMaterno=" + getApellidoMaterno()
                + ", correoElectronico=" + getCorreoElectronico()
                + ", fechaNacimiento=" + getFechaNacimiento()
                + ", telefono=" + getTelefono()
                + ", estado=" + getEstado()
                + ", rol=" + getRol()
                + ", calle=" + calle
                + ", CP=" + CP
                + ", numero=" + numero
                + '}';
    }

}
