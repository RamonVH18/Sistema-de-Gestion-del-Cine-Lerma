/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

import entidades.Asiento;
import enums.EstadoSala;
import java.util.List;

/**
 *
 * @author Ramon Valencia
 */
public class SalaViejaDTO {
    
    
    private String numSala;
    private Integer numAsientos;
    private EstadoSala estado;
    private List<AsientoDTO> asientos;

    public SalaViejaDTO() {
    }
    
    public SalaViejaDTO(String numSala, Integer numAsientos, EstadoSala estado, List<AsientoDTO> asientos) {
        this.numSala = numSala;
        this.numAsientos = numAsientos;
        this.estado = estado;
        this.asientos = asientos;
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

    public List<AsientoDTO> getAsientos() {
        return asientos;
    }

    public void setAsientos(List<AsientoDTO> asientos) {
        this.asientos = asientos;
    }
    
    
}
