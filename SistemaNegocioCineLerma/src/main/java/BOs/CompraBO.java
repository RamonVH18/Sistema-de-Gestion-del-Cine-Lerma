/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BOs;

import Interfaces.ICompraBO;
import DAOs.CompraDAO;
import entidades.Cliente;
import entidades.Compra;
import entidades.Funcion;
import entidades.Pago;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author isaac
 */
public class CompraBO implements ICompraBO {
    
    private static CompraBO instance;
    private CompraDAO compraDAO;
    
   
    private  CompraBO() {
        // Inicializar el DAO
        compraDAO = CompraDAO.getInstanceDAO();
    }
    
    // Método para obtener la instancia única (Singleton)
    public static CompraBO getInstanceBO() {
        if (instance == null) {
            instance = new CompraBO();
        }
        return instance;
    }
    
    
    
    @Override
    public Compra realizarCompra(Cliente cliente, Funcion funcion, int cantidadBoletos, String metodoPago) {
        // Validar que el cliente no sea nulo
        if (cliente == null) {
            System.out.println("Error: El cliente no puede ser nulo.");
            return null;
        }
        
        // Validar que la función no sea nula y esté activa
        if (funcion == null ) {
            System.out.println("Error: La función no está disponible.");
            return null;
        }
        
        // Validar cantidad de boletos
        if (cantidadBoletos <= 0) {
            System.out.println("Error: La cantidad de boletos debe ser mayor a cero.");
            return null;
        }
        
        // Calcular el monto total
        double montoTotal = funcion.getPrecio() * cantidadBoletos;
        
        // Crear un objeto de pago
        Pago pago = new Pago();
        pago.setIdPago((long) (Math.random() * 10000)); // ID temporal simulado
        pago.setMonto(montoTotal);
        pago.setFechaHora(LocalDateTime.now());
        pago.setEstado(true);
        
        
        // Crear un objeto de compra
        Compra compra = new Compra();
        compra.setPago(pago);
        compra.setMetodoPago(metodoPago);
        compra.setCliente(cliente);
        compra.setFecha(LocalDateTime.now());
        
        // Registrar la compra en el DAO
        return compraDAO.registrarCompra(compra);
    }
    
    
    // consutlta detalles de compra por su id y si la encuentra la devuelve
    @Override
    public Compra consultarCompra(Long idCompra) {
        if (idCompra == null || idCompra <= 0) {
            System.out.println("Error: ID de compra inválido.");
            return null;
        }
        
        Compra compraConsulta = new Compra();
        compraConsulta.setIdCompra(idCompra);
        
        return compraDAO.consultarCompra(compraConsulta);
    }
    
    // obtiene todas las compras registradas
    @Override
    public List<Compra> obtenerTodasLasCompras() {
        return compraDAO.obtenerTodasLasCompras();
    }
    
    
    @Override
    public boolean validarMetodoPago(String metodoPago) {
        if (metodoPago == null || metodoPago.trim().isEmpty()) {
            return false;
        }
        
        // Lista de métodos de pago aceptados
        String[] metodosAceptados = {"Tarjeta de crédito", "Tarjeta de débito", "PayPal", "Efectivo"};
        
        for (String metodoAceptado : metodosAceptados) {
            if (metodoAceptado.equalsIgnoreCase(metodoPago)) {
                return true;
            }
        }
        
        return false;
    }
    
    @Override
    public double calcularTotalGastadoPorCliente(Cliente cliente) {
        if (cliente == null) {
            return 0.0;
        }
        
        double totalGastado = 0.0;
        List<Compra> todasLasCompras = compraDAO.obtenerTodasLasCompras();
        
        for (Compra compra : todasLasCompras) {
            if (compra.getCliente().getIdCliente().equals(cliente.getIdCliente())) {
                totalGastado += compra.getPago().getMonto();
            }
        }
        
        return totalGastado;
    }
}
