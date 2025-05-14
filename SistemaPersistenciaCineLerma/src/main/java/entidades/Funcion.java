/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.time.LocalDateTime;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

/**
 *
 * @author Abraham Coronel Bringas
 */
public class Funcion {

    @BsonId
    private ObjectId idFuncion;

    private Sala sala;

    private Pelicula pelicula;

    private LocalDateTime fechaHora;

    private Double precio;

    public Funcion() {
    }

    public Funcion(ObjectId idFuncion, Sala sala, Pelicula pelicula, LocalDateTime fechaHora, Double precio) {
        this.idFuncion = idFuncion;
        this.sala = sala;
        this.pelicula = pelicula;
        this.fechaHora = fechaHora;
        this.precio = precio;
    }

    public Funcion(Sala sala, Pelicula pelicula, LocalDateTime fechaHora, Double precio) {
        this.sala = sala;
        this.pelicula = pelicula;
        this.fechaHora = fechaHora;
        this.precio = precio;
    }

    public ObjectId getIdFuncion() {
        return idFuncion;
    }

    public void setIdFuncion(ObjectId idFuncion) {
        this.idFuncion = idFuncion;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getIdString() {
        return idFuncion.toString();
    }

    public void setIdString(String idImportado) {
        this.idFuncion = new ObjectId(idImportado);
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Funcion{" + "idFuncion=" + idFuncion + ", sala=" + sala + ", pelicula=" + pelicula + ", fechaHora=" + fechaHora + ", precio=" + precio + '}';
    }

}
