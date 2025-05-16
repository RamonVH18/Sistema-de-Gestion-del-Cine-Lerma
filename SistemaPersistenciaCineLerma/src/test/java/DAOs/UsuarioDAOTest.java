/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package DAOs;

import Conexion.MongoConexion;
import Excepciones.usuarios.ActualizarUsuarioException;
import Excepciones.usuarios.ObtenerUsuariosException;
import Interfaces.IUsuarioDAO;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;

/**
 *
 * @author sonic
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioDAOTest {

    private final IUsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
    private final MongoConexion conexion = new MongoConexion();

    public UsuarioDAOTest() {
    }

    @BeforeEach
    public void agregarUsuarios() {
        MongoClient clienteMongo = conexion.crearConexion();
        MongoDatabase base = conexion.obtenerBaseDatos(clienteMongo);
        MongoCollection<Usuario> coleccion = base.getCollection("Usuarios", Usuario.class);

        Usuario usuario1 = new Usuario();
        usuario1.setNombre("Sebastian");
        usuario1.setApellidoPaterno("Borquez");
        usuario1.setApellidoMaterno("Huerta");
        usuario1.setNombreDeUsuario("Sebastiano");
        usuario1.setCorreoElectronico("sonic15622@gmail.com");
        usuario1.setContrasenia("Sebas");
        usuario1.setRol(Rol.CLIENTE);
        usuario1.setEstado(EstadoUsuario.ACTIVO);
        usuario1.setTelefono("6442595242");
        usuario1.setFechaNacimiento(LocalDate.of(2005, Month.MAY, 9).atStartOfDay());

        coleccion.insertOne(usuario1);
        conexion.cerrarConexion(clienteMongo);
    }

    @AfterEach
    private void limpiarColeccionUsuarios() {
        MongoClient clienteMongo = conexion.crearConexion();
        MongoDatabase base = conexion.obtenerBaseDatos(clienteMongo);
        MongoCollection<Usuario> coleccion = base.getCollection("Usuarios", Usuario.class);
        coleccion.deleteMany(new Document()); // Borra todos los documentos
        conexion.cerrarConexion(clienteMongo);
    }

    /**
     * Test of mostrarListaUsuarios method, of class UsuarioDAO. Test que
     * muestra la lista de usuarios, para determinar si fue exitoso o no se
     * verifica que la lista obtenida no este vacia
     */
    @Test
    public void testMostrarListaUsuariosPoblada() throws ObtenerUsuariosException {
        List<Usuario> usuarios = usuarioDAO.mostrarListaUsuarios();
        assertTrue(usuarios.size() > 0, "La lista de usuarios no debería estar vacia");
        assertFalse(usuarios.isEmpty(), "La lista de usuarios no esta vacia");
    }

    @Test
    public void testMostrarListaUsuariosVacia() throws ObtenerUsuariosException {
        // se limpia la coleccion para que este vacia la lista que se obtendra
        limpiarColeccionUsuarios();

        List<Usuario> usuarios = usuarioDAO.mostrarListaUsuarios();

        assertNotNull(usuarios, "La lista no debe ser null");
        assertTrue(usuarios.isEmpty(), "La lista debe estar vacía cuando no hay usuarios");

    }

    /**
     * Test of bloquearUsuario method, of class UsuarioDAO.
     */
    @Test
    public void testBloquearUsuario_Exito() {

        try {
            Boolean resultado = usuarioDAO.bloquearUsuario(usuarioDAO.obtenerUsuario("Sebastiano", "Sebas"));
            assertTrue(resultado, "El usuario deberia haberse bloqueado correctamente");

            Usuario actualizado = usuarioDAO.obtenerUsuario("Sebastiano", "Sebas");

            assertEquals(EstadoUsuario.BLOQUEADO.toString(), actualizado.getEstado().toString());
        } catch (ActualizarUsuarioException e) {
            fail("No se esperaba una excepcion al bloquear al usuario: " + e.getMessage());
        } catch (ObtenerUsuariosException e) {
            fail("No se esperaba una excepcion al encontrar al usuario: " + e.getMessage());
        }
    }

    /**
     * Test of desbloquearUsuario method, of class UsuarioDAO.
     */
    @Test
    public void testDesbloquearUsuario() throws Exception {
        try {
            Boolean resultado = usuarioDAO.bloquearUsuario(usuarioDAO.obtenerUsuario("Sebastiano", "Sebas"));
            Boolean resultado2 = usuarioDAO.desbloquearUsuario(usuarioDAO.obtenerUsuario("Sebastiano", "Sebas"));
            assertTrue(resultado2, "El usuario deberia haberse desbloqueado correctamente");

            Usuario actualizado = usuarioDAO.obtenerUsuario("Sebastiano", "Sebas");

            assertEquals(EstadoUsuario.ACTIVO.toString(), actualizado.getEstado().toString());
        } catch (ActualizarUsuarioException e) {
            fail("No se esperaba una excepcion al bloquear al usuario: " + e.getMessage());
        } catch (ObtenerUsuariosException e) {
            fail("No se esperaba una excepcion al encontrar al usuario: " + e.getMessage());
        }
    }

    @Test
    public void testObtenerListaFiltradaExito() throws ObtenerUsuariosException {
        System.out.println("obtener lista de usuarios filtrada completamente");
        try {
            List<Usuario> resultado = usuarioDAO.mostrarListaUsuariosFiltrada(
                    EstadoUsuario.ACTIVO,
                    Rol.CLIENTE,
                    LocalDateTime.of(2000, 1, 1, 0, 0),
                    LocalDateTime.of(2010, 6, 10, 0, 0),
                    "ebas"
            );

            assertNotNull(resultado);
            assertFalse(resultado.isEmpty());

            boolean encontrado = resultado.stream()
                    .anyMatch(u -> u.getNombreDeUsuario().toLowerCase().contains("ebas"));

            assertTrue(encontrado, "Ningún usuario tiene un nombreDeUsuario que contenga ebas");

        } catch (ObtenerUsuariosException e) {
            fail("No se esperaba excepción al encontrar la lista filtrada: " + e.getMessage());
        }
    }

    @Test
    public void testObtenerListaFiltradaFallo() throws ObtenerUsuariosException {
        limpiarColeccionUsuarios();
        try {
            List<Usuario> resultado = usuarioDAO.mostrarListaUsuariosFiltrada(
                    EstadoUsuario.ACTIVO,
                    Rol.CLIENTE,
                    LocalDateTime.of(2000, 1, 1, 0, 0),
                    LocalDateTime.of(2010, 6, 10, 0, 0),
                    "ebas"
            );

            assertNotNull(resultado);
            assertTrue(resultado.isEmpty());

            boolean encontrado = !resultado.stream()
                    .anyMatch(u -> u.getNombreDeUsuario().toLowerCase().contains("ebas"));

            assertTrue(encontrado, "Ningún usuario tiene un nombreDeUsuario que contenga ebas");

        } catch (ObtenerUsuariosException e) {
            fail("No se esperaba excepción al encontrar la lista filtrada: " + e.getMessage());
        }
    }

    /**
     * Test of obtenerUsuario method, of class UsuarioDAO.
     */
    @Test
    public void testObtenerUsuarioExito() throws ObtenerUsuariosException {
        System.out.println("obtener usuario con credenciales validas");

        String nombreUsuario = "Sebastiano";
        String contrasena = "Sebas";
        
        Usuario usuario = usuarioDAO.obtenerUsuario(nombreUsuario, contrasena);

        assertNotNull(usuario);
        assertEquals(nombreUsuario, usuario.getNombreDeUsuario());
    }
    
    /**
     * Test of obtenerUsuario method, of class UsuarioDAO.
     */
    @Test
    public void testObtenerUsuarioFallo() throws ObtenerUsuariosException {
        System.out.println("obtener usuario con credenciales validas");

        String nombreUsuario = "usuarioFalso";
        String contrasena = "contrasenaFalsa";
        
        Usuario usuario = usuarioDAO.obtenerUsuario(nombreUsuario, contrasena);

        assertNull(usuario, "Se esperaba null al no encontrar usuario con esas credenciales");
    }

}
