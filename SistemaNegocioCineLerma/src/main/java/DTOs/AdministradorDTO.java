/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

/**
 *
 * @author sonic
 */
/**
 * Clase DTO que representa a un Administrador.
 * Extiende de UsuarioDTO para incluir los atributos heredados de usuario,
 * y añade el campo específico RFC propio de un administrador.
 */
public class AdministradorDTO extends UsuarioDTO{
    
    //rfc del administrador
    private String RFC;

    public AdministradorDTO() {
    }

    public String getRFC() {
        return RFC;
    }

    public void setRFC(String RFC) {
        this.RFC = RFC;
    }
    
    
    
}
