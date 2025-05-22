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
    private final MongoConexion mongoConexion; // Gestor de la conexión a MongoDB

    /**
     * Constructor privado para implementar el patrón Singleton. Inicializa la
     * conexión a MongoDB.
     */
    private EmpleadoDAO() {
        this.mongoConexion = new MongoConexion();
    }

    /**
     * Obtiene la instancia única de EmpleadoDAO (Patrón Singleton). Este método
     * está sincronizado para asegurar la correcta creación de la instancia en
     * entornos concurrentes.
     *
     * @return La instancia única de EmpleadoDAO.
     */
    public static synchronized EmpleadoDAO getInstance() {
        if (instance == null) {
            instance = new EmpleadoDAO();
        }
        return instance;
    }

    /**
     * Convierte y valida una cadena de texto que representa un ID a un
     * ObjectId. Este método es de utilidad interna para el DAO.
     *
     * @param idString La cadena de texto del ID a convertir.
     * @param contextoError Una cadena descriptiva del contexto de la operación
     * para mensajes de error.
     * @return El ObjectId correspondiente si la cadena es válida.
     * @throws DAOValidacionEmpleadoException Si la cadena del ID es nula, vacía
     * o no tiene un formato válido de ObjectId.
     */
    private ObjectId convertirAObjectId(String idString, String contextoError) throws DAOValidacionEmpleadoException {
        if (idString == null || idString.trim().isEmpty()) {
            throw new DAOValidacionEmpleadoException("El ID (" + contextoError + ") no puede ser nulo o vacío.");
        }
        if (!ObjectId.isValid(idString)) {
            throw new DAOValidacionEmpleadoException("Formato de ID (" + contextoError + ") inválido: " + idString);
        }
        return new ObjectId(idString);
    }

    /**
     * Registra un nuevo empleado en la base de datos. Si el empleado no tiene
     * un ID (ObjectId) asignado, se le generará uno nuevo.
     *
     * @param empleado El objeto Empleado a registrar. Se espera que los campos
     * como activo y fechaRegistro sean manejados por la lógica de negocio o el
     * constructor de la entidad si son nuevos.
     * @return true si el empleado fue registrado exitosamente.
     * @throws DAORegistrarEmpleadoException Si ocurre un error durante la
     * conexión o la inserción en MongoDB, por ejemplo, una violación de índice
     * único (correo duplicado).
     */
    @Override
    public boolean registrarEmpleado(Empleado empleado) throws DAORegistrarEmpleadoException {
        MongoClient clienteMongo = null;
        try {
            clienteMongo = mongoConexion.crearConexion();
            MongoDatabase database = mongoConexion.obtenerBaseDatos(clienteMongo);
            MongoCollection<Empleado> coleccion = database.getCollection("empleados", Empleado.class); // Corregido a "empleados"

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

    /**
     * Obtiene una lista de todos los empleados que están marcados como activos
     * en la base de datos.
     *
     * @return Una lista de objetos Empleado activos. La lista estará vacía si
     * no hay empleados activos.
     * @throws DAOObtenerEmpleadoException Si ocurre un error durante la
     * conexión o la consulta a MongoDB.
     */
    @Override
    public List<Empleado> obtenerTodosLosEmpleadosActivos() throws DAOObtenerEmpleadoException {
        MongoClient clienteMongo = null;
        List<Empleado> empleados = new ArrayList<>();
        try {
            clienteMongo = mongoConexion.crearConexion();
            MongoDatabase database = mongoConexion.obtenerBaseDatos(clienteMongo);
            MongoCollection<Empleado> coleccion = database.getCollection("empleados", Empleado.class); // Corregido
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

    /**
     * Obtiene un empleado específico por su ID, solo si está activo.
     *
     * @param empleadoIdString El ID del empleado en formato String.
     * @return El objeto Empleado si se encuentra y está activo; null en caso
     * contrario.
     * @throws DAOObtenerEmpleadoException Si ocurre un error durante la
     * conexión o la consulta a MongoDB.
     * @throws DAOValidacionEmpleadoException Si el empleadoIdString es nulo,
     * vacío o no tiene un formato de ObjectId válido.
     */
    @Override
    public Empleado obtenerEmpleadoActivoPorId(String empleadoIdString) throws DAOObtenerEmpleadoException, DAOValidacionEmpleadoException {
        ObjectId empleadoId = convertirAObjectId(empleadoIdString, "obtener activo por ID");
        MongoClient clienteMongo = null;
        try {
            clienteMongo = mongoConexion.crearConexion();
            MongoDatabase database = mongoConexion.obtenerBaseDatos(clienteMongo);
            MongoCollection<Empleado> coleccion = database.getCollection("empleados", Empleado.class); // Corregido
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

    /**
     * Obtiene un empleado específico por su ID, sin importar su estado (activo
     * o inactivo). Usado internamente o para operaciones administrativas donde
     * se necesita el registro completo.
     *
     * @param empleadoIdString El ID del empleado en formato String.
     * @return El objeto Empleado si se encuentra; null en caso contrario.
     * @throws DAOObtenerEmpleadoException Si ocurre un error durante la
     * conexión o la consulta a MongoDB.
     * @throws DAOValidacionEmpleadoException Si el empleadoIdString es nulo,
     * vacío o no tiene un formato de ObjectId válido.
     */
    @Override
    public Empleado obtenerEmpleadoPorIdInterno(String empleadoIdString) throws DAOObtenerEmpleadoException, DAOValidacionEmpleadoException {
        ObjectId empleadoId = convertirAObjectId(empleadoIdString, "obtener interno por ID");
        MongoClient clienteMongo = null;
        try {
            clienteMongo = mongoConexion.crearConexion();
            MongoDatabase database = mongoConexion.obtenerBaseDatos(clienteMongo);
            MongoCollection<Empleado> coleccion = database.getCollection("empleados", Empleado.class); // Corregido
            return coleccion.find(Filters.eq("_id", empleadoId)).first();
        } catch (MongoException e) {
            throw new DAOObtenerEmpleadoException("Error al obtener empleado (interno) por ID " + empleadoIdString + ": " + e.getMessage(), e);
        } finally {
            if (clienteMongo != null) {
                mongoConexion.cerrarConexion(clienteMongo);
            }
        }
    }

    /**
     * Actualiza la información completa de un empleado existente en la base de
     * datos. La entidad Empleado proporcionada debe tener un ID (ObjectId)
     * válido.
     *
     * @param empleado El objeto Empleado con los datos actualizados y su ID
     * (ObjectId) original.
     * @return true si el empleado fue actualizado exitosamente (al menos un
     * documento modificado), false en caso contrario.
     * @throws DAOActualizarEmpleadoException Si ocurre un error durante la
     * conexión o la actualización en MongoDB.
     * @throws DAOValidacionEmpleadoException Si el ID (ObjectId) del empleado
     * en la entidad es nulo.
     */
    @Override
    public boolean actualizarEmpleado(Empleado empleado) throws DAOActualizarEmpleadoException, DAOValidacionEmpleadoException {
        MongoClient clienteMongo = null;
        if (empleado.getId() == null) { // Se refiere al ObjectId de la entidad
            throw new DAOValidacionEmpleadoException("El empleado a actualizar debe tener un ID (ObjectId).");
        }
        try {
            clienteMongo = mongoConexion.crearConexion();
            MongoDatabase database = mongoConexion.obtenerBaseDatos(clienteMongo);
            MongoCollection<Empleado> coleccion = database.getCollection("empleados", Empleado.class); // Corregido
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

    /**
     * Marca un empleado como inactivo (despido lógico) en la base de datos.
     * Solo afecta a empleados que actualmente están activos.
     *
     * @param empleadoIdString El ID del empleado a despedir, en formato String.
     * @return true si el empleado fue marcado como inactivo exitosamente (se
     * modificó un documento), false en caso contrario.
     * @throws DAODespedirEmpleadoException Si ocurre un error durante la
     * conexión o la actualización en MongoDB.
     * @throws DAOValidacionEmpleadoException Si el empleadoIdString es nulo,
     * vacío o no tiene un formato de ObjectId válido.
     */
    @Override
    public boolean despedirEmpleado(String empleadoIdString) throws DAODespedirEmpleadoException, DAOValidacionEmpleadoException {
        ObjectId empleadoId = convertirAObjectId(empleadoIdString, "despedir empleado");
        MongoClient clienteMongo = null;
        try {
            clienteMongo = mongoConexion.crearConexion();
            MongoDatabase database = mongoConexion.obtenerBaseDatos(clienteMongo);
            MongoCollection<Empleado> coleccion = database.getCollection("empleados", Empleado.class); // Corregido
            Bson filtro = Filters.and(
                    Filters.eq("_id", empleadoId),
                    Filters.eq("activo", true) // Solo dar de baja si está activo
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

    /**
     * Obtiene una lista de todos los empleados activos que tienen un cargo
     * específico.
     *
     * @param cargo El cargo por el cual filtrar los empleados.
     * @return Una lista de objetos Empleado activos con el cargo especificado.
     * Lista vacía si no hay coincidencias.
     * @throws DAOObtenerEmpleadoException Si ocurre un error durante la
     * conexión o la consulta a MongoDB.
     * @throws DAOValidacionEmpleadoException Si el objeto Cargo es nulo.
     */
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
            MongoCollection<Empleado> coleccion = database.getCollection("empleados", Empleado.class); // Corregido
            Bson filtro = Filters.and(
                    Filters.eq("cargo", cargo.name()), // Se almacena el nombre del enum
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

    /**
     * Actualiza el cargo de un empleado activo específico.
     *
     * @param empleadoIdString El ID del empleado a actualizar, en formato
     * String.
     * @param nuevoCargo El nuevo cargo a asignar al empleado.
     * @return true si el cargo fue actualizado exitosamente (se modificó un
     * documento), false en caso contrario.
     * @throws DAOActualizarEmpleadoException Si ocurre un error durante la
     * conexión o la actualización en MongoDB.
     * @throws DAOValidacionEmpleadoException Si el empleadoIdString es
     * nulo/inválido, o si nuevoCargo es nulo.
     */
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
            MongoCollection<Empleado> coleccion = database.getCollection("empleados", Empleado.class); // Corregido
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

    /**
     * Actualiza el sueldo de un empleado activo específico.
     *
     * @param empleadoIdString El ID del empleado a actualizar, en formato
     * String.
     * @param nuevoSueldo El nuevo sueldo a asignar. Puede ser null si se
     * permite sueldo nulo en la BD.
     * @return true si el sueldo fue actualizado exitosamente (se modificó un
     * documento), false en caso contrario.
     * @throws DAOActualizarEmpleadoException Si ocurre un error durante la
     * conexión o la actualización en MongoDB. (Podría ser
     * DAOActualizarSueldoException si la tienes).
     * @throws DAOValidacionEmpleadoException Si el empleadoIdString es nulo,
     * vacío o no tiene un formato de ObjectId válido. No valida el valor de
     * nuevoSueldo (ej. rango), eso es lógica de negocio.
     */
    @Override
    public boolean actualizarSueldoIndividual(String empleadoIdString, Double nuevoSueldo) throws DAOActualizarEmpleadoException, DAOValidacionEmpleadoException {
        ObjectId empleadoId = convertirAObjectId(empleadoIdString, "actualizar sueldo individual");
        MongoClient clienteMongo = null;
        try {
            clienteMongo = mongoConexion.crearConexion();
            MongoDatabase database = mongoConexion.obtenerBaseDatos(clienteMongo);
            MongoCollection<Empleado> coleccion = database.getCollection("empleados", Empleado.class); // Corregido
            Bson filtro = Filters.and(
                    Filters.eq("_id", empleadoId),
                    Filters.eq("activo", true)
            );
            Bson actualizacion = Updates.set("sueldo", nuevoSueldo); // MongoDB permite setear a null
            UpdateResult result = coleccion.updateOne(filtro, actualizacion);
            return result.getModifiedCount() > 0;
        } catch (MongoException e) {
            // Considera lanzar DAOActualizarSueldoException si la tienes y es más apropiada.
            throw new DAOActualizarEmpleadoException("Error al actualizar sueldo del empleado con ID " + empleadoIdString + ": " + e.getMessage(), e);
        } finally {
            if (clienteMongo != null) {
                mongoConexion.cerrarConexion(clienteMongo);
            }
        }
    }

    /**
     * Actualiza el sueldo de todos los empleados activos que tienen un cargo
     * específico.
     *
     * @param cargo El cargo de los empleados cuyo sueldo se actualizará.
     * @param nuevoSueldo El nuevo sueldo a asignar. Puede ser null.
     * @return El número de empleados cuyo sueldo fue actualizado.
     * @throws DAOActualizarEmpleadoException Si ocurre un error durante la
     * conexión o la actualización en MongoDB. (Podría ser
     * DAOActualizarSueldoException).
     * @throws DAOValidacionEmpleadoException Si el objeto Cargo es nulo.
     */
    @Override
    public long actualizarSueldoPorCargo(Cargo cargo, Double nuevoSueldo) throws DAOActualizarEmpleadoException, DAOValidacionEmpleadoException {
        if (cargo == null) {
            throw new DAOValidacionEmpleadoException("El cargo no puede ser nulo para actualizar sueldos masivamente.");
        }
        MongoClient clienteMongo = null;
        try {
            clienteMongo = mongoConexion.crearConexion();
            MongoDatabase database = mongoConexion.obtenerBaseDatos(clienteMongo);
            MongoCollection<Empleado> coleccion = database.getCollection("empleados", Empleado.class); // Corregido
            Bson filtro = Filters.and(
                    Filters.eq("cargo", cargo.name()),
                    Filters.eq("activo", true)
            );
            Bson actualizacion = Updates.set("sueldo", nuevoSueldo);
            UpdateResult result = coleccion.updateMany(filtro, actualizacion);
            return result.getModifiedCount();
        } catch (MongoException e) {
            throw new DAOActualizarEmpleadoException("Error al actualizar sueldos por cargo '" + cargo.name() + "': " + e.getMessage(), e);
        } finally {
            if (clienteMongo != null) {
                mongoConexion.cerrarConexion(clienteMongo);
            }
        }
    }

    /**
     * Verifica si ya existe un empleado registrado con el correo electrónico
     * proporcionado.
     *
     * @param correoE El correo electrónico a verificar.
     * @return true si ya existe un empleado con ese correo, false en caso
     * contrario.
     * @throws DAOObtenerEmpleadoException Si ocurre un error durante la
     * conexión o la consulta a MongoDB.
     * @throws DAOValidacionEmpleadoException Si el correoE es nulo o vacío.
     */
    @Override
    public boolean existeEmpleadoConEseCorreo(String correoE) throws DAOObtenerEmpleadoException, DAOValidacionEmpleadoException {
        if (correoE == null || correoE.trim().isEmpty()) {
            throw new DAOValidacionEmpleadoException("El correo para verificar existencia no puede ser nulo o vacío.");
        }
        MongoClient clienteMongo = null;
        try {
            clienteMongo = mongoConexion.crearConexion();
            MongoDatabase database = mongoConexion.obtenerBaseDatos(clienteMongo);
            MongoCollection<Empleado> coleccion = database.getCollection("empleados", Empleado.class); // Corregido
            Bson filtro = Filters.eq("correoE", correoE);
            return coleccion.countDocuments(filtro) > 0;
        } catch (MongoException e) {
            throw new DAOObtenerEmpleadoException("Error al verificar existencia de empleado por correo: " + e.getMessage(), e);
        } finally {
            if (clienteMongo != null) {
                mongoConexion.cerrarConexion(clienteMongo);
            }
        }
    }

    /**
     * Consulta un empleado activo por su correo electrónico, excluyendo un ID
     * de empleado específico. Útil para validaciones de unicidad de correo al
     * actualizar un empleado.
     *
     * @param correoE El correo electrónico a buscar.
     * @param excluirEmpleadoIdString El ID (en formato String) del empleado que
     * se debe excluir de la búsqueda.
     * @return El objeto Empleado si se encuentra otro empleado activo con ese
     * correo (y diferente ID); null en caso contrario.
     * @throws DAOObtenerEmpleadoException Si ocurre un error durante la
     * conexión o la consulta a MongoDB.
     * @throws DAOValidacionEmpleadoException Si el correoE es nulo/vacío o si
     * excluirEmpleadoIdString es nulo/inválido.
     */
    @Override
    public Empleado consultarPorCorreoActivoExcluyendoId(String correoE, String excluirEmpleadoIdString) throws DAOObtenerEmpleadoException, DAOValidacionEmpleadoException {
        if (correoE == null || correoE.trim().isEmpty()) {
            throw new DAOValidacionEmpleadoException("El correo para consultar no puede ser nulo o vacío.");
        }
        // La validación y conversión del excluirEmpleadoIdString se hace aquí
        ObjectId excluirEmpleadoId = convertirAObjectId(excluirEmpleadoIdString, "consultar correo excluyendo ID");

        MongoClient clienteMongo = null;
        try {
            clienteMongo = mongoConexion.crearConexion();
            MongoDatabase database = mongoConexion.obtenerBaseDatos(clienteMongo);
            MongoCollection<Empleado> coleccion = database.getCollection("empleados", Empleado.class); // Corregido
            Bson filtro = Filters.and(
                    Filters.eq("correoE", correoE.trim()),
                    Filters.eq("activo", true),
                    Filters.ne("_id", excluirEmpleadoId) // Usar el ObjectId convertido
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
