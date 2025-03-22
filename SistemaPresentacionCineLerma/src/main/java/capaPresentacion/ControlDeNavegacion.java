/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capaPresentacion;

import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

/**
 *
 * @author Ramon Valencia
 */
public class ControlDeNavegacion {
    
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
    
    public static void generarCartelera() {
        
    }
    
    public static JButton crearBotonImagen(JTable table) {
        JButton boton = new JButton();
        boton.setPreferredSize(new Dimension(25, 25));
        //ImagenIcon() icono = new ImageIcon();
        return boton;
    }
    
    public static void mostrarSeleccionarPelicula() {
        SwingUtilities.invokeLater(() -> {
            SeleccionarPelicula pantallaSeleccionarPelicula = new SeleccionarPelicula();
            pantallaSeleccionarPelicula.setLocationRelativeTo(null); 
            pantallaSeleccionarPelicula.setVisible(true);
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
