/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BOs;

import DTOs.FuncionDTO;
import DTOs.FuncionesPorPeliculaDTO;
import Excepciones.funciones.FuncionBusquedaException;
import Excepciones.funciones.FuncionEliminacionException;
import Excepciones.funciones.FuncionRegistroException;
import Interfaces.IFuncionBO;
import Mappers.FuncionMapper;
import entidades.Funcion;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Abraham Coronel Bringas
 */
public class FuncionBO implements IFuncionBO {

//    private static FuncionDAO instanceFuncionDAO;
//
//    private FuncionBO() {
//    }
//
//    public static FuncionDAO getInstanceDAO() {
//        if (instanceFuncionDAO == null) { // para la primera vez que se llama
//            instanceFuncionDAO = new FuncionDAO();
//        }
//        return instanceFuncionDAO;
//    }

    @Override
    public FuncionDTO registraFuncion(FuncionDTO funcionDTO) throws FuncionRegistroException {
//        // Validar que funcionDTO no sea nulo o vacio
//        if (funcionDTO == null) {
//            throw new FuncionRegistroException("Error: Los datos de la funcion no pueden estar vacios.");
//        }
//
//        // Validar que la fecha y hora no sean nulos
//        if (funcionDTO.getFechaHora() == null) {
//            throw new FuncionRegistroException("Error: Los datos de la fecha y hora no pueden ser vacios");
//        }
//
//        // Validar que la fecha no halla pasado
//        LocalDateTime ahora = LocalDateTime.now();
//        if (funcionDTO.getFechaHora().isBefore(ahora)) {
//            throw new FuncionRegistroException("Error: Los datos de la fecha ya pasaron y no son actuales");
//        }
//
//        // Validar que sala exista y no sea nula
//        if (funcionDTO.getSala() == null || funcionDTO.getSala().trim().isEmpty()) {
//            throw new FuncionRegistroException("Error: La sala debe ser obligatoria, no puede haber una funcion sin sala asignada.");
//        }
//
//        // Validar el precio para que sea positivo
//        if (funcionDTO.getPrecio() <= 0) {
//            throw new FuncionRegistroException("Error: El precio debe ser un valor positivo.");
//        }
//
//        // Se convierte el DTO en una entidad para su almacenamiento en la base de datos
//        Funcion funcionARegistrar = FuncionMapper.toFuncionEntidad(funcionDTO);
//
//        try {
//            Funcion funcionRegistrada = FuncionDAO.registrarFuncion(funcionARegistrar);
//
//            if (funcionRegistrada == null || funcionRegistrada.getId() == null) {
//                throw new FuncionRegistroException("Error: No se encontro la funcion a registrar.");
//            }
//            // Se convierte la entidad de vuelta a DTO y se retorna
//            return FuncionMapper.toFuncionDTO(funcionRegistrada);
//
//        } catch (PersistenciaException ex) {
//            throw new FuncionRegistroException("Error: No se puedo registrar la funcion", ex);
//        }
        
        return null;

    }

    @Override
    public Boolean eliminarFuncion(Long id) throws FuncionEliminacionException {

//        // Validar que el id sea valido
//        if (id == null || id <= 0) {
//            throw new FuncionEliminacionException("Error: el id es invalido");
//        }
//
//        //Falta validacion que la funcion no tenga boletos vendidos.
//        try {
//            return funcionDAO.eliminarFuncion(id);
//
//        } catch (PersistenciaException ex) {
//            throw new FuncionEliminacionException("Error: no se pudo eliminar la funcion", ex);
//        }
        
        return null;

    }

    @Override
    public FuncionDTO buscarFuncion(Long id) throws FuncionBusquedaException {
//        // Validar que el id sea valido
//        if (id == null || id <= 0) {
//            throw new FuncionBusquedaException("Error: el id es invalido");
//        }
//
//        try {
//            Funcion funcion = funcionDAO.obtenerFuncionPorId(id);
//
//            if (funcion == null) {
//                throw new FuncionBusquedaException("Error: funcion no encontrada");
//            }
//
//            LocalDateTime ahora = LocalDateTime.now();
//            if (funcion.getFechaHora().isBefore(ahora)) {
//                throw new FuncionRegistroException("Error: La funcion ya ocurrio");
//            }
//
//            return FuncionMapper.toFuncionDTO(funcion);
//
//        } catch (PersistenciaException ex) {
//            throw new FuncionRegistroException("Error: al buscar la funcion: " + ex.getMessage());
//        }
        
        return null;
    }

    @Override
    public FuncionesPorPeliculaDTO buscarFuncionPelicula(String pelicula) throws FuncionBusquedaException {
        
        return null;
        
    }

    @Override
    public List<FuncionDTO> buscarFuncionesActivas() throws FuncionBusquedaException {
        
        return null;
        
    }

}
