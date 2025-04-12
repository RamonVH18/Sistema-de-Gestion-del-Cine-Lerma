/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Excepciones.PersistenciaException;
import Interfaces.IFuncionDAO;
import Interfaces.IPeliculaDAO;
import Interfaces.ObservadorFuncion;
import entidades.Funcion;
import entidades.Pelicula;
import entidades.Sala;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 *
 * @author Ramon Valencia
 */
public class FuncionDAO implements IFuncionDAO {

    private static FuncionDAO instance;
    private final IPeliculaDAO peliculaDAO = PeliculaDAO.getInstanceDAO();
    private Map<Long, List<ObservadorFuncion>> observadores = new HashMap();

    private FuncionDAO() {
    }

    public static FuncionDAO getInstanceDAO() {
        if (instance == null) {
            instance = new FuncionDAO();
        }

        return instance;
    }

    List<Funcion> funciones = new ArrayList<>();

    @Override
    public List<Funcion> funcionesHarcodeadas() throws PersistenciaException {
        if (funciones.isEmpty()) {
            // Crear salas
            Sala salaA1 = new Sala(1L, 100, 1, true);
            Sala salaA2 = new Sala(2L, 90, 2, true);
            Sala salaA3 = new Sala(3L, 80, 3, true);
            Sala salaB1 = new Sala(4L, 120, 4, true);
            Sala salaB2 = new Sala(5L, 110, 5, true);
            Sala salaB3 = new Sala(6L, 105, 6, true);

            // Crear películas
            List<Pelicula> peliculas = peliculaDAO.mostrarListaPelicula();

            // Agregar funciones
            funciones.add(new Funcion(1L, salaA1, peliculas.get(0), LocalDateTime.of(2025, 3, 24, 14, 30), true, 80.00));
            funciones.add(new Funcion(2L, salaB1, peliculas.get(0), LocalDateTime.of(2025, 3, 25, 17, 0), true, 75.00));

            funciones.add(new Funcion(3L, salaA2, peliculas.get(1), LocalDateTime.of(2025, 3, 25, 16, 0), true, 75.00));
            funciones.add(new Funcion(4L, salaB1, peliculas.get(1), LocalDateTime.of(2025, 3, 29, 19, 30), true, 75.00));
            funciones.add(new Funcion(5L, salaA2, peliculas.get(1), LocalDateTime.of(2025, 3, 28, 13, 0), true, 60.00));
            funciones.add(new Funcion(6L, salaB2, peliculas.get(1), LocalDateTime.of(2025, 3, 25, 23, 59), true, 60.00));

            funciones.add(new Funcion(7L, salaA3, peliculas.get(2), LocalDateTime.of(2025, 3, 27, 18, 45), true, 80.00));
            funciones.add(new Funcion(8L, salaB3, peliculas.get(2), LocalDateTime.of(2025, 3, 26, 21, 0), true, 80.00));

            funciones.add(new Funcion(9L, salaA1, peliculas.get(3), LocalDateTime.of(2025, 3, 26, 20, 0), true, 90.00));
            funciones.add(new Funcion(10L, salaB3, peliculas.get(3), LocalDateTime.of(2025, 3, 26, 22, 15), true, 75.00));

            funciones.add(new Funcion(11L, salaA2, peliculas.get(4), LocalDateTime.of(2025, 3, 27, 17, 30), true, 65.00));
            funciones.add(new Funcion(12L, salaB2, peliculas.get(4), LocalDateTime.of(2025, 3, 26, 20, 45), true, 75.00));

            funciones.add(new Funcion(13L, salaA2, peliculas.get(5), LocalDateTime.of(2025, 3, 27, 17, 30), true, 65.00));
            funciones.add(new Funcion(14L, salaB2, peliculas.get(5), LocalDateTime.of(2025, 3, 26, 20, 45), true, 75.00));

            funciones.add(new Funcion(15L, salaA1, peliculas.get(6), LocalDateTime.of(2025, 3, 27, 17, 30), true, 65.00));
            funciones.add(new Funcion(16L, salaB1, peliculas.get(6), LocalDateTime.of(2025, 3, 26, 20, 45), true, 75.00));

            funciones.add(new Funcion(17L, salaA1, peliculas.get(7), LocalDateTime.of(2025, 3, 27, 17, 30), true, 65.00));
            funciones.add(new Funcion(18L, salaB1, peliculas.get(7), LocalDateTime.of(2025, 3, 26, 20, 45), true, 75.00));

            funciones.add(new Funcion(19L, salaA1, peliculas.get(8), LocalDateTime.of(2025, 3, 27, 17, 30), true, 65.00));
            funciones.add(new Funcion(20L, salaB1, peliculas.get(8), LocalDateTime.of(2025, 3, 26, 20, 45), true, 75.00));

            funciones.add(new Funcion(21L, salaA1, peliculas.get(9), LocalDateTime.of(2025, 3, 27, 17, 30), true, 65.00));
            funciones.add(new Funcion(22L, salaB1, peliculas.get(9), LocalDateTime.of(2025, 3, 26, 20, 45), true, 75.00));

            funciones.add(new Funcion(23L, salaA1, peliculas.get(10), LocalDateTime.of(2025, 3, 27, 17, 30), true, 65.00));
            funciones.add(new Funcion(24L, salaB1, peliculas.get(10), LocalDateTime.of(2025, 3, 26, 20, 45), true, 75.00));

        }

        return funciones;
    }

    @Override
    public List<Funcion> buscarFuncionesPelicula(Pelicula pelicula) throws PersistenciaException {
        List<Funcion> funcionesPelicula = new ArrayList<>();

        for (int i = 0; i < funciones.size(); i++) {
            Funcion funcion = funciones.get(i);
            if (funcion.getPelicula() == pelicula) {
                funcionesPelicula.add(funcion);
            }
        }
        return funcionesPelicula;
    }

    @Override
    public List<Funcion> mostrarFuncionesActivas() throws PersistenciaException {
        List<Funcion> funcionesPelicula = new ArrayList<>();
        funcionesHarcodeadas();
        for (int i = 0; i < funciones.size(); i++) {
            Funcion funcion = funciones.get(i);
            if (funcion.getEstado()) {
                funcionesPelicula.add(funcion);
            }
        }
        return funcionesPelicula;
    }

    @Override
    public List<Funcion> mostrarFuncionesPeliculas() throws PersistenciaException {
        if (!funciones.isEmpty()) {
            return funciones;
        }

        return null;
    }

    // metodo para actualizar una funcion con notificacion
    @Override
    public boolean actualizarFuncion(Funcion funcionActualizada) throws PersistenciaException {
        // Nuevo método implementado
        for (int i = 0; i < funciones.size(); i++) {
            Funcion funcionExistente = funciones.get(i);

            if (funcionExistente.getIdFuncion().equals(funcionActualizada.getIdFuncion())) {
                // Detectar cambios para enviar notificaciones
                detectarYNotificarCambios(funcionExistente, funcionActualizada);

                // Actualizar la función
                funciones.set(i, funcionActualizada);
                return true;
            }
        }
        return false;
    }

    // metodo de buscar funcion por id
    public Funcion buscarFuncionPorId(Long idFuncion) throws PersistenciaException {
        for (Funcion funcion : funciones) {
            if (funcion.getIdFuncion().equals(idFuncion)) {
                return funcion;
            }
        }
        return null;
    }

    // metodo privado para detectar cambios y notificar
    private void detectarYNotificarCambios(Funcion funcionOriginal, Funcion funcionActualizada) {
        // Verificar cambio de estado
        if (!funcionOriginal.getEstado().equals(funcionActualizada.getEstado())) {
            String mensaje = funcionActualizada.getEstado()
                    ? "La función ha sido activada"
                    : "La función ha sido cancelada";
            notificarObservadores(funcionActualizada.getIdFuncion(), "CAMBIO_ESTADO", mensaje);
        }

        // Verificar cambio de horario
        if (!funcionOriginal.getFechaHora().equals(funcionActualizada.getFechaHora())) {
            String mensaje = "Cambio de horario: de "
                    + funcionOriginal.getFechaHora() + " a "
                    + funcionActualizada.getFechaHora();
            notificarObservadores(funcionActualizada.getIdFuncion(), "CAMBIO_HORARIO", mensaje);
        }

        // Verificar cambio de sala
        if (!funcionOriginal.getSala().equals(funcionActualizada.getSala())) {
            String mensaje = "Cambio de sala: de sala #"
                    + funcionOriginal.getSala().getNumSala() + " a sala #"
                    + funcionActualizada.getSala().getNumSala();
            notificarObservadores(funcionActualizada.getIdFuncion(), "CAMBIO_SALA", mensaje);
        }

        // Verificar cambio de precio
        if (!funcionOriginal.getPrecio().equals(funcionActualizada.getPrecio())) {
            String mensaje = "Cambio de precio: de $"
                    + funcionOriginal.getPrecio() + " a $"
                    + funcionActualizada.getPrecio();
            notificarObservadores(funcionActualizada.getIdFuncion(), "CAMBIO_PRECIO", mensaje);
        }
    }

    @Override
    public void agregarObservador(Long idFuncion, ObservadorFuncion observador) {
        observadores.computeIfAbsent(idFuncion, k -> new ArrayList<>()).add(observador);
    }

    @Override
    public void eliminarObservador(Long idFuncion, ObservadorFuncion observador) {
        if (observadores.containsKey(idFuncion)) {
            observadores.get(idFuncion).remove(observador);
        }
    }

    // metodo de eliminar oobservcador por filtro
    @Override
    public void eliminarObservadorPorFiltro(Long idFuncion, Predicate<ObservadorFuncion> filtro) {
        if (observadores.containsKey(idFuncion)) {
            observadores.get(idFuncion).removeIf(filtro);
        }
    }

    @Override
    public void notificarObservadores(Long idFuncion, String tipoEvento, String mensaje) {
        if (observadores.containsKey(idFuncion)) {
            for (ObservadorFuncion observador : observadores.get(idFuncion)) {
                observador.actualizar(idFuncion, tipoEvento, mensaje);
            }
        }
    }
}
