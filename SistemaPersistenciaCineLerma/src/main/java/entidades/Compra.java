/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.time.LocalDateTime;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

/**
 *
 * @author sonic
 */
public class Compra {
    
    @BsonId
    @BsonProperty("_id")
    private ObjectId idCompra;
    
    private Pago pago;
    
    private String metodoPago;
    
    @BsonProperty("nombreDeUsuario")
    private String usuarioCliente;
    
    private LocalDateTime fecha;

    public Compra() {
    }

    public Compra(ObjectId idCompra, Pago pago, String metodoPago, String usuarioCliente, LocalDateTime fecha) {
        this.idCompra = idCompra;
        this.pago = pago;
        this.metodoPago = metodoPago;
        this.usuarioCliente = usuarioCliente;
        this.fecha = fecha;
    }

    public Compra(Pago pago, String metodoPago, String cliente, LocalDateTime fecha) {
        this.pago = pago;
        this.metodoPago = metodoPago;
        this.usuarioCliente = usuarioCliente;
        this.fecha = fecha;
    }

    public ObjectId getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(ObjectId idCompra) {
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

    @Override
    public String toString() {
        return "Compra{" + "idCompra=" + idCompra + ", pago=" + pago + ", metodoPago=" + metodoPago + ", cliente=" + usuarioCliente + ", fecha=" + fecha + '}';
    }    
}
