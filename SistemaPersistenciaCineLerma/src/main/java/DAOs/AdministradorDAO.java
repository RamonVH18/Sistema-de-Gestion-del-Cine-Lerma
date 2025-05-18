/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Conexion.MongoConexion;
import Excepciones.usuarios.EncontrarAdministradorException;
import Excepciones.usuarios.RegistrarAministradorException;
import Excepciones.usuarios.ValidarUsuarioException;
import Interfaces.IAdministradorDAO;
import com.mongodb.MongoException;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import entidades.Administrador;
import org.bson.conversions.Bson;

/**
 *
 * @author sonic
 */
public class AdministradorDAO implements IAdministradorDAO {

    private static AdministradorDAO instance;
    private final MongoConexion conexion = new MongoConexion();
    private final String nombreColeccion = "Usuarios"; // Nombre de la colección donde se almacenan los administradores.

    //Quizas agregar una proyeccion
    private AdministradorDAO() {

    }

    public static AdministradorDAO getInstance() {
        if (instance == null) {
            instance = new AdministradorDAO();
        }
        return instance;
    }

    /**
     * Registra un nuevo administrador en la base de datos.
     *
     * Este método valida la unicidad del nombre de usuario, correo, telefono o
     * RFC, si es válido, inserta el administrador en la colección. Si el nombre
     * de usuario ya está en uso o ocurre un error durante la operación, se
     * lanza RegistrarAdministradorException
     *
     * @param administrador el objeto Administrador que se desea registrar.
     * @return el objeto Administrador registrado, con información actualizada.
     * @throws RegistrarAministradorException si ocurre un error al intentar
     * registrar el administrador.
     */
    @Override
    public Administrador registrarAdministrador(Administrador administrador) throws RegistrarAministradorException {
        MongoClient clienteMongo = null;
        try {
            clienteMongo = conexion.crearConexion();
            MongoDatabase base = conexion.obtenerBaseDatos(clienteMongo);

            MongoCollection<Administrador> coleccion = base.getCollection(nombreColeccion, Administrador.class);

            //Llama a un metodo para validar que no se este repitiendo un nombre de usuario, telefono, correo o RFC que ya exista en la base de datos
            validarUnicidad(coleccion, administrador);

            //Si se pasan las validaciones se inserta el administrador en la coleccion de usuarios
            coleccion.insertOne(administrador);

            return administrador; // Devuelve el administrador registrado.

        } catch (MongoWriteException e) {
            if (e.getError().getCode() == 11000) {
                throw new RegistrarAministradorException("El nombre de usuario ya está en uso: " + e.getMessage());
            }

        } catch (MongoException e) {
            throw new RegistrarAministradorException(e.getMessage());
        } catch (ValidarUsuarioException e) {
            throw new RegistrarAministradorException(e.getMessage());
        } finally {
            if (clienteMongo != null) {
                conexion.cerrarConexion(clienteMongo);
            }
        }
        return null;
    }

    /**
     * Obtiene un administrador específico basado en su nombre de usuario y
     * contraseña.
     *
     * Este método se conecta a la base de datos MongoDB, busca al administrador
     * que coincide con las credenciales proporcionadas y devuelve el objeto
     * Administrador correspondiente. Si ocurre un error durante la conexión o
     * la búsqueda, se lanza una excepción personalizada.
     *
     * @param nombreUsuario el nombre de usuario del administrador que se desea
     * obtener.
     * @param contrasena la contraseña del administrador que se desea obtener.
     * @return el objeto Administrador correspondiente a las credenciales
     * proporcionadas, o null si no se encuentra.
     * @throws EncontrarAdministradorException si ocurre un error al intentar
     * obtener el administrador.
     */
    @Override
    public Administrador obtenerAdministrador(String nombreUsuario, String contrasena) throws EncontrarAdministradorException {
        MongoClient clienteMongo = null;
        try {
            clienteMongo = conexion.crearConexion();
            MongoDatabase base = conexion.obtenerBaseDatos(clienteMongo);

            // Crea un filtro para buscar al administrador por su nombre de usuario, contraseña y rol.
            Bson filtro = Filters.and(
                    Filters.eq("nombreDeUsuario", nombreUsuario),
                    Filters.eq("contrasenia", contrasena),
                    Filters.eq("rol", "ADMINISTRADOR"));

            // Obtiene la colección de administradores de la base de datos manejando entidad administrador
            MongoCollection<Administrador> coleccion = base.getCollection(nombreColeccion, Administrador.class);

            // Busca el administrador en la colección utilizando el filtro.
            Administrador adminEncontrado = coleccion.find(filtro).first();

            return adminEncontrado; // Devuelve el administrador encontrado o null si no se encuentra.

        } catch (MongoException e) {
            throw new EncontrarAdministradorException("Error al obtener el usuario: " + e.getMessage());
        } finally {
            if (clienteMongo != null) {
                conexion.cerrarConexion(clienteMongo);
            }
        }
    }

    //Metodo auxiliar que valida la unicidad cuando se va a registrar un administrador, si alguno de los datos se repite se lanza un ValidarUsuarioException
    public void validarUnicidad(MongoCollection coleccion, Administrador admin) throws ValidarUsuarioException {
        if (coleccion.find(Filters.eq("nombreDeUsuario", admin.getNombreDeUsuario())).first() != null) {
            throw new ValidarUsuarioException("El nombre de usuario ya está en uso");
        }
        if (coleccion.find(Filters.eq("correoElectronico", admin.getCorreoElectronico())).first() != null) {
            throw new ValidarUsuarioException("El correo electrónico ya está en uso");
        }
        if (coleccion.find(Filters.eq("telefono", admin.getTelefono())).first() != null) {
            throw new ValidarUsuarioException("El número de teléfono ya está en uso");
        }

        if (coleccion.find(Filters.eq("rFC", admin.getRFC())).first() != null) {
            throw new ValidarUsuarioException("El RFC ya esta en uso");
        }
    }
}
