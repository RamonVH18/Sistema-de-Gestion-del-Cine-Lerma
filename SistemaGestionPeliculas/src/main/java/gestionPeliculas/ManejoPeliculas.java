/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestionPeliculas;

import BOs.FuncionBO;
import BOs.PeliculaBO;
import DTOs.FuncionDTO;
import DTOs.PeliculaDTO;
import Excepciones.ActualizarPeliculaException;
import Excepciones.DarAltaPeliculaException;
import Excepciones.DarBajaPeliculaException;
import Excepciones.EliminarPeliculaException;
import Excepciones.MostrarPeliculasException;
import Excepciones.RegistrarPeliculaException;
import Excepciones.VerificarCamposPeliculaException;
import Excepciones.VerificarTamanioImagenException;
import Excepciones.peliculas.PeliculaActualizacionException;
import Excepciones.peliculas.PeliculaBusquedaException;
import Excepciones.peliculas.PeliculaDarAltaException;
import Excepciones.peliculas.PeliculaDarBajaException;
import Excepciones.peliculas.PeliculaEliminarException;
import Excepciones.peliculas.PeliculaRegistroException;
import Excepciones.peliculas.PeliculasActivasInactivasException;
import Interfaces.IFuncionBO;
import Interfaces.IPeliculaBO;
import java.util.List;

/**
 *
 * @author Daniel M
 */
public class ManejoPeliculas implements IManejoPeliculas {

    private static ManejoPeliculas instancePeliculas;
    private final IPeliculaBO peliculaBO = PeliculaBO.getInstanceBO();
    private final IFuncionBO funcionBO = FuncionBO.getInstanceDAO();

    private ManejoPeliculas() {
    }

    public static ManejoPeliculas getInstancePeliculas() {
        if (instancePeliculas == null) {
            instancePeliculas = new ManejoPeliculas();
        }

        return instancePeliculas;
    }

    @Override
    public PeliculaDTO registrarPelicula(PeliculaDTO peliculaDTO) throws RegistrarPeliculaException {
        try {
            verificarCamposPelicula(peliculaDTO);
            PeliculaDTO peliculaExistente = peliculaBO.buscarPelicula(peliculaDTO.getTitulo());

            if (peliculaExistente != null) {
                throw new RegistrarPeliculaException("No es posible registrar la pelicula ya que ya existe.");
            }

            return peliculaBO.registrarPelicula(peliculaDTO);
        } catch (VerificarCamposPeliculaException e) {
            throw new RegistrarPeliculaException("No se pudo registrar la pelicula: " + e.getMessage());
        } catch (PeliculaBusquedaException e) {
            throw new RegistrarPeliculaException("Ocurrio un error durante la verificacion de la pelicula: " + e.getMessage());
        } catch (PeliculaRegistroException e) {
            throw new RegistrarPeliculaException("Ocurrio un error durante el registro de la pelicula: " + e.getMessage());
        }
    }

    @Override
    public PeliculaDTO actualizarPelicula(PeliculaDTO peliculaDTO) throws ActualizarPeliculaException {
        try {
            verificarCamposPelicula(peliculaDTO);

            PeliculaDTO peliculaExistente = peliculaBO.buscarPelicula(peliculaDTO.getTitulo());

            if (peliculaExistente != null) {
                throw new ActualizarPeliculaException("No es posible actualizar la pelicula ya que ya existe una con el mismo titulo.");
            }

            return peliculaBO.actualizarPelicula(peliculaDTO);
        } catch (VerificarCamposPeliculaException e) {
            throw new ActualizarPeliculaException("No se pudo actualizar la pelicula: " + e.getMessage());
        } catch (PeliculaBusquedaException e) {
            throw new ActualizarPeliculaException("Ocurrio un error durante la verificacion de la pelicula: " + e.getMessage());
        } catch (PeliculaActualizacionException e) {
            throw new ActualizarPeliculaException("Ocurrio un error durante la actualizacion de la pelicula: " + e.getMessage());
        }
    }

    @Override
    public boolean eliminarPelicula(PeliculaDTO peliculaDTO) throws EliminarPeliculaException {
        try {
            List<FuncionDTO> funcionesExistentes = funcionBO.buscarFuncionesPelicula(peliculaDTO.getTitulo());

            if (!funcionesExistentes.isEmpty()) {
                throw new EliminarPeliculaException("No es posible eliminar la pelicula ya que tiene funciones existentes.");
            }

            return peliculaBO.eliminarPelicula(peliculaDTO);
        } catch (PeliculaEliminarException e) {
            throw new EliminarPeliculaException("Ocurrio un error durante la eliminacion de la pelicula: " + e.getMessage());
        }
    }

    @Override
    public boolean darAltaPelicula(PeliculaDTO peliculaDTO) throws DarAltaPeliculaException {
        try {
            if (peliculaDTO == null) {
                throw new DarAltaPeliculaException("No se pudo dar de alta la pelicula ya que los datos estan vacios.");
            }

            if (peliculaDTO.getActivo()) {
                throw new DarAltaPeliculaException("No se pudo dar de alta la pelicula ya que ya esta dada de alta.");
            }
            return peliculaBO.darAltaPelicula(peliculaDTO);
        } catch (PeliculaDarAltaException e) {
            throw new DarAltaPeliculaException("Ocurrio un error durante el alta de la pelicula: " + e.getMessage());
        }
    }

    @Override
    public boolean darBajaPelicula(PeliculaDTO peliculaDTO) throws DarBajaPeliculaException {
        try {
            if (peliculaDTO == null) {
                throw new DarBajaPeliculaException("No se pudo dar de baja la pelicula ya que los datos estan vacios.");
            }

            if (!peliculaDTO.getActivo()) {
                throw new DarBajaPeliculaException("No se pudo dar de baja la pelicula ya que ya esta dada de baja.");
            }

            List<FuncionDTO> funcionesExistentes = funcionBO.buscarFuncionesPelicula(peliculaDTO.getTitulo());

            if (!funcionesExistentes.isEmpty()) {
                throw new DarBajaPeliculaException("No es posible dar de baja la pelicula ya que tiene funciones existentes.");
            }

            return peliculaBO.darBajaPelicula(peliculaDTO);
        } catch (PeliculaDarBajaException e) {
            throw new DarBajaPeliculaException("Ocurrio un error durante la baja de la pelicula: " + e.getMessage());
        }
    }

    @Override
    public List<PeliculaDTO> mostrarPeliculasActivasOInactivas(boolean activo) throws MostrarPeliculasException {
        try {
            List<PeliculaDTO> peliculasActivasOInactivas = peliculaBO.mostrarPeliculasActivasOInactivas(activo);

            if (peliculasActivasOInactivas.isEmpty()) {
                throw new MostrarPeliculasException("No existen peliculas en cartelera o inactivas.");
            }

            return peliculasActivasOInactivas;
        } catch (PeliculasActivasInactivasException e) {
            throw new MostrarPeliculasException("Ocurrio un error durante la obtencion de las peliculas: " + e.getMessage());
        }
    }

    private void verificarCamposPelicula(PeliculaDTO peliculaDTO) throws VerificarCamposPeliculaException {
        if (peliculaDTO == null) {
            throw new VerificarCamposPeliculaException("Todos los campos son obligatorios.");
        }

        if (peliculaDTO.getTitulo() == null || peliculaDTO.getTitulo().isBlank()) {
            throw new VerificarCamposPeliculaException("El titulo es obligatorio.");
        }

        if (peliculaDTO.getTitulo().length() > 100) {
            throw new VerificarCamposPeliculaException("El titulo es demasiado largo.");
        }

        if (peliculaDTO.getDuracion() == null) {
            throw new VerificarCamposPeliculaException("La duracion es obligatoria.");
        }

        if (peliculaDTO.getDuracion() > 300) {
            throw new VerificarCamposPeliculaException("La duracion debe ser maximo de 300 minutos.");
        }

        if (peliculaDTO.getGenero() == null || peliculaDTO.getGenero().isBlank()) {
            throw new VerificarCamposPeliculaException("El genero es obligatorio.");
        }

        if (peliculaDTO.getClasificacion() == null || peliculaDTO.getClasificacion().isBlank()) {
            throw new VerificarCamposPeliculaException("La clasificacion es obligatoria.");
        }

        if (peliculaDTO.getSinopsis() == null || peliculaDTO.getSinopsis().isBlank()) {
            throw new VerificarCamposPeliculaException("La sinopsis es obligatoria.");
        }

        if (peliculaDTO.getSinopsis().length() > 400) {
            throw new VerificarCamposPeliculaException("La sinopsis es demasiado larga.");
        }

    }

    private void verificarTamanioImagen() throws VerificarTamanioImagenException {

    }

}
