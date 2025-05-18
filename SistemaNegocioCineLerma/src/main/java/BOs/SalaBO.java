/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BOs;

import DAOs.SalaDAO;
import DTOs.AsientoDTO;
import DTOs.SalaNuevaDTO;
import DTOs.SalaViejaDTO;
import Excepciones.Sala.SalaBusquedaException;
import Excepciones.Sala.SalaModificacionException;
import Excepciones.Sala.SalaRegistroException;
import Excepciones.salas.BuscarSalaException;
import Excepciones.salas.CreacionSalaException;
import Excepciones.salas.ModificarSalaException;
import Interfaces.ISalaBO;
import Interfaces.ISalaDAO;
import Interfaces.mappers.IAsientoMapper;
import Interfaces.mappers.ISalaMapper;
import Mappers.AsientoMapper;
import Mappers.SalaMapper;
import entidades.Sala;
import enums.EstadoSala;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ramon Valencia
 */
public class SalaBO implements ISalaBO {

    private static SalaBO instanceBO;
    private final ISalaMapper mapperSala = new SalaMapper();
    private final IAsientoMapper mapperAsiento = new AsientoMapper();
    private final ISalaDAO salaDAO = SalaDAO.getInstanceDAO();

    private SalaBO() {
    }

    public static SalaBO getInstanceBO() {
        if (instanceBO == null) {
            instanceBO = new SalaBO();
        }
        return instanceBO;
    }

    @Override
    public SalaViejaDTO agregarSala(SalaNuevaDTO salaNueva) throws SalaRegistroException {
        try {
            Sala sala = mapperSala.toSalaNuevaEntidad(salaNueva);

            sala = salaDAO.agregarSala(sala);

            List<AsientoDTO> asientosDTO = mapperAsiento.toAsientoDTO(sala.getAsientos());
            SalaViejaDTO salaVieja = mapperSala.toSalaViejaDTO(sala);
            salaVieja.setAsientos(asientosDTO);

            return salaVieja;
        } catch (CreacionSalaException e) {
            throw new SalaRegistroException("Hubo un error al agregar la sala en negocio: " + e.getMessage());
        }
    }

    @Override
    public SalaViejaDTO buscarSala(String numSala) throws SalaBusquedaException {
        try {
            
            Sala sala = salaDAO.buscarSala(numSala);

            List<AsientoDTO> asientosDTO = mapperAsiento.toAsientoDTO(sala.getAsientos());
            SalaViejaDTO salaVieja = mapperSala.toSalaViejaDTO(sala);
            salaVieja.setAsientos(asientosDTO);

            return salaVieja;
        } catch (BuscarSalaException e) {
            throw new SalaBusquedaException("Hubo un erro al buscar la sala: " + e.getMessage());
        }
    }

    @Override
    public List<SalaViejaDTO> buscarSalas(String filtroSalas, Boolean filtrarActivas) throws SalaBusquedaException {
        try {
            
            List<Sala> salas = salaDAO.buscarSalas(filtroSalas, filtrarActivas);
            Sala sala;
            List<SalaViejaDTO> salasViejas = new ArrayList<>();
            for (int i = 0; i < salas.size(); i++) {
                sala = salas.get(i);
                
                List<AsientoDTO> asientosDTO = mapperAsiento.toAsientoDTO(sala.getAsientos());
                SalaViejaDTO salaVieja = mapperSala.toSalaViejaDTO(sala);
                salaVieja.setAsientos(asientosDTO);
                salasViejas.add(salaVieja);
            }
            return salasViejas;
        } catch (BuscarSalaException e) {
            throw new SalaBusquedaException("Hubo un error al buscar las salas: " + e.getMessage());
        }
    }

    @Override
    public Boolean modificarSala(String numSala, EstadoSala estadoNuevo) throws SalaModificacionException {
        try {
            
            Boolean confirmacion = salaDAO.modificarEstadoSala(numSala, estadoNuevo);
            
            return confirmacion;
        } catch (ModificarSalaException e) {
            throw new SalaModificacionException("Hubo un error al modificar el estado de la sala: " + e.getMessage());
        }
    }

}
