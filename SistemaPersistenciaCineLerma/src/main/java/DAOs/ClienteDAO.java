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
import com.mongodb.MongoWriteException;
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

            MongoCollection<Cliente> coleccion = base.getCollection(nombreColeccion, Cliente.class);

            coleccion.insertOne(cliente);

            validarRegistro(coleccion, cliente);

            return cliente;

        } catch (MongoException e) {
            throw new RegistrarClienteException(e.getMessage());
        } catch (ValidarUsuarioException e) {
            throw new RegistrarClienteException(e.getMessage());
        } finally {
            if (clienteMongo != null) {
                conexion.cerrarConexion(clienteMongo);
            }
        }

    }

    @Override
    public Cliente actualizarCliente(Cliente clienteModificado) throws ActualizarClienteException {
        MongoClient clienteMongo = null;
        try {
            clienteMongo = conexion.crearConexion();
            MongoDatabase base = conexion.obtenerBaseDatos(clienteMongo);
            MongoCollection<Cliente> coleccion = base.getCollection(nombreColeccion, Cliente.class);

            Bson filtro = Filters.eq("nombreDeUsuario", clienteModificado.getNombreDeUsuario());
            Cliente original = coleccion.find(filtro).first();
            if (original == null) {
                throw new ActualizarClienteException("No se encontró el cliente para actualizar");
            }

            validarActualizacion(coleccion, original, clienteModificado);

            // 3) Reemplazar todo el documento
            UpdateResult result = coleccion.replaceOne(filtro, clienteModificado);
            if (result.getModifiedCount() == 0) {
                throw new ActualizarClienteException("No se modificó ningún documento");
            }

            return clienteModificado;
        } catch (ValidarUsuarioException e) {
            throw new ActualizarClienteException(e.getMessage(), e);
        } catch (MongoException e) {
            throw new ActualizarClienteException(e.getMessage(), e);
        } finally {
            if (clienteMongo != null) {
                conexion.cerrarConexion(clienteMongo);
            }
        }
    }

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

            MongoCollection<Cliente> coleccionUsuarios = base.getCollection(nombreColeccion, Cliente.class);

            Cliente clienteEncontrado = coleccionUsuarios.find(filtro).first();

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

            MongoCollection<Compra> coleccion = base.getCollection("Compras", Compra.class);

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

    public void validarRegistro(MongoCollection coleccion, Cliente cliente) throws ValidarUsuarioException {
        if (coleccion.find(Filters.eq("nombreDeUsuario", cliente.getNombreDeUsuario())).first() != null) {
            throw new ValidarUsuarioException("El nombre de usuario ya está en uso");
        }
        if (coleccion.find(Filters.eq("correoElectronico", cliente.getCorreoElectronico())).first() != null) {
            throw new ValidarUsuarioException("El correo electrónico ya está en uso");
        }
        if (coleccion.find(Filters.eq("telefono", cliente.getTelefono())).first() != null) {
            throw new ValidarUsuarioException("El número de teléfono ya está en uso");
        }
    }

    private void validarActualizacion(MongoCollection<Cliente> coleccion, Cliente original, Cliente modificado) throws ValidarUsuarioException {

        if (!modificado.getNombreDeUsuario().equals(original.getNombreDeUsuario())) {
            if (coleccion.find(Filters.and(Filters.eq("nombreDeUsuario", modificado.getNombreDeUsuario()), Filters.ne("_id", original.getIdUsuario()))).first() != null) {
                throw new ValidarUsuarioException("El nombre de usuario ya está en uso");
            }
        }

        if (!modificado.getCorreoElectronico().equals(original.getCorreoElectronico())) {
            if (coleccion.find(Filters.and(Filters.eq("correoElectronico", modificado.getCorreoElectronico()), Filters.ne("_id", original.getIdUsuario()))).first() != null) {
                throw new ValidarUsuarioException("El correo electrónico ya está en uso");
            }
        }

        if (!modificado.getTelefono().equals(original.getTelefono())) {
            if (coleccion.find(Filters.and(Filters.eq("telefono", modificado.getTelefono()), Filters.ne("_id", original.getIdUsuario()))).first() != null) {
                throw new ValidarUsuarioException("El número de teléfono ya está en uso");
            }
        }
    }
}
