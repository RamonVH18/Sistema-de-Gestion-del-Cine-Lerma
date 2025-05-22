/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package DAOs;

import Conexion.MongoConexion;
import Excepciones.usuarios.ActualizarClienteException;
import Excepciones.usuarios.CargarHistorialException;
import Excepciones.usuarios.EncontrarClienteException;
import Excepciones.usuarios.RegistrarClienteException;
import Excepciones.usuarios.ValidarUsuarioException;
import Interfaces.IClienteDAO;
import Interfaces.IUsuarioDAO;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import entidades.Cliente;
import entidades.Compra;
import entidades.Pago;
import entidades.Usuario;
import enums.EstadoUsuario;
import enums.Rol;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

/**
 *
 * @author sonic
 */
public class ClienteDAOTest {

    private final IClienteDAO clienteDAO = ClienteDAO.getInstance();
    private final MongoConexion conexion = new MongoConexion();

    public ClienteDAOTest() {
    }

    @BeforeEach
    public void agregarCliente() {
        MongoClient clienteMongo = conexion.crearConexion();
        MongoDatabase base = conexion.obtenerBaseDatos(clienteMongo);
        MongoCollection<Cliente> coleccion = base.getCollection("Usuarios", Cliente.class);

        Cliente cliente1 = new Cliente();
        cliente1.setNombre("Abraham");
        cliente1.setApellidoPaterno("Coronel");
        cliente1.setApellidoMaterno("Bringas");
        cliente1.setNombreDeUsuario("yizbin");
        cliente1.setCorreoElectronico("yizbin@gmail.com");
        cliente1.setContrasenia("1234");
        cliente1.setRol(Rol.CLIENTE);
        cliente1.setEstado(EstadoUsuario.ACTIVO);
        cliente1.setTelefono("6442596969");
        cliente1.setFechaNacimiento(LocalDate.of(2005, Month.SEPTEMBER, 11).atStartOfDay());
        cliente1.setNumero("12356");
        cliente1.setCP("1763");
        cliente1.setCalle("Calle loca");

        coleccion.insertOne(cliente1);
        conexion.cerrarConexion(clienteMongo);
    }

    @AfterEach
    private void limpiarColecciones() {
        MongoClient clienteMongo = conexion.crearConexion();
        MongoDatabase base = conexion.obtenerBaseDatos(clienteMongo);
        MongoCollection<Cliente> coleccion = base.getCollection("Usuarios", Cliente.class);
        MongoCollection<Compra> coleccion2 = base.getCollection("Compras", Compra.class);
        coleccion.deleteMany(new Document());
        coleccion2.deleteMany(new Document());
        conexion.cerrarConexion(clienteMongo);
    }

    /**
     * Test of registrarCliente method, of class ClienteDAO.
     */
    @Test
    public void testRegistrarClienteExito() {
        try {
            Cliente cliente1 = new Cliente();
            cliente1.setNombre("Abraham");
            cliente1.setApellidoPaterno("Coronel");
            cliente1.setApellidoMaterno("Bringas");
            cliente1.setNombreDeUsuario("carlosp");
            cliente1.setCorreoElectronico("lol@gmail.com");
            cliente1.setContrasenia("1234");
            cliente1.setRol(Rol.CLIENTE);
            cliente1.setEstado(EstadoUsuario.ACTIVO);
            cliente1.setTelefono("6442599999");
            cliente1.setFechaNacimiento(LocalDate.of(2005, Month.SEPTEMBER, 11).atStartOfDay());
            cliente1.setNumero("12356");
            cliente1.setCP("1763");
            cliente1.setCalle("Calle loca");

            Cliente registrado = clienteDAO.registrarCliente(cliente1);

            assertNotNull(registrado);
            assertEquals("carlosp", registrado.getNombreDeUsuario());

        } catch (RegistrarClienteException e) {
            fail("No se esperaba excepción al registrar cliente: " + e.getMessage());
        }
    }

    @Test
    public void testRegistrarClienteUsuarioRepetido() {
        Cliente nuevoCliente = new Cliente();
        nuevoCliente.setNombre("Carlos");
        nuevoCliente.setApellidoPaterno("Perez");
        nuevoCliente.setApellidoMaterno("Lopez");
        nuevoCliente.setNombreDeUsuario("yizbin");
        nuevoCliente.setCorreoElectronico("perro@example.com");
        nuevoCliente.setContrasenia("contrasenia123");
        nuevoCliente.setTelefono("8989898989");
        nuevoCliente.setEstado(EstadoUsuario.ACTIVO);
        nuevoCliente.setFechaNacimiento(LocalDate.of(2005, Month.SEPTEMBER, 1).atStartOfDay());
        nuevoCliente.setRol(Rol.CLIENTE);
        nuevoCliente.setNumero("12356");
        nuevoCliente.setCP("1763");
        nuevoCliente.setCalle("Calle chila");

        assertThrows(RegistrarClienteException.class, () -> {
            clienteDAO.registrarCliente(nuevoCliente);
        });
    }

    @Test
    public void testRegistrarClienteCorreoRepetido() {
        Cliente nuevoCliente = new Cliente();
        nuevoCliente.setNombre("Carlos");
        nuevoCliente.setApellidoPaterno("Perez");
        nuevoCliente.setApellidoMaterno("Lopez");
        nuevoCliente.setNombreDeUsuario("murcio");
        nuevoCliente.setCorreoElectronico("yizbin@gmail.com");
        nuevoCliente.setContrasenia("contrasenia123");
        nuevoCliente.setTelefono("1212121212");
        nuevoCliente.setEstado(EstadoUsuario.ACTIVO);
        nuevoCliente.setFechaNacimiento(LocalDate.of(2005, Month.SEPTEMBER, 1).atStartOfDay());
        nuevoCliente.setRol(Rol.CLIENTE);
        nuevoCliente.setNumero("12356");
        nuevoCliente.setCP("1763");
        nuevoCliente.setCalle("Calle chila");

        assertThrows(RegistrarClienteException.class, () -> {
            clienteDAO.registrarCliente(nuevoCliente);
        });
    }

    @Test
    public void testRegistrarClienteTelefonoRepetido() {
        Cliente nuevoCliente = new Cliente();
        nuevoCliente.setNombre("Carlos");
        nuevoCliente.setApellidoPaterno("Perez");
        nuevoCliente.setApellidoMaterno("Lopez");
        nuevoCliente.setNombreDeUsuario("cangrejo");
        nuevoCliente.setCorreoElectronico("poipoi@gmail.com");
        nuevoCliente.setContrasenia("contrasenia123");
        nuevoCliente.setTelefono("6442596969");
        nuevoCliente.setEstado(EstadoUsuario.ACTIVO);
        nuevoCliente.setFechaNacimiento(LocalDate.of(2005, Month.SEPTEMBER, 1).atStartOfDay());
        nuevoCliente.setRol(Rol.CLIENTE);
        nuevoCliente.setNumero("12356");
        nuevoCliente.setCP("1763");
        nuevoCliente.setCalle("Calle chila");

        assertThrows(RegistrarClienteException.class, () -> {
            clienteDAO.registrarCliente(nuevoCliente);
        });
    }

    /**
     * Test of actualizarCliente method, of class ClienteDAO.
     */
    @Test
    public void testActualizarClienteExito() {
        MongoClient clienteMongo = conexion.crearConexion();
        MongoDatabase base = conexion.obtenerBaseDatos(clienteMongo);

        try {
            Cliente original = clienteDAO.obtenerCliente("yizbin", "1234");

            original.setCorreoElectronico("minecraft@gmail.com");

            Cliente actualizado = clienteDAO.actualizarCliente(original);

            assertNotNull(actualizado);
            assertEquals("minecraft@gmail.com", actualizado.getCorreoElectronico());

        } catch (ActualizarClienteException e) {
            fail("No se esperaba excepción al actualizar cliente: " + e.getMessage());
        } catch (EncontrarClienteException e) {
            fail("No se esperaba excepción al encontrar cliente: " + e.getMessage());
        }
    }

    @Test
    public void testActualizarNombreUsuarioRepetido() {
        try {
            // Cliente 1
            Cliente cliente1 = new Cliente();
            cliente1.setNombre("Carlos");
            cliente1.setApellidoPaterno("Lopez");
            cliente1.setApellidoMaterno("Garcia");
            cliente1.setNombreDeUsuario("carlos123");
            cliente1.setCorreoElectronico("carlos@example.com");
            cliente1.setContrasenia("pass1");
            cliente1.setTelefono("1234567890");
            cliente1.setEstado(EstadoUsuario.ACTIVO);
            cliente1.setFechaNacimiento(LocalDate.of(2005, Month.SEPTEMBER, 20).atStartOfDay());
            cliente1.setRol(Rol.CLIENTE);
            cliente1.setNumero("12356");
            cliente1.setCP("1763");
            cliente1.setCalle("Calle norte");

            // Cliente 2
            Cliente cliente2 = new Cliente();
            cliente2.setNombre("Lucia");
            cliente2.setApellidoPaterno("Martinez");
            cliente2.setApellidoMaterno("Sanchez");
            cliente2.setNombreDeUsuario("lucia456");
            cliente2.setCorreoElectronico("lucia@example.com");
            cliente2.setContrasenia("pass2");
            cliente2.setTelefono("0987654321");
            cliente2.setEstado(EstadoUsuario.ACTIVO);
            cliente2.setFechaNacimiento(LocalDate.of(2005, Month.SEPTEMBER, 2).atStartOfDay());
            cliente2.setRol(Rol.CLIENTE);
            cliente2.setNumero("12356");
            cliente2.setCP("1763");
            cliente2.setCalle("Calle sur");

            // Registrar ambos clientes
            clienteDAO.registrarCliente(cliente1);
            clienteDAO.registrarCliente(cliente2);

            // Intentar actualizar cliente2 con nombreDeUsuario duplicado (ya usado por cliente1)
            cliente2.setNombreDeUsuario("carlos123");

            assertThrows(ActualizarClienteException.class, () -> {
                clienteDAO.actualizarCliente(cliente2);
            });

        } catch (Exception e) {
            fail("No se esperaba excepción durante la actualizacion: " + e.getMessage());
        }
    }

    @Test
    public void testActualizarNombreCorreoRepetido() {
        try {
            // Cliente 1
            Cliente cliente1 = new Cliente();
            cliente1.setNombre("Carlos");
            cliente1.setApellidoPaterno("Lopez");
            cliente1.setApellidoMaterno("Garcia");
            cliente1.setNombreDeUsuario("carlos123");
            cliente1.setCorreoElectronico("carlos@example.com");
            cliente1.setContrasenia("pass1");
            cliente1.setTelefono("1234567890");
            cliente1.setEstado(EstadoUsuario.ACTIVO);
            cliente1.setFechaNacimiento(LocalDate.of(2005, Month.SEPTEMBER, 20).atStartOfDay());
            cliente1.setRol(Rol.CLIENTE);
            cliente1.setNumero("12356");
            cliente1.setCP("1763");
            cliente1.setCalle("Calle norte");

            // Cliente 2
            Cliente cliente2 = new Cliente();
            cliente2.setNombre("Lucia");
            cliente2.setApellidoPaterno("Martinez");
            cliente2.setApellidoMaterno("Sanchez");
            cliente2.setNombreDeUsuario("lucia456");
            cliente2.setCorreoElectronico("lucia@example.com");
            cliente2.setContrasenia("pass2");
            cliente2.setTelefono("0987654321");
            cliente2.setEstado(EstadoUsuario.ACTIVO);
            cliente2.setFechaNacimiento(LocalDate.of(2005, Month.SEPTEMBER, 2).atStartOfDay());
            cliente2.setRol(Rol.CLIENTE);
            cliente2.setNumero("12356");
            cliente2.setCP("1763");
            cliente2.setCalle("Calle sur");

            // Registrar ambos clientes
            clienteDAO.registrarCliente(cliente1);
            clienteDAO.registrarCliente(cliente2);

            // Intentar actualizar cliente2 con correo duplicado (ya usado por cliente1)
            cliente2.setCorreoElectronico("carlos@example.com");

            assertThrows(ActualizarClienteException.class, () -> {
                clienteDAO.actualizarCliente(cliente2);
            });

        } catch (Exception e) {
            fail("No se esperaba excepción durante la actualizacion: " + e.getMessage());
        }
    }

    @Test
    public void testActualizarTelefonoCorreoRepetido() {
        try {
            // Cliente 1
            Cliente cliente1 = new Cliente();
            cliente1.setNombre("Carlos");
            cliente1.setApellidoPaterno("Lopez");
            cliente1.setApellidoMaterno("Garcia");
            cliente1.setNombreDeUsuario("carlos123");
            cliente1.setCorreoElectronico("carlos@example.com");
            cliente1.setContrasenia("pass1");
            cliente1.setTelefono("1234567890");
            cliente1.setEstado(EstadoUsuario.ACTIVO);
            cliente1.setFechaNacimiento(LocalDate.of(2005, Month.SEPTEMBER, 20).atStartOfDay());
            cliente1.setRol(Rol.CLIENTE);
            cliente1.setNumero("12356");
            cliente1.setCP("1763");
            cliente1.setCalle("Calle norte");

            // Cliente 2
            Cliente cliente2 = new Cliente();
            cliente2.setNombre("Lucia");
            cliente2.setApellidoPaterno("Martinez");
            cliente2.setApellidoMaterno("Sanchez");
            cliente2.setNombreDeUsuario("lucia456");
            cliente2.setCorreoElectronico("lucia@example.com");
            cliente2.setContrasenia("pass2");
            cliente2.setTelefono("0987654321");
            cliente2.setEstado(EstadoUsuario.ACTIVO);
            cliente2.setFechaNacimiento(LocalDate.of(2005, Month.SEPTEMBER, 2).atStartOfDay());
            cliente2.setRol(Rol.CLIENTE);
            cliente2.setNumero("12356");
            cliente2.setCP("1763");
            cliente2.setCalle("Calle sur");

            // Registrar ambos clientes
            clienteDAO.registrarCliente(cliente1);
            clienteDAO.registrarCliente(cliente2);

            // Intentar actualizar cliente2 con telefono duplicado (ya usado por cliente1)
            cliente2.setTelefono("1234567890");

            assertThrows(ActualizarClienteException.class, () -> {
                clienteDAO.actualizarCliente(cliente2);
            });

        } catch (Exception e) {
            fail("No se esperaba excepción durante la actualizacion: " + e.getMessage());
        }
    }

    /**
     * Test of obtenerCliente method, of class ClienteDAO.
     */
    @Test
    public void testObtenerClienteExito() {
        try {

            // Obtener
            Cliente encontrado = clienteDAO.obtenerCliente("yizbin", "1234");

            assertNotNull(encontrado);
            assertEquals("yizbin", encontrado.getNombreDeUsuario());

        } catch (EncontrarClienteException e) {
            fail("No se esperaba excepción al obtener un cliente: " + e.getMessage());
        }
    }

    @Test
    public void testObtenerClienteFallido() {
        try {

            // Obtener con una constraseña que es incorrecta
            Cliente encontrado = clienteDAO.obtenerCliente("yizbin", "nose xd");

            assertNull(encontrado);

        } catch (EncontrarClienteException e) {
            fail("No se esperaba excepción al obtener un cliente: " + e.getMessage());
        }
    }

//    /**
//     * Test of cargarHistorialCompras method, of class ClienteDAO.
//     */
//    @Test
//    public void testCargarHistorialComprasExito() {
//        try {
//            Cliente cliente = new Cliente();
//            cliente.setNombreDeUsuario("jaime");
//
//            Pago pago = new Pago();
//            pago.setEstado(Boolean.TRUE);
//            pago.setFechaHora(LocalDateTime.now());
//            pago.setMonto(150.0);
//
//            Compra compra = new Compra();
//            compra.setUsuarioCliente("jaime");
//            compra.setFecha(LocalDateTime.now());
//            compra.setPago(pago);
//
//            // Insertar la compra manualmente en la coleccion de compras
//            MongoClient clienteMongo = conexion.crearConexion();
//            MongoDatabase base = conexion.obtenerBaseDatos(clienteMongo);
//            MongoCollection<Compra> coleccion = base.getCollection("Compras", Compra.class);
//            coleccion.insertOne(compra);
//            conexion.cerrarConexion(clienteMongo);
//
//            List<Compra> historial = clienteDAO.cargarHistorialCompras(cliente);
//
//            assertNotNull(historial);
//            assertFalse(historial.isEmpty());
//            assertEquals("jaime", historial.get(0).getUsuarioCliente());
//
//        } catch (CargarHistorialException e) {
//            fail("No se esperaba excepción al cargar el historial: " + e.getMessage());
//        }
//    }
//
//    @Test
//    public void testCargarHistorialComprasFallido() {
//        try {
//            Cliente cliente = new Cliente();
//            cliente.setNombreDeUsuario("clienteConCompras");
//
//            List<Compra> listaCompras = clienteDAO.cargarHistorialCompras(cliente);
//            
//            assertTrue(listaCompras.isEmpty());
//
//        } catch (CargarHistorialException e) {
//            fail("No se esperaba excepción al cargar el historial: " + e.getMessage());
//        }
//    }
}
