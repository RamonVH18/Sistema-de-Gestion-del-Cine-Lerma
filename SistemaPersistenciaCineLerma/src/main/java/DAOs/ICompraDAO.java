/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAOs;

import entidades.Compra;

/**
 *
 * @author isaac
 */
public interface ICompraDAO {
    
    public Compra registrarCompra(Compra compra);
    
    public Compra consultarCompra( Compra compra );
    
}
