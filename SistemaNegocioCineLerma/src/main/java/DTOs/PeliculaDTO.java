/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

/**
 *
 * @author Daniel Miribe
 */
public class PeliculaDTO {

    private String nombrePelicula;
    private String peliculaImagen; // ESTE DEBE SER DE TIPO IMAGEN
    public String descripcionPelicula;
    private Double duracion;
    /**
     * Cosntructor vacio
     */
    public PeliculaDTO() {
    }
    /**
     * Constructor con todos los metodos
     * @param nombrePelicula
     * @param peliculaImagen
     * @param descripcionPelicula 
     */
    public PeliculaDTO(String nombrePelicula, String peliculaImagen, String descripcionPelicula, Double duracion) {    
        this.nombrePelicula = nombrePelicula;
        this.peliculaImagen = peliculaImagen;
        this.descripcionPelicula = descripcionPelicula;
        this.duracion = duracion;
    }

    /**
     *
     * @return 
     */
    public String getNombrePelicula() {
        return nombrePelicula;
    }
    /**
     * 
     * @param nombrePelicula 
     */
    public void setNombrePelicula(String nombrePelicula) {
        this.nombrePelicula = nombrePelicula;
    }
    /**
     * 
     * @return 
     */
    public String getPeliculaImagen() {
        return peliculaImagen;
    }
    /**
     * 
     * @param peliculaImagen 
     */
    public void setPeliculaImagen(String peliculaImagen) {
        this.peliculaImagen = peliculaImagen;
    }
    /**
     * 
     * @return 
     */
    public String getDescripcionPelicula() {
        return descripcionPelicula;
    }
    /**
     * 
     * @param descripcionPelicula 
     */
    public void setDescripcionPelicula(String descripcionPelicula) {
        this.descripcionPelicula = descripcionPelicula;
    }

    public Double getDuracion() {
        return duracion;
    }

    public void setDuracion(Double duracion) {
        this.duracion = duracion;
    }
    
    

}
