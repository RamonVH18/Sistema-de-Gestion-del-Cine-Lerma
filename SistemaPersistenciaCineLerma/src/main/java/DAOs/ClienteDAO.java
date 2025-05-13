/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Conexion.MongoConexion;
import Excepciones.usuarios.ActualizarClienteException;
import Excepciones.usuarios.CargarHistorialException;
import Excepciones.usuarios.EliminarUsuarioException;
import Excepciones.usuarios.EncontrarClienteException;
import Excepciones.usuarios.ObtenerUsuariosException;
import Excepciones.usuarios.RegistrarClienteException;
import Excepciones.usuarios.ValidarUsuarioException;
import Interfaces.IClienteDAO;
import Interfaces.IUsuarioDAO;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import entidades.Cliente;
import entidades.Compra;
import entidades.Usuario;
import enums.EstadoUsuario;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.conversions.Bson;

/**
 *
 * @author sonic
 */
public class ClienteDAO implements IClienteDAO {

    private static ClienteDAO instance;
    private final MongoConexion conexion = new MongoConexion();
    private final String nombreColeccion = "Usuarios";
    private final IUsuarioDAO usuarioDAO = UsuarioDAO.getInstance();

    //Quizas agregar una proyeccion
    private ClienteDAO() {

    }

    public static ClienteDAO getInstance() {
        if (instance == null) {
            instance = new ClienteDAO();
        }
        return instance;
    }

    @Override
    public Cliente registrarCliente(Cliente cliente) throws RegistrarClienteException {
        MongoClient clienteMongo = null;
        try {
            clienteMongo = conexion.crearConexion();
            MongoDatabase base = conexion.obtenerBaseDatos(clienteMongo);

            MongoCollection<Cliente> coleccion = base.getCollection("usuarios", Cliente.class);

            coleccion.createIndex(
                    Indexes.ascending("nombreDeUsuario"),
                    new IndexOptions().unique(true)
            );

            coleccion.insertOne(cliente);

            return cliente;

        } catch (MongoException e) {
            throw new RegistrarClienteException("El nombre de usuario ya esta en uso: " + e.getMessage());
        } finally {
            if (clienteMongo != null) {
                conexion.cerrarConexion(clienteMongo);
            }
        }
    }

    @Override
    public Cliente actualizarCliente(Cliente cliente) throws ActualizarClienteException {
        MongoClient clienteMongo = null;
        try {
            clienteMongo = conexion.crearConexion();
            MongoDatabase base = conexion.obtenerBaseDatos(clienteMongo);
            MongoCollection<Cliente> coleccion = base.getCollection("usuarios", Cliente.class);

            coleccion.createIndex(
                    Indexes.ascending("nombreDeUsuario"),
                    new IndexOptions().unique(true)
            );

            Bson filtro = Filters.eq("nombreUsuario", cliente.getNombreDeUsuario());
            //puede ser que al filtro se le pudiera agregar que el rol del usuario encontrado coincida con cliente

            Cliente clienteActualizar = coleccion.find(filtro).first();

            if (clienteActualizar == null) {
                throw new ActualizarClienteException("No se encontro el usuario para eliminar");
            }

            UpdateResult result = coleccion.replaceOne(filtro, cliente);

            if (result.getModifiedCount() == 0) {
                throw new ActualizarClienteException("No se modificó ningún documento");
            }

            return cliente;

        } catch (MongoException e) {
            throw new ActualizarClienteException("Error al actualizar el usuario: " + e.getMessage());
        } finally {
            if (clienteMongo != null) {
                conexion.cerrarConexion(clienteMongo);
            }
        }
    }

    @Override
    public Boolean eliminarCliente(Cliente cliente) throws EliminarUsuarioException {
        MongoClient clienteMongo = null;
        try {
            clienteMongo = conexion.crearConexion();

            MongoDatabase base = conexion.obtenerBaseDatos(clienteMongo);

            MongoCollection<Cliente> coleccion = base.getCollection("usuarios", Cliente.class);

            Bson filtro = Filters.eq("nombreUsuario", cliente.getNombreDeUsuario());

            Cliente clienteEliminar = coleccion.find(filtro).first();

            if (clienteEliminar == null) {
                throw new EliminarUsuarioException("No se encontro el cliente para eliminar");
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

//    @Override
//    public Boolean validarCliente(String nombreUsuario, String contrasena) throws ValidarUsuarioException {
//        MongoClient clienteMongo = null;
//        try {
//            clienteMongo = conexion.crearConexion();
//
//            MongoDatabase base = conexion.obtenerBaseDatos(clienteMongo);
//
//            MongoCollection<Cliente> coleccion = base.getCollection("usuarios", Cliente.class);
//
//            Bson filtro = Filters.and(
//                    Filters.eq("nombreDeUsuario", nombreUsuario),
//                    Filters.eq("contrasenia", contrasena),
//                    Filters.eq("rol", "CLIENTE"));
//
//            Cliente usuarioEncontrado = coleccion.find(filtro).first();
//
//            if (usuarioEncontrado == null) {
//                throw new ValidarUsuarioException("El usuario no se encontró o la contraseña es incorrecta");
//            }
//
//            if (usuarioEncontrado.getEstado() == EstadoUsuario.BLOQUEADO) {
//                throw new ValidarUsuarioException("El usuario esta bloqueado");
//            }
//
//            return true;
//
//        } catch (MongoException e) {
//            throw new ValidarUsuarioException("Error al desbloquear el usuario: " + e.getMessage());
//        } finally {
//            if (clienteMongo != null) {
//                conexion.cerrarConexion(clienteMongo);
//            }
//        }
//    }

    @Override
    public Cliente obtenerCliente(String nombreUsuario, String contrasena) throws EncontrarClienteException {
        MongoClient clienteMongo = null;
        try {
            clienteMongo = conexion.crearConexion();
            MongoDatabase base = conexion.obtenerBaseDatos(clienteMongo);

            Bson filtro = Filters.and(
                    Filters.eq("nombreDeUsuario", nombreUsuario),
                    Filters.eq("contrasenia", contrasena),
                    Filters.eq("rol", "CLIENTE"));

            MongoCollection<Cliente> coleccionUsuarios = base.getCollection("usuarios", Cliente.class);

            Cliente clienteEncontrado = coleccionUsuarios.find(filtro).first();

            if (clienteEncontrado == null) {
                throw new EncontrarClienteException("No se encontró el usuario");
            }

            return clienteEncontrado;

        } catch (MongoException e) {
            throw new EncontrarClienteException("Error al obtener el cliente: " + e.getMessage());
        } finally {
            if (clienteMongo != null) {
                conexion.cerrarConexion(clienteMongo);
            }
        }
    }

    @Override
    public List<Compra> cargarHistorialCompras(Cliente cliente) throws CargarHistorialException {
        MongoClient clienteMongo = null;
        try {
            clienteMongo = conexion.crearConexion();
            MongoDatabase base = conexion.obtenerBaseDatos(clienteMongo);

            MongoCollection<Compra> coleccion = base.getCollection("compras", Compra.class);

            Bson filtro = Filters.eq("nombreDeUsuario", cliente.getNombreDeUsuario());

            return coleccion.find(filtro).into(new ArrayList<>());

        } catch (MongoException e) {
            throw new CargarHistorialException("Error al cargar historial de compras: " + e.getMessage());
        } finally {
            if (clienteMongo != null) {
                conexion.cerrarConexion(clienteMongo);
            }
        }
    }
}
