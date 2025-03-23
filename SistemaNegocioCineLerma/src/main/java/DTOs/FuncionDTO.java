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
public class FuncionDTO {

    private Long id;

    private String sala;

    private String nombre;

    private Date fechaHora;
    
    private double precio;

    public FuncionDTO() {
    }

    public FuncionDTO(Long id, String sala, String nombre, Date fechaHora, double precio) {
        this.id = id;
        this.sala = sala;
        this.nombre = nombre;
        this.fechaHora = fechaHora;
        this.precio = precio;
    }

    public FuncionDTO(String sala, String nombre, Date fechaHora, double precio) {
        this.sala = sala;
        this.nombre = nombre;
        this.fechaHora = fechaHora;
        this.precio = precio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
    

}
