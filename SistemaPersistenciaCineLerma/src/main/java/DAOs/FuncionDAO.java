/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Conexion.MongoConexion;
import Excepciones.Funciones.FuncionBoletosVendidosException;
import Excepciones.Funciones.FuncionSalaOcupadaException;
import Excepciones.PersistenciaException;
import Interfaces.IFuncionDAO;
import Interfaces.IPeliculaDAO;
import Interfaces.ObservadorFuncion;
import entidades.Funcion;
import entidades.Pelicula;
import entidades.Sala;
import enums.EstadoSala;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import org.bson.types.ObjectId;

/**
 *
 * @author Ramon Valencia
 */
public class FuncionDAO implements IFuncionDAO {

    private static FuncionDAO instance;
    private final IPeliculaDAO peliculaDAO = PeliculaDAO.getInstanceDAO();
    private final MongoConexion conexion = new MongoConexion();
    private final String nombreColeccion = "Funciones";
    private Map<String, List<ObservadorFuncion>> observadores;

    private FuncionDAO() {
        this.observadores = new HashMap();
    }

    public static FuncionDAO getInstanceDAO() {
        if (instance == null) {
            instance = new FuncionDAO();
        }

        return instance;
    }

    @Override
    public List<Funcion> buscarFuncionesPelicula(String nombrePelicula) {

        return null;

    }

    @Override
    public List<Funcion> buscarFuncionesActivas() {

        return null;

    }

    @Override
    public List<Funcion> mostrarFuncionesPeliculas() {

        return null;

    }

    // metodo privado para detectar cambios y notificar
    private void detectarYNotificarCambios(Funcion funcionOriginal, Funcion funcionActualizada) {
        // Verificar cambio de estado
        if (!funcionOriginal.getEstado().equals(funcionActualizada.getEstado())) {
            String mensaje = funcionActualizada.getEstado()
                    ? "La función ha sido activada"
                    : "La función ha sido cancelada";
            notificarObservadores(funcionActualizada.getIdString(), "CAMBIO_ESTADO", mensaje);
        }

        // Verificar cambio de horario
        if (!funcionOriginal.getFechaHora().equals(funcionActualizada.getFechaHora())) {
            String mensaje = "Cambio de horario: de "
                    + funcionOriginal.getFechaHora() + " a "
                    + funcionActualizada.getFechaHora();
            notificarObservadores(funcionActualizada.getIdString(), "CAMBIO_HORARIO", mensaje);
        }

        // Verificar cambio de sala
        if (!funcionOriginal.getSala().equals(funcionActualizada.getSala())) {
            String mensaje = "Cambio de sala: de sala #"
                    + funcionOriginal.getSala().getNumSala() + " a sala #"
                    + funcionActualizada.getSala().getNumSala();
            notificarObservadores(funcionActualizada.getIdString(), "CAMBIO_SALA", mensaje);
        }

        // Verificar cambio de precio
        if (!funcionOriginal.getPrecio().equals(funcionActualizada.getPrecio())) {
            String mensaje = "Cambio de precio: de $"
                    + funcionOriginal.getPrecio() + " a $"
                    + funcionActualizada.getPrecio();
            notificarObservadores(funcionActualizada.getIdString(), "CAMBIO_PRECIO", mensaje);
        }
    }

    @Override
    public void agregarObservador(String idFuncion, ObservadorFuncion observador) {
        observadores.computeIfAbsent(idFuncion, k -> new ArrayList<>()).add(observador);
    }

    @Override
    public void eliminarObservador(String idFuncion, ObservadorFuncion observador) {
        if (observadores.containsKey(idFuncion)) {
            observadores.get(idFuncion).remove(observador);
        }
    }

    // metodo de eliminar oobservcador por filtro
    @Override
    public void eliminarObservadorPorFiltro(String idFuncion, Predicate<ObservadorFuncion> filtro) {
        if (observadores.containsKey(idFuncion)) {
            observadores.get(idFuncion).removeIf(filtro);
        }
    }

    @Override
    public void notificarObservadores(String idFuncion, String tipoEvento, String mensaje) {
        if (observadores.containsKey(idFuncion)) {
            for (ObservadorFuncion observador : observadores.get(idFuncion)) {
                observador.actualizar(idFuncion, tipoEvento, mensaje);
            }
        }
    }

    @Override
    public Funcion registrarFuncion(Funcion funcion) throws FuncionSalaOcupadaException {
        return null;
    }

    @Override
    public Funcion eliminarFuncion(Funcion funcion) throws FuncionBoletosVendidosException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Funcion buscarFuncionPorId(ObjectId idFuncion) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
