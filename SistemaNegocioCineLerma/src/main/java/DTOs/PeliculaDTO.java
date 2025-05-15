/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

/**
 *
 * @author Daniel M
 */
public class PeliculaDTO {

    private String idPelicula;
    private String imagen;
    private String titulo;
    private String genero;
    private Integer duracion;
    private String clasificacion;
    private String sinopsis;
    private Boolean activo;

    public PeliculaDTO() {
    }

    public PeliculaDTO(String idPelicula, String imagen, String titulo, String genero, Integer duracion, String clasificacion, String sinopsis, Boolean activo) {
        this.idPelicula = idPelicula;
        this.imagen = imagen;
        this.titulo = titulo;
        this.genero = genero;
        this.duracion = duracion;
        this.clasificacion = clasificacion;
        this.sinopsis = sinopsis;
        this.activo = activo;
    }

    public PeliculaDTO(String imagen, String titulo, String genero, Integer duracion, String clasificacion, String sinopsis, Boolean activo) {
        this.imagen = imagen;
        this.titulo = titulo;
        this.genero = genero;
        this.duracion = duracion;
        this.clasificacion = clasificacion;
        this.sinopsis = sinopsis;
        this.activo = activo;
    }

    public String getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(String idPelicula) {
        this.idPelicula = idPelicula;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
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

}
