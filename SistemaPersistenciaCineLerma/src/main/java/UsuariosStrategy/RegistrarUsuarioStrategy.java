/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UsuariosStrategy;

import Excepciones.PersistenciaException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import entidades.Administrador;
import entidades.Cliente;
import entidades.Usuario;

/**
 *
 * @author sonic
 */
public class RegistrarUsuarioStrategy implements IUsuarioStrategy<Usuario> {

    @Override
    public Usuario ejecutar(MongoDatabase base, Usuario usuario) throws UsuarioStrategyException {
        
        if (usuario instanceof Cliente) {
            MongoCollection<Cliente> coleccion = base.getCollection("usuarios", Cliente.class);
            
            coleccion.createIndex(
                Indexes.ascending("nombreDeUsuario"),
                new IndexOptions().unique(true)
            );
            
            coleccion.insertOne((Cliente) usuario);
        } else if (usuario instanceof Administrador) {
            MongoCollection<Administrador> coleccion = base.getCollection("usuarios", Administrador.class);
            
            coleccion.createIndex(
                Indexes.ascending("nombreDeUsuario"),
                new IndexOptions().unique(true)
            );
            
            coleccion.insertOne((Administrador) usuario);
        }
        return usuario;
    }
}
