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
    
    private String idFuncion; // Id de la funcion a la que pertenecen

    private String numAsiento; // Asiento al que hace referencia el asientoFuncion
    
    private String numSala; // Numero de sala a l que pertenece el asiento original

    private Boolean disponibilidad; // Disponibilidad del asiento durante la funcion

    private String idCliente; // Id del cliente que tiene reservado el asiento, este puede ser nulo

    /**
     * Constructor vacio
     */
    public AsientoFuncion() {
    }
    /**
     * Constructor con todos los atributos
     * @param idAsientoFuncion
     * @param idFuncion
     * @param numAsiento
     * @param numSala
     * @param disponibilidad
     * @param idCliente
     */
    public AsientoFuncion(ObjectId idAsientoFuncion, String idFuncion, String numAsiento, String numSala, Boolean disponibilidad, String idCliente) {
        this.idAsientoFuncion = idAsientoFuncion;
        this.idFuncion = idFuncion;
        this.numAsiento = numAsiento;
        this.numSala = numSala;
        this.disponibilidad = disponibilidad;
        this.idCliente = idCliente;
    }
    /**
     * Constructor con todos los atributos sin el id
     * @param idFuncion
     * @param numAsiento
     * @param numSala
     * @param disponibilidad
     * @param idCliente
     */
    public AsientoFuncion(String idFuncion, String numAsiento, String numSala, Boolean disponibilidad, String idCliente) {    
        this.idFuncion = idFuncion;
        this.numAsiento = numAsiento;
        this.numSala = numSala;
        this.disponibilidad = disponibilidad;
        this.idCliente = idCliente;
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
    public String getIdFuncion() {
        return idFuncion;
    }
    /**
     * Metodo para guardar la Funcion a la que pertenece el AsientoFuncion 
     * @param idFuncion
     */
    public void setIdFuncion(String idFuncion) {
        this.idFuncion = idFuncion;
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
     * Metodo para obtener el numero de la sala al que pertenece el asiento original
     * @return 
     */
    public String getNumSala() {
        return numSala;
    }
    /**
     * Metodo para guardar el numero de la sala al que pertenece el asiento original
     * @param numSala 
     */
    public void setNumSala(String numSala) {
        this.numSala = numSala;
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
    public String getIdCliente() {
        return idCliente;
    }
    /** 
     * Metodo para guardar el Cliente al que esta reservado el asiento 
     * @param idCliente
     */
    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    
    /**
     * ToString de AsientoFuncion
     * @return 
     */
    @Override
    public String toString() {
        return "AsientoFuncion{" + 
                "idAsientoFuncion=" + idAsientoFuncion + 
                ", idFuncion=" + idFuncion + 
                ", numAsiento=" + numAsiento + 
                ", disponibilidad=" + disponibilidad + 
                ", idCliente=" + idCliente + 
                '}';
    }
}
