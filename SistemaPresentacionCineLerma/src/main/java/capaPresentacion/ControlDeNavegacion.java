/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capaPresentacion;

import DTOs.PeliculaDTO;
import Excepciones.GestionReservaException;
import gestionReservaBoletos.IManejoDeBoletos;
import gestionReservaBoletos.ManejoDeBoletos;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

/**
 *
 * @author Ramon Valencia
 */
public class ControlDeNavegacion {

    IManejoDeBoletos manejoDeBoletos = new ManejoDeBoletos();
    private static final Logger logger = Logger.getLogger(Logger.class.getName());

    public static void main(String[] args) {
        // TODO code application logic here
        ControlDeNavegacion.mostrarMenuPrincipal();
    }

    public static void mostrarMenuPrincipal() {
        SwingUtilities.invokeLater(() -> {
            MenuPrincipal pantalla = new MenuPrincipal();
            pantalla.setLocationRelativeTo(null);
            pantalla.setVisible(true);
        });
    }

    public JPanel generarCartelera(JPanel panel) throws GestionReservaException {
        List<PeliculaDTO> peliculas = manejoDeBoletos.cargarPeliculasActivas();
        //Modificacion del JPanel para que sea 3 columnas de botones
        panel.setLayout(new GridLayout(0, 3, 10, 10));
        panel.setPreferredSize(new Dimension(600, Math.max(400, panel.getComponentCount() * 100)));
        panel.removeAll();
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
        
        
        JButton boton = new JButton(imagen);
        
        boton.setPreferredSize(new Dimension(250, 200));
        return boton;
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

    public static void mostrarSeleccionarAsientos() {

    }

    public static void recargarPaginaFunciones() {

    }

    public static void generarTablaMetodosPago() {

    }

    public static void mostrarSeleccionarMetodoPago() {

    }

    public static void mostrarPagoTarjeta() {

    }

    public static void mostrarPagoPaypal() {

    }

    public static void mostrarPagoMercado() {

    }

    public static void mostrarDetalleBoleto() {

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
