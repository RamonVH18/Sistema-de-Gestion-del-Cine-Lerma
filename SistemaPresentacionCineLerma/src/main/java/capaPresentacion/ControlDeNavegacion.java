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
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.net.URL;
import java.util.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
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

/**
 *
 * @author Ramon Valencia
 */
public class ControlDeNavegacion {

    private IManejoDeBoletos manejoDeBoletos = new ManejoDeBoletos();
    private BoletoDTO boletoFinal;
    private ClienteDTO cliente = new ClienteDTO("Abrahama Coronel Garcia", "jaime@lerma.com.mx");
    private static final Logger logger = Logger.getLogger(Logger.class.getName());

    public static void main(String[] args) {
        // TODO code application logic here
        ControlDeNavegacion.mostrarMenuPrincipalStatic();
    }

    //Este metodo sirve para inicializar el menu
    public static void mostrarMenuPrincipalStatic() {
        SwingUtilities.invokeLater(() -> {
            MenuPrincipal pantalla = new MenuPrincipal();
            pantalla.setLocationRelativeTo(null);
            pantalla.setVisible(true);
        });
    }

    //Este metodo sirve para regresar al menu Principal, se encontrara la forma de de fusionar
    public void mostrarMenuPrincipal() {
        SwingUtilities.invokeLater(() -> {
            MenuPrincipal pantalla = new MenuPrincipal();
            pantalla.setLocationRelativeTo(null);
            pantalla.setVisible(true);
        });
    }

    //Metodo para generar la Cartelera, se modificara un JPanel ya escrito y se le añadiran los JButton de cada respectiva pelicula
    public JPanel generarCartelera(JPanel panel) throws GestionReservaException {
        List<PeliculaDTO> peliculas = manejoDeBoletos.cargarPeliculasActivas();
        //Modificacion del JPanel para que sea 3 columnas de botones
        panel.setLayout(new GridLayout(0, 3, 10, 10));
        panel.setPreferredSize(new Dimension(600, Math.max(400, panel.getComponentCount() * 100)));
        panel.removeAll();
        //En este for se crean los botones, se recorre el arreglo de lista y por cada pelicula se manda a llamar al metodo crear pelicula
        for (int i = 0; i < peliculas.size(); i++) {
            PeliculaDTO pelicula = peliculas.get(i);
            JButton boton = crearBotonImagen(pelicula);
            panel.add(boton);
            logger.info("Pelicula: " + pelicula.getNombrePelicula());
        }
        panel.setVisible(true);
        panel.revalidate();
        panel.repaint();
        return panel;
    }

    //
    public JButton crearBotonImagen(PeliculaDTO pelicula) {
        //Esto csera modificado una vez que implementemos el sistema de bases de datos
        URL imageUrl = getClass().getClassLoader().getResource(pelicula.getPeliculaImagen());
        ImageIcon imagen = new ImageIcon(imageUrl);
        Image scaledImage = imagen.getImage().getScaledInstance(
                250,
                300,
                Image.SCALE_SMOOTH
        );
        imagen = new ImageIcon(scaledImage);

        JButton boton = new JButton(pelicula.getNombrePelicula(), imagen);
        LocalDate dia = LocalDate.now();
        boton.addActionListener(e -> {
            mostrarSeleccionarAsientos(pelicula, dia);
            JFrame SeleccionarPelicula = (JFrame) SwingUtilities.getWindowAncestor(boton);
            SeleccionarPelicula.dispose();

        }
        );

        boton.setPreferredSize(new Dimension(250, 200));
        return boton;
    }

    //Checar de nuevo la funcionalidad de este metodo
    public ImageIcon crearImagen(String url, int ancho, int altura) {
        URL imageUrl = getClass().getClassLoader().getResource(url);
        ImageIcon imagen = new ImageIcon(imageUrl);
        Image scaledImage = imagen.getImage().getScaledInstance(
                ancho,
                altura,
                Image.SCALE_SMOOTH
        );
        return imagen = new ImageIcon(scaledImage);
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

    public JPanel generarTablaFunciones(JPanel panel, LocalDate dia, PeliculaDTO pelicula) throws GestionReservaException {

        Date diaNuevo = new Date();
        if (dia.equals(LocalDate.now())) {
            LocalTime horaActual = LocalTime.now();
            LocalDateTime fechaConHora = LocalDateTime.of(dia, horaActual);
            diaNuevo = Date.from(fechaConHora.atZone(ZoneId.systemDefault()).toInstant());
        } else {
            diaNuevo = Date.from(dia.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }

        List<FuncionDTO> listaFunciones = manejoDeBoletos.cargarFuncionesDelDia(diaNuevo, pelicula.getNombrePelicula());
        panel.removeAll();

        for (int i = 0; i < listaFunciones.size(); i++) {
            FuncionDTO funcion = listaFunciones.get(i);
            JButton boton = crearBotonFuncion(funcion, panel);
            panel.add(boton);
        }
        return panel;
    }

    public JButton crearBotonFuncion(FuncionDTO funcion, JPanel panel) {
        JButton boton = new JButton();
        Date hora = funcion.getFechaHora();
        String funcionMinutos = (funcion.getFechaHora().getMinutes() < 10) ? "0"
                + Integer.toString(funcion.getFechaHora().getMinutes())
                : Integer.toString(funcion.getFechaHora().getMinutes());

        boton.setText(hora.getHours() + ":" + funcionMinutos);
        boton.setBackground(Color.decode("#A2845E"));
        //Aqui se define lo que va a pasar cuando el boton de una funcion sea seleccionado
        boton.addActionListener(e -> {
            SeleccionarAsientos seleccionarAsientos = (SeleccionarAsientos) SwingUtilities.getWindowAncestor(boton);
            seleccionarAsientos.revalidate();
            seleccionarAsientos.repaint();
            for (Component componente : panel.getComponents()) {
                if (componente instanceof JButton) { // Verificamos si es un botón
                    componente.setEnabled(true);
                }
            }
            boton.setEnabled(false);
            int asientosDisponibles;
            try {
                asientosDisponibles = manejoDeBoletos.consultarDisponibilidadAsientos(funcion);
                seleccionarAsientos.cargarDatos(funcion, asientosDisponibles);
            } catch (GestionReservaException ex) {
                Logger.getLogger(ControlDeNavegacion.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        return boton;
    }

    public void mostrarSeleccionarAsientos(PeliculaDTO pelicula, LocalDate dia) {
        SwingUtilities.invokeLater(() -> {
            SeleccionarAsientos pantallaSeleccionarAsientos;
            try {
                pantallaSeleccionarAsientos = new SeleccionarAsientos(pelicula, dia);
                pantallaSeleccionarAsientos.setLocationRelativeTo(null);
                pantallaSeleccionarAsientos.setVisible(true);
            } catch (GestionReservaException ex) {
                Logger.getLogger(ControlDeNavegacion.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public String traducirDia(DayOfWeek dia) {
        String nuevoDia = new String();
        switch (dia) {
            case dia.MONDAY:
                nuevoDia = "Lun";
                break;
            case dia.TUESDAY:
                nuevoDia = "Mar";
                break;
            case dia.WEDNESDAY:
                nuevoDia = "Mie";
                break;
            case dia.THURSDAY:
                nuevoDia = "Jue";
                break;
            case dia.FRIDAY:
                nuevoDia = "Vie";
                break;
            case dia.SATURDAY:
                nuevoDia = "Sab";
                break;
            case dia.SUNDAY:
                nuevoDia = "Dom";
                break;

        }
        return nuevoDia;
    }

    public String traducirMes(Month mes) {
        String nuevoMes = new String();
        switch (mes) {
            case JANUARY:
                nuevoMes = "Enero";
                break;
            case FEBRUARY:
                nuevoMes = "Febrero";
                break;
            case MARCH:
                nuevoMes = "Marzo";
                break;
            case APRIL:
                nuevoMes = "Abril";
                break;
            case MAY:
                nuevoMes = "Mayo";
                break;
            case JUNE:
                nuevoMes = "Junio";
                break;
            case JULY:
                nuevoMes = "Julio";
                break;
            case AUGUST:
                nuevoMes = "Agosto";
                break;
            case SEPTEMBER:
                nuevoMes = "Septiembre";
                break;
            case OCTOBER:
                nuevoMes = "Octobre";
                break;
            case NOVEMBER:
                nuevoMes = "Noviembre";
                break;
            case DECEMBER:
                nuevoMes = "Diciembre";
                break;
        }
        return nuevoMes;
    }

    public void recargarPaginaFunciones(LocalDate dia, JPanel panel, PeliculaDTO pelicula) {
        JFrame SeleccionarAsientos = (JFrame) SwingUtilities.getWindowAncestor(panel);
        SeleccionarAsientos.dispose();
        mostrarSeleccionarAsientos(pelicula, dia);
    }

    public void validarCamposAsientos(String texto, FuncionDTO funcion) throws GestionReservaException {
        if (manejoDeBoletos.validarCampoAsiento(texto)) {
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

    public JButton crearBotonMetodoPago(String url, Border border, String nombreMetodo) {
        ImageIcon image = crearImagen(url, 50, 50);
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

    public void mostrarSeleccionarMetodoPago(PeliculaDTO pelicula, FuncionDTO funcion, int numAsientos) {
        SwingUtilities.invokeLater(() -> {
            SeleccionarMetodoPago pantallaSeleccionarMetodoPago = new SeleccionarMetodoPago(pelicula, funcion, numAsientos);
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
            PantallaPagoMercado pantallaPagoMercado = new PantallaPagoMercado();
            pantallaPagoMercado.setLocationRelativeTo(null);
            pantallaPagoMercado.setVisible(true);
        });
    }

    public List<String> obtenerListaAsientosReservados(FuncionDTO funcion, int numAsientos) throws GestionReservaException {
        List<String> asientosReservados = manejoDeBoletos.reservarAsientoFuncion(funcion, numAsientos, cliente);
        return asientosReservados;
    }

    public BoletoDTO cargarBoleto(PeliculaDTO pelicula, FuncionDTO funcion, int numAsientos) throws GestionReservaException {
        System.out.println("Numero de asientos: " + numAsientos);
        List<String> asientos = obtenerListaAsientosReservados(funcion, numAsientos);
        System.out.println(asientos);
        this.boletoFinal = manejoDeBoletos.generarBoleto(pelicula, funcion, asientos, cliente);
        return boletoFinal;
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
