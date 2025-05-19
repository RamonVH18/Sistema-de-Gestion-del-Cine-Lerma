/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import DTOs.AdministradorDTO;
import DTOs.AsientoFuncionDTO;
import pantallas.reservaBoletos.SeleccionarPelicula;
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
import DTOs.SalaNuevaDTO;
import DTOs.SalaViejaDTO;
import DTOs.TarjetaDTO;
import DTOs.UsuarioDTO;
import Excepciones.ActualizacionDeCargoException;
import Excepciones.ActualizacionEmpleadoException;
import Excepciones.ActualizacionSueldoException;
import Excepciones.ActualizarUsuarioException;
import Excepciones.AgregarSalaException;
import Excepciones.BuscarSalaException;
import Excepciones.CalcularCostoTotalException;
import Excepciones.CargarHistorialException;
import Excepciones.DarAltaPeliculaException;
import Excepciones.DarBajaPeliculaException;
import Excepciones.DisponibilidadAsientosException;
import Excepciones.EliminarPeliculaException;
import Excepciones.Empleados.DespedirEmpleadoException;
import Excepciones.EncontrarUsuarioException;
import Excepciones.FuncionBoletosVendidosException;
import Excepciones.FuncionCapacidadSalaException;
import Excepciones.FuncionCargaException;
import Excepciones.FuncionDatosIncorrectosException;
import Excepciones.FuncionDuracionException;
import Excepciones.FuncionSolapamientoSalaException;
import Excepciones.GestionReservaException;
import Excepciones.PagoException;
import Excepciones.PeliculasCargaException;
import Excepciones.ValidarCuentaException;
import Excepciones.GenerarBoletoException;
import Excepciones.ModificarSalaException;
import Excepciones.MostrarPeliculasException;
import Excepciones.ObtenerEmpleadoException;
import Excepciones.ObtenerEmpleadoPorCargoException;
import Excepciones.ObtenerPeliculasFiltradasException;
import Excepciones.PresentacionException;
import Excepciones.RegistrarNuevoEmpleadoException;
import Excepciones.RegistrarPeliculaException;
import Excepciones.RegistrarUsuarioException;
import Excepciones.ReservarAsientoFuncionException;
import Excepciones.ValidacionEmpleadoIdException;
import Excepciones.ValidacionSalaException;
import Excepciones.ValidarCampoAsientoException;
import Excepciones.ValidarEmpleadoException;
import Excepciones.ValidarUsuarioException;
import Excepciones.asientos.ErrorCargarAsientoException;
import Excepciones.asientos.ErrorGeneracionAsientoFuncionException;
import Excepciones.asientos.ErrorReservacionAsientoException;
import Excepciones.usuarios.ObtenerUsuariosException;
import Excepciones.validarActualizacionSueldoDeCargoException;
import GestionEmpleados.IManejoEmpleados;
import GestionEmpleados.ManejoEmpleados;
import GestionFunciones.IManejoFunciones;
import GestionFunciones.ManejoFunciones;
import enums.Cargo;
import enums.EstadoSala;
import enums.EstadoUsuario;
import enums.Rol;
import gestionPagos.GestionPagos;
import gestionPagos.IGestionPagos;
import gestionPeliculas.IManejoPeliculas;
import gestionPeliculas.ManejoPeliculas;
import gestionReservaBoletos.IManejoDeBoletos;
import gestionReservaBoletos.ManejoDeBoletos;
import gestionSalasAsientos.IManejoDeAsientos;
import gestionSalasAsientos.IManejoDeSalas;
import gestionSalasAsientos.ManejoDeAsientos;
import gestionSalasAsientos.ManejoDeSalas;
import gestionUsuarios.IManejoUsuarios;
import gestionUsuarios.ManejoUsuarios;
import java.awt.Component;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import pantallas.Empleados.ActualizarEmpleado;
import pantallas.Empleados.ActualizarEmpleadoDatos;
import pantallas.Empleados.DespedirEmpleados;
import pantallas.Empleados.DialogActualizarCargoEmpleado;
import pantallas.Empleados.DialogActualizarSueldoDeCargo;
import pantallas.Empleados.DialogSeleccionarEmpleado;
import pantallas.Empleados.MenuEmpleados;
import pantallas.Empleados.RegistrarEmpleado;
import pantallas.Empleados.SueldoOpciones;
import pantallas.Funciones.ConsultarFunciones;
import pantallas.Funciones.ProgramarFuncion;
import pantallas.IniciarSesion;
import pantallas.MenuPrincipalAdmin;
import pantallas.reservaBoletos.DetalleDelBoleto;
import pantallas.MenuPrincipalCliente;
import pantallas.Pagos.PantallaPago;
import pantallas.Pagos.PantallaPagoRechazado;
import pantallas.Reportes.Reportes;
import pantallas.Salas.AgregarSala;
import pantallas.Salas.ConsultarAsientosReservados;
import pantallas.Salas.ConsultarFuncionesSalas;
import pantallas.Salas.EstadisticasSala;
import pantallas.Salas.MenuSalas;
import pantallas.Salas.ModificarSala;
import pantallas.Salas.SeleccionarSala;
import pantallas.Usuarios.ConsultarUsuarios;
import pantallas.Usuarios.EditarUsuario;
import pantallas.Usuarios.HistorialCliente;
import pantallas.Usuarios.RegistrarUsuario;
import pantallas.administracionPeliculas.AgregarPelicula;
import pantallas.administracionPeliculas.DetallesPelicula;
import pantallas.administracionPeliculas.EditarPelicula;
import pantallas.administracionPeliculas.MenuAdministrarPeliculas;
import pantallas.reservaBoletos.SeleccionarAsientos;
import pantallas.reservaBoletos.SeleccionarMetodoPago;

/**
 *
 * @author Ramon Valencia
 */
public class ControlDeNavegacion implements IControl {

    //Instancias y clases para llamar metodos
    private final IManejoDeBoletos manejoDeBoletos = ManejoDeBoletos.getInstancia();
    private final IManejoDeSalas manejoDeSalas = ManejoDeSalas.getInstanceSalas();
    private final IGestionPagos gestionDePagos = GestionPagos.getInstancia();
    private final IManejoUsuarios gestionUsuarios = ManejoUsuarios.getInstance();
    private final IManejoFunciones gestionFunciones = ManejoFunciones.getInstanceDAO();
    private final IManejoPeliculas gestionPeliculas = ManejoPeliculas.getInstanceDAO();
    private final IManejoDeAsientos manejoDeAsientos = ManejoDeAsientos.getInstanceAsientos();
    private final IManejoEmpleados gestionEmpleados = ManejoEmpleados.getInstance();

    //Variables que se usan para guardar la pelicula Selecciona y la funcion seleccionada
    private PeliculaDTO peliculaSeleccionada;
    private FuncionDTO funcionSeleccionada;
    private UsuarioDTO usuarioActual;
    private AdministradorDTO administrador;
    private ClienteDTO cliente;

    private final String titulo = "¡ERROR!";

    private List<String> asientos;

    private int numAsientos;

    private static ControlDeNavegacion instancia;

    /**
     * Constructor privado para evitar múltiples instancias
     */
    private ControlDeNavegacion() {
    }

    /**
     * Metodo para obtener la instancia de la clase y que no se reinicie cada
     * vez que se use un metodo.
     *
     * @return La unica instancia de la clase
     */
    public static ControlDeNavegacion getInstancia() {
        if (instancia == null) {
            instancia = new ControlDeNavegacion();
        }
        return instancia;
    }

    @Override
    public UsuarioDTO obtenerUsuarioActual() {
        return usuarioActual;
    }

    @Override
    public AdministradorDTO obtenerAdministradorActual() {
        return administrador;
    }

    @Override
    public void guardarAdministradorActual(AdministradorDTO administrador) {
        this.administrador = administrador;
    }

    @Override
    public ClienteDTO obtenerClienteActual() {
        return cliente;
    }

    @Override
    public void guardarClienteActual(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    /**
     * Este metodo sirve para regresar al menu Principal, se encontrara la forma
     * de de fusionar
     */
    @Override
    public void mostrarMenuCliente(JFrame frameAnterior) {
        SwingUtilities.invokeLater(() -> {
            MenuPrincipalCliente pantalla = new MenuPrincipalCliente();
            pantalla.setLocationRelativeTo(null);
            pantalla.setVisible(true);
        });
    }

    /**
     * Metodo que se encarga de abrir la pantalla de pago rechazado
     */
    @Override
    public void mostrarMenuAdministrador(JFrame frameAnterior) {
        SwingUtilities.invokeLater(() -> {
            MenuPrincipalAdmin pantallaMenuAdmin = new MenuPrincipalAdmin();
            pantallaMenuAdmin.setLocationRelativeTo(null);
            pantallaMenuAdmin.setVisible(true);
        });
    }

    /**
     * Metodo que se encarga de abrir la pantalla de SeleccionarPelicula
     */
    @Override
    public void mostrarSeleccionarPelicula() {
        SwingUtilities.invokeLater(() -> {
            SeleccionarPelicula pantallaSeleccionarPelicula = new SeleccionarPelicula();
            pantallaSeleccionarPelicula.setLocationRelativeTo(null);
            pantallaSeleccionarPelicula.setVisible(true);
        });
    }

    /**
     * Metodo que se encarga de abrir la pantalla de SeleccionarAsientos.
     *
     * @param pelicula
     */
    @Override
    public void mostrarSeleccionarAsientos(PeliculaDTO pelicula) {
        SwingUtilities.invokeLater(() -> {
            peliculaSeleccionada = pelicula;
            SeleccionarAsientos pantallaSeleccionarAsientos = new SeleccionarAsientos(pelicula);
            pantallaSeleccionarAsientos.setLocationRelativeTo(null);
            pantallaSeleccionarAsientos.setVisible(true);
        });
    }

    /**
     * Metodo que se encarga de abrir la pantalla de SeleccionarMetodoPago.
     *
     * @param funcion
     */
    @Override
    public void mostrarSeleccionarMetodoPago(FuncionDTO funcion) {
        SwingUtilities.invokeLater(() -> {
            funcionSeleccionada = funcion;
            SeleccionarMetodoPago pantallaSeleccionarMetodoPago = new SeleccionarMetodoPago();
            pantallaSeleccionarMetodoPago.setLocationRelativeTo(null);
            pantallaSeleccionarMetodoPago.setVisible(true);
        });
    }

    /**
     * Metodo que se encarga de mostrar las pantallas de pago, Anteriormente
     * solian ser 3 sin embargo utilizando el factory method reducimos eso, de
     * esa manera solo un metodo, Este metodo recibe de string el tipo de metodo
     * de pago y en base a eso abre el JDailog correspondiente
     *
     * @param tipo
     * @throws Excepciones.PresentacionException
     */
    @Override
    public void mostrarPantallaPago(String tipo) throws PresentacionException {
        PantallaPago dialog = FactoryPantallasPago.crearPantallaPago(tipo);
        if (dialog != null) {
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        } else {

        }
    }

    /**
     * Metodo que se encarga de abrir la pantalla de DetalleBoleto
     */
    @Override
    public void mostrarDetalleBoleto() {
        SwingUtilities.invokeLater(() -> {
            DetalleDelBoleto pantallaDetalleDelBoleto = new DetalleDelBoleto();
            pantallaDetalleDelBoleto.setLocationRelativeTo(null);
            pantallaDetalleDelBoleto.setVisible(true);

        });
    }

    /**
     * Metodo que se encarga de abrir la pantalla de pago rechazado.
     */
    @Override
    public void mostrarPantallaPagoRechazado() {
        SwingUtilities.invokeLater(() -> {
            PantallaPagoRechazado pantallaPagoRechazado = new PantallaPagoRechazado();
            pantallaPagoRechazado.setLocationRelativeTo(null);
            pantallaPagoRechazado.setVisible(true);
        });
    }

    /**
     * Metodo que se encarga de obtener todas las peliculas que estan
     * disponibles y lo píde desde el subsistema
     *
     * @return
     */
    @Override
    public List<PeliculaDTO> obtenerPeliculas() {
        try {
            List<PeliculaDTO> peliculas = manejoDeBoletos.cargarPeliculasActivas();
            return peliculas;
        } catch (PeliculasCargaException e) {
            //PONER JOptionPane y cerrar la pantalla actual y volver al menuPrincipal
            JOptionPane.showMessageDialog(null, e.getMessage(), titulo, JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    /**
     * Metodo que se usa para cuando una clase Boundary necesite consultar la
     * pelicula que fue seleccionada
     *
     * @return La pelicula seleccionada
     */
    @Override
    public PeliculaDTO consultarPelicula() {
        return peliculaSeleccionada;
    }

    //FUNCION
    /**
     * Metodo que se encarga de obtener todas las funciones que estan
     * disponibles y lo píde desde el subsistema
     *
     * @param nombrePelicula Nombre de la pelicula dada por el parametro
     * @return Las funciones, null en caso de una excepcion
     */
    @Override
    public List<FuncionDTO> obtenerFunciones(String nombrePelicula) {
        try {
            List<FuncionDTO> funciones = manejoDeBoletos.cargarFuncionesPelicula(nombrePelicula);
            return funciones;
        } catch (FuncionCargaException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), titulo, JOptionPane.ERROR_MESSAGE);
            return new ArrayList<>();
        }
    }

    /**
     * Metodo que se usa para cuando una clase Boundary necesite consultar la
     * funcion que fue seleccionada
     *
     * @return La funcion seleccionada
     */
    @Override
    public FuncionDTO consultarFuncion() {
        return funcionSeleccionada;
    }

    //ASIENTOS
    /**
     * Metodo que consigue todos los asientosDisponibles en una funcion
     *
     * @param funcion Funcion dada por el parametro
     * @return Los asiento disponibles, cero en caso de una excepcion
     */
    @Override
    public int obtenerAsientosDisponibles(FuncionDTO funcion) {
        try {
            int asientosDisponibles = manejoDeBoletos.consultarDisponibilidadAsientos(funcion);
            return asientosDisponibles;
        } catch (DisponibilidadAsientosException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), titulo, JOptionPane.ERROR_MESSAGE);
            return 0;
        }
    }

    /**
     * Metodo que se encarga de guardar el numero de asientos.
     *
     * @param numAsientos numero de los asientos dado por el parametro
     */
    @Override
    public void guardarNumeroAsientos(int numAsientos) {
        this.numAsientos = numAsientos;
    }

    /**
     * Metodo para consultar el numero de asiento que se seleccionaron
     *
     * @return El numero de los asientos seleccionados
     */
    @Override
    public int consultarNumeroAsientos() {
        return numAsientos;
    }

    //VALIDACIONES
    /**
     * Metodo para validar los campos numeros de asientos
     *
     * @param texto Texto dado por el parametro
     * @param funcion Funcion dada por el parametro
     * @return El texto validado o null en caso de excepcion
     */
    @Override
    public String validarCamposAsientos(String texto, FuncionDTO funcion) {
        try {
            if (manejoDeBoletos.validarCampoAsiento(texto, funcion)) {
                String textoValidado = texto.trim();
                numAsientos = Integer.parseInt(textoValidado);
                manejoDeBoletos.validarDisponibilidaDeAsientos(numAsientos, funcion);

                return textoValidado;
            }
            return null;
        } catch (ValidarCampoAsientoException | DisponibilidadAsientosException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), titulo, JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    /**
     * Metodo para obtener los metodos de pago que estan guardados en el
     * sistema.
     *
     * @return Una lista de los metodos de pago
     */
    @Override
    public List<MetodoPagoDTO> obtenerMetodosPago() {
        List<MetodoPagoDTO> metodosPago = manejoDeBoletos.cargarMetodosPago();
        return metodosPago;
    }

    /**
     * Metodo para obtener una lista con todo los asientos reservados.
     *
     * @param funcion La funcion dada por el parametro
     * @param numAsientos Numero de asientos reservados dado por el parametro
     * @return Retorna una lista de los asientos reservados, null en caso de
     * excepcion
     * @throws GestionReservaException
     */
    private List<String> obtenerListaAsientosReservados(FuncionDTO funcion, int numAsientos, ClienteDTO cliente) {
        try {
            List<String> asientosReservados = manejoDeBoletos.reservarAsientoFuncion(funcion, numAsientos, cliente);
            this.asientos = asientosReservados;
            return asientosReservados;
        } catch (ReservarAsientoFuncionException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), titulo, JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    /**
     * Metodo que carga el boleto de una funcion.
     *
     * @return El boleto generado de la funcion, null en caso de excepcion
     */
    @Override
    public BoletoDTO cargarBoleto(ClienteDTO cliente) {
        List<String> asientosReservados = obtenerListaAsientosReservados(funcionSeleccionada, numAsientos, cliente);
        try {
            return manejoDeBoletos.generarBoleto(peliculaSeleccionada, funcionSeleccionada, asientosReservados, cliente);
        } catch (GenerarBoletoException ex) {
            Logger.getLogger(ControlDeNavegacion.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * Metodo que verifica la cuenta de mercado pago dada por el parametro.
     *
     * @param cuentaMercado Cuenta de mercado pago dada por el parametro
     * @return La cuenta de mercado pago existente, null en caso de excepcion
     */
    @Override
    public CuentaMercadoDTO verificarCuentaMercado(CuentaMercadoDTO cuentaMercado) {
        try {
            return gestionDePagos.validarMercado(cuentaMercado);
        } catch (ValidarCuentaException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), titulo, JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    /**
     * Metodo que verifica la cuenta de PayPal dada por el parametro.
     *
     * @param cuentaPaypal Cuenta de PayPal dada por el parametro
     * @return La cuenta de PayPal existente, null en caso de excepcion
     */
    @Override
    public PaypalDTO verificarCuentaPaypal(PaypalDTO cuentaPaypal) {
        try {
            return gestionDePagos.validarCuentaPaypal(cuentaPaypal);
        } catch (ValidarCuentaException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), titulo, JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    /**
     * Metodo que verifica la cuenta de tarjeta dada por el parametro.
     *
     * @param cuentaTarjeta Cuenta de tarjeta dada por el parametro
     * @return La cuenta de tarjeta existente, null en caso de excepcion
     */
    @Override
    public TarjetaDTO verificarCuentaTarjeta(TarjetaDTO cuentaTarjeta) {
        try {
            return gestionDePagos.validarTarjeta(cuentaTarjeta);
        } catch (ValidarCuentaException ex) {
            Logger.getLogger(ControlDeNavegacion.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * Metodo que calcula el costo total de los boletos seleccionados de una
     * funcion.
     *
     * @return El costo total de los boletos seleccionados, 0 en caso de
     * excepcion
     */
    @Override
    public Double calcularCostoTotal() {
        try {
            Double costoTotal = manejoDeBoletos.calcularCostoTotal(numAsientos, funcionSeleccionada);
            return costoTotal;
        } catch (CalcularCostoTotalException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), titulo, JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    /**
     * Metodo que calcula el saldo de una cuenta de mercado pago.
     *
     * @param mercadoPago Cuenta de mercado pago dada por el parametro
     * @param pago Pago que contiene el monto, fecha y estado del pago
     */
    @Override
    public void actualizarSaldoMercado(CuentaMercadoDTO mercadoPago, PagoDTO pago) {
        gestionDePagos.actualizarSaldoMercado(mercadoPago, pago);
    }

    /**
     * Metodo que calcula el saldo de una cuenta de PayPal.
     *
     * @param paypal Cuenta de PayPal dada por el parametro
     * @param pago Pago que contiene el monto, fecha y estado del pago
     */
    @Override
    public void actualizarSaldoPaypal(PaypalDTO paypal, PagoDTO pago) {
        gestionDePagos.actualizarSaldoPaypal(paypal, pago);

    }

    /**
     * Metodo que calcula el saldo de una cuenta de tarjeta.
     *
     * @param tarjeta Cuenta de tarjeta dada por el parametro
     * @param pago Pago que contiene el monto, fecha y estado del pago
     */
    @Override
    public void actualizarSaldoTarjeta(TarjetaDTO tarjeta, PagoDTO pago) {
        gestionDePagos.actualizarSaldoTarjeta(tarjeta, pago);
    }

    /**
     * Metodo que procesa el pago de una cuenta de mercado pago.
     *
     * @param mercadoPago Cuenta de mercado pago dada por el parametro
     * @param pago Pago que contiene el monto, fecha y estado del pago
     */
    @Override
    public void procesarPagoMercado(CuentaMercadoDTO mercadoPago, PagoDTO pago) {
        try {
            gestionDePagos.procesarPagoMercado(mercadoPago, pago);
        } catch (PagoException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), titulo, JOptionPane.ERROR_MESSAGE);
        } catch (ValidarCuentaException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), titulo, JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Metodo que procesa el pago de una cuenta de PayPal.
     *
     * @param paypal Cuenta de PayPal dada por el parametro
     * @param pago Pago que contiene el monto, fecha y estado del pago
     */
    @Override
    public void procesarPagoPaypal(PaypalDTO paypal, PagoDTO pago) {
        try {
            gestionDePagos.procesarPagoPaypal(paypal, pago);
        } catch (PagoException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), titulo, JOptionPane.ERROR_MESSAGE);
        } catch (ValidarCuentaException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), titulo, JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Metodo que procesa el pago de una cuenta de tarjeta.
     *
     * @param tarjeta Cuenta de tarjeta dada por el parametro
     * @param pago Pago que contiene el monto, fecha y estado del pago
     */
    @Override
    public void procesarPagoTarjeta(TarjetaDTO tarjeta, PagoDTO pago) {
        try {
            gestionDePagos.procesarPagoTarjeta(tarjeta, pago);
        } catch (PagoException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), titulo, JOptionPane.ERROR_MESSAGE);
        } catch (ValidarCuentaException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), titulo, JOptionPane.ERROR_MESSAGE);
        }
    }

    /*
    --------------INICIO DE LOS METODOS DEL CONTROL DE NAVEGACION DE SALAS--------------
     */
    /**
     * Metodo para abrir el menu del caso de uso de gestion de salas
     *
     * @param frameAnterior
     */
    @Override
    public void mostrarMenuSalas(JFrame frameAnterior) {
        SwingUtilities.invokeLater(() -> {
            MenuSalas pantallaMenuSalas = new MenuSalas();
            pantallaMenuSalas.setLocationRelativeTo(null);
            pantallaMenuSalas.setVisible(true);
            frameAnterior.dispose();
        });
    }

    /**
     * Metodo para abrir la pantalla de agregar sala
     *
     * @param frameAnterior
     */
    @Override
    public void mostrarAgregarSala(JFrame frameAnterior) {
        SwingUtilities.invokeLater(() -> {
            AgregarSala pantallaAgregarSala = new AgregarSala();
            pantallaAgregarSala.setLocationRelativeTo(null);
            pantallaAgregarSala.setVisible(true);
            frameAnterior.dispose();
        });
    }

    @Override
    public void agregarSala(SalaNuevaDTO salaNueva) {
        try {
            manejoDeSalas.agregarSala(salaNueva);
            JOptionPane.showMessageDialog(null, "Sala agregada exitosamente!!", "SALA", JOptionPane.INFORMATION_MESSAGE);
        } catch (AgregarSalaException e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage(), "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }

    }

    @Override
    public Boolean validarCamposAgregarSala(String numSala, String numAsientos) {
        try {
            manejoDeSalas.validarSala(numSala, numAsientos);
            return Boolean.TRUE;
        } catch (ValidacionSalaException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "¡ERROR!", JOptionPane.ERROR_MESSAGE);
            return Boolean.FALSE;
        }
    }

    @Override
    public void mostrarSeleccionarSala(JFrame frameAnterior) {
        SwingUtilities.invokeLater(() -> {
            SeleccionarSala pantallaAgregarSala = new SeleccionarSala();
            pantallaAgregarSala.setLocationRelativeTo(null);
            pantallaAgregarSala.setVisible(true);
            frameAnterior.dispose();
        });
    }

    @Override
    public SalaViejaDTO consultarSala(String numSala) {

        try {
            SalaViejaDTO sala = manejoDeSalas.cargarSalaUnica(numSala);
            return sala;
        } catch (BuscarSalaException e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage(), "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    @Override
    public List<SalaViejaDTO> consultarSalas(String filtro, Boolean filtrarActivas) {
        try {
            List<SalaViejaDTO> salas = manejoDeSalas.cargarSalas(filtro, filtrarActivas);
            return salas;
        } catch (BuscarSalaException e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage(), "¡ERROR!", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    @Override
    public void mostrarModificarSala(JFrame frameAnterior, SalaViejaDTO sala) {
        SwingUtilities.invokeLater(() -> {
            ModificarSala pantallaAgregarSala = new ModificarSala(sala);
            pantallaAgregarSala.setLocationRelativeTo(null);
            pantallaAgregarSala.setVisible(true);
            frameAnterior.dispose();
        });
    }

    @Override
    public Boolean modificarSala(String numSala, EstadoSala estadoSala) {
        try {
            Boolean confirmacion = manejoDeSalas.modificarEstadoSala(numSala, estadoSala);
            return confirmacion;
        } catch (ModificarSalaException e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage(), "¡ERROR!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    /**
     * Metodo para abrir la pantalla de estadisticas de una las sala
     *
     * @param frameAnterior
     */
    @Override
    public void mostrarEstadisticasSala(JFrame frameAnterior) {
        SwingUtilities.invokeLater(() -> {
            EstadisticasSala pantallaEstadisticasSala = new EstadisticasSala();
            pantallaEstadisticasSala.setLocationRelativeTo(null);
            pantallaEstadisticasSala.setVisible(true);
            frameAnterior.dispose();
        });
    }

    @Override
    public void mostrarConsultarFuncionesSalas(JFrame frameAnterior) {
        SwingUtilities.invokeLater(() -> {
            ConsultarFuncionesSalas pantallaConsultarFuncionesSalas = new ConsultarFuncionesSalas();
            pantallaConsultarFuncionesSalas.setLocationRelativeTo(null);
            pantallaConsultarFuncionesSalas.setVisible(true);
            frameAnterior.dispose();
        });
    }

    @Override
    public void mostrarConsultarAsientosReservados(JFrame frameAnterior, FuncionDTO funcionDTO) {
        SwingUtilities.invokeLater(() -> {
            ConsultarAsientosReservados pantallaConsultarAsientosReservados = new ConsultarAsientosReservados(funcionDTO);
            pantallaConsultarAsientosReservados.setLocationRelativeTo(null);
            pantallaConsultarAsientosReservados.setVisible(true);
            frameAnterior.dispose();
        });
    }

    @Override
    public List<AsientoFuncionDTO> agregarAsientoFuncion(FuncionDTO funcionSelecionada, SalaViejaDTO salaSelecionada) {
        try {
            return manejoDeAsientos.registrarAsientoFuncion(funcionSelecionada, salaSelecionada);
        } catch (ErrorGeneracionAsientoFuncionException e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage(), "¡ERROR!", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    @Override
    public Boolean reservarAsientos(List<AsientoFuncionDTO> asientosAReservar) {
        try {
            return manejoDeAsientos.reservarAsientosFuncion(asientosAReservar);
        } catch (ErrorReservacionAsientoException e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage(), "¡ERROR!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public List<AsientoFuncionDTO> cargarListaAsientos(FuncionDTO funcion, Boolean mostrarDisponibles) {
        try {
            return manejoDeAsientos.cargarListaAsientos(funcion, mostrarDisponibles);
        } catch (ErrorCargarAsientoException e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage(), "¡ERROR!", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    /*
    --------------FIN DE LOS METODOS DEL CONTROL DE NAVEGACION DE SALAS--------------
     */
 /*
    --------------INICIO DE LOS METODOS DEL CONTROL DE NAVEGACION DE FUNCIONES--------------
     */
    @Override
    public void mostrarConsultarFunciones(JFrame frameAnterior, String nombrePelicula) {
        SwingUtilities.invokeLater(() -> {
            ConsultarFunciones pantalla = new ConsultarFunciones(nombrePelicula, gestionFunciones);
            pantalla.setLocationRelativeTo(null);
            pantalla.setVisible(true);
            if (frameAnterior != null) {
                frameAnterior.dispose();
            }
        });
    }

    @Override
    public void mostrarProgramarFuncion(ConsultarFunciones frameAnterior, String nombrePelicula) {
        SwingUtilities.invokeLater(() -> {
            ProgramarFuncion pantalla = new ProgramarFuncion(frameAnterior, nombrePelicula);
            pantalla.setLocationRelativeTo(null);
            pantalla.setVisible(true);
        });
    }

    @Override
    public List<FuncionDTO> consultarFuncionesFiltradas(String textoFiltro) {

        try {
            return gestionFunciones.buscarFuncionesFiltradas(textoFiltro);
        } catch (FuncionDatosIncorrectosException e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage(), "¡ERROR!", JOptionPane.ERROR_MESSAGE);
            return new ArrayList<>();
        }
    }

    /*
    --------------Fin DE LOS METODOS DEL CONTROL DE NAVEGACION DE FUNCIONES--------------
     */
 /*
    --------------INICIO DE LOS METODOS DEL CONTROL DE NAVEGACION DE ADMINISTRACION DE PELICULAS--------------
     */
    @Override
    public void mostrarMenuAdministrarPeliculas(JFrame frameAnterior) {
        SwingUtilities.invokeLater(() -> {
            MenuAdministrarPeliculas pantallaMenuAdministrarPeliculas = new MenuAdministrarPeliculas();
            pantallaMenuAdministrarPeliculas.setLocationRelativeTo(null);
            pantallaMenuAdministrarPeliculas.setVisible(true);
            frameAnterior.dispose();
        });
    }

    @Override
    public void mostrarAgregarPelicula(JFrame frameAnterior) {
        SwingUtilities.invokeLater(() -> {
            AgregarPelicula pantallaAgregarPelicula = new AgregarPelicula();
            pantallaAgregarPelicula.setLocationRelativeTo(null);
            pantallaAgregarPelicula.setVisible(true);
        });
    }

    @Override
    public void mostrarDetallesPelicula(PeliculaDTO peliculaDTO) {
        SwingUtilities.invokeLater(() -> {
            DetallesPelicula pantallaDetallesPelicula = new DetallesPelicula(peliculaDTO);
            pantallaDetallesPelicula.setLocationRelativeTo(null);
            pantallaDetallesPelicula.setVisible(true);
        });
    }

    @Override
    public void mostrarEditarPelicula(PeliculaDTO peliculaDTO) {
        SwingUtilities.invokeLater(() -> {
            EditarPelicula pantallaEditarPelicula = new EditarPelicula(peliculaDTO);
            pantallaEditarPelicula.setLocationRelativeTo(null);
            pantallaEditarPelicula.setVisible(true);
        });
    }

    @Override
    public PeliculaDTO encontrarPelicula(String nombrePelicula) {
        try {
            return gestionPeliculas.buscarPelicula(nombrePelicula);
        } catch (MostrarPeliculasException e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage(), "¡ERROR!", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    /*
    --------------FIN DE LOS METODOS DEL CONTROL DE NAVEGACION DE ADMINISTRACION DE PELICULAS--------------
     */
    //METODOS DE NAVEGACION DE LA GESTION DE USUARIOS
    @Override
    public void mostrarIniciarSesion() {
        SwingUtilities.invokeLater(() -> {
            IniciarSesion pantallaIniciarSecion = new IniciarSesion();
            pantallaIniciarSecion.setLocationRelativeTo(null);
            pantallaIniciarSecion.setVisible(true);
        });
    }

    @Override
    public void mostrarGestionDeUsuarios(JFrame frameAnterior, AdministradorDTO admin) {
        SwingUtilities.invokeLater(() -> {
            ConsultarUsuarios gestionDeUsuarios = new ConsultarUsuarios(admin);
            gestionDeUsuarios.setLocationRelativeTo(null);
            gestionDeUsuarios.setVisible(true);
            frameAnterior.dispose();
        });
    }

    @Override
    public void mostrarEditarUsuario(JFrame frameAnterior, ClienteDTO cliente, ClienteDTO clienteAlMando) {
        SwingUtilities.invokeLater(() -> {
            EditarUsuario pantallaEditarUsuario = new EditarUsuario(frameAnterior, cliente, clienteAlMando);
            pantallaEditarUsuario.setLocationRelativeTo(null);
            pantallaEditarUsuario.setVisible(true);
            frameAnterior.dispose();
        });
    }

    @Override
    public void mostrarMenuReportes(JFrame frameAnterior) {
        SwingUtilities.invokeLater(() -> {
            Reportes pantallaReportes = new Reportes();
            pantallaReportes.setLocationRelativeTo(null);
            pantallaReportes.setVisible(true);
            frameAnterior.dispose();
        });
    }

    @Override
    public UsuarioDTO validarUsuario(String nombreUsuario, String contrasena) {
        try {
            usuarioActual = gestionUsuarios.validarUsuario(nombreUsuario, contrasena);
            return usuarioActual;
        } catch (ValidarUsuarioException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), titulo, JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    @Override
    public void mostrarRegistrarUsuario(JFrame frameAnterior) {
        SwingUtilities.invokeLater(() -> {
            RegistrarUsuario pantallaRegistrarUsuario = new RegistrarUsuario();
            pantallaRegistrarUsuario.setLocationRelativeTo(null);
            pantallaRegistrarUsuario.setVisible(true);
            frameAnterior.dispose();
        });
    }

    @Override
    public void mostrarHistorialCliente(JFrame frameAnterior, ClienteDTO cliente) {
        SwingUtilities.invokeLater(() -> {
            HistorialCliente pantallaHistorialCliente = new HistorialCliente(frameAnterior, cliente);
            pantallaHistorialCliente.setLocationRelativeTo(null);
            pantallaHistorialCliente.setVisible(true);
            frameAnterior.dispose();
        });
    }

    @Override
    public List<UsuarioDTO> mostrarListaUsuarios() {
        try {
            return gestionUsuarios.mostrarListaUsuarios();
        } catch (ObtenerUsuariosException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), titulo, JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    @Override
    public Boolean bloquearUsuario(UsuarioDTO usuario) {
        try {
            return gestionUsuarios.bloquearUsuario(usuario);
        } catch (ActualizarUsuarioException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), titulo, JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    @Override
    public Boolean desbloquearUsuario(UsuarioDTO usuario) {
        try {
            return gestionUsuarios.desbloquearUsuario(usuario);
        } catch (ActualizarUsuarioException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), titulo, JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    @Override
    public List<UsuarioDTO> mostrarListaUsuariosFiltrada(EstadoUsuario estado, Rol rol, LocalDateTime fechaInicio, LocalDateTime fechaFin, String nombre) {
        try {
            return gestionUsuarios.mostrarListaUsuariosFiltrada(estado, rol, fechaInicio, fechaFin, nombre);
        } catch (ObtenerUsuariosException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), titulo, JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    @Override
    public ClienteDTO registrarCliente(ClienteDTO cliente) {
        try {
            return gestionUsuarios.registrarCliente(cliente);
        } catch (RegistrarUsuarioException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), titulo, JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    @Override
    public ClienteDTO actualizarCliente(ClienteDTO cliente) {
        try {
            return gestionUsuarios.actualizarCliente(cliente);
        } catch (ActualizarUsuarioException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), titulo, JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    @Override
    public ClienteDTO obtenerCliente(String nombreUsuario, String contrasena) {
        try {
            return gestionUsuarios.obtenerCliente(nombreUsuario, contrasena);
        } catch (EncontrarUsuarioException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), titulo, JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    @Override
    public List<CompraDTO> cargarHistorialCompras(ClienteDTO cliente) {
        try {
            return gestionUsuarios.cargarHistorialCompras(cliente);
        } catch (CargarHistorialException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), titulo, JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    @Override
    public AdministradorDTO registrarAdministrador(AdministradorDTO administrador) {
        try {
            return gestionUsuarios.registrarAdministrador(administrador);
        } catch (RegistrarUsuarioException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), titulo, JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    @Override
    public AdministradorDTO obtenerAdministrador(String nombreUsuario, String contrasena) {
        try {
            return gestionUsuarios.obtenerAdministrador(nombreUsuario, contrasena);
        } catch (EncontrarUsuarioException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), titulo, JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    /*
    --------------FIN DE LOS METODOS DEL CONTROL DE NAVEGACION DE GESTION DE USUARIOS--------------
     */

 /*
    --------------METODOS DE FUNCION--------------
     */
    @Override
    public FuncionDTO registrarFuncion(FuncionDTO funcionDTO) {
        try {
            return gestionFunciones.registraFuncion(funcionDTO);
        } catch (FuncionDatosIncorrectosException | FuncionSolapamientoSalaException | FuncionCapacidadSalaException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), titulo, JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    @Override
    public Boolean eliminarFuncion(FuncionDTO funcionDTO) {
        try {
            return gestionFunciones.eliminarFuncion(funcionDTO);
        } catch (FuncionDatosIncorrectosException | FuncionBoletosVendidosException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), titulo, JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    @Override
    public List<FuncionDTO> buscarFunciones(String nombrePelicula, LocalDateTime fechaHora) {
        try {
            return gestionFunciones.buscarFunciones(nombrePelicula, null);
        } catch (FuncionDatosIncorrectosException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), titulo, JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    @Override
    public LocalDateTime calcularHoraTerminoFuncion(String idFuncion) {
        try {
            return gestionFunciones.calcularHoraTerminoFuncion(idFuncion);
        } catch (FuncionDuracionException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), titulo, JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    /*
    --------------FIN DE METODOS DE FUNCION--------------
     */

 /*
    --------------METODOS DE ADMINISTRAR PELICULAS--------------
     */
    @Override
    public PeliculaDTO registrarPelicula(PeliculaDTO peliculaDTO) {
        try {
            return gestionPeliculas.registrarPelicula(peliculaDTO);
        } catch (RegistrarPeliculaException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), titulo, JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    @Override
    public PeliculaDTO editarPelicula(PeliculaDTO peliculaDTO) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean darAltaPelicula(PeliculaDTO peliculaDTO) {
        try {
            return gestionPeliculas.darAltaPelicula(peliculaDTO);
        } catch (DarAltaPeliculaException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), titulo, JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public boolean darBajaPelicula(PeliculaDTO peliculaDTO) {
        try {
            return gestionPeliculas.darBajaPelicula(peliculaDTO);
        } catch (DarBajaPeliculaException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), titulo, JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public boolean eliminarPelicula(PeliculaDTO peliculaDTO) {
        try {
            return gestionPeliculas.eliminarPelicula(peliculaDTO);
        } catch (EliminarPeliculaException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), titulo, JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public List<PeliculaDTO> mostrarPeliculasFiltradas(Boolean activo, String clasificacion, String genero, String titulo) {
        try {
            return gestionPeliculas.mostrarPeliculasFiltradas(activo, clasificacion, genero, titulo);
        } catch (ObtenerPeliculasFiltradasException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), titulo, JOptionPane.ERROR_MESSAGE);
            return new ArrayList<>();
        }
    }

    /*
    --------------FIN DE METODOS DE ADMINISTRAR PELICULAS--------------
     */

 /*
    --------------INICIO DE METODOS DE ADMINISTRAR EMPLEADOS --------------
     */
    @Override
    public void mostrarMenuAdministrarEmpleados(JFrame frameAnterior) {

        SwingUtilities.invokeLater(() -> {

            MenuEmpleados pantallaMenu = new MenuEmpleados();
            pantallaMenu.setLocationRelativeTo(null);
            pantallaMenu.setVisible(true);

            if (frameAnterior != null) {
                frameAnterior.dispose();
            }
            System.out.println("Mostrando Menu Administrador de empleados");
        });
    }

    @Override
    public void mostrarRegistrarEmpleado(JFrame frameAnterior) {

        SwingUtilities.invokeLater(() -> {

            RegistrarEmpleado pantallaRegistrar = new RegistrarEmpleado();
            pantallaRegistrar.setLocationRelativeTo(null);
            pantallaRegistrar.setVisible(true);

            if (frameAnterior != null) {
                frameAnterior.dispose();
            }
        });
    }

    @Override
    public void mostrarActualizarDatosEmpleado(JFrame frameAnterior, String empleadoId) {

        SwingUtilities.invokeLater(() -> {
            try {
                // CHECAR ESTO DE LA EXCEPCIONES
                ActualizarEmpleadoDatos pantallaActualizarDatos = new ActualizarEmpleadoDatos(empleadoId);
                pantallaActualizarDatos.setLocationRelativeTo(null);
                pantallaActualizarDatos.setVisible(true);

                if (frameAnterior != null) {
                    frameAnterior.dispose();
                }
            } catch (ValidarEmpleadoException ex) {
                Logger.getLogger(ControlDeNavegacion.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ObtenerEmpleadoException ex) {
                Logger.getLogger(ControlDeNavegacion.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ValidacionEmpleadoIdException ex) {
                Logger.getLogger(ControlDeNavegacion.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    @Override
    public void mostrarDialogActualizarCargoDeEmpleado(JFrame frameAnterior, EmpleadoDTO empleadoActual) {

        // ponemos el dialog con el frame dandole frame padre,el dto
        DialogActualizarCargoEmpleado dialogoCargo = new DialogActualizarCargoEmpleado(frameAnterior, true, empleadoActual);
        dialogoCargo.setVisible(true);

    }

    @Override
    public void iniciarFlujoActualizarCargo(JFrame framePadre) {
        SwingUtilities.invokeLater(() -> {

            EmpleadoDTO empleado = solicitarSeleccionEmpleado(framePadre); // Llama al metodo de seleccion

            if (empleado != null) {
                // El usuario seleccionó un empleado, ahora muestra el dialog para actualizar el cargo
                mostrarDialogActualizarCargoDeEmpleado(framePadre, empleado);
            }
            // Si empleado es null, el usuario cancelo, no se hace nada más en este flujo.
        });
    }

    @Override
    public EmpleadoDTO solicitarSeleccionEmpleado(JFrame padreFrame) {

        DialogSeleccionarEmpleado dialogoSeleccion = new DialogSeleccionarEmpleado(padreFrame, true); // true para modal
        dialogoSeleccion.setLocationRelativeTo(padreFrame); // Centrar
        dialogoSeleccion.setVisible(true); // Esta línea bloquea hasta que el diálogo se cierre

        // despues de que el dialog se cierra, obtenemos el empleado seleccionado
        return dialogoSeleccion.getEmpleadoSeleccionado(); // Necesitas este método en tu DialogSeleccionarEmpleado
    }

    @Override
    public void mostrarFrameSueldoOpciones(JFrame frameAnterior) {

        // esto abre el frame de las 2 opciones de actualizacion de sueldo
        // el individual se selecciona y mediante un JOptionPane se actualiza pero con el de cargo si tiene un dialog
        SwingUtilities.invokeLater(() -> {

            SueldoOpciones pantallaOpcionesSueldo = new SueldoOpciones();
            pantallaOpcionesSueldo.setLocationRelativeTo(null);
            pantallaOpcionesSueldo.setVisible(true);

            if (frameAnterior != null) {
                frameAnterior.dispose();
            }
        });
    }

    @Override
    public void mostrarActualizacionSueldoPorCargo(JFrame frameAnterior) {

        SwingUtilities.invokeLater(() -> {

            DialogActualizarSueldoDeCargo dialogActSueldoPorCargo = new DialogActualizarSueldoDeCargo(frameAnterior, true);
            dialogActSueldoPorCargo.setLocationRelativeTo(null);
            dialogActSueldoPorCargo.setVisible(true);

            if (frameAnterior != null) {
                frameAnterior.dispose();
            }
        });

    }

    @Override
    public boolean procesarActualizacionSueldoIndividual(String empleadoId, double nuevoSueldo, Component parentComponent) {
        try {

            if (nuevoSueldo <= 0) {
                JOptionPane.showMessageDialog(parentComponent, "El nuevo sueldo debe ser un valor positivo.", "Error de Validación", JOptionPane.WARNING_MESSAGE);
                return false;
            }
            if (nuevoSueldo < 1000 || nuevoSueldo > 200000) { // Rango de tu EmpleadoBO
                JOptionPane.showMessageDialog(parentComponent, "El sueldo está fuera de los rangos permitidos (1,000 - 200,000).", "Error de Validación", JOptionPane.WARNING_MESSAGE);
                return false;
            }

            boolean exito = gestionEmpleados.actualizarSueldoEmpleadoIndividual(empleadoId, nuevoSueldo);
            if (exito) {
                JOptionPane.showMessageDialog(parentComponent, "Sueldo del empleado actualizado exitosamente.", "Actualización Exitosa", JOptionPane.INFORMATION_MESSAGE);
            } else {

                JOptionPane.showMessageDialog(parentComponent, "No se pudo actualizar el sueldo.", titulo, JOptionPane.ERROR_MESSAGE);
            }
            return exito;
        } catch (ActualizacionSueldoException | ValidarEmpleadoException | ValidacionEmpleadoIdException e) {
            JOptionPane.showMessageDialog(parentComponent, e.getMessage(), titulo, JOptionPane.ERROR_MESSAGE);
            return false;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(parentComponent, "Error inesperado al actualizar sueldo: " + e.getMessage(), titulo, JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
    }

    // inicialmente esto era un JOptionPane que en el mismo frame se actualizaba, por lo que lo movi aca
    @Override
    public void iniciarFlujoActualizarSueldoIndividual(JFrame framePadreSueldoOpciones) {

        SwingUtilities.invokeLater(() -> {
            EmpleadoDTO empleado = solicitarSeleccionEmpleado(framePadreSueldoOpciones); // Llama al metodo de seleccion

            if (empleado != null) {
                String nuevoSueldoStr = JOptionPane.showInputDialog(
                        framePadreSueldoOpciones,
                        "Ingrese el nuevo sueldo para " + empleado.getNombre() + empleado.getApellidoP() + ":",
                        "Actualizar Sueldo Individual",
                        JOptionPane.PLAIN_MESSAGE
                );

                if (nuevoSueldoStr != null && !nuevoSueldoStr.trim().isEmpty()) {
                    try {
                        double nuevoSueldoDouble = Double.parseDouble(nuevoSueldoStr.trim());

                        boolean exito = procesarActualizacionSueldoIndividual(empleado.getId(), nuevoSueldoDouble, framePadreSueldoOpciones);

                        if (exito) {

                        }
                    } catch (Exception nfe) {
                        JOptionPane.showMessageDialog(
                                framePadreSueldoOpciones,
                                "El sueldo ingresado no es un número válido.",
                                "Error de Formato",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                }

            }
        });
    }

    @Override
    public void mostrarActualizarEmpleado(JFrame frameAnterior) {

        SwingUtilities.invokeLater(() -> {

            ActualizarEmpleado pantallaActualizarEmpleado = new ActualizarEmpleado();
            pantallaActualizarEmpleado.setLocationRelativeTo(null);
            pantallaActualizarEmpleado.setVisible(true);

            if (frameAnterior != null) {
                frameAnterior.dispose();
            }
        });

    }

    @Override
    public void mostrarDespedirEmpleado(JFrame frameAnterior) {

        SwingUtilities.invokeLater(() -> {

            DespedirEmpleados pantallaDespedirmpleado = new DespedirEmpleados();
            pantallaDespedirmpleado.setLocationRelativeTo(null);
            pantallaDespedirmpleado.setVisible(true);

            if (frameAnterior != null) {
                frameAnterior.dispose();
            }
        });

    }

    // ------------------ METODOS DE ADMINISTRACION EMPLEADO DE OPERACIONES
    @Override
    public EmpleadoDTO controlRegistrarNuevoEmpleado(EmpleadoDTO empleadoDTO) {
        try {
            return gestionEmpleados.registrarNuevoEmpleado(empleadoDTO);
        } catch (ValidarEmpleadoException vex) {
            JOptionPane.showMessageDialog(null, vex.getMessage(), "Error de Validación (Empleados)", JOptionPane.WARNING_MESSAGE);
        } catch (RegistrarNuevoEmpleadoException opex) {
            JOptionPane.showMessageDialog(null, "Error en la operación de registro: " + opex.getMessage(), "Error de Registro (Empleados)", JOptionPane.ERROR_MESSAGE);
            if (opex.getCause() != null) {
                System.err.println("Causa original: " + opex.getCause().getMessage());
                // :)
            }
        } catch (Exception e) { // Captura genérica final
            JOptionPane.showMessageDialog(null, "Error inesperado al registrar empleado: " + e.getMessage(), "Error General (Empleados)", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return null; // Devuelve null si hubo error

    }

    @Override
    public EmpleadoDTO controlActualizarInformacionEmpleado(String empleadoId, EmpleadoDTO datosNuevosDTO) {
        try {
            return gestionEmpleados.actualizarInformacionEmpleado(empleadoId, datosNuevosDTO);
        } catch (ValidarEmpleadoException vex) {
            JOptionPane.showMessageDialog(null, vex.getMessage(), "Error de Validación (Empleados)", JOptionPane.WARNING_MESSAGE);
        } catch (ActualizacionEmpleadoException opex) {
            JOptionPane.showMessageDialog(null, "Error en la operación de actualización: " + opex.getMessage(), "Error de Actualización (Empleados)", JOptionPane.ERROR_MESSAGE);
            if (opex.getCause() != null) {
                System.err.println("Causa original: " + opex.getCause().getMessage());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error inesperado al actualizar empleado: " + e.getMessage(), "Error General (Empleados)", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean controlDespedirEmpleado(String empleadoIdString) {
        try {
            return gestionEmpleados.despedirEmpleado(empleadoIdString);
        } catch (ValidacionEmpleadoIdException vex) {
            JOptionPane.showMessageDialog(null, vex.getMessage(), "Error de Validación (Empleados)", JOptionPane.WARNING_MESSAGE);
        } catch (DespedirEmpleadoException opex) {
            JOptionPane.showMessageDialog(null, "Error en la operación de despido: " + opex.getMessage(), "Error de Despido (Empleados)", JOptionPane.ERROR_MESSAGE);
            if (opex.getCause() != null) {
                System.err.println("Causa original: " + opex.getCause().getMessage());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error inesperado al despedir empleado: " + e.getMessage(), "Error General (Empleados)", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return false; // Devuelve false si hubo error
    }

    @Override
    public EmpleadoDTO consultarEmpleadoActivoPorId(String empleadoIdString) {
        try {
            return gestionEmpleados.buscarEmpleadoActivoPorId(empleadoIdString);
        } catch (ValidacionEmpleadoIdException vex) {
            JOptionPane.showMessageDialog(null, vex.getMessage(), "Error de Validación (Empleados)", JOptionPane.WARNING_MESSAGE);
        } catch (ObtenerEmpleadoException opex) {
            JOptionPane.showMessageDialog(null, "Error al buscar empleado: " + opex.getMessage(), "Error de Búsqueda (Empleados)", JOptionPane.ERROR_MESSAGE);
            if (opex.getCause() != null) {
                System.err.println("Causa original: " + opex.getCause().getMessage());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error inesperado al buscar empleado: " + e.getMessage(), "Error General (Empleados)", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<EmpleadoDTO> consultarTodosLosEmpleadosActivos() {
        try {
            return gestionEmpleados.obtenerTodosLosEmpleadosActivos();
        } catch (ObtenerEmpleadoException opex) {
            JOptionPane.showMessageDialog(null, "Error al obtener lista de empleados: " + opex.getMessage(), "Error de Consulta (Empleados)", JOptionPane.ERROR_MESSAGE);
            if (opex.getCause() != null) {
                System.err.println("Causa original: " + opex.getCause().getMessage());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error inesperado al obtener empleados: " + e.getMessage(), "Error General (Empleados)", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return new ArrayList<>(); // Devuelve lista vacía en caso de error
    }

    @Override
    public List<EmpleadoDTO> consultarEmpleadosActivosPorCargo(Cargo cargo) {
        try {
            return gestionEmpleados.obtenerEmpleadosActivosPorCargo(cargo);
        } catch (ObtenerEmpleadoPorCargoException opex) {
            JOptionPane.showMessageDialog(null, "Error al obtener empleados por cargo: " + opex.getMessage(), "Error de Consulta (Empleados)", JOptionPane.ERROR_MESSAGE);
            if (opex.getCause() != null) {
                System.err.println("Causa original: " + opex.getCause().getMessage());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error inesperado al obtener empleados por cargo: " + e.getMessage(), "Error General (Empleados)", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public boolean controlActualizarCargoEmpleado(String empleadoIdString, Cargo nuevoCargo) {
        try {
            return gestionEmpleados.actualizarCargoEmpleado(empleadoIdString, nuevoCargo);
        } catch (ValidarEmpleadoException vex) {
            JOptionPane.showMessageDialog(null, vex.getMessage(), "Error de Validación (Empleados)", JOptionPane.WARNING_MESSAGE);
        } catch (ActualizacionDeCargoException opex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar cargo: " + opex.getMessage(), "Error de Actualización (Empleados)", JOptionPane.ERROR_MESSAGE);
            if (opex.getCause() != null) {
                System.err.println("Causa original: " + opex.getCause().getMessage());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error inesperado al actualizar cargo: " + e.getMessage(), "Error General (Empleados)", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean controlActualizarSueldoEmpleadoIndividual(String empleadoIdString, double nuevoSueldo) {
        try {
            return gestionEmpleados.actualizarSueldoEmpleadoIndividual(empleadoIdString, nuevoSueldo);
        } catch (ValidarEmpleadoException vex) {
            JOptionPane.showMessageDialog(null, vex.getMessage(), "Error de Validación (Empleados)", JOptionPane.WARNING_MESSAGE);
        } catch (ActualizacionSueldoException opex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar sueldo: " + opex.getMessage(), "Error de Actualización (Empleados)", JOptionPane.ERROR_MESSAGE);
            if (opex.getCause() != null) {
                System.err.println("Causa original: " + opex.getCause().getMessage());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error inesperado al actualizar sueldo: " + e.getMessage(), "Error General (Empleados)", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public long controlActualizarSueldoGeneralPorCargo(Cargo cargo, double nuevoSueldo) {
        try {
            return gestionEmpleados.actualizarSueldoGeneralPorCargo(cargo, nuevoSueldo);
        } catch (validarActualizacionSueldoDeCargoException vex) {
            JOptionPane.showMessageDialog(null, vex.getMessage(), titulo, JOptionPane.WARNING_MESSAGE);
        }
        return -1; // Devuelve un valor que indique error, ya que el original devuelve long
    }

}
