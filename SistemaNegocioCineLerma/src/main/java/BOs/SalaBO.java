/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BOs;

import DTOs.SalaDTO;
import Excepciones.Sala.SalaBusquedaException;
import Excepciones.Sala.SalaModificacionException;
import Excepciones.Sala.SalaRegistroException;
import Interfaces.ISalaBO;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Ramon Valencia
 */
public class SalaBO implements ISalaBO {
    
    private static SalaBO instanceBO;
    
    private SalaBO(){
    }
    
    public static SalaBO getInstanceBO() {
        if (instanceBO == null) {
            instanceBO = new SalaBO();
        }
        return instanceBO;
    }

    @Override
    public SalaDTO agregarSala(SalaDTO sala) throws SalaRegistroException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public SalaDTO buscarSala() throws SalaBusquedaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<SalaDTO> buscarTodasSalas() throws SalaBusquedaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Boolean modificarSala() throws SalaModificacionException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<SalaDTO> buscarSalasFiltro() throws SalaBusquedaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
