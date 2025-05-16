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

// el enum rol define los posibles roles que un usuario puede tener en el sistema, en este caso pueden ser cliente o administrador.
// este enum es muy importante por que es el valor que se utiliza como discriminador en la herencia de mongoDB,
// sirve para distinguir entre diferentes subclases al almacenarlas en la base de datos
public enum Rol {
    @BsonRepresentation(BsonType.STRING) //Anotacion que le indica a mongoDB que debe guardar el valor de el enum en forma de String en los documentos
    //Estado activo por default de un usuario
    CLIENTE,  // Representa el rol de un cliente en el sistema. 
    @BsonRepresentation(BsonType.STRING) //Anotacion que le indica a mongoDB que debe guardar el valor de el enum en forma de String en los documentos   
    ADMINISTRADOR; // Representa el rol de un administrador en el sistema.
}
