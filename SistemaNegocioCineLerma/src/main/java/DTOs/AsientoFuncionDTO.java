/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

/**
 *
 * @author Ramon Valencia
 */
public class AsientoFuncionDTO {

    private String idFuncion;
    private String asiento;
    private String numSala;
    private Boolean disponibilidad;
    private String nombreCliente;

    /**
     * Constructor vacio
     */
    public AsientoFuncionDTO() {
    }

    /**
     * Constructor con todos los metodos
     *
     * @param idFuncion
     * @param asiento
     * @param numSala
     * @param disponibilidad
     * @param nombreCliente
     */
    public AsientoFuncionDTO(String idFuncion, String asiento, String numSala, Boolean disponibilidad, String nombreCliente) {
        this.idFuncion = idFuncion;
        this.asiento = asiento;
        this.numSala = numSala;
        this.disponibilidad = disponibilidad;
        this.nombreCliente = nombreCliente;
    }

    /**
     * 
     * @return 
     */
    public String getIdFuncion() {
        return idFuncion;
    }

    /**
     *
     * @param idFuncion
     */
    public void setIdFuncion(String idFuncion) {
        this.idFuncion = idFuncion;
    }

    /**
     *
     * @return
     */
    public String getAsiento() {
        return asiento;
    }

    /**
     *
     * @param asiento
     */
    public void setAsiento(String asiento) {
        this.asiento = asiento;
    }

    public String getNumSala() {
        return numSala;
    }

    public void setNumSala(String numSala) {
        this.numSala = numSala;
    }

    /**
     *
     * @return
     */
    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    /**
     *
     * @param disponibilidad
     */
    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    /**
     *
     * @return
     */
    public String getNombreCliente() {
        return nombreCliente;
    }

    /**
     *
     * @param nombreCliente
     */
    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }
    
}
