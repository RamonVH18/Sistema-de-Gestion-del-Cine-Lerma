/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capaPresentacion;

import DTOs.BoletoDTO;
import DTOs.ClienteDTO;
import DTOs.CuentaMercadoDTO;
import DTOs.FuncionDTO;
import DTOs.MetodoPagoDTO;
import DTOs.PaypalDTO;
import DTOs.PeliculaDTO;
import DTOs.TarjetaDTO;
import Excepciones.GestionReservaException;
import Excepciones.TransferenciaException;
import com.google.zxing.WriterException;
import gestionPagos.GestionPagos;
import gestionPagos.IGestionPagos;
import gestionReservaBoletos.IManejoDeBoletos;
import gestionReservaBoletos.ManejoDeBoletos;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

/**
 *
 * @author Ramon Valencia
 */
public class ControlDeNavegacion {

    //Instancias y clases para llamar metodos
    private IManejoDeBoletos manejoDeBoletos = ManejoDeBoletos.getInstancia();
    private IGestionPagos gestionDePagos = GestionPagos.getInstancia();

    //Variables que hay que checar
    private PeliculaDTO peliculaSeleccionada = new PeliculaDTO();
    private FuncionDTO funcionSeleccionada = new FuncionDTO();

    private List<String> asientos = new ArrayList<>();
    
    private int numAsientos = 0;
    
    private ClienteDTO cliente = new ClienteDTO("Abrahama Coronel Garcia", "jaime@lerma.com.mx");

    private static ControlDeNavegacion instancia;

    private ControlDeNavegacion() {
        // Constructor privado para evitar múltiples instancias 
    }

    public static ControlDeNavegacion getInstancia() {
        if (instancia == null) {
            instancia = new ControlDeNavegacion();
        }
        return instancia;
    }

    //Este metodo sirve para regresar al menu Principal, se encontrara la forma de de fusionar
    public void mostrarMenuPrincipal() {
        SwingUtilities.invokeLater(() -> {
            MenuPrincipal pantalla = new MenuPrincipal();
            pantalla.setLocationRelativeTo(null);
            pantalla.setVisible(true);
        });
    }

    public void mostrarSeleccionarPelicula() {
        SwingUtilities.invokeLater(() -> {
            SeleccionarPelicula pantallaSeleccionarPelicula;
            try {
                pantallaSeleccionarPelicula = new SeleccionarPelicula();
                pantallaSeleccionarPelicula.setLocationRelativeTo(null);
                pantallaSeleccionarPelicula.setVisible(true);
            } catch (GestionReservaException ex) {
                Logger.getLogger(ControlDeNavegacion.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
    }

    public List<PeliculaDTO> obtenerPeliculas() throws GestionReservaException {
        List<PeliculaDTO> peliculas = manejoDeBoletos.cargarPeliculasActivas();
        return peliculas;
    }

    public void guardarPeliculaSeleccionada(PeliculaDTO pelicula) {
        this.peliculaSeleccionada = pelicula;
    }

    public PeliculaDTO consultarPelicula() {
        return peliculaSeleccionada;
    }

    public List<FuncionDTO> obtenerFunciones(String nombrePelicula) throws GestionReservaException {
        List<FuncionDTO> funciones = manejoDeBoletos.cargarFuncionesPelicula(nombrePelicula);
        return funciones;
    }

    public void guardarFuncionSeleccionada(FuncionDTO funcion) {
        this.funcionSeleccionada = funcion;
    }

    public FuncionDTO consultarFuncion() {
        return funcionSeleccionada;
    }

    public void mostrarSeleccionarAsientos(LocalDate dia) {
        SwingUtilities.invokeLater(() -> {
            SeleccionarAsientos pantallaSeleccionarAsientos;
            try {
                pantallaSeleccionarAsientos = new SeleccionarAsientos(dia);
                pantallaSeleccionarAsientos.setLocationRelativeTo(null);
                pantallaSeleccionarAsientos.setVisible(true);
            } catch (GestionReservaException ex) {
                Logger.getLogger(ControlDeNavegacion.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public int obtenerAsientosDisponibles(FuncionDTO funcion) throws GestionReservaException {
        int asientosDisponibles = manejoDeBoletos.consultarDisponibilidadAsientos(funcion);
        return asientosDisponibles;
    }

    public void guardarNumeroAsientos(int numAsientos) {
        this.numAsientos = numAsientos;
    }

    public int consultarNumeroAsientos() {
        return numAsientos;
    }

    public void validarCamposAsientos(String texto, FuncionDTO funcion) throws GestionReservaException {
        if (manejoDeBoletos.validarCampoAsiento(texto, funcion)) {
            int numAsientos = Integer.parseInt(texto);
            manejoDeBoletos.validarDisponibilidaDeAsientos(numAsientos, funcion);
        }
    }

    public void mostrarSeleccionarMetodoPago() {
        this.numAsientos = numAsientos;
        SwingUtilities.invokeLater(() -> {
            SeleccionarMetodoPago pantallaSeleccionarMetodoPago;
            try {
                pantallaSeleccionarMetodoPago = new SeleccionarMetodoPago();
                pantallaSeleccionarMetodoPago.setLocationRelativeTo(null);
                pantallaSeleccionarMetodoPago.setVisible(true);
            } catch (GestionReservaException ex) {
                Logger.getLogger(ControlDeNavegacion.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public List<MetodoPagoDTO> obtenerMetodosPago() throws GestionReservaException {
        List<MetodoPagoDTO> metodosPago = manejoDeBoletos.cargarMetodosPago();
        return metodosPago;
    }

    public void mostrarPagoTarjeta() {
        SwingUtilities.invokeLater(() -> {
            PantallaPagoTarjeta pantallaPagoTarjeta = new PantallaPagoTarjeta();
            pantallaPagoTarjeta.setLocationRelativeTo(null);
            pantallaPagoTarjeta.setVisible(true);
        });
    }

    public void mostrarPagoPaypal() {
        SwingUtilities.invokeLater(() -> {
            PantallaPagoPaypal pantallaPagoPaypal = new PantallaPagoPaypal();
            pantallaPagoPaypal.setLocationRelativeTo(null);
            pantallaPagoPaypal.setVisible(true);
        });
    }

    public void mostrarPagoMercado() {
        SwingUtilities.invokeLater(() -> {
            PantallaPagoMercado pantallaPagoMercado = new PantallaPagoMercado(numAsientos);
            pantallaPagoMercado.setLocationRelativeTo(null);
            pantallaPagoMercado.setVisible(true);
        });
    }

    private List<String> obtenerListaAsientosReservados(FuncionDTO funcion, int numAsientos) throws GestionReservaException {
        List<String> asientosReservados = manejoDeBoletos.reservarAsientoFuncion(funcion, numAsientos, cliente);
        this.asientos = asientosReservados;
        return asientosReservados;
    }

    public BoletoDTO cargarBoleto() throws GestionReservaException {
        List<String> asientosReservados = obtenerListaAsientosReservados(funcionSeleccionada, numAsientos);
        return manejoDeBoletos.generarBoleto(peliculaSeleccionada, funcionSeleccionada, asientosReservados, cliente);
    }

    public void mostrarDetalleBoleto() {
        SwingUtilities.invokeLater(() -> {
            DetalleDelBoleto pantallaDetalleDelBoleto;
            try {
                pantallaDetalleDelBoleto = new DetalleDelBoleto();
                pantallaDetalleDelBoleto.setLocationRelativeTo(null);
                pantallaDetalleDelBoleto.setVisible(true);
            } catch (GestionReservaException ex) {
                Logger.getLogger(ControlDeNavegacion.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(ControlDeNavegacion.class.getName()).log(Level.SEVERE, null, ex);
            } catch (WriterException ex) {
                Logger.getLogger(ControlDeNavegacion.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
    }

    public static void mostrarErrorCamposVaciosPagoMercado() {

    }

    public static void mostrarErrorCamposVaciosPagoTarjeta() {

    }

    public static void mostrarErrorCamposVaciosPagoPaypal() {

    }

    public static void mostrarErrorFaltaAsientosDisponibles() {

    }

    public static void mostrarErrorCampoAsientosVacios() {

    }

    public static void mostrarPagoRechazado() {

    }

    //Metodos de gestion de pagos
    public boolean verificarCuentaMercado(CuentaMercadoDTO cuentaMercado) throws TransferenciaException {
        boolean esValida = gestionDePagos.validarMercado(cuentaMercado);
        return esValida;
    }

    public boolean verificarCuentaPaypal(PaypalDTO cuentaPaypal) throws TransferenciaException {
        boolean esValida = gestionDePagos.validarCuentaPaypal(cuentaPaypal);
        return esValida;
    }
    public boolean verificarCuentaTarjeta(TarjetaDTO cuentaTarjeta) throws TransferenciaException {
        boolean esValida = gestionDePagos.validarTarjeta(cuentaTarjeta);
        return esValida;
    }

}
