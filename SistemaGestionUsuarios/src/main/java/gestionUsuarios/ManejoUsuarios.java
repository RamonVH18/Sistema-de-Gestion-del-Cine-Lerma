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
import DTOs.ReporteUsuarioDTO;
import DTOs.UsuarioDTO;
import Excepciones.ActualizarUsuarioException;
import Excepciones.CargarHistorialException;
import Excepciones.EncontrarUsuarioException;
import Excepciones.RegistrarUsuarioException;
import Excepciones.Usuarios.ActualizarClienteExceptionBO;
import Excepciones.Usuarios.ActualizarUsuarioExceptionBO;
import Excepciones.Usuarios.CargarHistorialExceptionBO;
import Excepciones.Usuarios.EncontrarAdminExceptionBO;
import Excepciones.Usuarios.EncontrarClienteExceptionBO;
import Excepciones.Usuarios.ObtenerUsuariosExceptionBO;
import Excepciones.Usuarios.RegistrarAdminExceptionBO;
import Excepciones.Usuarios.RegistrarClienteExceptionBO;
import Excepciones.ValidarUsuarioException;
import Excepciones.usuarios.ObtenerUsuariosException;
import Interfaces.IAdministradorBO;
import Interfaces.IClienteBO;
import Interfaces.IUsuarioBO;
import enums.EstadoUsuario;
import enums.Rol;
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
    public UsuarioDTO validarUsuario(String nombreUsuario, String contrasena) throws ValidarUsuarioException {
        try {
            if (nombreUsuario == null || nombreUsuario == "" || nombreUsuario.trim().isEmpty() || contrasena == null || contrasena == "" || contrasena.trim().isEmpty()) {
                throw new ValidarUsuarioException("Por favor ingrese los datos de inicio de sesion correctamente");
            }

            UsuarioDTO usuarioEncontrado = usuarioBO.obtenerUsuarioBO(nombreUsuario, contrasena);

            if (usuarioEncontrado == null) {
                throw new ValidarUsuarioException("El usuario no existe");
            }

            if (usuarioEncontrado.getEstado() == EstadoUsuario.BLOQUEADO) {
                throw new ValidarUsuarioException("El usuario esta bloqueado");
            }

            return usuarioEncontrado;

        } catch (ObtenerUsuariosExceptionBO e) {
            throw new ValidarUsuarioException("El administrador no es valido: " + e.getMessage());
        }
    }

    @Override
    public Boolean bloquearUsuario(UsuarioDTO usuario) throws ActualizarUsuarioException {
        try {

            if (usuario == null) {
                throw new ActualizarUsuarioException("El usuario no se pudo encontrar");
            }

            if (usuario.getRol() == Rol.ADMINISTRADOR) {
                throw new ActualizarUsuarioException("No se pueden bloquear administradores");
            }

            //AQUI FALTA VALIDAR QUE SI UN CLIENTE AU TIENE FUNCIONES O ALGO PENDIENTE NO SE PUEDE BLOQUEAR
            if (usuario.getEstado() != EstadoUsuario.ACTIVO) {
                throw new ActualizarUsuarioException("El usuario seleccionado ya esta bloqueado");
            }

            return usuarioBO.bloquearUsuarioBO(usuario);

        } catch (ActualizarUsuarioExceptionBO e) {
            throw new ActualizarUsuarioException("No se pudo bloquear el usuario: " + e.getMessage(), e);
        }
    }

    @Override
    public Boolean desbloquearUsuario(UsuarioDTO usuario) throws ActualizarUsuarioException {
        try {

            if (usuario == null) {
                throw new ActualizarUsuarioException("El usuario no se pudo encontrar");
            }

            if (usuario.getEstado() == EstadoUsuario.ACTIVO) {
                throw new ActualizarUsuarioException("El usuario seleccionado no esta bloqueado");
            }

            return usuarioBO.desbloquearUsuarioBO(usuario);

        } catch (ActualizarUsuarioExceptionBO e) {
            throw new ActualizarUsuarioException("No se pudo desbloquear el cliente: " + e.getMessage(), e);
        }
    }

    //METODOS QUE SIRVEN PARA FILTRAR LA LISTA DE USUARIOS
    @Override
    public List<UsuarioDTO> mostrarListaUsuariosFiltrada(EstadoUsuario estado, Rol rol, LocalDateTime fechaInicio, LocalDateTime fechaFin, String usuario) throws ObtenerUsuariosException {
        try {
            List<UsuarioDTO> listaFiltrada = usuarioBO.mostrarListaUsuariosFiltradaBO(estado, rol, fechaInicio, fechaFin, usuario);

            return listaFiltrada;

        } catch (ObtenerUsuariosExceptionBO e) {
            throw new ObtenerUsuariosException("La lista no se pudo filtrar: " + e.getMessage());
        }
    }

    @Override
    public List<ReporteUsuarioDTO> mostrarUsuariosParaReporte() throws ObtenerUsuariosException {
        try {
            List<ReporteUsuarioDTO> reporteUsuarios = usuarioBO.obtenerReporteUsuarios();

            return reporteUsuarios;

        } catch (ObtenerUsuariosExceptionBO e) {
            throw new ObtenerUsuariosException("No se pudieron obtener a los usuarios para el reporte: " + e.getMessage());
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

    private void validarDatosCliente(ClienteDTO cliente) throws ValidarUsuarioException, EncontrarUsuarioException {

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
        try {

            if (cliente == null) {
                throw new RegistrarUsuarioException("El cliente no puede ser null");
            }

            validarDatosUsuario(cliente);
            validarDatosCliente(cliente);

            return clienteBO.registrarClienteBO(cliente);

        } catch (RegistrarClienteExceptionBO e) {
            throw new RegistrarUsuarioException("No se pudo registrar el cliente: " + e.getMessage());
        } catch (ValidarUsuarioException e) {
            throw new RegistrarUsuarioException("Error al registrar el cliente: " + e.getMessage());
        } catch (EncontrarUsuarioException e) {
            throw new RegistrarUsuarioException("Error al registrar el cliente: " + e.getMessage());
        }
    }

    @Override
    public ClienteDTO actualizarCliente(ClienteDTO cliente) throws ActualizarUsuarioException {
        try {

            validarDatosUsuario(cliente);
            validarDatosCliente(cliente);

            return clienteBO.actualizarClienteBO(cliente);

        } catch (ValidarUsuarioException e) {
            throw new ActualizarUsuarioException("No se pudo actualizar el cliente: " + e.getMessage());
        } catch (ActualizarClienteExceptionBO e) {
            throw new ActualizarUsuarioException("No se pudo actualizar el cliente: " + e.getMessage(), e);
        } catch (EncontrarUsuarioException e) {
            throw new ActualizarUsuarioException("No se pudo actualizar el cliente: " + e.getMessage());
        }
    }

    @Override
    public ClienteDTO obtenerCliente(String nombreUsuario, String contrasena) throws EncontrarUsuarioException {
        try {
            if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
                throw new EncontrarUsuarioException("El nombre de usuario es obligatorio");
            }

            if (contrasena == null || contrasena.trim().isEmpty()) {
                throw new EncontrarUsuarioException("La contrasena es obligatoria");
            }

            ClienteDTO clienteEncontrado = clienteBO.obtenerClienteBO(nombreUsuario, contrasena);
            if (clienteEncontrado == null) {
                throw new EncontrarUsuarioException("No se encontro el administrador");
            }

            return clienteEncontrado;

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
    public AdministradorDTO obtenerAdministrador(String nombreUsuario, String contrasena) throws EncontrarUsuarioException {

        try {
            if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
                throw new EncontrarUsuarioException("El nombre de usuario es obligatorio");
            }

            if (contrasena == null || contrasena.trim().isEmpty()) {
                throw new EncontrarUsuarioException("La contrasena es obligatoria");
            }

            AdministradorDTO adminEncontrado = adminBO.obtenerAdministradorBO(nombreUsuario, contrasena);
            if (adminEncontrado == null) {
                throw new EncontrarUsuarioException("No se encontro el administrador");
            }

            return adminEncontrado;

        } catch (EncontrarAdminExceptionBO e) {
            throw new EncontrarUsuarioException("No se pudo obtener el administrador: " + e.getMessage(), e);
        }
    }

}
