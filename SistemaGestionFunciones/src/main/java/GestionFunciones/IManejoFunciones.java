/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package GestionFunciones;

import DTOs.ClienteDTO;
import DTOs.FuncionDTO;
import DTOs.SalaDTO;
import Excepciones.FuncionBusquedaException;
import Excepciones.FuncionEliminacionException;
import Excepciones.FuncionRegistroException;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Abraham Coronel Bringas
 */
public interface IManejoFunciones {
    
    public FuncionDTO registraFuncion(FuncionDTO funcionDTO) throws FuncionRegistroException;

    public Boolean eliminarFuncion(Long id) throws FuncionEliminacionException;

    public FuncionDTO buscarFuncion(Long id) throws FuncionBusquedaException;

    public List<FuncionDTO> buscarFuncionesPelicula(String pelicula) throws FuncionBusquedaException;

    public List<FuncionDTO> buscarFuncionesActivas() throws FuncionBusquedaException;
    
    // metodos de observer, falta corregir desde negocio, deben ser DTOs y no entidades normales
    public void suscribirClienteAFuncion(ClienteDTO cliente, String idFuncion) throws FuncionBusquedaException;

    public void desuscribirClienteDeFuncion(ClienteDTO cliente, String idFuncion) throws FuncionBusquedaException;

    public boolean cambiarEstadoFuncion(String idFuncion, Boolean nuevoEstado) throws FuncionBusquedaException;

    public boolean cambiarHorarioFuncion(Long idFuncion, LocalDateTime nuevoHorario) throws FuncionBusquedaException;

    public boolean cambiarSalaFuncion(String idFuncion, SalaDTO nuevaSala) throws FuncionBusquedaException;

    public boolean cambiarPrecioFuncion(Long idFuncion, Double nuevoPrecio) throws FuncionBusquedaException;
    
}
