/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import DTOs.FuncionDTO;
import Excepciones.funciones.FuncionEliminarException;
import Excepciones.funciones.FuncionFechaValidaException;
import Excepciones.funciones.FuncionPeliculaNoEncontradaException;
import Excepciones.funciones.FuncionRegistrarException;
import Excepciones.funciones.FuncionValidadaException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Interfaz que define las operaciones de lógica de negocio para la gestión de
 * Funciones. Establece los metodos necesarios para registrar, eliminar, buscar
 * y calcular informacion de funciones, manejando excepciones especificas del
 * dominio.
 *
 * @author Abraham Coronel Bringas
 */
public interface IFuncionBO {

    /**
     * Registra una funcion en el sistema despues de validar y convertir el DTO
     * a entidad.
     *
     * @param funcionDTO Datos de la funcion en formato DTO.
     * @return FuncionDTO registrada con ID generado.
     * @throws FuncionRegistrarException Si hay errores de validacion, datos
     * invalidos, conflictos de horario, o fallas en dependencias (pelicula/sala
     * no encontradas).
     */
    public FuncionDTO registraFuncion(FuncionDTO funcionDTO) throws FuncionRegistrarException;

    /**
     * Elimina una función existente del sistema.
     *
     * @param funcionDTO Funcion a eliminar (debe incluir ID válido).
     * @return true si la eliminacion fue exitosa, false en caso contrario.
     * @throws FuncionEliminarException Si la funcion no existe o hay errores en
     * la operacion.
     */
    public Boolean eliminarFuncion(FuncionDTO funcionDTO) throws FuncionEliminarException;

    /**
     * Busca funciones asociadas a una pelicula por coincidencia parcial en el
     * título.
     *
     * @param nombrePelicula Texto para filtrar.
     * @return Lista de FuncionDTO que coinciden con el filtro (puede estar
     * vacía).
     */
    public List<FuncionDTO> buscarFuncionesPelicula(String nombrePelicula);

    /**
     * Busca funciones que inician exactamente en la fecha y hora especificadas.
     *
     * @param fechaHora Fecha y hora exacta de inicio.
     * @return Lista de FuncionDTO con el horario indicado.
     * @throws FuncionFechaValidaException Si la fecha es nula o anterior al
     * momento actual.
     */
    public List<FuncionDTO> buscarFuncionFechaInicio(LocalDateTime fechaHora) throws FuncionFechaValidaException;

    /**
     * Calcula la hora de término de una funcion sumando la duracion de la
     * pelicula a su inicio.
     *
     * @param IdFuncion ID de la funcion en formato String.
     * @return Fecha y hora de termino calculada.
     * @throws FuncionValidadaException Si el ID es invalido, la funcion no
     * existe, o la duracion de la película es incorrecta.
     */
    public LocalDateTime calcularHoraTerminoFuncion(String IdFuncion) throws FuncionValidadaException;

    /**
     * Busca funciones que coinciden con un texto en el numero de sala o titulo
     * de película.
     *
     * @param textoFiltro Texto para filtrar.
     * @return Lista de FuncionDTO que coinciden con el filtro.
     * @throws FuncionPeliculaNoEncontradaException Si no hay resultados o hay
     * errores en la consulta.
     */
    public List<FuncionDTO> buscarFuncionesFiltradas(String textoFiltro) throws FuncionPeliculaNoEncontradaException;
}
