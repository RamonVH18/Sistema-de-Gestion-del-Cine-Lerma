/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Conexion.MongoConexion;
import Excepciones.PersistenciaException;
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
    private final MongoConexion mongoConexion; // instancia conexion

    private EmpleadoDAO() {
        this.mongoConexion = new MongoConexion(); // Asumiendo que así la inicializas
    }

    // get instance del dao
    public static synchronized EmpleadoDAO getInstance() {
        if (instance == null) {
            instance = new EmpleadoDAO();
        }
        return instance;
    }

    @Override
    public boolean registrarEmpleado(Empleado empleado) throws PersistenciaException {
        MongoClient clienteMongo = null;
        try {
            clienteMongo = mongoConexion.crearConexion();
            MongoDatabase database = mongoConexion.obtenerBaseDatos(clienteMongo);
            MongoCollection<Empleado> coleccion = database.getCollection("empleados", Empleado.class);

            // Asegurar que el empleado tenga un ID antes de insertar.
            // El constructor de Empleado ya inicializa 'activo' y 'fechaRegistro'.
            if (empleado.getId() == null) {
                empleado.setId(new ObjectId()); // Genera un ID si no existe
            }

            coleccion.insertOne(empleado);
            return true;

        } catch (MongoException e) {
            throw new PersistenciaException("Error al registrar empleado: " + e.getMessage(), e);
        } finally {
            if (clienteMongo != null) {
                mongoConexion.cerrarConexion(clienteMongo);
            }
        }
    }

    // obtiene todos los empleados activos
    @Override
    public List<Empleado> obtenerTodosLosEmpleadosActivos() throws PersistenciaException {
        MongoClient clienteMongo = null;
        List<Empleado> empleados = new ArrayList<>();
        try {
            clienteMongo = mongoConexion.crearConexion();
            MongoDatabase database = mongoConexion.obtenerBaseDatos(clienteMongo);
            MongoCollection<Empleado> coleccion = database.getCollection("empleados", Empleado.class);

            // Filtrar para obtener solo empleados activos
            coleccion.find(Filters.eq("activo", true)).into(empleados);
            return empleados;
        } catch (MongoException e) {
            throw new PersistenciaException("Error al obtener todos los empleados activos: " + e.getMessage(), e);
        } finally {
            if (clienteMongo != null) {
                mongoConexion.cerrarConexion(clienteMongo);
            }
        }
    }

    // obtiene un empleado activo buscandolo por su id
    @Override
    public Empleado obtenerEmpleadoActivoPorId(ObjectId empleadoId) throws PersistenciaException {
        MongoClient clienteMongo = null;
        try {
            clienteMongo = mongoConexion.crearConexion();
            MongoDatabase database = mongoConexion.obtenerBaseDatos(clienteMongo);
            MongoCollection<Empleado> coleccion = database.getCollection("empleados", Empleado.class);

            // Buscar por _id y que esté activo
            Bson filtro = Filters.and(
                    Filters.eq("_id", empleadoId),
                    Filters.eq("activo", true)
            );
            return coleccion.find(filtro).first();
        } catch (MongoException e) {
            throw new PersistenciaException("Error al obtener empleado activo por ID " + empleadoId + ": " + e.getMessage(), e);
        } finally {
            if (clienteMongo != null) {
                mongoConexion.cerrarConexion(clienteMongo);
            }
        }
    }

    // obtiene un empleado por su id sin importar que este activo o no
    @Override
    public Empleado obtenerEmpleadoPorIdInterno(ObjectId empleadoId) throws PersistenciaException {
        MongoClient clienteMongo = null;
        try {
            clienteMongo = mongoConexion.crearConexion();
            MongoDatabase database = mongoConexion.obtenerBaseDatos(clienteMongo);
            MongoCollection<Empleado> coleccion = database.getCollection("empleados", Empleado.class);
            return coleccion.find(Filters.eq("_id", empleadoId)).first();
        } catch (MongoException e) {
            throw new PersistenciaException("Error al obtener empleado (interno) por ID " + empleadoId + ": " + e.getMessage(), e);
        } finally {
            if (clienteMongo != null) {
                mongoConexion.cerrarConexion(clienteMongo);
            }
        }
    }

    // actualiza un empleado dado un empleado
    @Override
    public boolean actualizarEmpleado(Empleado empleado) throws PersistenciaException {
        MongoClient clienteMongo = null;
        if (empleado.getId() == null) {
            throw new PersistenciaException("El empleado a actualizar debe tener un ID.");
        }
        try {
            clienteMongo = mongoConexion.crearConexion();
            MongoDatabase database = mongoConexion.obtenerBaseDatos(clienteMongo);
            MongoCollection<Empleado> coleccion = database.getCollection("empleados", Empleado.class);

            UpdateResult result = coleccion.replaceOne(Filters.eq("_id", empleado.getId()), empleado);
            return result.getModifiedCount() > 0;

        } catch (MongoException e) {
            throw new PersistenciaException("Error al actualizar empleado con ID " + empleado.getId() + ": " + e.getMessage(), e);
        } finally {
            if (clienteMongo != null) {
                mongoConexion.cerrarConexion(clienteMongo);
            }
        }
    }

    // marca un empleado como inactivo dado el id, osea lo despide
    @Override
    public boolean despedirEmpleado(ObjectId empleadoId) throws PersistenciaException {
        MongoClient clienteMongo = null;
        if (empleadoId == null) {
            throw new PersistenciaException("El ID del empleado para la baja no puede ser nulo.");
        }
        try {
            clienteMongo = mongoConexion.crearConexion();
            MongoDatabase database = mongoConexion.obtenerBaseDatos(clienteMongo);
            MongoCollection<Empleado> coleccion = database.getCollection("empleados", Empleado.class);

            Bson filtro = Filters.and(
                    Filters.eq("_id", empleadoId),
                    Filters.eq("activo", true) // Solo dar de baja si está activo
            );
            Bson actualizacion = Updates.set("activo", false);
            UpdateResult result = coleccion.updateOne(filtro, actualizacion);

            return result.getModifiedCount() > 0;

        } catch (MongoException e) {
            throw new PersistenciaException("Error al marcar como inactivo al empleado con ID " + empleadoId + ": " + e.getMessage(), e);
        } finally {
            if (clienteMongo != null) {
                mongoConexion.cerrarConexion(clienteMongo);
            }
        }
    }

    // obtiene todos los empleados activos por un cargo especifico
    @Override
    public List<Empleado> obtenerEmpleadosActivosPorCargo(Cargo cargo) throws PersistenciaException {
        MongoClient clienteMongo = null;
        List<Empleado> empleados = new ArrayList<>();
        try {
            clienteMongo = mongoConexion.crearConexion();
            MongoDatabase database = mongoConexion.obtenerBaseDatos(clienteMongo);
            MongoCollection<Empleado> coleccion = database.getCollection("empleados", Empleado.class);

            Bson filtro = Filters.and(
                    Filters.eq("cargo", cargo.name()),
                    Filters.eq("activo", true)
            );
            coleccion.find(filtro).into(empleados);
            return empleados;
        } catch (MongoException e) {
            throw new PersistenciaException("Error al obtener empleados activos por cargo '" + cargo.name() + "': " + e.getMessage(), e);
        } finally {
            if (clienteMongo != null) {
                mongoConexion.cerrarConexion(clienteMongo);
            }
        }
    }

    // actualiza el cargo de un empleado activo por su id, si fue exitoso retorna true, false si no
    @Override
    public boolean actualizarCargoEmpleado(ObjectId empleadoId, Cargo nuevoCargo) throws PersistenciaException {
        MongoClient clienteMongo = null;
        if (empleadoId == null || nuevoCargo == null) {
            throw new PersistenciaException("ID de empleado y nuevo cargo no pueden ser nulos.");
        }
        try {
            clienteMongo = mongoConexion.crearConexion();
            MongoDatabase database = mongoConexion.obtenerBaseDatos(clienteMongo);
            MongoCollection<Empleado> coleccion = database.getCollection("empleados", Empleado.class);

            Bson filtro = Filters.and(
                    Filters.eq("_id", empleadoId),
                    Filters.eq("activo", true)
            );
            Bson actualizacion = Updates.set("cargo", nuevoCargo.name());
            UpdateResult result = coleccion.updateOne(filtro, actualizacion);

            return result.getModifiedCount() > 0;

        } catch (MongoException e) {
            throw new PersistenciaException("Error al actualizar cargo del empleado con ID " + empleadoId + ": " + e.getMessage(), e);
        } finally {
            if (clienteMongo != null) {
                mongoConexion.cerrarConexion(clienteMongo);
            }
        }
    }

    // actualiza el saldo de un empleado de manera individual por su id, true si fue exitoso, false si no
    @Override
    public boolean actualizarSueldoIndividual(ObjectId empleadoId, double nuevoSueldo) throws PersistenciaException {
        MongoClient clienteMongo = null;
        if (empleadoId == null) {
            throw new PersistenciaException("ID de empleado no puede ser nulo para actualizar sueldo.");
        }
        try {
            clienteMongo = mongoConexion.crearConexion();
            MongoDatabase database = mongoConexion.obtenerBaseDatos(clienteMongo);
            MongoCollection<Empleado> coleccion = database.getCollection("empleados", Empleado.class);

            Bson filtro = Filters.and(
                    Filters.eq("_id", empleadoId),
                    Filters.eq("activo", true)
            );
            Bson actualizacion = Updates.set("sueldo", nuevoSueldo);
            UpdateResult result = coleccion.updateOne(filtro, actualizacion);

            return result.getModifiedCount() > 0;

        } catch (MongoException e) {
            throw new PersistenciaException("Error al actualizar sueldo del empleado con ID " + empleadoId + ": " + e.getMessage(), e);
        } finally {
            if (clienteMongo != null) {
                mongoConexion.cerrarConexion(clienteMongo);
            }
        }
    }

    // actualiza el saldo de todos los empleados activos con ese cargo, retorna el numero de empleados actualizados
    @Override
    public long actualizarSueldoPorCargo(Cargo cargo, double nuevoSueldo) throws PersistenciaException {
        MongoClient clienteMongo = null;
        if (cargo == null) {
            throw new PersistenciaException("El cargo no puede ser nulo para actualizar sueldos masivamente.");
        }
        try {
            clienteMongo = mongoConexion.crearConexion();
            MongoDatabase database = mongoConexion.obtenerBaseDatos(clienteMongo);
            MongoCollection<Empleado> coleccion = database.getCollection("empleados", Empleado.class);

            Bson filtro = Filters.and(
                    Filters.eq("cargo", cargo.name()),
                    Filters.eq("activo", true) // Solo actualizar empleados activos
            );
            Bson actualizacion = Updates.set("sueldo", nuevoSueldo);
            UpdateResult result = coleccion.updateMany(filtro, actualizacion);

            return result.getModifiedCount();

        } catch (MongoException e) {
            throw new PersistenciaException("Error al actualizar sueldos por cargo '" + cargo.name() + "': " + e.getMessage(), e);
        } finally {
            if (clienteMongo != null) {
                mongoConexion.cerrarConexion(clienteMongo);
            }
        } 
   }

    public boolean existeEmpleadoConEseCorreo(String correoE) throws PersistenciaException {

        MongoClient clienteMongo = null;
        try {
            clienteMongo = mongoConexion.crearConexion();
            MongoDatabase database = mongoConexion.obtenerBaseDatos(clienteMongo);
            MongoCollection<Empleado> coleccion = database.getCollection("empleados", Empleado.class);

            Bson filtro = Filters.eq("correoE", correoE);

            Empleado empleado = coleccion.find(filtro).first();

            if (empleado != null) {
                return true;
            } else {
                return false;
            }
        } catch (MongoException e) {
            throw new PersistenciaException("Error al ver la existencia de un empleado por correo ");
        } finally {
            if (clienteMongo != null) {
                mongoConexion.cerrarConexion(clienteMongo);
            }
        }

    }
    
    @Override
    public Empleado consultarPorCorreoActivoExcluyendoId(String correoE, ObjectId excluirEmpleadoId) throws PersistenciaException {
    MongoClient clienteMongo = null;
    if (correoE == null || correoE.trim().isEmpty() || excluirEmpleadoId == null) {
        // Podrías lanzar una excepción por parámetros inválidos
        return null;
    }
    try {
        clienteMongo = mongoConexion.crearConexion();
        MongoDatabase database = mongoConexion.obtenerBaseDatos(clienteMongo);
        MongoCollection<Empleado> coleccion = database.getCollection("empleados", Empleado.class);

        Bson filtro = Filters.and(
                Filters.eq("correoE", correoE.trim()),
                Filters.eq("activo", true),
                Filters.ne("_id", excluirEmpleadoId) // ne = Not Equal (no igual a este ID)
        );
        return coleccion.find(filtro).first();
    } catch (MongoException e) {
        throw new PersistenciaException("Error al consultar empleado por correoE '" + correoE + "' excluyendo ID " + excluirEmpleadoId + ": " + e.getMessage(), e);
    } finally {
        if (clienteMongo != null) {
            mongoConexion.cerrarConexion(clienteMongo);
        }
    }
}

}
