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
/**
 * 
 * Clase que representa una tarjeta de pago.
 */
public class TarjetaDTO {

    private String numeroTarjeta;
    private String titular;
    private Integer cvv;
    private Date fechaVencimiento;
    private double saldo;

    /**
     * Constructor por defecto.
     */
    public TarjetaDTO() {
    }

    /**
     * 
     * 
     * Constructor que inicializa todos los atributos de la tarjeta.
     *
     * 
     * @param numeroTarjeta el numero de la tarjeta.
     * @param titular el nombre del titular de la tarjeta.
     * @param cvv el código de verificación de la tarjeta.
     * @param fechaVencimiento la fecha de vencimiento de la tarjeta.
     * @param saldo el saldo disponible en la tarjeta.
     */
    public TarjetaDTO(String numeroTarjeta, String titular, Integer cvv, Date fechaVencimiento, double saldo) {
        this.numeroTarjeta = numeroTarjeta;
        this.titular = titular;
        this.cvv = cvv;
        this.fechaVencimiento = fechaVencimiento;
        this.saldo = saldo;
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

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    //Estos metodos hash y equals se utilizan para buscar la cuenta dentro de las listas de cuentas registradas, se hacen comparaciones, en este caso se comparan todos los atributos
    /**
     * 
     * 
     * Calcula el código hash de la tarjeta basado en sus atributos.
     *
     * @return el codigo hash de la tarjeta.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.numeroTarjeta);
        hash = 29 * hash + Objects.hashCode(this.titular);
        hash = 29 * hash + Objects.hashCode(this.cvv);
        hash = 29 * hash + Objects.hashCode(this.fechaVencimiento);
        return hash;
    }

    /**
     * 
     * 
     * Compara esta tarjeta con otra para determinar si son iguales. Dos
     * tarjetas son iguales si tienen el mismo número de tarjeta, titular, CVV y
     * fecha de vencimiento.
     *
     * @param obj el objeto a comparar.
     * @return true si son iguales, false en caso contrario.
     */
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
        return "TarjetaDTO{" + "numeroTarjeta=" + numeroTarjeta + ", titular=" + titular + ", cvv=" + cvv + ", fechaVencimiento=" + fechaVencimiento + ", saldo=" + saldo + '}';
    }

}
