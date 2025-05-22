/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Conexion.MongoConexion;
import Excepciones.salas.BuscarSalaException;
import Excepciones.salas.CreacionSalaException;
import Excepciones.salas.ModificarSalaException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import entidades.Sala;
import enums.EstadoSala;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Ramon Valencia
 */
public class SalaDAOTest {

    private static SalaDAO salaDAO;
    private static final MongoConexion conexion = new MongoConexion();
    private static ObjectId idSalaInsertada;
    private static final Sala salaPrueba= new Sala(100, "9999", EstadoSala.ACTIVA);

    @BeforeAll
    public static void setUp() {
        salaDAO = SalaDAO.getInstanceDAO();
    }
    
    @BeforeEach
public void limpiarSalaDePrueba() throws BuscarSalaException {
    // Asegúrate de eliminar por número o por algún criterio que evite el choque de _id
    Sala sala = salaDAO.buscarSala("9999");
    if (sala != null) {
        MongoClient clienteMongo = conexion.crearConexion();
            MongoDatabase base = conexion.obtenerBaseDatos(clienteMongo);
            MongoCollection<Sala> coleccion = base.getCollection("Salas", Sala.class);
            Bson filtro = Filters.eq("numSala", "9999");
            DeleteResult resultado = coleccion.deleteOne(filtro); // Borra todos los documentos
            conexion.cerrarConexion(clienteMongo);
            System.out.println("Sala de prueba eliminada: " + resultado.wasAcknowledged());
    }
}

    @Test
    @Order(1)
    public void testInsertarSalaDePrueba() throws CreacionSalaException, BuscarSalaException {

        salaDAO.agregarSala(salaPrueba);
        assertNotNull(salaPrueba.getIdSala());

        idSalaInsertada = salaPrueba.getIdSala();

        Sala encontrada = salaDAO.buscarSala("9999");
        assertNotNull(encontrada);
        assertEquals("9999", encontrada.getNumSala());
    }

    @Test
    @Order(2)
    public void testActualizarEstadoSalaDePrueba() throws BuscarSalaException, ModificarSalaException, CreacionSalaException {
        salaDAO.modificarEstadoSala("9999", EstadoSala.INACTIVA);

        Sala actualizada = salaDAO.buscarSala("9999");
        assertEquals(EstadoSala.INACTIVA, actualizada.getEstado());
    }

}
