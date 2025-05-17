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

    private String sala;

    private String nombre;

    private LocalDateTime fechaHora;

    private double precio;

    private String empleado;

    /**
     * Constructor vacio
     */
    public FuncionDTO() {
    }

    /**
     * Constructor con todos los atributos y con id
     *
     * @param idFuncion
     * @param sala
     * @param nombre
     * @param fechaHora
     * @param precio
     * @param empleado
     */
    public FuncionDTO(String idFuncion, String sala, String nombre, LocalDateTime fechaHora, double precio, String empleado) {
        this.idFuncion = idFuncion;
        this.sala = sala;
        this.nombre = nombre;
        this.fechaHora = fechaHora;
        this.precio = precio;
        this.empleado = empleado;
    }

    /**
     * Constructor con todos los atributos sin el id
     *
     * @param sala
     * @param nombre
     * @param fechaHora
     * @param precio
     * @param empleado
     */
    public FuncionDTO(String sala, String nombre, LocalDateTime fechaHora, double precio, String empleado) {
        this.sala = sala;
        this.nombre = nombre;
        this.fechaHora = fechaHora;
        this.precio = precio;
        this.empleado = empleado;
    }

    public FuncionDTO(String idFuncion, String sala, String nombre, LocalDateTime fechaHora, double precio) {
        this.idFuncion = idFuncion;
        this.sala = sala;
        this.nombre = nombre;
        this.fechaHora = fechaHora;
        this.precio = precio;
    }

    public FuncionDTO(String sala, String nombre, LocalDateTime fechaHora, double precio) {
        this.sala = sala;
        this.nombre = nombre;
        this.fechaHora = fechaHora;
        this.precio = precio;
    }

    /**
     *
     * @return
     */
    public String getId() {
        return idFuncion;
    }

    /**
     *
     * @param id
     */
    public void setId(String idFuncion) {
        this.idFuncion = idFuncion;
    }

    /**
     *
     * @return
     */
    public String getSala() {
        return sala;
    }

    /**
     *
     * @param sala
     */
    public void setSala(String sala) {
        this.sala = sala;
    }

    /**
     *
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     *
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     *
     * @return
     */
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    /**
     *
     * @param fechaHora
     */
    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    /**
     *
     * @return
     */
    public double getPrecio() {
        return precio;
    }

    /**
     *
     * @param precio
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getIdFuncion() {
        return idFuncion;
    }

    public void setIdFuncion(String idFuncion) {
        this.idFuncion = idFuncion;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

}
