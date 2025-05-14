/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

import enums.EstadoSala;

/**
 * DTO que representa una sala existente
 * @author Ramon Valencia
 */
public class SalaNuevaDTO {
    
    private String numSala;
    private Integer numAsientos;
    private EstadoSala estado;

    public SalaNuevaDTO() {
    }

    public SalaNuevaDTO(String numSala, Integer numAsientos, EstadoSala estado) {
        this.numSala = numSala;
        this.numAsientos = numAsientos;
        this.estado = estado;
    }

    public String getNumSala() {
        return numSala;
    }

    public void setNumSala(String numSala) {
        this.numSala = numSala;
    }

    public Integer getNumAsientos() {
        return numAsientos;
    }

    public void setNumAsientos(Integer numAsientos) {
        this.numAsientos = numAsientos;
    }

    public EstadoSala getEstado() {
        return estado;
    }

    public void setEstado(EstadoSala estado) {
        this.estado = estado;
    }
        
}
