/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import DTOs.AdministradorDTO;
import pantallas.reservaBoletos.SeleccionarPelicula;
import DTOs.BoletoDTO;
import DTOs.ClienteDTO;
import DTOs.CompraDTO;
import DTOs.CuentaMercadoDTO;
import DTOs.FuncionDTO;
import DTOs.MetodoPagoDTO;
import DTOs.PagoDTO;
import DTOs.PaypalDTO;
import DTOs.PeliculaDTO;
import DTOs.SalaNuevaDTO;
import DTOs.SalaViejaDTO;
import DTOs.TarjetaDTO;
import DTOs.UsuarioDTO;
import Excepciones.ActualizarUsuarioException;
import Excepciones.AgregarSalaException;
import Excepciones.BuscarSalaException;
import Excepciones.CalcularCostoTotalException;
import Excepciones.CargarHistorialException;
import Excepciones.DisponibilidadAsientosException;
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
import Excepciones.PresentacionException;
import Excepciones.RegistrarPeliculaException;
import Excepciones.RegistrarUsuarioException;
import Excepciones.ReservarAsientoFuncionException;
import Excepciones.ValidacionSalaException;
import Excepciones.ValidarCampoAsientoException;
import Excepciones.ValidarUsuarioException;
import Excepciones.usuarios.ObtenerUsuariosException;
import GestionFunciones.IManejoFunciones;
import GestionFunciones.ManejoFunciones;
import enums.EstadoSala;
import enums.EstadoUsuario;
import enums.Rol;
import gestionPagos.GestionPagos;
import gestionPagos.IGestionPagos;
import gestionPeliculas.IManejoPeliculas;
import gestionPeliculas.ManejoPeliculas;
import gestionReservaBoletos.IManejoDeBoletos;
import gestionReservaBoletos.ManejoDeBoletos;
import gestionSalasAsientos.IManejoDeSalas;
import gestionSalasAsientos.ManejoDeSalas;
import gestionUsuarios.IManejoUsuarios;
import gestionUsuarios.ManejoUsuarios;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import pantallas.Funciones.ConsultarFunciones;
import pantallas.Funciones.ProgramarFuncion;
import pantallas.IniciarSesion;
import pantallas.MenuPrincipalAdmin;
import pantallas.reservaBoletos.DetalleDelBoleto;
import pantallas.MenuPrincipalCliente;
import pantallas.Pagos.PantallaPago;
import pantallas.Pagos.PantallaPagoRechazado;
import pantallas.Salas.AgregarSala;
import pantallas.Salas.EstadisticasSala;
import pantallas.Salas.MenuSalas;
import pantallas.Salas.ModificarSala;
import pantallas.Salas.SeleccionarSala;
import pantallas.Usuarios.ConsultarUsuarios;
import pantallas.Usuarios.EditarUsuario;
import pantallas.Usuarios.HistorialCliente;
import pantallas.Usuarios.RegistrarUsuario;
import pantallas.administracionPeliculas.AgregarPelicula;
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

    //Variables que se usan para guardar la pelicula Selecciona y la funcion seleccionada
    private PeliculaDTO peliculaSeleccionada;
    private FuncionDTO funcionSeleccionada;
    private UsuarioDTO usuarioActual;

    private final String titulo = "¡ERROR!";

    private List<String> asientos;

    private int numAsientos;

    private final ClienteDTO cliente = new ClienteDTO();

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

    /**
     * Este metodo sirve para regresar al menu Principal, se encontrara la forma
     * de de fusionar
     */
    @Override
    public void mostrarMenuCliente(JFrame frameAnterior, ClienteDTO cliente) {
        SwingUtilities.invokeLater(() -> {
            MenuPrincipalCliente pantalla = new MenuPrincipalCliente(cliente);
            pantalla.setLocationRelativeTo(null);
            pantalla.setVisible(true);
        });
    }

    /**
     * Metodo que se encarga de abrir la pantalla de pago rechazado
     */
    @Override
    public void mostrarMenuAdministrador(JFrame frameAnterior, AdministradorDTO admin) {
        SwingUtilities.invokeLater(() -> {
            MenuPrincipalAdmin pantallaMenuAdmin = new MenuPrincipalAdmin(admin);
            pantallaMenuAdmin.setLocationRelativeTo(null);
            pantallaMenuAdmin.setVisible(true);
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
            mostrarMenuCliente(null, null);
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
            mostrarSeleccionarPelicula();
            return null;
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
    private List<String> obtenerListaAsientosReservados(FuncionDTO funcion, int numAsientos) {
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
    public BoletoDTO cargarBoleto() {
        List<String> asientosReservados = obtenerListaAsientosReservados(funcionSeleccionada, numAsientos);
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
    public List<SalaViejaDTO> consultarSalas(String filtro) {
        try {
            List<SalaViejaDTO> salas = manejoDeSalas.cargarSalas(filtro);
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
    public void mostrarProgramarFuncion() {
        SwingUtilities.invokeLater(() -> {
            ProgramarFuncion pantalla = new ProgramarFuncion();
            pantalla.setLocationRelativeTo(null);
            pantalla.setVisible(true);

        });
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
    /*
    --------------FIN DE METODOS DE ADMINISTRAR PELICULAS--------------
     */
}
