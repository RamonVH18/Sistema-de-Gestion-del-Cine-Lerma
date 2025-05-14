/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import DTOs.FuncionDTO;
import Excepciones.funciones.FuncionEliminarException;
import Excepciones.funciones.FuncionRegistrarException;
import java.util.List;

/**
 *
 * @author Abraham Coronel Bringas
 */
public interface IFuncionBO {

    public FuncionDTO registraFuncion(FuncionDTO funcionDTO) throws FuncionRegistrarException;

    public Boolean eliminarFuncion(FuncionDTO funcionDTO) throws FuncionEliminarException;

    public List<FuncionDTO> buscarFunciones(String nombrePelicula);

}
