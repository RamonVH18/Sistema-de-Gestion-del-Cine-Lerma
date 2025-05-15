/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BOs;

import DAOs.FuncionDAO;
import DTOs.FuncionDTO;
import Excepciones.Funciones.FuncionDuracionIncorrectaException;
import Excepciones.Funciones.FuncionNoEncontradaException;
import Excepciones.Funciones.FuncionSalaOcupadaException;
import Excepciones.Funciones.FuncionSalaVaciaException;
import Excepciones.funciones.FuncionEliminarException;
import Excepciones.funciones.FuncionFechaValidaException;
import Excepciones.funciones.FuncionRegistrarException;
import Excepciones.funciones.FuncionValidadaException;
import Interfaces.IFuncionBO;
import Interfaces.IFuncionDAO;
import Mappers.FuncionMapper;
import entidades.Funcion;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Abraham Coronel Bringas
 */
public class FuncionBO implements IFuncionBO {

    private static FuncionBO instanceFuncionBO;
    private final IFuncionDAO funcionDAO = FuncionDAO.getInstanceDAO();
    private final FuncionMapper funcionMapper = new FuncionMapper();

    private FuncionBO() {
    }

    public static FuncionBO getInstanceDAO() {
        if (instanceFuncionBO == null) {
            instanceFuncionBO = new FuncionBO();
        }
        return instanceFuncionBO;
    }

    @Override
    public FuncionDTO registraFuncion(FuncionDTO funcionDTO) throws FuncionRegistrarException {

        if (funcionDTO == null) {
            throw new FuncionRegistrarException("La funcion no puede ser nulo");
        }

        try {
            Funcion funcionRegistrar = funcionMapper.toFuncionEntidad(funcionDTO);
            Funcion funcionRegistrado = funcionDAO.registrarFuncion(funcionRegistrar);
            return funcionMapper.toFuncionDTO(funcionRegistrado);
        } catch (FuncionSalaOcupadaException e) {
            throw new FuncionRegistrarException("Error al registrar la funcion" + e.getMessage(), e);
        } catch (FuncionSalaVaciaException e) {
            throw new FuncionRegistrarException("Error la sala es nula o no existe" + e.getMessage(), e);
        }

    }

    @Override
    public Boolean eliminarFuncion(FuncionDTO funcionDTO) throws FuncionEliminarException {
        if (funcionDTO == null) {
            throw new FuncionEliminarException("La funcion no puede ser nulo.");
        }
        try {
            Funcion funcionEliminar = funcionMapper.toFuncionEntidad(funcionDTO);
            Funcion funcionEliminada = funcionDAO.eliminarFuncion(funcionEliminar);

            if (funcionEliminada == null) {
                return false;
            }
            return true;
        } catch (FuncionNoEncontradaException e) {
            throw new FuncionEliminarException("Error al eliminar usuario", e);
        }

    }

    @Override
    public List<FuncionDTO> buscarFuncionesPelicula(String nombrePelicula) {
        List<FuncionDTO> funcionesDTO = new ArrayList<>();
        List<Funcion> funcionesEntity = funcionDAO.buscarFuncionesPelicula(nombrePelicula);
        for (Funcion funcion : funcionesEntity) {
            funcionesDTO.add(funcionMapper.toFuncionDTO(funcion));
        }

        return funcionesDTO;
    }

    @Override
    public List<FuncionDTO> buscarFuncionFechaInicio(LocalDateTime fechaHora) throws FuncionFechaValidaException {
        if (fechaHora == null) {
            throw new FuncionFechaValidaException("La fecha y la hora no pueden ser nulas");
        }

        if (fechaHora.isBefore(LocalDateTime.now())) {
            throw new FuncionFechaValidaException("La fecha y hora deben ser futuras");
        }

        List<Funcion> funcionesEntity = funcionDAO.buscarFuncionFechaInicio(fechaHora);
        List<FuncionDTO> funcionesDTO = new ArrayList<>();
        for (Funcion funcion : funcionesEntity) {
            funcionesDTO.add(funcionMapper.toFuncionDTO(funcion));
        }
        return funcionesDTO;
    }

    @Override
    public LocalDateTime calcularHoraTerminoFuncion(String idFuncion) throws FuncionValidadaException {
        if (idFuncion == null || idFuncion.isEmpty()) {
            throw new FuncionValidadaException("El id de la funcion no puede ser nulo");
        }

        try {
            return funcionDAO.calcularHoraTerminoFuncion(idFuncion);
        } catch (FuncionNoEncontradaException | FuncionDuracionIncorrectaException e) {
            throw new FuncionValidadaException("Error al calcular la hora" + e.getMessage(), e);
        }
    }

}
