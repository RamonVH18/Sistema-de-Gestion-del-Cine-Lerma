/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import enums.Cargo;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

/**
 *
 * @author isaac
 */
public class Empleado {
    
    @BsonId
    private ObjectId id;

    private String nombre;

    private String apellidoP;

    private String apellidoM;

    private String correoE;

    private String telefono;

    private LocalDateTime fechaNacimiento;

    private Cargo cargo;

    private double sueldo;
    
    private boolean activo; // para habilitar o desabilitar si esta activo el empleado
    
    private LocalDate fechaRegistro;
    
    // DIRECCION DEL EMPLEADO
    private String calle;
    private String colonia;
    private String numExterior;

    // constructor vacio
    public Empleado() {
        
        this.activo = true; // por defecto esta activo el empleado cuando se registra
        this.fechaRegistro = LocalDate.now(); // se registra con la fecha actual

    }

    // constructor con todo 

    public Empleado(ObjectId id, String nombre, String apellidoP, String apellidoM, String correoE, String telefono, LocalDateTime fechaNacimiento, Cargo cargo, double sueldo, boolean activo, LocalDate fechaRegistro, String calle, String colonia, String numExterior) {
        this.id = id;
        this.nombre = nombre;
        this.apellidoP = apellidoP;
        this.apellidoM = apellidoM;
        this.correoE = correoE;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.cargo = cargo;
        this.sueldo = sueldo;
        this.activo = activo;
        this.fechaRegistro = fechaRegistro;
        this.calle = calle;
        this.colonia = colonia;
        this.numExterior = numExterior;
    }
  

    //constructor con todo menos id
    public Empleado(String nombre, String apellidoP, String apellidoM, String correoE, String telefono, LocalDateTime fechaNacimiento, Cargo cargo, double sueldo, boolean activo, LocalDate fechaRegistro, String calle, String colonia, String numExterior) {    
        this.nombre = nombre;
        this.apellidoP = apellidoP;
        this.apellidoM = apellidoM;
        this.correoE = correoE;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.cargo = cargo;
        this.sueldo = sueldo;
        this.activo = activo;
        this.fechaRegistro = fechaRegistro;
        this.calle = calle;
        this.colonia = colonia;
        this.numExterior = numExterior;
    }

    //getters y setters
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoP() {
        return apellidoP;
    }

    public void setApellidoP(String apellidoP) {
        this.apellidoP = apellidoP;
    }

    public String getApellidoM() {
        return apellidoM;
    }

    public void setApellidoM(String apellidoM) {
        this.apellidoM = apellidoM;
    }

    public String getCorreoE() {
        return correoE;
    }

    public void setCorreoE(String correoE) {
        this.correoE = correoE;
    }

    public LocalDateTime getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDateTime fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getNumExterior() {
        return numExterior;
    }

    public void setNumExterior(String numExterior) {
        this.numExterior = numExterior;
    }

    @Override
    public String toString() {
        return "Empleado{" + "id=" + id + ", nombre=" + nombre + ", apellidoP=" + apellidoP + ", apellidoM=" + apellidoM + ", correoE=" + correoE + ", telefono=" + telefono + ", fechaNacimiento=" + fechaNacimiento + ", cargo=" + cargo + ", sueldo=" + sueldo + ", activo=" + activo + ", fechaRegistro=" + fechaRegistro + ", calle=" + calle + ", colonia=" + colonia + ", numExterior=" + numExterior + '}';
    }

    

    

}
