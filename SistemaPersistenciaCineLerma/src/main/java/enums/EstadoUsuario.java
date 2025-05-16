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
// el enum EstadoUsuario define los posibles estados que un usuario puede tener en el sistema, en este caso pueden ser ACTIVO O BLOQUEADO y estan representados en forma de String
public enum EstadoUsuario {
    @BsonRepresentation(BsonType.STRING) //Anotacion que le indica a mongoDB que debe guardar el valor de el enum en forma de String en los documentos
    //Estado activo por default de un usuario
    ACTIVO, 
    @BsonRepresentation(BsonType.STRING) //Anotacion que le indica a mongoDB que debe guardar el valor de el enum en forma de String en los documentos
    //Representa el estado bloqueado de un usuario, al estar bloqueado un usuario no puede acceder al sistema
    BLOQUEADO;
    
}
