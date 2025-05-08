/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Conexion.ConexionMejor;
import Excepciones.usuarios.EditarUsuarioException;
import Excepciones.usuarios.EliminarUsuarioException;
import Excepciones.usuarios.EncontrarUsuarioException;
import Excepciones.usuarios.ObtenerUsuariosException;
import Excepciones.usuarios.RegistrarUsuarioException;
import Interfaces.IUsuarioDAO;
import UsuariosStrategy.IUsuarioStrategy;
import UsuariosStrategy.StrategyFactory;
import UsuariosStrategy.UsuarioStrategyException;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import entidades.Cliente;
import entidades.Compra;
import entidades.Usuario;
import enums.EstadoUsuario;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;

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
    public List<Usuario> mostrarListaUsuarios() throws ObtenerUsuariosException {
        MongoClient clienteMongo = null;
        List<Usuario> usuarios = new ArrayList<>();

        try {
            clienteMongo = conexion.crearConexion();
            MongoDatabase base = conexion.obtenerBaseDatos(clienteMongo);
            MongoCollection<Usuario> coleccion = base.getCollection("usuarios", Usuario.class);

            MongoCursor<Usuario> cursor = coleccion.find().iterator();
            while (cursor.hasNext()) {
                Usuario usuarioEncontrado = cursor.next();
                usuarios.add(usuarioEncontrado);
            }

            return usuarios;

        } catch (MongoException e) {
            throw new ObtenerUsuariosException("Error al encontrar usuarios " + e.getMessage());
        } finally {
            if (clienteMongo != null) {
                conexion.cerrarConexion(clienteMongo);
            }
        }
    }

    @Override
    public Usuario registrarUsuario(Usuario usuario) throws RegistrarUsuarioException {
        MongoClient clienteMongo = null;
        try {
            clienteMongo = conexion.crearConexion();
            MongoDatabase base = conexion.obtenerBaseDatos(clienteMongo);
            IUsuarioStrategy<Usuario> estrategia = StrategyFactory.get("registrar");

            Usuario usuarioRegistrado = estrategia.ejecutar(base, usuario);

            return usuarioRegistrado;

        } catch (MongoException e) {
            throw new RegistrarUsuarioException("Error al registrar el cliente: " + e.getMessage());
        } catch (UsuarioStrategyException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (clienteMongo != null) {
                conexion.cerrarConexion(clienteMongo);
            }
        }
        return null;
    }

    @Override
    public Usuario actualizarUsuario(Usuario usuario) throws EditarUsuarioException {
        MongoClient clienteMongo = null;
        try {
            clienteMongo = conexion.crearConexion();

            MongoDatabase base = conexion.obtenerBaseDatos(clienteMongo);
            IUsuarioStrategy<Usuario> estrategia = StrategyFactory.get("actualizar");

            Usuario usuarioActualizado = estrategia.ejecutar(base, usuario);

            return usuarioActualizado;

        } catch (MongoException e) {
            throw new EditarUsuarioException("Error al actualizar el usuario: " + e.getMessage());
        } catch (UsuarioStrategyException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (clienteMongo != null) {
                conexion.cerrarConexion(clienteMongo);
            }
        }
        return null;
    }

    @Override
    public Boolean eliminarUsuario(Usuario usuario) throws EliminarUsuarioException {
        MongoClient clienteMongo = null;
        try {
            clienteMongo = conexion.crearConexion();

            MongoDatabase base = conexion.obtenerBaseDatos(clienteMongo);

            MongoCollection<Usuario> coleccion = base.getCollection("usuarios", Usuario.class);

            Bson filtro = Filters.eq("nombreUsuario", usuario.getNombreDeUsuario());

            Usuario usuarioEliminar = coleccion.find(filtro).first();

            if (usuarioEliminar == null) {
                throw new EliminarUsuarioException("No se encontro el usuario para eliminar");
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
    public Boolean bloquearUsuario(Usuario usuario) throws EditarUsuarioException {
        MongoClient clienteMongo = null;
        try {
            clienteMongo = conexion.crearConexion();

            MongoDatabase base = conexion.obtenerBaseDatos(clienteMongo);

            MongoCollection<Usuario> coleccion = base.getCollection("usuarios", Usuario.class);

            Bson filtro = Filters.eq("nombreUsuario", usuario.getNombreDeUsuario());

            Usuario usuarioBloquear = coleccion.find(filtro).first();

            if (usuarioBloquear == null) {
                throw new EditarUsuarioException("No se encontro el usuario para bloquearlo");
            }

            Bson bloqueo = Updates.set("estado", EstadoUsuario.BLOQUEADO.toString());

            UpdateResult resultado = coleccion.updateOne(filtro, bloqueo);

            if (resultado.getModifiedCount() == 0) {
                throw new EditarUsuarioException("No se bloqueo al usuario");
            }

            return true;

        } catch (MongoException e) {
            throw new EditarUsuarioException("Error al bloquear el usuario: " + e.getMessage());
        } finally {
            if (clienteMongo != null) {
                conexion.cerrarConexion(clienteMongo);
            }
        }
    }

    @Override
    public Boolean desbloquearUsuario(Usuario usuario) throws EditarUsuarioException {
        MongoClient clienteMongo = null;
        try {
            clienteMongo = conexion.crearConexion();

            MongoDatabase base = conexion.obtenerBaseDatos(clienteMongo);

            MongoCollection<Usuario> coleccion = base.getCollection("usuarios", Usuario.class);

            Bson filtro = Filters.eq("nombreUsuario", usuario.getNombreDeUsuario());

            Usuario usuarioBloquear = coleccion.find(filtro).first();

            if (usuarioBloquear == null) {
                throw new EditarUsuarioException("No se encontro el usuario para bloquearlo");
            }

            Bson bloqueo = Updates.set("estado", EstadoUsuario.ACTIVO.toString());

            UpdateResult resultado = coleccion.updateOne(filtro, bloqueo);

            if (resultado.getModifiedCount() == 0) {
                throw new EditarUsuarioException("No se bloqueo al usuario");
            }

            return true;

        } catch (MongoException e) {
            throw new EditarUsuarioException("Error al bloquear el usuario: " + e.getMessage());
        } finally {
            if (clienteMongo != null) {
                conexion.cerrarConexion(clienteMongo);
            }
        }
    }

    @Override
    public List<Compra> cargarHistorialCompras(String nombreUsuario) throws EncontrarUsuarioException {
        MongoClient clienteMongo = null;
        try {
            clienteMongo = conexion.crearConexion();
            MongoDatabase base = conexion.obtenerBaseDatos(clienteMongo);

            MongoCollection<Compra> coleccion = base.getCollection("compras", Compra.class);

            Bson filtro = Filters.eq("nombreDeUsuario", nombreUsuario);


            return coleccion.find(filtro).into(new ArrayList<>());

        } catch (MongoException e) {
            throw new EncontrarUsuarioException("Error al cargar historial de compras: " + e.getMessage());
        } finally {
            if (clienteMongo != null) {
                conexion.cerrarConexion(clienteMongo);
            }
        }
    }

    @Override
    public Boolean validarUsuario(String nombreUsuario, String contrasena) throws EncontrarUsuarioException {
        MongoClient clienteMongo = null;
        try {
            clienteMongo = conexion.crearConexion();

            MongoDatabase base = conexion.obtenerBaseDatos(clienteMongo);

            MongoCollection<Usuario> coleccion = base.getCollection("usuarios", Usuario.class);

            Bson filtro = Filters.and(
                    Filters.eq("nombreUsuario", nombreUsuario),
                    Filters.eq("contrasena", contrasena));

            Usuario usuarioEncontrado = coleccion.find(filtro).first();

            if (usuarioEncontrado == null && usuarioEncontrado.getEstado() != EstadoUsuario.BLOQUEADO) {
                throw new EncontrarUsuarioException("El usuario no se encontro");
            }

            if (usuarioEncontrado.getEstado() == EstadoUsuario.BLOQUEADO) {
                throw new EncontrarUsuarioException("El usuario esta bloqueado");
            }

            return true;

        } catch (MongoException e) {
            throw new EncontrarUsuarioException("Error al desbloquear el usuario: " + e.getMessage());
        } finally {
            if (clienteMongo != null) {
                conexion.cerrarConexion(clienteMongo);
            }
        }
    }

    @Override
    public List<Usuario> mostrarListaUsuariosFiltrada(EstadoUsuario estado, LocalDateTime fechaInicio, LocalDateTime fechaFin, String correo, String nombre) throws ObtenerUsuariosException {
        MongoClient clienteMongo = null;
        List<Usuario> usuarios = new ArrayList<>();

        try {
            clienteMongo = conexion.crearConexion();

            MongoDatabase base = conexion.obtenerBaseDatos(clienteMongo);

            MongoCollection<Usuario> coleccion = base.getCollection("usuarios", Usuario.class);

            List<Bson> filtros = new ArrayList<>();

            filtroEstado(estado, filtros);
            
            // Filtro por fechas
            if (fechaInicio != null && fechaFin != null) {

                Date fecha1 = Date.from(fechaInicio.atZone(ZoneId.systemDefault()).toInstant());
                Date fecha2 = Date.from(fechaFin.atZone(ZoneId.systemDefault()).toInstant());

                filtros.add(Filters.and(
                        Filters.gte("fechaNacimiento", fecha1),
                        Filters.lte("fechaNacimiento", fecha2)
                ));
            }

            // Filtro por correo
            if (correo != null && !correo.isEmpty()) {
                filtros.add(Filters.regex("correoElectronico", correo, "i"));
            }

            // Filtro por nombre 
            if (nombre != null && !nombre.isEmpty()) {
                filtros.add(Filters.regex("nombres", nombre, "i"));
            }

            // Construir consulta final
            Bson filtro = filtros.isEmpty() ? new Document() : Filters.and(filtros);

            MongoCursor<Usuario> cursor = coleccion.find(filtro).iterator();
            while (cursor.hasNext()) {
                Usuario usuarioEncontrado = cursor.next();
                usuarios.add(usuarioEncontrado);
            }

            return usuarios;

        } catch (MongoException e) {
            throw new ObtenerUsuariosException("Error al registrar el cliente: " + e.getMessage());
        } finally {
            if (clienteMongo != null) {
                conexion.cerrarConexion(clienteMongo);
            }
        }
    }
    
    private void filtroEstado(EstadoUsuario estado, List<Bson> filtros){
        //filtrar por estado
            if (estado != null) {
                filtros.add(Filters.eq("estado", estado.toString()));
            }
    }

    @Override
    public Usuario obtenerUsuario(String nombreUsuario) throws EncontrarUsuarioException {
        MongoClient clienteMongo = null;
        try {
            clienteMongo = conexion.crearConexion();
            MongoDatabase base = conexion.obtenerBaseDatos(clienteMongo);

            Bson filtro = Filters.eq("nombreDeUsuario", nombreUsuario);
            MongoCollection<Usuario> coleccionUsuarios = base.getCollection("usuarios", Usuario.class);

            Usuario usuario = coleccionUsuarios.find(filtro).first();

            if (usuario == null) {
                throw new EncontrarUsuarioException("No se encontró el usuario");
            }

            return usuario;

        } catch (MongoException e) {
            throw new EncontrarUsuarioException("Error al obtener el usuario: " + e.getMessage());
        } finally {
            if (clienteMongo != null) {
                conexion.cerrarConexion(clienteMongo);
            }
        }
    }

}
