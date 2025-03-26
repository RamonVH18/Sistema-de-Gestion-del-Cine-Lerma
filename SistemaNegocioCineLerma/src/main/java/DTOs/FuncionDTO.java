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

    /**
     * Constructor vacio
     */
    public FuncionDTO() {
    }
    /**
     * Constructor con todos los atributos y con id
     * @param id
     * @param sala
     * @param nombre
     * @param fechaHora
     * @param precio 
     */
    public FuncionDTO(Long id, String sala, String nombre, Date fechaHora, double precio) {
        this.id = id;
        this.sala = sala;
        this.nombre = nombre;
        this.fechaHora = fechaHora;
        this.precio = precio;
    }
    /**
     * Constructor con todos los atributos sin el id
     * @param sala
     * @param nombre
     * @param fechaHora
     * @param precio 
     */
    public FuncionDTO(String sala, String nombre, Date fechaHora, double precio) {
        this.sala = sala;
        this.nombre = nombre;
        this.fechaHora = fechaHora;
        this.precio = precio;
    }
    /**
     * 
     * @return 
     */
    public Long getId() {
        return id;
    }
    /**
     * 
     * @param id 
     */
    public void setId(Long id) {
        this.id = id;
    }
    /**
     * 
     * @return 
     */
    public String getSala() {
        return sala;
    }
    /**
     * 
     * @param sala 
     */
    public void setSala(String sala) {
        this.sala = sala;
    }
    /**
     * 
     * @return 
     */
    public String getNombre() {
        return nombre;
    }
    /**
     * 
     * @param nombre 
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
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
    /**
     * 
     * @return 
     */
    public double getPrecio() {
        return precio;
    }
    /**
     * 
     * @param precio 
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
    

}
