/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gestionReservaBoletos;

import DTOs.FuncionDTO;
import DTOs.PeliculaDTO;
import Excepciones.GestionReservaException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Ramon Valencia
 */
public interface IManejoDeBoletos {
    
    public List<PeliculaDTO> cargarPeliculasActivas() throws GestionReservaException;
    
    public List<FuncionDTO> cargarFuncionesDelDia(Date dia, String nombrePelicula) throws GestionReservaException;
    
    public boolean validarCampoAsiento(String campoAsiento) throws GestionReservaException;
    
    public int consultarDisponibilidadAsientos(FuncionDTO funcion) throws GestionReservaException;
    
    public boolean validarDisponibilidaDeAsientos(int numAsientos, FuncionDTO funcion) throws GestionReservaException;
    
    public double calcularCostoTotal(int numAsientos, FuncionDTO funcion) throws GestionReservaException;
}
