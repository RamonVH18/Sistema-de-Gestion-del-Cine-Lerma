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
import org.bson.types.ObjectId;

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

            // Buscar la película por id
            Bson filtro = Filters.eq("_id", pelicula.getIdPelicula());
            Pelicula peliculaActualizar = coleccionPeliculas.find(filtro).first();

            // Validar que existe
            if (peliculaActualizar == null) {
                throw new ActualizarPeliculaException("No se encontró la película para actualizar.");
            }

            // Reemplazamos la pelicula con los nuevos datos
            UpdateResult resultado = coleccionPeliculas.replaceOne(filtro, pelicula);

            // Checamos que si fue modificada, si no lanza una excepcion
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

            // Si se encontro la eliminamos
            DeleteResult eliminar = coleccionPeliculas.deleteOne(filtro);

            // Checamos que se haya eliminado, si no lanza una excepcion
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
     * Desactiva una película activada (campo "activo" en false).
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
    public Pelicula buscarPeliculaPorTitulo(String titulo) throws BuscarPeliculaException {
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

    /**
     * Busca una película por su Id.
     *
     * @param idPelicula Id de la película a buscar
     * @return Película encontrada
     * @throws BuscarPeliculaException si no se encuentra la película o ocurre
     * un error
     */
    @Override
    public Pelicula buscarPeliculaPorId(String idPelicula) throws BuscarPeliculaException {
        MongoClient clienteMongo = null;
        try {
            // Establecer conexión
            clienteMongo = conexion.crearConexion();
            MongoDatabase baseDatos = conexion.obtenerBaseDatos(clienteMongo);
            MongoCollection<Pelicula> coleccionPeliculas = baseDatos.getCollection(nombreColeccion, Pelicula.class);

            // Buscar por id
            Bson filtro = Filters.eq("_id", new ObjectId(idPelicula));
            return coleccionPeliculas.find(filtro).first();
        } catch (MongoException e) {
            throw new BuscarPeliculaException("Error al buscar la película.");
        } finally {
            conexion.cerrarConexion(clienteMongo);
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
     * Busca y retorna una lista de películas aplicando múltiples filtros
     * opcionales: estado (activo/inactivo), clasificación, género y título.
     *
     * Cada filtro se añade a una lista de condiciones y se combinan con una
     * operación lógica AND para ejecutar la consulta en la base de datos
     * MongoDB.
     *
     * @param activo Estado de activación de la película: true para activas,
     * false para inactivas, o null para ignorar este filtro.
     * @param clasificacion Clasificación de la película (ej. "B15"). Se aplica
     * de forma insensible a mayúsculas/minúsculas. Puede ser null o venir vacio
     * para ignorar este filtro.
     * @param genero Género de la película (ej. "Acción", "Drama"). Se aplica de
     * forma insensible a mayúsculas/minúsculas. Puede ser null o vacio para
     * ignorar este filtro.
     * @param titulo Parte o totalidad del título de la película a buscar. Se
     * busca de forma parcial e insensible a mayúsculas/minúsculas. Puede ser
     * null o vacío para ignorar este filtro.
     * @return Lista de películas que cumplen con los filtros proporcionados. Si
     * no se especifica ningún filtro, retorna todas.
     * @throws MostrarPeliculasException Si ocurre un error al realizar la
     * consulta en la base de datos.
     */
    @Override
    public List<Pelicula> mostrarPeliculasFiltradas(Boolean activo, String clasificacion, String genero, String titulo) throws MostrarPeliculasException {
        MongoClient clienteMongo = null;
        List<Pelicula> peliculas = new ArrayList<>();
        try {
            // Hacemos conexion con la BD de Mongo
            clienteMongo = conexion.crearConexion();
            MongoDatabase baseDatos = conexion.obtenerBaseDatos(clienteMongo);
            MongoCollection<Pelicula> coleccionPeliculas = baseDatos.getCollection(nombreColeccion, Pelicula.class);

            // Se crea la lista para almacenar los filtros que se vayan aplicando.
            List<Bson> filtros = new ArrayList<>();

            // Llamar metodos para agregar los filtros (los que apliquen)
            filtroEstado(activo, filtros);
            filtroClasificacion(clasificacion, filtros);
            filtroGenero(genero, filtros);
            filtroTitulo(titulo, filtros);

            // Construir consulta final
            Bson filtro;
            if (filtros.isEmpty()) {
                // si la lista de filtros esta vacia (es decir, no aplico ningun filtro)
                // se crea un documento, es decir, sin filtros, devolvera todas las peliculas
                filtro = new Document();
            } else {
                // si a la lista si aplicaron filtros y contiene algunos, se combinaran todos 
                // con AND
                filtro = Filters.and(filtros);
            }

            // Ejecuta la consulta y obtiene un cursor para iterar sobre los resultados.
            MongoCursor<Pelicula> cursor = coleccionPeliculas.find(filtro).iterator();
            while (cursor.hasNext()) {
                Pelicula peliculaEncontrada = cursor.next();
                // se agrega a la lista de peliculas creada anteriormente
                peliculas.add(peliculaEncontrada);
            }

            return peliculas; // Devuelve la lista de peliculas filtradas
        } catch (MongoException e) {
            throw new MostrarPeliculasException("Error al mostrar las películas filtradas.");
        }
    }

    /**
     * Agrega un filtro por estado de activación de la película a la lista de
     * filtros.
     *
     * @param activo Valor booleano que indica si se deben buscar películas
     * activas (true) o inactivas (false). Si es null, no se añade ningún
     * filtro.
     * @param filtros Lista de filtros a la cual se agregará el filtro de estado
     * si corresponde.
     */
    private void filtroEstado(Boolean activo, List<Bson> filtros) {
        //filtrar por estado activo o inactivo
        if (activo != null) {
            boolean activoPrimitivo = activo;
            filtros.add(Filters.eq("activo", activoPrimitivo));
        }
    }

    /**
     * Agrega un filtro por clasificación a la lista de filtros. La comparación
     * se realiza con coincidencia exacta, pero insensible a
     * mayúsculas/minúsculas.
     *
     * @param clasificacion Clasificación de la película (ej. "A", "B15"). Si es
     * null o está en blanco, no se agrega el filtro.
     * @param filtros Lista de filtros a la cual se agregará el filtro de
     * clasificación si corresponde.
     */
    private void filtroClasificacion(String clasificacion, List<Bson> filtros) {
        //filtrar por clasificacion
        if (clasificacion != null) {
            filtros.add(Filters.regex("clasificacion", "^" + Pattern.quote(clasificacion) + "$", "i"));
        }
    }

    /**
     * Agrega un filtro por género a la lista de filtros. La comparación se
     * realiza con coincidencia exacta, pero insensible a mayúsculas/minúsculas.
     *
     * @param genero Género de la película (ej. "Acción", "Comedia"). Si es null
     * o está en blanco, no se agrega el filtro.
     * @param filtros Lista de filtros a la cual se agregará el filtro de género
     * si corresponde.
     */
    private void filtroGenero(String genero, List<Bson> filtros) {
        //filtrar por genero
        if (genero != null) {
            filtros.add(Filters.regex("genero", "^" + Pattern.quote(genero) + "$", "i"));
        }
    }

    /**
     * Agrega un filtro por título a la lista de filtros. El filtro permite
     * coincidencias parciales e ignora mayúsculas y minúsculas.
     *
     * @param titulo Parte del título de la película a buscar. Si es null o está
     * en blanco, no se agrega el filtro.
     * @param filtros Lista de filtros a la cual se agregará el filtro de título
     * si corresponde.
     */
    private void filtroTitulo(String titulo, List<Bson> filtros) {
        // Filtro por titulo de la pelicula
        if (titulo != null && !titulo.isBlank()) {
            filtros.add(Filters.regex("titulo", titulo, "i")); // Filtro que ignora mayusculas y minusculas, y que solo
            // tiene coincidencias parciales
        }
    }
}
