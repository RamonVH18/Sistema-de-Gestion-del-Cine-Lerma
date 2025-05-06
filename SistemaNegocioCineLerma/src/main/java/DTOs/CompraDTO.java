/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

import java.time.LocalDateTime;

/**
 *
 * @author Sebastian Borquez
 */
public class CompraDTO {
    private PagoDTO pago;
    private String metodoPago;
    private String usuarioCliente;
    private LocalDateTime fecha;
    
    /**
     * Constructor vacio
     */
    public CompraDTO() {
    }
    
    /**
     * Constructor con todos los atributos
     * @param pago
     * @param metodoPago
     * @param usuarioCliente
     */
    public CompraDTO(PagoDTO pago, String metodoPago, String usuarioCliente, LocalDateTime fecha) {    
        this.pago = pago;
        this.metodoPago = metodoPago;
        this.usuarioCliente = usuarioCliente;
        this.fecha = fecha;
    }

    /**
     * Getters y setters
     */
    public PagoDTO getPago() {
        return pago;
    }
    /**
     * 
     * @param pago 
     */
    public void setPago(PagoDTO pago) {
        this.pago = pago;
    }
    /**
     * 
     * @return 
     */
    public String getMetodoPago() {
        return metodoPago;
    }
    /**
     * 
     * @param metodoPago 
     */
    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getUsuarioCliente() {
        return usuarioCliente;
    }

    public void setUsuarioCliente(String usuarioCliente) {
        this.usuarioCliente = usuarioCliente;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
    
    
    
    
    
    
    
}
