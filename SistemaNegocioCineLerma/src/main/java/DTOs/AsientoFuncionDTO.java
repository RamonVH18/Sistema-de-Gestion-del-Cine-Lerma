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
     * Metodo para obtener el id de la funcion al que pertenece el AsientoFuncionDTO
     * @return 
     */
    public String getIdFuncion() {
        return idFuncion;
    }
    /**
     * Metodo para asignarle un id de la funcion al que pertenece el AsientoFuncionDTO
     * @param idFuncion
     */
    public void setIdFuncion(String idFuncion) {
        this.idFuncion = idFuncion;
    }
    /**
     * Metodo para obtener el numero de asiento del AsientoFuncionDTO
     * @return
     */
    public String getAsiento() {
        return asiento;
    }
    /**
     * Metodo para asignarle un numero de asiento al AsientoFuncionDTO
     * @param asiento
     */
    public void setAsiento(String asiento) {
        this.asiento = asiento;
    }
    /**
     * Metodo para obtener el numero de la sala en la que se encuentra el AsientoFuncionDTO
     * @return 
     */
    public String getNumSala() {
        return numSala;
    }
    /**
     * Metodo para asignarle el numero de la sala en la 
     * @param numSala 
     */
    public void setNumSala(String numSala) {
        this.numSala = numSala;
    }
    /**
     * Metodo para checar la disponibilidad del AsientoFuncionDTO
     * @return
     */
    public boolean isDisponibilidad() {
        return disponibilidad;
    }
    /**
     *  Metodo para asignar la disponibilidad del AsientoFuncionDTO
     * @param disponibilidad
     */
    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }
    /**
     * Metodo para obtener el nombre del cliente que esta asignado al cliente
     * @return
     */
    public String getNombreCliente() {
        return nombreCliente;
    }
    /**
     * Metodo para asignarle el nombre cliente al AsientoFuncionDTO
     * @param nombreCliente
     */
    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }
    
}
