/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GestionFunciones;

import BOs.FuncionBO;
import DTOs.ClienteDTO;
import DTOs.FuncionDTO;
import DTOs.HistorialFuncionesDTO;
import DTOs.SalaDTO;
import Excepciones.FuncionSolapamientoSalaException;
import Excepciones.FuncionCapacidadSalaException;
import Excepciones.FuncionDatosIncorrectosException;
import Excepciones.funciones.FuncionRegistrarException;
import Interfaces.IFuncionBO;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Abraham Coronel Bringas
 */
public class ManejoFunciones implements IManejoFunciones {

    private static ManejoFunciones instanceManejoFunciones;

    private ManejoFunciones() {
    }

    public static ManejoFunciones getInstanceDAO() {
        if (instanceManejoFunciones == null) {
            instanceManejoFunciones = new ManejoFunciones();
        }
        return instanceManejoFunciones;
    }

    private final IFuncionBO funcionBO = FuncionBO.getInstanceDAO();

    @Override
    public FuncionDTO registraFuncion(FuncionDTO funcionDTO) throws FuncionDatosIncorrectosException {
        // Validar que funcionDTO no sea nulo o vacio
        if (funcionDTO == null) {
            throw new FuncionDatosIncorrectosException("Error: Los datos de la funcion no pueden estar vacios.");
        }

        // Validar que la fecha y hora no sean nulos
        if (funcionDTO.getFechaHora() == null) {
            throw new FuncionDatosIncorrectosException("Error: Los datos de la fecha y hora no pueden ser vacios");
        }

        // Validar que la fecha no halla pasado
        LocalDateTime ahora = LocalDateTime.now();
        if (funcionDTO.getFechaHora().isBefore(ahora)) {
            throw new FuncionDatosIncorrectosException("Error: Los datos de la fecha ya pasaron y no son actuales");
        }

        // Validar que sala exista y no sea nula
        if (funcionDTO.getSala() == null || funcionDTO.getSala().trim().isEmpty()) {
            throw new FuncionDatosIncorrectosException("Error: La sala debe ser obligatoria, no puede haber una funcion sin sala asignada.");
        }

        // Validar el precio para que sea positivo
        if (funcionDTO.getPrecio() <= 0) {
            throw new FuncionDatosIncorrectosException("Error: El precio debe ser un valor positivo.");
        }

        // Validar que no se intente solapar 2 salas
        // Validar que la capacidad de la sala sea correcta
        try {
            return funcionBO.registraFuncion(funcionDTO);

        } catch (FuncionRegistrarException ex) {
            throw new FuncionDatosIncorrectosException("Error: No se puedo registrar la funcion", ex);
        }
    }

    @Override
    public Boolean eliminarFuncion(FuncionDTO funcionDTO) throws FuncionDatosIncorrectosException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<HistorialFuncionesDTO> buscarHistorialFunciones() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public FuncionDTO validarFuncion(FuncionDTO funcionDTO) throws FuncionDatosIncorrectosException, FuncionSolapamientoSalaException, FuncionCapacidadSalaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<FuncionDTO> buscarFuncionesPelicula(String pelicula) throws FuncionDatosIncorrectosException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<FuncionDTO> buscarFuncionesActivas() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void suscribirClienteAFuncion(ClienteDTO cliente, String idFuncion) throws FuncionSolapamientoSalaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void desuscribirClienteDeFuncion(ClienteDTO cliente, String idFuncion) throws FuncionSolapamientoSalaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean cambiarEstadoFuncion(String idFuncion, Boolean nuevoEstado) throws FuncionSolapamientoSalaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean cambiarHorarioFuncion(String idFuncion, LocalDateTime nuevoHorario) throws FuncionSolapamientoSalaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean cambiarSalaFuncion(String idFuncion, SalaDTO nuevaSala) throws FuncionSolapamientoSalaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean cambiarPrecioFuncion(String idFuncion, Double nuevoPrecio) throws FuncionSolapamientoSalaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
