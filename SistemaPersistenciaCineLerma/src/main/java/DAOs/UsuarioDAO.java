/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;


import com.mongodb.client.MongoClient;
import Conexion.MongoConexion;
import Excepciones.usuarios.ActualizarUsuarioException;
import Excepciones.usuarios.EliminarUsuarioException;
import Excepciones.usuarios.ObtenerUsuariosException;
import Interfaces.IUsuarioDAO;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
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
    private final MongoConexion conexion = new MongoConexion();
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

//    @Override
//    public Boolean eliminarUsuario(Usuario usuario) throws EliminarUsuarioException {
//        MongoClient clienteMongo = null;
//        try {
//            clienteMongo = conexion.crearConexion();
//
//            MongoDatabase base = conexion.obtenerBaseDatos(clienteMongo);
//
//            MongoCollection<Usuario> coleccion = base.getCollection("usuarios", Usuario.class);
//
//            Bson filtro = Filters.eq("nombreUsuario", usuario.getNombreDeUsuario());
//
//            Usuario usuarioEliminar = coleccion.find(filtro).first();
//
//            if (usuarioEliminar == null) {
//                throw new EliminarUsuarioException("No se encontro el usuario para eliminar");
//            }
//
//            DeleteResult eliminar = coleccion.deleteOne(filtro);
//
//            if (eliminar.getDeletedCount() == 0) {
//                throw new EliminarUsuarioException("No se elimino el usuario");
//            }
//
//            return true;
//
//        } catch (MongoException e) {
//            throw new EliminarUsuarioException("Error al actualizar el usuario: " + e.getMessage());
//        } finally {
//            if (clienteMongo != null) {
//                conexion.cerrarConexion(clienteMongo);
//            }
//        }
//
//    }

    @Override
    public Boolean bloquearUsuario(Usuario usuario) throws ActualizarUsuarioException {
        MongoClient clienteMongo = null;
        try {
            clienteMongo = conexion.crearConexion();

            MongoDatabase base = conexion.obtenerBaseDatos(clienteMongo);

            MongoCollection<Usuario> coleccion = base.getCollection("usuarios", Usuario.class);

            Bson filtro = Filters.eq("nombreDeUsuario", usuario.getNombreDeUsuario());

            Usuario usuarioBloquear = coleccion.find(filtro).first();

            if (usuarioBloquear == null) {
                throw new ActualizarUsuarioException("No se encontro el usuario para bloquearlo");
            }

            Bson bloqueo = Updates.set("estado", EstadoUsuario.BLOQUEADO.toString());

            UpdateResult resultado = coleccion.updateOne(filtro, bloqueo);

            if (resultado.getModifiedCount() == 0) {
                throw new ActualizarUsuarioException("No se bloqueo al usuario");
            }

            return true;

        } catch (MongoException e) {
            throw new ActualizarUsuarioException ("Error al bloquear el usuario: " + e.getMessage());
        } finally {
            if (clienteMongo != null) {
                conexion.cerrarConexion(clienteMongo);
            }
        }
    }

    @Override
    public Boolean desbloquearUsuario(Usuario usuario) throws ActualizarUsuarioException {
        MongoClient clienteMongo = null;
        try {
            clienteMongo = conexion.crearConexion();

            MongoDatabase base = conexion.obtenerBaseDatos(clienteMongo);

            MongoCollection<Usuario> coleccion = base.getCollection("usuarios", Usuario.class);

            Bson filtro = Filters.eq("nombreDeUsuario", usuario.getNombreDeUsuario());

            Usuario usuarioDesbloquear = coleccion.find(filtro).first();

            if (usuarioDesbloquear == null) {
                throw new ActualizarUsuarioException("No se encontro el usuario para desbloquearlo");
            }

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

    @Override
    public List<Usuario> mostrarListaUsuariosFiltrada(EstadoUsuario estado, Rol rol, LocalDateTime fechaInicio, LocalDateTime fechaFin, String nombre) throws ObtenerUsuariosException {
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


            // Filtro por nombre de usuario
            if (nombre != null && !nombre.isEmpty()) {
                filtros.add(Filters.regex("nombreDeUsuario", nombre, "i"));
            }
            
            if (estado != null) {
                filtros.add(Filters.eq("estado", estado.toString()));
            }
            
            if (rol != null) {
                filtros.add(Filters.eq("rol", rol.toString()));
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


}
