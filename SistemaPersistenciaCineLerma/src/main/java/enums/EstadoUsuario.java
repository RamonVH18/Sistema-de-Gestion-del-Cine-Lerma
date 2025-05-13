/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package enums;

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

/**
 *
 * @author sonic
 */
public enum EstadoUsuario {
    @BsonRepresentation(BsonType.STRING)
    ACTIVO, 
    @BsonRepresentation(BsonType.STRING)
    BLOQUEADO;
    
}
