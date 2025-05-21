/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gestionSalasAsientos;

import DTOs.AsientoFuncionDTO;
import DTOs.EstadisticaSalaDTO;
import DTOs.FuncionDTO;
import DTOs.SalaViejaDTO;
import Excepciones.asientos.ErrorCargarAsientoException;
import Excepciones.asientos.ErrorEliminacionAsientosException;
import Excepciones.asientos.ErrorGeneracionAsientoFuncionException;
import Excepciones.asientos.ErrorObtencionEstadisticasException;
import java.util.List;

/**
 *
 * @author Ramon Valencia
 */
public interface IManejoDeAsientos {

    public List<AsientoFuncionDTO> registrarAsientoFuncion(FuncionDTO funcion, SalaViejaDTO sala) throws ErrorGeneracionAsientoFuncionException;
    
    public Boolean eliminarAsientos(String idFuncion) throws ErrorEliminacionAsientosException;
    
    public List<AsientoFuncionDTO> cargarListaAsientos(FuncionDTO funcion, Boolean mostrarDisponibles) throws ErrorCargarAsientoException;
    
    public List<EstadisticaSalaDTO> obtenerEstadisticasSalas() throws ErrorObtencionEstadisticasException;
}
