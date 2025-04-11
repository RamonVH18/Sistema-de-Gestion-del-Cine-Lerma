/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BOs;

import DTOs.AsientoFuncionDTO;
import DTOs.FuncionDTO;
import Interfaces.IAsientoFuncionBO;
import java.util.List;

/**
 *
 * @author Ramon Valencia
 */
public class AsientoFuncionBO implements IAsientoFuncionBO{
    
    private static AsientoFuncionBO instance;
    
    private AsientoFuncionBO() {
        
    }
    
    public AsientoFuncionBO getInstance() {
        if (instance == null) {
            instance = new AsientoFuncionBO();
        }
        return instance;
    }
    
    @Override
    public List<AsientoFuncionDTO> registrarAsientosFuncion(List<AsientoFuncionDTO> asientos) {
        
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Boolean reservarAsientosFuncion(List<AsientoFuncionDTO> asientos) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<AsientoFuncionDTO> obtenerAsientosFuncion(FuncionDTO funcion) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<AsientoFuncionDTO> obtenerAsientosDisponibles(FuncionDTO funcion) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
