/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

import java.util.Objects;

/**
 *
 * @author Abraham Coronel Bringas
 */
public class PaypalDTO {

    private String correo;

    private String contrasenia;
    
    private double saldo;

    public PaypalDTO() {
    }

    public PaypalDTO(String correo, String contrasenia, double saldo) {
        this.correo = correo;
        this.contrasenia = contrasenia;
        this.saldo = saldo;
    }

    

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
    
    
    
    
    //Estos metodos hash y equals se utilizan para buscar la cuenta dentro de las listas de cuentas registradas, se hacen comparaciones, en este caso se comparan todos los atributos
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.correo);
        hash = 47 * hash + Objects.hashCode(this.contrasenia);
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
        final PaypalDTO other = (PaypalDTO) obj;
        if (!Objects.equals(this.correo, other.correo)) {
            return false;
        }
        return Objects.equals(this.contrasenia, other.contrasenia);
    }

    
    
    

}
