/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package enums;

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

/**
 *
 * @author Ramon Valencia
 */
public enum EstadoSala {
    @BsonRepresentation(BsonType.STRING)
    ACTIVA, 
    @BsonRepresentation(BsonType.STRING)
    INACTIVA, 
    @BsonRepresentation(BsonType.STRING)
    MANTENIMIENTO;
}
