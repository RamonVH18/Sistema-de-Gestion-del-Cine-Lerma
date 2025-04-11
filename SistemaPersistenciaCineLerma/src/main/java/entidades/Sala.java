/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

/**
 *
 * @author Ramon Valencia
 */
public class Sala {
    
    private Long idSala;
    
    private Integer numAsientos;
    
    private Integer numSala;
    
    private Boolean estado;

    public Sala() {
    }

    public Sala(Long idSala, Integer numAsientos, Integer numSala, Boolean estado) {
        this.idSala = idSala;
        this.numAsientos = numAsientos;
        this.numSala = numSala;
        this.estado = estado;
    }

    public Sala(Integer numAsientos, Integer numSala, Boolean estado) {
        this.numAsientos = numAsientos;
        this.numSala = numSala;
        this.estado = estado;
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

    @Override
    public String toString() {
        return "Sala{" + "idSala=" + idSala + ", numAsientos=" + numAsientos + ", numSala=" + numSala + ", estado=" + estado + '}';
    }
    
    
}
