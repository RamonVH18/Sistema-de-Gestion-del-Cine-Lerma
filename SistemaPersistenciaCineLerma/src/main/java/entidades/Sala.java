/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import enums.EstadoSala;
import java.util.ArrayList;
import java.util.List;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

/**
 * Clase Sala que hace referencia a una sala de cine
 * @author Ramon Valencia
 */
public class Sala {
   
    @BsonId
    private ObjectId idSala; // Id de la sala

    private Integer numAsientos; // Numero de asientos que tiene la sala

    private String numSala; // Numero de la sala 

    private EstadoSala estado; // Estado en el que se encuentra la sala

    private List<Asiento> asientos; // Lista de asientos que tiene la sala
    /**
     * Constructor vacio
     */
    public Sala() {
    }
    /**
     * Constructor con todos los atributos
     * @param idSala
     * @param numAsientos
     * @param numSala
     * @param estado 
     */
    public Sala(ObjectId idSala, Integer numAsientos, String numSala, EstadoSala estado) {
        this.idSala = idSala;
        this.numAsientos = numAsientos;
        this.numSala = numSala;
        this.estado = estado;
        this.asientos = new ArrayList<>();
    }
    /**
     * Constructor con todos los atributos exceptuando el id
     * @param numAsientos
     * @param numSala
     * @param estado 
     */
    public Sala(Integer numAsientos, String numSala, EstadoSala estado) {
        this.numAsientos = numAsientos;
        this.numSala = numSala;
        this.estado = estado;
        this.asientos = new ArrayList<>();
    }
    /**
     * Metodo para obtener el id de la sala
     * @return 
     */
    public ObjectId getIdSala() {
        return idSala;
    }
    /**
     * Metodo para guardar el id de la sala
     * @param idSala 
     */
    public void setIdSala(ObjectId idSala) {
        this.idSala = idSala;
    }
    
    /**
     * Metodo para obtener el numero de asientos de la sala
     * @return 
     */
    public Integer getNumAsientos() {
        return numAsientos;
    }
    /**
     * Metodo para guardar el numero de asientos de la sala
     * @param numAsientos 
     */
    public void setNumAsientos(Integer numAsientos) {
        this.numAsientos = numAsientos;
    }
    /**
     * Metodo para obtener el numero de la sala
     * @return 
     */
    public String getNumSala() {
        return numSala;
    }
    /**
     * Metodo para guardar el numero de la sala
     * @param numSala 
     */
    public void setNumSala(String numSala) {
        this.numSala = numSala;
    }
    /**
     * Metodo para obtener el estado de la sala
     * @return 
     */
    public EstadoSala getEstado() {
        return estado;
    }
    /**
     * Metodo para guardar el estado de la sala
     * @param estado 
     */
    public void setEstado(EstadoSala estado) {
        this.estado = estado;
    }
    /**
     * Metodo para obtener la lista de asientos de la sala
     * @return 
     */
    public List<Asiento> getAsientos() {
        return asientos;
    }
    /**
     * Metodo para guardar la lista de asientos de la sala
     * @param asientos 
     */
    public void setAsientos(List<Asiento> asientos) {
        this.asientos = asientos;
    }
    /** 
     * ToString de la Sala
     * @return 
     */
    @Override
    public String toString() {
        return "Sala{"
                + "idSala=" + idSala
                + ", numAsientos=" + numAsientos
                + ", numSala=" + numSala
                + ", estado=" + estado
                + ", asientos=" + asientos
                + '}';
    }

}
