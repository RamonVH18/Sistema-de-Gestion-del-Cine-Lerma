/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;


import DTOs.FuncionDTO;
import Excepciones.funciones.FuncionFechaValidaException;
import Excepciones.funciones.FuncionDatosIncorrectosException;
import entidades.Cliente;
import entidades.Sala;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Ramon Valencia
 */
public interface IFuncionBO {
//
//    public FuncionDTO registraFuncion(FuncionDTO funcionDTO) throws FuncionDatosIncorrectosException, FuncionFechaValidaException;
//
//    public Boolean eliminarFuncion(FuncionDTO funcionDTO) throws FuncionBoletosVendidosException;
//
//    public FuncionDTO buscarFuncion(String id) throws FuncionFechaValidaException;
//
//    public List<FuncionDTO> buscarFuncionesPelicula(String pelicula) throws FuncionFechaValidaException;
//
    public List<FuncionDTO> buscarFuncionesActivas() throws FuncionFechaValidaException;
//    
//    // metodos de observer
//    
//    public void suscribirClienteAFuncion(Cliente cliente, Long idFuncion) throws FuncionFechaValidaException;
//
//    public void desuscribirClienteDeFuncion(Cliente cliente, Long idFuncion) throws FuncionFechaValidaException;
//
//    public boolean cambiarEstadoFuncion(Long idFuncion, boolean nuevoEstado) throws FuncionFechaValidaException;
//
//    public boolean cambiarHorarioFuncion(Long idFuncion, LocalDateTime nuevoHorario) throws FuncionFechaValidaException;
//
//    public boolean cambiarSalaFuncion(Long idFuncion, Sala nuevaSala) throws FuncionFechaValidaException;
//
//    public boolean cambiarPrecioFuncion(Long idFuncion, Double nuevoPrecio) throws FuncionFechaValidaException;
}
