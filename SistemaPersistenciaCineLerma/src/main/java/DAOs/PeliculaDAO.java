/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import entidades.Pelicula;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ramon Valencia
 */
public class PeliculaDAO {
    
    private static PeliculaDAO instance;
    
    private PeliculaDAO() {
    }
    
    public PeliculaDAO getInstanceDAO(){
        if(instance == null) {
            instance = new PeliculaDAO();
        }
        
        return instance;
    }
    
    List<Pelicula> peliculas = new ArrayList<>();
    
    public List<Pelicula> peliculasHarcodeadas() {
        if (peliculas.isEmpty()){
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
        Pelicula pelicula5 = new Pelicula(5L, "Wazaa la Pelicula", "img/wazaa.jpg", "Comedia",  88, "WAZAAAAAAAAAAAAAA", true);
        Pelicula pelicula6 = new Pelicula(6L, "Thor: Ragnarok", "img/thor.jpg", "Accion", 130,"Thor, mas thor que nunca", false);
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
}
