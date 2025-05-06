/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Interfaces.ICompraDAO;
import entidades.Cliente;
import entidades.Compra;
import entidades.Pago;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author isaac
 */
public class CompraDAO implements ICompraDAO {

    private static CompraDAO instance;
    private List<Compra> compras = new ArrayList<>();

    private CompraDAO() {
        inicializarDatos();
    }

    public static CompraDAO getInstanceDAO() {
        if (instance == null) {
            instance = new CompraDAO();
        }

        return instance;
    }
    
    
    private void inicializarDatos() {

        //Cliente cliente1 = new Cliente(1L, "Abrahamlexis Molina", "abrabo@gmail.com", "freski1");
        //Cliente cliente2 = new Cliente(2L, "John Java", "joja@gmail.com", "javaman1");

        Pago pago1 = new Pago(1L, 150.00, LocalDateTime.now(), true);
        Pago pago2 = new Pago(2L, 200.00, LocalDateTime.now(), true);

        //compras.add(new Compra(1L, pago1, "Tarjeta de credito", cliente1, LocalDateTime.now()));
        //compras.add(new Compra(2L, pago2, "Paypal", cliente2, LocalDateTime.now()));
    }
    
    @Override
    public Compra registrarCompra(Compra compra) {

        Long nuevoId = (long) (compras.size() + 1);
//        compra.setIdCompra(nuevoId);

        // anadir compras a la lista
        compras.add(compra);

        return compra;
    }
    
    @Override
    public Compra consultarCompra( Compra compra ) {
        
        for (Compra c: compras) {
            if ( c.getIdCompra().equals(compra.getIdCompra())) {
                return c;
            }
        }
        return null;
    }
    
    public List<Compra> obtenerTodasLasCompras() {
        return compras;
    }

}
