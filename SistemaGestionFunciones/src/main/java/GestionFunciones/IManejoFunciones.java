/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package GestionFunciones;

import DTOs.FuncionDTO;
import Excepciones.FuncionBoletosVendidosException;
import Excepciones.FuncionSolapamientoSalaException;
import Excepciones.FuncionCapacidadSalaException;
import Excepciones.FuncionDatosIncorrectosException;
import Excepciones.FuncionDuracionException;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Abraham Coronel Bringas
 */
public interface IManejoFunciones {

    public FuncionDTO registraFuncion(FuncionDTO funcionDTO) throws FuncionDatosIncorrectosException, FuncionSolapamientoSalaException, FuncionCapacidadSalaException;

    public Boolean eliminarFuncion(FuncionDTO funcionDTO) throws FuncionDatosIncorrectosException, FuncionBoletosVendidosException;

    public List<FuncionDTO> buscarFunciones(String nombrePelicula, LocalDateTime fechaHora) throws FuncionDatosIncorrectosException;
    
    public LocalDateTime calcularHoraTerminoFuncion(String idFuncion) throws FuncionDuracionException;

}
