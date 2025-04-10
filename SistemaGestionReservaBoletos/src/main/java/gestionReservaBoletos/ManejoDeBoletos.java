/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestionReservaBoletos;

import DTOs.AsientoFuncionDTO;
import DTOs.BoletoDTO;
import DTOs.ClienteDTO;
import DTOs.FuncionDTO;
import DTOs.MetodoPagoDTO;
import DTOs.PeliculaDTO;
import Excepciones.CalcularCostoTotalException;
import Excepciones.DisponibilidadAsientosException;
import Excepciones.FuncionCargaException;
import Excepciones.GestionReservaException;
import Excepciones.PeliculasCargaException;
import Excepciones.GenerarBoletoException;
import Excepciones.ReservarAsientoFuncionException;
import Excepciones.ValidarCampoAsientoException;
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
    

    //FUNCIONES HARDCODEADAS, ESTE SERA RETIRADO EN LA VERSION FINAL
    

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
    public List<PeliculaDTO> cargarPeliculasActivas() throws PeliculasCargaException {
        try {
            // aqui se llamaria a un metodo que de una listapeliculas, sin embargo como aun no tenemos la BO, voy hardcodearlas
            List<PeliculaDTO> peliculas = peliculasHarcodeadas();
            if (peliculas.isEmpty() || peliculas == null) {
                throw new PeliculasCargaException("Hubo un error al cargar las peliculas, favor de ingresar mas al rato.");
            }
            return peliculas;

        } catch (Exception e) {
            throw new PeliculasCargaException("ERROR: " + e.getMessage());
        }
    }

    @Override
    public List<FuncionDTO> cargarFuncionesPelicula(String nombrePelicula) throws FuncionCargaException {
        try {
            if (nombrePelicula == null || nombrePelicula.isBlank()) {
                throw new FuncionCargaException("El nombre de la pelicula esta vacio o es nulo");
            }
            // aqui se llamaria a un metodo que de una listaFunciones, sin embargo como aun no tenemos la BO, voy hardcodearlas
            List<FuncionDTO> funciones = funcionesHarcodeadas();
            if (funciones.isEmpty() || funciones == null) {
                throw new FuncionCargaException("Hubo un error al cargar las funciones, favor de ingresar mas al rato.");
            }
            //Lista donde se guardada las funciones del dia
            List<FuncionDTO> funcionesPelicula = new ArrayList<>();
            
            //En este for se van a filtrar las funciones y se guardaran solo las funciones que sean del dia correspondiente
            
            for (int i = 0; i < funciones.size(); i++) {
                FuncionDTO funcion = funciones.get(i);
                if (funcion.getNombre() == nombrePelicula) {
                    funcionesPelicula.add(funcion);
                }
            }
            
            if (funcionesPelicula == null || funcionesPelicula.isEmpty()) {
                throw new FuncionCargaException("Hubo un error al cargar las funciones de esta pelicula, seleccione otra pelicula o ingrese mas al rato.");
            }
            //Funcion usada para poder ordernar la lista de funciones, para de esa manera mostrar las que van primero en fecha hora
            funcionesPelicula.sort(Comparator.comparing(e -> e.getFechaHora()));
            
            return funcionesPelicula;
        } catch (FuncionCargaException e) {
            throw new FuncionCargaException("ERROR: " + e.getMessage());
        }
    }

    @Override //Metodo para validar el campoAsiento de la pantalla Seleccionar Asientos
    public boolean validarCampoAsiento(String campoAsiento, FuncionDTO funcion) throws ValidarCampoAsientoException {
        try {
            if (funcion == null) {
                throw new ValidarCampoAsientoException("Debe de ingresar una funcion.");
            }
            if (campoAsiento == null || campoAsiento.isBlank()) {
                throw new ValidarCampoAsientoException("El campo de numero de asientos no puede estar vacio.");
            }

            if (!campoAsiento.matches("\\d+")) {
                throw new ValidarCampoAsientoException("Solo puede ingresar digitos en el campo numero de asientos.");
            }

            return true;
        } catch (Exception e) {
            throw new ValidarCampoAsientoException("ERROR: " + e.getMessage());
        }
    }

    // Metodo que solo se utilizara en esta clase, sera uitilizado para filtrar la lista de asientos y guardar unicamente los asientos que esten disponibles
    public List<AsientoFuncionDTO> listaAsientosDisponibles(FuncionDTO funcion) throws DisponibilidadAsientosException {
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
    public int consultarDisponibilidadAsientos(FuncionDTO funcion) throws DisponibilidadAsientosException {
        try {
            if (funcion == null) {
                throw new DisponibilidadAsientosException("La funcion no puede ser nula.");
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
            throw new DisponibilidadAsientosException("Hubo un error al contabilizar el numero de asientos disponibles.", e.getCause());
        }
    }

    @Override //Metodo para validar que el numero de asientos selecionados este disponible
    public boolean validarDisponibilidaDeAsientos(int numAsientos, FuncionDTO funcion) throws DisponibilidadAsientosException {
        try {
            
            if (numAsientos <= 0) {
                throw new DisponibilidadAsientosException("Tiene que ingresar minimo 1 asiento.");
            }
            //Llamada al metodo para consultar los asientos disponibles de esa funcion
            List<AsientoFuncionDTO> asientosDisponibles = listaAsientosDisponibles(funcion);
            if (asientosDisponibles.size() < numAsientos) {
                throw new DisponibilidadAsientosException("Ingreso mas asientos de los que hay disponibles.");
            }
            return true;
        } catch (Exception e) {
            throw new DisponibilidadAsientosException("ERROR: " + e.getMessage());
        }
    }

    @Override //Metodo que sirve para calcular el costo final del boleto
    public double calcularCostoTotal(int numAsientos, FuncionDTO funcion) throws CalcularCostoTotalException {
        try {
            if (numAsientos <= 0) {
                throw new CalcularCostoTotalException("Tiene que ingresar minimo 1 asiento.");
            }
            if (funcion == null) {
                throw new CalcularCostoTotalException("La funcion no puede ser nula.");
            }
            double precio = funcion.getPrecio();
            return precio * numAsientos;
        } catch (Exception e) {
            throw new CalcularCostoTotalException("Hubo un problema al calcular el costo total del boleto");
        }
    }

    @Override 
    public List<MetodoPagoDTO> cargarMetodosPago() {
            List<MetodoPagoDTO> metodosPago = metodosPagoHarcodeados();
            return metodosPago;
    }
    
    @Override
    public BoletoDTO generarBoleto(PeliculaDTO pelicula, FuncionDTO funcion, List<String> asientos, ClienteDTO cliente) throws GenerarBoletoException {
        try {
            if (pelicula == null) {
                throw new GenerarBoletoException("Hubo un problema al guardar la pelicula en el boleto. Intente mas tarde.");
            }
            if (funcion == null) {
                throw new GenerarBoletoException("Hubo un problema al guardar la funcion en el boleto. Intente mas tarde.");
            }
            if (asientos == null || asientos.isEmpty()) {
                throw new GenerarBoletoException("Hubo un problema al guardar los asientos en el boleto. Intente mas tarde.");
            }
            //Aqui abajo se añadira un metodo que registre el Boleto 
            return new BoletoDTO(pelicula.getNombrePelicula(), pelicula.getPeliculaImagen(), funcion.getFechaHora(), funcion.getSala(), asientos, cliente.getNombre());

        } catch (Exception e) {
            throw new GenerarBoletoException("ERROR: " + e.getMessage());
        }
    }

    //ESTE METODO CAMBIARA CASI POR COMPLETO
    @Override
    public List<String> reservarAsientoFuncion(FuncionDTO funcion, int numAsiento, ClienteDTO cliente) throws ReservarAsientoFuncionException {
        
        try {
            if (funcion == null) {
                throw new ReservarAsientoFuncionException("La funcion no puede ser nula.");
            }
            if (numAsiento < 1) {
                throw new ReservarAsientoFuncionException("No puede haber menos de 1 asiento.");
            }
            if (cliente == null) {
                throw new ReservarAsientoFuncionException("El cliente no puede ser nula.");
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
            throw new ReservarAsientoFuncionException("ERROR: " + e.getMessage());
        }

    }
}
