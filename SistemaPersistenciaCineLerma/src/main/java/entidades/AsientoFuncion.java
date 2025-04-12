/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

/**
 *
 * @author Ramon Valencia
 */
public class AsientoFuncion {

    private Long idAsientoFuncion;

    private Funcion funcion;

    private Sala sala;

    private Boolean disponibilidad;

    private Cliente cliente;

    public AsientoFuncion() {
    }

    public AsientoFuncion(Long idAsientoFuncion, Funcion funcion, Sala sala, Boolean disponibilidad, Cliente cliente) {
        this.idAsientoFuncion = idAsientoFuncion;
        this.funcion = funcion;
        this.sala = sala;
        this.disponibilidad = disponibilidad;
        this.cliente = cliente;
    }

    public AsientoFuncion(Funcion funcion, Sala sala, Boolean disponibilidad, Cliente cliente) {
        this.funcion = funcion;
        this.sala = sala;
        this.disponibilidad = disponibilidad;
        this.cliente = cliente;
    }

    public Long getIdAsientoFuncion() {
        return idAsientoFuncion;
    }

    public void setIdAsientoFuncion(Long idAsientoFuncion) {
        this.idAsientoFuncion = idAsientoFuncion;
    }

    public Funcion getFuncion() {
        return funcion;
    }

    public void setFuncion(Funcion funcion) {
        this.funcion = funcion;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public Boolean getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(Boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    @Override
    public String toString() {
        return "AsientoFuncion{"
                + "idAsientoFuncion=" + idAsientoFuncion
                + ", funcion=" + funcion
                + ", sala=" + sala
                + ", disponibilidad=" + disponibilidad
                + ", cliente=" + cliente
                + '}';
    }

}
