/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * @author Ramon Valencia
 */
public class PagoDTO {
    
    private Double monto;
    
    private LocalDateTime fechaHora;
    
    private Boolean estado;
    
    /**
     * 
     */
    public PagoDTO() {
    }
    
    /**
     * 
     * @param monto
     * @param fechaHora
     * @param estado 
     */
    public PagoDTO(Double monto, LocalDateTime fechaHora, Boolean estado) {    
        this.monto = monto;
        this.fechaHora = fechaHora;
        this.estado = estado;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    
}
