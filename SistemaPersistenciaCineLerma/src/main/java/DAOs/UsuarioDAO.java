/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Conexion.ConexionMejor;
import Conexion.MongoConexion;
import Excepciones.PersistenciaException;
import Interfaces.IUsuarioDAO;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Projections;
import entidades.Administrador;
import entidades.Cliente;
import entidades.Funcion;
import entidades.Usuario;
import enums.EstadoUsuario;
import java.time.LocalDateTime;
import java.util.List;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

/**
 *
 * @author sonic
 */
public class UsuarioDAO implements IUsuarioDAO {

    private static UsuarioDAO instance;
    private final ConexionMejor conexion = new ConexionMejor();
    private final String nombreColeccion = "Usuarios";

    //Quizas agregar una proyeccion
    private UsuarioDAO() {

    }

    public static UsuarioDAO getInstance() {
        if (instance == null) {
            instance = new UsuarioDAO();
        }
        return instance;
    }

    @Override
    public List<Usuario> mostrarListaUsuarios() throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Cliente registrarCliente(Cliente cliente) throws PersistenciaException {
        MongoClient clienteMongo = null;
        try {
            clienteMongo = conexion.crearConexion();

            ObjectId clienteId = new ObjectId();
            Document clienteRegistrar = new Document("_idUsuario", clienteId)
                    .append("nombreUsuario", cliente.getNombreDeUsuario())
                    .append("contraseña", cliente.getContrasenia())
                    .append("nombres", cliente.getNombre())
                    .append("apellidoPaterno", cliente.getApellidoPaterno())
                    .append("apellidoMaterno", cliente.getApellidoMaterno())
                    .append("correoElectronico", cliente.getCorreoElectronico())
                    .append("fechaNacimiento", cliente.getFechaNacimiento())
                    .append("telefono", cliente.getTelefono())
                    .append("rol", cliente.getRol())
                    .append("estado", cliente.getEstado())
                    .append("calle", cliente.getCalle())
                    .append("CP", cliente.getCP())
                    .append("Numero", cliente.getNumero());

            MongoCollection<Document> coleccionUsuarios = clienteMongo.getDatabase("CineLerma").getCollection("usuarios");

            coleccionUsuarios.insertOne(clienteRegistrar);

            return cliente;

        } catch (MongoException e) {
            throw new PersistenciaException("Error al registrar el cliente: " + e.getMessage());
        } finally {
            if (clienteMongo != null) {
                conexion.cerrarConexion(clienteMongo);
            }
        }
    }

    @Override
    public Administrador registrarAdministrador(Administrador administrador) throws PersistenciaException {
        MongoClient clienteMongo = null;
        try {
            clienteMongo = conexion.crearConexion();

            ObjectId adminId = new ObjectId();
            Document clienteRegistrar = new Document("_idUsuario", adminId)
                    .append("nombreUsuario", administrador.getNombreDeUsuario())
                    .append("contraseña", administrador.getContrasenia())
                    .append("nombres", administrador.getNombre())
                    .append("apellidoPaterno", administrador.getApellidoPaterno())
                    .append("apellidoMaterno", administrador.getApellidoMaterno())
                    .append("correoElectronico", administrador.getCorreoElectronico())
                    .append("fechaNacimiento", administrador.getFechaNacimiento())
                    .append("telefono", administrador.getTelefono())
                    .append("rol", administrador.getRol())
                    .append("estado", administrador.getEstado())
                    .append("RFC", administrador.getRFC());

            MongoCollection<Document> coleccionUsuarios = clienteMongo.getDatabase("CineLerma").getCollection("usuarios");

            coleccionUsuarios.insertOne(clienteRegistrar);

            return administrador;

        } catch (MongoException e) {
            throw new PersistenciaException("Error al registrar el administrador: " + e.getMessage());
        } finally {
            if (clienteMongo != null) {
                conexion.cerrarConexion(clienteMongo);
            }
        }
    }

    @Override
    public Usuario actualizarUsuario(Usuario usuario) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Boolean eliminarUsuario(Usuario usuario) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Boolean bloquearUsuario(Usuario usuario) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Funcion> cargarHistorialCompras(Cliente cliente) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Usuario validarUsuario(String nombreUsuario, String contrasena) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Usuario> mostrarListaUsuariosFiltrada(EstadoUsuario estado, LocalDateTime fechaInicio, LocalDateTime fechaFin, String correo, String nombre) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Usuario buscarUsuario(String nombreUsuario) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
