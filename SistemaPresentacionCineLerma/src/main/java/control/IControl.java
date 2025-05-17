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
import DTOs.ReporteUsuarioDTO;
import DTOs.SalaNuevaDTO;
import DTOs.SalaViejaDTO;
import DTOs.TarjetaDTO;
import DTOs.UsuarioDTO;
import Excepciones.EncontrarUsuarioException;
import Excepciones.PresentacionException;
import enums.EstadoSala;
import enums.EstadoUsuario;
import enums.Rol;
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
    
    public void mostrarMenuReportes(JFrame frameAnterior);

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

    // -------------------------------METODOS DE NAVEGACION DE SALAS--------------------------------
    public void mostrarMenuSalas(JFrame frameAnterior);

    public void mostrarAgregarSala(JFrame frameAnterior);

    public void agregarSala(SalaNuevaDTO salaNueva);

    public Boolean validarCamposAgregarSala(String numSala, String numAsientos);

    public void mostrarEstadisticasSala(JFrame frameAnterior);

    public void mostrarSeleccionarSala(JFrame frameAnterior);

    public SalaViejaDTO consultarSala(String numSala);

    public List<SalaViejaDTO> consultarSalas(String filtro);

    public void mostrarModificarSala(JFrame frameAnterior, SalaViejaDTO sala);
    
    public Boolean modificarSala(String numSala, EstadoSala estadoSala);

    // -----------------------------FIN DE METODOS DE SALAS-----------------------------------------
    //Metodos de navegacion de usuarios /////////////////////
    public void mostrarIniciarSesion();

    public void mostrarGestionDeUsuarios(JFrame frameAnterior, AdministradorDTO admin);

    public void mostrarEditarUsuario(JFrame frameAnterior, ClienteDTO cliente, ClienteDTO clienteAlMando);

    public void mostrarRegistrarUsuario(JFrame frameAnterior);

    public void mostrarHistorialCliente(JFrame frameAnterior, ClienteDTO cliente);

    //Metodos de navegacion de Funciones
    public void mostrarConsultarFunciones(JFrame frameAnterior, String nombrePelicula);

    public void mostrarProgramarFuncion(JFrame frameAnterior, String nombrePelicula);
    
    // -------------------------------METODOS DE NAVEGACION DE PELICULAS--------------------------------
    public void mostrarMenuAdministrarPeliculas(JFrame frameAnterior);
    
    public void mostrarAgregarPelicula(JFrame frameAnterior);
    // -------------------------------FIN DE METODOS DE NAVEGACION DE PELICULAS-------------------------

    //Usuarios:
    public List<UsuarioDTO> mostrarListaUsuarios();

    public Boolean bloquearUsuario(UsuarioDTO usuario);

    public Boolean desbloquearUsuario(UsuarioDTO usuario);

    public List<UsuarioDTO> mostrarListaUsuariosFiltrada(EstadoUsuario estado, Rol rol, LocalDateTime fechaInicio, LocalDateTime fechaFin, String nombre);

    public UsuarioDTO validarUsuario(String nombreUsuario, String contrasena);

    public UsuarioDTO obtenerUsuarioActual();
    
    public List<ReporteUsuarioDTO> obtenerReporteUsuarios();

    //Cliente:
    public ClienteDTO registrarCliente(ClienteDTO cliente);

    public ClienteDTO actualizarCliente(ClienteDTO cliente);

    public ClienteDTO obtenerCliente(String nombreUsuario, String contrasena);

    public List<CompraDTO> cargarHistorialCompras(ClienteDTO cliente);

    //Administrador:
    public AdministradorDTO registrarAdministrador(AdministradorDTO administrador);

    public AdministradorDTO obtenerAdministrador(String nombreUsuario, String contrasena);

    //Metodos Funciones
    public FuncionDTO registrarFuncion(FuncionDTO funcionDTO);

    public Boolean eliminarFuncion(FuncionDTO funcionDTO);

    public List<FuncionDTO> buscarFunciones(String nombrePelicula, LocalDateTime fechaHora);

    public LocalDateTime calcularHoraTerminoFuncion(String idFuncion);
    
    // -------------------------------METODOS DE PELICULAS--------------------------------
    public PeliculaDTO registrarPelicula(PeliculaDTO peliculaDTO);
    // -------------------------------FIN DE METODOS DE PELICULAS-------------------------

}
