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
    
    private String imagenPelicula; // LUEGO LO CAMBIAMOS A UN ARREGLO DE BYTES
    
    private Date fechaHoraFuncion;
    
    private String numeroSala;
    
    private String nombreCliente;
    
    private List<String> listaAsientosSeleccionados;

    /**
     * Constructor vacio
     */
    public BoletoDTO() {
    }

    /**
     * Constructor normal
     * @param nombrePelicula
     * @param imagenPelicula
     * @param fechaHoraFuncion
     * @param numeroSala
     * @param listaAsientosSeleccionados
     * @param nombreCliente 
     */
    public BoletoDTO(String nombrePelicula, String imagenPelicula, Date fechaHoraFuncion, String numeroSala, List<String> listaAsientosSeleccionados, String nombreCliente) {
        this.nombrePelicula = nombrePelicula;
        this.imagenPelicula = imagenPelicula;
        this.fechaHoraFuncion = fechaHoraFuncion;
        this.numeroSala = numeroSala;
        this.listaAsientosSeleccionados = listaAsientosSeleccionados;
        this.nombreCliente = nombreCliente;
    }
    
    /**
     * 
     * @return 
     */
    public String getNombrePelicula() {
        return nombrePelicula;
    }
    /**
     * 
     * @param nombrePelicula 
     */
    public void setNombrePelicula(String nombrePelicula) {
        this.nombrePelicula = nombrePelicula;
    }
    /**
     * 
     * @return 
     */
    public String getImagenPelicula() {
        return imagenPelicula;
    }
    /**
     * 
     * @param imagenPelicula 
     */
    public void setImagenPelicula(String imagenPelicula) {
        this.imagenPelicula = imagenPelicula;
    }
    /**
     * 
     * @return 
     */
    public Date getFechaHoraFuncion() {
        return fechaHoraFuncion;
    }
    /**
     * 
     * @param fechaHoraFuncion 
     */
    public void setFechaHoraFuncion(Date fechaHoraFuncion) {
        this.fechaHoraFuncion = fechaHoraFuncion;
    }
    /**
     * 
     * @return 
     */
    public String getNumeroSala() {
        return numeroSala;
    }
    /**
     * 
     * @param numeroSala 
     */
    public void setNumeroSala(String numeroSala) {
        this.numeroSala = numeroSala;
    }
    /**
     * 
     * @return 
     */
    public List<String> getListaAsientosSeleccionados() {
        return listaAsientosSeleccionados;
    }
    /**
     * 
     * @param listaAsientosSeleccionados 
     */
    public void setListaAsientosSeleccionados(List<String> listaAsientosSeleccionados) {
        this.listaAsientosSeleccionados = listaAsientosSeleccionados;
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
