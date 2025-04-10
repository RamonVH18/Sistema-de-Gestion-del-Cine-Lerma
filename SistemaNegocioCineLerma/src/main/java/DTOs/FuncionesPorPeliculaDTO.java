/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

import java.util.List;

/**
 *
 * @author Abraham Coronel Bringas
 */
public class FuncionesPorPeliculaDTO {

    private String nombrePelicula;
    private String genero;
    private List<FuncionDTO> listaFuncionesDelDia;
    /**
     * Constructor vacio
     */
    public FuncionesPorPeliculaDTO() {
    }
    /**
     * Constructor con todos los atributos
     * @param nombrePelicula
     * @param listaFuncionesDelDia 
     */
    public FuncionesPorPeliculaDTO(String nombrePelicula, String genero, List<FuncionDTO> listaFuncionesDelDia) {    
        this.nombrePelicula = nombrePelicula;
        this.genero = genero;
        this.listaFuncionesDelDia = listaFuncionesDelDia;
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
    public List<FuncionDTO> getListaFuncionesDelDia() {
        return listaFuncionesDelDia;
    }
    /**
     * 
     * @param listaFuncionesDelDia 
     */
    public void setListaFuncionesDelDia(List<FuncionDTO> listaFuncionesDelDia) {
        this.listaFuncionesDelDia = listaFuncionesDelDia;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
}
