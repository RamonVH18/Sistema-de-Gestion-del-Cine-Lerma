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
import Excepciones.ObtenerPeliculasFiltradasException;
import Excepciones.RegistrarPeliculaException;
import Excepciones.VerificarCamposPeliculaException;
import Excepciones.VerificarImagenException;
import Excepciones.peliculas.MostrarPeliculasFiltradasException;
import Excepciones.peliculas.PeliculaActualizacionException;
import Excepciones.peliculas.PeliculaBusquedaException;
import Excepciones.peliculas.PeliculaDarAltaException;
import Excepciones.peliculas.PeliculaDarBajaException;
import Excepciones.peliculas.PeliculaEliminarException;
import Excepciones.peliculas.PeliculaRegistroException;
import Interfaces.IFuncionBO;
import Interfaces.IPeliculaBO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

/**
 * Clase que implementa la interfaz IManejoPeliculas.
 *
 * Esta clase actúa como un intermediario entre la capa de presentación y la
 * capa de negocio. Su propósito es facilitar el manejo de operaciones
 * relacionadas con películas, como registrar, actualizar, eliminar, dar de
 * alta/baja, entre otras. Implementa validaciones previas antes de delegar las
 * operaciones al subsistema de negocio correspondiente.
 *
 * Se implementa como Singleton para asegurar una única instancia en todo el
 * sistema.
 *
 * @author Daniel M
 */
public class ManejoPeliculas implements IManejoPeliculas {

    // Singleton para manejar instancia única de la clase
    private static ManejoPeliculas instancePeliculas;

    // Interfaces de la capa de negocio para delegar operaciones
    private final IPeliculaBO peliculaBO = PeliculaBO.getInstanceBO();
    private final IFuncionBO funcionBO = FuncionBO.getInstanceDAO();

    // Constructor privado para el Singleton
    private ManejoPeliculas() {
    }

    /**
     * Devuelve la instancia única de ManejoPeliculas (Singleton).
     *
     * @return instancia única de ManejoPeliculas
     */
    public static ManejoPeliculas getInstanceDAO() {
        if (instancePeliculas == null) {
            instancePeliculas = new ManejoPeliculas();
        }
        return instancePeliculas;
    }

    /**
     * Registra una nueva película en el sistema después de realizar las
     * validaciones correspondientes.
     *
     * @param peliculaDTO DTO de la película a registrar
     * @return PeliculaDTO registrada
     * @throws RegistrarPeliculaException si ocurre algún error durante el
     * proceso
     */
    @Override
    public PeliculaDTO registrarPelicula(PeliculaDTO peliculaDTO) throws RegistrarPeliculaException {
        try {
            verificarCamposPelicula(peliculaDTO);
            PeliculaDTO peliculaExistente = peliculaBO.buscarPeliculaPorTitulo(peliculaDTO.getTitulo());

            if (peliculaExistente != null) {
                throw new RegistrarPeliculaException("No es posible registrar la película ya que ya existe.");
            }

            verificarImagenCompleta(peliculaDTO.getImagen());

            return peliculaBO.registrarPelicula(peliculaDTO);
        } catch (VerificarCamposPeliculaException | VerificarImagenException
                | PeliculaBusquedaException | PeliculaRegistroException e) {
            throw new RegistrarPeliculaException("No se pudo registrar la película: " + e.getMessage());
        }
    }

    /**
     * Actualiza los datos de una película existente tras realizar validaciones.
     *
     * @param peliculaActualizarDTO DTO de la película con los nuevos datos
     * @return PeliculaDTO actualizada
     * @throws ActualizarPeliculaException si ocurre algún error durante el
     * proceso
     */
    @Override
    public PeliculaDTO actualizarPelicula(PeliculaDTO peliculaActualizarDTO) throws ActualizarPeliculaException {
        try {
            verificarCamposPelicula(peliculaActualizarDTO);
            verificarImagenCompleta(peliculaActualizarDTO.getImagen());

            PeliculaDTO peliculaExistente = peliculaBO.buscarPeliculaPorTitulo(peliculaActualizarDTO.getTitulo());

            // Verifica que no se repita el título con otra película
            if (peliculaExistente != null
                    && !peliculaExistente.getIdPelicula().equals(peliculaActualizarDTO.getIdPelicula())) {
                throw new ActualizarPeliculaException("Ya existe una película con ese título.");
            }

            // Evita que se actualice si no hay cambios reales
            if (peliculaExistente != null
                    && Arrays.equals(peliculaExistente.getImagen(), peliculaActualizarDTO.getImagen())
                    && peliculaExistente.getTitulo().equals(peliculaActualizarDTO.getTitulo())
                    && peliculaExistente.getGenero().equals(peliculaActualizarDTO.getGenero())
                    && peliculaExistente.getDuracion().equals(peliculaActualizarDTO.getDuracion())
                    && peliculaExistente.getClasificacion().equals(peliculaActualizarDTO.getClasificacion())
                    && peliculaExistente.getSinopsis().equals(peliculaActualizarDTO.getSinopsis())) {
                throw new ActualizarPeliculaException("Debe modificar al menos un campo.");
            }

            return peliculaBO.actualizarPelicula(peliculaActualizarDTO);
        } catch (VerificarCamposPeliculaException | VerificarImagenException
                | PeliculaBusquedaException | PeliculaActualizacionException e) {
            throw new ActualizarPeliculaException("No se pudo actualizar la película: " + e.getMessage());
        }
    }

    /**
     * Elimina una película del sistema si no tiene funciones existentes.
     *
     * @param peliculaDTO DTO de la película a eliminar
     * @return true si la película fue eliminada correctamente
     * @throws EliminarPeliculaException si ocurre algún error durante la
     * eliminación
     */
    @Override
    public boolean eliminarPelicula(PeliculaDTO peliculaDTO) throws EliminarPeliculaException {
        try {
            List<FuncionDTO> funcionesExistentes = funcionBO.buscarFuncionesPelicula(peliculaDTO.getTitulo());

            // No permite eliminar si hay funciones asociadas
            if (!funcionesExistentes.isEmpty()) {
                throw new EliminarPeliculaException("La película tiene funciones asociadas.");
            }

            return peliculaBO.eliminarPelicula(peliculaDTO);
        } catch (PeliculaEliminarException e) {
            throw new EliminarPeliculaException("Error al eliminar la película: " + e.getMessage());
        }
    }

    /**
     * Busca una película por su título.
     *
     * @param nombrePelicula título de la película
     * @return PeliculaDTO correspondiente
     * @throws MostrarPeliculasException si ocurre un error o el título es
     * inválido
     */
    @Override
    public PeliculaDTO buscarPeliculaPorTitulo(String nombrePelicula) throws MostrarPeliculasException {
        try {
            // verifica que se haya ingresado el titulo
            if (nombrePelicula == null || nombrePelicula.isBlank()) {
                throw new MostrarPeliculasException("Debe ingresar un nombre válido.");
            }
            return peliculaBO.buscarPeliculaPorTitulo(nombrePelicula);
        } catch (PeliculaBusquedaException e) {
            throw new MostrarPeliculasException("Error al buscar la película.");
        }
    }

    /**
     * Busca una película por su identificador único.
     *
     * @param idPelicula identificador de la película
     * @return PeliculaDTO correspondiente
     * @throws MostrarPeliculasException si ocurre un error
     */
    @Override
    public PeliculaDTO buscarPeliculaPorId(String idPelicula) throws MostrarPeliculasException {
        try {
            // verifica si no llego un identificador vacio
            if (idPelicula == null || idPelicula.isBlank()) {
                throw new MostrarPeliculasException("El ID no puede estar vacío.");
            }

            return peliculaBO.buscarPeliculaPorId(idPelicula);
        } catch (PeliculaBusquedaException e) {
            throw new MostrarPeliculasException("Error al buscar la película.");
        }
    }

    /**
     * Activa una película previamente desactivada.
     *
     * @param peliculaDTO DTO de la película a dar de alta
     * @return true si se activó correctamente
     * @throws DarAltaPeliculaException si ocurre un error o ya está activa
     */
    @Override
    public boolean darAltaPelicula(PeliculaDTO peliculaDTO) throws DarAltaPeliculaException {
        try {
            // verifica que la pelicula no sea nula
            if (peliculaDTO == null) {
                throw new DarAltaPeliculaException("Los datos de la película están vacíos.");
            }

            // verifica si la pelicula ya estaba activa
            if (peliculaDTO.getActivo()) {
                throw new DarAltaPeliculaException("La película ya está activa.");
            }

            return peliculaBO.darAltaPelicula(peliculaDTO);
        } catch (PeliculaDarAltaException e) {
            throw new DarAltaPeliculaException("Error al dar de alta la película: " + e.getMessage());
        }
    }

    /**
     * Desactiva una película si no tiene funciones asociadas.
     *
     * @param peliculaDTO DTO de la película a dar de baja
     * @return true si se desactivó correctamente
     * @throws DarBajaPeliculaException si ocurre un error o tiene funciones
     * existentes
     */
    @Override
    public boolean darBajaPelicula(PeliculaDTO peliculaDTO) throws DarBajaPeliculaException {
        try {
            // verifica que la pelicula no sea nula
            if (peliculaDTO == null) {
                throw new DarBajaPeliculaException("Los datos de la película están vacíos.");
            }

            // verifica si la pelicula ya estaba activa
            if (!peliculaDTO.getActivo()) {
                throw new DarBajaPeliculaException("La película ya está inactiva.");
            }

            List<FuncionDTO> funcionesExistentes = funcionBO.buscarFuncionesPelicula(peliculaDTO.getTitulo());

            // verifica que no tenga funciones activas
            if (!funcionesExistentes.isEmpty()) {
                throw new DarBajaPeliculaException("Tiene funciones asociadas.");
            }

            return peliculaBO.darBajaPelicula(peliculaDTO);
        } catch (PeliculaDarBajaException e) {
            throw new DarBajaPeliculaException("Error al dar de baja la película: " + e.getMessage());
        }
    }

    /**
     * Muestra una lista de películas aplicando filtros opcionales como
     * clasificación, género, título y estado.
     *
     * @param activo si la película está activa o no
     * @param clasificacion filtro de clasificación
     * @param genero filtro de género
     * @param titulo filtro de título
     * @return lista filtrada de PeliculaDTO
     * @throws ObtenerPeliculasFiltradasException si ocurre un error durante la
     * consulta
     */
    @Override
    public List<PeliculaDTO> mostrarPeliculasFiltradas(Boolean activo, String clasificacion, String genero, String titulo)
            throws ObtenerPeliculasFiltradasException {
        try {
            // devuelve la lista de peliculas filtradas
            return peliculaBO.mostrarPeliculasFiltradas(activo, clasificacion, genero, titulo);
        } catch (MostrarPeliculasFiltradasException e) {
            throw new ObtenerPeliculasFiltradasException("Error al obtener películas filtradas: " + e.getMessage());
        }
    }

    /**
     * Verifica que los campos de un objeto PeliculaDTO sean válidos.
     *
     * Realiza comprobaciones como: Título no nulo, no vacío y con un máximo de
     * 100 caracteres. Duración no nula y menor o igual a 300 minutos. Género,
     * clasificación y sinopsis no nulos ni vacíos. Sinopsis con máximo de 400
     * caracteres.
     *
     * @param peliculaDTO el DTO que contiene los datos de la película.
     * @throws VerificarCamposPeliculaException si algún campo es inválido.
     */
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

    /**
     * Verifica que una imagen cumpla con tamaño, formato y validez.
     *
     * @param imagen los bytes de la imagen.
     * @throws VerificarImagenException si la imagen es inválida, está corrupta
     * o su formato no es permitido.
     */
    private void verificarImagenCompleta(byte[] imagen) throws VerificarImagenException {
        verificarTamanioImagen(imagen);
        verificarFormatoImagen(imagen);
        verificarImagenValida(imagen);
    }

    /**
     * Verifica que el tamaño de la imagen no exceda los 5MB.
     *
     * @param imagen los bytes de la imagen.
     * @throws VerificarImagenException si la imagen es nula o supera el tamaño
     * permitido.
     */
    private void verificarTamanioImagen(byte[] imagen) throws VerificarImagenException {
        if (imagen == null) {
            throw new VerificarImagenException("La imagen es obligatoria.");
        }
        final int MAX_SIZE = 5 * 1024 * 1024; // 5MB maximo
        if (imagen.length > MAX_SIZE) {
            throw new VerificarImagenException("El tamaño de la imagen no debe exceder 5MB.");
        }
    }

    /**
     * Verifica que los bytes de la imagen representen una imagen válida (no
     * corrupta).
     *
     * @param imagen los bytes de la imagen.
     * @throws VerificarImagenException si la imagen es ilegible o inválida.
     */
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

    /**
     * Verifica que el formato de la imagen sea JPG, JPEG o PNG.
     *
     * @param imagen los bytes de la imagen.
     * @throws VerificarImagenException si el formato no está soportado o ocurre
     * un error al leer la imagen.
     */
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
