/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Conexion.MongoConexion;
import Excepciones.usuarios.ActualizarAdministradorException;
import Excepciones.usuarios.ActualizarClienteException;
import Excepciones.usuarios.EliminarUsuarioException;
import Excepciones.usuarios.EncontrarAdministradorException;
import Excepciones.usuarios.RegistrarAministradorException;
import Excepciones.usuarios.RegistrarClienteException;
import Excepciones.usuarios.ValidarUsuarioException;
import Interfaces.IAdministradorDAO;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import entidades.Administrador;
import entidades.Cliente;
import enums.EstadoUsuario;
import org.bson.conversions.Bson;

/**
 *
 * @author sonic
 */
public class AdministradorDAO implements IAdministradorDAO {

    private static AdministradorDAO instance;
    private final MongoConexion conexion = new MongoConexion();
    private final String nombreColeccion = "Usuarios";

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

            coleccion.createIndex(
                    Indexes.ascending("nombreDeUsuario"),
                    new IndexOptions().unique(true)
            );

            coleccion.insertOne(administrador);

            return administrador;

        } catch (MongoException e) {
            throw new RegistrarAministradorException("Error al registrar el cliente: " + e.getMessage());
        } finally {
            if (clienteMongo != null) {
                conexion.cerrarConexion(clienteMongo);
            }
        }
    }

    @Override
    public Administrador actualizarAdministrador(Administrador administrador) throws ActualizarAdministradorException {
        MongoClient clienteMongo = null;
        try {
            clienteMongo = conexion.crearConexion();
            MongoDatabase base = conexion.obtenerBaseDatos(clienteMongo);
            MongoCollection<Administrador> coleccion = base.getCollection("usuarios", Administrador.class);

            coleccion.createIndex(
                    Indexes.ascending("nombreDeUsuario"),
                    new IndexOptions().unique(true)
            );

            Bson filtro = Filters.eq("nombreUsuario", administrador.getNombreDeUsuario());
            //puede ser que al filtro se le pudiera agregar que el rol del usuario encontrado coincida con cliente

            Administrador adminActualizar = coleccion.find(filtro).first();

            if (adminActualizar == null) {
                throw new ActualizarAdministradorException("No se encontro el admin para actualizar");
            }

            UpdateResult result = coleccion.replaceOne(filtro, administrador);

            if (result.getModifiedCount() == 0) {
                throw new ActualizarAdministradorException("No se modificó ningún documento");
            }

            return administrador;

        } catch (MongoException e) {
            throw new ActualizarAdministradorException("Error al actualizar el usuario: " + e.getMessage());
        } finally {
            if (clienteMongo != null) {
                conexion.cerrarConexion(clienteMongo);
            }
        }
    }

    @Override
    public Boolean eliminarAdministrador(Administrador administrador) throws EliminarUsuarioException {
        MongoClient clienteMongo = null;
        try {
            clienteMongo = conexion.crearConexion();

            MongoDatabase base = conexion.obtenerBaseDatos(clienteMongo);

            MongoCollection<Administrador> coleccion = base.getCollection("usuarios", Administrador.class);

            Bson filtro = Filters.eq("nombreUsuario", administrador.getNombreDeUsuario());

            Administrador adminEliminar = coleccion.find(filtro).first();

            if (adminEliminar == null) {
                throw new EliminarUsuarioException("No se encontro el admin para eliminar");
            }

            DeleteResult eliminar = coleccion.deleteOne(filtro);

            if (eliminar.getDeletedCount() == 0) {
                throw new EliminarUsuarioException("No se elimino el usuario");
            }

            return true;

        } catch (MongoException e) {
            throw new EliminarUsuarioException("Error al actualizar el usuario: " + e.getMessage());
        } finally {
            if (clienteMongo != null) {
                conexion.cerrarConexion(clienteMongo);
            }
        }
    }

    @Override
    public Boolean validarAdministrador(String nombreUsuario, String contrasena) throws ValidarUsuarioException {
        MongoClient clienteMongo = null;
        try {
            clienteMongo = conexion.crearConexion();

            MongoDatabase base = conexion.obtenerBaseDatos(clienteMongo);

            MongoCollection<Administrador> coleccion = base.getCollection("usuarios", Administrador.class);

            Bson filtro = Filters.and(
                    Filters.eq("nombreDeUsuario", nombreUsuario),
                    Filters.eq("contrasenia", contrasena),
                    Filters.eq("rol", "ADMINISTRADOR"));

            Administrador adminEncontrado = coleccion.find(filtro).first();
            
            System.out.println(adminEncontrado);

            if (adminEncontrado == null) {
                throw new ValidarUsuarioException("El usuario no se encontró");
            }

            if (adminEncontrado.getEstado() == EstadoUsuario.BLOQUEADO) {
                throw new ValidarUsuarioException("El usuario esta bloqueado");
            }

            return true;

        } catch (MongoException e) {
            throw new ValidarUsuarioException("Error al validar y encontrar el usuario: " + e.getMessage());
        } finally {
            if (clienteMongo != null) {
                conexion.cerrarConexion(clienteMongo);
            }
        }
    }

    @Override
    public Administrador obtenerAdministrador(String nombreUsuario) throws EncontrarAdministradorException {
        MongoClient clienteMongo = null;
        try {
            clienteMongo = conexion.crearConexion();
            MongoDatabase base = conexion.obtenerBaseDatos(clienteMongo);

            Bson filtro = Filters.eq("nombreDeUsuario", nombreUsuario);
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
}
