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
import Excepciones.Usuarios.ActualizarAdminExceptionBO;
import Excepciones.Usuarios.ActualizarClienteExceptionBO;
import Excepciones.Usuarios.ActualizarUsuarioExceptionBO;
import Excepciones.Usuarios.CargarHistorialExceptionBO;
import Excepciones.Usuarios.EliminarUsuarioExceptionBO;
import Excepciones.Usuarios.EncontrarAdminExceptionBO;
import Excepciones.Usuarios.EncontrarClienteExceptionBO;
import Excepciones.Usuarios.ObtenerUsuariosExceptionBO;
import Excepciones.Usuarios.RegistrarAdminExceptionBO;
import Excepciones.Usuarios.RegistrarClienteExceptionBO;
import Excepciones.Usuarios.ValidarUsuarioExceptionBO;
import Excepciones.ValidarUsuarioException;
import Excepciones.usuarios.EliminarUsuarioException;
import Excepciones.usuarios.ObtenerUsuariosException;
import Interfaces.IAdministradorBO;
import Interfaces.IClienteBO;
import Interfaces.IUsuarioBO;
import enums.EstadoUsuario;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author sonic
 */
public class ManejoUsuarios implements IManejoUsuarios {

    private IUsuarioBO usuarioBO = UsuarioBO.getInstanceBO();
    private IAdministradorBO adminBO = AdministradorBO.getInstanceBO();
    private IClienteBO clienteBO = ClienteBO.getInstanceBO();

    private static ManejoUsuarios instanceManejoUsuarios;

    private ManejoUsuarios() {
    }

    public static ManejoUsuarios getInstance() {
        if (instanceManejoUsuarios == null) { // para la primera vez que se llama
            instanceManejoUsuarios = new ManejoUsuarios();
        }
        return instanceManejoUsuarios;
    }

    /////////////////////////////////
    //-------------------------------------------------METODOS DE USUARIOS ---------------------------------------------------------------------------------------
    @Override
    public List<UsuarioDTO> mostrarListaUsuarios() throws ObtenerUsuariosException {
        try {
            return usuarioBO.mostrarListaUsuariosBO();

        } catch (ObtenerUsuariosExceptionBO e) {
            throw new ObtenerUsuariosException("No se pudo obtener el cliente: " + e.getMessage(), e);
        }
    }

    @Override
    public Boolean bloquearUsuario(UsuarioDTO usuario) throws ActualizarUsuarioException {
        try {
            return usuarioBO.bloquearUsuarioBO(usuario);

        } catch (ActualizarUsuarioExceptionBO e) {
            throw new ActualizarUsuarioException("No se pudo bloquear el cliente: " + e.getMessage(), e);
        }
    }

    @Override
    public Boolean desbloquearUsuario(UsuarioDTO usuario) throws ActualizarUsuarioException {
        try {
            return usuarioBO.desbloquearUsuarioBO(usuario);

        } catch (ActualizarUsuarioExceptionBO e) {
            throw new ActualizarUsuarioException("No se pudo bloquear el cliente: " + e.getMessage(), e);
        }
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

        if (!usuario.getTelefono().matches("^[0-9]+$")) {
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

        if (!cliente.getCP().matches("^[0-9]+$")) {
            throw new ValidarUsuarioException("El CP del cliente no es valido");
        }

        if (cliente.getCalle() == null || cliente.getCalle().trim().isEmpty()) {
            throw new ValidarUsuarioException("La calle del cliente es obligatoria");
        }

        if (cliente.getNumero() == null || cliente.getNumero().trim().isEmpty()) {
            throw new ValidarUsuarioException("El numero de domicilio del cliente es obligatorio");
        }

        if (!cliente.getNumero().matches("^[0-9]+$")) {
            throw new ValidarUsuarioException("El numero de domicilio del cliente no es valido");
        }

    }

    private void validarDatosAdministrador(AdministradorDTO admin) throws ValidarUsuarioException {

        if (admin.getRFC() == null || admin.getRFC().trim().isEmpty()) {
            throw new ValidarUsuarioException("El RFC del administrador es obligatorio");
        }

    }

    @Override
    public ClienteDTO registrarCliente(ClienteDTO cliente) throws RegistrarUsuarioException {
        //faltaria validar que no se repitan los nombres de usuario i guess osea que no exista uno ya con ese nombre 
        try {

            if (cliente == null) {
                throw new RegistrarUsuarioException("El cliente no puede ser null");
            }

            if (!validarCliente(cliente.getNombreUsuario(), cliente.getContraseña())) {
                throw new RegistrarUsuarioException("Ya existe un usuario con ese username o esta bloqueado");
            }

            validarDatosUsuario(cliente);
            validarDatosCliente(cliente);

            return clienteBO.registrarClienteBO(cliente);

        } catch (ValidarUsuarioException e) {
            throw new RegistrarUsuarioException("Error al registrar el cliente: " + e.getMessage());
        } catch (RegistrarClienteExceptionBO e) {
            throw new RegistrarUsuarioException("No se pudo registrar el cliente (subsistema): " + e.getMessage(), e);
        }
    }

    @Override
    public ClienteDTO actualizarCliente(ClienteDTO cliente) throws ActualizarUsuarioException {
        try {

            if (obtenerCliente(cliente.getNombreUsuario()) != null) {
                throw new ActualizarUsuarioException("Ya existe un usuario con ese username");
            }

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
        } catch (EncontrarUsuarioException e) {
            throw new ActualizarUsuarioException("No se pudo actualizar el cliente por: " + e.getMessage());
        }
    }

    @Override
    public Boolean eliminarCliente(ClienteDTO cliente) throws EliminarUsuarioException {
        try {

            //FALTAN MUCHAS VALIDACIONES AQUI EHHHH XDDDDDDDDDDDDDDDDDDD MUCHAS POR QUE NO SE PUEDE ELIMINAR ASI COMO ASI
            return clienteBO.eliminarClienteBO(cliente);

        } catch (EliminarUsuarioExceptionBO e) {
            throw new EliminarUsuarioException("No se pudo eliminar al cliente : " + e.getMessage());
        }
    }

    @Override
    public Boolean validarCliente(String nombreUsuario, String contrasena) throws ValidarUsuarioException {
        if (nombreUsuario == null || nombreUsuario == "" || nombreUsuario.trim().isEmpty() || contrasena == null || contrasena == "" || contrasena.trim().isEmpty()) {
            throw new ValidarUsuarioException("Por favor ingrese los datos de inicio de sesion correctamente");
        }
        
        try {

            return clienteBO.validarClienteBO(nombreUsuario, contrasena);

        } catch (ValidarUsuarioExceptionBO e) {
            throw new ValidarUsuarioException("El cliente no es valido: " + e.getMessage());
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
        try {
            return clienteBO.cargarHistorialComprasBO(cliente);

        } catch (CargarHistorialExceptionBO e) {
            throw new CargarHistorialException("No se pudo obtener el cliente: " + e.getMessage(), e);
        }
    }

    //////////////////////////////////////////////
    //-------------------------------------------------METODOS DE ADMINISTRADOR ---------------------------------------------------------------------------------------
    @Override
    public AdministradorDTO registrarAdministrador(AdministradorDTO administrador) throws RegistrarUsuarioException {
        try {

            if (administrador == null) {
                throw new RegistrarUsuarioException("El cliente no puede ser null");
            }

            validarDatosUsuario(administrador);
            validarDatosAdministrador(administrador);

            return adminBO.registrarAdministradorBO(administrador);

        } catch (ValidarUsuarioException e) {
            throw new RegistrarUsuarioException("La sala ingresada no cumple con la siguiente validacion: " + e.getMessage());
        } catch (RegistrarAdminExceptionBO e) {
            throw new RegistrarUsuarioException("No se pudo registrar el administrador (subsistema): " + e.getMessage(), e);
        }
    }

    @Override
    public AdministradorDTO actualizarAdministrador(AdministradorDTO administrador) throws ActualizarUsuarioException {
        try {

            if (obtenerAdministrador(administrador.getNombreUsuario()) != null) {
                throw new ActualizarUsuarioException("Ya existe un usuario con ese username");
            }

            validarDatosUsuario(administrador);
            validarDatosAdministrador(administrador);

            if (!validarAdministrador(administrador.getNombreUsuario(), administrador.getContraseña())) {
                throw new ActualizarUsuarioException("El administrador no es valido");
            }

            return adminBO.actualizarAdministradorBO(administrador);

        } catch (ValidarUsuarioException e) {
            throw new ActualizarUsuarioException("La sala ingresada no cumple con la siguiente validacion: " + e.getMessage());
        } catch (ActualizarAdminExceptionBO e) {
            throw new ActualizarUsuarioException("No se pudo actualizar el admin (subsistema): " + e.getMessage(), e);
        } catch (EncontrarUsuarioException e) {
            throw new ActualizarUsuarioException("No se pudo actualizar el cliente por: " + e.getMessage());
        }
    }

    @Override
    public Boolean eliminarAdministrador(AdministradorDTO administrador) throws EliminarUsuarioException {
        try {

            //FALTAN MUCHAS VALIDACIONES AQUI EHHHH XDDDDDDDDDDDDDDDDDDD MUCHAS POR QUE NO SE PUEDE ELIMINAR ASI COMO ASI
            return adminBO.eliminarAdministradorBO(administrador);

        } catch (EliminarUsuarioExceptionBO e) {
            throw new EliminarUsuarioException("No se pudo eliminar al cliente : " + e.getMessage());
        }
    }

    @Override
    public Boolean validarAdministrador(String nombreUsuario, String contrasena) throws ValidarUsuarioException {
        try {
            if (nombreUsuario == null || nombreUsuario == "" || nombreUsuario.trim().isEmpty() || contrasena == null || contrasena == "" || contrasena.trim().isEmpty()) {
                throw new ValidarUsuarioException("Por favor ingrese los datos de inicio de sesion correctamente");
            }

            return adminBO.validarAdministradorBO(nombreUsuario, contrasena);

        } catch (ValidarUsuarioExceptionBO e) {
            throw new ValidarUsuarioException("El cliente no es valido: " + e.getMessage());
        }
    }

    @Override
    public AdministradorDTO obtenerAdministrador(String nombreUsuario) throws EncontrarUsuarioException {

        try {
            if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
                throw new EncontrarUsuarioException("El nombre de usuario es obligatorio");
            }

            AdministradorDTO adminEncontrado = adminBO.obtenerAdministradorBO(nombreUsuario);
            if (adminEncontrado == null) {
                throw new EncontrarUsuarioException("No se encontro el administrador");
            }

            return adminEncontrado;

        } catch (EncontrarAdminExceptionBO e) {
            throw new EncontrarUsuarioException("No se pudo obtener el administrador: " + e.getMessage(), e);
        }
    }

}
