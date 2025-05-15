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
import org.bson.types.ObjectId;

/**
 *
 * @author Abraham Coronel Bringas
 */
public interface IFuncionDAO {

    public Funcion registrarFuncion(Funcion funcion) throws FuncionSalaOcupadaException, FuncionSalaVaciaException;

    public Funcion eliminarFuncion(Funcion funcion) throws FuncionNoEncontradaException;

    public List<Funcion> buscarFuncionesPelicula(String nombrePelicula);

    public List<Funcion> buscarFuncionFechaInicio(LocalDateTime fechaHora);
    
    public LocalDateTime calcularHoraTerminoFuncion(ObjectId idFuncion) throws FuncionNoEncontradaException, FuncionDuracionIncorrectaException;

}
