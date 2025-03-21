/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Ramon Valencia
 */
public class BoletoDTO {
    
    private String nombrePelicula;
    
    private String imagenPelicula; // LUEGO LO CAMBIAMOS A IMAGEN
    
    private Date fechaHoraFuncion;
    
    private String numeroSala;
    
    private List<String> listaAsientosSeleccionados;

    public BoletoDTO() {
    }

    public String getNombrePelicula() {
        return nombrePelicula;
    }

    public BoletoDTO(String nombrePelicula, String imagenPelicula, Date fechaHoraFuncion, String numeroSala, List<String> listaAsientosSeleccionados) {
        this.nombrePelicula = nombrePelicula;
        this.imagenPelicula = imagenPelicula;
        this.fechaHoraFuncion = fechaHoraFuncion;
        this.numeroSala = numeroSala;
        this.listaAsientosSeleccionados = listaAsientosSeleccionados;
    }

    public void setNombrePelicula(String nombrePelicula) {
        this.nombrePelicula = nombrePelicula;
    }

    public String getImagenPelicula() {
        return imagenPelicula;
    }

    public void setImagenPelicula(String imagenPelicula) {
        this.imagenPelicula = imagenPelicula;
    }

    public Date getFechaHoraFuncion() {
        return fechaHoraFuncion;
    }

    public void setFechaHoraFuncion(Date fechaHoraFuncion) {
        this.fechaHoraFuncion = fechaHoraFuncion;
    }

    public String getNumeroSala() {
        return numeroSala;
    }

    public void setNumeroSala(String numeroSala) {
        this.numeroSala = numeroSala;
    }

    public List<String> getListaAsientosSeleccionados() {
        return listaAsientosSeleccionados;
    }

    public void setListaAsientosSeleccionados(List<String> listaAsientosSeleccionados) {
        this.listaAsientosSeleccionados = listaAsientosSeleccionados;
    }
    
    
    

}
