/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UsuariosStrategy;

import entidades.Usuario;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author sonic
 */
public class StrategyFactory {

    private static final Map<String, IUsuarioStrategy<?>> strategias = new HashMap<>();

    static {
        strategias.put("registrar", new RegistrarUsuarioStrategy());
        strategias.put("actualizar", new ActualizarUsuarioStrategy());
        
    }

    public static <T extends Usuario> IUsuarioStrategy<T> get(String key) {
        return (IUsuarioStrategy<T>) strategias.get(key);
    }
}
