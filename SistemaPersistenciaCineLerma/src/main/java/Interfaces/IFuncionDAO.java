/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import entidades.Funcion;
import java.util.List;

/**
 *
 * @author Ramon Valencia
 */
public interface IFuncionDAO {
    
    public Funcion buscarFuncion(Long funcionId);
    
    public List<Funcion> mostrarHistorialFunciones();
}
