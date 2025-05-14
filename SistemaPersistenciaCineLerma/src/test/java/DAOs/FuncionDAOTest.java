/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package DAOs;

import Excepciones.Funciones.FuncionNoEncontradaException;
import Excepciones.Funciones.FuncionSalaOcupadaException;
import Excepciones.Funciones.FuncionSalaVaciaException;
import Interfaces.IFuncionDAO;
import entidades.Asiento;
import entidades.Funcion;
import entidades.Pelicula;
import entidades.Sala;
import enums.EstadoSala;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Abraham Coronel Bringas
 */
public class FuncionDAOTest {

    private IFuncionDAO funcionDAO;
    private Funcion funcionPrueba;
    private Pelicula peliculaPrueba;
    private Sala salaPrueba;

    public FuncionDAOTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        funcionDAO = FuncionDAO.getInstanceDAO();
        ObjectId id = new ObjectId();
        peliculaPrueba = new Pelicula(id,"", "PeliculaPrueba", "Accion", 200, "B15", "Sinopsis", true);

        // Inicializar Sala con asientos
        salaPrueba = new Sala(50, "Sala1", EstadoSala.ACTIVA);
        List<Asiento> asientos = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            asientos.add(new Asiento(String.valueOf(i))); // Números como String
        }
        salaPrueba.setAsientos(asientos);

        funcionPrueba = new Funcion(
                new ObjectId(),
                salaPrueba,
                peliculaPrueba,
                LocalDateTime.now().plusHours(1),
                150.0
        );
    }

    @AfterEach
    public void tearDown() {
        try {
            Funcion funcionEnBD = funcionDAO.buscarFuncionId(funcionPrueba.getIdFuncion());
            if (funcionEnBD != null) {
                funcionDAO.eliminarFuncion(funcionPrueba);
            }
        } catch (FuncionNoEncontradaException e) {
            System.out.println("Error en limpieza: " + e.getMessage());
        }
    }

    /**
     * Test of getInstanceDAO method, of class FuncionDAO.
     */
    @Test
    public void testGetInstanceDAO() {
        assertNotNull(funcionDAO, "La instancia del DAO no debe ser nula");
    }

    /**
     * Test of registrarFuncion method, of class FuncionDAO.
     */
    @Test
    public void testRegistrarFuncionExitoso() throws Exception {
        Funcion registrada = funcionDAO.registrarFuncion(funcionPrueba);
        assertNotNull(registrada.getIdFuncion(), "La función debe tener un ID después de registrarse");
    }

    @Test
    public void testRegistrarFuncionSalaInactiva() {
        Sala salaInactiva = new Sala(50, "Sala2", EstadoSala.INACTIVA);
        Funcion funcionInvalida = new Funcion(
                new ObjectId(),
                salaInactiva,
                peliculaPrueba,
                LocalDateTime.now().plusHours(1),
                150.0
        );

        assertThrows(FuncionSalaVaciaException.class, () -> { // Crear esta excepción
            funcionDAO.registrarFuncion(funcionInvalida);
        });
    }

    @Test
    public void testRegistrarFuncionSalaOcupada() throws Exception {
        funcionDAO.registrarFuncion(funcionPrueba);
        Funcion funcionRepetida = new Funcion(
                new ObjectId(),
                salaPrueba,
                peliculaPrueba,
                funcionPrueba.getFechaHora(),
                200.0
        );

        assertThrows(FuncionSalaOcupadaException.class, () -> {
            funcionDAO.registrarFuncion(funcionRepetida);
        }, "Debe fallar al registrar funciones en la misma sala");
    }

    /**
     * Test of eliminarFuncion method, of class FuncionDAO.
     */
    @Test
    public void testEliminarFuncionExitoso() throws Exception {
        funcionDAO.registrarFuncion(funcionPrueba);
        Funcion eliminada = funcionDAO.eliminarFuncion(funcionPrueba);
        assertEquals(funcionPrueba.getIdFuncion(), eliminada.getIdFuncion(), "Debe eliminar la función correcta");
    }

    @Test
    public void testEliminarFuncionFallido() {
        // Crear una función que NO se registra en la base de datos
        Funcion funcionNoRegistrada = new Funcion(
                new ObjectId(),
                salaPrueba,
                peliculaPrueba,
                LocalDateTime.now().plusHours(3),
                150.0
        );

        // Verificar que se lanza la excepción al intentar eliminar
        assertThrows(FuncionNoEncontradaException.class, () -> {
            funcionDAO.eliminarFuncion(funcionNoRegistrada);
        }, "Debe fallar al eliminar una función no registrada");
    }

    /**
     * Test of buscarFuncionPorId method, of class FuncionDAO.
     */
    @Test
    public void testBuscarFuncionPorId() throws Exception {
        funcionDAO.registrarFuncion(funcionPrueba);
        Funcion encontrada = funcionDAO.buscarFuncionId(funcionPrueba.getIdFuncion());
        assertEquals(funcionPrueba.getIdFuncion(), encontrada.getIdFuncion(), "Los IDs deben coincidir");
    }

    /**
     * Test of buscarFuncionesPelicula method, of class FuncionDAO.
     */
    @Test
    public void testBuscarFuncionesPelicula() throws Exception {
        funcionDAO.registrarFuncion(funcionPrueba);
        List<Funcion> funciones = funcionDAO.buscarFuncionesPelicula("PeliculaPrueba");
        assertTrue(funciones.size() > 0, "Debe encontrar al menos una función para la película");
    }

}
