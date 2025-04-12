/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Excepciones.PersistenciaException;
import entidades.Funcion;
import entidades.Pelicula;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 * @author Ramon Valencia
 */
public interface IFuncionDAO {

    public List<Funcion> buscarFuncionesPelicula(Pelicula pelicula) throws PersistenciaException;

    public List<Funcion> mostrarFuncionesActivas() throws PersistenciaException;

    public List<Funcion> mostrarFuncionesPeliculas() throws PersistenciaException;

    // Métodos existentes
    List<Funcion> funcionesHarcodeadas() throws PersistenciaException;

    // Nuevos métodos
    Funcion buscarFuncionPorId(Long idFuncion) throws PersistenciaException;

    boolean actualizarFuncion(Funcion funcion) throws PersistenciaException;

    // Métodos para gestión de observadores
    void agregarObservador(Long idFuncion, ObservadorFuncion observador);

    void eliminarObservador(Long idFuncion, ObservadorFuncion observador);

    void eliminarObservadorPorFiltro(Long idFuncion, Predicate<ObservadorFuncion> filtro);

    void notificarObservadores(Long idFuncion, String tipoEvento, String mensaje);

    

}
