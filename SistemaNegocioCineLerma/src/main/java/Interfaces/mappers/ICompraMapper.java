/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces.mappers;

import DTOs.CompraDTO;
import entidades.Compra;

/**
 *
 * @author sonic
 */
public interface ICompraMapper {
    
    public CompraDTO toCompraDTO(Compra compra);
    
    public Compra toCompraEntidad(CompraDTO compradto);
    
}
