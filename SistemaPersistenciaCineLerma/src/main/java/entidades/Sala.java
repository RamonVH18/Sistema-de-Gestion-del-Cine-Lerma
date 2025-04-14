/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ramon Valencia
 */
public class Sala {

    private Long idSala;

    private Integer numAsientos;

    private Integer numSala;

    private Boolean estado;

    private List<Asiento> asientos;

    public Sala() {
    }

    public Sala(Long idSala, Integer numAsientos, Integer numSala, Boolean estado) {
        this.idSala = idSala;
        this.numAsientos = numAsientos;
        this.numSala = numSala;
        this.estado = estado;
        this.asientos = new ArrayList<>();
    }

    public Sala(Integer numAsientos, Integer numSala, Boolean estado) {
        this.numAsientos = numAsientos;
        this.numSala = numSala;
        this.estado = estado;
        this.asientos = new ArrayList<>();
    }

    public Long getIdSala() {
        return idSala;
    }

    public void setIdSala(Long idSala) {
        this.idSala = idSala;
    }

    public Integer getNumAsientos() {
        return numAsientos;
    }

    public void setNumAsientos(Integer numAsientos) {
        this.numAsientos = numAsientos;
    }

    public Integer getNumSala() {
        return numSala;
    }

    public void setNumSala(Integer numSala) {
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
