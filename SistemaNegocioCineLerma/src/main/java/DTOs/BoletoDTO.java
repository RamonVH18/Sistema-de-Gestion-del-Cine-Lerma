/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Ramon Valencia
 */
public class BoletoDTO {
    
    private String nombrePelicula;
    
    private byte[] imagen;
    
    private LocalDateTime fechaHoraFuncion;
    
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
     * @param imagen
     * @param fechaHoraFuncion
     * @param numeroSala
     * @param listaAsientosSeleccionados
     * @param nombreCliente 
     */
    public BoletoDTO(String nombrePelicula, byte[] imagen, LocalDateTime fechaHoraFuncion, String numeroSala, String nombreCliente, List<String> listaAsientosSeleccionados) {
        this.nombrePelicula = nombrePelicula;
        this.imagen = imagen;
        this.fechaHoraFuncion = fechaHoraFuncion;
        this.numeroSala = numeroSala;
        this.nombreCliente = nombreCliente;
        this.listaAsientosSeleccionados = listaAsientosSeleccionados;
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
    public byte[] getImagen() {
        return imagen;
    }

    /**
     * 
     * @param imagen
     */
    public void setImagen(byte[] imagen) {    
        this.imagen = imagen;
    }

    /**
     *
     * @return 
     */
    public LocalDateTime getFechaHoraFuncion() {
        return fechaHoraFuncion;
    }
    /**
     * 
     * @param fechaHoraFuncion 
     */
    public void setFechaHoraFuncion(LocalDateTime fechaHoraFuncion) {
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
