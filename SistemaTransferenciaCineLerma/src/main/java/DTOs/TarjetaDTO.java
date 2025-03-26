/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Daniel Miribe
 */
public class TarjetaDTO {

    private String numeroTarjeta;
    private String titular;
    private Integer cvv;
    private Date fechaVencimiento;

    public TarjetaDTO() {
    }

    public TarjetaDTO(String numeroTarjeta, String titular, Integer cvv, Date fechaVencimiento) {
        this.numeroTarjeta = numeroTarjeta;
        this.titular = titular;
        this.cvv = cvv;
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public Integer getCvv() {
        return cvv;
    }

    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }
    
    //Estos metodos hash y equals se utilizan para buscar la cuenta dentro de las listas de cuentas registradas, se hacen comparaciones, en este caso se comparan todos los atributos
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.numeroTarjeta);
        hash = 29 * hash + Objects.hashCode(this.titular);
        hash = 29 * hash + Objects.hashCode(this.cvv);
        hash = 29 * hash + Objects.hashCode(this.fechaVencimiento);
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
        final TarjetaDTO other = (TarjetaDTO) obj;
        if (!Objects.equals(this.numeroTarjeta, other.numeroTarjeta)) {
            return false;
        }
        if (!Objects.equals(this.titular, other.titular)) {
            return false;
        }
        if (!Objects.equals(this.cvv, other.cvv)) {
            return false;
        }
        return Objects.equals(this.fechaVencimiento, other.fechaVencimiento);
    }

    
    
    

    @Override
    public String toString() {
        return "TarjetaDTO{" + "numeroTarjeta=" + numeroTarjeta + ", titular=" + titular + ", cvv=" + cvv + ", fechaVencimiento=" + fechaVencimiento + '}';
    }

}
