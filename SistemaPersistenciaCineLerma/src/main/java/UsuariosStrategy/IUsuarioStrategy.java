/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package UsuariosStrategy;

import com.mongodb.client.MongoDatabase;
import entidades.Usuario;

/**
 *
 * @author sonic
 */
public interface IUsuarioStrategy <T extends Usuario> {
    T ejecutar(MongoDatabase base, T usuario) throws UsuarioStrategyException;
    
}
