/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.time.LocalDateTime;

/**
 *
 * @author sonic
 */
public class Pago {
    
    private Long idPago;
    
    private Double monto;
    
    private LocalDateTime fechaHora;
    
    private Boolean estado;

    public Pago() {
    }

    public Pago(Long idPago, Double monto, LocalDateTime fechaHora, Boolean estado) {
        this.idPago = idPago;
        this.monto = monto;
        this.fechaHora = fechaHora;
        this.estado = estado;
    }

    public Pago(Double monto, LocalDateTime fechaHora, Boolean estado) {
        this.monto = monto;
        this.fechaHora = fechaHora;
        this.estado = estado;
    }

    public Pago(Double monto) {
        this.monto = monto;
    }
    
    
    
    public Long getIdPago() {
        return idPago;
    }

    public void setIdPago(Long idPago) {
        this.idPago = idPago;
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

    @Override
    public String toString() {
        return "Pago{" + "idPago=" + idPago + ", monto=" + monto + ", fechaHora=" + fechaHora + ", estado=" + estado + '}';
    }
 
    
    
}
