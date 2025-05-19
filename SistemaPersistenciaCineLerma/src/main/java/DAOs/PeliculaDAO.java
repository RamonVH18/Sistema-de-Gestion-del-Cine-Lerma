/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Conexion.MongoConexion;
import Excepciones.peliculas.*;
import Interfaces.IPeliculaDAO;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import entidades.Pelicula;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 * Clase DAO para manejar operaciones relacionadas con la colección de películas
 * en MongoDB. Implementa la interfaz IPeliculaDAO.
 *
 * Se utiliza el patrón Singleton para asegurar que solo exista una instancia de
 * esta clase durante la ejecución.
 *
 * @author Daniel M
 */
public class PeliculaDAO implements IPeliculaDAO {

    private static PeliculaDAO instance;
    private final MongoConexion conexion = new MongoConexion(); // Instancia de la clase para manejar conexión Mongo
    private final String nombreColeccion = "Peliculas"; // Nombre de la colección en la base de datos

    /**
     * Constructor privado para aplicar el patrón Singleton.
     */
    private PeliculaDAO() {
    }

    /**
     * Obtiene la única instancia de PeliculaDAO (patrón Singleton).
     *
     * @return instancia única de PeliculaDAO
     */
    public static PeliculaDAO getInstanceDAO() {
        if (instance == null) {
            instance = new PeliculaDAO();
        }
        return instance;
    }

    /**
     * Registra una nueva película en la base de datos.
     *
     * @param pelicula Objeto Pelicula a registrar
     * @return Pelicula registrada
     * @throws RegistrarPeliculaException si ocurre un error durante la
     * operación
     */
    @Override
    public Pelicula registrarPelicula(Pelicula pelicula) throws RegistrarPeliculaException {
        MongoClient clienteMongo = null;
        try {
            // Establecer conexión con MongoDB
            clienteMongo = conexion.crearConexion();
            MongoDatabase baseDatos = conexion.obtenerBaseDatos(clienteMongo);
            MongoCollection<Pelicula> coleccionPeliculas = baseDatos.getCollection(nombreColeccion, Pelicula.class);

            // Insertar la nueva película
            coleccionPeliculas.insertOne(pelicula);
            return pelicula;

        } catch (MongoException e) {
            throw new RegistrarPeliculaException("Ocurrio un problema al registrar la pelicula.");
        } finally {
            // Cerrar la conexión
            conexion.cerrarConexion(clienteMongo);
        }
    }

    /**
     * Actualiza los datos de una película existente.
     *
     * @param pelicula Objeto Pelicula con los nuevos datos
     * @return Pelicula actualizada
     * @throws ActualizarPeliculaException si no se encuentra o no se puede
     * actualizar la película
     */
    @Override
    public Pelicula actualizarPelicula(Pelicula pelicula) throws ActualizarPeliculaException {
        MongoClient clienteMongo = null;
        try {
            // Conexión a la base de datos
            clienteMongo = conexion.crearConexion();
            MongoDatabase baseDatos = conexion.obtenerBaseDatos(clienteMongo);
            MongoCollection<Pelicula> coleccionPeliculas = baseDatos.getCollection(nombreColeccion, Pelicula.class);

            // Buscar la película por título
            Bson filtro = Filters.eq("titulo", pelicula.getTitulo());
            Pelicula peliculaActualizar = coleccionPeliculas.find(filtro).first();

            // Validar que existe
            if (peliculaActualizar == null) {
                throw new ActualizarPeliculaException("No se encontró la película para actualizar.");
            }

            // Reemplazar el documento
            UpdateResult resultado = coleccionPeliculas.replaceOne(filtro, pelicula);

            // Validar que fue modificada
            if (resultado.getModifiedCount() == 0) {
                throw new ActualizarPeliculaException("No se actualizó la película.");
            }

            return pelicula;

        } catch (MongoException e) {
            throw new ActualizarPeliculaException("Ocurrió un problema al actualizar la película.");
        } finally {
            conexion.cerrarConexion(clienteMongo);
        }
    }

    /**
     * Elimina una película de la base de datos.
     *
     * @param pelicula Película a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     * @throws EliminarPeliculaException si ocurre un error o no se encuentra la
     * película
     */
    @Override
    public Boolean eliminarPelicula(Pelicula pelicula) throws EliminarPeliculaException {
        MongoClient clienteMongo = null;
        try {
            // Conexión y obtención de colección
            clienteMongo = conexion.crearConexion();
            MongoDatabase baseDatos = conexion.obtenerBaseDatos(clienteMongo);
            MongoCollection<Pelicula> coleccionPeliculas = baseDatos.getCollection(nombreColeccion, Pelicula.class);

            // Buscar por título
            Bson filtro = Filters.eq("titulo", pelicula.getTitulo());
            Pelicula peliculaEliminar = coleccionPeliculas.find(filtro).first();

            if (peliculaEliminar == null) {
                throw new EliminarPeliculaException("No se encontró la película para eliminar.");
            }

            // Eliminar el documento
            DeleteResult eliminar = coleccionPeliculas.deleteOne(filtro);

            if (eliminar.getDeletedCount() == 0) {
                throw new EliminarPeliculaException("No se eliminó ninguna película.");
            }

            return eliminar.getDeletedCount() > 0;

        } catch (MongoException e) {
            throw new EliminarPeliculaException("Ocurrió un problema al eliminar la película.");
        } finally {
            conexion.cerrarConexion(clienteMongo);
        }
    }

    /**
     * Activa una película desactivada (campo "activo" en true).
     *
     * @param pelicula Película a activar
     * @return true si se activó correctamente, false si ya estaba activa
     * @throws DarAltaPeliculaException si ocurre un error o no se encuentra la
     * película
     */
    @Override
    public Boolean darAltaPelicula(Pelicula pelicula) throws DarAltaPeliculaException {
        MongoClient clienteMongo = null;
        try {
            // Conexión a MongoDB
            clienteMongo = conexion.crearConexion();
            MongoDatabase baseDatos = conexion.obtenerBaseDatos(clienteMongo);
            MongoCollection<Pelicula> coleccionPeliculas = baseDatos.getCollection(nombreColeccion, Pelicula.class);

            // Buscar película por título
            Bson filtro = Filters.eq("titulo", pelicula.getTitulo());
            Pelicula peliculaActualizar = coleccionPeliculas.find(filtro).first();

            if (peliculaActualizar != null) {
                if (!peliculaActualizar.getActivo()) {
                    // Si está desactivada, la activamos
                    Bson update = Updates.set("activo", true);
                    UpdateResult resultado = coleccionPeliculas.updateOne(filtro, update);
                    return resultado.getModifiedCount() > 0;
                } else {
                    // Ya está activa
                    return false;
                }
            } else {
                throw new DarAltaPeliculaException("La película no fue encontrada.");
            }

        } catch (MongoException e) {
            throw new DarAltaPeliculaException("Error al dar de alta la película.");
        } finally {
            conexion.cerrarConexion(clienteMongo);
        }
    }

    /**
     * Desactiva una película (campo "activo" en false).
     *
     * @param pelicula Película a desactivar
     * @return true si se desactivó correctamente, false si ya estaba inactiva
     * @throws DarBajaPeliculaException si ocurre un error o no se encuentra la
     * película
     */
    @Override
    public Boolean darBajaPelicula(Pelicula pelicula) throws DarBajaPeliculaException {
        MongoClient clienteMongo = null;
        try {
            // Conexión con la base de datos
            clienteMongo = conexion.crearConexion();
            MongoDatabase baseDatos = conexion.obtenerBaseDatos(clienteMongo);
            MongoCollection<Pelicula> coleccionPeliculas = baseDatos.getCollection(nombreColeccion, Pelicula.class);

            // Buscar por título
            Bson filtro = Filters.eq("titulo", pelicula.getTitulo());
            Pelicula peliculaActualizar = coleccionPeliculas.find(filtro).first();

            if (peliculaActualizar != null) {
                if (peliculaActualizar.getActivo()) {
                    // Si está activa, la desactivamos
                    Bson update = Updates.set("activo", false);
                    UpdateResult resultado = coleccionPeliculas.updateOne(filtro, update);
                    return resultado.getModifiedCount() > 0;
                } else {
                    // Ya está desactivada
                    return false;
                }
            } else {
                throw new DarBajaPeliculaException("La película no fue encontrada.");
            }

        } catch (MongoException e) {
            throw new DarBajaPeliculaException("Error al dar de baja la película.");
        } finally {
            conexion.cerrarConexion(clienteMongo);
        }
    }

    /**
     * Busca una película por su título.
     *
     * @param titulo Título de la película a buscar
     * @return Película encontrada
     * @throws BuscarPeliculaException si no se encuentra la película o ocurre
     * un error
     */
    @Override
    public Pelicula buscarPelicula(String titulo) throws BuscarPeliculaException {
        MongoClient clienteMongo = null;
        try {
            // Establecer conexión
            clienteMongo = conexion.crearConexion();
            MongoDatabase baseDatos = conexion.obtenerBaseDatos(clienteMongo);
            MongoCollection<Pelicula> coleccionPeliculas = baseDatos.getCollection(nombreColeccion, Pelicula.class);

            // Buscar por título
            Bson filtro = Filters.regex("titulo", "^" + Pattern.quote(titulo) + "$", "i");
            return coleccionPeliculas.find(filtro).first();
        } catch (MongoException e) {
            throw new BuscarPeliculaException("Error al buscar la película.");
        } finally {
            conexion.cerrarConexion(clienteMongo);
        }
    }

    @Override
    public List<Pelicula> mostrarPeliculasFiltradas(Boolean activo, String clasificacion, String genero, String titulo) throws MostrarPeliculasException {
        MongoClient clienteMongo = null;
        List<Pelicula> peliculas = new ArrayList<>();
        try {
            clienteMongo = conexion.crearConexion();
            MongoDatabase baseDatos = conexion.obtenerBaseDatos(clienteMongo);
            MongoCollection<Pelicula> coleccionPeliculas = baseDatos.getCollection(nombreColeccion, Pelicula.class);

            List<Bson> filtros = new ArrayList<>(); // Lista para almacenar los filtros que se vayan aplicando.

            filtroEstado(activo, filtros);
            filtroClasificacion(clasificacion, filtros);
            filtroGenero(genero, filtros);
            filtroTitulo(titulo, filtros);

            // Construir consulta final
            Bson filtro = filtros.isEmpty() ? new Document() : Filters.and(filtros);

            // Ejecuta la consulta y obtiene un cursor para iterar sobre los resultados.
            MongoCursor<Pelicula> cursor = coleccionPeliculas.find(filtro).iterator();
            while (cursor.hasNext()) {
                Pelicula usuarioEncontrado = cursor.next();
                peliculas.add(usuarioEncontrado);
            }

            return peliculas; // Devuelve la lista de peliculas filtradas
        } catch (MongoException e) {
            throw new MostrarPeliculasException("Error al mostrar las películas filtradas.");
        }
    }

    private void filtroEstado(Boolean activo, List<Bson> filtros) {
        //filtrar por estado activo o inactivo
        if (activo != null) {
            boolean activoPrimitivo = activo;
            filtros.add(Filters.eq("activo", activoPrimitivo));
        }
    }

    private void filtroClasificacion(String clasificacion, List<Bson> filtros) {
        //filtrar por clasificacion
        if (clasificacion != null) {
            filtros.add(Filters.regex("clasificacion", "^" + Pattern.quote(clasificacion) + "$", "i"));
        }
    }

    private void filtroGenero(String genero, List<Bson> filtros) {
        //filtrar por genero
        if (genero != null) {
            filtros.add(Filters.regex("genero", "^" + Pattern.quote(genero) + "$", "i"));
        }
    }

    private void filtroTitulo(String titulo, List<Bson> filtros) {
        // Filtro por titulo de la pelicula
        if (titulo != null && !titulo.isBlank()) {
            filtros.add(Filters.regex("titulo", titulo, "i")); // Filtro que ignora mayusculas y minusculas, y que solo
            // tiene coincidencias parciales
        }
    }

    /**
     * Obtiene una lista de películas activas o inactivas dependiendo del
     * parametro recibido
     *
     * @param activo true para filtrar por peliculas activas, false para filtrar
     * por inactivas
     * @return Lista de películas activas/inactivas.
     * @throws MostrarPeliculasException si ocurre un error al obtener las
     * películas.
     */
    @Override
    public List<Pelicula> mostrarPeliculasActivasOInactivas(boolean activo) throws MostrarPeliculasException {
        MongoClient clienteMongo = null;
        try {
            // Se establece la conexión con la base de datos y se accede a la colección
            clienteMongo = conexion.crearConexion();
            MongoDatabase baseDatos = conexion.obtenerBaseDatos(clienteMongo);
            MongoCollection<Pelicula> coleccionPeliculas = baseDatos.getCollection(nombreColeccion, Pelicula.class);

            // Se filtran las películas dependiendo el filtro recibido
            Bson filtro = Filters.eq("activo", activo);
            List<Pelicula> peliculasFiltradas = coleccionPeliculas.find(filtro).into(new ArrayList<>());

            return peliculasFiltradas;
        } catch (MongoException e) {
            throw new MostrarPeliculasException("Error al mostrar las películas.");
        } finally {
            conexion.cerrarConexion(clienteMongo);
        }
    }

    /**
     * Obtiene una lista con todas las películas en la base de datos, sin
     * importar si están activas o inactivas.
     *
     * @return Lista de todas las películas.
     * @throws MostrarPeliculasException si ocurre un error al obtener las
     * películas.
     */
    @Override
    public List<Pelicula> mostrarTodasLasPeliculas() throws MostrarPeliculasException {
        MongoClient clienteMongo = null;
        try {
            // Se establece la conexión con la base de datos y se accede a la colección
            clienteMongo = conexion.crearConexion();
            MongoDatabase baseDatos = conexion.obtenerBaseDatos(clienteMongo);
            MongoCollection<Pelicula> coleccionPeliculas = baseDatos.getCollection(nombreColeccion, Pelicula.class);

            // Se obtienen todas las películas sin aplicar ningún filtro
            return coleccionPeliculas.find().into(new ArrayList<>());
        } catch (MongoException e) {
            throw new MostrarPeliculasException("Error al mostrar todas las películas.");
        } finally {
            conexion.cerrarConexion(clienteMongo);
        }
    }

}
