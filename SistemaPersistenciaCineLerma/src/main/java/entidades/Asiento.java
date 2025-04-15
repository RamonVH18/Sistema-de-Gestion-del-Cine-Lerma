/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

/**
 *
 * @author Daniel M
 */
public class Asiento {

    private Long idAsiento;

    private String numero;

    private Sala sala;

    public Asiento() {
    }

    public Asiento(Long idAsiento, String numero, Sala sala) {
        this.idAsiento = idAsiento;
        this.numero = numero;
        this.sala = sala;
    }

    public Asiento(String numero, Sala sala) {
        this.numero = numero;
        this.sala = sala;
    }

    public Long getIdAsiento() {
        return idAsiento;
    }

    public void setIdAsiento(Long idAsiento) {
        this.idAsiento = idAsiento;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    @Override
    public String toString() {
        return "Asiento{"
                + "idAsiento=" + idAsiento
                + ", numero=" + numero
                + ", sala=" + sala
                + '}';
    }
}
