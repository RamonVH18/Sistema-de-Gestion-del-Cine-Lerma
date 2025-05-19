/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package GestionFunciones;

import DTOs.FuncionDTO;
import Excepciones.FuncionBoletosVendidosException;
import Excepciones.FuncionSolapamientoSalaException;
import Excepciones.FuncionCapacidadSalaException;
import Excepciones.FuncionDatosIncorrectosException;
import Excepciones.FuncionDuracionException;
import Excepciones.FuncionFechaFuturaException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Interfaz que define las operaciones para gestionar funciones en un sistema de
 * cine. Incluye métodos para registrar, eliminar, buscar funciones y calcular
 * horarios.
 *
 * @author Abraham Coronel Bringas
 */
public interface IManejoFunciones {

    /**
     * Registra una nueva función en el sistema después de validar los datos.
     *
     * @param funcionDTO Objeto que contiene los datos de la funcion a
     * registrar.
     * @return {@link FuncionDTO} La funcion registrada, con informacion
     * actualizada (ej: ID generado).
     * @throws FuncionDatosIncorrectosException Si los datos son invalidos (ej:
     * campos vacíos, precios negativos).
     * @throws FuncionSolapamientoSalaException Si la funcion se solapa con otra
     * en la misma sala.
     * @throws FuncionCapacidadSalaException Si la sala asociada supera su
     * capacidad máxima.
     */
    public FuncionDTO registraFuncion(FuncionDTO funcionDTO) throws FuncionDatosIncorrectosException, FuncionSolapamientoSalaException, FuncionCapacidadSalaException;

    /**
     * Elimina una función existente, siempre que no tenga boletos vendidos.
     *
     * @param funcionDTO Objeto que representa la funcion a eliminar (debe
     * incluir ID valido).
     * @return {@code true} si la eliminación fue exitosa, {@code false} en caso
     * contrario.
     * @throws FuncionDatosIncorrectosException Si la funcion no existe o los
     * datos son invalidos.
     * @throws FuncionBoletosVendidosException Si hay boletos vendidos asociados
     * a la funcion.
     */
    public Boolean eliminarFuncion(FuncionDTO funcionDTO) throws FuncionDatosIncorrectosException, FuncionBoletosVendidosException;

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
    public List<FuncionDTO> buscarFuncionesPorPelicula(String nombrePelicula) throws FuncionDatosIncorrectosException;

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
    public List<FuncionDTO> buscarFuncionesPorFecha(LocalDateTime fechaHora) throws FuncionFechaFuturaException, FuncionDatosIncorrectosException;

    /**
     * Calcula la hora de finalización de una función basada en su ID.
     *
     * @param idFuncion ID único de la funcion (formato MongoDB ObjectId
     * valido).
     * @return {@link LocalDateTime} Hora estimada de termino (hora de inicio +
     * duracion de la película).
     * @throws FuncionDuracionException Si el ID es invalido o no se puede
     * calcular la duración.
     */
    public LocalDateTime calcularHoraTerminoFuncion(String idFuncion) throws FuncionDuracionException;

    /**
     * Busca funciones usando un texto de filtro general (ej: búsqueda parcial
     * por nombre de película).
     *
     * @param textoFiltro Texto para filtrar funciones (puede ser vacío, pero no
     * nulo).
     * @return Lista de {@link FuncionDTO} que coinciden con el texto de
     * búsqueda.
     * @throws FuncionDatosIncorrectosException Si ocurre un error al procesar
     * el filtro.
     */
    public List<FuncionDTO> buscarFuncionesFiltradas(String textoFiltro) throws FuncionDatosIncorrectosException;

}
