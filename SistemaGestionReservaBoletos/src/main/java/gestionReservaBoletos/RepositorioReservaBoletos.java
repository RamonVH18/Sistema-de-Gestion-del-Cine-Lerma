/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestionReservaBoletos;

import DTOs.AsientoFuncionDTO;
import DTOs.ClienteDTO;
import DTOs.FuncionDTO;
import DTOs.PeliculaDTO;
import java.util.List;

/**
 * Clase donde se crea una especie de repositorio
 * Este repositorio servira para que guardan los objetos necesarios para el uso del subsistema, de esta forma se pueden reutilizar en varias partes  
 * @author Ramon Valencia
 */
public class RepositorioReservaBoletos {
    
    private PeliculaDTO pelicula;
    
    private FuncionDTO funcion;
    
    private List<AsientoFuncionDTO> asientosFuncion;
    
    private ClienteDTO cliente;
    
    private List<AsientoFuncionDTO> asientosSeleccionados;
    
    private Integer numAsientos;
    
    private RepositorioReservaBoletos instanciaRepositorio;
    
    private RepositorioReservaBoletos(){
    }
    
    public RepositorioReservaBoletos getInstance(){
        if (instanciaRepositorio == null) {
            instanciaRepositorio = new RepositorioReservaBoletos();
        }
        return instanciaRepositorio;
    }

    public PeliculaDTO getPelicula() {
        return pelicula;
    }

    public void setPelicula(PeliculaDTO pelicula) {
        this.pelicula = pelicula;
    }

    public FuncionDTO getFuncion() {
        return funcion;
    }

    public void setFuncion(FuncionDTO funcion) {
        this.funcion = funcion;
    }

    public List<AsientoFuncionDTO> getAsientosFuncion() {
        return asientosFuncion;
    }

    public void setAsientosFuncion(List<AsientoFuncionDTO> asientosFuncion) {
        this.asientosFuncion = asientosFuncion;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    public List<AsientoFuncionDTO> getAsientosSeleccionados() {
        return asientosSeleccionados;
    }

    public void setAsientosSeleccionados(List<AsientoFuncionDTO> asientosSeleccionados) {
        this.asientosSeleccionados = asientosSeleccionados;
    }

    public Integer getNumAsientos() {
        return numAsientos;
    }

    public void setNumAsientos(Integer numAsientos) {
        this.numAsientos = numAsientos;
    }
    
    
}
