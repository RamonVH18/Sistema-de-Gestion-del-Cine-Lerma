/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestionUsuarios;

import BOs.AdministradorBO;
import BOs.ClienteBO;
import BOs.UsuarioBO;
import DTOs.AdministradorDTO;
import DTOs.ClienteDTO;
import DTOs.CompraDTO;
import DTOs.UsuarioDTO;
import Excepciones.ActualizarUsuarioException;
import Excepciones.CargarHistorialException;
import Excepciones.EncontrarUsuarioException;
import Excepciones.RegistrarUsuarioException;
import Excepciones.Usuarios.ActualizarClienteExceptionBO;
import Excepciones.Usuarios.EliminarUsuarioExceptionBO;
import Excepciones.Usuarios.EncontrarClienteExceptionBO;
import Excepciones.Usuarios.ObtenerUsuariosExceptionBO;
import Excepciones.Usuarios.RegistrarClienteExceptionBO;
import Excepciones.Usuarios.ValidarUsuarioExceptionBO;
import Excepciones.ValidarUsuarioException;
import Excepciones.usuarios.EliminarUsuarioException;
import Excepciones.usuarios.ObtenerUsuariosException;
import Interfaces.IAdministradorBO;
import Interfaces.IClienteBO;
import Interfaces.IUsuarioBO;
import entidades.Usuario;
import enums.EstadoUsuario;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sonic
 */
public class ManejoUsuarios implements IManejoUsuarios {

    private IUsuarioBO usuarioBO = UsuarioBO.getInstanceBO();
    private IAdministradorBO adminBO = AdministradorBO.getInstanceBO();
    private IClienteBO clienteBO = ClienteBO.getInstanceBO();

    
    
    
    
    /////////////////////////////////
    //-------------------------------------------------METODOS DE USUARIOS ---------------------------------------------------------------------------------------
    @Override
    public List<UsuarioDTO> mostrarListaUsuarios() throws ObtenerUsuariosException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Boolean eliminarUsuario(UsuarioDTO usuario) throws EliminarUsuarioException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Boolean bloquearUsuario(UsuarioDTO usuario) throws ActualizarUsuarioException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Boolean desbloquearUsuario(UsuarioDTO usuario) throws ActualizarUsuarioException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    //METODOS QUE SIRVEN PARA FILTRAR LA LISTA DE USUARIOS
    @Override
    public List<UsuarioDTO> mostrarListaUsuariosPorEstado(EstadoUsuario estado) throws ObtenerUsuariosException {
        try {
            List<UsuarioDTO> listaFiltrada = usuarioBO.mostrarListaUsuariosFiltradaBO(estado, null, null, null, null);

            return listaFiltrada;

        } catch (ObtenerUsuariosExceptionBO e) {
            throw new ObtenerUsuariosException("La lista no se pudo filtrar: " + e.getMessage());
        }
    }

    @Override
    public List<UsuarioDTO> mostrarListaUsuariosPorPeriodo(LocalDateTime fechaInicio, LocalDateTime fechaFin) throws ObtenerUsuariosException {
        try {
            List<UsuarioDTO> listaFiltrada = usuarioBO.mostrarListaUsuariosFiltradaBO(null, fechaInicio, fechaFin, null, null);

            return listaFiltrada;

        } catch (ObtenerUsuariosExceptionBO e) {
            throw new ObtenerUsuariosException("La lista no se pudo filtrar: " + e.getMessage());
        }
    }

    @Override
    public List<UsuarioDTO> mostrarListaUsuariosPorCorreo(String correo) throws ObtenerUsuariosException {
        try {
            List<UsuarioDTO> listaFiltrada = usuarioBO.mostrarListaUsuariosFiltradaBO(null, null, null, correo, null);

            return listaFiltrada;

        } catch (ObtenerUsuariosExceptionBO e) {
            throw new ObtenerUsuariosException("La lista no se pudo filtrar: " + e.getMessage());
        }
    }

    @Override
    public List<UsuarioDTO> mostrarListaUsuariosPorNombre(String nombre) throws ObtenerUsuariosException {
        try {
            List<UsuarioDTO> listaFiltrada = usuarioBO.mostrarListaUsuariosFiltradaBO(null, null, null, null, nombre);

            return listaFiltrada;

        } catch (ObtenerUsuariosExceptionBO e) {
            throw new ObtenerUsuariosException("La lista no se pudo filtrar: " + e.getMessage());
        }
    }

    
    
    
    
    
    
    ///////////////////////////////////////////
    //-------------------------------------------------METODOS DE CLIENTES ---------------------------------------------------------------------------------------
    @Override
    public ClienteDTO registrarCliente(ClienteDTO cliente) throws RegistrarUsuarioException {
        //faltaria validar que no se repitan los nombres de usuario i guess osea que no exista uno ya con ese nombre 
        try {

            if (cliente == null) {
                throw new RegistrarUsuarioException("El cliente no puede ser null");
            }

            validarDatosUsuario(cliente);
            validarDatosCliente(cliente);

            return clienteBO.registrarClienteBO(cliente);

        } catch (ValidarUsuarioException e) {
            throw new RegistrarUsuarioException("La sala ingresada no cumple con la siguiente validacion: " + e.getMessage());
        } catch (RegistrarClienteExceptionBO e) {
            throw new RegistrarUsuarioException("No se pudo registrar el cliente (subsistema): " + e.getMessage(), e);
        }
    }

    private void validarDatosUsuario(UsuarioDTO usuario) throws ValidarUsuarioException {

        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
            throw new ValidarUsuarioException("El nombre del cliente es obligatorio");
        }

        if (usuario.getApellidoPaterno() == null || usuario.getApellidoPaterno().trim().isEmpty()) {
            throw new ValidarUsuarioException("El apellido paterno del cliente es obligatorio");
        }

        if (usuario.getNombreUsuario() == null || usuario.getNombreUsuario().trim().isEmpty()) {
            throw new ValidarUsuarioException("El nombre de usuario es obligatorio");
        }

        if (usuario.getContraseña() == null || usuario.getContraseña().trim().isEmpty()) {
            throw new ValidarUsuarioException("La contraseña es obligatoria");
        }

        if (usuario.getCorreoElectronico() == null || usuario.getCorreoElectronico().trim().isEmpty()) {
            throw new ValidarUsuarioException("El correo electrónico es obligatorio");
        }

        if (usuario.getTelefono() == null || usuario.getTelefono().trim().isEmpty()) {
            throw new ValidarUsuarioException("El telefono es obligatorio");
        }

        if (usuario.getTelefono().length() > 10) {
            throw new ValidarUsuarioException("El telefono no puede contener mas de 10 numeros");
        }

        if (usuario.getTelefono().length() != 10) {
            throw new ValidarUsuarioException("El telefono debe tener una longitud de 10 numeros");
        }

        if (!usuario.getTelefono().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new ValidarUsuarioException("El formato del telefono ingresado no es valido");
        }

        if (!usuario.getCorreoElectronico().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new ValidarUsuarioException("El formato del correo electrónico no es valido");
        }

        if (usuario.getFechaNacimiento() == null) {
            throw new ValidarUsuarioException("La fecha de nacimiento es obligatoria");
        }

        if (usuario.getFechaNacimiento().isAfter(LocalDateTime.now().minusYears(13))) {
            throw new ValidarUsuarioException("El usuario debe tener al menos 13 años");
        }

    }

    private void validarDatosCliente(ClienteDTO cliente) throws ValidarUsuarioException {

        if (cliente.getCP() == null || cliente.getCP().trim().isEmpty()) {
            throw new ValidarUsuarioException("El CP del cliente es obligatorio");
        }

        if (cliente.getCP() == null || cliente.getCP().trim().isEmpty()) {
            throw new ValidarUsuarioException("El CP del cliente es obligatorio");
        }

        if (cliente.getCP().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new ValidarUsuarioException("El CP del cliente no es valido");
        }

        if (cliente.getCalle() == null || cliente.getCalle().trim().isEmpty()) {
            throw new ValidarUsuarioException("La calle del cliente es obligatoria");
        }

        if (cliente.getNumero() == null || cliente.getNumero().trim().isEmpty()) {
            throw new ValidarUsuarioException("El numero de domicilio del cliente es obligatorio");
        }

        if (cliente.getNumero().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new ValidarUsuarioException("El numero de domicilio del cliente no es valido");
        }

    }

    @Override
    public ClienteDTO actualizarCliente(ClienteDTO cliente) throws ActualizarUsuarioException {
        try {

            validarDatosUsuario(cliente);
            validarDatosCliente(cliente);

            if (!validarCliente(cliente.getNombreUsuario(), cliente.getContraseña())) {
                throw new ActualizarUsuarioException("El cliente no es valido");
            }

            return clienteBO.actualizarClienteBO(cliente);

        } catch (ValidarUsuarioException e) {
            throw new ActualizarUsuarioException("La sala ingresada no cumple con la siguiente validacion: " + e.getMessage());
        } catch (ActualizarClienteExceptionBO e) {
            throw new ActualizarUsuarioException("No se pudo registrar el cliente (subsistema): " + e.getMessage(), e);
        }
    }

    @Override
    public Boolean eliminarCliente(ClienteDTO cliente) throws EliminarUsuarioException {
        try {

            return clienteBO.eliminarClienteBO(cliente);

        } catch (EliminarUsuarioExceptionBO e) {
            throw new EliminarUsuarioException("No se pudo eliminar al cliente : " + e.getMessage());
        }
    }

    @Override
    public Boolean validarCliente(String nombreUsuario, String contrasena) throws ValidarUsuarioException {
        try {

            return clienteBO.validarClienteBO(nombreUsuario, contrasena);

        } catch (ValidarUsuarioExceptionBO e) {
            throw new ValidarUsuarioException("La sala ingresada no cumple con la siguiente validacion: " + e.getMessage());
        }
    }

    @Override
    public ClienteDTO obtenerCliente(String nombreUsuario) throws EncontrarUsuarioException {
        if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
            throw new EncontrarUsuarioException("El nombre de usuario es obligatorio");
        }
        
        try {
            return clienteBO.obtenerClienteBO(nombreUsuario);
            
        } catch (EncontrarClienteExceptionBO e) {
            throw new EncontrarUsuarioException("No se pudo obtener el cliente: " + e.getMessage(), e);
        }
    }

    @Override
    public List<CompraDTO> cargarHistorialCompras(ClienteDTO cliente) throws CargarHistorialException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
    
    
    
    
    
    
    
    
    

    //////////////////////////////////////////////
    //-------------------------------------------------METODOS DE ADMINISTRADOR ---------------------------------------------------------------------------------------
    @Override
    public AdministradorDTO registrarAdministrador(AdministradorDTO administrador) throws RegistrarUsuarioException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public AdministradorDTO actualizarAdministrador(AdministradorDTO administrador) throws ActualizarUsuarioException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Boolean eliminarAdministrador(AdministradorDTO administrador) throws EliminarUsuarioException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Boolean validarAdministrador(String nombreUsuario, String contrasena) throws ValidarUsuarioException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public AdministradorDTO obtenerAdministrador(String nombreUsuario) throws EncontrarUsuarioException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
