/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import DTOs.GananciaSalaDTO;
import Excepciones.AsientoFuncion.FalloCreacionAsientosFuncionException;
import Excepciones.AsientoFuncion.FalloEliminacionAsientosFuncion;
import Excepciones.AsientoFuncion.FalloMostrarAsientosFuncionException;
import Excepciones.AsientoFuncion.FalloOcuparAsientosFuncionException;
import Excepciones.salas.ErrorCalculoEstadisticasSalaException;
import entidades.AsientoFuncion;
import entidades.Funcion;
import java.util.List;

/**
 *
 * @author Ramon Valencia
 */
public interface IAsientoFuncionDAO {
    
    public List<AsientoFuncion> agregarAsientosFuncion(List<AsientoFuncion> asientosFuncion) throws FalloCreacionAsientosFuncionException;
    
    public Boolean eliminarAsientosFuncion(String idFuncion) throws FalloEliminacionAsientosFuncion;
    
    public List<AsientoFuncion> mostrarListaAsientosPorFuncion(Funcion funcion, Boolean disponibilidad) throws FalloMostrarAsientosFuncionException;
    
    public Boolean ocuparAsientos(List<AsientoFuncion> asientosReservados) throws FalloOcuparAsientosFuncionException;
    
    public List<GananciaSalaDTO> obtenerEstadisticasDeSalas() throws ErrorCalculoEstadisticasSalaException;
}
