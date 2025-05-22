/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Conexion.MongoConexion;
import Excepciones.PersistenciaException;
import Excepciones.empleados.DAOActualizarEmpleadoException;
import Excepciones.empleados.DAODespedirEmpleadoException;
import Excepciones.empleados.DAOObtenerEmpleadoException;
import Excepciones.empleados.DAORegistrarEmpleadoException;
import Excepciones.empleados.DAOValidacionEmpleadoException;
import Interfaces.IEmpleadoDAO;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import entidades.Empleado;
import enums.Cargo;
import java.util.ArrayList;
import java.util.List;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

/**
 *
 * @author isaac
 */
public class EmpleadoDAO implements IEmpleadoDAO {

    private static EmpleadoDAO instance;
    private final MongoConexion mongoConexion;

    private EmpleadoDAO() {
        this.mongoConexion = new MongoConexion();
    }

    public static synchronized EmpleadoDAO getInstance() {
        if (instance == null) {
            instance = new EmpleadoDAO();
        }
        return instance;
    }

    // Método de utilidad privado para convertir y validar String a ObjectId
    private ObjectId convertirAObjectId(String idString, String contextoError) throws DAOValidacionEmpleadoException {
        if (idString == null || idString.trim().isEmpty()) {
            throw new DAOValidacionEmpleadoException("El ID (" + contextoError + ") no puede ser nulo o vacío.");
        }
        if (!ObjectId.isValid(idString)) {
            throw new DAOValidacionEmpleadoException("Formato de ID (" + contextoError + ") inválido: " + idString);
        }
        return new ObjectId(idString);
    }

    @Override
    public boolean registrarEmpleado(Empleado empleado) throws DAORegistrarEmpleadoException {
        MongoClient clienteMongo = null;
        try {
            clienteMongo = mongoConexion.crearConexion();
            MongoDatabase database = mongoConexion.obtenerBaseDatos(clienteMongo);
            MongoCollection<Empleado> coleccion = database.getCollection("Empleados", Empleado.class);

            if (empleado.getId() == null) { // El ObjectId de la entidad
                empleado.setId(new ObjectId());
            }

            coleccion.insertOne(empleado);
            return true;
        } catch (MongoException e) {

            throw new DAORegistrarEmpleadoException("Error al registrar empleado: " + e.getMessage(), e);
        } finally {
            if (clienteMongo != null) {
                mongoConexion.cerrarConexion(clienteMongo);
            }
        }
    }

    @Override
    public List<Empleado> obtenerTodosLosEmpleadosActivos() throws DAOObtenerEmpleadoException {
        MongoClient clienteMongo = null;
        List<Empleado> empleados = new ArrayList<>();
        try {
            clienteMongo = mongoConexion.crearConexion();
            MongoDatabase database = mongoConexion.obtenerBaseDatos(clienteMongo);
            MongoCollection<Empleado> coleccion = database.getCollection("Empleados", Empleado.class);
            coleccion.find(Filters.eq("activo", true)).into(empleados);
            return empleados;
        } catch (MongoException e) {
            throw new DAOObtenerEmpleadoException("Error al obtener todos los empleados activos: " + e.getMessage(), e);
        } finally {
            if (clienteMongo != null) {
                mongoConexion.cerrarConexion(clienteMongo);
            }
        }
    }

    @Override
    public Empleado obtenerEmpleadoActivoPorId(String empleadoIdString) throws DAOObtenerEmpleadoException, DAOValidacionEmpleadoException {
        ObjectId empleadoId = convertirAObjectId(empleadoIdString, "obtener activo por ID");
        MongoClient clienteMongo = null;
        try {
            clienteMongo = mongoConexion.crearConexion();
            MongoDatabase database = mongoConexion.obtenerBaseDatos(clienteMongo);
            MongoCollection<Empleado> coleccion = database.getCollection("Empleados", Empleado.class);
            Bson filtro = Filters.and(
                    Filters.eq("_id", empleadoId),
                    Filters.eq("activo", true)
            );
            return coleccion.find(filtro).first();
        } catch (MongoException e) {
            throw new DAOObtenerEmpleadoException("Error al obtener empleado activo por ID " + empleadoIdString + ": " + e.getMessage(), e);
        } finally {
            if (clienteMongo != null) {
                mongoConexion.cerrarConexion(clienteMongo);
            }
        }
    }

    @Override
    public Empleado obtenerEmpleadoPorIdInterno(String empleadoIdString) throws DAOObtenerEmpleadoException, DAOValidacionEmpleadoException {
        ObjectId empleadoId = convertirAObjectId(empleadoIdString, "obtener interno por ID");
        MongoClient clienteMongo = null;
        try {
            clienteMongo = mongoConexion.crearConexion();
            MongoDatabase database = mongoConexion.obtenerBaseDatos(clienteMongo);
            MongoCollection<Empleado> coleccion = database.getCollection("Empleados", Empleado.class);
            return coleccion.find(Filters.eq("_id", empleadoId)).first();
        } catch (MongoException e) {
            throw new DAOObtenerEmpleadoException("Error al obtener empleado (interno) por ID " + empleadoIdString + ": " + e.getMessage(), e);
        } finally {
            if (clienteMongo != null) {
                mongoConexion.cerrarConexion(clienteMongo);
            }
        }
    }

    @Override
    public boolean actualizarEmpleado(Empleado empleado) throws DAOActualizarEmpleadoException, DAOValidacionEmpleadoException {
        MongoClient clienteMongo = null;
        if (empleado.getId() == null) { // Aquí se refiere al ObjectId de la entidad
            throw new DAOValidacionEmpleadoException("El empleado a actualizar debe tener un ID (ObjectId).");
        }
        try {
            clienteMongo = mongoConexion.crearConexion();
            MongoDatabase database = mongoConexion.obtenerBaseDatos(clienteMongo);
            MongoCollection<Empleado> coleccion = database.getCollection("Empleados", Empleado.class);
            UpdateResult result = coleccion.replaceOne(Filters.eq("_id", empleado.getId()), empleado);
            return result.getModifiedCount() > 0;
        } catch (MongoException e) {
            throw new DAOActualizarEmpleadoException("Error al actualizar empleado con ID " + empleado.getId().toHexString() + ": " + e.getMessage(), e);
        } finally {
            if (clienteMongo != null) {
                mongoConexion.cerrarConexion(clienteMongo);
            }
        }
    }

    @Override
    public boolean despedirEmpleado(String empleadoIdString) throws DAODespedirEmpleadoException, DAOValidacionEmpleadoException {
        ObjectId empleadoId = convertirAObjectId(empleadoIdString, "despedir empleado");
        MongoClient clienteMongo = null;
        try {
            clienteMongo = mongoConexion.crearConexion();
            MongoDatabase database = mongoConexion.obtenerBaseDatos(clienteMongo);
            MongoCollection<Empleado> coleccion = database.getCollection("Empleados", Empleado.class);
            Bson filtro = Filters.and(
                    Filters.eq("_id", empleadoId),
                    Filters.eq("activo", true)
            );
            Bson actualizacion = Updates.set("activo", false);
            UpdateResult result = coleccion.updateOne(filtro, actualizacion);
            return result.getModifiedCount() > 0;
        } catch (MongoException e) {
            throw new DAODespedirEmpleadoException("Error al marcar como inactivo al empleado con ID " + empleadoIdString + ": " + e.getMessage(), e);
        } finally {
            if (clienteMongo != null) {
                mongoConexion.cerrarConexion(clienteMongo);
            }
        }
    }

    @Override
    public List<Empleado> obtenerEmpleadosActivosPorCargo(Cargo cargo) throws DAOObtenerEmpleadoException, DAOValidacionEmpleadoException {
        if (cargo == null) {
            throw new DAOValidacionEmpleadoException("El cargo no puede ser nulo para buscar empleados.");
        }
        MongoClient clienteMongo = null;
        List<Empleado> empleados = new ArrayList<>();
        try {
            clienteMongo = mongoConexion.crearConexion();
            MongoDatabase database = mongoConexion.obtenerBaseDatos(clienteMongo);
            MongoCollection<Empleado> coleccion = database.getCollection("Empleados", Empleado.class);
            Bson filtro = Filters.and(
                    Filters.eq("cargo", cargo.name()), // Guardar el nombre del enum
                    Filters.eq("activo", true)
            );
            coleccion.find(filtro).into(empleados);
            return empleados;
        } catch (MongoException e) {
            throw new DAOObtenerEmpleadoException("Error al obtener empleados activos por cargo '" + cargo.name() + "': " + e.getMessage(), e);
        } finally {
            if (clienteMongo != null) {
                mongoConexion.cerrarConexion(clienteMongo);
            }
        }
    }

    @Override
    public boolean actualizarCargoEmpleado(String empleadoIdString, Cargo nuevoCargo) throws DAOActualizarEmpleadoException, DAOValidacionEmpleadoException {
        ObjectId empleadoId = convertirAObjectId(empleadoIdString, "actualizar cargo");
        if (nuevoCargo == null) {
            throw new DAOValidacionEmpleadoException("El nuevo cargo no puede ser nulo.");
        }
        MongoClient clienteMongo = null;
        try {
            clienteMongo = mongoConexion.crearConexion();
            MongoDatabase database = mongoConexion.obtenerBaseDatos(clienteMongo);
            MongoCollection<Empleado> coleccion = database.getCollection("Empleados", Empleado.class);
            Bson filtro = Filters.and(
                    Filters.eq("_id", empleadoId),
                    Filters.eq("activo", true)
            );
            Bson actualizacion = Updates.set("cargo", nuevoCargo.name());
            UpdateResult result = coleccion.updateOne(filtro, actualizacion);
            return result.getModifiedCount() > 0;
        } catch (MongoException e) {
            throw new DAOActualizarEmpleadoException("Error al actualizar cargo del empleado con ID " + empleadoIdString + ": " + e.getMessage(), e);
        } finally {
            if (clienteMongo != null) {
                mongoConexion.cerrarConexion(clienteMongo);
            }
        }
    }

    @Override
    public boolean actualizarSueldoIndividual(String empleadoIdString, Double nuevoSueldo) throws DAOActualizarEmpleadoException, DAOValidacionEmpleadoException {
        ObjectId empleadoId = convertirAObjectId(empleadoIdString, "actualizar sueldo individual");
        // La validación de rangos de sueldo es más lógica de negocio, el DAO se enfoca en la operación.
        MongoClient clienteMongo = null;
        try {
            clienteMongo = mongoConexion.crearConexion();
            MongoDatabase database = mongoConexion.obtenerBaseDatos(clienteMongo);
            MongoCollection<Empleado> coleccion = database.getCollection("Empleados", Empleado.class);
            Bson filtro = Filters.and(
                    Filters.eq("_id", empleadoId),
                    Filters.eq("activo", true)
            );
            Bson actualizacion = Updates.set("sueldo", nuevoSueldo);
            UpdateResult result = coleccion.updateOne(filtro, actualizacion);
            return result.getModifiedCount() > 0;
        } catch (MongoException e) {
            // Podrías lanzar DAOActualizarSueldoException si la tienes y es específica
            throw new DAOActualizarEmpleadoException("Error al actualizar sueldo del empleado con ID " + empleadoIdString + ": " + e.getMessage(), e);
        } finally {
            if (clienteMongo != null) {
                mongoConexion.cerrarConexion(clienteMongo);
            }
        }
    }

    @Override
    public long actualizarSueldoPorCargo(Cargo cargo, Double nuevoSueldo) throws DAOActualizarEmpleadoException, DAOValidacionEmpleadoException {
        if (cargo == null) {
            throw new DAOValidacionEmpleadoException("El cargo no puede ser nulo para actualizar sueldos masivamente.");
        }
        MongoClient clienteMongo = null;
        try {
            clienteMongo = mongoConexion.crearConexion();
            MongoDatabase database = mongoConexion.obtenerBaseDatos(clienteMongo);
            MongoCollection<Empleado> coleccion = database.getCollection("Empleados", Empleado.class);
            Bson filtro = Filters.and(
                    Filters.eq("cargo", cargo.name()),
                    Filters.eq("activo", true)
            );
            Bson actualizacion = Updates.set("sueldo", nuevoSueldo);
            UpdateResult result = coleccion.updateMany(filtro, actualizacion);
            return result.getModifiedCount();
        } catch (MongoException e) {
            // Podrías lanzar DAOActualizarSueldoException si la tienes
            throw new DAOActualizarEmpleadoException("Error al actualizar sueldos por cargo '" + cargo.name() + "': " + e.getMessage(), e);
        } finally {
            if (clienteMongo != null) {
                mongoConexion.cerrarConexion(clienteMongo);
            }
        }
    }

    @Override
    public boolean existeEmpleadoConEseCorreo(String correoE) throws DAOObtenerEmpleadoException, DAOValidacionEmpleadoException {
        if (correoE == null || correoE.trim().isEmpty()) {
            throw new DAOValidacionEmpleadoException("El correo para verificar existencia no puede ser nulo o vacío.");
        }
        MongoClient clienteMongo = null;
        try {
            clienteMongo = mongoConexion.crearConexion();
            MongoDatabase database = mongoConexion.obtenerBaseDatos(clienteMongo);
            MongoCollection<Empleado> coleccion = database.getCollection("Empleados", Empleado.class);
            Bson filtro = Filters.eq("correoE", correoE);
            return coleccion.countDocuments(filtro) > 0; // Más eficiente que traer el documento
        } catch (MongoException e) {
            throw new DAOObtenerEmpleadoException("Error al verificar existencia de empleado por correo: " + e.getMessage(), e);
        } finally {
            if (clienteMongo != null) {
                mongoConexion.cerrarConexion(clienteMongo);
            }
        }
    }

    @Override
    public Empleado consultarPorCorreoActivoExcluyendoId(String correoE, String excluirEmpleadoIdString) throws DAOObtenerEmpleadoException, DAOValidacionEmpleadoException {
        if (correoE == null || correoE.trim().isEmpty()) {
            throw new DAOValidacionEmpleadoException("El correo para consultar no puede ser nulo o vacío.");
        }
        ObjectId excluirEmpleadoId = convertirAObjectId(excluirEmpleadoIdString, "consultar correo excluyendo ID");
        MongoClient clienteMongo = null;
        try {
            clienteMongo = mongoConexion.crearConexion();
            MongoDatabase database = mongoConexion.obtenerBaseDatos(clienteMongo);
            MongoCollection<Empleado> coleccion = database.getCollection("Empleados", Empleado.class);
            Bson filtro = Filters.and(
                    Filters.eq("correoE", correoE.trim()),
                    Filters.eq("activo", true),
                    Filters.ne("_id", excluirEmpleadoId)
            );
            return coleccion.find(filtro).first();
        } catch (MongoException e) {
            throw new DAOObtenerEmpleadoException("Error al consultar empleado por correoE '" + correoE + "' excluyendo ID " + excluirEmpleadoIdString + ": " + e.getMessage(), e);
        } finally {
            if (clienteMongo != null) {
                mongoConexion.cerrarConexion(clienteMongo);
            }
        }
    }

}
