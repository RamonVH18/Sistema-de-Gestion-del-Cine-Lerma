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
    
    private FuncionDTO funcion;
    private String asiento;
    private boolean disponibilidad;
    private ClienteDTO cliente;

    public AsientoFuncionDTO() {
    }
    
    public AsientoFuncionDTO(FuncionDTO funcion, String asiento, boolean disponibilidad, ClienteDTO cliente) {
        this.funcion = funcion;
        this.asiento = asiento;
        this.disponibilidad = disponibilidad;
        this.cliente = cliente;
    }

    public FuncionDTO getFuncion() {
        return funcion;
    }

    public void setFuncion(FuncionDTO funcion) {
        this.funcion = funcion;
    }

    public String getAsiento() {
        return asiento;
    }

    public void setAsiento(String asiento) {
        this.asiento = asiento;
    }

    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    } 
}
