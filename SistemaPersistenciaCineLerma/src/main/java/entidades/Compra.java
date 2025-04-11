/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.time.LocalDateTime;

/**
 *
 * @author sonic
 */
public class Compra {
    
    private Long idCompra;
    
    private Pago pago;
    
    private String metodoPago;
    
    private Cliente cliente;
    
    private LocalDateTime fecha;

    public Compra() {
    }

    public Compra(Long idCompra, Pago pago, String metodoPago, Cliente cliente, LocalDateTime fecha) {
        this.idCompra = idCompra;
        this.pago = pago;
        this.metodoPago = metodoPago;
        this.cliente = cliente;
        this.fecha = fecha;
    }

    public Compra(Pago pago, String metodoPago, Cliente cliente, LocalDateTime fecha) {
        this.pago = pago;
        this.metodoPago = metodoPago;
        this.cliente = cliente;
        this.fecha = fecha;
    }

    public Long getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(Long idCompra) {
        this.idCompra = idCompra;
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Compra{" + "idCompra=" + idCompra + ", pago=" + pago + ", metodoPago=" + metodoPago + ", cliente=" + cliente + ", fecha=" + fecha + '}';
    }    
}
