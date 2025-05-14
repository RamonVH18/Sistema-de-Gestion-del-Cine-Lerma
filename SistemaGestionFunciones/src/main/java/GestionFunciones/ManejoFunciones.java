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
import Excepciones.FuncionBoletosVendidosException;
import Excepciones.FuncionSolapamientoSalaException;
import Excepciones.FuncionCapacidadSalaException;
import Excepciones.FuncionDatosIncorrectosException;
import Excepciones.funciones.FuncionEliminarException;
import Excepciones.funciones.FuncionFechaValidaException;
import Excepciones.funciones.FuncionRegistrarException;
import Interfaces.IFuncionBO;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    public FuncionDTO registraFuncion(FuncionDTO funcionDTO) throws FuncionDatosIncorrectosException, FuncionSolapamientoSalaException, FuncionCapacidadSalaException {
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

        } catch (FuncionRegistrarException e) {
            throw new FuncionDatosIncorrectosException("Error: No se puedo registrar la funcion", e);
        }
    }

    @Override
    public Boolean eliminarFuncion(FuncionDTO funcionDTO) throws FuncionDatosIncorrectosException, FuncionBoletosVendidosException {
        if (funcionDTO == null || funcionDTO.getId() == null) {
            throw new FuncionDatosIncorrectosException("La funcion no existe o los datos no son validos");
        }
        
        // Validar que no haya boletos vendidos

        try {
            return funcionBO.eliminarFuncion(funcionDTO);
        } catch (FuncionEliminarException e) {
            throw new FuncionDatosIncorrectosException("Error al eliminar funcion" + e.getMessage());
        }
    }

    @Override
    public List<FuncionDTO> buscarFuncionesPelicula(String pelicula) {

        return null;

    }
}
