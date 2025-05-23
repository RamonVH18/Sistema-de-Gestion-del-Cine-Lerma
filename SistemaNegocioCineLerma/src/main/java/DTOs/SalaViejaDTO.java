/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

import enums.EstadoSala;
import java.util.List;

/**
 * DTO para mostrar la informacion de salas existentes en la base de datos
 * @author Ramon Valencia
 */
public class SalaViejaDTO {
    
    
    private String numSala;
    private Integer numAsientos;
    private EstadoSala estado;
    private List<AsientoDTO> asientos;
    /**
     * Constructor vacio
     */
    public SalaViejaDTO() {
    }
    /**
     * Constructor con todos los atributos
     * @param numSala
     * @param numAsientos
     * @param estado
     * @param asientos 
     */
    public SalaViejaDTO(String numSala, Integer numAsientos, EstadoSala estado, List<AsientoDTO> asientos) {
        this.numSala = numSala;
        this.numAsientos = numAsientos;
        this.estado = estado;
        this.asientos = asientos;
    }
    /**
     * Metodo para obtener el numero de la sala del DTO
     * @return 
     */
    public String getNumSala() {
        return numSala;
    }
    /**
     * Metodo para asignarle el numero de la sala al DTO
     * @param numSala 
     */
    public void setNumSala(String numSala) {
        this.numSala = numSala;
    }
    /**
     * Metodo para obtener el numero de asientos del DTO
     * @return 
     */
    public Integer getNumAsientos() {
        return numAsientos;
    }
    /**
     * Metodo para asignarle el numero de asientos al DTO
     * @param numAsientos 
     */
    public void setNumAsientos(Integer numAsientos) {
        this.numAsientos = numAsientos;
    }
    /**
     * Metodo para obtener el estado del DTO
     * @return 
     */
    public EstadoSala getEstado() {
        return estado;
    }
    /**
     * Metodo para asignarle el estado al DTO
     * @param estado 
     */
    public void setEstado(EstadoSala estado) {
        this.estado = estado;
    }
    /**
     * Metodo para obtener la lista de asientos del DTO
     * @return 
     */
    public List<AsientoDTO> getAsientos() {
        return asientos;
    }
    /**
     * Metodo para asignarle la lista de asientos al DTO
     * @param asientos 
     */
    public void setAsientos(List<AsientoDTO> asientos) {
        this.asientos = asientos;
    }
    
    
}
