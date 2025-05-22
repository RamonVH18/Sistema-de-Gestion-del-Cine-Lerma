/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

/**
 * DTO que se usa par poder obtener las ganancias de una sala desde la base de datos
 * @author Ramon Valencia
 */
public class GananciaSalaDTO {
    
    private String numSala;
    private Integer capacidad;
    private Double totalGanado;
    private Integer asientosVendidos;
    private Integer funcionesRealizadas;
    /**
     * Constructor vacio
     */
    public GananciaSalaDTO() {
    }
    /**
     * Constructor con todos los atributos
     * @param numSala
     * @param capacidad
     * @param totalGanado
     * @param asientosVendidos
     * @param funcionesRealizadas 
     */
    public GananciaSalaDTO(String numSala, Integer capacidad, Double totalGanado, Integer asientosVendidos, Integer funcionesRealizadas) {
        this.numSala = numSala;
        this.capacidad = capacidad;
        this.totalGanado = totalGanado;
        this.asientosVendidos = asientosVendidos;
        this.funcionesRealizadas = funcionesRealizadas;
    }
    /**
     * Metodo para obtenerel numero de la sala
     * @return 
     */
    public String getNumSala() {
        return numSala;
    }
    /**
     * Metodo para guardar el numero de la sala
     * @param numSala 
     */
    public void setNumSala(String numSala) {
        this.numSala = numSala;
    }
    /**
     * Metodo para obtener la capacidad de la sala
     * @return 
     */
    public Integer getCapacidad() {
        return capacidad;
    }
    /**
     * Metodo para guardar la capacidad de la sala
     * @param capacidad 
     */
    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }
    /**
     * Metodo para obtener el total ganado de una sala
     * @return 
     */
    public Double getTotalGanado() {
        return totalGanado;
    }
    /**
     * Metodo para guardar el total ganado de una sala
     * @param totalGanado 
     */
    public void setTotalGanado(Double totalGanado) {
        this.totalGanado = totalGanado;
    }   
    /**
     * Metodo para obtener el numero de asientos vendidos
     * @return 
     */
    public Integer getAsientosVendidos() {
        return asientosVendidos;
    }
    /**
     * Metodo para guardar el numero de asientos vendidos
     * @param asientosVendidos 
     */
    public void setAsientosVendidos(Integer asientosVendidos) {
        this.asientosVendidos = asientosVendidos;
    }
    /**
     * Metodo para obtener el numero de funciones realizadas
     * @return 
     */
    public Integer getFuncionesRealizadas() {
        return funcionesRealizadas;
    }
    /**
     * Metodo para guardar el numero de las funciones realizadas
     * @param funcionesRealizadas 
     */
    public void setFuncionesRealizadas(Integer funcionesRealizadas) {
        this.funcionesRealizadas = funcionesRealizadas;
    }
    
}
