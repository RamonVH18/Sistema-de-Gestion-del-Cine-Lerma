/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BOs;

import DAOs.AsientoFuncionDAO;
import DTOs.AsientoFuncionDTO;
import DTOs.FuncionDTO;
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

/**
 *
 * @author Ramon Valencia
 */
public class AsientoFuncionBO implements IAsientoFuncionBO {

    private static AsientoFuncionBO instance;
    private final IAsientoFuncionDAO asientoFuncionDAO = AsientoFuncionDAO.getInstance();
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
    public List<AsientoFuncionDTO> obtenerAsientosFuncion(FuncionDTO funcion) throws AsientoFuncionBusquedaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<AsientoFuncionDTO> obtenerAsientosDisponibles(FuncionDTO funcion) throws AsientoFuncionBusquedaException {
        try {
            List<AsientoFuncionDTO> asientosFuncionDTO = new ArrayList<>();
            Funcion funcionEntity = funcionMapper.toFuncionEntidad(funcion);
            List<AsientoFuncion> asientosFuncionEntity = asientoFuncionDAO.mostrarAsientosDisponibles(funcionEntity);
            for (AsientoFuncion asientoFuncion : asientosFuncionEntity) {
                AsientoFuncionDTO asientoFuncionDTO = asientoFuncionMapper.toAsientoFuncionDTO(asientoFuncion);
                asientosFuncionDTO.add(asientoFuncionDTO);
            }
            
            return asientosFuncionDTO;
        } catch (PersistenciaException e) {
            throw new AsientoFuncionBusquedaException("Ocurrio un error durante la busqueda de los asientos disponibles.");
        }
    }


}
