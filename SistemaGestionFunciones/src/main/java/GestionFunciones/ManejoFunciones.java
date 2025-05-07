/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GestionFunciones;

import DTOs.ClienteDTO;
import DTOs.FuncionDTO;
import DTOs.SalaDTO;
import Excepciones.FuncionBusquedaException;
import Excepciones.FuncionEliminacionException;
import Excepciones.FuncionRegistroException;
import Interfaces.IFuncionBO;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Abraham Coronel Bringas
 */
public class ManejoFunciones implements IManejoFunciones {

    private IFuncionBO funcionBO;

    @Override
    public FuncionDTO registraFuncion(FuncionDTO funcionDTO) throws FuncionRegistroException {
        // Validar que funcionDTO no sea nulo o vacio
        if (funcionDTO == null) {
            throw new FuncionRegistroException("Error: Los datos de la funcion no pueden estar vacios.");
        }

        // Validar que la fecha y hora no sean nulos
        if (funcionDTO.getFechaHora() == null) {
            throw new FuncionRegistroException("Error: Los datos de la fecha y hora no pueden ser vacios");
        }

        // Validar que la fecha no halla pasado
        LocalDateTime ahora = LocalDateTime.now();
        if (funcionDTO.getFechaHora().isBefore(ahora)) {
            throw new FuncionRegistroException("Error: Los datos de la fecha ya pasaron y no son actuales");
        }

        // Validar que sala exista y no sea nula
        if (funcionDTO.getSala() == null || funcionDTO.getSala().trim().isEmpty()) {
            throw new FuncionRegistroException("Error: La sala debe ser obligatoria, no puede haber una funcion sin sala asignada.");
        }

        // Validar el precio para que sea positivo
        if (funcionDTO.getPrecio() <= 0) {
            throw new FuncionRegistroException("Error: El precio debe ser un valor positivo.");
        }

        try {
            return funcionBO.registraFuncion(funcionDTO);

        } catch (Exception ex) {
            throw new FuncionRegistroException("Error: No se puedo registrar la funcion", ex);
        }
    }

    @Override
    public Boolean eliminarFuncion(Long id) throws FuncionEliminacionException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public FuncionDTO buscarFuncion(Long id) throws FuncionBusquedaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<FuncionDTO> buscarFuncionesPelicula(String pelicula) throws FuncionBusquedaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<FuncionDTO> buscarFuncionesActivas() throws FuncionBusquedaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void suscribirClienteAFuncion(ClienteDTO cliente, String idFuncion) throws FuncionBusquedaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void desuscribirClienteDeFuncion(ClienteDTO cliente, String idFuncion) throws FuncionBusquedaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean cambiarEstadoFuncion(String idFuncion, Boolean nuevoEstado) throws FuncionBusquedaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean cambiarHorarioFuncion(Long idFuncion, LocalDateTime nuevoHorario) throws FuncionBusquedaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean cambiarSalaFuncion(String idFuncion, SalaDTO nuevaSala) throws FuncionBusquedaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean cambiarPrecioFuncion(Long idFuncion, Double nuevoPrecio) throws FuncionBusquedaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
