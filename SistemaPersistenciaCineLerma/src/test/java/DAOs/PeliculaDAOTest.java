package DAOs;

import Excepciones.peliculas.*;
import entidades.Pelicula;
import java.util.List;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase PeliculaDAO utilizando JUnit 5. Valida el
 * funcionamiento de las operaciones CRUD, cambios de estado y búsquedas.
 *
 * @author Daniel M
 */
public class PeliculaDAOTest {

    private PeliculaDAO peliculaDAO; // Instancia del DAO a probar
    private Pelicula peliculaPrueba; // Película base para pruebas

    public PeliculaDAOTest() {
    }

    /**
     * Configura el entorno antes de cada prueba: - Crea una instancia del DAO -
     * Crea una película de prueba con datos válidos
     */
    @BeforeEach
    public void setUp() {
        peliculaDAO = PeliculaDAO.getInstanceDAO();

        // Crear película de prueba
        ObjectId idPelicula = new ObjectId();
        byte[] imagen = null;
        peliculaPrueba = new Pelicula(
                idPelicula,
                imagen,
                "PeliculaPrueba",
                "Terror",
                120,
                "B15",
                "Sinopsis de prueba",
                true
        );

        // Registrar la película en la base de datos para pruebas que lo requieran
        try {
            peliculaDAO.registrarPelicula(peliculaPrueba);
        } catch (RegistrarPeliculaException e) {
            fail("Error al configurar la película de prueba: " + e.getMessage());
        }
    }

    /**
     * Elimina la película de prueba después de cada test para evitar
     * interferencias.
     */
    @AfterEach
    public void tearDown() {
        try {
            peliculaDAO.eliminarPelicula(peliculaPrueba);
        } catch (EliminarPeliculaException e) {
            System.out.println("Error en limpieza: " + e.getMessage());
        }
    }

    /**
     * Verifica que la instancia del DAO se obtenga correctamente (Singleton).
     */
    @Test
    public void testGetInstanceDAO() {
        assertNotNull(peliculaDAO, "La instancia del DAO no debe ser nula");
        PeliculaDAO otraInstancia = PeliculaDAO.getInstanceDAO();
        assertSame(peliculaDAO, otraInstancia, "Debería ser la misma instancia (Singleton)");
    }

    /**
     * Prueba el registro exitoso de una película. - La película registrada debe
     * tener un ID asignado por MongoDB.
     */
    @Test
    public void testRegistrarPeliculaExitoso() {
        try {
            // Crear nueva película para registro
            Pelicula nuevaPelicula = new Pelicula(
                    null, // Sin ID
                    null, // Sin imagen
                    "NuevaPelicula",
                    "Drama",
                    150,
                    "A",
                    "Otra sinopsis",
                    true
            );

            Pelicula registrada = peliculaDAO.registrarPelicula(nuevaPelicula);
            assertNotNull(registrada.getIdPelicula(), "La película debe tener un ID después de registrarse");

            // Limpieza
            peliculaDAO.eliminarPelicula(registrada);
        } catch (RegistrarPeliculaException | EliminarPeliculaException e) {
            fail("Excepción inesperada: " + e.getMessage());
        }
    }

    /**
     * Prueba el registro fallido cuando la película ya existe.
     */
    @Test
    public void testRegistrarPeliculaDuplicada() {
        assertThrows(RegistrarPeliculaException.class, () -> {
            // Intentar registrar la misma película dos veces
            peliculaDAO.registrarPelicula(peliculaPrueba);
        }, "Debe fallar al registrar una película duplicada");
    }

    /**
     * Prueba la actualización exitosa de una película.
     */
    @Test
    public void testActualizarPeliculaExitoso() {
        try {
            // Modificar datos de la película
            peliculaPrueba.setTitulo("Título Modificado");
            peliculaPrueba.setGenero("Comedia");

            Pelicula actualizada = peliculaDAO.actualizarPelicula(peliculaPrueba);

            assertEquals("Título Modificado", actualizada.getTitulo(), "El título debe haberse actualizado");
            assertEquals("Comedia", actualizada.getGenero(), "El género debe haberse actualizado");
        } catch (ActualizarPeliculaException e) {
            fail("Excepción inesperada al actualizar: " + e.getMessage());
        }
    }

    /**
     * Prueba la actualización fallida de una película no existente.
     */
    @Test
    public void testActualizarPeliculaNoExistente() {
        Pelicula peliculaInexistente = new Pelicula(
                new ObjectId(), // ID que no existe en la BD
                null,
                "Inexistente",
                "Genero",
                100,
                "B",
                "Sinopsis",
                true
        );

        assertThrows(ActualizarPeliculaException.class, () -> {
            peliculaDAO.actualizarPelicula(peliculaInexistente);
        }, "Debe fallar al actualizar una película no existente");
    }

    /**
     * Prueba la eliminación exitosa de una película.
     */
    @Test
    public void testEliminarPeliculaExitoso() {
        try {
            // Crear película temporal para eliminar
            Pelicula tempPelicula = new Pelicula(
                    null,
                    null,
                    "TempPelicula",
                    "Terror",
                    90,
                    "C",
                    "Sinopsis temporal",
                    true
            );
            peliculaDAO.registrarPelicula(tempPelicula);

            boolean resultado = peliculaDAO.eliminarPelicula(tempPelicula);
            assertTrue(resultado, "Debe retornar true al eliminar exitosamente");
        } catch (RegistrarPeliculaException | EliminarPeliculaException e) {
            fail("Excepción inesperada: " + e.getMessage());
        }
    }

    /**
     * Prueba la eliminación fallida de una película no existente.
     */
    @Test
    public void testEliminarPeliculaNoExistente() {
        Pelicula peliculaInexistente = new Pelicula(
                new ObjectId(), // ID que no existe en la BD
                null,
                "Inexistente",
                "Genero",
                100,
                "B",
                "Sinopsis",
                true
        );

        assertThrows(EliminarPeliculaException.class, () -> {
            peliculaDAO.eliminarPelicula(peliculaInexistente);
        }, "Debe fallar al eliminar una película no existente");
    }

    /**
     * Prueba el cambio de estado a activo exitoso.
     */
    @Test
    public void testDarAltaPeliculaExitoso() {
        try {
            // Primero dar de baja la película
            peliculaDAO.darBajaPelicula(peliculaPrueba);

            // Luego intentar dar de alta
            boolean resultado = peliculaDAO.darAltaPelicula(peliculaPrueba);
            assertTrue(resultado, "Debe retornar true al dar de alta exitosamente");

            // Verificar que quedó activa
            Pelicula pelicula = peliculaDAO.buscarPeliculaPorTitulo(peliculaPrueba.getTitulo());
            assertTrue(pelicula.getActivo(), "La película debe estar activa");
        } catch (DarBajaPeliculaException | DarAltaPeliculaException | BuscarPeliculaException e) {
            fail("Excepción inesperada: " + e.getMessage());
        }
    }

    /**
     * Prueba el cambio de estado a activo cuando ya está activa.
     */
    @Test
    public void testDarAltaPeliculaYaActiva() {
        try {
            boolean resultado = peliculaDAO.darAltaPelicula(peliculaPrueba);
            assertFalse(resultado, "Debe retornar false cuando ya está activa");
        } catch (DarAltaPeliculaException e) {
            fail("Excepción inesperada: " + e.getMessage());
        }
    }

    /**
     * Prueba el cambio de estado a inactivo exitoso.
     */
    @Test
    public void testDarBajaPeliculaExitoso() {
        try {
            boolean resultado = peliculaDAO.darBajaPelicula(peliculaPrueba);
            assertTrue(resultado, "Debe retornar true al dar de baja exitosamente");

            // Verificar que quedó inactiva
            Pelicula pelicula = peliculaDAO.buscarPeliculaPorTitulo(peliculaPrueba.getTitulo());
            assertFalse(pelicula.getActivo(), "La película debe estar inactiva");
        } catch (DarBajaPeliculaException | BuscarPeliculaException e) {
            fail("Excepción inesperada: " + e.getMessage());
        }
    }

    /**
     * Prueba el cambio de estado a inactivo cuando ya está inactiva.
     */
    @Test
    public void testDarBajaPeliculaYaInactiva() {
        try {
            // Primero dar de baja
            peliculaDAO.darBajaPelicula(peliculaPrueba);

            // Intentar dar de baja nuevamente
            boolean resultado = peliculaDAO.darBajaPelicula(peliculaPrueba);
            assertFalse(resultado, "Debe retornar false cuando ya está inactiva");
        } catch (DarBajaPeliculaException e) {
            fail("Excepción inesperada: " + e.getMessage());
        }
    }

    /**
     * Prueba la búsqueda de película por título exitosa.
     */
    @Test
    public void testBuscarPeliculaPorTituloExitoso() {
        try {
            Pelicula encontrada = peliculaDAO.buscarPeliculaPorTitulo(peliculaPrueba.getTitulo());
            assertNotNull(encontrada, "Debe encontrar la película por título");
            assertEquals(peliculaPrueba.getTitulo(), encontrada.getTitulo(), "Los títulos deben coincidir");
        } catch (BuscarPeliculaException e) {
            fail("Excepción inesperada: " + e.getMessage());
        }
    }

    /**
     * Prueba la búsqueda de película por ID exitosa.
     */
    @Test
    public void testBuscarPeliculaPorIdExitoso() {
        try {
            String id = peliculaPrueba.getIdPelicula().toString();
            Pelicula encontrada = peliculaDAO.buscarPeliculaPorId(id);
            assertNotNull(encontrada, "Debe encontrar la película por ID");
            assertEquals(peliculaPrueba.getIdPelicula(), encontrada.getIdPelicula(), "Los IDs deben coincidir");
        } catch (BuscarPeliculaException e) {
            fail("Excepción inesperada: " + e.getMessage());
        }
    }

    /**
     * Prueba la obtención de películas activas.
     */
    @Test
    public void testMostrarPeliculasActivas() {
        try {
            List<Pelicula> activas = peliculaDAO.mostrarPeliculasActivasOInactivas(true);
            assertFalse(activas.isEmpty(), "Debe haber al menos una película activa");
            activas.forEach(p -> assertTrue(p.getActivo(), "Todas deben estar activas"));
        } catch (MostrarPeliculasException e) {
            fail("Excepción inesperada: " + e.getMessage());
        }
    }

    /**
     * Prueba la obtención de películas inactivas.
     */
    @Test
    public void testMostrarPeliculasInactivas() {
        try {
            // Asegurarse de que hay al menos una inactiva
            Pelicula inactiva = new Pelicula(
                    null,
                    null,
                    "InactivaTest",
                    "Genero",
                    100,
                    "B",
                    "Sinopsis",
                    false
            );
            peliculaDAO.registrarPelicula(inactiva);

            List<Pelicula> inactivas = peliculaDAO.mostrarPeliculasActivasOInactivas(false);
            assertFalse(inactivas.isEmpty(), "Debe haber al menos una película inactiva");
            inactivas.forEach(p -> assertFalse(p.getActivo(), "Todas deben estar inactivas"));

            peliculaDAO.eliminarPelicula(inactiva);
        } catch (RegistrarPeliculaException | MostrarPeliculasException | EliminarPeliculaException e) {
            fail("Excepción inesperada: " + e.getMessage());
        }
    }

    /**
     * Prueba la búsqueda de películas con múltiples filtros.
     */
    @Test
    public void testMostrarPeliculasFiltradas() {
        try {
            // Crear películas adicionales para pruebas de filtrado
            Pelicula pelicula1 = new Pelicula(null, null, "FiltroTest1", "Accion", 120, "B15", "Sinopsis", true);
            Pelicula pelicula2 = new Pelicula(null, null, "FiltroTest2", "Drama", 90, "A", "Sinopsis", true);
            Pelicula pelicula3 = new Pelicula(null, null, "OtraPelicula", "Comedia", 110, "B", "Sinopsis", false);

            peliculaDAO.registrarPelicula(pelicula1);
            peliculaDAO.registrarPelicula(pelicula2);
            peliculaDAO.registrarPelicula(pelicula3);

            // Prueba 1: Filtrar por género
            List<Pelicula> porGenero = peliculaDAO.mostrarPeliculasFiltradas(null, null, "Accion", null);
            assertEquals(1, porGenero.size(), "Debe encontrar 1 película de acción");
            assertEquals("Accion", porGenero.get(0).getGenero(), "El género debe ser Accion");

            // Prueba 2: Filtrar por clasificación
            List<Pelicula> porClasificacion = peliculaDAO.mostrarPeliculasFiltradas(null, "A", null, null);
            assertEquals(1, porClasificacion.size(), "Debe encontrar 1 película con clasificación A");
            assertEquals("A", porClasificacion.get(0).getClasificacion(), "La clasificación debe ser A");

            // Prueba 3: Filtrar por estado y título parcial
            List<Pelicula> porEstadoYTitulo = peliculaDAO.mostrarPeliculasFiltradas(false, null, null, "Otra");
            assertEquals(1, porEstadoYTitulo.size(), "Debe encontrar 1 película inactiva con 'Otra' en el título");
            assertFalse(porEstadoYTitulo.get(0).getActivo(), "Debe estar inactiva");
            assertTrue(porEstadoYTitulo.get(0).getTitulo().contains("Otra"), "El título debe contener 'Otra'");

            peliculaDAO.eliminarPelicula(pelicula1);
            peliculaDAO.eliminarPelicula(pelicula2);
            peliculaDAO.eliminarPelicula(pelicula3);
        } catch (RegistrarPeliculaException | MostrarPeliculasException | EliminarPeliculaException e) {
            fail("Excepción inesperada: " + e.getMessage());
        }
    }
}
