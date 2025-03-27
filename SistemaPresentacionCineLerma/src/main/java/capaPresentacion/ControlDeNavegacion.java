/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capaPresentacion;

import DTOs.BoletoDTO;
import DTOs.ClienteDTO;
import DTOs.CuentaMercadoDTO;
import DTOs.FuncionDTO;
import DTOs.MetodoPagoDTO;
import DTOs.PaypalDTO;
import DTOs.PeliculaDTO;
import DTOs.TarjetaDTO;
import Excepciones.FuncionCargaException;
import Excepciones.GestionReservaException;
import Excepciones.PeliculasCargaException;
import Excepciones.ValidarCuentaException;
import com.google.zxing.WriterException;
import gestionPagos.GestionPagos;
import gestionPagos.IGestionPagos;
import gestionReservaBoletos.IManejoDeBoletos;
import gestionReservaBoletos.ManejoDeBoletos;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Ramon Valencia
 */
public class ControlDeNavegacion {

    //Instancias y clases para llamar metodos
    private IManejoDeBoletos manejoDeBoletos = ManejoDeBoletos.getInstancia();
    private IGestionPagos gestionDePagos = GestionPagos.getInstancia();

    //Variables que se usan para guardar la pelicula Selecciona y la funcion seleccionada
    private PeliculaDTO peliculaSeleccionada = new PeliculaDTO();
    private FuncionDTO funcionSeleccionada = new FuncionDTO();

    private String titulo = "¡ERROR!";

    private List<String> asientos = new ArrayList<>();

    private int numAsientos;

    private ClienteDTO cliente = new ClienteDTO("Jaime Flores Valenzuela", "jaime@lerma.com.mx");

    private static ControlDeNavegacion instancia;
    private JFrame instanciaJFrame;
    private JDialog instanciaJDialog;

    private ControlDeNavegacion() {
        // Constructor privado para evitar múltiples instancias 
    }

    //Metodo para obtener la instancia de la clase y que no se reinicie cada vez que se use un metodo.
    public static ControlDeNavegacion getInstancia() {
        if (instancia == null) {
            instancia = new ControlDeNavegacion();
        }
        return instancia;
    }

    //METODOS PARA ABRIR PANTALLAS
    //Este metodo sirve para regresar al menu Principal, se encontrara la forma de de fusionar
    public void mostrarMenuPrincipal() {
        SwingUtilities.invokeLater(() -> {
            MenuPrincipal pantalla = new MenuPrincipal();
            pantalla.setLocationRelativeTo(null);
            pantalla.setVisible(true);
        });
    }

    //Metodo que se encarga de abrir la pantalla de SeleccionarPelicula
    public void mostrarSeleccionarPelicula() {
        SwingUtilities.invokeLater(() -> {
            SeleccionarPelicula pantallaSeleccionarPelicula = new SeleccionarPelicula();
            pantallaSeleccionarPelicula.setLocationRelativeTo(null);
            pantallaSeleccionarPelicula.setVisible(true);

        });
    }

    //Metodo que se encarga de abrir la pantalla de SeleccionarAsientos
    public void mostrarSeleccionarAsientos() {
        SwingUtilities.invokeLater(() -> {
            SeleccionarAsientos pantallaSeleccionarAsientos = new SeleccionarAsientos();
            pantallaSeleccionarAsientos.setLocationRelativeTo(null);
            pantallaSeleccionarAsientos.setVisible(true);
        });
    }

    //Metodo que se encarga de abrir la pantalla de SeleccionarMetodoPago
    public void mostrarSeleccionarMetodoPago() {
        SwingUtilities.invokeLater(() -> {
            try {
                SeleccionarMetodoPago pantallaSeleccionarMetodoPago = new SeleccionarMetodoPago();
                pantallaSeleccionarMetodoPago.setLocationRelativeTo(null);
                pantallaSeleccionarMetodoPago.setVisible(true);
            } catch (GestionReservaException ex) {
                Logger.getLogger(ControlDeNavegacion.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    //Metodo que se encarga de abrir el JDialog para el pago con tarjeta
    public void mostrarPagoTarjeta() {
        SwingUtilities.invokeLater(() -> {
            PantallaPagoTarjeta pantallaPagoTarjeta = new PantallaPagoTarjeta();
            pantallaPagoTarjeta.setLocationRelativeTo(null);
            pantallaPagoTarjeta.setVisible(true);
        });
    }

    //Metodo que se encarga de abrir el JDialog para el pago con Paypal
    public void mostrarPagoPaypal() {
        SwingUtilities.invokeLater(() -> {
            PantallaPagoPaypal pantallaPagoPaypal = new PantallaPagoPaypal();
            pantallaPagoPaypal.setLocationRelativeTo(null);
            pantallaPagoPaypal.setVisible(true);
        });
    }

    //Metodo que se encarga de abrir el JDialog para el pago con Mercado pago
    public void mostrarPagoMercado() {
        SwingUtilities.invokeLater(() -> {
            PantallaPagoMercado pantallaPagoMercado = new PantallaPagoMercado(numAsientos);
            pantallaPagoMercado.setLocationRelativeTo(null);
            pantallaPagoMercado.setVisible(true);
        });
    }

    //Metodo que se encarga de abrir la pantalla de DetalleBoleto
    public void mostrarDetalleBoleto() {
        SwingUtilities.invokeLater(() -> {
            try {
                DetalleDelBoleto pantallaDetalleDelBoleto = new DetalleDelBoleto();
                pantallaDetalleDelBoleto.setLocationRelativeTo(null);
                pantallaDetalleDelBoleto.setVisible(true);
            } catch (GestionReservaException ex) {
                Logger.getLogger(ControlDeNavegacion.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(ControlDeNavegacion.class.getName()).log(Level.SEVERE, null, ex);
            } catch (WriterException ex) {
                Logger.getLogger(ControlDeNavegacion.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
    }

    //METODOS PARA GUARDAR Y OBTENER LOS ELEMENTOS NECESARIOS
    //PELICULA
    /**
     * Metodo que se encarga de obtener todas las peliculas que estan
     * disponibles y lo píde desde el subsistema
     */
    public List<PeliculaDTO> obtenerPeliculas(JPanel panel) {
        try {
            List<PeliculaDTO> peliculas = manejoDeBoletos.cargarPeliculasActivas();
            return peliculas;
        } catch (PeliculasCargaException e) {
            //PONER JOptionPane y cerrar la pantalla actual y volver al menuPrincipal
            JOptionPane.showMessageDialog(null, e.getMessage(), titulo, JOptionPane.ERROR_MESSAGE);
            mostrarMenuPrincipal();
            return null;
        }
    }

    /**
     * Metodo que se encarga de guardar la pelicula que fue seleccionada
     */
    public void guardarPeliculaSeleccionada(PeliculaDTO pelicula) {
        this.peliculaSeleccionada = pelicula;
    }

    /**
     * Metodo que se usa para cuando una clase Boundary necesite consultar la
     * pelicula que fue seleccionada
     *
     * @return
     */
    public PeliculaDTO consultarPelicula() {
        return peliculaSeleccionada;
    }

    //FUNCION
    /**
     * Metodo que se encarga de obtener todas las funciones que estan
     * disponibles y lo píde desde el subsistema
     *
     * @param nombrePelicula
     * @return
     * @throws GestionReservaException
     */
    public List<FuncionDTO> obtenerFunciones(String nombrePelicula) {
        try {
            List<FuncionDTO> funciones = manejoDeBoletos.cargarFuncionesPelicula(nombrePelicula);
            return funciones;
        } catch (FuncionCargaException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), titulo, JOptionPane.ERROR_MESSAGE);
            mostrarMenuPrincipal();
            return null;
        }
    }

    /**
     * Metodo que se encarga de guardar la funcion que fue seleccionada
     *
     * @param funcion
     */
    public void guardarFuncionSeleccionada(FuncionDTO funcion) {
        this.funcionSeleccionada = funcion;
    }

    /**
     * Metodo que se usa para cuando una clase Boundary necesite consultar la
     * funcion que fue seleccionada
     *
     * @return
     */
    public FuncionDTO consultarFuncion() {
        return funcionSeleccionada;
    }

    //ASIENTOS
    /**
     * Metodo que consigue todos los asientosDisponibles en una funcion
     *
     * @param funcion
     * @return
     * @throws GestionReservaException
     */
    public int obtenerAsientosDisponibles(FuncionDTO funcion) {
        try {
            int asientosDisponibles = manejoDeBoletos.consultarDisponibilidadAsientos(funcion);
            return asientosDisponibles;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), titulo, numAsientos);
            return 0;
        }
    }

    /**
     * Metodo que se encarga de guardar el numero de asientos
     *
     * @param numAsientos
     */
    public void guardarNumeroAsientos(int numAsientos) {
        this.numAsientos = numAsientos;
    }

    /**
     * Metodo para consultar el numero de asiento que se seleccionaron
     *
     * @return
     */
    public int consultarNumeroAsientos() {
        return numAsientos;
    }

    //VALIDACIONES
    /**
     * Metodo para validar los campos numeros de asientos
     *
     * @param texto
     * @param funcion
     * @throws GestionReservaException
     */
    public void validarCamposAsientos(String texto, FuncionDTO funcion) throws GestionReservaException {
        if (manejoDeBoletos.validarCampoAsiento(texto, funcion)) {
            String textoValidado = texto.trim();
            int numAsientos = Integer.parseInt(textoValidado);
            manejoDeBoletos.validarDisponibilidaDeAsientos(numAsientos, funcion);
        }
    }

    /**
     * Metodo para obtener los metodos de pago que estan guardados en el sistema
     *
     * @return
     * @throws GestionReservaException
     */
    public List<MetodoPagoDTO> obtenerMetodosPago() throws GestionReservaException {
        List<MetodoPagoDTO> metodosPago = manejoDeBoletos.cargarMetodosPago();
        return metodosPago;
    }

    /**
     * Metodo para obtener una lista con todo los asientos reservados
     *
     * @param funcion
     * @param numAsientos
     * @return
     * @throws GestionReservaException
     */
    private List<String> obtenerListaAsientosReservados(FuncionDTO funcion, int numAsientos) throws GestionReservaException {
        List<String> asientosReservados = manejoDeBoletos.reservarAsientoFuncion(funcion, numAsientos, cliente);
        this.asientos = asientosReservados;
        return asientosReservados;
    }

    public BoletoDTO cargarBoleto() throws GestionReservaException {
        List<String> asientosReservados = obtenerListaAsientosReservados(funcionSeleccionada, numAsientos);
        return manejoDeBoletos.generarBoleto(peliculaSeleccionada, funcionSeleccionada, asientosReservados, cliente);
    }

    //Metodos de gestion de pagos
    public boolean verificarCuentaMercado(CuentaMercadoDTO cuentaMercado) throws ValidarCuentaException  {
        boolean esValida = gestionDePagos.validarMercado(cuentaMercado);
        return esValida;
    }

    public boolean verificarCuentaPaypal(PaypalDTO cuentaPaypal) throws ValidarCuentaException {
        boolean esValida = gestionDePagos.validarCuentaPaypal(cuentaPaypal);
        return esValida;
    }

    public boolean verificarCuentaTarjeta(TarjetaDTO cuentaTarjeta) throws ValidarCuentaException {
        boolean esValida = gestionDePagos.validarTarjeta(cuentaTarjeta);
        return esValida;
    }

}
