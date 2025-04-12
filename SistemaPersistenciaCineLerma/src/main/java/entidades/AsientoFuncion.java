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

    private Asiento asiento;

    private Boolean disponibilidad;

    private Cliente cliente;

    public AsientoFuncion() {
    }

    public AsientoFuncion(Long idAsientoFuncion, Funcion funcion, Asiento asiento, Boolean disponibilidad, Cliente cliente) {
        this.idAsientoFuncion = idAsientoFuncion;
        this.funcion = funcion;
        this.asiento = asiento;
        this.disponibilidad = disponibilidad;
        this.cliente = cliente;
    }

    public AsientoFuncion(Funcion funcion, Asiento asiento, Boolean disponibilidad, Cliente cliente) {
        this.funcion = funcion;
        this.asiento = asiento;
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

    public Asiento getAsiento() {
        return asiento;
    }

    public void setAsiento(Asiento asiento) {
        this.asiento = asiento;
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
                + ", asiento=" + asiento
                + ", disponibilidad=" + disponibilidad
                + ", cliente=" + cliente
                + '}';
    }

}
