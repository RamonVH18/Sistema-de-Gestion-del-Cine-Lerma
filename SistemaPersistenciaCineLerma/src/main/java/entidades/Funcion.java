/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ramon Valencia
 */
public class Funcion {

    private Long idFuncion;

    private Sala sala;

    private Pelicula pelicula;

    private LocalDateTime fechaHora;

    private Boolean estado;

    private Double precio;

    private List<AsientoFuncion> asientosFuncion;

    public Funcion() {
    }

    public Funcion(Long idFuncion, Sala sala, Pelicula pelicula, LocalDateTime fechaHora, Boolean estado, Double precio) {
        this.idFuncion = idFuncion;
        this.sala = sala;
        this.pelicula = pelicula;
        this.fechaHora = fechaHora;
        this.estado = estado;
        this.precio = precio;
        this.asientosFuncion = new ArrayList<>();
    }

    public Funcion(Sala sala, Pelicula pelicula, LocalDateTime fechaHora, Boolean estado, Double precio) {
        this.sala = sala;
        this.pelicula = pelicula;
        this.fechaHora = fechaHora;
        this.estado = estado;
        this.precio = precio;
        this.asientosFuncion = new ArrayList<>();
    }

    public Long getIdFuncion() {
        return idFuncion;
    }

    public void setIdFuncion(Long idFuncion) {
        this.idFuncion = idFuncion;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Funcion{"
                + "idFuncion=" + idFuncion
                + ", sala=" + sala
                + ", pelicula=" + pelicula
                + ", fechaHora=" + fechaHora
                + ", estado=" + estado
                + ", precio=" + precio
                + ", asientosFuncion=" + asientosFuncion
                + '}';
    }
}
