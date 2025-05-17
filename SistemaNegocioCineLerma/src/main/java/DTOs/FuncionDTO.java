/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

import java.time.LocalDateTime;

/**
 *
 * @author Abraham Coronel Bringas
 */
public class FuncionDTO {

    private String idFuncion;

    private String numSala;

    private String nombrePelicula;

    private LocalDateTime fechaHora;

    private double precio;

    private String idEmpleado;

    /**
     * Constructor vacio
     */
    public FuncionDTO() {
    }

    public FuncionDTO(String idFuncion, String numSala, String nombrePelicula, LocalDateTime fechaHora, double precio, String idEmpleado) {
        this.idFuncion = idFuncion;
        this.numSala = numSala;
        this.nombrePelicula = nombrePelicula;
        this.fechaHora = fechaHora;
        this.precio = precio;
        this.idEmpleado = idEmpleado;
    }

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

    public String getIdFuncion() {
        return idFuncion;
    }

    public void setIdFuncion(String idFuncion) {
        this.idFuncion = idFuncion;
    }

    public String getNumSala() {
        return numSala;
    }

    public void setNumSala(String numSala) {
        this.numSala = numSala;
    }

    public String getNombrePelicula() {
        return nombrePelicula;
    }

    public void setNombrePelicula(String nombrePelicula) {
        this.nombrePelicula = nombrePelicula;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

}
