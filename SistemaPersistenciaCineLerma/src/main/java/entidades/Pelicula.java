/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

/**
 *
 * @author Ramon Valencia
 */
public class Pelicula {

    private Long id;
    private String titulo;
    private String imagen;
    private String genero;
    private Integer duracion;
    private String sinopsis;
    private Boolean estado;

    public Pelicula() {
    }

    public Pelicula(Long id, String titulo, String imagen, String genero, Integer duracion, String sinopsis, Boolean estado) {
        this.id = id;
        this.titulo = titulo;
        this.imagen = imagen;
        this.genero = genero;
        this.duracion = duracion;
        this.sinopsis = sinopsis;
        this.estado = estado;
    }

    public Pelicula(String titulo, String imagen, String genero, Integer duracion, String sinopsis, Boolean estado) {
        this.titulo = titulo;
        this.imagen = imagen;
        this.genero = genero;
        this.duracion = duracion;
        this.sinopsis = sinopsis;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
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

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Pelicula{"
                + "id=" + id
                + ", titulo=" + titulo
                + ", imagen=" + imagen
                + ", genero=" + genero
                + ", duracion=" + duracion
                + ", sinopsis=" + sinopsis
                + ", estado=" + estado
                + '}';
    }
}
