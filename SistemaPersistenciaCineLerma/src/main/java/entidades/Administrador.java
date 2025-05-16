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
 * @author sonic
 */

// La anotación @BsonDiscriminator sirve para indicar a MongoDB cómo distinguir esta subclase en la base de datos.
// key = "rol": el campo del documento BSON que almacenará el tipo de documento (en este caso, "rol").
// value = "CLIENTE": el valor asignado a ese campo para identificar los documentos que son de la clase Cliente.
@BsonDiscriminator(key = "rol", value = "ADMINISTRADOR")
public class Administrador extends Usuario { //Administrador extiende de la entidad Usuario, ademas tiene sus atributos propios, en este caso un administrador tiene RFC
    
    //RFC de un administrador, el cual tambien es un identificador unico irrepetible
    private String RFC;

    public Administrador() { //Constructor vacio para mongoDB
    }

    //Super constructor con los atributos de usuario, los de administrador y el id de usuario
    public Administrador(String RFC, ObjectId idUsuario, String nombreDeUsuario, String contrasenia, String nombre, String apellidoPaterno, String apellidoMaterno, String correoElectronico, LocalDateTime fechaNacimiento, String telefono, EstadoUsuario estado, Rol rol) {
        super(idUsuario, nombreDeUsuario, contrasenia, nombre, apellidoPaterno, apellidoMaterno, correoElectronico, fechaNacimiento, telefono, estado, rol);
        this.RFC = RFC;
    }

    //Super constructor con los atributos de usuario, los de administrador sin el id de usuario
    public Administrador(String RFC, String nombreDeUsuario, String contrasenia, String nombre, String apellidoPaterno, String apellidoMaterno, String correoElectronico, LocalDateTime fechaNacimiento, String telefono, EstadoUsuario estado, Rol rol) {
        super(nombreDeUsuario, contrasenia, nombre, apellidoPaterno, apellidoMaterno, correoElectronico, fechaNacimiento, telefono, estado, rol);
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
