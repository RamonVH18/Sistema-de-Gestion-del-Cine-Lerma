/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Excepciones.AsientoFuncion.FalloCreacionAsientosFuncionException;
import Excepciones.PersistenciaException;
import entidades.AsientoFuncion;
import entidades.Funcion;
import java.util.List;

/**
 *
 * @author Ramon Valencia
 */
public interface IAsientoFuncionDAO {
    
    public List<AsientoFuncion> agregarAsientosFuncion(List<AsientoFuncion> asientosFuncion) throws FalloCreacionAsientosFuncionException;
    
    public List<AsientoFuncion> mostrarAsientosFunciones(Funcion funcion) throws PersistenciaException;
    
    public Boolean ocuparAsiento(AsientoFuncion asiento) throws PersistenciaException;
    
    public List<AsientoFuncion> mostrarAsientosDisponibles(Funcion funcion) throws PersistenciaException;
}
