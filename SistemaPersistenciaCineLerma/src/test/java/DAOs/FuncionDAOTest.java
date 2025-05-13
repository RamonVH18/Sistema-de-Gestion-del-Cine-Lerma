/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package DAOs;

import entidades.Funcion;
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
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getInstanceDAO method, of class FuncionDAO.
     */
    @Test
    public void testGetInstanceDAO() {
        System.out.println("getInstanceDAO");
        FuncionDAO expResult = null;
        FuncionDAO result = FuncionDAO.getInstanceDAO();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of registrarFuncion method, of class FuncionDAO.
     */
    @Test
    public void testRegistrarFuncion() throws Exception {
        System.out.println("registrarFuncion");
        Funcion funcion = null;
        FuncionDAO instance = null;
        Funcion expResult = null;
        Funcion result = instance.registrarFuncion(funcion);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of eliminarFuncion method, of class FuncionDAO.
     */
    @Test
    public void testEliminarFuncion() throws Exception {
        System.out.println("eliminarFuncion");
        Funcion funcion = null;
        FuncionDAO instance = null;
        Funcion expResult = null;
        Funcion result = instance.eliminarFuncion(funcion);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of buscarFuncionPorId method, of class FuncionDAO.
     */
    @Test
    public void testBuscarFuncionPorId() {
        System.out.println("buscarFuncionPorId");
        ObjectId idFuncion = null;
        FuncionDAO instance = null;
        Funcion expResult = null;
        Funcion result = instance.buscarFuncionPorId(idFuncion);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of buscarFuncionesPelicula method, of class FuncionDAO.
     */
    @Test
    public void testBuscarFuncionesPelicula() {
        System.out.println("buscarFuncionesPelicula");
        String nombrePelicula = "";
        FuncionDAO instance = null;
        List<Funcion> expResult = null;
        List<Funcion> result = instance.buscarFuncionesPelicula(nombrePelicula);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of buscarFuncionesActivas method, of class FuncionDAO.
     */
    @Test
    public void testBuscarFuncionesActivas() {
        System.out.println("buscarFuncionesActivas");
        FuncionDAO instance = null;
        List<Funcion> expResult = null;
        List<Funcion> result = instance.buscarFuncionesActivas();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
