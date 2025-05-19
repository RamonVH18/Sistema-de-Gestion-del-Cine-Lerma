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
import Excepciones.VerificarImagenException;
import Excepciones.peliculas.PeliculaActualizacionException;
import Excepciones.peliculas.PeliculaBusquedaException;
import Excepciones.peliculas.PeliculaDarAltaException;
import Excepciones.peliculas.PeliculaDarBajaException;
import Excepciones.peliculas.PeliculaEliminarException;
import Excepciones.peliculas.PeliculaRegistroException;
import Excepciones.peliculas.PeliculasActivasInactivasException;
import Interfaces.IFuncionBO;
import Interfaces.IPeliculaBO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

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

    public static ManejoPeliculas getInstanceDAO() {
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

            verificarImagenCompleta(peliculaDTO.getImagen());

            return peliculaBO.registrarPelicula(peliculaDTO);
        } catch (VerificarCamposPeliculaException e) {
            throw new RegistrarPeliculaException("No se pudo registrar la pelicula: " + e.getMessage());
        } catch (VerificarImagenException e) {
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
    public PeliculaDTO buscarPelicula(String nombrePelicula) throws MostrarPeliculasException {
        try {
            if (nombrePelicula == null || nombrePelicula.isBlank()) {
                throw new MostrarPeliculasException("Debe ingresar un nombre valido para buscar la pelicula");    
            }
            return peliculaBO.buscarPelicula(nombrePelicula);
        } catch (PeliculaBusquedaException e) {
            throw new MostrarPeliculasException("Hubo un error al buscar la pelicula.");
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

    private void verificarImagenCompleta(byte[] imagen) throws VerificarImagenException {
        verificarTamanioImagen(imagen);
        verificarFormatoImagen(imagen);
        verificarImagenValida(imagen);
//        verificarDimensionesImagen(imagen);            
    }

    private void verificarTamanioImagen(byte[] imagen) throws VerificarImagenException {
        if (imagen == null) {
            throw new VerificarImagenException("La imagen no puede ser nula.");
        }
        final int MAX_SIZE = 5 * 1024 * 1024; // 5MB maximo
        if (imagen.length > MAX_SIZE) {
            throw new VerificarImagenException("El tamaño de la imagen no debe exceder 5MB.");
        }
    }

    private void verificarImagenValida(byte[] imagen) throws VerificarImagenException {
        try {
            BufferedImage img = ImageIO.read(new ByteArrayInputStream(imagen));
            if (img == null) {
                throw new VerificarImagenException("La imagen proporcionada no es válida o está corrupta.");
            }
        } catch (IOException e) {
            throw new VerificarImagenException("Error al leer la imagen.");
        }
    }

//    private void verificarDimensionesImagen(byte[] imagen) throws VerificarImagenException {
//        try {
//            BufferedImage img = ImageIO.read(new ByteArrayInputStream(imagen));
//            if (img == null) {
//                throw new VerificarImagenException("La imagen proporcionada no es válida.");
//            }
//            int ancho = img.getWidth();
//            int alto = img.getHeight();
//            final int MAX_ANCHO = 1920;
//            final int MAX_ALTO = 1080;
//
//            if (ancho > MAX_ANCHO || alto > MAX_ALTO) {
//                throw new VerificarImagenException("Las dimensiones de la imagen no deben superar " + MAX_ANCHO + "x" + MAX_ALTO + " píxeles.");
//            }
//        } catch (Exception e) {
//            throw new VerificarImagenException("Error al verificar dimensiones de la imagen: " + e.getMessage());
//        }
//    }
    private void verificarFormatoImagen(byte[] imagen) throws VerificarImagenException {
        try (ImageInputStream iis = ImageIO.createImageInputStream(new ByteArrayInputStream(imagen))) {
            Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
            if (!readers.hasNext()) {
                throw new VerificarImagenException("Formato de imagen no soportado.");
            }
            String formato = readers.next().getFormatName().toLowerCase();
            if (!formato.equals("jpg") && !formato.equals("jpeg") && !formato.equals("png")) {
                throw new VerificarImagenException("Solo se permiten imágenes en formato JPG, JPEG o PNG.");
            }
        } catch (Exception e) {
            throw new VerificarImagenException("Error al verificar formato de la imagen.");
        }
    }

}
