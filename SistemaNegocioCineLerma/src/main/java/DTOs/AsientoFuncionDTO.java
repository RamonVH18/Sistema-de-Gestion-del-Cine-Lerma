/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

import entidades.Cliente;
import entidades.Funcion;

/**
 *
 * @author Ramon Valencia
 */
public class AsientoFuncionDTO {

    private Funcion funcion;
    private String asiento;
    private boolean disponibilidad;
    private Cliente cliente;

    /**
     * Constructor vacio
     */
    public AsientoFuncionDTO() {
    }

    /**
     * Constructor con todos los metodos
     *
     * @param funcion
     * @param asiento
     * @param disponibilidad
     * @param cliente
     */
    public AsientoFuncionDTO(Funcion funcion, String asiento, boolean disponibilidad, Cliente cliente) {
        this.funcion = funcion;
        this.asiento = asiento;
        this.disponibilidad = disponibilidad;
        this.cliente = cliente;
    }

    /**
     * Getters y setters
     */
    public Funcion getFuncion() {
        return funcion;
    }

    /**
     *
     * @param funcion
     */
    public void setFuncion(Funcion funcion) {
        this.funcion = funcion;
    }

    /**
     *
     * @return
     */
    public String getAsiento() {
        return asiento;
    }

    /**
     *
     * @param asiento
     */
    public void setAsiento(String asiento) {
        this.asiento = asiento;
    }

    /**
     *
     * @return
     */
    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    /**
     *
     * @param disponibilidad
     */
    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    /**
     *
     * @return
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     *
     * @param cliente
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
