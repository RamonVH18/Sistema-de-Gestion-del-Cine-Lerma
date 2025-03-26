/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestionReservaBoletos;

import DTOs.AsientoFuncionDTO;
import DTOs.BoletoDTO;
import DTOs.ClienteDTO;
import DTOs.FuncionAsientosDTO;
import DTOs.FuncionDTO;
import DTOs.MetodoPagoDTO;
import DTOs.PagoDTO;
import DTOs.PeliculaDTO;
import Excepciones.GestionReservaException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ramon Valencia
 */
public class ManejoDeBoletos implements IManejoDeBoletos {

    private static final Logger logger = Logger.getLogger(Logger.class.getName());

    List<PeliculaDTO> peliculas = new ArrayList<>();
    List<FuncionDTO> funciones = new ArrayList<>();
    List<AsientoFuncionDTO> asientos = new ArrayList<>();
    List<MetodoPagoDTO> metodosPago = new ArrayList<>();
    
    private static ManejoDeBoletos instancia;
    
    private ManejoDeBoletos() { 
        // Constructor privado para evitar múltiples instancias 
    }
    
    public static ManejoDeBoletos getInstancia() {
        if (instancia == null) {
            instancia = new ManejoDeBoletos();
        }
        return instancia;
    }
    
    
    
    //PELICULAS HARDCODEADAS, ESTE SERA RETIRADO EN LA VERSION FINAL
    public List<PeliculaDTO> peliculasHarcodeadas() {
        if (peliculas.isEmpty()){
        PeliculaDTO pelicula1 = new PeliculaDTO("Batman El Caballero Loco",
                "img/batman.jpg",
                "Pelicula de las God");
        PeliculaDTO pelicula2 = new PeliculaDTO("Interstellar", "img/interstellar.jpg", "Pelicula algo Bien");
        PeliculaDTO pelicula3 = new PeliculaDTO("John Wick 3", "img/johnWick3.jpg", "Tercera Pelicula donde sale el John Wick");
        PeliculaDTO pelicula4 = new PeliculaDTO("Sonic 3", "img/sonic3.jpg", "Pelicula donde sale el Sonic");
        PeliculaDTO pelicula5 = new PeliculaDTO("Wazaa la Pelicula", "img/wazaa.jpg", "WAZAAAAAAAAAAAAAA");
        PeliculaDTO pelicula6 = new PeliculaDTO("Thor: Ragnarok", "img/thor.jpg", "Thor, mas thor que nunca");

        peliculas.add(pelicula1);
        peliculas.add(pelicula2);
        peliculas.add(pelicula3);
        peliculas.add(pelicula4);
        peliculas.add(pelicula5);
        peliculas.add(pelicula6);
        }
        return peliculas;
    }

    //FUNCIONES HARDCODEADAS, ESTE SERA RETIRADO EN LA VERSION FINAL
    public List<FuncionDTO> funcionesHarcodeadas() {
        if (funciones.isEmpty()) {
            FuncionDTO funcion1 = new FuncionDTO("A1", "Batman El Caballero Loco", new Date(125, 02, 24, 14, 30, 0), 80.00);
            FuncionDTO funcion2 = new FuncionDTO("B1", "Batman El Caballero Loco", new Date(125, 02, 25, 17, 00, 0), 75.00);

            FuncionDTO funcion3 = new FuncionDTO("A2", "Interstellar", new Date(125, 02, 25, 16, 00, 0), 75.00);
            FuncionDTO funcion4 = new FuncionDTO("B1", "Interstellar", new Date(125, 02, 29, 19, 30, 0), 75.00);

            FuncionDTO funcion5 = new FuncionDTO("A3", "John Wick 3", new Date(125, 02, 27, 18, 45, 0), 80.00);
            FuncionDTO funcion6 = new FuncionDTO("B3", "John Wick 3", new Date(125, 02, 26, 21, 00, 0), 80.00);

            FuncionDTO funcion7 = new FuncionDTO("A2", "Interstellar", new Date(125, 02, 28, 13, 00, 0), 60.00);
            FuncionDTO funcion8 = new FuncionDTO("B2", "Interstellar", new Date(125, 02, 25, 23, 59, 0), 60.00);

            FuncionDTO funcion9 = new FuncionDTO("A1", "Wazaa la Pelicula", new Date(125, 02, 26, 20, 00, 0), 90.00);
            FuncionDTO funcion10 = new FuncionDTO("B3", "Wazaa la Pelicula", new Date(125, 02, 26, 22, 15, 0), 75.00);

            FuncionDTO funcion11 = new FuncionDTO("A2", "Thor: Ragnarok", new Date(125, 02, 27, 17, 30, 0), 65.00);
            FuncionDTO funcion12 = new FuncionDTO("B2", "Thor: Ragnarok", new Date(125, 02, 26, 20, 45, 0), 75.00);

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
        }

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
    
    //METODOS DE PAGO HARDCODEADOS
    public List<MetodoPagoDTO> metodosPagoHarcodeados() {
        if (metodosPago.isEmpty()) {
            MetodoPagoDTO mercadoPago = new MetodoPagoDTO("Mercado Pago", "img/mercadoPago.jpg");
            MetodoPagoDTO paypal = new MetodoPagoDTO("Paypal", "img/paypal.png");
            MetodoPagoDTO tarjeta = new MetodoPagoDTO("Tarjeta", "img/visamaster.png");
            
            metodosPago.add(tarjeta);
            metodosPago.add(mercadoPago);
            metodosPago.add(paypal);
        }
        return metodosPago;
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
            throw new GestionReservaException("ERROR: Hubo un error al cargar la lista de Peliculas: " + e.getMessage());
        }
    }

    @Override
    public List<FuncionDTO> cargarFuncionesPelicula(String nombrePelicula) throws GestionReservaException {
        try {
            if (nombrePelicula == null || nombrePelicula.isBlank()) {
                throw new GestionReservaException("El nombre de la pelicula esta vacio o es nulo");
            }
            // aqui se llamaria a un metodo que de una listaFunciones, sin embargo como aun no tenemos la BO, voy hardcodearlas
            List<FuncionDTO> funciones = funcionesHarcodeadas();
            //Lista donde se guardada las funciones del dia
            List<FuncionDTO> funcionesPelicula = new ArrayList<>();
            //En este for se van a filtrar las funciones y se guardaran solo las funciones que sean del dia correspondiente
            
            for (int i = 0; i < funciones.size(); i++) {
                FuncionDTO funcion = funciones.get(i);
                if (funcion.getNombre() == nombrePelicula) {
                    funcionesPelicula.add(funcion);
                }
            }
            //Funcion usada para poder ordernar la lista de funciones, para de esa manera mostrar las que van primero en fecha hora
            funcionesPelicula.sort(Comparator.comparing(e -> e.getFechaHora()));
            
            return funcionesPelicula;
        } catch (Exception e) {
            throw new GestionReservaException("ERROR: " + e.getMessage());
        }
    }

    @Override //Metodo para validar el campoAsiento de la pantalla Seleccionar Asientos
    public boolean validarCampoAsiento(String campoAsiento, FuncionDTO funcion) throws GestionReservaException {
        try {
            if (funcion == null) {
                throw new GestionReservaException("Debe de ingresar una funcion.");
            }
            if (campoAsiento == null || campoAsiento.isBlank()) {
                throw new GestionReservaException("El campo de numero de asientos no puede estar vacio.");
            }

            if (!campoAsiento.matches("\\d+")) {
                throw new GestionReservaException("Solo puede ingresar digitos en el campo numero de asientos.");
            }

            return true;
        } catch (Exception e) {
            throw new GestionReservaException("ERROR: " + e.getMessage());
        }
    }

    // Metodo que solo se utilizara en esta clase, sera uitilizado para filtrar la lista de asientos y guardar unicamente los asientos que esten disponibles
    public List<AsientoFuncionDTO> listaAsientosDisponibles(FuncionDTO funcion) {
        // Aqui se llamaria un metodo para consultar a los asientos de la respectiva funcion, pero eso sera hasta que agreguemos los BOs
        
        //Lista que servira para guardar aquellos asientos vacios
        asientos = asientosHarcodeados();
        List<AsientoFuncionDTO> asientosDisponibles = new ArrayList<>();
        for (int i = 0; i < asientos.size(); i++) {
            AsientoFuncionDTO asiento = asientos.get(i);
            if (asiento.getFuncion() == funcion && asiento.isDisponibilidad()) {
                asientosDisponibles.add(asiento);
            }
        }
        return asientosDisponibles;
    }

    @Override //Metodo para consultar cuantos Asientos disponibles haya para cierta funcion
    public int consultarDisponibilidadAsientos(FuncionDTO funcion) throws GestionReservaException {
        try {
            if (funcion == null) {
                throw new GestionReservaException("La funcion no puede ser nula.");
            }
            List<AsientoFuncionDTO> asientosDisponibles = listaAsientosDisponibles(funcion);
            int cuenta = 0;
            for (int i = 0; i < asientosDisponibles.size(); i++) {
                if (asientosDisponibles.get(i).isDisponibilidad()) {
                    cuenta++;
                }
            }
            return cuenta;
        } catch (Exception e) {
            throw new GestionReservaException("Hubo un error al contabilizar el numero de asientos disponibles.", e.getCause());
        }
    }

    @Override //Metodo para validar que el numero de asientos selecionados este disponible
    public boolean validarDisponibilidaDeAsientos(int numAsientos, FuncionDTO funcion) throws GestionReservaException {
        try {
            
            if (numAsientos <= 0) {
                throw new GestionReservaException("Tiene que ingresar minimo 1 asiento.");
            }
            //Llamada al metodo para consultar los asientos disponibles de esa funcion
            List<AsientoFuncionDTO> asientosDisponibles = listaAsientosDisponibles(funcion);
            if (asientosDisponibles.size() < numAsientos) {
                throw new GestionReservaException("Ingreso mas asientos de los que hay disponibles.");
            }
            return true;
        } catch (Exception e) {
            throw new GestionReservaException("ERROR: " + e.getMessage());
        }
    }

    @Override //Metodo que sirve para calcular el costo final del boleto
    public double calcularCostoTotal(int numAsientos, FuncionDTO funcion) throws GestionReservaException {
        try {
            if (numAsientos <= 0) {
                throw new GestionReservaException("Tiene que ingresar minimo 1 asiento.");
            }
            if (funcion == null) {
                throw new GestionReservaException("La funcion no puede ser nula.");
            }
            double precio = funcion.getPrecio();
            return precio * numAsientos;
        } catch (Exception e) {
            throw new GestionReservaException("Hubo un problema al calcular el costo total del boleto");
        }
    }

    @Override 
    public List<MetodoPagoDTO> cargarMetodosPago() throws GestionReservaException {
        try {
            List<MetodoPagoDTO> metodosPago = metodosPagoHarcodeados();
            return metodosPago;
        } catch (Exception e) {
            throw new GestionReservaException("ERROR: Hubo un error al cargar los metodos de pago: " + e.getMessage());
        }
    }
    
    @Override
    public BoletoDTO generarBoleto(PeliculaDTO pelicula, FuncionDTO funcion, List<String> asientos, ClienteDTO cliente) throws GestionReservaException {
        try {
            if (pelicula == null) {
                throw new GestionReservaException("La pelicula no puede ser nula.");
            }
            if (funcion == null) {
                throw new GestionReservaException("La funcion no puede ser nula.");
            }
            if (asientos == null || asientos.isEmpty()) {
                throw new GestionReservaException("Debe proporcionar al menos un asiento.");
            }
            //Aqui abajo se añadira un metodo que registre el Boleto 
            return new BoletoDTO(pelicula.getNombrePelicula(), pelicula.getPeliculaImagen(), funcion.getFechaHora(), funcion.getSala(), asientos, cliente.getNombre());

        } catch (Exception e) {
            throw new GestionReservaException("ERROR: " + e.getMessage());
        }
    }

    //ESTE METODO CAMBIARA CASI POR COMPLETO
    @Override
    public List<String> reservarAsientoFuncion(FuncionDTO funcion, int numAsiento, ClienteDTO cliente) throws GestionReservaException {
        try {
            if (funcion == null) {
                throw new GestionReservaException("La funcion no puede ser nula.");
            }
            if (numAsiento < 1) {
                throw new GestionReservaException("No puede haber menos de 1 asiento.");
            }
            if (cliente == null) {
                throw new GestionReservaException("El cliente no puede ser nula.");
            }

            List<String> numAsientos = new ArrayList<>();
            
            int conta = 0;
            asientos = asientosHarcodeados();

            for (int i = 0; i < asientos.size(); i++) {
                if (conta == numAsiento) {
                    break;
                }
                AsientoFuncionDTO asiento = asientos.get(i);
                
                
                
                // Validar que la función y sala coincidan
                if (asiento.getFuncion().getSala().equals(funcion.getSala()) && asiento.isDisponibilidad()
                        && asiento.getFuncion().getFechaHora().equals(funcion.getFechaHora())) {

                    // Reservar el asiento
                    asiento.setCliente(cliente);
                    asiento.setDisponibilidad(false);
                    asientos.set(i, asiento);
                    numAsientos.add(asiento.getAsiento());
                    conta++;

                }
            }

            return numAsientos;
        } catch (Exception e) {
            throw new GestionReservaException("ERROR: " + e.getMessage());
        }

    }
}
