/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Excepciones.PersistenciaException;
import entidades.Funcion;
import entidades.Pelicula;
import java.util.List;

/**
 *
 * @author Ramon Valencia
 */
public interface IFuncionDAO {
    
//    public Funcion buscarFuncion(Long funcionId);
    
//    public List<Funcion> mostrarHistorialFunciones();
    
      public List<Funcion> mostrarFuncionesPelicula(Pelicula pelicula) throws PersistenciaException;
      
      public List<Funcion> mostrarFuncionesActivas() throws PersistenciaException;
      
      
}
