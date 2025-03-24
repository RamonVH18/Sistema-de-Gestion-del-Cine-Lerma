/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

import java.util.Objects;

/**
 *
 * @author Sebastian Borquez
 */
public class CuentaMercadoDTO {
    private String titular;
    private String correo;
    private Integer clienteID;

    public CuentaMercadoDTO() {
    }

    public CuentaMercadoDTO(String titular, String correo, Integer clienteID) {
        this.titular = titular;
        this.correo = correo;
        this.clienteID = clienteID;
    }

    public CuentaMercadoDTO(Integer clienteID) {
        this.clienteID = clienteID;
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

    public Integer getClienteID() {
        return clienteID;
    }

    public void setClienteID(Integer clienteID) {
        this.clienteID = clienteID;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.clienteID);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CuentaMercadoDTO other = (CuentaMercadoDTO) obj;
        return Objects.equals(this.clienteID, other.clienteID);
    }

    
    
    

    @Override
    public String toString() {
        return "CuentaMercadoDTO{" + "titular=" + titular + ", correo=" + correo + ", clienteID=" + clienteID + '}';
    }

}
