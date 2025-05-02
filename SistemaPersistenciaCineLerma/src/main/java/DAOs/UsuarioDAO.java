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
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import entidades.Administrador;
import entidades.Cliente;
import entidades.Funcion;
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
        MongoClient clienteMongo = null;
        List<Usuario> usuarios = new ArrayList<>();

        try {
            clienteMongo = conexion.crearConexion();
            MongoCollection<Document> coleccionUsuarios = clienteMongo.getDatabase("CineLerma").getCollection("usuarios");

            MongoCursor<Document> cursor = coleccionUsuarios.find().iterator();
            while (cursor.hasNext()) {
                Document documentUsuario = cursor.next();

                Usuario usuario = documentoAUsuario(documentUsuario);
                usuarios.add(usuario);
            }

            return usuarios;

        } catch (MongoException e) {
            throw new PersistenciaException("Error al registrar el cliente: " + e.getMessage());
        } finally {
            if (clienteMongo != null) {
                conexion.cerrarConexion(clienteMongo);
            }
        }
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
                    .append("rol", cliente.getRol().toString())
                    .append("estado", cliente.getEstado().toString())
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
                    .append("rol", administrador.getRol().toString())
                    .append("estado", administrador.getEstado().toString())
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
        MongoClient clienteMongo = null;
        try {
            clienteMongo = conexion.crearConexion();

            Bson filtro = Filters.eq("nombreUsuario", usuario.getNombreDeUsuario());
            MongoCollection<Document> coleccionUsuarios = clienteMongo.getDatabase("CineLerma").getCollection("usuarios");

            Document documentActualizar = coleccionUsuarios.find(filtro).first();

            if (documentActualizar == null) {
                throw new PersistenciaException("No se encontro el usuario para eliminar");
            }

            Document actualizacion = new Document()
                    .append("nombreUsuario", usuario.getNombreDeUsuario())
                    .append("contraseña", usuario.getContrasenia())
                    .append("nombres", usuario.getNombre())
                    .append("apellidoPaterno", usuario.getApellidoPaterno())
                    .append("apellidoMaterno", usuario.getApellidoMaterno())
                    .append("correoElectronico", usuario.getCorreoElectronico())
                    .append("fechaNacimiento", usuario.getFechaNacimiento())
                    .append("telefono", usuario.getTelefono())
                    .append("rol", usuario.getRol().toString())
                    .append("estado", usuario.getEstado().toString());

            if (usuario instanceof Administrador) {
                actualizacion.append("RFC", ((Administrador) usuario).getRFC());
            }

            if (usuario instanceof Cliente) {
                actualizacion.append("Calle", ((Cliente) usuario).getCalle());
                actualizacion.append("CP", ((Cliente) usuario).getCP());
                actualizacion.append("Numero", ((Cliente) usuario).getNumero());
            }

            Bson actualizar = new Document("$set", actualizacion);

            UpdateResult resultado = coleccionUsuarios.updateOne(filtro, actualizar);

            if (resultado.getModifiedCount() == 0) {
                throw new PersistenciaException("No se encontro el usuario para actualizar");
            }

            return usuario;

        } catch (MongoException e) {
            throw new PersistenciaException("Error al actualizar el usuario: " + e.getMessage());
        } finally {
            if (clienteMongo != null) {
                conexion.cerrarConexion(clienteMongo);
            }
        }
    }

    @Override
    public Boolean eliminarUsuario(Usuario usuario) throws PersistenciaException {
        MongoClient clienteMongo = null;
        try {
            clienteMongo = conexion.crearConexion();

            Bson filtro = Filters.eq("nombreUsuario", usuario.getNombreDeUsuario());
            MongoCollection<Document> coleccionUsuarios = clienteMongo.getDatabase("CineLerma").getCollection("usuarios");

            Document documentEliminar = coleccionUsuarios.find(filtro).first();

            if (documentEliminar == null) {
                throw new PersistenciaException("No se encontro el usuario para eliminar");
            }

            DeleteResult eliminar = coleccionUsuarios.deleteOne(filtro);

            if (eliminar.getDeletedCount() == 0) {
                throw new PersistenciaException("No se elimino el usuario");
            }

            return true;

        } catch (MongoException e) {
            throw new PersistenciaException("Error al actualizar el usuario: " + e.getMessage());
        } finally {
            if (clienteMongo != null) {
                conexion.cerrarConexion(clienteMongo);
            }
        }

    }

    @Override
    public Boolean bloquearUsuario(Usuario usuario) throws PersistenciaException {
        MongoClient clienteMongo = null;
        try {
            clienteMongo = conexion.crearConexion();

            Bson filtro = Filters.eq("nombreUsuario", usuario.getNombreDeUsuario());
            MongoCollection<Document> coleccionUsuarios = clienteMongo.getDatabase("CineLerma").getCollection("usuarios");

            Document documentBloquear = coleccionUsuarios.find(filtro).first();

            if (documentBloquear == null) {
                throw new PersistenciaException("No se encontro el usuario para bloquearlo");
            }

            Bson bloqueo = Updates.set("estado", EstadoUsuario.BLOQUEADO.toString());

            UpdateResult resultado = coleccionUsuarios.updateOne(filtro, bloqueo);

            if (resultado.getModifiedCount() == 0) {
                throw new PersistenciaException("No se encontro el usuario para bloquearlo");
            }

            return true;

        } catch (MongoException e) {
            throw new PersistenciaException("Error al bloquear el usuario: " + e.getMessage());
        } finally {
            if (clienteMongo != null) {
                conexion.cerrarConexion(clienteMongo);
            }
        }
    }

    @Override
    public Boolean desbloquearUsuario(Usuario usuario) throws PersistenciaException {
        MongoClient clienteMongo = null;
        try {
            clienteMongo = conexion.crearConexion();

            Bson filtro = Filters.eq("nombreUsuario", usuario.getNombreDeUsuario());
            MongoCollection<Document> coleccionUsuarios = clienteMongo.getDatabase("CineLerma").getCollection("usuarios");

            Document documentDesbloquear = coleccionUsuarios.find(filtro).first();

            if (documentDesbloquear == null) {
                throw new PersistenciaException("No se encontro el usuario para desbloquearlo");
            }

            Bson desbloqueo = Updates.set("estado", EstadoUsuario.ACTIVO.toString());

            UpdateResult resultado = coleccionUsuarios.updateOne(filtro, desbloqueo);

            if (resultado.getModifiedCount() == 0) {
                throw new PersistenciaException("No se encontro el usuario para desbloquearlo");
            }

            return true;

        } catch (MongoException e) {
            throw new PersistenciaException("Error al desbloquear el usuario: " + e.getMessage());
        } finally {
            if (clienteMongo != null) {
                conexion.cerrarConexion(clienteMongo);
            }
        }
    }

    @Override
    public List<Funcion> cargarHistorialCompras(Cliente cliente) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Boolean validarUsuario(String nombreUsuario, String contrasena) throws PersistenciaException {
        MongoClient clienteMongo = null;
        try {
            clienteMongo = conexion.crearConexion();

            Bson filtro = Filters.and(
                    Filters.eq("nombreUsuario", nombreUsuario),
                    Filters.eq("contrasena", contrasena));

            MongoCollection<Document> coleccionUsuarios = clienteMongo.getDatabase("CineLerma").getCollection("usuarios");

            Document usuarioEncontrado = coleccionUsuarios.find(filtro).first();

            if (usuarioEncontrado == null && usuarioEncontrado.getString("estado") != EstadoUsuario.BLOQUEADO.toString()) {
                throw new PersistenciaException("El usuario no se encontro");
            }

            if (usuarioEncontrado.getString("estado") == EstadoUsuario.BLOQUEADO.toString()) {
                throw new PersistenciaException("El usuario esta bloqueado");
            }

            return true;

        } catch (MongoException e) {
            throw new PersistenciaException("Error al desbloquear el usuario: " + e.getMessage());
        } finally {
            if (clienteMongo != null) {
                conexion.cerrarConexion(clienteMongo);
            }
        }
    }

    @Override
    public List<Usuario> mostrarListaUsuariosFiltrada(EstadoUsuario estado, LocalDateTime fechaInicio, LocalDateTime fechaFin, String correo, String nombre) throws PersistenciaException {
        MongoClient clienteMongo = null;
        List<Usuario> usuarios = new ArrayList<>();

        try {
            clienteMongo = conexion.crearConexion();
            MongoCollection<Document> coleccionUsuarios = clienteMongo.getDatabase("CineLerma").getCollection("usuarios");

            List<Bson> filtros = new ArrayList<>();

            //filtrar por estado
            if (estado != null) {
                filtros.add(Filters.eq("estado", estado.toString()));
            }

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
            

            MongoCursor<Document> cursor = coleccionUsuarios.find(filtro).iterator();
            while (cursor.hasNext()) {
                Document documentUsuario = cursor.next();

                Usuario usuario = documentoAUsuario(documentUsuario);
                usuarios.add(usuario);
            }

            return usuarios;

        } catch (MongoException e) {
            throw new PersistenciaException("Error al registrar el cliente: " + e.getMessage());
        } finally {
            if (clienteMongo != null) {
                conexion.cerrarConexion(clienteMongo);
            }
        }
    }

    @Override
    public Usuario obtenerUsuario(String nombreUsuario) throws PersistenciaException {
        MongoClient clienteMongo = null;
        try {
            clienteMongo = conexion.crearConexion();

            Bson filtro = Filters.eq("nombreUsuario", nombreUsuario);
            MongoCollection<Document> coleccionUsuarios = clienteMongo.getDatabase("CineLerma").getCollection("usuarios");

            Document documentUsuario = coleccionUsuarios.find(filtro).first();

            if (documentUsuario == null) {
                throw new PersistenciaException("No se encontro el usuario");
            }

            return documentoAUsuario(documentUsuario);

        } catch (MongoException e) {
            throw new PersistenciaException("Error al obtener el usuario: " + e.getMessage());
        } finally {
            if (clienteMongo != null) {
                conexion.cerrarConexion(clienteMongo);
            }
        }
    }

    private Usuario documentoAUsuario(Document documentUsuario) {
        Usuario usuario = new Usuario();

        usuario.setIdUsuario(documentUsuario.getObjectId("_id").toString());
        usuario.setNombreDeUsuario(documentUsuario.getString("nombreUsuario"));
        usuario.setContrasenia(documentUsuario.getString("contrasenia"));
        usuario.setCorreoElectronico(documentUsuario.getString("correoElectronico"));
        usuario.setNombre(documentUsuario.getString("nombres"));
        usuario.setApellidoMaterno(documentUsuario.getString("apellidoPaterno"));
        usuario.setApellidoMaterno(documentUsuario.getString("apellidoMaterno"));
        usuario.setFechaNacimiento((LocalDateTime) documentUsuario.get("fechaNacimiento"));

        Rol rol = Rol.valueOf(documentUsuario.getString("rol"));
        usuario.setRol(rol);

        if (rol == Rol.CLIENTE) {
            Cliente cliente = new Cliente();
            cliente.setCalle(documentUsuario.getString("calle"));
            cliente.setCP(documentUsuario.getString("CP"));
            cliente.setNumero(documentUsuario.getString("numero"));
            usuario = cliente;
        } else if (rol == Rol.ADMINISTRADOR) {
            Administrador admin = new Administrador();
            admin.setRFC(documentUsuario.getString("RFC"));
            usuario = admin;
        }

        EstadoUsuario estado = EstadoUsuario.valueOf(documentUsuario.getString("estado"));
        usuario.setEstado(estado);

        usuario.setTelefono(documentUsuario.getString("telefono"));

        return usuario;
    }

}
