/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GestionFunciones;

import BOs.AsientoFuncionBO;
import BOs.FuncionBO;
import DTOs.AsientoFuncionDTO;
import DTOs.FuncionDTO;
import Excepciones.FuncionBoletosVendidosException;
import Excepciones.FuncionSolapamientoSalaException;
import Excepciones.FuncionCapacidadSalaException;
import Excepciones.FuncionDatosIncorrectosException;
import Excepciones.FuncionDuracionException;
import Excepciones.FuncionFechaFuturaException;
import Excepciones.asientoFuncion.AsientoFuncionBusquedaException;
import Excepciones.funciones.FuncionEliminarException;
import Excepciones.funciones.FuncionFechaValidaException;
import Excepciones.funciones.FuncionPeliculaNoEncontradaException;
import Excepciones.funciones.FuncionRegistrarException;
import Excepciones.funciones.FuncionValidadaException;
import Interfaces.IAsientoFuncionBO;
import Interfaces.IFuncionBO;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Clase que gestiona las operaciones relacionadas con las funciones de un
 * sistema de gestión de cine. Implementa la interfaz {@link IManejoFunciones} y
 * utiliza patrones singleton para asegurar una única instancia. Proporciona
 * métodos para registrar, eliminar, buscar funciones y calcular horarios de
 * término.
 *
 * @author Abraham Coronel Bringas
 *
 */
public class ManejoFunciones implements IManejoFunciones {

    /**
     * Instancia unica de la clase (patrón singleton).
     */
    private static ManejoFunciones instanceManejoFunciones;

    /**
     * Constructor privado para evitar instanciación externa (singleton).
     */
    private ManejoFunciones() {
    }

    /**
     * Obtiene la instancia única de la clase (patrón singleton).
     *
     * @return La única instancia de {@link ManejoFunciones}.
     */
    public static ManejoFunciones getInstanceDAO() {
        if (instanceManejoFunciones == null) {
            instanceManejoFunciones = new ManejoFunciones();
        }
        return instanceManejoFunciones;
    }

    /**
     * Objeto de negocio para gestionar funciones.
     */
    private final IFuncionBO funcionBO = FuncionBO.getInstanceDAO();
    /**
     * Objeto de negocio para gestionar asientos de funciones.
     */
    private final IAsientoFuncionBO asientoFuncionBO = AsientoFuncionBO.getInstance();

    /**
     * Registra una nueva función validando previamente los datos
     * proporcionados.
     *
     * @param funcionDTO Objeto que contiene los datos de la funcion a
     * registrar.
     * @return {@link FuncionDTO} La funcion registrada, con datos actualizados
     * (ej: ID).
     * @throws FuncionDatosIncorrectosException Si los datos de la funcion son
     * inválidos (nulos, precios negativos, etc.).
     * @throws FuncionSolapamientoSalaException Si la funcion se solapa con otra
     * en la misma sala.
     * @throws FuncionCapacidadSalaException Si la sala asociada no tiene
     * capacidad valida.
     */
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

        LocalDateTime fechaHora = funcionDTO.getFechaHora();
        Integer hora = fechaHora.getHour();
        if (hora < 8 || hora >= 22) {
            throw new FuncionDatosIncorrectosException("Error: Las funciones solo pueden registrarse entre las 8:00 am y las 10:00 pm.");
        }

        // Validar que sala exista y no sea nula
        if (funcionDTO.getNumSala() == null || funcionDTO.getNumSala().trim().isEmpty()) {
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

    /**
     * Elimina una función siempre que no tenga boletos vendidos.
     *
     * @param funcionDTO Objeto que contiene los datos de la funcion a eliminar.
     * @return {@code true} si la eliminación fue exitosa, {@code false} en caso
     * contrario.
     * @throws FuncionDatosIncorrectosException Si la funcion no existe o los
     * datos son invalidos.
     * @throws FuncionBoletosVendidosException Si la funcion tiene boletos
     * vendidos y no puede eliminarse.
     */
    @Override
    public Boolean eliminarFuncion(FuncionDTO funcionDTO) throws FuncionDatosIncorrectosException, FuncionBoletosVendidosException {
        if (funcionDTO == null || funcionDTO.getIdFuncion() == null) {
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

    /**
     * Busca funciones utilizando un texto de filtro general (ej: nombre parcial
     * de película).
     *
     * @param textoFiltro Texto para filtrar funciones (ej: "avengers").
     * @return Lista de {@link FuncionDTO} que coinciden con el filtro.
     * @throws FuncionDatosIncorrectosException Si ocurre un error al procesar
     * el filtro.
     */
    @Override
    public List<FuncionDTO> buscarFuncionesFiltradas(String textoFiltro) throws FuncionDatosIncorrectosException {
        try {
            if (textoFiltro == null) {
                textoFiltro = "";
            }

            return funcionBO.buscarFuncionesFiltradas(textoFiltro);
        } catch (FuncionPeliculaNoEncontradaException ex) {
            throw new FuncionDatosIncorrectosException("Hubo un problema al cargar las salas");
        }
    }

    /**
     * Calcula la hora de término de una función basada en su duración y hora de
     * inicio.
     *
     * @param idFuncion ID unico de la función.
     * @return {@link LocalDateTime} Hora estimada de término.
     * @throws FuncionDuracionException Si el ID es inválido o hay errores al
     * calcular la duración.
     */
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

    /**
     * Busca funciones por el nombre de una película, validando que el nombre no
     * este vacío. Delega la búsqueda en el objeto de negocio correspondiente.
     *
     * @param nombrePelicula Nombre de la película a buscar. No puede ser nulo o
     * vacio.
     * @return Lista de {@link FuncionDTO} que coinciden con el nombre de la
     * pelicula.
     * @throws FuncionDatosIncorrectosException Si el nombre de la película es
     * nulo, vacio o ocurre un error durante la busqueda.
     */
    @Override
    public List<FuncionDTO> buscarFuncionesPorPelicula(String nombrePelicula) throws FuncionDatosIncorrectosException {
        if (nombrePelicula == null || nombrePelicula.isEmpty()) {
            throw new FuncionDatosIncorrectosException("El nombre de la pelicula no puede estar vacio.");
        }
        try {
            return funcionBO.buscarFuncionesPelicula(nombrePelicula);
        } catch (Exception e) {
            throw new FuncionDatosIncorrectosException("Error al buscar por pelicula: " + e.getMessage(), e);
        }
    }

    /**
     * Busca funciones por fecha y hora, validando que la fecha sea futura y no
     * sea nula. Utiliza el objeto de negocio para obtener las funciones que
     * coinciden con la fecha proporcionada.
     *
     * @param fechaHora Fecha y hora para filtrar las funciones. No puede ser
     * nula y debe ser futura.
     * @return Lista de {@link FuncionDTO} que comienzan en la fecha y hora
     * especificadas.
     * @throws FuncionFechaFuturaException Si la fecha proporcionada es anterior
     * a la fecha actual.
     * @throws FuncionDatosIncorrectosException Si la fecha es nula o ocurre un
     * error en la búsqueda.
     */
    @Override
    public List<FuncionDTO> buscarFuncionesPorFecha(LocalDateTime fechaHora) throws FuncionFechaFuturaException, FuncionDatosIncorrectosException {
        if (fechaHora == null) {
            throw new FuncionDatosIncorrectosException("La fecha no puede estar vacia.");
        }
        LocalDateTime ahora = LocalDateTime.now();
        if (fechaHora.isBefore(ahora)) {
            throw new FuncionFechaFuturaException("La fecha debe ser futura.");
        }
        try {
            return funcionBO.buscarFuncionFechaInicio(fechaHora);
        } catch (FuncionFechaValidaException e) {
            throw new FuncionDatosIncorrectosException("Fecha invalida: " + e.getMessage(), e);
        }
    }
}
