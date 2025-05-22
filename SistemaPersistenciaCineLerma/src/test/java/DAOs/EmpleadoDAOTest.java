/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package DAOs;

import Conexion.MongoConexion;
import Excepciones.empleados.DAOActualizarEmpleadoException;
import Excepciones.empleados.DAOObtenerEmpleadoException;
import Excepciones.empleados.DAORegistrarEmpleadoException;
import Excepciones.empleados.DAOValidacionEmpleadoException;
import Interfaces.IEmpleadoDAO;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import entidades.Empleado;
import enums.Cargo;
import static enums.Cargo.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author isaac
 */
public class EmpleadoDAOTest {

    // --- Atributos de la Clase de Test ---
    private final IEmpleadoDAO empleadoDAO = EmpleadoDAO.getInstance(); // DAO bajo prueba
    private final MongoConexion conexion = new MongoConexion();     // Para limpieza de BD

    // Empleados de prueba que se configurarán en @BeforeEach y estarán disponibles para todos los tests
    private Empleado empleadoSetup1;
    private String empleadoSetup1IdString; // ID del empleadoSetup1 como String

    private Empleado empleadoSetup2;
    private String empleadoSetup2IdString; // ID del empleadoSetup2 como String

    // --- Configuración y Limpieza ---
    @BeforeEach
    public void setUp() {
        // 1. Limpiar la colección ANTES de insertar nuevos datos
        MongoClient clienteMongoConexion = conexion.crearConexion();
        MongoDatabase baseDatos = conexion.obtenerBaseDatos(clienteMongoConexion);
        MongoCollection<Document> coleccionParaLimpieza = baseDatos.getCollection("empleados");
        coleccionParaLimpieza.deleteMany(new Document());
        MongoCollection<Empleado> coleccionEmpleados = baseDatos.getCollection("empleados", Empleado.class);

        // 2. Crear y registrar Empleado 1 (Activo)
        empleadoSetup1 = new Empleado();
        empleadoSetup1.setId(new ObjectId()); // Asignamos ID para tenerlo antes de insertar
        empleadoSetup1IdString = empleadoSetup1.getId().toHexString();
        empleadoSetup1.setNombre("Diana");
        empleadoSetup1.setApellidoP("Prince");
        empleadoSetup1.setApellidoM("Themyscira");
        empleadoSetup1.setCorreoE("diana.prince@example.com"); // Correo único
        empleadoSetup1.setTelefono("555000111");
        empleadoSetup1.setCargo(Cargo.GERENTE);
        empleadoSetup1.setSueldo(75000.0);
        empleadoSetup1.setFechaNacimiento(LocalDateTime.of(1980, Month.MARCH, 22, 0, 0));
        empleadoSetup1.setActivo(true); // Explícitamente activo
        empleadoSetup1.setCalle("Isla Paraiso");
        empleadoSetup1.setColonia("Amazonas");
        empleadoSetup1.setNumExterior("001");
        coleccionEmpleados.insertOne(empleadoSetup1); // Inserción directa

        // 3. Crear y registrar Empleado 2 (Activo)
        empleadoSetup2 = new Empleado();
        empleadoSetup2.setId(new ObjectId());
        empleadoSetup2IdString = empleadoSetup2.getId().toHexString();
        empleadoSetup2.setNombre("Bruce");
        empleadoSetup2.setApellidoP("Wayne");
        empleadoSetup2.setApellidoM("");
        empleadoSetup2.setCorreoE("bruce.wayne@example.com"); // Correo único
        empleadoSetup2.setTelefono("555000222");
        empleadoSetup2.setCargo(Cargo.JEFE_PISO);
        empleadoSetup2.setSueldo(60000.0);
        empleadoSetup2.setFechaNacimiento(LocalDateTime.of(1975, Month.APRIL, 17, 0, 0));
        empleadoSetup2.setActivo(true); // Explícitamente activo
        empleadoSetup2.setCalle("Wayne Manor");
        empleadoSetup2.setColonia("Ciudad Gotica");
        empleadoSetup2.setNumExterior("1007");
        coleccionEmpleados.insertOne(empleadoSetup2); // Inserción directa

        // Cerrar la conexión usada para el setup
        conexion.cerrarConexion(clienteMongoConexion);
    }

    @AfterEach
    public void tearDown() {
        MongoClient clienteMongoConexion = conexion.crearConexion();
        MongoDatabase baseDatos = conexion.obtenerBaseDatos(clienteMongoConexion);
        MongoCollection<Document> coleccionParaLimpieza = baseDatos.getCollection("empleados");
        coleccionParaLimpieza.deleteMany(new Document());
        conexion.cerrarConexion(clienteMongoConexion);
    }

    // --- Métodos de Prueba ---
    @Test
    public void testGetInstance() {
        System.out.println("test: getInstance");
        IEmpleadoDAO instance1 = EmpleadoDAO.getInstance();
        IEmpleadoDAO instance2 = EmpleadoDAO.getInstance();
        assertNotNull(instance1, "La primera instancia no debe ser nula.");
        assertSame(instance1, instance2, "Ambas llamadas a getInstance deben retornar la misma instancia.");
    }

    @Test
    public void testRegistrarEmpleado() throws DAOObtenerEmpleadoException, DAOValidacionEmpleadoException {
        System.out.println("test: registrarEmpleado_Exito");
        Empleado empleadoNuevo = new Empleado();
        // El DAO debe generar el ObjectId si empleadoNuevo.getId() es null
        empleadoNuevo.setNombre("Barry");
        empleadoNuevo.setApellidoP("Allen");
        empleadoNuevo.setApellidoM("West");
        empleadoNuevo.setCorreoE("barry.allen@example.com"); // Correo único para este test
        empleadoNuevo.setCargo(Cargo.CAJERO);
        empleadoNuevo.setSueldo(35000.0);
        empleadoNuevo.setTelefono("555000444");
        empleadoNuevo.setFechaNacimiento(LocalDateTime.of(1990, Month.SEPTEMBER, 30, 0, 0));
        // El método registrarEmpleado del DAO se encarga de 'activo' y 'fechaRegistro'
        empleadoNuevo.setCalle("Central City ST");
        empleadoNuevo.setColonia("Flashpoint");
        empleadoNuevo.setNumExterior("STAR01");

        try {
            boolean resultado = empleadoDAO.registrarEmpleado(empleadoNuevo);
            assertTrue(resultado, "El registro del empleado debería ser exitoso.");
            assertNotNull(empleadoNuevo.getId(), "El empleado debe tener un ID (ObjectId) asignado después del registro.");
            assertTrue(empleadoNuevo.isActivo(), "El empleado debería estar activo por defecto (según lógica del DAO).");
            assertNotNull(empleadoNuevo.getFechaRegistro(), "La fecha de registro no debe ser nula (según lógica del DAO).");

            Empleado recuperado = empleadoDAO.obtenerEmpleadoPorIdInterno(empleadoNuevo.getId().toHexString());
            assertNotNull(recuperado, "El empleado debería poder recuperarse de la BD.");
            assertEquals("Barry", recuperado.getNombre());
            assertEquals("barry.allen@example.com", recuperado.getCorreoE());

        } catch (DAORegistrarEmpleadoException e) {
            fail("No se esperaba DAORegistrarEmpleadoException durante un registro válido: " + e.getMessage(), e);
        }
    }

    @Test
    
    public void testObtenerTodosLosEmpleadosActivos() throws DAOObtenerEmpleadoException {
        System.out.println("test: obtenerTodosLosEmpleadosActivos_ConResultados");
        // En setUp se insertaron 2 empleados activos
        List<Empleado> activos = empleadoDAO.obtenerTodosLosEmpleadosActivos();

        assertNotNull(activos, "La lista no debería ser nula.");
        assertEquals(2, activos.size(), "Debería haber 2 empleados activos del setup.");
        assertTrue(activos.stream().allMatch(Empleado::isActivo), "Todos los empleados en la lista deberían estar activos.");

        assertTrue(activos.stream().anyMatch(e -> e.getId().toHexString().equals(empleadoSetup1IdString)), "EmpleadoSetup1 no encontrado.");
        assertTrue(activos.stream().anyMatch(e -> e.getId().toHexString().equals(empleadoSetup2IdString)), "EmpleadoSetup2 no encontrado.");
    }

    @Test
    public void testObtenerEmpleadoActivoPorId() throws DAOObtenerEmpleadoException, DAOValidacionEmpleadoException {
        System.out.println("test: obtenerEmpleadoActivoPorId_Exito");
        Empleado encontrado = empleadoDAO.obtenerEmpleadoActivoPorId(empleadoSetup1IdString); // USA ID del setup

        assertNotNull(encontrado, "Se debería encontrar el empleado activo.");
        assertEquals(empleadoSetup1IdString, encontrado.getId().toHexString(), "El ID del empleado encontrado no coincide.");
        assertEquals(empleadoSetup1.getNombre(), encontrado.getNombre(), "El nombre no coincide.");
        assertTrue(encontrado.isActivo(), "El empleado encontrado debería estar activo.");
    }
    
    @Test
    public void testActualizarEmpleado() throws DAOObtenerEmpleadoException, DAOValidacionEmpleadoException, DAOActualizarEmpleadoException {
        System.out.println("test: actualizarEmpleado_Exito");
        Empleado paraActualizar = empleadoDAO.obtenerEmpleadoPorIdInterno(empleadoSetup1IdString);
        assertNotNull(paraActualizar, "El empleado a actualizar debe existir.");

        String nombreOriginal = paraActualizar.getNombre();
        String nuevoNombre = "Diana Prince (Wonder Woman)";
        String nuevoTelefono = "5558889999";
        paraActualizar.setNombre(nuevoNombre);
        paraActualizar.setTelefono(nuevoTelefono);
        // No se cambia el ID, activo, fechaRegistro, cargo, sueldo aquí (se prueban en otros tests)

        boolean resultado = empleadoDAO.actualizarEmpleado(paraActualizar);
        assertTrue(resultado, "La actualización debería ser exitosa.");

        Empleado actualizado = empleadoDAO.obtenerEmpleadoPorIdInterno(empleadoSetup1IdString);
        assertNotNull(actualizado);
        assertEquals(nuevoNombre, actualizado.getNombre());
        assertEquals(nuevoTelefono, actualizado.getTelefono());
        assertEquals(empleadoSetup1.getCorreoE(), actualizado.getCorreoE(), "El correo no debería haber cambiado."); // Ejemplo de campo que no cambia
    }
    
    @Test
    public void testDespedirEmpleado_Exito() throws Exception {
        System.out.println("test: despedirEmpleado_Exito");
        assertTrue(empleadoDAO.obtenerEmpleadoPorIdInterno(empleadoSetup1IdString).isActivo(), "Empleado debe estar activo antes.");
        
        boolean resultado = empleadoDAO.despedirEmpleado(empleadoSetup1IdString);
        assertTrue(resultado, "Despedir empleado activo debería ser exitoso.");

        Empleado despedido = empleadoDAO.obtenerEmpleadoPorIdInterno(empleadoSetup1IdString);
        assertNotNull(despedido);
        assertFalse(despedido.isActivo(), "El empleado debería estar inactivo después de ser despedido.");
    }
    
    @Test
    public void testObtenerEmpleadosActivosPorCargo_ConResultados() throws DAOObtenerEmpleadoException, DAOValidacionEmpleadoException {
        System.out.println("test: obtenerEmpleadosActivosPorCargo_ConResultados");
        // empleadoSetup1 es GERENTE, empleadoSetup2 es JEFE_PISO
        List<Empleado> gerentes = empleadoDAO.obtenerEmpleadosActivosPorCargo(Cargo.GERENTE);
        assertNotNull(gerentes);
        assertEquals(1, gerentes.size());
        assertEquals(empleadoSetup1IdString, gerentes.get(0).getId().toHexString());

        List<Empleado> jefesDePiso = empleadoDAO.obtenerEmpleadosActivosPorCargo(Cargo.JEFE_PISO);
        assertNotNull(jefesDePiso);
        assertEquals(1, jefesDePiso.size());
        assertEquals(empleadoSetup2IdString, jefesDePiso.get(0).getId().toHexString());
    }
    
    
    @Test
    public void testObtenerEmpleadosActivosPorCargo_SinResultados() throws DAOObtenerEmpleadoException, DAOValidacionEmpleadoException {
        System.out.println("test: obtenerEmpleadosActivosPorCargo_SinResultados");
        List<Empleado> tecnicos = empleadoDAO.obtenerEmpleadosActivosPorCargo(Cargo.TECNICO_SONIDO);
        assertNotNull(tecnicos);
        assertTrue(tecnicos.isEmpty(), "La lista debería estar vacía para un cargo sin empleados activos.");
    }
    
    @Test
    public void testActualizarCargoEmpleado_Exito() throws Exception {
        System.out.println("test: actualizarCargoEmpleado_Exito");
        Cargo nuevoCargo = Cargo.TECNICO_SONIDO;
        boolean resultado = empleadoDAO.actualizarCargoEmpleado(empleadoSetup1IdString, nuevoCargo);
        assertTrue(resultado, "La actualización de cargo debería ser exitosa.");

        Empleado actualizado = empleadoDAO.obtenerEmpleadoPorIdInterno(empleadoSetup1IdString);
        assertNotNull(actualizado);
        assertEquals(nuevoCargo, actualizado.getCargo());
    }
    
    
    @Test
    public void testActualizarSueldoIndividual() throws Exception {
        System.out.println("test: actualizarSueldoIndividual_Exito");
        Double nuevoSueldo = 80000.0;
        boolean resultado = empleadoDAO.actualizarSueldoIndividual(empleadoSetup1IdString, nuevoSueldo);
        assertTrue(resultado, "La actualización de sueldo debería ser exitosa.");

        Empleado actualizado = empleadoDAO.obtenerEmpleadoPorIdInterno(empleadoSetup1IdString);
        assertNotNull(actualizado);
        assertEquals(nuevoSueldo, actualizado.getSueldo(), 0.001); // Delta para doubles
    }
    
    
    
    @Test
    public void testActualizarSueldoPorCargo() throws Exception {
        System.out.println("test: actualizarSueldoPorCargo_Exito");
        // empleadoSetup2 es JEFE_PISO. Vamos a añadir otro JEFE_PISO.
        Empleado otroJefePiso = new Empleado();
        otroJefePiso.setId(new ObjectId());
        otroJefePiso.setNombre("JefeAdicional");
        otroJefePiso.setCorreoE("jefeadicional@example.com");
        otroJefePiso.setCargo(Cargo.JEFE_PISO);
        otroJefePiso.setActivo(true);
        otroJefePiso.setSueldo(40000.0);
        // ... (llenar otros campos obligatorios para insertar)
        otroJefePiso.setApellidoP("A"); otroJefePiso.setApellidoM("B"); otroJefePiso.setTelefono("123");
        otroJefePiso.setFechaNacimiento(LocalDateTime.now().minusYears(30));
        otroJefePiso.setCalle("C"); otroJefePiso.setColonia("O"); otroJefePiso.setNumExterior("N");
        
        MongoClient mc = conexion.crearConexion();
        mc.getDatabase(conexion.obtenerBaseDatos(mc).getName()).getCollection("empleados", Empleado.class).insertOne(otroJefePiso);
        conexion.cerrarConexion(mc);


        Double nuevoSueldo = 55000.0;
        long actualizados = empleadoDAO.actualizarSueldoPorCargo(Cargo.JEFE_PISO, nuevoSueldo);
        assertEquals(2, actualizados, "Deberían haberse actualizado 2 empleados JEFE_PISO.");

        Empleado jefe1Actualizado = empleadoDAO.obtenerEmpleadoPorIdInterno(empleadoSetup2IdString);
        Empleado jefe2Actualizado = empleadoDAO.obtenerEmpleadoPorIdInterno(otroJefePiso.getId().toHexString());

        assertNotNull(jefe1Actualizado);
        assertEquals(nuevoSueldo, jefe1Actualizado.getSueldo(), 0.001);
        assertNotNull(jefe2Actualizado);
        assertEquals(nuevoSueldo, jefe2Actualizado.getSueldo(), 0.001);
    }
    
    
    
    
    
    
    
    

    
    
    
}
