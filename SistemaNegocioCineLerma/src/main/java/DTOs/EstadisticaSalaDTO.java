/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

/**
 *
 * @author Ramon Valencia
 */
public class EstadisticaSalaDTO {
    
    private String numSala;
    private Integer capacidad;
    private Double totalGanado;
    private Integer asientosVendidos;
    private Integer funcionesRealizadas;

    public EstadisticaSalaDTO() {
    }

    public EstadisticaSalaDTO(String numSala, Integer capacidad, Double totalGanado, Integer asientosVendidos, Integer funcionesRealizadas) {
        this.numSala = numSala;
        this.capacidad = capacidad;
        this.totalGanado = totalGanado;
        this.asientosVendidos = asientosVendidos;
        this.funcionesRealizadas = funcionesRealizadas;
    }

    public String getNumSala() {
        return numSala;
    }

    public void setNumSala(String numSala) {
        this.numSala = numSala;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public Double getTotalGanado() {
        return totalGanado;
    }

    public void setTotalGanado(Double totalGanado) {
        this.totalGanado = totalGanado;
    }

    public Integer getAsientosVendidos() {
        return asientosVendidos;
    }

    public void setAsientosVendidos(Integer asientosVendidos) {
        this.asientosVendidos = asientosVendidos;
    }

    public Integer getFuncionesRealizadas() {
        return funcionesRealizadas;
    }

    public void setFuncionesRealizadas(Integer funcionesRealizadas) {
        this.funcionesRealizadas = funcionesRealizadas;
    }
}
