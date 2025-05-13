/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Excepciones.Funciones.FuncionNoEncontradaException;
import Excepciones.Funciones.FuncionSalaOcupadaException;
import entidades.Funcion;
import java.util.List;
import org.bson.types.ObjectId;

/**
 *
 * @author Abraham Coronel Bringas
 */
public interface IFuncionDAO {

    public Funcion registrarFuncion(Funcion funcion) throws FuncionSalaOcupadaException;

    public Funcion eliminarFuncion(Funcion funcion) throws FuncionNoEncontradaException;

    public List<Funcion> buscarFuncionesPelicula(String nombrepelicula);

    public List<Funcion> buscarFuncionesActivas();

    public Funcion buscarFuncionPorId(ObjectId idFuncion);

}
