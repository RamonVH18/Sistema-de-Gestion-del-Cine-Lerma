/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import entidades.Funcion;
import entidades.Pelicula;
import entidades.Sala;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Ramon Valencia
 */
public class FuncionDAO {

    private static FuncionDAO instance;

    private FuncionDAO() {
    }

    public FuncionDAO getInstanceDAO() {
        if (instance == null) {
            instance = new FuncionDAO();
        }

        return instance;
    }

    List<Funcion> funciones = new ArrayList<>();

    public List<Funcion> funcionesHarcodeadas() {
        if (funciones.isEmpty()) {
            // Crear salas
            Sala salaA1 = new Sala(1L, 100, 1, true);
            Sala salaA2 = new Sala(2L, 90, 2, true);
            Sala salaA3 = new Sala(3L, 80, 3, true);
            Sala salaB1 = new Sala(4L, 120, 4, true);
            Sala salaB2 = new Sala(5L, 110, 5, true);
            Sala salaB3 = new Sala(6L, 105, 6, true);

            // Crear películas
            Pelicula batman = new Pelicula(1L,
                    "Batman El Caballero Loco",
                    "img/batman.jpg",
                    "Accion",
                    152,
                    "Pelicula de las God",
                    true
            );
            Pelicula interstellar = new Pelicula(2L, "Interstellar", "img/interstellar.jpg", "Ciencia Funcion", 169, "Pelicula algo Bien", true);
            Pelicula johnWick = new Pelicula(3L, "John Wick 3", "img/johnWick3.jpg", "Accion", 131, "Tercera Pelicula donde sale el John Wick", true);
            Pelicula wazaa = new Pelicula(4L, "La vida es bella", "img/vidaBella.jpg", "Drama", 116, "La pelicula favorita del jaime", false);
            Pelicula thor = new Pelicula(5L, "Wazaa la Pelicula", "img/wazaa.jpg", "Comedia", 88, "WAZAAAAAAAAAAAAAA", true);
            Pelicula bella = new Pelicula(6L, "Thor: Ragnarok", "img/thor.jpg", "Accion", 130, "Thor, mas thor que nunca", false);
            Pelicula cholos = new Pelicula(7L, "Cholos Empericados 2", "img/CHOLOS_EMPERICADOS.jpg", "Romance", 134, "Mas empericados que nunca...", false);
            Pelicula pancho = new Pelicula(8L, "Pancho el perro millonario", "img/PANCHO.jpg", "Comedia", 125, "Pancho se mete en problemas", true);
            Pelicula gemelas = new Pelicula(9L, "Juego de gemelas", "img/JUEGOGEM.jpg", "Comedia", 118, "juego de gemelas", true);
            Pelicula tacos = new Pelicula(10L, "Tacos al carbon", "img/tacosCarbon.jpg", "Comida", 54, "Dos amigos con una idea loca, haran las cosas un poco diferente y haran tacos al carbon", false);
            Pelicula jamesMono = new Pelicula(11L, "James mono: El detective", "img/jamesMono.jpg", "Drama", 123, "El mayor detective de la historia", false);

            // Agregar funciones
            funciones.add(new Funcion(1L, salaA1, batman, LocalDateTime.of(2025, 3, 24, 14, 30), true, 80.00));
            funciones.add(new Funcion(2L, salaB1, batman, LocalDateTime.of(2025, 3, 25, 17, 0), true, 75.00));

            funciones.add(new Funcion(3L, salaA2, interstellar, LocalDateTime.of(2025, 3, 25, 16, 0), true, 75.00));
            funciones.add(new Funcion(4L, salaB1, interstellar, LocalDateTime.of(2025, 3, 29, 19, 30), true, 75.00));
            funciones.add(new Funcion(5L, salaA2, interstellar, LocalDateTime.of(2025, 3, 28, 13, 0), true, 60.00));
            funciones.add(new Funcion(6L, salaB2, interstellar, LocalDateTime.of(2025, 3, 25, 23, 59), true, 60.00));

            funciones.add(new Funcion(7L, salaA3, johnWick, LocalDateTime.of(2025, 3, 27, 18, 45), true, 80.00));
            funciones.add(new Funcion(8L, salaB3, johnWick, LocalDateTime.of(2025, 3, 26, 21, 0), true, 80.00));

            funciones.add(new Funcion(9L, salaA1, wazaa, LocalDateTime.of(2025, 3, 26, 20, 0), true, 90.00));
            funciones.add(new Funcion(10L, salaB3, wazaa, LocalDateTime.of(2025, 3, 26, 22, 15), true, 75.00));

            funciones.add(new Funcion(11L, salaA2, thor, LocalDateTime.of(2025, 3, 27, 17, 30), true, 65.00));
            funciones.add(new Funcion(12L, salaB2, thor, LocalDateTime.of(2025, 3, 26, 20, 45), true, 75.00));

            funciones.add(new Funcion(13L, salaA2, bella, LocalDateTime.of(2025, 3, 27, 17, 30), true, 65.00));
            funciones.add(new Funcion(14L, salaB2, bella, LocalDateTime.of(2025, 3, 26, 20, 45), true, 75.00));

            funciones.add(new Funcion(15L, salaA1, cholos, LocalDateTime.of(2025, 3, 27, 17, 30), true, 65.00));
            funciones.add(new Funcion(16L, salaB1, cholos, LocalDateTime.of(2025, 3, 26, 20, 45), true, 75.00));

            funciones.add(new Funcion(17L, salaA1, pancho, LocalDateTime.of(2025, 3, 27, 17, 30), true, 65.00));
            funciones.add(new Funcion(18L, salaB1, pancho, LocalDateTime.of(2025, 3, 26, 20, 45), true, 75.00));

            funciones.add(new Funcion(19L, salaA1, gemelas, LocalDateTime.of(2025, 3, 27, 17, 30), true, 65.00));
            funciones.add(new Funcion(20L, salaB1, gemelas, LocalDateTime.of(2025, 3, 26, 20, 45), true, 75.00));

            funciones.add(new Funcion(21L, salaA1, tacos, LocalDateTime.of(2025, 3, 27, 17, 30), true, 65.00));
            funciones.add(new Funcion(22L, salaB1, tacos, LocalDateTime.of(2025, 3, 26, 20, 45), true, 75.00));

            funciones.add(new Funcion(23L, salaA1, jamesMono, LocalDateTime.of(2025, 3, 27, 17, 30), true, 65.00));
            funciones.add(new Funcion(24L, salaB1, jamesMono, LocalDateTime.of(2025, 3, 26, 20, 45), true, 75.00));

        }

        return funciones;
    }
    
    
    
}
