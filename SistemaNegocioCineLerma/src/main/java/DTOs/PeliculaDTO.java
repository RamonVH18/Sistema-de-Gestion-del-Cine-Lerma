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

    public PeliculaDTO() {
    }

    public PeliculaDTO(String nombrePelicula, String peliculaImagen, String descripcionPelicula) {
        this.nombrePelicula = nombrePelicula;
        this.peliculaImagen = peliculaImagen;
        this.descripcionPelicula = descripcionPelicula;
    }

    public String getNombrePelicula() {
        return nombrePelicula;
    }

    public void setNombrePelicula(String nombrePelicula) {
        this.nombrePelicula = nombrePelicula;
    }

    public String getPeliculaImagen() {
        return peliculaImagen;
    }

    public void setPeliculaImagen(String peliculaImagen) {
        this.peliculaImagen = peliculaImagen;
    }

    public String getDescripcionPelicula() {
        return descripcionPelicula;
    }

    public void setDescripcionPelicula(String descripcionPelicula) {
        this.descripcionPelicula = descripcionPelicula;
    }

}
