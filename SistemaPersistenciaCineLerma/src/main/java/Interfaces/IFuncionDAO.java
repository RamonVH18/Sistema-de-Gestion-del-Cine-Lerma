/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Excepciones.Funciones.FuncionBoletosVendidosException;
import Excepciones.Funciones.FuncionSalaOcupadaException;
import Excepciones.PersistenciaException;
import entidades.Funcion;
import entidades.Pelicula;
import java.util.List;
import java.util.function.Predicate;
import org.bson.types.ObjectId;

/**
 *
 * @author Ramon Valencia
 */
public interface IFuncionDAO {
    
    public Funcion registrarFuncion(Funcion funcion) throws FuncionSalaOcupadaException;
    
    public Funcion eliminarFuncion(Funcion funcion) throws FuncionBoletosVendidosException;

    public List<Funcion> buscarFuncionesPelicula(Pelicula pelicula);

    public List<Funcion> mostrarFuncionesActivas();

    public List<Funcion> mostrarFuncionesPeliculas();

    // Métodos existentes
    List<Funcion> funcionesHarcodeadas() throws PersistenciaException;

    // Nuevos métodos
    Funcion buscarFuncionPorId(ObjectId idFuncion);

    boolean actualizarFuncion(Funcion funcion) throws PersistenciaException; // Revisar metodo, nose actualiza funcion.

    // Métodos para gestión de observadores
    void agregarObservador(Long idFuncion, ObservadorFuncion observador);

    void eliminarObservador(Long idFuncion, ObservadorFuncion observador);

    void eliminarObservadorPorFiltro(Long idFuncion, Predicate<ObservadorFuncion> filtro);

    void notificarObservadores(Long idFuncion, String tipoEvento, String mensaje);

    

}
