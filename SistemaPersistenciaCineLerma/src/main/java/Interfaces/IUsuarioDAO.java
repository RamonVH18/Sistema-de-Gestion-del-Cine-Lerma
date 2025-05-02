/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Excepciones.PersistenciaException;
import entidades.Administrador;
import entidades.Cliente;
import entidades.Funcion;
import entidades.Usuario;
import enums.EstadoUsuario;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author sonic
 */
public interface IUsuarioDAO {
    
    public List<Usuario> mostrarListaUsuarios() throws PersistenciaException;
    
    public Cliente registrarCliente(Cliente cliente) throws PersistenciaException;

    public Administrador registrarAdministrador(Administrador administrador) throws PersistenciaException;

    public Usuario actualizarUsuario(Usuario usuario) throws PersistenciaException;

    public Boolean eliminarUsuario(Usuario usuario) throws PersistenciaException;

    public Boolean bloquearUsuario(Usuario usuario) throws PersistenciaException;

    public List<Funcion> cargarHistorialCompras(Cliente cliente) throws PersistenciaException;
    
    public Usuario validarUsuario(String nombreUsuario, String contrasena) throws PersistenciaException;
    
    public List<Usuario> mostrarListaUsuariosFiltrada(EstadoUsuario estado, LocalDateTime fechaInicio, LocalDateTime fechaFin, String correo, String nombre) throws PersistenciaException;
    
    public Usuario buscarUsuario(String nombreUsuario) throws PersistenciaException; //Este metodo probablemente se use para verificar si existe un usuario concreto en la base y otras operaciones
    
}
