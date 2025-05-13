/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package DAOs;

import Excepciones.Funciones.FuncionNoEncontradaException;
import Excepciones.Funciones.FuncionSalaOcupadaException;
import Interfaces.IFuncionDAO;
import entidades.Funcion;
import entidades.Pelicula;
import entidades.Sala;
import enums.EstadoSala;
import java.time.LocalDateTime;
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
        peliculaPrueba = new Pelicula(1L, "PeliculaPrueba", "imagen.jpg", "Acción", 120, "Sinopsis", true);
        salaPrueba = new Sala(50, "Sala1", EstadoSala.ACTIVA);
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
            if (funcionPrueba.getIdFuncion() != null) {
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

    /**
     * Test of eliminarFuncion method, of class FuncionDAO.
     */
    @Test
    public void testEliminarFuncionExitoso() throws Exception {
        funcionDAO.registrarFuncion(funcionPrueba);
        Funcion eliminada = funcionDAO.eliminarFuncion(funcionPrueba);
        assertEquals(funcionPrueba.getIdFuncion(), eliminada.getIdFuncion(), "Debe eliminar la función correcta");
    }

    /**
     * Test of buscarFuncionPorId method, of class FuncionDAO.
     */
//    @Test
//    public void testBuscarFuncionPorId() throws Exception {
//        funcionDAO.registrarFuncion(funcionPrueba);
//        Funcion encontrada = funcionDAO.buscarFuncionPorId(funcionPrueba.getIdFuncion());
//        assertEquals(funcionPrueba.getIdFuncion(), encontrada.getIdFuncion(), "Los IDs deben coincidir");
//    }

    /**
     * Test of buscarFuncionesPelicula method, of class FuncionDAO.
     */
//    @Test
//    public void testBuscarFuncionesPelicula() throws Exception {
//        funcionDAO.registrarFuncion(funcionPrueba);
//        List<Funcion> funciones = funcionDAO.buscarFuncionesPelicula("PeliculaPrueba");
//        assertTrue(funciones.size() > 0, "Debe encontrar al menos una función para la película");
//    }

}
