/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BOs;

import DAOs.FuncionDAO;
import DTOs.FuncionDTO;
import DTOs.HistorialFuncionesDTO;
import Excepciones.Funciones.FuncionNoEncontradaException;
import Excepciones.Funciones.FuncionSalaOcupadaException;
import Excepciones.PersistenciaException;
import Excepciones.funciones.FuncionEliminarException;
import Excepciones.funciones.FuncionFechaValidaException;
import Excepciones.funciones.FuncionRegistrarException;
import Excepciones.funciones.FuncionValidadaException;
import Interfaces.IFuncionBO;
import Interfaces.IFuncionDAO;
import Mappers.FuncionMapper;
import ObservadorClienteFuncion.ObservadorClienteFuncion;
import entidades.Cliente;
import entidades.Funcion;
import entidades.Sala;
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
        if (instanceFuncionBO == null) { // para la primera vez que se llama
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
        try {
            List<Funcion> funcionesEntity = funcionDAO.buscarFuncionesPelicula(nombrePelicula);
            for (Funcion funcion : funcionesEntity) {
                funcionesDTO.add(funcionMapper.toFuncionDTO(funcion));
            }
        } catch (Exception e) {
            System.err.println("Error al buscar funciones: " + e.getMessage());
        }
        return funcionesDTO;
    }

    @Override
    public List<FuncionDTO> buscarFuncionesActivas() throws FuncionFechaValidaException {
        List<FuncionDTO> funcionesActivasDTO = new ArrayList<>();

        try {
            List<Funcion> funcionesActivasEntity = funcionDAO.buscarFuncionesActivas();
            for (Funcion funcionActivaEntity : funcionesActivasEntity) {
                FuncionDTO funcionActivaDTO = funcionMapper.toFuncionDTO(funcionActivaEntity);

                funcionesActivasDTO.add(funcionActivaDTO);
            }

            return funcionesActivasDTO;
        } catch (Exception e) {
            throw new FuncionFechaValidaException("Error al buscar las funciones activas.");
        }
    }

    @Override
    public List<HistorialFuncionesDTO> buscarHistorialFunciones() {
        return null;
    }

//
//    @Override
//    public void suscribirClienteAFuncion(Cliente cliente, Long idFuncion) throws FuncionFechaValidaException {
//        try {
//            // Verificar que la función existe
//            Funcion funcion = funcionDAO.buscarFuncionPorId(idFuncion);
//            if (funcion == null) {
//                throw new FuncionFechaValidaException("La función no existe");
//            }
//
//            // Crear observador para el cliente
//            ObservadorClienteFuncion observador = new ObservadorClienteFuncion(cliente);
//
//            // Registrar el observador
//            funcionDAO.agregarObservador(idFuncion, observador);
//
//        } catch (PersistenciaException e) {
//            throw new FuncionFechaValidaException("Error al suscribir cliente a la función: " + e.getMessage());
//        }
//    }
//
//    public void desuscribirClienteDeFuncion(Cliente cliente, Long idFuncion) throws FuncionFechaValidaException {
//        try {
//            // Verificar que la función existe
//            Funcion funcion = funcionDAO.buscarFuncionPorId(idFuncion);
//            if (funcion == null) {
//                throw new FuncionFechaValidaException("La función no existe");
//            }
//
//            //final ObjectId clienteId = cliente.getIdUsuario();
//            // Eliminar el observador del cliente para esta función
//            funcionDAO.eliminarObservadorPorFiltro(idFuncion, observador -> {
//                if (observador instanceof ObservadorClienteFuncion) {
//                    return ((ObservadorClienteFuncion) observador).getCliente().getIdUsuario().equals(clienteId);
//                }
//                return false;
//            });
//
//        } catch (PersistenciaException e) {
//            throw new FuncionFechaValidaException("Error al desuscribir cliente de la función: " + e.getMessage());
//        }
//    }
//
//    @Override
//    public boolean cambiarEstadoFuncion(Long idFuncion, boolean nuevoEstado) throws FuncionFechaValidaException {
//        try {
//            Funcion funcion = funcionDAO.buscarFuncionPorId(idFuncion);
//            if (funcion == null) {
//                throw new FuncionFechaValidaException("La función no existe");
//            }
//
//            funcion.setEstado(nuevoEstado);
//            return funcionDAO.actualizarFuncion(funcion);
//
//        } catch (PersistenciaException e) {
//            throw new FuncionFechaValidaException("Error al cambiar estado de la función: " + e.getMessage());
//        }
//    }
//
//    @Override
//    public boolean cambiarHorarioFuncion(Long idFuncion, LocalDateTime nuevoHorario) throws FuncionFechaValidaException {
//        try {
//            Funcion funcion = funcionDAO.buscarFuncionPorId(idFuncion);
//            if (funcion == null) {
//                throw new FuncionFechaValidaException("La función no existe");
//            }
//
//            funcion.setFechaHora(nuevoHorario);
//            return funcionDAO.actualizarFuncion(funcion);
//
//        } catch (PersistenciaException e) {
//            throw new FuncionFechaValidaException("Error al cambiar horario de la función: " + e.getMessage());
//        }
//    }
//
//    @Override
//    public boolean cambiarSalaFuncion(Long idFuncion, Sala nuevaSala) throws FuncionFechaValidaException {
//        try {
//            Funcion funcion = funcionDAO.buscarFuncionPorId(idFuncion);
//            if (funcion == null) {
//                throw new FuncionFechaValidaException("La función no existe");
//            }
//
//            funcion.setSala(nuevaSala);
//            return funcionDAO.actualizarFuncion(funcion);
//
//        } catch (PersistenciaException e) {
//            throw new FuncionFechaValidaException("Error al cambiar sala de la función: " + e.getMessage());
//        }
//    }
//
//    @Override
//    public boolean cambiarPrecioFuncion(Long idFuncion, Double nuevoPrecio) throws FuncionFechaValidaException {
//        try {
//            Funcion funcion = funcionDAO.buscarFuncionPorId(idFuncion);
//            if (funcion == null) {
//                throw new FuncionFechaValidaException("La función no existe");
//            }
//
//            funcion.setPrecio(nuevoPrecio);
//            return funcionDAO.actualizarFuncion(funcion);
//
//        } catch (PersistenciaException e) {
//            throw new FuncionFechaValidaException("Error al cambiar precio de la función: " + e.getMessage());
//        }
//    }
}
