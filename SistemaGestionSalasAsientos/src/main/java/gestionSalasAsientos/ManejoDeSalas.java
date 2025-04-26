/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestionSalasAsientos;

import DTOs.GananciaSalaDTO;
import DTOs.SalaDTO;
import Excepciones.AgregarSalaException;
import Excepciones.BuscarSalaException;
import Excepciones.EstadisticasSalaException;
import Excepciones.ModificarSalaException;
import Excepciones.ValidacionSalaException;
import enums.EstadoSala;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Ramon Valencia
 */
public class ManejoDeSalas implements IManejoDeSalas {

    @Override
    public SalaDTO agregarSala(SalaDTO sala) throws AgregarSalaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Boolean validarSala(SalaDTO sala) throws ValidacionSalaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<SalaDTO> cargarSalas() throws BuscarSalaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<SalaDTO> cargarSalasFiltro(String filtro) throws BuscarSalaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<SalaDTO> cargarSalaPorNumero(String numero) throws BuscarSalaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<SalaDTO> cargarSalasPorPeriodo(LocalDate periodoInicio, LocalDate periodoFinal) throws BuscarSalaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<GananciaSalaDTO> obtenerGananciaSala(List<SalaDTO> listaSalas) throws EstadisticasSalaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Boolean imprimirEstadisticasSala(GananciaSalaDTO gananciaSala) throws EstadisticasSalaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Boolean modificarEstadoSala(String numeroSala, EstadoSala estado) throws ModificarSalaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
