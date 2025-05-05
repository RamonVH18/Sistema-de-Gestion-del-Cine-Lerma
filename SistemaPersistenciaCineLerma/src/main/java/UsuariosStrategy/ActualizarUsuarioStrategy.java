/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UsuariosStrategy;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.result.UpdateResult;
import entidades.Administrador;
import entidades.Cliente;
import entidades.Usuario;
import org.bson.conversions.Bson;

/**
 *
 * @author sonic
 */
public class ActualizarUsuarioStrategy implements IUsuarioStrategy<Usuario> {

    MongoCollection<? extends Usuario> coleccion;
    

    @Override
    public Usuario ejecutar(MongoDatabase base, Usuario usuario) throws UsuarioStrategyException {
        if (usuario instanceof Cliente) {
            coleccion = base.getCollection("usuarios", Cliente.class);
            
            coleccion.createIndex(
                Indexes.ascending("nombreDeUsuario"),
                new IndexOptions().unique(true)
            );
        } else if (usuario instanceof Administrador) {
            coleccion = base.getCollection("usuarios", Administrador.class);
            
            coleccion.createIndex(
                Indexes.ascending("nombreDeUsuario"),
                new IndexOptions().unique(true)
            );
        }

        Bson filtro = Filters.eq("nombreUsuario", usuario.getNombreDeUsuario());

        Usuario usuarioActualizar = coleccion.find(filtro).first();

        if (usuarioActualizar == null) {
            throw new UsuarioStrategyException("No se encontro el usuario para eliminar");
        }

        UpdateResult result = ((MongoCollection<Usuario>)coleccion).replaceOne(filtro, usuario);
        
        if (result.getModifiedCount() == 0) {
            throw new UsuarioStrategyException("No se modificó ningún documento");
        }
        return usuario;
    }

}
