/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package DAOs;

import Conexion.MongoConexion;
import Excepciones.usuarios.EncontrarAdministradorException;
import Excepciones.usuarios.RegistrarAministradorException;
import Excepciones.usuarios.RegistrarClienteException;
import Interfaces.IAdministradorDAO;
import Interfaces.IClienteDAO;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import entidades.Administrador;
import entidades.Cliente;
import entidades.Compra;
import enums.EstadoUsuario;
import enums.Rol;
import java.time.LocalDate;
import java.time.Month;
import org.bson.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

/**
 *
 * @author sonic
 */
public class AdministradorDAOTest {

    private final IAdministradorDAO administradorDAO = AdministradorDAO.getInstance();
    private final MongoConexion conexion = new MongoConexion();

    public AdministradorDAOTest() {
    }

    @BeforeEach
    public void agregarAdministrador() {
        MongoClient clienteMongo = conexion.crearConexion();
        MongoDatabase base = conexion.obtenerBaseDatos(clienteMongo);
        MongoCollection<Administrador> coleccion = base.getCollection("Usuarios", Administrador.class);

        Administrador admin1 = new Administrador();
        admin1.setNombre("Gatito");
        admin1.setApellidoPaterno("tito");
        admin1.setApellidoMaterno("porris");
        admin1.setNombreDeUsuario("camada");
        admin1.setCorreoElectronico("gato@gmail.com");
        admin1.setContrasenia("zanahoria");
        admin1.setRol(Rol.ADMINISTRADOR);
        admin1.setEstado(EstadoUsuario.ACTIVO);
        admin1.setTelefono("6996969696");
        admin1.setFechaNacimiento(LocalDate.of(2005, Month.SEPTEMBER, 11).atStartOfDay());
        admin1.setRFC("LOL123151515");

        coleccion.insertOne(admin1);
        conexion.cerrarConexion(clienteMongo);
    }

    @AfterEach
    private void limpiarColecciones() {
        MongoClient clienteMongo = conexion.crearConexion();
        MongoDatabase base = conexion.obtenerBaseDatos(clienteMongo);
        MongoCollection<Administrador> coleccion = base.getCollection("Usuarios", Administrador.class);
        coleccion.deleteMany(new Document());
        conexion.cerrarConexion(clienteMongo);
    }

    /**
     * Test of registrarAdministrador method, of class AdministradorDAO.
     */
    @Test
    public void testRegistrarAdministrador() {
        try {
            Administrador admin = new Administrador();
            admin.setNombre("Perrito");
            admin.setApellidoPaterno("gatito");
            admin.setApellidoMaterno("morrison");
            admin.setNombreDeUsuario("master");
            admin.setCorreoElectronico("perroloco@gmail.com");
            admin.setContrasenia("croqueta");
            admin.setRol(Rol.ADMINISTRADOR);
            admin.setEstado(EstadoUsuario.ACTIVO);
            admin.setTelefono("7979797979");
            admin.setFechaNacimiento(LocalDate.of(2005, Month.SEPTEMBER, 11).atStartOfDay());
            admin.setRFC("MEAC123563");

            Administrador registrado = administradorDAO.registrarAdministrador(admin);

            assertNotNull(registrado);
            assertEquals("master", registrado.getNombreDeUsuario());

        } catch (RegistrarAministradorException e) {
            fail("No se esperaba excepción al registrar administrador: " + e.getMessage());
        }
    }
    
    @Test
    public void testRegistrarAdministradorUsuarioRepetido() {
            Administrador admin = new Administrador();
            admin.setNombre("Perrito");
            admin.setApellidoPaterno("gatito");
            admin.setApellidoMaterno("morrison");
            admin.setNombreDeUsuario("camada");
            admin.setCorreoElectronico("perroloco@gmail.com");
            admin.setContrasenia("croqueta");
            admin.setRol(Rol.ADMINISTRADOR);
            admin.setEstado(EstadoUsuario.ACTIVO);
            admin.setTelefono("7979797979");
            admin.setFechaNacimiento(LocalDate.of(2005, Month.SEPTEMBER, 11).atStartOfDay());
            admin.setRFC("MEAC123563");

            assertThrows(RegistrarAministradorException.class, () -> {
            administradorDAO.registrarAdministrador(admin);
        });
    }
    
    @Test
    public void testRegistrarAdministradorCorreoRepetido() {
            Administrador admin = new Administrador();
            admin.setNombre("Perrito");
            admin.setApellidoPaterno("gatito");
            admin.setApellidoMaterno("morrison");
            admin.setNombreDeUsuario("master");
            admin.setCorreoElectronico("gato@gmail.com");
            admin.setContrasenia("croqueta");
            admin.setRol(Rol.ADMINISTRADOR);
            admin.setEstado(EstadoUsuario.ACTIVO);
            admin.setTelefono("7979797979");
            admin.setFechaNacimiento(LocalDate.of(2005, Month.SEPTEMBER, 11).atStartOfDay());
            admin.setRFC("MEAC123563");

            assertThrows(RegistrarAministradorException.class, () -> {
            administradorDAO.registrarAdministrador(admin);
        });
    }
    
    @Test
    public void testRegistrarAdministradorTelefonoRepetido() {
            Administrador admin = new Administrador();
            admin.setNombre("Perrito");
            admin.setApellidoPaterno("gatito");
            admin.setApellidoMaterno("morrison");
            admin.setNombreDeUsuario("master");
            admin.setCorreoElectronico("perroloco@gmail.com");
            admin.setContrasenia("croqueta");
            admin.setRol(Rol.ADMINISTRADOR);
            admin.setEstado(EstadoUsuario.ACTIVO);
            admin.setTelefono("6996969696");
            admin.setFechaNacimiento(LocalDate.of(2005, Month.SEPTEMBER, 11).atStartOfDay());
            admin.setRFC("MEAC123563");

            assertThrows(RegistrarAministradorException.class, () -> {
            administradorDAO.registrarAdministrador(admin);
        });
    }

    /**
     * Test of obtenerAdministrador method, of class AdministradorDAO.
     */
    @Test
    public void testObtenerAdministradorExito() {
        try {

            // Obtener
            Administrador encontrado = administradorDAO.obtenerAdministrador("camada", "zanahoria");

            assertNotNull(encontrado);
            assertEquals("camada", encontrado.getNombreDeUsuario());

        } catch (EncontrarAdministradorException e) {
            fail("No se esperaba excepción al obtener un administrador: " + e.getMessage());
        }
    }

    @Test
    public void testObtenerAdministradorFallido() {
        try {

            // Obtener con una constraseña que es incorrecta
            Administrador encontrado = administradorDAO.obtenerAdministrador("camada", "contrasena generica");

            assertNull(encontrado);

        } catch (EncontrarAdministradorException e) {
            fail("No se esperaba excepción al obtener un administrador: " + e.getMessage());
        }
    }

}
