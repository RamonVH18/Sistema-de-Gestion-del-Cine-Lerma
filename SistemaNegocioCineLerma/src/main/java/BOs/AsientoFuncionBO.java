/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BOs;

import DAOs.AsientoFuncionDAO;
import DAOs.ClienteDAO;
import DTOs.AsientoFuncionDTO;
import DTOs.ClienteDTO;
import DTOs.EstadisticaSalaDTO;
import DTOs.FuncionDTO;
import DTOs.GananciaSalaDTO;
import Excepciones.AsientoFuncion.FalloCreacionAsientosFuncionException;
import Excepciones.AsientoFuncion.FalloEliminacionAsientosFuncion;
import Excepciones.AsientoFuncion.FalloMostrarAsientosFuncionException;
import Excepciones.AsientoFuncion.FalloOcuparAsientosFuncionException;
import Excepciones.asientoFuncion.AsientoFuncionBusquedaException;
import Excepciones.asientoFuncion.AsientoFuncionEliminacionException;
import Excepciones.asientoFuncion.AsientoFuncionRegistroException;
import Excepciones.asientoFuncion.AsientoFuncionReservaException;
import Excepciones.asientoFuncion.ErrorAlObtenerEstadisticasException;
import Excepciones.salas.ErrorCalculoEstadisticasSalaException;
import Excepciones.usuarios.EncontrarClienteException;
import Interfaces.IAsientoFuncionBO;
import Interfaces.IAsientoFuncionDAO;
import Interfaces.IClienteDAO;
import Interfaces.IUsuarioBO;
import Interfaces.mappers.IAsientoFuncionMapper;
import Interfaces.mappers.IClienteMapper;
import Mappers.AsientoFuncionMapper;
import Mappers.ClienteMapper;
import Mappers.FuncionMapper;
import entidades.AsientoFuncion;
import entidades.Cliente;
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
    private final IClienteMapper clienteMapper = new ClienteMapper();
    private final IClienteDAO clienteDAO = ClienteDAO.getInstance();
    private final IAsientoFuncionMapper asientoFuncionMapper = new AsientoFuncionMapper();
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

        try {
            List<AsientoFuncion> asientosMapeados = asientoFuncionMapper.toAsientoFuncionEntidad(asientos);

            asientosMapeados = asientoFuncionDAO.agregarAsientosFuncion(asientosMapeados);

            return asientoFuncionMapper.toAsientoFuncionDTO(asientosMapeados);
        } catch (FalloCreacionAsientosFuncionException e) {
            throw new AsientoFuncionRegistroException("Hubo un error al consultar los asiento de la funcion: " + e.getMessage());
        }

    }

    @Override
    public Boolean eliminarAsientosFuncion(String idFuncion) throws AsientoFuncionEliminacionException {
        try {
            return asientoFuncionDAO.eliminarAsientosFuncion(idFuncion);
        } catch (FalloEliminacionAsientosFuncion e) {
            throw new AsientoFuncionEliminacionException("Hubo un error al eliminar los asientos de la funcion: " + e.getMessage());
        }
    }

    @Override
    public Boolean reservarAsientosFuncion(List<AsientoFuncionDTO> asientos, ClienteDTO clienteDTO) throws AsientoFuncionReservaException {
        try {
            List<AsientoFuncion> asientosMapeados = asientoFuncionMapper.toAsientoFuncionEntidad(asientos);
            
            Cliente cliente = clienteDAO.obtenerCliente(clienteDTO.getNombreUsuario(), clienteDTO.getContraseña());
            
            List<AsientoFuncion> asientosReservados = new ArrayList<>();
            AsientoFuncion asiento;
            for (int i = 0; i < asientosMapeados.size(); i++) {
                
                asiento = asientosMapeados.get(i);
                
                asiento.setIdCliente(cliente.getIdString());
                
                asientosReservados.add(asiento);
            
            }

            return asientoFuncionDAO.ocuparAsientos(asientosReservados);
        } catch (FalloOcuparAsientosFuncionException e) {
            throw new AsientoFuncionReservaException("Hubo un error al reservar los asientos: " + e.getMessage());
        } catch (EncontrarClienteException e) {
            throw new AsientoFuncionReservaException("Hubo un error al consultar el cliente en el asiento: " + e.getMessage());
        }
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
    public List<AsientoFuncionDTO> obtenerAsientosDisponibles(FuncionDTO funcionDTO) throws AsientoFuncionBusquedaException {
        try {
            Funcion funcion = funcionMapper.toFuncionEntidad(funcionDTO);
            List<AsientoFuncion> asientosGuardados = asientoFuncionDAO.mostrarListaAsientosPorFuncion(funcion, Boolean.TRUE);

            return asientoFuncionMapper.toAsientoFuncionDTO(asientosGuardados);
        } catch (FalloMostrarAsientosFuncionException e) {
            throw new AsientoFuncionBusquedaException("Hubo un error al consultar los asientos de la funcion: " + e.getMessage());
        }

    }

    @Override
    public List<EstadisticaSalaDTO> obtenerEstadisticasSala() throws ErrorAlObtenerEstadisticasException {
        try {
            List<GananciaSalaDTO> gananciasSalas = asientoFuncionDAO.obtenerEstadisticasDeSalas();
            GananciaSalaDTO ganancias;
            List<EstadisticaSalaDTO> estadisticas = new ArrayList<>();

            for (int i = 0; i < gananciasSalas.size(); i++) {
                ganancias = gananciasSalas.get(i);

                estadisticas.add(
                        new EstadisticaSalaDTO(ganancias.getNumSala(),
                                ganancias.getCapacidad(),
                                ganancias.getTotalGanado(),
                                ganancias.getAsientosVendidos(),
                                ganancias.getFuncionesRealizadas()
                        )
                );
            }
            return estadisticas;
        } catch (ErrorCalculoEstadisticasSalaException e) {
            throw new ErrorAlObtenerEstadisticasException("Hubo un error al consultar las estadisticas de las salas: " + e.getMessage());
        }
    }

}
