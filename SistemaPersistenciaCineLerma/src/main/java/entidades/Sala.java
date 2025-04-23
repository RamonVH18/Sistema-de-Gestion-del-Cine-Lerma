/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;

/**
 *
 * @author Ramon Valencia
 */
public class Sala {

    private ObjectId idSala;

    private Integer numAsientos;

    private String numSala;

    private Boolean estado;

    private List<Asiento> asientos;

    public Sala() {
    }

    public Sala(ObjectId idSala, Integer numAsientos, String numSala, Boolean estado) {
        this.idSala = idSala;
        this.numAsientos = numAsientos;
        this.numSala = numSala;
        this.estado = estado;
        this.asientos = new ArrayList<>();
    }

    public Sala(Integer numAsientos, String numSala, Boolean estado) {
        this.numAsientos = numAsientos;
        this.numSala = numSala;
        this.estado = estado;
        this.asientos = new ArrayList<>();
    }

    public ObjectId getIdSala() {
        return idSala;
    }

    public void setIdSala(ObjectId idSala) {
        this.idSala = idSala;
    }

    public Integer getNumAsientos() {
        return numAsientos;
    }

    public void setNumAsientos(Integer numAsientos) {
        this.numAsientos = numAsientos;
    }

    public String getNumSala() {
        return numSala;
    }

    public void setNumSala(String numSala) {
        this.numSala = numSala;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public List<Asiento> getAsientos() {
        return asientos;
    }

    public void setAsientos(List<Asiento> asientos) {
        this.asientos = asientos;
    }

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
