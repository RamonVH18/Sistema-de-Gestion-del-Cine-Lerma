/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

import enums.EstadoSala;

/**
 * DTO que representa una sala nueva
 * @author Ramon Valencia
 */
public class SalaNuevaDTO {
    
    private String numSala;
    private Integer numAsientos;
    private EstadoSala estado;
    /**
     * Constructor vacio
     */
    public SalaNuevaDTO() {
    }
    /**
     * Constructor con todos los atributos
     * @param numSala
     * @param numAsientos
     * @param estado 
     */
    public SalaNuevaDTO(String numSala, Integer numAsientos, EstadoSala estado) {
        this.numSala = numSala;
        this.numAsientos = numAsientos;
        this.estado = estado;
    }
    /**
     * Metodo para obtener el numero de la sala
     * @return 
     */
    public String getNumSala() {
        return numSala;
    }
    /**
     * Metodo para asignarle el numero a la sala
     * @param numSala 
     */
    public void setNumSala(String numSala) {
        this.numSala = numSala;
    }
    /**
     * Metodo para obtener el numero de asientos que tiene la sala
     * @return 
     */
    public Integer getNumAsientos() {
        return numAsientos;
    }
    /**
     * Metodo para asignar el numero de asientos que tiene una sala
     * @param numAsientos 
     */
    public void setNumAsientos(Integer numAsientos) {
        this.numAsientos = numAsientos;
    }
    /**
     * Metodo para obtener el estado de la Sala
     * @return 
     */
    public EstadoSala getEstado() {
        return estado;
    }
    /**
     * Metodo para asignar el estado a una sala
     * @param estado 
     */
    public void setEstado(EstadoSala estado) {
        this.estado = estado;
    }
        
}
