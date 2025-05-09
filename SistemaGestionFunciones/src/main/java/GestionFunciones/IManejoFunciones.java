/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package GestionFunciones;

import DTOs.ClienteDTO;
import DTOs.FuncionDTO;
import DTOs.HistorialFuncionesDTO;
import DTOs.SalaDTO;
import Excepciones.FuncionSolapamientoSalaException;
import Excepciones.FuncionCapacidadSalaException;
import Excepciones.FuncionDatosIncorrectosException;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Abraham Coronel Bringas
 */
public interface IManejoFunciones {
    
    public FuncionDTO registraFuncion(FuncionDTO funcionDTO) throws FuncionDatosIncorrectosException, FuncionSolapamientoSalaException, FuncionCapacidadSalaException;

    public Boolean eliminarFuncion(FuncionDTO funcionDTO) throws FuncionDatosIncorrectosException;
    
    public List<HistorialFuncionesDTO> buscarHistorialFunciones();
    
    public FuncionDTO validarFuncion(FuncionDTO funcionDTO) throws FuncionDatosIncorrectosException, FuncionSolapamientoSalaException, FuncionCapacidadSalaException;

    public List<FuncionDTO> buscarFuncionesPelicula(String pelicula) throws FuncionDatosIncorrectosException;

    public List<FuncionDTO> buscarFuncionesActivas();
    
    // metodos de observer, falta corregir desde negocio, deben ser DTOs y no entidades normales
    //Cambiar sus excepciones
    public void suscribirClienteAFuncion(ClienteDTO cliente, String idFuncion) throws FuncionSolapamientoSalaException;

    public void desuscribirClienteDeFuncion(ClienteDTO cliente, String idFuncion) throws FuncionSolapamientoSalaException;

    public boolean cambiarEstadoFuncion(String idFuncion, Boolean nuevoEstado) throws FuncionSolapamientoSalaException;

    public boolean cambiarHorarioFuncion(String idFuncion, LocalDateTime nuevoHorario) throws FuncionSolapamientoSalaException;

    public boolean cambiarSalaFuncion(String idFuncion, SalaDTO nuevaSala) throws FuncionSolapamientoSalaException;

    public boolean cambiarPrecioFuncion(String idFuncion, Double nuevoPrecio) throws FuncionSolapamientoSalaException;
    
}
