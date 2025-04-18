/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gestionReservaBoletos;

import DTOs.BoletoDTO;
import DTOs.ClienteDTO;
import DTOs.FuncionDTO;
import DTOs.MetodoPagoDTO;
import DTOs.PeliculaDTO;
import Excepciones.FuncionCargaException;
import Excepciones.PeliculasCargaException;
import Excepciones.ReservarAsientoFuncionException;
import Excepciones.*;
import java.util.List;

/**
 *
 * @author Ramon Valencia
 */
public interface IManejoDeBoletos {
    
    public List<PeliculaDTO> cargarPeliculasActivas() throws PeliculasCargaException;
    
    public List<FuncionDTO> cargarFuncionesPelicula(String nombrePelicula) throws FuncionCargaException;
    
    public boolean validarCampoAsiento(String campoAsiento, FuncionDTO funcion) throws ValidarCampoAsientoException;
    
    public int consultarDisponibilidadAsientos(FuncionDTO funcion) throws DisponibilidadAsientosException;
    
    public boolean validarDisponibilidaDeAsientos(int numAsientos, FuncionDTO funcion) throws DisponibilidadAsientosException;
    
    public Double calcularCostoTotal(int numAsientos, FuncionDTO funcion) throws CalcularCostoTotalException;
    
    public List<MetodoPagoDTO> cargarMetodosPago();
    
    public BoletoDTO generarBoleto(PeliculaDTO pelicula, FuncionDTO funcion, List<String> asientos, ClienteDTO clienteDTO) throws GenerarBoletoException;
    
    public List<String> reservarAsientoFuncion(FuncionDTO funcion, int numAsiento, ClienteDTO cliente) throws ReservarAsientoFuncionException;
}
