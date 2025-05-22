/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import enums.EstadoUsuario;
import enums.Rol;
import java.time.LocalDateTime;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

/**
 *
 * @author sonic
 */

// La anotación @BsonDiscriminator sirve para indicar a MongoDB cómo distinguir esta subclase en la base de datos.
@BsonDiscriminator
public class Usuario {
    //La entidad usuario funcionara como una clase padre, por lo tanto contiene como atributos los datos basicos de un usuario ya sea admin o cliente

    @BsonId // Indica que este atributo sera el id
    @BsonProperty("_id") //BsonProperty indica a mongo como debe guardar este atributo, mongo guarda los id por default como _id asi que lo indicaremos de esa forma
    private ObjectId idUsuario; //Id del usuario, el cual tambien sera usado por clientes y administradores
    
    private String nombreDeUsuario; //Nombre de usuario, se utilizara como identificador unico
    
    private String contrasenia; //Contraseña del usuario
    
    private String nombre; //Nombre del usuario
    
    private String apellidoPaterno; //Apellidos del usuario
    
    private String apellidoMaterno;
    
    @BsonProperty
    private String correoElectronico; //Correo electronico
    
    @BsonProperty
    private LocalDateTime fechaNacimiento; //Fecha de nacimiento que se maneja con localDateTime
    
    private String telefono; //Numero de telefono string del cliente
    
    private EstadoUsuario estado; //Estado del usuario que puede ser ACTIVO O BLOQUEADO, utiliza un ENUM asi como Rol
    
    private Rol rol; //Rol es un enum importante ya que funcionara como discriminador para que mongoDB pueda manejar la herencia

    public Usuario() { //constructor vacio
    }

    //constructor con todo y id
    public Usuario(ObjectId idUsuario, String nombreDeUsuario, String contrasenia, String nombre, String apellidoPaterno, String apellidoMaterno, String correoElectronico, LocalDateTime fechaNacimiento, String telefono, EstadoUsuario estado, Rol rol) {
        this.idUsuario = idUsuario;
        this.nombreDeUsuario = nombreDeUsuario;
        this.contrasenia = contrasenia;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.correoElectronico = correoElectronico;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.estado = estado;
        this.rol = rol;
    }

    
    //contructor con todo sin id
    public Usuario(String nombreDeUsuario, String contrasenia, String nombre, String apellidoPaterno, String apellidoMaterno, String correoElectronico, LocalDateTime fechaNacimiento, String telefono, EstadoUsuario estado, Rol rol) {
        this.nombreDeUsuario = nombreDeUsuario;
        this.contrasenia = contrasenia;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.correoElectronico = correoElectronico;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.estado = estado;
        this.rol = rol;
    }

    public ObjectId getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(ObjectId idUsuario) {
        this.idUsuario = idUsuario;
    }

    

    public String getNombreDeUsuario() {
        return nombreDeUsuario;
    }

    public void setNombreDeUsuario(String nombreDeUsuario) {
        this.nombreDeUsuario = nombreDeUsuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public LocalDateTime getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDateTime fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public EstadoUsuario getEstado() {
        return estado;
    }

    public void setEstado(EstadoUsuario estado) {
        this.estado = estado;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
    
    @BsonIgnore
    public String getIdString() {
        return idUsuario.toString();
    }
    
    @BsonIgnore
    public void setIdString(String idString) {
        this.idUsuario = new ObjectId(idString);
    }

    @Override
    public String toString() {
        return "Usuario{" + "idUsuario=" + idUsuario + ", nombreDeUsuario=" + nombreDeUsuario + ", contrasenia=" + contrasenia + ", nombre=" + nombre + ", apellidoPaterno=" + apellidoPaterno + ", apellidoMaterno=" + apellidoMaterno + ", correoElectronico=" + correoElectronico + ", fechaNacimiento=" + fechaNacimiento + ", telefono=" + telefono + ", estado=" + estado + ", rol=" + rol + '}';
    }
}
