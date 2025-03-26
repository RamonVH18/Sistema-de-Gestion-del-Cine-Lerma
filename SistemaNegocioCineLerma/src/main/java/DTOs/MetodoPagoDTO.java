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

    public MetodoPagoDTO() {
    }
    
    public MetodoPagoDTO(String nombreMetodo, String imagenMetodo) {
        this.nombreMetodo = nombreMetodo;
        this.imagenMetodo = imagenMetodo;
    }

    public String getNombreMetodo() {
        return nombreMetodo;
    }

    public void setNombreMetodo(String nombreMetodo) {
        this.nombreMetodo = nombreMetodo;
    }

    public String getImagenMetodo() {
        return imagenMetodo;
    }

    public void setImagenMetodo(String imagenMetodo) {
        this.imagenMetodo = imagenMetodo;
    }
    
}
