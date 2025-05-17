/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

/**
 *
 * @author Daniel M
 */
public class Pelicula {

    @BsonId
    private ObjectId idPelicula;
    private byte[] imagen;
    private String titulo;
    private String genero;
    private Integer duracion;
    private String clasificacion;
    private String sinopsis;
    private Boolean activo;

    public Pelicula() {
    }

    public Pelicula(ObjectId idPelicula, byte[] imagen, String titulo, String genero, Integer duracion, String clasificacion, String sinopsis, Boolean estado) {
        this.idPelicula = idPelicula;
        this.imagen = imagen;
        this.titulo = titulo;
        this.genero = genero;
        this.duracion = duracion;
        this.clasificacion = clasificacion;
        this.sinopsis = sinopsis;
        this.activo = estado;
    }

    public Pelicula(byte[] imagen, String titulo, String genero, Integer duracion, String clasificacion, String sinopsis, Boolean estado) {
        this.imagen = imagen;
        this.titulo = titulo;
        this.genero = genero;
        this.duracion = duracion;
        this.clasificacion = clasificacion;
        this.sinopsis = sinopsis;
        this.activo = estado;
    }

    public ObjectId getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(ObjectId idPelicula) {
        this.idPelicula = idPelicula;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "Pelicula{"
                + "id=" + idPelicula
                + ", imagen=" + imagen
                + ", titulo=" + titulo
                + ", genero=" + genero
                + ", duracion=" + duracion
                + ", clasificacion=" + clasificacion
                + ", sinopsis=" + sinopsis
                + ", estado=" + activo
                + '}';
    }
}
