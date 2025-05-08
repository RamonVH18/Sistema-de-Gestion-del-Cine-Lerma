/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestionSalasAsientos;

import BOs.SalaBO;
import DTOs.GananciaSalaDTO;
import DTOs.SalaDTO;
import Excepciones.AgregarSalaException;
import Excepciones.BuscarSalaException;
import Excepciones.EstadisticasSalaException;
import Excepciones.ModificarSalaException;
import Excepciones.Sala.SalaRegistroException;
import Excepciones.ValidacionSalaException;
import Interfaces.ISalaBO;
import enums.EstadoSala;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author Ramon Valencia
 */
public class ManejoDeSalas implements IManejoDeSalas {
    private ISalaBO salaBO = SalaBO.getInstanceBO();
    private final Integer minAsientos = 10; // Numero minimo de asientos que puede tener una sala
    
    @Override
    public SalaDTO agregarSala(SalaDTO sala) throws AgregarSalaException {
        try {
            validarSala(sala);
            sala = salaBO.agregarSala(sala);
            
        } catch(ValidacionSalaException e) {
            throw new AgregarSalaException("La sala ingresada no cumple con la siguiente validacion: " + e.getMessage());
        } catch (SalaRegistroException e) {
            throw new AgregarSalaException("La sala no se pudo registrar debido al siguiente problema: " + e.getMessage());
        }
        return sala;
    }

    @Override
    public void validarSala(SalaDTO sala) throws ValidacionSalaException {
        validarNumeroSala(sala.getNumSala());
        
        validarNumeroAsientos(sala.getNumAsientos());
        
    }
    
    private void validarNumeroSala(String numSala) throws ValidacionSalaException {
        if (numSala.length() > 3) {
            throw new ValidacionSalaException("El numero de la sala no puede superar los 4 caracteres");
        }
        if (Pattern.matches("^[A-Za-z][0-9]$", numSala)){
            throw new ValidacionSalaException("El numero de la sala");
        } 
    }
    
    private void validarNumeroAsientos(Integer numAsientos) throws ValidacionSalaException {
        if (numAsientos > minAsientos) {
            throw new ValidacionSalaException("El numero de asientos no puede ser menor a : " + minAsientos);
        }
    }
    
    private void validarEstadoSala(EstadoSala estadoAnterior, EstadoSala estadoNuevo) throws ValidacionSalaException {
        if (estadoAnterior == estadoNuevo) {
            throw new ValidacionSalaException("El estado que se le quiere asignar a la sala es el mismo que tenia anteriormente.");
        }
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
