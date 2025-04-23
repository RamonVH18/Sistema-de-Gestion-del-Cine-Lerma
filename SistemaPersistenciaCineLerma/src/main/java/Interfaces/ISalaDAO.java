/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Excepciones.PersistenciaException;
import entidades.Sala;

/**
 *
 * @author Ramon Valencia
 */
public interface ISalaDAO {
    
    public Sala agregarSala(Sala sala) throws PersistenciaException ;
    
}
