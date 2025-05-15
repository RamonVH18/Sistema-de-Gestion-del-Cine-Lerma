/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces.mappers;

import DTOs.FuncionDTO;
import entidades.Funcion;
import org.bson.types.ObjectId;

/**
 *
 * @author Abraham Coronel Bringas
 */
public interface IFuncionMapper {

    public FuncionDTO toFuncionDTO(Funcion funcion);

    public Funcion toFuncionEntidad(FuncionDTO funciondto);
    
    public ObjectId toObjectId(String id);
}
