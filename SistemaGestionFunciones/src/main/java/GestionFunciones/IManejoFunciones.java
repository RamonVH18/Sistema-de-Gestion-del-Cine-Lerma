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
     * Busca funciones aplicando filtros combinados de nombre de película y/o
     * fecha.
     *
     * @param nombrePelicula Nombre completo o parcial de la película
     * (opcional).
     * @param fechaHora Fecha y hora de inicio de la funcion (opcional).
     * @return Lista de {@link FuncionDTO} que coinciden con los filtros
     * aplicados.
     * @throws FuncionDatosIncorrectosException Si no se proporciona al menos un
     * filtro o los datos son invalidos.
     */
    public List<FuncionDTO> buscarFunciones(String nombrePelicula, LocalDateTime fechaHora) throws FuncionDatosIncorrectosException;

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
