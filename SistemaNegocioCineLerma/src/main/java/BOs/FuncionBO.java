/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BOs;

import DAOs.FuncionDAO;
import DTOs.FuncionDTO;
import Excepciones.PersistenciaException;
import Excepciones.funciones.FuncionBusquedaException;
import Excepciones.funciones.FuncionEliminacionException;
import Excepciones.funciones.FuncionRegistroException;
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
//        
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
    public List<FuncionDTO> buscarFuncionesPelicula(String tituloPelicula) throws FuncionBusquedaException {
        List<FuncionDTO> funcionesPelicula = new ArrayList<>();

        try {
            List<Funcion> funcionesPeliculas = funcionDAO.mostrarFuncionesPeliculas();
            for (Funcion funcionPelicula : funcionesPeliculas) {
                FuncionDTO funcionPeliculaMapeada = funcionMapper.toFuncionDTO(funcionPelicula);
                if (funcionPeliculaMapeada.getNombre().equals(tituloPelicula)) {
                    funcionesPelicula.add(funcionPeliculaMapeada);
                }
            }

            return funcionesPelicula;
        } catch (PersistenciaException e) {
            throw new FuncionBusquedaException("Error al buscar las funciones de la pelicula.");
        }
    }

    @Override
    public List<FuncionDTO> buscarFuncionesActivas() throws FuncionBusquedaException {

        return null;

    }

    @Override
    public void suscribirClienteAFuncion(Cliente cliente, Long idFuncion) throws FuncionBusquedaException {
        try {
            // Verificar que la función existe
            Funcion funcion = funcionDAO.buscarFuncionPorId(idFuncion);
            if (funcion == null) {
                throw new FuncionBusquedaException("La función no existe");
            }

            // Crear observador para el cliente
            ObservadorClienteFuncion observador = new ObservadorClienteFuncion(cliente);

            // Registrar el observador
            funcionDAO.agregarObservador(idFuncion, observador);

        } catch (PersistenciaException e) {
            throw new FuncionBusquedaException("Error al suscribir cliente a la función: " + e.getMessage());
        }
    }

    public void desuscribirClienteDeFuncion(Cliente cliente, Long idFuncion) throws FuncionBusquedaException {
        try {
            // Verificar que la función existe
            Funcion funcion = funcionDAO.buscarFuncionPorId(idFuncion);
            if (funcion == null) {
                throw new FuncionBusquedaException("La función no existe");
            }

            final Long clienteId = cliente.getIdCliente();

            // Eliminar el observador del cliente para esta función
            funcionDAO.eliminarObservadorPorFiltro(idFuncion, observador -> {
                if (observador instanceof ObservadorClienteFuncion) {
                    return ((ObservadorClienteFuncion) observador).getCliente().getIdCliente().equals(clienteId);
                }
                return false;
            });

        } catch (PersistenciaException e) {
            throw new FuncionBusquedaException("Error al desuscribir cliente de la función: " + e.getMessage());
        }
    }

    @Override
    public boolean cambiarEstadoFuncion(Long idFuncion, boolean nuevoEstado) throws FuncionBusquedaException {
        try {
            Funcion funcion = funcionDAO.buscarFuncionPorId(idFuncion);
            if (funcion == null) {
                throw new FuncionBusquedaException("La función no existe");
            }

            funcion.setEstado(nuevoEstado);
            return funcionDAO.actualizarFuncion(funcion);

        } catch (PersistenciaException e) {
            throw new FuncionBusquedaException("Error al cambiar estado de la función: " + e.getMessage());
        }
    }

    @Override
    public boolean cambiarHorarioFuncion(Long idFuncion, LocalDateTime nuevoHorario) throws FuncionBusquedaException {
        try {
            Funcion funcion = funcionDAO.buscarFuncionPorId(idFuncion);
            if (funcion == null) {
                throw new FuncionBusquedaException("La función no existe");
            }

            funcion.setFechaHora(nuevoHorario);
            return funcionDAO.actualizarFuncion(funcion);

        } catch (PersistenciaException e) {
            throw new FuncionBusquedaException("Error al cambiar horario de la función: " + e.getMessage());
        }
    }

    @Override
    public boolean cambiarSalaFuncion(Long idFuncion, Sala nuevaSala) throws FuncionBusquedaException {
        try {
            Funcion funcion = funcionDAO.buscarFuncionPorId(idFuncion);
            if (funcion == null) {
                throw new FuncionBusquedaException("La función no existe");
            }

            funcion.setSala(nuevaSala);
            return funcionDAO.actualizarFuncion(funcion);

        } catch (PersistenciaException e) {
            throw new FuncionBusquedaException("Error al cambiar sala de la función: " + e.getMessage());
        }
    }

    @Override
    public boolean cambiarPrecioFuncion(Long idFuncion, Double nuevoPrecio) throws FuncionBusquedaException {
        try {
            Funcion funcion = funcionDAO.buscarFuncionPorId(idFuncion);
            if (funcion == null) {
                throw new FuncionBusquedaException("La función no existe");
            }

            funcion.setPrecio(nuevoPrecio);
            return funcionDAO.actualizarFuncion(funcion);

        } catch (PersistenciaException e) {
            throw new FuncionBusquedaException("Error al cambiar precio de la función: " + e.getMessage());
        }
    }

}
