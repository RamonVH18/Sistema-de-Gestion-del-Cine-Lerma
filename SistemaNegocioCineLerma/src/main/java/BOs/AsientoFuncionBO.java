/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BOs;

import DAOs.AsientoFuncionDAO;
import DTOs.AsientoFuncionDTO;
import DTOs.FuncionDTO;
import Excepciones.AsientoFuncion.FalloMostrarAsientosFuncionException;
import Excepciones.PersistenciaException;
import Excepciones.asientoFuncion.AsientoFuncionBusquedaException;
import Excepciones.asientoFuncion.AsientoFuncionRegistroException;
import Excepciones.asientoFuncion.AsientoFuncionReservaException;
import Interfaces.IAsientoFuncionBO;
import Interfaces.IAsientoFuncionDAO;
import Mappers.AsientoFuncionMapper;
import Mappers.FuncionMapper;
import entidades.AsientoFuncion;
import entidades.Funcion;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ramon Valencia
 */
public class AsientoFuncionBO implements IAsientoFuncionBO {

    private static AsientoFuncionBO instance;
    private final IAsientoFuncionDAO asientoFuncionDAO = AsientoFuncionDAO.getInstanceDAO();
    private final AsientoFuncionMapper asientoFuncionMapper = new AsientoFuncionMapper();
    private final FuncionMapper funcionMapper = new FuncionMapper();

    private AsientoFuncionBO() {

    }

    public static AsientoFuncionBO getInstance() {
        if (instance == null) {
            instance = new AsientoFuncionBO();
        }
        return instance;
    }

    @Override
    public List<AsientoFuncionDTO> registrarAsientosFuncion(List<AsientoFuncionDTO> asientos) throws AsientoFuncionRegistroException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Boolean reservarAsientosFuncion(List<AsientoFuncionDTO> asientos) throws AsientoFuncionReservaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<AsientoFuncionDTO> obtenerAsientosFuncion(FuncionDTO funcionDTO) throws AsientoFuncionBusquedaException {
        try {
            Funcion funcion = funcionMapper.toFuncionEntidad(funcionDTO);
            List<AsientoFuncion> asientosFuncion = asientoFuncionDAO.mostrarListaAsientosPorFuncion(funcion, Boolean.FALSE);
            List<AsientoFuncionDTO> asientosMapeados = asientoFuncionMapper.toAsientoFuncionDTO(asientosFuncion);
            
            return asientosMapeados;
        } catch (FalloMostrarAsientosFuncionException e) {
            throw new AsientoFuncionBusquedaException("Hubo un error al consultar los asientos de la funcion: " + e.getMessage());
        }
    }

    @Override
    public List<AsientoFuncionDTO> obtenerAsientosDisponibles(FuncionDTO funcion) throws AsientoFuncionBusquedaException {
//        try {
//            List<AsientoFuncionDTO> asientosFuncionDTO = new ArrayList<>();
//            Funcion funcionEntity = funcionMapper.toFuncionEntidad(funcion);
////            List<AsientoFuncion> asientosFuncionEntity = asientoFuncionDAO.mostrarAsientosDisponibles(funcionEntity);
////            for (AsientoFuncion asientoFuncion : asientosFuncionEntity) {
////                AsientoFuncionDTO asientoFuncionDTO = asientoFuncionMapper.toAsientoFuncionDTO(asientoFuncion);
////                asientosFuncionDTO.add(asientoFuncionDTO);
////            }
//            
//            return asientosFuncionDTO;
//        } catch (PersistenciaException e) {
//            throw new AsientoFuncionBusquedaException("Ocurrio un error durante la busqueda de los asientos disponibles.");
//        }
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
