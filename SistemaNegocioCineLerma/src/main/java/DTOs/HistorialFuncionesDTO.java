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
public class HistorialFuncionesDTO {

    private String nombrePelicula;

    List<FuncionDTO> listaFunciones;

    public HistorialFuncionesDTO() {
    }

    public HistorialFuncionesDTO(String nombrePelicula, List<FuncionDTO> listaFunciones) {
        this.nombrePelicula = nombrePelicula;
        this.listaFunciones = listaFunciones;
    }

    public String getNombrePelicula() {
        return nombrePelicula;
    }

    public void setNombrePelicula(String nombrePelicula) {
        this.nombrePelicula = nombrePelicula;
    }

    public List<FuncionDTO> getListaFunciones() {
        return listaFunciones;
    }

    public void setListaFunciones(List<FuncionDTO> listaFunciones) {
        this.listaFunciones = listaFunciones;
    }

    @Override
    public String toString() {
        return "HistorialFuncionesDTO{" + "nombrePelicula=" + nombrePelicula + ", listaFunciones=" + listaFunciones + '}';
    }

    //Metodos
    public List<FuncionDTO> obtenerFuncionesDePelicula() {
        return this.listaFunciones;
    }

}
