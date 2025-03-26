/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

import java.util.Date;

/**
 *
 * @author Ramon Valencia
 */
public class PagoDTO {
    
  private Double monto;
    
    private Date fechaHora;
    
    private String estado;
    
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
    public PagoDTO(Double monto, Date fechaHora, String estado) {
        this.monto = monto;
        this.fechaHora = fechaHora;
        this.estado = estado;
    }
    /**
     * 
     * @return 
     */
    public Double getMonto() {
        return monto;
    }
    /**
     * 
     * @param monto 
     */
    public void setMonto(Double monto) {
        this.monto = monto;
    }
    /**
     * 
     * @return 
     */
    public Date getFechaHora() {
        return fechaHora;
    }
    /**
     * 
     * @param fechaHora 
     */
    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }
    /**
     * 
     * @return 
     */
    public String getEstado() {
        return estado;
    }
    /**
     * 
     * @param estado 
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }
}
