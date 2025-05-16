/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GestionFunciones;

import BOs.AsientoFuncionBO;
import BOs.FuncionBO;
import BOs.SalaBO;
import DTOs.AsientoFuncionDTO;
import DTOs.FuncionDTO;
import Excepciones.FuncionBoletosVendidosException;
import Excepciones.FuncionSolapamientoSalaException;
import Excepciones.FuncionCapacidadSalaException;
import Excepciones.FuncionDatosIncorrectosException;
import Excepciones.FuncionDuracionException;
import Excepciones.asientoFuncion.AsientoFuncionBusquedaException;
import Excepciones.funciones.FuncionEliminarException;
import Excepciones.funciones.FuncionFechaValidaException;
import Excepciones.funciones.FuncionRegistrarException;
import Excepciones.funciones.FuncionValidadaException;
import Interfaces.IAsientoFuncionBO;
import Interfaces.IFuncionBO;
import Interfaces.ISalaBO;
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
    private final IAsientoFuncionBO asientoFuncionBO = AsientoFuncionBO.getInstance();
    private final ISalaBO salaBO = SalaBO.getInstanceBO();

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

        try {
            List<AsientoFuncionDTO> asientos = asientoFuncionBO.obtenerAsientosFuncion(funcionDTO);
            Boolean hayBoletosVendidos = asientos.stream()
                    .anyMatch(asiento -> !asiento.isDisponibilidad());

            if (hayBoletosVendidos) {
                throw new FuncionBoletosVendidosException("No se puede eliminar una funcion si tiene boletos vendidos");
            }
            return funcionBO.eliminarFuncion(funcionDTO);
        } catch (FuncionEliminarException e) {
            throw new FuncionDatosIncorrectosException("Error al eliminar funcion" + e.getMessage());
        } catch (AsientoFuncionBusquedaException e) {
            throw new FuncionDatosIncorrectosException("Error al buscar asientos disponibles ");
        }
    }

    @Override
    public List<FuncionDTO> buscarFunciones(String nombrePelicula, LocalDateTime fechaHora) throws FuncionDatosIncorrectosException {
        try {
            if (nombrePelicula != null && !nombrePelicula.isEmpty() && fechaHora != null) {
                List<FuncionDTO> funcionesPorNombre = funcionBO.buscarFuncionesPelicula(nombrePelicula);
                List<FuncionDTO> funcionesPorFecha = funcionBO.buscarFuncionFechaInicio(fechaHora);

                funcionesPorNombre.retainAll(funcionesPorFecha);
                return funcionesPorNombre;
            }

            if (nombrePelicula != null && !nombrePelicula.isEmpty()) {
                return funcionBO.buscarFuncionesPelicula(nombrePelicula);
            }

            if (fechaHora != null) {
                return funcionBO.buscarFuncionFechaInicio(fechaHora);
            }

            throw new FuncionDatosIncorrectosException("Seleccione por lo menos 1 filtro");
        } catch (FuncionFechaValidaException e) {
            throw new FuncionDatosIncorrectosException("Fecha invalida" + e.getMessage());
        }
    }

    @Override
    public LocalDateTime calcularHoraTerminoFuncion(String idFuncion) throws FuncionDuracionException {
        if (idFuncion == null || idFuncion.isEmpty()) {
            throw new FuncionDuracionException("El ID de la funcion es requerido.");
        }

        if (!org.bson.types.ObjectId.isValid(idFuncion)) {
            throw new FuncionDuracionException("El ID de la funcion no tiene un formato valido.");
        }

        try {
            return funcionBO.calcularHoraTerminoFuncion(idFuncion);

        } catch (FuncionValidadaException e) {
            throw new FuncionDuracionException("Error al calcular la hora de término: " + e.getMessage(), e);
        }
    }
}
