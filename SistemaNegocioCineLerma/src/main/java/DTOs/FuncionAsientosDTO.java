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

    public FuncionAsientosDTO() {
    }

    public FuncionAsientosDTO(List<AsientoFuncionDTO> Asientos, Date fechaHora) {
        this.Asientos = Asientos;
        this.fechaHora = fechaHora;
    }

    public List<AsientoFuncionDTO> getAsientos() {
        return Asientos;
    }

    public void setAsientos(List<AsientoFuncionDTO> Asientos) {
        this.Asientos = Asientos;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    @Override
    public String toString() {
        return "FuncionAsientosDTO{" + 
                "Asientos=" + Asientos + 
                ", fechaHora=" + fechaHora + 
                '}';
    }
    
    
}
