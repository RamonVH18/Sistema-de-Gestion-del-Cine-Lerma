/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestionReservaBoletos;

import DTOs.AsientoFuncionDTO;
import DTOs.ClienteDTO;
import DTOs.FuncionAsientosDTO;
import DTOs.FuncionDTO;
import DTOs.PeliculaDTO;
import Excepciones.GestionReservaException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Ramon Valencia
 */
public class ManejoDeBoletos implements IManejoDeBoletos {

    List<PeliculaDTO> peliculas = new ArrayList<>();
    List<FuncionDTO> funciones = new ArrayList<>();
    List<AsientoFuncionDTO> asientos = new ArrayList<>();

    //PELICULAS HARDCODEADAS, ESTE SERA RETIRADO EN LA VERSION FINAL
    public List<PeliculaDTO> peliculasHarcodeadas() {
        PeliculaDTO pelicula1 = new PeliculaDTO("Batman El Caballero Loco",
                "/img/batman.jpg",
                "Pelicula de las God");
        PeliculaDTO pelicula2 = new PeliculaDTO("Interstellar", "/img/interstellar.jpg", "Pelicula algo Bien");
        PeliculaDTO pelicula3 = new PeliculaDTO("John Wick 3", "/img/johnWick3.jpg", "Tercera Pelicula donde sale el John Wick");
        PeliculaDTO pelicula4 = new PeliculaDTO("Sonic 3", "/img/sonic3.jpg", "Pelicula donde sale el Sonic");
        PeliculaDTO pelicula5 = new PeliculaDTO("Wazaa la Pelicula", "/img/wazaa.jpg", "WAZAAAAAAAAAAAAAA");
        PeliculaDTO pelicula6 = new PeliculaDTO("Thor: Ragnarok", "/img/thor.jpg", "Thor, mas thor que nunca");

        peliculas.add(pelicula1);
        peliculas.add(pelicula2);
        peliculas.add(pelicula3);
        peliculas.add(pelicula4);
        peliculas.add(pelicula5);
        peliculas.add(pelicula6);
        return peliculas;
    }
    //FUNCIONES HARDCODEADAS, ESTE SERA RETIRADO EN LA VERSION FINAL
    public List<FuncionDTO> funcionesHarcodeadas() {

        FuncionDTO funcion1 = new FuncionDTO("A1", "Batman El Caballero Loco", new Date(125, 02, 24, 14, 30, 0));
        FuncionDTO funcion2 = new FuncionDTO("B1", "Batman El Caballero Loco", new Date(125, 02, 23, 17, 00, 0));

        FuncionDTO funcion3 = new FuncionDTO("A2", "Interstellar", new Date(125, 02, 25, 16, 00, 0));
        FuncionDTO funcion4 = new FuncionDTO("B1", "Interstellar", new Date(125, 02, 24, 19, 30, 0));

        FuncionDTO funcion5 = new FuncionDTO("A3", "John Wick 3", new Date(125, 02, 23, 18, 45, 0));
        FuncionDTO funcion6 = new FuncionDTO("B3", "John Wick 3", new Date(125, 02, 24, 21, 00, 0));

        FuncionDTO funcion7 = new FuncionDTO("A2", "Sonic 3", new Date(125, 02, 28, 13, 00, 0));
        FuncionDTO funcion8 = new FuncionDTO("B2", "Sonic 3", new Date(125, 02, 22, 15, 30, 0));

        FuncionDTO funcion9 = new FuncionDTO("A1", "Wazaa la Pelicula", new Date(125, 02, 25, 20, 00, 0));
        FuncionDTO funcion10 = new FuncionDTO("B3", "Wazaa la Pelicula", new Date(125, 02, 26, 22, 15, 0));

        FuncionDTO funcion11 = new FuncionDTO("A2", "Thor: Ragnarok", new Date(125, 02, 27, 17, 30, 0));
        FuncionDTO funcion12 = new FuncionDTO("B2", "Thor: Ragnarok", new Date(125, 02, 26, 20, 45, 0));

        funciones.add(funcion1);
        funciones.add(funcion2);
        funciones.add(funcion3);
        funciones.add(funcion4);
        funciones.add(funcion5);
        funciones.add(funcion6);
        funciones.add(funcion7);
        funciones.add(funcion8);
        funciones.add(funcion9);
        funciones.add(funcion10);
        funciones.add(funcion11);
        funciones.add(funcion12);

        return funciones;
    }
    
    //ASIENTOS HARDCODEADOS, ESTE SERA RETIRADO EN LA VERSION FINAL
    public List<AsientoFuncionDTO> asientosHarcodeados() {
        if (asientos.isEmpty()) {
            List<FuncionDTO> funciones = funcionesHarcodeadas();
            for (int i = 0; i < funciones.size(); i++) {
                FuncionDTO funcion = funciones.get(i);
                for (int s = 0; s < 25; s++) {
                    String numero = String.valueOf(s + 1);
                    AsientoFuncionDTO asiento = new AsientoFuncionDTO(funcion, numero, true, new ClienteDTO());
                    asientos.add(asiento);
                }
            }
        }
        return asientos;
    }

    /**
     * Metodo que sirve para crear una lista de las peliculas que se van a
     * mostrar en Cartelera, AUN NO ES LA VERSION FINAL DEL METODO
     *
     * @return
     * @throws GestionReservaException
     */
    @Override
    public List<PeliculaDTO> cargarPeliculasActivas() throws GestionReservaException {
        try {
            // aqui se llamaria a un metodo que de una listapeliculas, sin embargo como aun no tenemos la BO, voy hardcodearlas

            List<PeliculaDTO> peliculas = peliculasHarcodeadas();
            return peliculas;

        } catch (Exception e) {
            throw new GestionReservaException("Hubo un error al cargar la lista de Peliculas: ", e.getCause());
        }
    }

    @Override
    public List<FuncionDTO> cargarFuncionesDelDia(Date dia, String nombrePelicula) throws GestionReservaException {
        try {
            // aqui se llamaria a un metodo que de una listaFunciones, sin embargo como aun no tenemos la BO, voy hardcodearlas
            List<FuncionDTO> funciones = funcionesHarcodeadas();

            //Lista donde se guardada las funciones del dia
            List<FuncionDTO> funcionDia = new ArrayList<>();
            //En este for se van a filtrar las funciones y se guardaran solo las funciones que sean del dia correspondiente
            for (int i = 0; i < funciones.size(); i++) {
                FuncionDTO funcion = funciones.get(i);
                if (funcion.getNombre() == nombrePelicula && funcion.getFechaHora().getDay() == dia.getDay()) {
                    funcionDia.add(funcion);
                }
            }
            return funcionDia;
        } catch (Exception e) {
            throw new GestionReservaException("Hubo un error al obtener las peliculas del dia " + dia.getDay(), e.getCause());
        }
    }

    @Override
    public boolean validarCampoAsiento(String campoAsiento) throws GestionReservaException {
        try {

            if (campoAsiento == null || campoAsiento.isBlank()) {
                throw new GestionReservaException("El Campo de Asiento no puede estar vacio.");
            }

            if (!campoAsiento.matches("\\d+")) {
                throw new GestionReservaException("Solo puede ingresar digitos en este campo.");
            }

            return true;
        } catch (Exception e) {
            throw new GestionReservaException("Hubo un error al validar el campoAsiento", e.getCause());
        }
    }
    
    public int consultar

}
