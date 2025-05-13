/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package control;

import DTOs.AdministradorDTO;
import DTOs.BoletoDTO;
import DTOs.ClienteDTO;
import DTOs.CompraDTO;
import DTOs.CuentaMercadoDTO;
import DTOs.FuncionDTO;
import DTOs.MetodoPagoDTO;
import DTOs.PagoDTO;
import DTOs.PaypalDTO;
import DTOs.PeliculaDTO;
import DTOs.TarjetaDTO;
import DTOs.UsuarioDTO;
import Excepciones.ActualizarUsuarioException;
import Excepciones.CargarHistorialException;
import Excepciones.EncontrarUsuarioException;
import Excepciones.PresentacionException;
import Excepciones.RegistrarUsuarioException;
import Excepciones.ValidarUsuarioException;
import Excepciones.usuarios.EliminarUsuarioException;
import Excepciones.usuarios.ObtenerUsuariosException;
import enums.EstadoUsuario;
import java.time.LocalDateTime;
import java.util.List;
import javax.swing.JFrame;

/**
 *
 * @author Ramon Valencia
 */
public interface IControl {
    
    public void mostrarMenuCliente(JFrame frameAnterior, ClienteDTO cliente);
    
    public void mostrarSeleccionarPelicula();
    
    public void mostrarSeleccionarAsientos(PeliculaDTO pelicula);
    
    public void mostrarSeleccionarMetodoPago(FuncionDTO funcion);
    
    public void mostrarPantallaPago(String tipo) throws PresentacionException;
    
    public void mostrarDetalleBoleto();
    
    public void mostrarPantallaPagoRechazado();
    
    public void mostrarMenuAdministrador(JFrame frameAnterior, AdministradorDTO admin);
    
    public void mostrarMenuSalas(JFrame frameAnterior);
    
    public void mostrarAgregarSala(JFrame frameAnterior);
    
    public void mostrarEstadisticasSala(JFrame frameAnterior);
    
    public List<PeliculaDTO> obtenerPeliculas();
    
    public PeliculaDTO consultarPelicula();
    
    public List<FuncionDTO> obtenerFunciones(String nombrePelicula);
    
    public FuncionDTO consultarFuncion();
    
    public int obtenerAsientosDisponibles(FuncionDTO funcion);
    
    public void guardarNumeroAsientos(int numAsientos);
    
    public int consultarNumeroAsientos();
    
    public String validarCamposAsientos(String texto, FuncionDTO funcion);
    
    public List<MetodoPagoDTO> obtenerMetodosPago();
    
    public BoletoDTO cargarBoleto();
    
    public CuentaMercadoDTO verificarCuentaMercado(CuentaMercadoDTO cuentaMercado);
    
    public PaypalDTO verificarCuentaPaypal(PaypalDTO cuentaPaypal);
    
    public TarjetaDTO verificarCuentaTarjeta(TarjetaDTO cuentaTarjeta);
    
    public Double calcularCostoTotal();
    
    public void actualizarSaldoMercado(CuentaMercadoDTO mercadoPago, PagoDTO pago);
    
    public void actualizarSaldoPaypal(PaypalDTO paypal, PagoDTO pago);
    
    public void actualizarSaldoTarjeta(TarjetaDTO tarjeta, PagoDTO pago);
    
    public void procesarPagoMercado(CuentaMercadoDTO mercadoPago, PagoDTO pago);
    
    public void procesarPagoPaypal(PaypalDTO paypal, PagoDTO pago);
    
    public void procesarPagoTarjeta(TarjetaDTO tarjeta, PagoDTO pago);
    
    
    
    //Metodos de navegacion de usuarios /////////////////////
    public void mostrarIniciarSesion();
    
    public void mostrarAdministracionDeUsuario(JFrame frameAnterior);
    
    public void mostrarEditarUsuario(JFrame frameAnterior);
    
    public void mostrarRegistrarUsuario(JFrame frameAnterior);
    
    public void mostrarHistorialCliente(JFrame frameAnterior);
    
    //Usuarios:
    
    public List<UsuarioDTO> mostrarListaUsuarios();

    public Boolean bloquearUsuario(UsuarioDTO usuario);
    
    public Boolean desbloquearUsuario(UsuarioDTO usuario);
    
    public List<UsuarioDTO> mostrarListaUsuariosPorEstado(EstadoUsuario estado);
    
    public List<UsuarioDTO> mostrarListaUsuariosPorPeriodo(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    
    public List<UsuarioDTO> mostrarListaUsuariosPorCorreo(String correo);
    
    public List<UsuarioDTO> mostrarListaUsuariosPorNombre(String nombre);
    
    
    //Cliente:
    
    public ClienteDTO registrarCliente (ClienteDTO cliente);
    
    public ClienteDTO actualizarCliente (ClienteDTO cliente);
    
    public Boolean eliminarCliente (ClienteDTO cliente);
    
    //public Boolean validarCliente(String nombreUsuario, String contrasena) throws ValidarUsuarioException;
    
    public ClienteDTO obtenerCliente(String nombreUsuario, String contrasena) throws EncontrarUsuarioException;
    
    public List<CompraDTO> cargarHistorialCompras(ClienteDTO cliente);
    
    //Administrador:
    
    public AdministradorDTO registrarAdministrador (AdministradorDTO administrador);
    
    public AdministradorDTO actualizarAdministrador (AdministradorDTO administrador);
    
    public Boolean eliminarAdministrador (AdministradorDTO administrador);
    
    //public Boolean validarAdministrador(String nombreUsuario, String contrasena) throws ValidarUsuarioException;
    
    public AdministradorDTO obtenerAdministrador(String nombreUsuario, String contrasena);
    
}
