/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import org.bson.types.ObjectId;

/**
 *
 * @author Daniel M
 */
public class Asiento {

    private ObjectId idAsiento;

    private String numero;

    private Sala sala;

    public Asiento() {
    }

    public Asiento(ObjectId idAsiento, String numero, Sala sala) {
        this.idAsiento = idAsiento;
        this.numero = numero;
        this.sala = sala;
    }

    public Asiento(String numero, Sala sala) {
        this.numero = numero;
        this.sala = sala;
    }

    public ObjectId getIdAsiento() {
        return idAsiento;
    }

    public void setIdAsiento(ObjectId idAsiento) {
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
