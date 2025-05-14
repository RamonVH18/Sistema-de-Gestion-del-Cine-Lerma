/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import org.bson.types.ObjectId;

/**
 * Clase AsientoFuncion que hace referencia a un asiento de un funcion en especifica
 * 
 * @author Ramon Valencia
 */
public class AsientoFuncion {

    private ObjectId idAsientoFuncion; // Id del AsientoFuncion
    
    private Funcion funcion; // Id de la funcion a la que pertenecen

    private String numAsiento; // Asiento al que hace referencia el asientoFuncion

    private Boolean disponibilidad; // Disponibilidad del asiento durante la funcion

    private Cliente cliente; // Id del cliente que tiene reservado el asiento, este puede ser nulo

    /**
     * Constructor vacio
     */
    public AsientoFuncion() {
    }
    /**
     * Constructor con todos los atributos
     * @param idAsientoFuncion
     * @param funcion
     * @param numAsiento
     * @param disponibilidad
     * @param cliente
     */
    public AsientoFuncion(ObjectId idAsientoFuncion, Funcion funcion, String numAsiento, Boolean disponibilidad, Cliente cliente) {
        this.idAsientoFuncion = idAsientoFuncion;
        this.funcion = funcion;
        this.numAsiento = numAsiento;
        this.disponibilidad = disponibilidad;
        this.cliente = cliente;
    }
    /**
     * Constructor con todos los atributos sin el id
     * @param funcion
     * @param numAsiento
     * @param disponibilidad
     * @param cliente
     */
    public AsientoFuncion(Funcion funcion, String numAsiento, Boolean disponibilidad, Cliente cliente) {    
        this.funcion = funcion;
        this.numAsiento = numAsiento;
        this.disponibilidad = disponibilidad;
        this.cliente = cliente;
    }

    /**
     * Metodo para obtener el id del AsientoFuncion
     * @return 
     */
    public ObjectId getIdAsientoFuncion() {
        return idAsientoFuncion;
    }
    /**
     * Metodo para guardar el id del AsientoFuncion
     * @param idAsientoFuncion 
     */
    public void setIdAsientoFuncion(ObjectId idAsientoFuncion) {
        this.idAsientoFuncion = idAsientoFuncion;
    }
    /**
     * Metodo para obtener la Funcion a la que pertenece el AsientoFuncion
     * @return 
     */
    public Funcion getFuncion() {
        return funcion;
    }
    /**
     * Metodo para guardar la Funcion a la que pertenece el AsientoFuncion 
     * @param funcion
     */
    public void setFuncion(Funcion funcion) {
        this.funcion = funcion;
    }
    /**
     * Metodo para obtener el Asiento al que hace referencia el asientoFuncion
     * @return 
     */
    public String getNumAsiento() {
        return numAsiento;
    }
    /**
     * Metodo para guardar el asiento al que hace referencia el asientoFuncion
     * @param numAsiento 
     */
    public void setNumAsiento(String numAsiento) {
        this.numAsiento = numAsiento;
    }
    /**
     * Metodo para obtener la disponibilidad del asiento durante la funcion
     * @return 
     */
    public Boolean getDisponibilidad() {
        return disponibilidad;
    }
    /**
     * Metodo para guardar la disponibilidad del asiento durante la funcion
     * @param disponibilidad 
     */
    public void setDisponibilidad(Boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }
    /**
     * Metodo para obtener el Cliente al que esta reservado el asiento
     * @return 
     */
    public Cliente getCliente() {
        return cliente;
    }
    /** 
     * Metodo para guardar el Cliente al que esta reservado el asiento 
     * @param cliente
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return "AsientoFuncion{" + 
                "idAsientoFuncion=" + idAsientoFuncion + 
                ", funcion=" + funcion + 
                ", numAsiento=" + numAsiento + 
                ", disponibilidad=" + disponibilidad + 
                ", cliente=" + cliente + 
                '}';
    }
    /**
     * ToString de AsientoFuncion
     * @return 
     */

}
