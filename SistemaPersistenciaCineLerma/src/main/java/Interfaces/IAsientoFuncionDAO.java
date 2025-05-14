/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Excepciones.AsientoFuncion.FalloCreacionAsientosFuncionException;
import Excepciones.AsientoFuncion.FalloMostrarAsientosFuncionException;
import Excepciones.AsientoFuncion.FalloOcuparAsientosFuncionException;
import entidades.AsientoFuncion;
import entidades.Funcion;
import java.util.List;

/**
 *
 * @author Ramon Valencia
 */
public interface IAsientoFuncionDAO {
    
    public List<AsientoFuncion> agregarAsientosFuncion(List<AsientoFuncion> asientosFuncion) throws FalloCreacionAsientosFuncionException;
    
    public List<AsientoFuncion> mostrarListaAsientosPorFuncion(Funcion funcion, Boolean disponibilidad) throws FalloMostrarAsientosFuncionException;
    
    public Boolean ocuparAsientos(List<AsientoFuncion> asientosReservados, String idFuncion) throws FalloOcuparAsientosFuncionException;
}
