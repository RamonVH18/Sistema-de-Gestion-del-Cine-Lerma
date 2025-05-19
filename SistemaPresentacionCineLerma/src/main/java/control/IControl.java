/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package control;

import DTOs.AdministradorDTO;
import DTOs.AsientoFuncionDTO;
import DTOs.BoletoDTO;
import DTOs.ClienteDTO;
import DTOs.CompraDTO;
import DTOs.CuentaMercadoDTO;
import DTOs.EmpleadoDTO;
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
import Excepciones.PresentacionException;
import enums.Cargo;
import enums.EstadoSala;
import enums.EstadoUsuario;
import enums.Rol;
import java.awt.Component;
import java.time.LocalDateTime;
import java.util.List;
import javax.swing.JFrame;
import pantallas.Funciones.ConsultarFunciones;

/**
 *
 * @author Ramon Valencia
 */
public interface IControl {

//---------------Metodos para guardar los recursos necesarios-----------------------

    public UsuarioDTO obtenerUsuarioActual();
    
    public AdministradorDTO obtenerAdministradorActual();
    
    public void guardarAdministradorActual(AdministradorDTO administrador);
    
    public ClienteDTO obtenerClienteActual();
    
    public void guardarClienteActual(ClienteDTO cliente);
    
//---------------Fin de los metodos para guardar los recursos necesarios-----------------------
    
    public void mostrarMenuCliente(JFrame frameAnterior);

    public void mostrarSeleccionarPelicula();

    public void mostrarSeleccionarAsientos(PeliculaDTO pelicula);

    public void mostrarSeleccionarMetodoPago(FuncionDTO funcion);

    public void mostrarPantallaPago(String tipo) throws PresentacionException;

    public void mostrarDetalleBoleto();

    public void mostrarPantallaPagoRechazado();

    public void mostrarMenuAdministrador(JFrame frameAnterior);
    
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

    public BoletoDTO cargarBoleto(ClienteDTO cliente);

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

    public List<SalaViejaDTO> consultarSalas(String filtro, Boolean filtrarActivas);

    public void mostrarModificarSala(JFrame frameAnterior, SalaViejaDTO sala);
    
    public Boolean modificarSala(String numSala, EstadoSala estadoSala);
    
    public void mostrarConsultarFuncionesSalas(JFrame frameAnterior);
    
    public void mostrarConsultarAsientosReservados(JFrame frameAnterior, FuncionDTO funcionDTO);
    
    public List<AsientoFuncionDTO> agregarAsientoFuncion(FuncionDTO funcionSelecionada, SalaViejaDTO salaSelecionada);
    
    public Boolean reservarAsientos(List<AsientoFuncionDTO> asientosAReservar);
    
    public List<AsientoFuncionDTO> cargarListaAsientos(FuncionDTO funcion, Boolean mostrarDisponibles);

    // -----------------------------FIN DE METODOS DE SALAS-----------------------------------------
    //Metodos de navegacion de usuarios /////////////////////
    public void mostrarIniciarSesion();

    public void mostrarGestionDeUsuarios(JFrame frameAnterior, AdministradorDTO admin);

    public void mostrarEditarUsuario(JFrame frameAnterior, ClienteDTO cliente, ClienteDTO clienteAlMando);

    public void mostrarRegistrarUsuario(JFrame frameAnterior);

    public void mostrarHistorialCliente(JFrame frameAnterior, ClienteDTO cliente);

    //Metodos de navegacion de Funciones
    public void mostrarConsultarFunciones(JFrame frameAnterior, String nombrePelicula);

    public void mostrarProgramarFuncion(ConsultarFunciones frameAnterior, String nombrePelicula);
    
    public List<FuncionDTO> consultarFuncionesFiltradas(String textoFiltro);
    
    // -------------------------------METODOS DE NAVEGACION DE PELICULAS--------------------------------
    public void mostrarMenuAdministrarPeliculas(JFrame frameAnterior);
    
    public void mostrarAgregarPelicula(JFrame frameAnterior);
    
    public void mostrarDetallesPelicula(PeliculaDTO peliculaDTO);
    
    public void mostrarEditarPelicula(PeliculaDTO peliculaDTO);
    
    public PeliculaDTO encontrarPelicula(String nombrePelicula);
    // -------------------------------FIN DE METODOS DE NAVEGACION DE PELICULAS-------------------------

    //Usuarios:
    public List<UsuarioDTO> mostrarListaUsuarios();

    public Boolean bloquearUsuario(UsuarioDTO usuario);

    public Boolean desbloquearUsuario(UsuarioDTO usuario);

    public List<UsuarioDTO> mostrarListaUsuariosFiltrada(EstadoUsuario estado, Rol rol, LocalDateTime fechaInicio, LocalDateTime fechaFin, String nombre);

    public UsuarioDTO validarUsuario(String nombreUsuario, String contrasena);
    
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
    
    public PeliculaDTO editarPelicula(PeliculaDTO peliculaDTO);
    
    public boolean darAltaPelicula(PeliculaDTO peliculaDTO);
    
    public boolean darBajaPelicula(PeliculaDTO peliculaDTO);
    
    public boolean eliminarPelicula(PeliculaDTO peliculaDTO);
    // -------------------------------FIN DE METODOS DE PELICULAS-------------------------
    
    public void mostrarMenuAdministrarEmpleados(JFrame frame);

    public void mostrarRegistrarEmpleado(JFrame frameAnterior);

     public void mostrarActualizarDatosEmpleado(JFrame frameAnterior, String empleadoId);
     
    public void mostrarDialogActualizarCargoDeEmpleado(JFrame frameAnterior, EmpleadoDTO empleadoActual);

    public void iniciarFlujoActualizarCargo(JFrame framePadre);

    public void mostrarFrameSueldoOpciones(JFrame frameAnterior);

    public void mostrarActualizacionSueldoPorCargo(JFrame frameAnterior);

    public void mostrarActualizarEmpleado(JFrame frameAnterior);

    public void mostrarDespedirEmpleado(JFrame frameAnterior);

   
    public EmpleadoDTO solicitarSeleccionEmpleado(JFrame padreFrame);

    // --- metodos admin empleados
    public boolean procesarActualizacionSueldoIndividual(String empleadoId, double nuevoSueldo, Component parentComponent);

    public EmpleadoDTO controlRegistrarNuevoEmpleado(EmpleadoDTO empleadoDTO);

    public EmpleadoDTO controlActualizarInformacionEmpleado(String empleadoId, EmpleadoDTO datosNuevosDTO);

    public boolean controlDespedirEmpleado(String empleadoIdString);

    public EmpleadoDTO consultarEmpleadoActivoPorId(String empleadoIdString);

    public List<EmpleadoDTO> consultarTodosLosEmpleadosActivos();

    public List<EmpleadoDTO> consultarEmpleadosActivosPorCargo(Cargo cargo);

    public boolean controlActualizarCargoEmpleado(String empleadoIdString, Cargo nuevoCargo);

    public boolean controlActualizarSueldoEmpleadoIndividual(String empleadoIdString, double nuevoSueldo);

    public long controlActualizarSueldoGeneralPorCargo(Cargo cargo, double nuevoSueldo);

    public void iniciarFlujoActualizarSueldoIndividual(JFrame framePadreSueldoOpciones);

}

