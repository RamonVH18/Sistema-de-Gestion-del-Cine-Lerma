/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package BOs;

import entidades.Cliente;
import entidades.Compra;
import entidades.Funcion;
import java.util.List;

/**
 *
 * @author isaac
 */
public interface ICompraBO {

    public boolean validarMetodoPago(String metodoPago);

    public Compra consultarCompra(Long idCompra);

    public double calcularTotalGastadoPorCliente(Cliente cliente);

    public Compra realizarCompra(Cliente cliente, Funcion funcion, int cantidadBoletos, String metodoPago);

    public List<Compra> obtenerTodasLasCompras();

}
