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
import DTOs.EstadisticaSalaDTO;
import DTOs.FuncionDTO;
import DTOs.MetodoPagoDTO;
import DTOs.PagoDTO;
import DTOs.PaypalDTO;
import DTOs.PeliculaDTO;
import DTOs.SalaNuevaDTO;
import DTOs.SalaViejaDTO;
import DTOs.TarjetaDTO;
import DTOs.UsuarioDTO;
import Excepciones.FuncionDatosIncorrectosException;
import Excepciones.FuncionFechaFuturaException;
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

    public List<EstadisticaSalaDTO> consultarEstadisticasSala();

    public void mostrarSeleccionarSala(JFrame frameAnterior);

    public SalaViejaDTO consultarSala(String numSala);

    public List<SalaViejaDTO> consultarSalas(String filtro, Boolean filtrarActivas);

    public void mostrarModificarSala(JFrame frameAnterior, SalaViejaDTO sala);

    public Boolean modificarSala(String numSala, EstadoSala estadoSala);

    public void mostrarConsultarFuncionesSalas(JFrame frameAnterior);

    public void mostrarConsultarAsientosReservados(JFrame frameAnterior, FuncionDTO funcionDTO);

    public List<AsientoFuncionDTO> agregarAsientoFuncion(FuncionDTO funcionSelecionada, SalaViejaDTO salaSelecionada);

    public void eliminarAsientoFuncion(String idFuncion);

    public List<AsientoFuncionDTO> cargarListaAsientos(FuncionDTO funcion, Boolean mostrarDisponibles);

    // -----------------------------FIN DE METODOS DE SALAS-----------------------------------------
    //Metodos de navegacion de usuarios /////////////////////
    public void mostrarIniciarSesion();

    public void mostrarGestionDeUsuarios(JFrame frameAnterior, AdministradorDTO admin);

    public void mostrarEditarUsuario(JFrame frameAnterior, ClienteDTO cliente, ClienteDTO clienteAlMando);

    public void mostrarRegistrarUsuario(JFrame frameAnterior);

    //Metodos de navegacion de Funciones
    public void mostrarConsultarFunciones(JFrame frameAnterior, String nombrePelicula);

    public void mostrarProgramarFuncion(ConsultarFunciones frameAnterior, String nombrePelicula);

    public List<FuncionDTO> consultarFuncionesFiltradas(String textoFiltro);

     // -------------------------------MÉTODOS DE NAVEGACIÓN DE PELÍCULAS--------------------------------
    /**
     * Muestra la pantalla del menú de administración de películas.
     *
     * @param frameAnterior la ventana desde la cual se navegaba anteriormente.
     */
    public void mostrarMenuAdministrarPeliculas(JFrame frameAnterior);

    /**
     * Muestra la pantalla para agregar una nueva película.
     *
     * @param frameAnterior la ventana desde la cual se navegaba anteriormente.
     */
    public void mostrarAgregarPelicula(JFrame frameAnterior);

    /**
     * Muestra la pantalla de detalles de una película específica.
     *
     * @param peliculaDTO el objeto que contiene los datos de la película a
     * mostrar.
     */
    public void mostrarDetallesPelicula(PeliculaDTO peliculaDTO);

    /**
     * Muestra la pantalla para editar una película existente.
     *
     * @param peliculaDTO el objeto que contiene los datos actuales de la
     * película.
     */
    public void mostrarEditarPelicula(PeliculaDTO peliculaDTO);
    // -------------------------------FIN DE MÉTODOS DE NAVEGACIÓN DE PELÍCULAS-------------------------

    //Usuarios:
    public List<UsuarioDTO> mostrarListaUsuarios();

    public Boolean bloquearUsuario(UsuarioDTO usuario);

    public Boolean desbloquearUsuario(UsuarioDTO usuario);

    public List<UsuarioDTO> mostrarListaUsuariosFiltrada(EstadoUsuario estado, Rol rol, LocalDateTime fechaInicio, LocalDateTime fechaFin, String nombre);

    public UsuarioDTO validarUsuario(String nombreUsuario, String contrasena);

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

    List<FuncionDTO> buscarFuncionesPorPelicula(String nombrePelicula);

    List<FuncionDTO> buscarFuncionesPorFecha(LocalDateTime fechaHora) throws FuncionDatosIncorrectosException, FuncionFechaFuturaException;

    public LocalDateTime calcularHoraTerminoFuncion(String idFuncion);

    // -------------------------------MÉTODOS DE PELÍCULAS--------------------------------
    /**
     * Registra una nueva película en el sistema.
     *
     * @param peliculaDTO los datos de la película a registrar.
     * @return el DTO de la película registrada con datos actualizados.
     */
    public PeliculaDTO registrarPelicula(PeliculaDTO peliculaDTO);

    /**
     * Actualiza los datos de una película existente.
     *
     * @param peliculaDTO los nuevos datos de la película.
     * @return el DTO actualizado de la película.
     */
    public PeliculaDTO actualizarPelicula(PeliculaDTO peliculaDTO);

    /**
     * Muestra una lista de películas filtradas según los parámetros dados.
     *
     * @param activo si se desea filtrar por estado activo/inactivo; puede ser
     * null.
     * @param clasificacion la clasificación de la película; puede ser null para
     * no filtrar.
     * @param genero el género de la película; puede ser null para no filtrar.
     * @param titulo una coincidencia parcial o total con el título; puede ser
     * null.
     * @return una lista de películas que coinciden con los filtros aplicados.
     */
    public List<PeliculaDTO> mostrarPeliculasFiltradas(Boolean activo, String clasificacion, String genero, String titulo);

    /**
     * Busca una película en el sistema por su ID único.
     *
     * @param idPelicula el identificador de la película.
     * @return el DTO de la película encontrada, o null si no se encuentra.
     */
    public PeliculaDTO buscarPeliculaPorId(String idPelicula);

    /**
     * Busca y devuelve una película por su nombre.
     *
     * @param titulo el titulo de la película a buscar.
     * @return el DTO de la película si se encuentra, o {@code null} si no
     * existe.
     */
    public PeliculaDTO buscarPeliculaPorTitulo(String titulo);

    /**
     * Da de alta (activa) una película previamente dada de baja.
     *
     * @param peliculaDTO la película a activar.
     * @return true si la operación fue exitosa; false si falló.
     */
    public boolean darAltaPelicula(PeliculaDTO peliculaDTO);

    /**
     * Da de baja (desactiva) una película sin eliminarla permanentemente.
     *
     * @param peliculaDTO la película a desactivar.
     * @return true si la operación fue exitosa; false si falló.
     */
    public boolean darBajaPelicula(PeliculaDTO peliculaDTO);

    /**
     * Elimina permanentemente una película del sistema.
     *
     * @param peliculaDTO la película a eliminar.
     * @return true si la película fue eliminada con éxito; false si ocurrió un
     * error.
     */
    public boolean eliminarPelicula(PeliculaDTO peliculaDTO);
    // -------------------------------FIN DE MÉTODOS DE PELÍCULAS-------------------------

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
