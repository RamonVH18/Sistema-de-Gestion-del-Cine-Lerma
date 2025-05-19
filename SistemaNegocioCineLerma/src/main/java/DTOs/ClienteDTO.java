/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

/**
 *
 * @author Ramon Valencia
 */
/**
 * Clase que representa la información de un cliente en el sistema.
 * Extiende de UsuarioDTO para heredar los atributos básicos de usuario.
 * Contiene información específica de la dirección del cliente.
 * 
 */
public class ClienteDTO extends UsuarioDTO{
    
    //Nombre de la calle del domicilio del cliente
    private String calle;
    //Codigo postal del cliente
    private String CP;
    //numero exterior del domicilio del cliente
    private String numero;

    public ClienteDTO() {
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getCP() {
        return CP;
    }

    public void setCP(String CP) {
        this.CP = CP;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return "ClienteDTO{" + "calle=" + calle + ", CP=" + CP + ", numero=" + numero + '}';
    }
    
    
    
    
    
    

   
}
