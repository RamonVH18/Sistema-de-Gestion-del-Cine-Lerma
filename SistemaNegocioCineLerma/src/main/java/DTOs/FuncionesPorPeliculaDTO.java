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

    private List<FuncionDTO> listaFuncionesDelDia;

    public FuncionesPorPeliculaDTO(String nombrePelicula, List<FuncionDTO> listaFuncionesDelDia) {
        this.nombrePelicula = nombrePelicula;
        this.listaFuncionesDelDia = listaFuncionesDelDia;
    }

    public FuncionesPorPeliculaDTO() {
    }

    public String getNombrePelicula() {
        return nombrePelicula;
    }

    public void setNombrePelicula(String nombrePelicula) {
        this.nombrePelicula = nombrePelicula;
    }

    public List<FuncionDTO> getListaFuncionesDelDia() {
        return listaFuncionesDelDia;
    }

    public void setListaFuncionesDelDia(List<FuncionDTO> listaFuncionesDelDia) {
        this.listaFuncionesDelDia = listaFuncionesDelDia;
    }

}
