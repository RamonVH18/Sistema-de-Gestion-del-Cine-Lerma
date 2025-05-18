/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BOs;

import DAOs.FuncionDAO;
import DAOs.PeliculaDAO;
import DAOs.SalaDAO;
import DTOs.FuncionDTO;
import Excepciones.Funciones.FuncionDuracionIncorrectaException;
import Excepciones.Funciones.FuncionNoEncontradaException;
import Excepciones.Funciones.FuncionSalaOcupadaException;
import Excepciones.Funciones.FuncionSalaVaciaException;
import Excepciones.funciones.FuncionEliminarException;
import Excepciones.funciones.FuncionFechaValidaException;
import Excepciones.funciones.FuncionPeliculaNoEncontradaException;
import Excepciones.funciones.FuncionRegistrarException;
import Excepciones.funciones.FuncionValidadaException;
import Excepciones.peliculas.BuscarPeliculaException;
import Excepciones.salas.BuscarSalaException;
import Interfaces.IFuncionBO;
import Interfaces.IFuncionDAO;
import Interfaces.IPeliculaDAO;
import Interfaces.ISalaDAO;
import Mappers.FuncionMapper;
import entidades.Funcion;
import entidades.Pelicula;
import entidades.Sala;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que implementa la lógica de negocio para las operaciones relacionadas
 * con Funciones. Actúa como intermediario entre la capa de presentacion y los
 * DAOs, manejando validaciones, conversiones DTO/Entidad y excepciones
 * específicas del dominio.
 *
 * @author Abraham Coronel Bringas
 */
public class FuncionBO implements IFuncionBO {

    private static FuncionBO instanceFuncionBO;  // Instancia única (Singleton)
    private final IFuncionDAO funcionDAO = FuncionDAO.getInstanceDAO(); // Acceso a operaciones de funciones
    private final IPeliculaDAO peliculaDAO = PeliculaDAO.getInstanceDAO(); // Acceso a operaciones de películas
    private final ISalaDAO salaDAO = SalaDAO.getInstanceDAO(); // Acceso a operaciones de salas
    private final FuncionMapper funcionMapper = new FuncionMapper(); // Conversor DTO <-> Entidad

    /**
     * Constructor privado para Singleton.
     */
    private FuncionBO() {
    }

    /**
     * Obtiene la instancia unica de FuncionBO (Singleton).
     *
     * @return Instancia unica de FuncionBO.
     */
    public static FuncionBO getInstanceDAO() {
        if (instanceFuncionBO == null) {
            instanceFuncionBO = new FuncionBO();
        }
        return instanceFuncionBO;
    }

    /**
     * Registra una funcion validando y convirtiendo el DTO a entidad antes de
     * persistir.
     *
     * @param funcionDTO Datos de la funcion en formato DTO.
     * @return FuncionDTO registrada con ID generado.
     * @throws FuncionRegistrarException Si hay errores de validacion, datos
     * invalidos o conflictos de horario/sala.
     */
    @Override
    public FuncionDTO registraFuncion(FuncionDTO funcionDTO) throws FuncionRegistrarException {
        if (funcionDTO == null) {
            throw new FuncionRegistrarException("La funcion no puede ser nula");
        }

        try {
            Funcion funcionRegistrar = funcionMapper.toFuncionEntidad(funcionDTO);

            Pelicula pelicula = peliculaDAO.buscarPelicula(funcionDTO.getNombrePelicula());
            Sala sala = salaDAO.buscarSala(funcionDTO.getNumSala());

            funcionRegistrar.setPelicula(pelicula);
            funcionRegistrar.setSala(sala);

            Funcion funcionRegistrada = funcionDAO.registrarFuncion(funcionRegistrar);

            return funcionMapper.toFuncionDTO(funcionRegistrada);

        } catch (FuncionSalaOcupadaException | FuncionSalaVaciaException | FuncionDuracionIncorrectaException e) {
            throw new FuncionRegistrarException("Error al registrar: " + e.getMessage(), e);
        } catch (BuscarPeliculaException | BuscarSalaException e) {
            throw new FuncionRegistrarException("Error en datos: " + e.getMessage(), e);
        }
    }

    /**
     * Elimina una función existente a partir de su representacion DTO.
     *
     * @param funcionDTO Función a eliminar (debe contener ID valido).
     * @return true si la eliminación fue exitosa, false en caso contrario.
     * @throws FuncionEliminarException Si la funcion no existe o hay errores en
     * los datos.
     */
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

    /**
     * Busca funciones asociadas a una pelicula por nombre (coincidencia
     * parcial).
     *
     * @param nombrePelicula Texto para filtrar (ej: "Aven" encontrará
     * "Avengers").
     * @return Lista de FuncionDTO que coinciden con el filtro.
     */
    @Override
    public List<FuncionDTO> buscarFuncionesPelicula(String nombrePelicula) {
        List<FuncionDTO> funcionesDTO = new ArrayList<>();
        List<Funcion> funcionesEntity = funcionDAO.buscarFuncionesPelicula(nombrePelicula);
        for (Funcion funcion : funcionesEntity) {
            funcionesDTO.add(funcionMapper.toFuncionDTO(funcion));
        }

        return funcionesDTO;
    }

    /**
     * Busca funciones que coinciden con un texto en sala o pelicula (busqueda
     * combinada).
     *
     * @param textoFiltro Texto para filtrar.
     * @return Lista de FuncionDTO que coinciden con el filtro.
     * @throws FuncionPeliculaNoEncontradaException Si no hay resultados o hay
     * errores en la consulta.
     */
    @Override
    public List<FuncionDTO> buscarFuncionesFiltradas(String textoFiltro) throws FuncionPeliculaNoEncontradaException {
        try {
            List<FuncionDTO> funcionesDTO = new ArrayList<>();
            List<Funcion> funcionesFiltradas = funcionDAO.buscarPeliculasFiltradas(textoFiltro);
            for (Funcion funcion : funcionesFiltradas) {
                funcionesDTO.add(funcionMapper.toFuncionDTO(funcion));
            }
            return funcionesDTO;
        } catch (FuncionNoEncontradaException e) {
            throw new FuncionPeliculaNoEncontradaException("No se encontraron las funciones filtradas: " + e.getMessage());
        }
    }

    /**
     * Busca funciones que inician en una fecha y hora especificas (validando
     * fecha futura).
     *
     * @param fechaHora Fecha y hora exacta de inicio.
     * @return Lista de FuncionDTO con el horario especificado.
     * @throws FuncionFechaValidaException Si la fecha es nula o pasada.
     */
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

    /**
     * Calcula la hora de término de una funcion sumando la duracion de la
     * pelicula.
     *
     * @param idFuncion ID de la funcion en formato String.
     * @return Fecha y hora de término calculada.
     * @throws FuncionValidadaException Si el ID es invalido o hay
     * inconsistencias en los datos.
     */
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
