/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Excepciones.PersistenciaException;
import Interfaces.IPeliculaDAO;
import entidades.Pelicula;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ramon Valencia
 */
public class PeliculaDAO implements IPeliculaDAO {

    private static PeliculaDAO instance;
    private List<Pelicula> peliculas;

    private PeliculaDAO() {
        peliculas = new ArrayList<>();
        peliculasHarcodeadas();
    }

    public PeliculaDAO getInstanceDAO() {
        if (instance == null) {
            instance = new PeliculaDAO();
        }

        return instance;
    }

    public List<Pelicula> peliculasHarcodeadas() {
        if (peliculas.isEmpty()) {
            Pelicula pelicula1 = new Pelicula(1L,
                    "Batman El Caballero Loco",
                    "img/batman.jpg",
                    "Accion",
                    152,
                    "Pelicula de las God",
                    true
            );
            Pelicula pelicula2 = new Pelicula(2L, "Interstellar", "img/interstellar.jpg", "Ciencia Funcion", 169, "Pelicula algo Bien", true);
            Pelicula pelicula3 = new Pelicula(3L, "John Wick 3", "img/johnWick3.jpg", "Accion", 131, "Tercera Pelicula donde sale el John Wick", true);
            Pelicula pelicula4 = new Pelicula(4L, "La vida es bella", "img/vidaBella.jpg", "Drama", 116, "La pelicula favorita del jaime", false);
            Pelicula pelicula5 = new Pelicula(5L, "Wazaa la Pelicula", "img/wazaa.jpg", "Comedia", 88, "WAZAAAAAAAAAAAAAA", true);
            Pelicula pelicula6 = new Pelicula(6L, "Thor: Ragnarok", "img/thor.jpg", "Accion", 130, "Thor, mas thor que nunca", false);
            Pelicula pelicula7 = new Pelicula(7L, "Cholos Empericados 2", "img/CHOLOS_EMPERICADOS.jpg", "Romance", 134, "Mas empericados que nunca...", false);
            Pelicula pelicula8 = new Pelicula(8L, "Pancho el perro millonario", "img/PANCHO.jpg", "Comedia", 125, "Pancho se mete en problemas", true);
            Pelicula pelicula9 = new Pelicula(9L, "Juego de gemelas", "img/JUEGOGEM.jpg", "Comedia", 118, "juego de gemelas", true);
            Pelicula pelicula10 = new Pelicula(10L, "Tacos al carbon", "img/tacosCarbon.jpg", "Comida", 54, "Dos amigos con una idea loca, haran las cosas un poco diferente y haran tacos al carbon", false);
            Pelicula pelicula11 = new Pelicula(11L, "James mono: El detective", "img/jamesMono.jpg", "Drama", 123, "El mayor detective de la historia", false);

            peliculas.add(pelicula1);
            peliculas.add(pelicula2);
            peliculas.add(pelicula3);
            peliculas.add(pelicula4);
            peliculas.add(pelicula5);
            peliculas.add(pelicula6);
            peliculas.add(pelicula7);
            peliculas.add(pelicula8);
            peliculas.add(pelicula9);
            peliculas.add(pelicula10);
            peliculas.add(pelicula11);
        }
        return peliculas;
    }

    @Override
    public Pelicula registrarPelicula(Pelicula pelicula) throws PersistenciaException {
        Long nuevoId = peliculas.get(peliculas.size() - 1).getId() + 1L;
        pelicula.setId(nuevoId);
        peliculas.add(pelicula);
        return pelicula;
    }

    @Override
    public Pelicula actualizarPelicula(Pelicula peliculaActualizar) throws PersistenciaException {
        for (int i = 0; i < peliculas.size(); i++) {
            if (peliculas.get(i).getId().equals(peliculaActualizar.getId())) {
                peliculas.set(i, peliculaActualizar);
                return peliculaActualizar;
            }
        }
        throw new PersistenciaException("Error: Pelicula no encontrada");
    }

    @Override
    public Boolean eliminarPelicula(Long id) throws PersistenciaException {
        for (Pelicula pelicula : peliculas) {
            if (pelicula.getId().equals(id)) {
                peliculas.remove(pelicula);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Pelicula> mostrarListaPelicula() throws PersistenciaException {
        List<Pelicula> copia = new ArrayList<>();
        copia.addAll(peliculas);
        return copia;
    }

    @Override
    public Boolean actualizarEstado(Long id) throws PersistenciaException {
        for (Pelicula pelicula : peliculas) {
            if (pelicula.getId().equals(id)) {
                Boolean nuevoEstado = !pelicula.getEstado();
                pelicula.setEstado(nuevoEstado);
                return true;
            }
        }
        return false;
    }

    @Override
    public Pelicula buscarPelicula(String titulo) throws PersistenciaException {
        for (Pelicula pelicula : peliculas) {
            if (pelicula.getTitulo().equalsIgnoreCase(titulo)) {
                return pelicula;
            }
        }
        throw new PersistenciaException("Pelicula no encontrada" + titulo);
    }
}
