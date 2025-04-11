/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BOs;

import DAOs.PeliculaDAO;
import DTOs.PeliculaDTO;
import Excepciones.PersistenciaException;
import Excepciones.peliculas.PeliculaActualizacionException;
import Excepciones.peliculas.PeliculaBusquedaException;
import Excepciones.peliculas.PeliculaRegistroException;
import Interfaces.IPeliculaBO;
import Interfaces.IPeliculaDAO;
import Mappers.PeliculaMapper;
import entidades.Pelicula;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ramon Valencia
 */
public class PeliculaBO implements IPeliculaBO{
    
    private static PeliculaBO instancePeliculaBO;
    private final IPeliculaDAO peliculaDAO = PeliculaDAO.getInstanceDAO();
    private final PeliculaMapper mapperPeli = new PeliculaMapper();

    private PeliculaBO() {
    }

    public static PeliculaBO getInstanceBO() {
        if (instancePeliculaBO == null) { // para la primera vez que se llama
            instancePeliculaBO = new PeliculaBO();
        }
        return instancePeliculaBO;
    }

    @Override
    public PeliculaDTO registrarPelicula(PeliculaDTO peliculaDTO) throws PeliculaRegistroException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Boolean actualizarPelicula(PeliculaDTO peliculaDTO) throws PeliculaActualizacionException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public PeliculaDTO buscarPelicula(String nombrePelicula) throws PeliculaBusquedaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public PeliculaDTO buscarTodasPeliculas() throws PeliculaBusquedaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<PeliculaDTO> buscarTodasPeliculasActivas() throws PeliculaBusquedaException {
        try {
            List<Pelicula> peliculas = peliculaDAO.mostrarListaPelicula();
            
            for (int i = 0; i < peliculas.size(); i++) {
                PeliculaDTO peliculaMap = PeliculaMapper.toDTO(peliculas.get(i));
            }
        } catch (PersistenciaException e) {
            throw new PeliculaBusquedaException("Hubo un error al buscar las peliculas activas");
        }
    }

    @Override
    public List<PeliculaDTO> buscarConFiltros(String filtro) throws PeliculaBusquedaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
