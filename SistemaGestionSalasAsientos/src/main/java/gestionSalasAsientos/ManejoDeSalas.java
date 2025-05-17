/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestionSalasAsientos;

import BOs.SalaBO;
import DTOs.GananciaSalaDTO;
import DTOs.SalaNuevaDTO;
import DTOs.SalaViejaDTO;
import Excepciones.AgregarSalaException;
import Excepciones.BuscarSalaException;
import Excepciones.EstadisticasSalaException;
import Excepciones.ModificarSalaException;
import Excepciones.Sala.SalaBusquedaException;
import Excepciones.Sala.SalaModificacionException;
import Excepciones.Sala.SalaRegistroException;
import Excepciones.ValidacionSalaException;
import Interfaces.ISalaBO;
import enums.EstadoSala;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author Ramon Valencia
 */
public class ManejoDeSalas implements IManejoDeSalas {

    private static ManejoDeSalas instanceSalas;
    private final ISalaBO salaBO = SalaBO.getInstanceBO();
    private final Integer minAsientos = 10; // Numero minimo de asientos que puede tener una sala
    private final Integer maxAsientos = 150; // Numero maximo de asientos que puede tener una sala

    private ManejoDeSalas() {

    }

    public static ManejoDeSalas getInstanceSalas() {
        if (instanceSalas == null) {
            instanceSalas = new ManejoDeSalas();
        }
        return instanceSalas;
    }

    @Override
    public SalaViejaDTO agregarSala(SalaNuevaDTO salaNueva) throws AgregarSalaException {
        try {
            
            SalaViejaDTO salaVieja = salaBO.agregarSala(salaNueva);

            return salaVieja;
        } catch (SalaRegistroException e) {
            throw new AgregarSalaException("La sala no se pudo registrar");
        }
    }
    
    @Override
    public void validarSala( String numSala, String numAsientos) throws ValidacionSalaException {
        validarExistenciaSala(numSala);
        
        validarNumeroSala(numSala);

        validarNumeroAsientos(numAsientos);

    }
    
    private void validarExistenciaSala(String numSala) throws ValidacionSalaException {
        try {
            List<SalaViejaDTO> salas = salaBO.buscarSalas("");
            for (SalaViejaDTO sala : salas) {
                if (sala.getNumSala().equalsIgnoreCase(numSala)) {
                    throw new ValidacionSalaException("Ya existe una sala con ese numero de sala");
                }
            }
        } catch (SalaBusquedaException e) {
            throw new ValidacionSalaException("Hubo un error al consultar si ya existia una sala con ese numero de sala");
        }
    }

    private void validarNumeroSala(String numSala) throws ValidacionSalaException {
        if (numSala.length() > 3) {
            throw new ValidacionSalaException("El numero de la sala no puede superar los 4 caracteres");
        }
        if (!Pattern.matches("^[A-Za-z][0-9]$", numSala)) {
            throw new ValidacionSalaException("El numero de la sala tiene que contar con una letra y un numero");
        }
    }

    private void validarNumeroAsientos(String asientos) throws ValidacionSalaException {
        if (!Pattern.matches("\\d+", asientos)) {
            throw new ValidacionSalaException("Debe de ingresar unicamente numeros entero en el campo de numero de asientos");
        }
        
        Integer numAsientos = Integer.valueOf(asientos);
        
        if (numAsientos < minAsientos) {
            throw new ValidacionSalaException("El numero de asientos no puede ser menor a : " + minAsientos);
        }
        if (numAsientos > maxAsientos) {
            throw new ValidacionSalaException("El numero de asientos no puede ser mayor a : " + maxAsientos);
        }
    }

    private void validarEstadoSala(EstadoSala estadoAnterior, EstadoSala estadoNuevo) throws ValidacionSalaException {
        if (estadoAnterior == estadoNuevo) {
            throw new ValidacionSalaException("El estado que se le quiere asignar a la sala es el mismo que tenia anteriormente.");
        }
    }
    
    private void validarSalaSinFuncionesActivas() throws ValidacionSalaException {
        
    }

    @Override
    public List<SalaViejaDTO> cargarSalas(String filtro) throws BuscarSalaException {
        try {
            if (filtro == null) {
                filtro = "";
            }
             filtro = filtro.replace(" ", "");
            List<SalaViejaDTO> salas = salaBO.buscarSalas(filtro);
            return salas;
        } catch (SalaBusquedaException e) {
            throw new BuscarSalaException("Hubo un problema al mostrar las salas. \n Intentelo de nuevo mas adelante");
        }
    }
    
    @Override
    public SalaViejaDTO cargarSalaUnica(String numSala) throws BuscarSalaException {
        
        try {
            if (numSala == null) {
                throw new BuscarSalaException("Debe seleccionar una sala");
            }
            SalaViejaDTO sala = salaBO.buscarSala(numSala);
            return sala;
        } catch (SalaBusquedaException e) {
            throw new BuscarSalaException("Hubo un error al obtener los datos de la sala");
        }
        
    }

    @Override
    public List<SalaViejaDTO> cargarSalasFiltro(String filtro) throws BuscarSalaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<SalaViejaDTO> cargarSalaPorNumero(String numero) throws BuscarSalaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<GananciaSalaDTO> obtenerGananciaSala(List<SalaViejaDTO> listaSalas) throws EstadisticasSalaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Boolean imprimirEstadisticasSala(GananciaSalaDTO gananciaSala) throws EstadisticasSalaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Boolean modificarEstadoSala(String numeroSala, EstadoSala estadoNuevo) throws ModificarSalaException {
        try {
            
            SalaViejaDTO sala = cargarSalaUnica(numeroSala);
            validarEstadoSala(sala.getEstado(), estadoNuevo);
            if (sala == null) {
                throw new ModificarSalaException("Hubo un problema al modificar el estado de la sala. Intente luego");
            }
            sala.setEstado(estadoNuevo);
            Boolean confirmacion = salaBO.modificarSala(numeroSala, estadoNuevo);
            
            return confirmacion;
        } catch (BuscarSalaException | SalaModificacionException e) {
            throw new ModificarSalaException("Hubo un problema al modificar el estado de la sala. Intente luego");
        } catch (ValidacionSalaException e) {
            throw new ModificarSalaException(e.getMessage());
        }
    }

}
