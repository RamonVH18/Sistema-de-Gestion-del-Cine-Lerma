/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

/**
 *
 * @author Sebastian Borquez
 */
public class CompraDTO {
    private PagoDTO pago;
    private String metodoPago;
    private Cliente cliente;
    
    public class Cliente {
        // Clase interna sin atributos ni métodos
    }

    public CompraDTO(PagoDTO pago, String metodoPago, Cliente cliente) {
        this.pago = pago;
        this.metodoPago = metodoPago;
        this.cliente = cliente;
    }

    public CompraDTO() {
    }

    public PagoDTO getPago() {
        return pago;
    }

    public void setPago(PagoDTO pago) {
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
    
    
}
