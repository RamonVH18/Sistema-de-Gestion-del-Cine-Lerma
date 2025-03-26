/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capaPresentacion;

import DTOs.BoletoDTO;
import DTOs.ClienteDTO;
import DTOs.FuncionDTO;
import DTOs.PeliculaDTO;
import Excepciones.GestionReservaException;
import gestionReservaBoletos.IManejoDeBoletos;
import gestionReservaBoletos.ManejoDeBoletos;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import utilitades.Utilerias;

/**
 *
 * @author Ramon Valencia
 */
public class ControlDeNavegacion {

    //Instancias y clases para llamar metodos
    private IManejoDeBoletos manejoDeBoletos = ManejoDeBoletos.getInstancia();
    private Utilerias utilidades = new Utilerias();
    //Variables que hay que checar
    private PeliculaDTO peliculaSeleccionada = new PeliculaDTO();
    private FuncionDTO funcionSeleccionada = new FuncionDTO();
    private List<String> asientos = new ArrayList<>();
    private int numAsientos = 0;
    private BoletoDTO boletoFinal;
    
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

    public void validarCamposAsientos(String texto, FuncionDTO funcion) throws GestionReservaException {
        if (manejoDeBoletos.validarCampoAsiento(texto, funcion)) {
            int numAsientos = Integer.parseInt(texto);
            manejoDeBoletos.validarDisponibilidaDeAsientos(numAsientos, funcion);
        }
    }

    public JPanel generarTablaMetodosPago(JPanel panel) {
        panel.setLayout(new GridLayout(0, 2, 0, 0));
        panel.removeAll();
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
        JButton boton1 = crearBotonMetodoPago("img/visamaster.png", border, "tarjeta");
        JLabel label1 = new JLabel("Tarjeta");
        label1.setBorder(border);

        JButton boton2 = crearBotonMetodoPago("img/paypal.png", border, "paypal");
        JLabel label2 = new JLabel("Paypal");
        label2.setBorder(border);

        JButton boton3 = crearBotonMetodoPago("img/mercadoPago.jpg", border, "mercado");
        JLabel label3 = new JLabel("Mercado pago");
        label3.setBorder(border);

        panel.add(label1);
        panel.add(boton1);
        panel.add(label2);
        panel.add(boton2);
        panel.add(label3);
        panel.add(boton3);

        return panel;

    }

    private JButton crearBotonMetodoPago(String url, Border border, String nombreMetodo) {
        ImageIcon image = utilidades.crearImagen(url, 50, 50);
        JButton boton = new JButton(image);
        boton.setPreferredSize(new Dimension(150, 50));
        boton.setBorder(border);
        boton.addActionListener(e -> {
            
            JDialog SeleccionarMetodoPago = (JDialog) SwingUtilities.getWindowAncestor(boton);
            SeleccionarMetodoPago.dispose();
            
            switch (nombreMetodo) {
                case "tarjeta" ->
                    mostrarPagoTarjeta();
                case "paypal" ->
                    mostrarPagoPaypal();
                case "mercado" ->
                    mostrarPagoMercado();
                default -> {
                }
            }
        });
        return boton;
    }

    public void mostrarSeleccionarMetodoPago(int numAsientos) {
        this.numAsientos = numAsientos;
        SwingUtilities.invokeLater(() -> {
            SeleccionarMetodoPago pantallaSeleccionarMetodoPago = new SeleccionarMetodoPago();
            pantallaSeleccionarMetodoPago.setLocationRelativeTo(null);
            pantallaSeleccionarMetodoPago.setVisible(true);
        });
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

    public void cargarBoleto() throws GestionReservaException {
        System.out.println("Numero de asientos: " + numAsientos);
        List<String> asientosReservados = obtenerListaAsientosReservados(funcionSeleccionada, numAsientos);
        System.out.println(asientosReservados);
        //this.boletoFinal = manejoDeBoletos.generarBoleto(pelicula, funcionSeleccionada, asientosReservados, cliente);
    }

    public void mostrarDetalleBoleto() {
        SwingUtilities.invokeLater(() -> {
            DetalleDelBoleto pantallaDetalleDelBoleto = new DetalleDelBoleto(boletoFinal);
            pantallaDetalleDelBoleto.setLocationRelativeTo(null);
            pantallaDetalleDelBoleto.setVisible(true);
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

}
