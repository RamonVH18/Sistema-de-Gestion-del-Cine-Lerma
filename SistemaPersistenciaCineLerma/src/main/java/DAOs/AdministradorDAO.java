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

    //Quizas agregar una proyeccion
    private AdministradorDAO() {

    }

    public static AdministradorDAO getInstance() {
        if (instance == null) {
            instance = new AdministradorDAO();
        }
        return instance;
    }

    @Override
    public Administrador registrarAdministrador(Administrador administrador) throws RegistrarAministradorException {
        MongoClient clienteMongo = null;
        try {
            clienteMongo = conexion.crearConexion();
            MongoDatabase base = conexion.obtenerBaseDatos(clienteMongo);

            MongoCollection<Administrador> coleccion = base.getCollection("usuarios", Administrador.class);

            validarUnicidad(coleccion, administrador);
            
            coleccion.insertOne(administrador);

            return administrador;

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

    @Override
    public Administrador obtenerAdministrador(String nombreUsuario, String contrasena) throws EncontrarAdministradorException {
        MongoClient clienteMongo = null;
        try {
            clienteMongo = conexion.crearConexion();
            MongoDatabase base = conexion.obtenerBaseDatos(clienteMongo);

            Bson filtro = Filters.and(
                    Filters.eq("nombreDeUsuario", nombreUsuario),
                    Filters.eq("contrasenia", contrasena),
                    Filters.eq("rol", "ADMINISTRADOR"));
            
            MongoCollection<Administrador> coleccion = base.getCollection("usuarios", Administrador.class);

            Administrador adminEncontrado = coleccion.find(filtro).first();

            if (adminEncontrado == null) {
                throw new EncontrarAdministradorException("No se encontró el admin");
            }

            return adminEncontrado;

        } catch (MongoException e) {
            throw new EncontrarAdministradorException("Error al obtener el usuario: " + e.getMessage());
        } finally {
            if (clienteMongo != null) {
                conexion.cerrarConexion(clienteMongo);
            }
        }
    }
    
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
