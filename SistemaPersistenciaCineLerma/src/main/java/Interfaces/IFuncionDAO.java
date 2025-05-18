/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Excepciones.Funciones.FuncionDuracionIncorrectaException;
import Excepciones.Funciones.FuncionSalaVaciaException;
import Excepciones.Funciones.FuncionNoEncontradaException;
import Excepciones.Funciones.FuncionSalaOcupadaException;
import entidades.Funcion;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Interfaz que define las operaciones CRUD y de consulta para la entidad
 * Funcion. Incluye validaciones de reglas de negocio y manejo de excepciones
 * especificas.
 *
 * @author Abraham Coronel Bringas
 */
public interface IFuncionDAO {

    /**
     * Registra una funcion en la base de datos después de validar
     * disponibilidad de sala y duracion de pelicula.
     *
     * @param funcion Funcion a registrar.
     * @return Función registrada con ID generado.
     * @throws FuncionSalaOcupadaException Si la sala está ocupada en el horario
     * seleccionado.
     * @throws FuncionSalaVaciaException Si la sala no está activa o es
     * inválida.
     * @throws FuncionDuracionIncorrectaException Si la duración de la película
     * es ≤0 o nula.
     */
    public Funcion registrarFuncion(Funcion funcion) throws FuncionSalaOcupadaException, FuncionSalaVaciaException, FuncionDuracionIncorrectaException;

    /**
     * Elimina una funcion existente de la base de datos.
     *
     * @param funcion Funcion a eliminar (debe contener un ID valido).
     * @return Función eliminada.
     * @throws FuncionNoEncontradaException Si la función no existe en la base
     * de datos.
     */
    public Funcion eliminarFuncion(Funcion funcion) throws FuncionNoEncontradaException;

    /**
     * Busca funciones por coincidencia parcial en el título de la pelicula
     * (insensible a mayusculas).
     *
     * @param nombrePelicula Texto para filtrar (ej: "Aven" encontrará
     * "Avengers").
     * @return Lista de funciones que coinciden con el filtro.
     */
    public List<Funcion> buscarFuncionesPelicula(String nombrePelicula);

    /**
     * Busca funciones que inician exactamente en la fecha y hora especificadas.
     *
     * @param fechaHora Fecha y hora exacta de inicio.
     * @return Lista de funciones con el horario especificado.
     */
    public List<Funcion> buscarFuncionFechaInicio(LocalDateTime fechaHora);

    /**
     * Calcula la hora de termino de una función sumando la duración de la
     * pelicula a su hora de inicio.
     *
     * @param idFuncion ID de la funcion en formato String.
     * @return Fecha y hora calculada de termino.
     * @throws FuncionNoEncontradaException Si el ID es invalido o la funcion no
     * existe.
     * @throws FuncionDuracionIncorrectaException Si la duracion de la pelicula
     * es nula o invalida.
     */
    public LocalDateTime calcularHoraTerminoFuncion(String idFuncion) throws FuncionNoEncontradaException, FuncionDuracionIncorrectaException;

    /**
     * Busca funciones que coinciden con un texto en el numero de sala o titulo
     * de película. Los resultados se ordenan por numero de sala de forma
     * ascendente.
     *
     * @param textoFiltro Texto para filtrar.
     * @return Lista de funciones que coinciden con el filtro.
     * @throws FuncionNoEncontradaException Si ocurre un error en la consulta a
     * la base de datos.
     */
    public List<Funcion> buscarPeliculasFiltradas(String textoFiltro) throws FuncionNoEncontradaException;

}
