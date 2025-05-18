/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Conexion.MongoConexion;
import Excepciones.usuarios.ActualizarClienteException;
import Excepciones.usuarios.CargarHistorialException;
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

    //Quizas agregar una proyeccion
    ClienteDAO() {

    }

    public static ClienteDAO getInstance() {
        if (instance == null) {
            instance = new ClienteDAO();
        }
        return instance;
    }

    /**
     * Registra un nuevo cliente en la base de datos.
     *
     * Este método valida la información del cliente y, si es válida, inserta el
     * cliente en la colección. Si ocurre un error durante la validación o la
     * inserción se lanza RegistrarClienteException
     *
     * @param cliente el objeto Cliente que se desea registrar.
     * @return el objeto Cliente registrado, con información actualizada.
     * @throws RegistrarClienteException si ocurre un error al intentar
     * registrar el cliente.
     */
    @Override
    public Cliente registrarCliente(Cliente cliente) throws RegistrarClienteException {
        MongoClient clienteMongo = null;
        try {
            clienteMongo = conexion.crearConexion();
            MongoDatabase base = conexion.obtenerBaseDatos(clienteMongo);

            MongoCollection<Cliente> coleccion = base.getCollection(nombreColeccion, Cliente.class);

            //llama al metodo de validacion, este metodo revisa que no se dulplique ningun dato unico que ya este registrado en la base de datos
            validarRegistro(coleccion, cliente);

            coleccion.insertOne(cliente); //Realiza la operacion de insertar un cliente en la coleccion

            return cliente; // Devuelve el cliente registrado

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

    /**
     * Actualiza la información de un cliente en la base de datos.
     *
     * Este método busca al cliente en la base de datos utilizando su nombre de
     * usuario, valida la información del cliente modificado y, si es válida,
     * reemplaza el documento del cliente en la colección, antes de hacer la
     * actualizacion del cliente realiza una busqueda con el nombre de usuario
     * para validar que el cliente que se quiere actualizar si exista
     *
     * @param clienteModificado el objeto Cliente que contiene la información
     * actualizada.
     * @return el objeto Cliente modificado, con la información actualizada.
     * @throws ActualizarClienteException si ocurre un error al intentar
     * actualizar el cliente.
     */
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

            //Se llama al metodo para validar la actuliazacion, como los anteriores este metodo se encarga de validar
            //que no se repitan datos que ya estan registrados en la base, nombre de usuario, correo, telefono, etc.
            validarActualizacion(coleccion, original, clienteModificado);

            //Reemplazar todo el documento
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

    /**
     * Obtiene un cliente específico basado en su nombre de usuario y
     * contraseña.
     *
     * Este método busca al cliente que
     * coincide con las credenciales proporcionadas y devuelve el objeto Cliente
     * correspondiente. Si ocurre un error durante la conexión o la búsqueda se lanza EncontrarClienteException
     *
     * @param nombreUsuario el nombre de usuario del cliente que se desea
     * obtener.
     * @param contrasena la contraseña del cliente que se desea obtener.
     * @return el objeto Cliente correspondiente a las credenciales
     * proporcionadas, o null si no se encuentra.
     * @throws EncontrarClienteException si ocurre un error al intentar obtener
     * el cliente.
     */
    @Override
    public Cliente obtenerCliente(String nombreUsuario, String contrasena) throws EncontrarClienteException {
        MongoClient clienteMongo = null;
        try {
            clienteMongo = conexion.crearConexion();
            MongoDatabase base = conexion.obtenerBaseDatos(clienteMongo);

            //Se construye el filtro que se utilizara para buscar al cliente, busca por el nombre de usuario, la contraseña y limita la busqueda a usuarios que tengan
            //el rol de "CLIENTE" es decir usuarios que sean clientes
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

    //Metodos auxiliares para validar el registro de un cliente y la actualizacion. Si alguna de las validaciones falla
    //se lanza un ValidarUsuarioException, hay validaciones para el nombre de usuysario, correo electronico y telefono
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

    //La diferencia entre el metodo de validar registro y validar actualizacion es que 
    //en la validacion de la actualizacion se reciben el cliente original que se encontro para ser actualizado y el cliente con el que se quiere reemplazar
    //para hacer la actualizacion, esto por que si se hace una actualizacion en la que se deja el nombre de usuario igual que como estaba antes
    //el sistema creera que se estan tratando de duplicar, por lo tanto se comparan el original y el modificado haciendo busquedas con filtros que excluyen al original
    public void validarActualizacion(MongoCollection<Cliente> coleccion, Cliente original, Cliente modificado) throws ValidarUsuarioException {

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
