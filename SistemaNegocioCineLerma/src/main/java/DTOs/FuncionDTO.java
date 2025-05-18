/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

import java.time.LocalDateTime;

/**
 * Clase DTO (Data Transfer Object) que representa una función de cine para
 * transferencia de datos entre capas. Contiene informacion esencial de la
 * funcion sin lógica de negocio, facilitando la comunicacion entre la interfaz
 * de usuario y servicios.
 *
 * @author Abraham Coronel Bringas
 */
public class FuncionDTO {

    private String idFuncion; // ID unico de la función en formato String

    private String numSala; // Numero identificador de la sala asignada

    private String nombrePelicula;  // Titulo de la pelicula a proyectar

    private LocalDateTime fechaHora; // Fecha y hora de inicio de la funcion

    private double precio; // Precio de la entrada

    private String idEmpleado; // ID del empleado responsable de la funcion

    /**
     * Constructor vacio
     */
    public FuncionDTO() {
    }

    /**
     * Constructor completo con todos los atributos, incluyendo empleado
     * responsable.
     *
     * @param idFuncion ID unico de la funcion (puede ser nulo para nuevas
     * funciones).
     * @param numSala Número de sala asignada.
     * @param nombrePelicula Titulo de la pelicula.
     * @param fechaHora Fecha y hora de inicio programada.
     * @param precio Precio de la entrada en MXN.
     * @param idEmpleado ID del empleado que gestiona la función.
     */
    public FuncionDTO(String idFuncion, String numSala, String nombrePelicula, LocalDateTime fechaHora, double precio, String idEmpleado) {
        this.idFuncion = idFuncion;
        this.numSala = numSala;
        this.nombrePelicula = nombrePelicula;
        this.fechaHora = fechaHora;
        this.precio = precio;
        this.idEmpleado = idEmpleado;
    }

    /**
     * Constructor para creacion de nuevas funciones sin ID (el ID se genera al
     * persistir).
     *
     * @param numSala Numero de sala asignada.
     * @param nombrePelicula Titulo de la película.
     * @param fechaHora Fecha y hora de inicio.
     * @param precio Precio de la entrada.
     * @param idEmpleado ID del empleado responsable.
     */
    public FuncionDTO(String numSala, String nombrePelicula, LocalDateTime fechaHora, double precio, String idEmpleado) {
        this.numSala = numSala;
        this.nombrePelicula = nombrePelicula;
        this.fechaHora = fechaHora;
        this.precio = precio;
        this.idEmpleado = idEmpleado;
    }

    public FuncionDTO(String idFuncion, String numSala, String nombrePelicula, LocalDateTime fechaHora, double precio) {
        this.idFuncion = idFuncion;
        this.numSala = numSala;
        this.nombrePelicula = nombrePelicula;
        this.fechaHora = fechaHora;
        this.precio = precio;
    }

    public FuncionDTO(String numSala, String nombrePelicula, LocalDateTime fechaHora, double precio) {
        this.numSala = numSala;
        this.nombrePelicula = nombrePelicula;
        this.fechaHora = fechaHora;
        this.precio = precio;
    }

    /**
     * @return ID único de la función (String hexadecimal) o null si no está
     * persistida.
     */
    public String getIdFuncion() {
        return idFuncion;
    }

    /**
     * @param idFuncion Establece el ID unico (usado al cargar datos desde la
     * base de datos).
     */
    public void setIdFuncion(String idFuncion) {
        this.idFuncion = idFuncion;
    }

    /**
     * @return Numero identificador de la sala asignada.
     */
    public String getNumSala() {
        return numSala;
    }

    /**
     * @param numSala Establece el numero de sala.
     */
    public void setNumSala(String numSala) {
        this.numSala = numSala;
    }

    /**
     * @return Titulo de la pelicula programada.
     */
    public String getNombrePelicula() {
        return nombrePelicula;
    }

    /**
     * @param nombrePelicula Establece el titulo de la pelicula.
     */
    public void setNombrePelicula(String nombrePelicula) {
        this.nombrePelicula = nombrePelicula;
    }

    /**
     * @return Fecha y hora de inicio en formato LocalDateTime.
     */
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    /**
     * @param fechaHora Establece la fecha y hora de inicio.
     */
    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    /**
     * @return Precio de la entrada en formato double.
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * @param precio Establece el precio de la entrada.
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * @return ID del empleado asociado a la función.
     */
    public String getIdEmpleado() {
        return idEmpleado;
    }

    /**
     * @param idEmpleado Establece el ID del empleado responsable.
     */
    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

}
