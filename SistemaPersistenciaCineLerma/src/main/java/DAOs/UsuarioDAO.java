/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import com.mongodb.client.MongoClient;
import Conexion.MongoConexion;
import Excepciones.usuarios.ActualizarUsuarioException;
import Excepciones.usuarios.ObtenerUsuariosException;
import Interfaces.IUsuarioDAO;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import entidades.Usuario;
import enums.EstadoUsuario;
import enums.Rol;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 *
 * @author sonic
 */
public class UsuarioDAO implements IUsuarioDAO {

    private static UsuarioDAO instance;
    private final MongoConexion conexion = new MongoConexion(); //instancia de la conexion con mongo
    private final String nombreColeccion = "Usuarios"; //Definir el nombre de la coleccion que se manejan estos metodos

    private UsuarioDAO() {

    }

    public static UsuarioDAO getInstance() {
        if (instance == null) {
            instance = new UsuarioDAO();
        }
        return instance;
    }

    /**
     * Muestra la lista de todos los usuarios en el sistema.
     *
     * Este metodo se conecta a la base de datos MongoDB y recupera todos los
     * documentos de la colección de usuarios mapeandolos como entidad Usuario y
     * los almacena en una lista que despues el metodo devuelve. Si ocurre un
     * error durante la conexión o la recuperación de datos, se lanza una
     * ObtenerUsuariosException
     *
     * @return una lista de objetos Usuario que representan todos los usuarios
     * en la base de datos.
     * @throws ObtenerUsuariosException si ocurre un error al obtener la lista
     * de usuarios.
     */
    @Override
    public List<Usuario> mostrarListaUsuarios() throws ObtenerUsuariosException {
        MongoClient clienteMongo = null;  // Inicializa el cliente de MongoDB como nulo.
        List<Usuario> usuarios = new ArrayList<>();  // Crea una lista para almacenar los usuarios encontrados.

        try {
            // Establece la conexión con mongodb
            clienteMongo = conexion.crearConexion();
            // Obtiene la base de datos
            MongoDatabase base = conexion.obtenerBaseDatos(clienteMongo);
            //obtener la coleccion accediendo a la base de datos, se hace de tipo Usuario y se busca segun el nombre de la coleccion "Usuarios"
            MongoCollection<Usuario> coleccion = base.getCollection(nombreColeccion, Usuario.class);

            // Crea un cursor para iterar sobre los documentos de la colección
            MongoCursor<Usuario> cursor = coleccion.find().iterator();
            // bucle que itera sobre los documentos encontrados, los mapea y los agrega a la lista de usuarios.
            while (cursor.hasNext()) {
                Usuario usuarioEncontrado = cursor.next(); //obtener siguiente usuario y guardarlo como Usuario
                usuarios.add(usuarioEncontrado); //agregar el usuario encontrado a la lista
            }

            return usuarios; //devolver la lista una vez que se obtuvieron todos los documentos

        } catch (MongoException e) {
            throw new ObtenerUsuariosException("Error al encontrar usuarios " + e.getMessage());
        } finally {
            if (clienteMongo != null) {
                conexion.cerrarConexion(clienteMongo);
            }
        }
    }

    /**
     * Bloquea un usuario en el sistema cambiando su estado a BLOQUEADO.
     *
     * busca al usuario especificado por su nombre de usuario y actualiza su
     * estado. Si el usuario no se encuentra o si ocurre un error durante la
     * operación, se lanza una ActualizarUsuarioException. Este metodo consiste
     * en una actualizacion de un atributo de Usuario, se verifica que el
     * usuario exista y que se haya actualizado como bloqueado exitosamente, mas
     * adelante en reglas de negocio se establece que solamente se pueden
     * bloquear usuarios con el rol "CLIENTE"
     *
     * @param usuario el objeto Usuario que se desea bloquear.
     * @return true si el usuario fue bloqueado exitosamente, false en caso
     * contrario.
     * @throws ActualizarUsuarioException si ocurre un error al intentar
     * bloquear el usuario o si el usuario no se encuentra.
     */
    @Override
    public Boolean bloquearUsuario(Usuario usuario) throws ActualizarUsuarioException {
        MongoClient clienteMongo = null;
        try {
            clienteMongo = conexion.crearConexion();

            MongoDatabase base = conexion.obtenerBaseDatos(clienteMongo);

            MongoCollection<Usuario> coleccion = base.getCollection(nombreColeccion, Usuario.class);

            //filtro para buscar al usuario por su nombre de usuario.
            Bson filtro = Filters.eq("nombreDeUsuario", usuario.getNombreDeUsuario());

            //encontrar al usuario que se quiere bloquear
            Usuario usuarioBloquear = coleccion.find(filtro).first();

            //si no se encuentra nada se lanza una excepcion
            if (usuarioBloquear == null) {
                throw new ActualizarUsuarioException("No se encontro el usuario para bloquearlo");
            }

            //Se hace la actualizacion del usuario encontrado, cambia el campo "estado" por BLOQUEADO
            Bson bloqueo = Updates.set("estado", EstadoUsuario.BLOQUEADO.toString());

            //Se ejecuta la actualizacion que se creo anteriormente
            UpdateResult resultado = coleccion.updateOne(filtro, bloqueo);

            //Se valida que la actualizacion se haya efectuado exitosamente
            if (resultado.getModifiedCount() == 0) {
                throw new ActualizarUsuarioException("No se bloqueo al usuario");
            }

            //Si todo esta en orden se regresa un valor True booleano
            return true;

        } catch (MongoException e) {
            throw new ActualizarUsuarioException("Error al bloquear el usuario: " + e.getMessage());
        } finally {
            if (clienteMongo != null) {
                conexion.cerrarConexion(clienteMongo);
            }
        }
    }

    /**
     * Desnloquea un usuario en el sistema cambiando su estado a ACTIVO.
     *
     * busca al usuario especificado por su nombre de usuario y actualiza su
     * estado. Si el usuario no se encuentra o si ocurre un error durante la
     * operación, se lanza una ActualizarUsuarioException. Este metodo consiste
     * en una actualizacion de un atributo de Usuario, se verifica que el
     * usuario exista y que se haya actualizado como activo exitosamente, mas
     * adelante en reglas de negocio se establece que solamente se pueden
     * desbloquear usuarios con el rol "CLIENTE"
     *
     * @param usuario el objeto Usuario que se desea bloquear.
     * @return true si el usuario fue bloqueado exitosamente, false en caso
     * contrario.
     * @throws ActualizarUsuarioException si ocurre un error al intentar
     * bloquear el usuario o si el usuario no se encuentra.
     */
    @Override
    public Boolean desbloquearUsuario(Usuario usuario) throws ActualizarUsuarioException {
        MongoClient clienteMongo = null;
        //la logica de este metodo es casi exactamente igual que la de bloquear un usuario, sin embargo este lo marca como 
        //ACTIVO cuando un usuario se encuentra previamente bloqueado, esto se hace mas adelante en reglas de negocio
        try {
            clienteMongo = conexion.crearConexion();

            MongoDatabase base = conexion.obtenerBaseDatos(clienteMongo);

            MongoCollection<Usuario> coleccion = base.getCollection(nombreColeccion, Usuario.class);

            Bson filtro = Filters.eq("nombreDeUsuario", usuario.getNombreDeUsuario());

            Usuario usuarioDesbloquear = coleccion.find(filtro).first();

            if (usuarioDesbloquear == null) {
                throw new ActualizarUsuarioException("No se encontro el usuario para desbloquearlo");
            }

            //Se hace la actualizacion del usuario encontrado, cambia el campo "estado" por ACTIVO
            Bson desbloqueo = Updates.set("estado", EstadoUsuario.ACTIVO.toString());

            UpdateResult resultado = coleccion.updateOne(filtro, desbloqueo);

            if (resultado.getModifiedCount() == 0) {
                throw new ActualizarUsuarioException("No se desbloqueo al usuario");
            }

            return true;

        } catch (MongoException e) {
            throw new ActualizarUsuarioException("Error al desbloquear el usuario: " + e.getMessage());
        } finally {
            if (clienteMongo != null) {
                conexion.cerrarConexion(clienteMongo);
            }
        }
    }

    /**
     * Muestra una lista de usuarios filtrados según los criterios
     * especificados.
     *
     * Este método aplica filtros basados en el estado, rol, fechas de
     * nacimiento y nombre de usuario, y recupera los documentos que cumplen con
     * esos criterios. Segun los parametros que reciba el metodo se iran
     * construyendo los filtros bson el metodo puede manejar si se reciben
     * parametros null. Si ocurre un error durante la conexión o la recuperación
     * de datos, se lanza ObtenerUsuariosExcepcion
     *
     * @param estado el estado del usuario (ACTIVO, BLOQUEADO) para filtrar.
     * @param rol el rol del usuario (CLIENTE, ADMINISTRADOR) para filtrar.
     * @param fechaInicio la fecha de inicio para filtrar usuarios por fecha de
     * nacimiento.
     * @param fechaFin la fecha de fin para filtrar usuarios por fecha de
     * nacimiento.
     * @param nombre el nombre del usuario para filtrar.
     * @return una lista de objetos Usuario que cumplen con los criterios de
     * filtrado.
     * @throws ObtenerUsuariosException si ocurre un error al obtener la lista
     * de usuarios filtrados.
     */
    @Override
    public List<Usuario> mostrarListaUsuariosFiltrada(EstadoUsuario estado, Rol rol, LocalDateTime fechaInicio, LocalDateTime fechaFin, String nombre) throws ObtenerUsuariosException {
        MongoClient clienteMongo = null;
        List<Usuario> usuarios = new ArrayList<>();

        try {
            clienteMongo = conexion.crearConexion();

            MongoDatabase base = conexion.obtenerBaseDatos(clienteMongo);

            MongoCollection<Usuario> coleccion = base.getCollection(nombreColeccion, Usuario.class);

            List<Bson> filtros = new ArrayList<>(); // Lista para almacenar los filtros que se vayan aplicando.

            filtroEstado(estado, filtros);

            // Filtro por fechas
            if (fechaInicio != null && fechaFin != null) {

                Date fecha1 = Date.from(fechaInicio.atZone(ZoneId.systemDefault()).toInstant());
                Date fecha2 = Date.from(fechaFin.atZone(ZoneId.systemDefault()).toInstant());

                filtros.add(Filters.and(
                        Filters.gte("fechaNacimiento", fecha1), // Fecha de nacimiento mayor o igual a fechaInicio.
                        Filters.lte("fechaNacimiento", fecha2) // Fecha de nacimiento menor o igual a fechaFin.
                ));
            }

            filtroNombre(nombre, filtros);

            filtroRol(rol, filtros);

            // Construir consulta final
            Bson filtro = filtros.isEmpty() ? new Document() : Filters.and(filtros);

            // Ejecuta la consulta y obtiene un cursor para iterar sobre los resultados.
            MongoCursor<Usuario> cursor = coleccion.find(filtro).iterator();
            while (cursor.hasNext()) {
                Usuario usuarioEncontrado = cursor.next();
                usuarios.add(usuarioEncontrado);
            }

            return usuarios; // Devuelve la lista de usuarios filtrados

        } catch (MongoException e) {
            throw new ObtenerUsuariosException("Error al obtener usuarios: " + e.getMessage());
        } finally {
            if (clienteMongo != null) {
                conexion.cerrarConexion(clienteMongo);
            }
        }
    }

    /**
     * Obtiene un usuario específico basado en su nombre de usuario y
     * contraseña.
     *
     * busca al usuario que
     * coincide con las credenciales proporcionadas y devuelve el objeto Usuario
     * correspondiente. Si ocurre un error durante la conexión o la búsqueda, lanza ObtenerUsuariosException
     *
     * @param nombreUsuario el nombre de usuario del usuario que se desea
     * obtener.
     * @param contrasena la contraseña del usuario que se desea obtener.
     * @return el objeto Usuario correspondiente a las credenciales
     * proporcionadas, o null si no se encuentra.
     * @throws ObtenerUsuariosException si ocurre un error al intentar obtener
     * el usuario.
     */
    @Override
    public Usuario obtenerUsuario(String nombreUsuario, String contrasena) throws ObtenerUsuariosException {
        MongoClient clienteMongo = null;
        try {
            clienteMongo = conexion.crearConexion();
            MongoDatabase base = conexion.obtenerBaseDatos(clienteMongo);

            // Crea un filtro para buscar al usuario por su nombre de usuario y contraseña
            Bson filtro = Filters.and(
                    Filters.eq("nombreDeUsuario", nombreUsuario),
                    Filters.eq("contrasenia", contrasena));

            MongoCollection<Usuario> coleccionUsuarios = base.getCollection(nombreColeccion, Usuario.class);

            Usuario usuarioEncontrado = coleccionUsuarios.find(filtro).first();

            return usuarioEncontrado;

        } catch (MongoException e) {
            throw new ObtenerUsuariosException("Error al obtener el cliente: " + e.getMessage());
        } finally {
            if (clienteMongo != null) {
                conexion.cerrarConexion(clienteMongo);
            }
        }
    }

    private void filtroEstado(EstadoUsuario estado, List<Bson> filtros) {
        //filtrar por estado
        if (estado != null) {
            filtros.add(Filters.eq("estado", estado.toString()));
        }
    }

    private void filtroNombre(String nombre, List<Bson> filtros) {
        // Filtro por nombre de usuario
        if (nombre != null && !nombre.isEmpty()) {
            filtros.add(Filters.regex("nombreDeUsuario", nombre, "i")); // Filtro que busca coincidencias en el nombre de usuario, ignorando mayúsculas, construye un regex por lo que 
            // solo una parte del patron debe coincidir con la busqueda
        }
    }

    private void filtroRol(Rol rol, List<Bson> filtros) {
        // Filtro por rol.
        if (rol != null) {
            filtros.add(Filters.eq("rol", rol.toString()));
        }
    }

}
