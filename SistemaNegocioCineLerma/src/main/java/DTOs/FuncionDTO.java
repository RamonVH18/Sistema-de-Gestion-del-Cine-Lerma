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

    private Integer sala;

    private String nombre;

    private Date fechaHora;

    public FuncionDTO() {
    }

    public FuncionDTO(Long id, Integer sala, String nombre, Date fechaHora) {
        this.id = id;
        this.sala = sala;
        this.nombre = nombre;
        this.fechaHora = fechaHora;
    }

    public FuncionDTO(Integer sala, String nombre, Date fechaHora) {
        this.sala = sala;
        this.nombre = nombre;
        this.fechaHora = fechaHora;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSala() {
        return sala;
    }

    public void setSala(Integer sala) {
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

}
