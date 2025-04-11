/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Excepciones.PersistenciaException;
import entidades.AsientoFuncion;
import entidades.Funcion;
import java.util.List;

/**
 *
 * @author Ramon Valencia
 */
public interface IAsientoFuncionDAO {
    
    public List<AsientoFuncion> mostrarAsientosFunciones(Funcion funcion) throws PersistenciaException;
    
    public Boolean ocuparAsiento(AsientoFuncion asiento) throws PersistenciaException;
}
