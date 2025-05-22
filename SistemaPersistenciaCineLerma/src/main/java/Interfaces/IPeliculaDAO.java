package Interfaces;

import Excepciones.peliculas.*;
import entidades.Pelicula;
import java.util.List;

/**
 * Interfaz que define las operaciones CRUD y de búsqueda para el manejo de
 * objetos de Pelicula.
 *
 * @author Daniel M
 */
public interface IPeliculaDAO {

    /**
     * Registra una nueva película en el sistema.
     *
     * @param pelicula La película a registrar.
     * @return La película registrada, posiblemente con su ID generado.
     * @throws RegistrarPeliculaException Si ocurre un error al registrar la
     * película.
     */
    public Pelicula registrarPelicula(Pelicula pelicula) throws RegistrarPeliculaException;

    /**
     * Actualiza los datos de una película existente.
     *
     * @param pelicula La película con los datos actualizados.
     * @return La película actualizada.
     * @throws ActualizarPeliculaException Si ocurre un error al actualizar la
     * película.
     */
    public Pelicula actualizarPelicula(Pelicula pelicula) throws ActualizarPeliculaException;

    /**
     * Elimina una película de la base de datos.
     *
     * @param pelicula La película a eliminar.
     * @return true si la película fue eliminada correctamente, false en caso
     * contrario.
     * @throws EliminarPeliculaException Si ocurre un error al eliminar la
     * película.
     */
    public Boolean eliminarPelicula(Pelicula pelicula) throws EliminarPeliculaException;

    /**
     * Marca una película como activa (alta lógica).
     *
     * @param pelicula La película a dar de alta.
     * @return true si se activó correctamente, false si falló.
     * @throws DarAltaPeliculaException Si ocurre un error al activar la
     * película.
     */
    public Boolean darAltaPelicula(Pelicula pelicula) throws DarAltaPeliculaException;

    /**
     * Marca una película como inactiva.
     *
     * @param pelicula La película a dar de baja.
     * @return true si se desactivó correctamente, false si falló.
     * @throws DarBajaPeliculaException Si ocurre un error al desactivar la
     * película.
     */
    public Boolean darBajaPelicula(Pelicula pelicula) throws DarBajaPeliculaException;

    /**
     * Busca una película por su título.
     *
     * @param titulo El título de la película.
     * @return La película encontrada.
     * @throws BuscarPeliculaException Si ocurre un error durante la búsqueda.
     */
    public Pelicula buscarPeliculaPorTitulo(String titulo) throws BuscarPeliculaException;

    /**
     * Busca una película por su ID.
     *
     * @param idPelicula El ID de la película en formatoString.
     * @return La película correspondiente al ID.
     * @throws BuscarPeliculaException Si ocurre un error durante la búsqueda.
     */
    public Pelicula buscarPeliculaPorId(String idPelicula) throws BuscarPeliculaException;

    /**
     * Muestra una lista de películas según su estado (activas o inactivas).
     *
     * @param activo true para películas activas, false para inactivas.
     * @return Lista de películas según el estado indicado.
     * @throws MostrarPeliculasException Si ocurre un error al obtener las
     * películas.
     */
    public List<Pelicula> mostrarPeliculasActivasOInactivas(boolean activo) throws MostrarPeliculasException;

    /**
     * Muestra una lista de películas aplicando filtros opcionales.
     *
     * @param activo Estado de la película true = activas, false = inactivas.
     * @param clasificacion Filtro por clasificación (ej. "A", "B15").
     * @param genero Filtro por género (ej. "Acción", "Comedia").
     * @param titulo Filtro parcial por título.
     * @return Lista de películas que coinciden con los filtros aplicados.
     * @throws MostrarPeliculasException Si ocurre un error al obtener las
     * películas.
     */
    public List<Pelicula> mostrarPeliculasFiltradas(Boolean activo, String clasificacion, String genero, String titulo) throws MostrarPeliculasException;
}
