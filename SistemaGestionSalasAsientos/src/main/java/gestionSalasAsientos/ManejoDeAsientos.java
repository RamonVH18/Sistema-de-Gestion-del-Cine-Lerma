/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestionSalasAsientos;

import BOs.AsientoFuncionBO;
import DTOs.AsientoDTO;
import DTOs.AsientoFuncionDTO;
import DTOs.EstadisticaSalaDTO;
import DTOs.FuncionDTO;
import DTOs.SalaViejaDTO;
import Excepciones.asientoFuncion.AsientoFuncionBusquedaException;
import Excepciones.asientoFuncion.AsientoFuncionEliminacionException;
import Excepciones.asientoFuncion.AsientoFuncionRegistroException;
import Excepciones.asientoFuncion.ErrorAlObtenerEstadisticasException;
import Excepciones.asientos.ErrorCargarAsientoException;
import Excepciones.asientos.ErrorEliminacionAsientosException;
import Excepciones.asientos.ErrorGeneracionAsientoFuncionException;
import Excepciones.asientos.ErrorObtencionEstadisticasException;
import Excepciones.asientos.ValidacionAsientosException;
import Interfaces.IAsientoFuncionBO;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Clase para poder gestionar los asientos
 * @author Ramon Valencia
 */
public class ManejoDeAsientos implements IManejoDeAsientos {

    private static ManejoDeAsientos instancia;
    private final IAsientoFuncionBO asientoBO = AsientoFuncionBO.getInstance();
    /**
     * Constructor vacio
     */
    private ManejoDeAsientos() {

    }
    /**
     * Metodo para obtener la instancia de esta clase
     * @return 
     */
    public static ManejoDeAsientos getInstanceAsientos() {
        if (instancia == null) {
            instancia = new ManejoDeAsientos();
        }
        return instancia;
    }
    /**
     * Metodo para registrar los asientos de una funcion
     * @param funcion
     * @param sala
     * @return
     * @throws ErrorGeneracionAsientoFuncionException 
     */
    @Override
    public List<AsientoFuncionDTO> registrarAsientoFuncion(FuncionDTO funcion, SalaViejaDTO sala) throws ErrorGeneracionAsientoFuncionException {
        try {
            List<AsientoFuncionDTO> asientosRegistrar = new ArrayList<>();
            validarExistenciaAsientos(funcion);
            for (int i = 0; i < sala.getNumAsientos(); i++) {
                AsientoDTO asiento = sala.getAsientos().get(i);
                AsientoFuncionDTO asientoF = new AsientoFuncionDTO(funcion.getIdFuncion(), asiento.getNumAsiento(), sala.getNumSala(), Boolean.TRUE, null);
                asientosRegistrar.add(asientoF);
            }
            asientosRegistrar = asientoBO.registrarAsientosFuncion(asientosRegistrar);
            return asientosRegistrar;
        } catch (AsientoFuncionRegistroException e) {
            throw new ErrorGeneracionAsientoFuncionException("No se pudieron generar los asientos de la funcion correctamente");
        } catch (ValidacionAsientosException e) {
            throw new ErrorGeneracionAsientoFuncionException("No se pudieron generar los asientos de la funcion correctamente: " + e.getMessage());
        }
    }
    /**
     * Metodo para eliminar asientosFuncion
     * @param idFuncion
     * @return
     * @throws ErrorEliminacionAsientosException 
     */
    @Override
    public Boolean eliminarAsientos(String idFuncion) throws ErrorEliminacionAsientosException {
        
        try {
            if (idFuncion == null || idFuncion.isBlank()) {
                throw new ErrorEliminacionAsientosException("Debe ingresar una funcion existente");
            }
            return asientoBO.eliminarAsientosFuncion(idFuncion);
        } catch (AsientoFuncionEliminacionException e) {
            throw new ErrorEliminacionAsientosException("No se pudieron eliminar los asientos de la funcion");
        }
        
    }
    /**
     * Metodo para validar la existencia de asientos en una funcion
     * @param funcion
     * @throws ValidacionAsientosException 
     */
    private void validarExistenciaAsientos(FuncionDTO funcion) throws ValidacionAsientosException {
        try {
            List<AsientoFuncionDTO> asientos = asientoBO.obtenerAsientosFuncion(funcion);
            if (!asientos.isEmpty()) {
                throw new ValidacionAsientosException("Ya existen asientos para esta funcion");
            }
        } catch (AsientoFuncionBusquedaException e) {
            throw new ValidacionAsientosException("Ya existen asientos para esta funcion");
        }
    }
    /**
     * Metodo para cargar la lista de aisentos de una funcion
     * @param funcion
     * @param mostrarDisponibles
     * @return
     * @throws ErrorCargarAsientoException 
     */
    @Override
    public List<AsientoFuncionDTO> cargarListaAsientos(FuncionDTO funcion, Boolean mostrarDisponibles) throws ErrorCargarAsientoException {
        try {
            if (mostrarDisponibles) {
                return asientoBO.obtenerAsientosDisponibles(funcion);
            } else {
                return asientoBO.obtenerAsientosFuncion(funcion);
            }
        } catch (AsientoFuncionBusquedaException e) {
            throw new ErrorCargarAsientoException("Hubo un problema al cargar la lista de asientos de la funcion.");
        }
    }
    /**
     * Metodo para obtener las estadisticas de las salas
     * @return
     * @throws ErrorObtencionEstadisticasException 
     */
    @Override
    public List<EstadisticaSalaDTO> obtenerEstadisticasSalas() throws ErrorObtencionEstadisticasException {
        try {
            List<EstadisticaSalaDTO> estadisticas = asientoBO.obtenerEstadisticasSala();
            estadisticas.sort(Comparator.comparing(p -> p.getNumSala()));
            if (estadisticas.isEmpty()) {
                throw new ErrorObtencionEstadisticasException("");
            }
            return estadisticas;
        } catch (ErrorAlObtenerEstadisticasException e) {
            throw new ErrorObtencionEstadisticasException("No se pudieron obtener las estadisticas de las salas. Favor de intentar luego.");
        }
    }
}
