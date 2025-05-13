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
import DTOs.TarjetaDTO;
import DTOs.UsuarioDTO;
import Excepciones.ActualizarUsuarioException;
import Excepciones.CalcularCostoTotalException;
import Excepciones.CargarHistorialException;
import Excepciones.DisponibilidadAsientosException;
import Excepciones.EncontrarUsuarioException;
import Excepciones.FuncionCargaException;
import Excepciones.GestionReservaException;
import Excepciones.PagoException;
import Excepciones.PeliculasCargaException;
import Excepciones.ValidarCuentaException;
import Excepciones.GenerarBoletoException;
import Excepciones.PresentacionException;
import Excepciones.RegistrarUsuarioException;
import Excepciones.ReservarAsientoFuncionException;
import Excepciones.ValidarCampoAsientoException;
import Excepciones.ValidarUsuarioException;
import Excepciones.usuarios.EliminarUsuarioException;
import Excepciones.usuarios.ObtenerUsuariosException;
import enums.EstadoUsuario;
import gestionPagos.GestionPagos;
import gestionPagos.IGestionPagos;
import gestionReservaBoletos.IManejoDeBoletos;
import gestionReservaBoletos.ManejoDeBoletos;
import gestionUsuarios.IManejoUsuarios;
import gestionUsuarios.ManejoUsuarios;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import pantallas.IniciarSesion;
import pantallas.MenuPrincipalAdmin;
import pantallas.reservaBoletos.DetalleDelBoleto;
import pantallas.MenuPrincipalCliente;
import pantallas.Pagos.PantallaPago;
import pantallas.Pagos.PantallaPagoRechazado;
import pantallas.Salas.AgregarSala;
import pantallas.Salas.EstadisticasSala;
import pantallas.Salas.MenuSalas;
import pantallas.Usuarios.AdministracionDeUsuario;
import pantallas.Usuarios.EditarUsuario;
import pantallas.Usuarios.HistorialCliente;
import pantallas.Usuarios.RegistrarUsuario;
import pantallas.reservaBoletos.SeleccionarAsientos;
import pantallas.reservaBoletos.SeleccionarMetodoPago;

/**
 *
 * @author Ramon Valencia
 */
public class ControlDeNavegacion implements IControl {

    //Instancias y clases para llamar metodos
    private final IManejoDeBoletos manejoDeBoletos = ManejoDeBoletos.getInstancia();
    private final IGestionPagos gestionDePagos = GestionPagos.getInstancia();
    private final IManejoUsuarios gestionUsuarios = ManejoUsuarios.getInstance();

    //Variables que se usan para guardar la pelicula Selecciona y la funcion seleccionada
    private PeliculaDTO peliculaSeleccionada;
    private FuncionDTO funcionSeleccionada;

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

    /**
     * Metodo para abrir el menu del caso de uso de gestion de salas
     *
     * @param tituloFrame
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
     * @param tituloFrame
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

    /**
     * Metodo para abrir la pantalla de estadisticas de una las sala
     *
     * @param tituloFrame
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
    public void mostrarAdministracionDeUsuario(JFrame frameAnterior) {
        SwingUtilities.invokeLater(() -> {
            AdministracionDeUsuario pantallaAdministrarUsuario = new AdministracionDeUsuario();
            pantallaAdministrarUsuario.setLocationRelativeTo(null);
            pantallaAdministrarUsuario.setVisible(true);
            frameAnterior.dispose();
        });
    }

    @Override
    public void mostrarEditarUsuario(JFrame frameAnterior) {
        SwingUtilities.invokeLater(() -> {
            EditarUsuario pantallaEditarUsuario = new EditarUsuario();
            pantallaEditarUsuario.setLocationRelativeTo(null);
            pantallaEditarUsuario.setVisible(true);
            frameAnterior.dispose();
        });
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
    public void mostrarHistorialCliente(JFrame frameAnterior) {
        SwingUtilities.invokeLater(() -> {
            HistorialCliente pantallaHistorialCliente = new HistorialCliente();
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
            return gestionUsuarios.bloquearUsuario(usuario);
        } catch (ActualizarUsuarioException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), titulo, JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    @Override
    public List<UsuarioDTO> mostrarListaUsuariosPorEstado(EstadoUsuario estado) {
        try {
            return gestionUsuarios.mostrarListaUsuariosPorEstado(estado);
        } catch (ObtenerUsuariosException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), titulo, JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    @Override
    public List<UsuarioDTO> mostrarListaUsuariosPorPeriodo(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        try {
            return gestionUsuarios.mostrarListaUsuariosPorPeriodo(fechaInicio, fechaFin);
        } catch (ObtenerUsuariosException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), titulo, JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    @Override
    public List<UsuarioDTO> mostrarListaUsuariosPorCorreo(String correo) {
        try {
            return gestionUsuarios.mostrarListaUsuariosPorCorreo(correo);
        } catch (ObtenerUsuariosException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), titulo, JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    @Override
    public List<UsuarioDTO> mostrarListaUsuariosPorNombre(String nombre) {
        try {
            return gestionUsuarios.mostrarListaUsuariosPorNombre(nombre);
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
    public Boolean eliminarCliente(ClienteDTO cliente) {
        try {
            return gestionUsuarios.eliminarCliente(cliente);
        } catch (EliminarUsuarioException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), titulo, JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

//    @Override
//    public Boolean validarCliente(String nombreUsuario, String contrasena) throws ValidarUsuarioException{
//            return gestionUsuarios.validarCliente(nombreUsuario, contrasena);
//    }

    @Override
    public ClienteDTO obtenerCliente(String nombreUsuario, String contrasena) throws EncontrarUsuarioException {
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
    public AdministradorDTO actualizarAdministrador(AdministradorDTO administrador) {
        try {
            return gestionUsuarios.actualizarAdministrador(administrador);
        } catch (ActualizarUsuarioException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), titulo, JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    @Override
    public Boolean eliminarAdministrador(AdministradorDTO administrador) {
        try {
            return gestionUsuarios.eliminarAdministrador(administrador);
        } catch (EliminarUsuarioException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), titulo, JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

//    @Override
//    public Boolean validarAdministrador(String nombreUsuario, String contrasena) throws ValidarUsuarioException{
//            return gestionUsuarios.validarAdministrador(nombreUsuario, contrasena);
//    }

    @Override
    public AdministradorDTO obtenerAdministrador(String nombreUsuario, String contrasena) {
        try {
            return gestionUsuarios.obtenerAdministrador(nombreUsuario, contrasena);
        } catch (EncontrarUsuarioException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), titulo, JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

}
