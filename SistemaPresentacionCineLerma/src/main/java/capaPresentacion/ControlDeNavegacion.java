/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capaPresentacion;

import javax.swing.SwingUtilities;

/**
 *
 * @author Ramon Valencia
 */
public class ControlDeNavegacion {
    
    public static void main(String args[]) {
        mostrarMenuPrincipal();
    }
    
    public static void mostrarMenuPrincipal() {
        SwingUtilities.invokeLater(() -> {
            PantallaPagoMercado pantalla = new PantallaPagoMercado();
            pantalla.setLocationRelativeTo(null); 
            pantalla.setVisible(true);
        });
    }
    
    public void generarCartelera() {
        
    }
    
    public void mostrarSeleccionPelicula() {
        
    }
    
    public void recargarPaginaFunciones() {
        
    }
    
    public void generarTablaMetodosPago() {
        
    }
    
    public void mostrarSeleccionMetodoPago() {
        
    }
    
    public void mostrarPagoTarjeta() {
        
    }
    
    public void mostraPagoPaypal() {
        
    }
    
    public void mostrarPagoMercado() {
        
    }
    
    public void mostrarDetalleBoleto() {
        
    }
    
    public void mostrarErrorCamposVaciosPagoMercado() {
        
    }
    
    public void mostrarErrorCamposVaciosPagoTarjeta() {
        
    }
    
    public void mostrarErrorCamposVaciosPagoPaypal() {
        
    }
    
    public void mostrarErrorFaltaAsientosDisponibles() {
        
    }
    
    public void mostrarErrorCampoAsientosVacios() {
        
    }
    
    public void mostrarPagoRechazado() {
        
    }
    
}
