/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package DAOs;

import Excepciones.Funciones.FuncionDuracionIncorrectaException;
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
 * Pruebas unitarias para la clase FuncionDAO utilizando JUnit 5. Valida el
 * funcionamiento de las operaciones CRUD, reglas de negocio y manejo de
 * excepciones.
 *
 * @author Abraham Coronel Bringas
 */
public class FuncionDAOTest {

    private IFuncionDAO funcionDAO; // Instancia del DAO a probar
    private Funcion funcionPrueba; // Funcion base para pruebas
    private Pelicula peliculaPrueba; // Película asociada a la funcion
    private Sala salaPrueba; // Sala activa para pruebas

    public FuncionDAOTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    /**
     * Configura el entorno antes de cada prueba: - Crea una sala activa con 50
     * asientos. - Crea una película con duracion válida. - Crea una función de
     * prueba con hora de inicio en el futuro.
     */
    @BeforeEach
    public void setUp() {
        funcionDAO = FuncionDAO.getInstanceDAO();
        ObjectId idPelicula = new ObjectId();
        byte[] imagen = null;
        peliculaPrueba = new Pelicula(idPelicula, imagen, "PeliculaPrueba", "Accion", 200, "B15", "Sinopsis", true);

        salaPrueba = new Sala(50, "Sala1", EstadoSala.ACTIVA);
        List<Asiento> asientos = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            asientos.add(new Asiento(String.valueOf(i)));
        }
        salaPrueba.setAsientos(asientos);

        LocalDateTime fechaHoraInicio = LocalDateTime.now().plusHours(1);

        funcionPrueba = new Funcion(
                new ObjectId(),
                salaPrueba,
                peliculaPrueba,
                fechaHoraInicio,
                150.0
        );
    }

    /**
     * Elimina la funcion de prueba despues de cada test para evitar
     * interferencias.
     */
    @AfterEach
    public void tearDown() {
        try {
            funcionDAO.eliminarFuncion(funcionPrueba);
        } catch (FuncionNoEncontradaException e) {
            System.out.println("Error en limpieza: " + e.getMessage());
        }
    }

    /**
     * Verifica que la instancia del DAO se obtenga correctamente (Singleton).
     */
    @Test
    public void testGetInstanceDAO() {
        assertNotNull(funcionDAO, "La instancia del DAO no debe ser nula");
    }

    /**
     * Prueba el registro exitoso de una función: - La función registrada debe
     * tener un ID asignado por MongoDB.
     */
    @Test
    public void testRegistrarFuncionExitoso() throws Exception {
        Funcion registrada = funcionDAO.registrarFuncion(funcionPrueba);
        assertNotNull(registrada.getIdFuncion(), "La función debe tener un ID después de registrarse");
    }

    /**
     * Prueba el registro fallido cuando la sala está inactiva. Se espera una
     * excepcion {FuncionSalaVaciaException}.
     */
    @Test
    public void testRegistrarFuncionSalaInactiva() {
        Sala salaInactiva = new Sala(50, "Sala2", EstadoSala.INACTIVA);
        LocalDateTime fechaHoraInicio = LocalDateTime.now().plusHours(1);
        Funcion funcionInvalida = new Funcion(
                new ObjectId(),
                salaInactiva,
                peliculaPrueba,
                fechaHoraInicio,
                150.0
        );

        assertThrows(FuncionSalaVaciaException.class, () -> {
            funcionDAO.registrarFuncion(funcionInvalida);
        });
    }

    /**
     * Prueba el registro fallido cuando la sala está ocupada en el mismo
     * horario. Se espera una excepcion {FuncionSalaOcupadaException}.
     */
    @Test
    public void testRegistrarFuncionSalaOcupada() throws Exception {
        funcionDAO.registrarFuncion(funcionPrueba);
        LocalDateTime fechaHoraInicioSolapada = funcionPrueba.getFechaHora().plusMinutes(1);
        Funcion funcionRepetida = new Funcion(
                new ObjectId(),
                salaPrueba,
                peliculaPrueba,
                fechaHoraInicioSolapada,
                200.0
        );

        assertThrows(FuncionSalaOcupadaException.class, () -> {
            funcionDAO.registrarFuncion(funcionRepetida);
        }, "Debe fallar al registrar funciones en la misma sala");
    }

    /**
     * Prueba la eliminación exitosa de una funcion. Verifica que la funcion
     * eliminada coincida con la registrada.
     */
    @Test
    public void testEliminarFuncionExitoso() throws Exception {
        funcionDAO.registrarFuncion(funcionPrueba);
        Funcion eliminada = funcionDAO.eliminarFuncion(funcionPrueba);
        assertEquals(funcionPrueba.getIdFuncion(), eliminada.getIdFuncion(), "Debe eliminar la funcion correcta");
    }

    /**
     * Prueba la eliminación fallida de una funcion no registrada. Se espera una
     * excepcion {FuncionNoEncontradaException}.
     */
    @Test
    public void testEliminarFuncionFallido() {
        LocalDateTime fechaHoraInicio = LocalDateTime.now().plusHours(1);
        Funcion funcionNoRegistrada = new Funcion(
                new ObjectId(),
                salaPrueba,
                peliculaPrueba,
                fechaHoraInicio,
                150.0
        );

        // Verificar que se lanza la excepción al intentar eliminar
        assertThrows(FuncionNoEncontradaException.class, () -> {
            funcionDAO.eliminarFuncion(funcionNoRegistrada);
        }, "Debe fallar al eliminar una funcion no registrada");
    }

    /**
     * Prueba el calculo correcto de la hora de termino de una funcion. Compara
     * el resultado con la hora de inicio + duración de la pelicula.
     */
    @Test
    public void testCalcularHoraTerminoFuncionExitoso() throws Exception {
        funcionDAO.registrarFuncion(funcionPrueba);
        String idFuncion = funcionPrueba.getIdFuncion().toHexString();
        LocalDateTime horaTermino = funcionDAO.calcularHoraTerminoFuncion(idFuncion);
        LocalDateTime expected = funcionPrueba.getFechaHora().plusMinutes(peliculaPrueba.getDuracion()).truncatedTo(java.time.temporal.ChronoUnit.MILLIS);

        LocalDateTime actualTruncado = horaTermino.truncatedTo(java.time.temporal.ChronoUnit.MILLIS);

        assertEquals(expected, actualTruncado, "La hora de término debe coincidir con la duración de la película");
    }

    /**
     * Prueba el cálculo fallido con un ID de funcion inexistente. Se espera una
     * excepcion {@link FuncionNoEncontradaException}.
     */
    @Test
    public void testCalcularHoraTerminoFuncionNoEncontrada() {
        String idNoExistente = new ObjectId().toHexString();
        assertThrows(FuncionNoEncontradaException.class, () -> {
            funcionDAO.calcularHoraTerminoFuncion(idNoExistente);
        }, "Debe lanzar excepción cuando la función no existe");
    }

    /**
     * Prueba el registro fallido de una funcion con película de duracion nula.
     * Se espera una excepcion {FuncionDuracionIncorrectaException}.
     */
    @Test
    public void testCalcularHoraTerminoFuncionDuracionIncorrecta() throws Exception {
        // Crear película con duración nula
        Pelicula peliculaSinDuracion = new Pelicula(
                new ObjectId(),
                new byte[]{},
                "PeliculaSinDuracion",
                "Género",
                null, // Duración nula
                "Clasificacion",
                "Sinopsis",
                true
        );

        Funcion funcionInvalida = new Funcion(
                new ObjectId(),
                salaPrueba,
                peliculaSinDuracion,
                LocalDateTime.now().plusHours(3),
                150.0
        );

        assertThrows(FuncionDuracionIncorrectaException.class, () -> {
            funcionDAO.registrarFuncion(funcionInvalida);
        }, "Debe lanzar excepción si la duración es incorrecta");

    }

    /**
     * Prueba la busqueda de funciones por nombre de pelicula. Verifica que se
     * encuentre al menos una coincidencia.
     */
    @Test
    public void testBuscarFuncionesPelicula() throws Exception {
        funcionDAO.registrarFuncion(funcionPrueba);
        List<Funcion> funciones = funcionDAO.buscarFuncionesPelicula("PeliculaPrueba");
        assertTrue(!funciones.isEmpty(), "Debe encontrar al menos una funcion para la pelicula");
    }
}
