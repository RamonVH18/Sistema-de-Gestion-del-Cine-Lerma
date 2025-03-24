/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

/**
 *
 * @author Sebastian Borquez
 */
public class CuentaMercadoDTO {
    private String titular;
    private String correo;

    public CuentaMercadoDTO() {
    }

    public CuentaMercadoDTO(String titular, String correo) {
        this.titular = titular;
        this.correo = correo;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public String toString() {
        return "CuentaMercadoDTO{" + "titular=" + titular + ", correo=" + correo + '}';
    }
    
    
    
    
}
