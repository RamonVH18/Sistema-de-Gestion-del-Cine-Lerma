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
    private ClienteDTO cliente;
    
    /**
     * Constructor vacio
     */
    public CompraDTO() {
    }
    
    /**
     * Constructor con todos los atributos
     * @param pago
     * @param metodoPago
     * @param cliente 
     */
    public CompraDTO(PagoDTO pago, String metodoPago, ClienteDTO cliente) {
        this.pago = pago;
        this.metodoPago = metodoPago;
        this.cliente = cliente;
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
    /**
     * 
     * @return 
     */
    public ClienteDTO getCliente() {
        return cliente;
    }
    /**
     * 
     * @param cliente 
     */
    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }
    
    
}
