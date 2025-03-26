/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

/**
 *
 * @author Ramon Valencia
 */
public class MetodoPagoDTO {
    
    private String nombreMetodo;
    private String imagenMetodo;
    /**
     * Constructor vacio
     */
    public MetodoPagoDTO() {
    }
    /**
     * Constructor con todos los metodos
     * @param nombreMetodo
     * @param imagenMetodo 
     */
    public MetodoPagoDTO(String nombreMetodo, String imagenMetodo) {
        this.nombreMetodo = nombreMetodo;
        this.imagenMetodo = imagenMetodo;
    }
    /**
     * 
     * @return 
     */
    public String getNombreMetodo() {
        return nombreMetodo;
    }
    /**
     * 
     * @param nombreMetodo 
     */
    public void setNombreMetodo(String nombreMetodo) {
        this.nombreMetodo = nombreMetodo;
    }
    /**
     * 
     * @return 
     */
    public String getImagenMetodo() {
        return imagenMetodo;
    }
    /**
     * 
     * @param imagenMetodo 
     */
    public void setImagenMetodo(String imagenMetodo) {
        this.imagenMetodo = imagenMetodo;
    }
    
}
