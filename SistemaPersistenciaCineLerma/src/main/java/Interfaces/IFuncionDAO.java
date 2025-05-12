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

    public List<Funcion> buscarFuncionesPelicula(String nombrepelicula);

    public List<Funcion> buscarFuncionesActivas();

    public List<Funcion> mostrarFuncionesPeliculas();

    // Nuevos métodos
    Funcion buscarFuncionPorId(ObjectId idFuncion);

    // Métodos para gestión de observadores
    void agregarObservador(String idFuncion, ObservadorFuncion observador);

    void eliminarObservador(String idFuncion, ObservadorFuncion observador);

    void eliminarObservadorPorFiltro(String idFuncion, Predicate<ObservadorFuncion> filtro);

    void notificarObservadores(String idFuncion, String tipoEvento, String mensaje);

}
