/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Ramon Valencia
 */
public class FuncionAsientosDTO {
    
    private List<AsientoFuncionDTO> Asientos;
    private Date fechaHora; 
    /**
     * Constructor vacio
     */
    public FuncionAsientosDTO() {
    }
    /**
     * Constructor con todos los atributos
     * @param Asientos
     * @param fechaHora 
     */
    public FuncionAsientosDTO(List<AsientoFuncionDTO> Asientos, Date fechaHora) {
        this.Asientos = Asientos;
        this.fechaHora = fechaHora;
    }
    /**
     * 
     * @return 
     */
    public List<AsientoFuncionDTO> getAsientos() {
        return Asientos;
    }
    /**
     * 
     * @param Asientos 
     */
    public void setAsientos(List<AsientoFuncionDTO> Asientos) {
        this.Asientos = Asientos;
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
    
}
