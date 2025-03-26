/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

/**
 *
 * @author Ramon Valencia
 */
public class ClienteDTO {
    
    private String nombre;
    private String correo;
    
    /**
     * Constructor Vacio
     */
    public ClienteDTO() {
    }
    
    /**
     * Constructor con todos los atributos
     * @param nombre
     * @param correo 
     */
    public ClienteDTO(String nombre, String correo) {
        this.nombre = nombre;
        this.correo = correo;
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
    public String getCorreo() {
        return correo;
    }
    /**
     * 
     * @param correo 
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }
   
}
