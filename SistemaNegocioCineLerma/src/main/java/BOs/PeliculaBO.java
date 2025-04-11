/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BOs;

import DTOs.PeliculaDTO;
import Excepciones.peliculas.PeliculaActualizacionException;
import Excepciones.peliculas.PeliculaBusquedaException;
import Excepciones.peliculas.PeliculaRegistroException;
import Interfaces.IPeliculaBO;
import java.util.List;

/**
 *
 * @author Ramon Valencia
 */
public class PeliculaBO implements IPeliculaBO{
    
    private static PeliculaBO instancePeliculaBO;

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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<PeliculaDTO> buscarConFiltros(String filtro) throws PeliculaBusquedaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
