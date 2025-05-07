/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Excepciones.funciones.FuncionEliminacionException;
import DTOs.FuncionDTO;
import Excepciones.funciones.FuncionBusquedaException;
import Excepciones.funciones.FuncionRegistroException;
import entidades.Cliente;
import entidades.Sala;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Ramon Valencia
 */
public interface IFuncionBO {

    public FuncionDTO registraFuncion(FuncionDTO funcionDTO) throws FuncionRegistroException;

    public Boolean eliminarFuncion(FuncionDTO funcionDTO) throws FuncionEliminacionException;

    public FuncionDTO buscarFuncion(String id) throws FuncionBusquedaException;

    public List<FuncionDTO> buscarFuncionesPelicula(String pelicula) throws FuncionBusquedaException;

    public List<FuncionDTO> buscarFuncionesActivas() throws FuncionBusquedaException;
    
    // metodos de observer
    
    public void suscribirClienteAFuncion(Cliente cliente, Long idFuncion) throws FuncionBusquedaException;

    public void desuscribirClienteDeFuncion(Cliente cliente, Long idFuncion) throws FuncionBusquedaException;

    public boolean cambiarEstadoFuncion(Long idFuncion, boolean nuevoEstado) throws FuncionBusquedaException;

    public boolean cambiarHorarioFuncion(Long idFuncion, LocalDateTime nuevoHorario) throws FuncionBusquedaException;

    public boolean cambiarSalaFuncion(Long idFuncion, Sala nuevaSala) throws FuncionBusquedaException;

    public boolean cambiarPrecioFuncion(Long idFuncion, Double nuevoPrecio) throws FuncionBusquedaException;
}
