/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestionPagos;

import DTOs.CuentaMercadoDTO;
import DTOs.PagoDTO;
import DTOs.PaypalDTO;
import DTOs.TarjetaDTO;
import Excepciones.TransferenciaException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Abraham Coronel Bringas
 */
public class GestionPagos implements IGestionPagos {

    private static GestionPagos instancia;

    //Listas de cuentas
    private List<CuentaMercadoDTO> mercados = new ArrayList<>();
    private List<PaypalDTO> paypals = new ArrayList<>();
    private List<TarjetaDTO> tarjetas = new ArrayList<>();

    public GestionPagos() {
        mercados = agregarCuentasMercado();
        tarjetas = agregarCuentasTarjeta();
        paypals = agregarCuentasPaypal();
    }

    //METODOS PARA LLENAR LAS LISTAS DE CUENTAS
    @Override
    public final List<CuentaMercadoDTO> agregarCuentasMercado() {
        if (mercados.isEmpty()) {
            CuentaMercadoDTO cuenta1 = new CuentaMercadoDTO("Jaime Lerma", "lerma@gmail.com", 101);
            CuentaMercadoDTO cuenta2 = new CuentaMercadoDTO("Joseson master", "Giga@gmail.com", 696);
            mercados.add(cuenta1);
            mercados.add(cuenta2);
        }
        return mercados;
    }

    @Override
    public final List<PaypalDTO> agregarCuentasPaypal() {
        if (paypals.isEmpty()) {

            PaypalDTO cuenta1 = new PaypalDTO("sonic15622@gmail.com", "camello");
            PaypalDTO cuenta2 = new PaypalDTO("abrahama@gmail.com", "perrito");
            paypals.add(cuenta1);
            paypals.add(cuenta2);

        }
        return paypals;
    }

    @Override
    public final List<TarjetaDTO> agregarCuentasTarjeta() {
        if (tarjetas.isEmpty()) {

            TarjetaDTO cuenta1 = new TarjetaDTO("987654321123456", "ramoncito", 789, new Date());
            TarjetaDTO cuenta2 = new TarjetaDTO("123456789675321", "jaimico", 123, new Date());
            tarjetas.add(cuenta1);
            tarjetas.add(cuenta2);

        }
        return tarjetas;
    }

    //GETINSTANCE
    public static GestionPagos getInstancia() {
        if (instancia == null) {
            instancia = new GestionPagos();
        }
        return instancia;
    }

    //METODOS DE VALIDACIONES
    @Override
    public void procesarPagoPaypal(PaypalDTO paypal, PagoDTO pago) throws TransferenciaException {
//        if (!validarPaypal(paypal)) {
//            throw new TransferenciaException("Error: Los datos de la cuenta de paypal son incorrectos");
//        }

        if (pago.getMonto() == null || pago.getMonto() <= 0) {
            throw new TransferenciaException("Error: El monto debe ser mayor a 0");
        }

        System.out.println("Procesando pago de " + pago.getMonto() + "con Paypal");
        System.out.println("Correo: " + paypal.getCorreo());

        boolean pagoExitoso = true; //Para que sea exitoso

        if (pagoExitoso) {
            pago.setEstado("EXITOSO");
            System.out.println("Pago realizando con exito, " + pago.getEstado());
        } else {
            pago.setEstado("FALLIDO");
            System.out.println("Pago no realizado, " + pago.getEstado());
//            PantallaPagoRechazado pagoRechazado = new PantallaPagoRechazado(new JFrame(), true);
//            pagoRechazado.setVisible(true);
        }

    }

    @Override
    public void procesarPagoMercado(CuentaMercadoDTO mercadoPago, PagoDTO pago) throws TransferenciaException {
        Date fechaHoy = new Date();
        if (!validarMercado(mercadoPago)) {
            throw new TransferenciaException("Error: Los datos de la cuenta de MercadoPago son incorrectos");
        }

        if (pago.getMonto() == null || pago.getMonto() <= 0) {
            throw new TransferenciaException("Error: El monto debe ser mayor a 0");
        }

        if (!pago.getFechaHora().equals(fechaHoy)) {
            throw new TransferenciaException("Error: La fecha del pago debe ser la del dia de hoy");
        }

        System.out.println("Procesando pago de " + pago.getMonto() + "con MercadoPago");
        System.out.println("Correo: " + mercadoPago.getCorreo());

        boolean pagoExitoso = true; //Para que sea exitoso siempre, si se quiere probar que el pago falle este valor debera cambiarse a false

        if (pagoExitoso) {
            pago.setEstado("EXITOSO");
            System.out.println("Pago realizando con exito, " + pago.getEstado());
        } else {
            pago.setEstado("FALLIDO");
            System.out.println("Pago no realizado, " + pago.getEstado());
        }

    }

    @Override
    public void procesarPagoTarjeta(TarjetaDTO tarjeta, PagoDTO pago) throws TransferenciaException {
        Date fechaHoy = new Date();
        if (!validarTarjeta(tarjeta)) {
            throw new TransferenciaException("Error: Los datos de tarjeta son incorrectos");
        }

        if (pago.getMonto() == null || pago.getMonto() <= 0) {
            throw new TransferenciaException("Error: El monto debe ser mayor a 0");
        }

        if (!pago.getFechaHora().equals(fechaHoy)) {
            throw new TransferenciaException("Error: La fecha del pago debe ser la del dia de hoy");
        }

        System.out.println("Procesando pago de: $" + pago.getMonto() + "con tarjeta");
        System.out.println("Titular: " + tarjeta.getTitular());

        boolean pagoExitoso = true;

        if (pagoExitoso) {
            pago.setEstado("EXITOSO");
            System.out.println("Pago realizando con exito, " + pago.getEstado());
        } else {
            pago.setEstado("FALLIDO");
            System.out.println("Pago no realizado, " + pago.getEstado());
        }
    }

    @Override
    public void validarCamposPaypal(String correo, String contrasenia) throws TransferenciaException {
        try {
            
            if (correo == null || correo.isEmpty()) {
                throw new TransferenciaException("Favor de ingresar un correo en el espacio en blanco.");
            }
            
            if (contrasenia == null || contrasenia.isEmpty()) {
                throw new TransferenciaException("Favor de ingresar su contraseña en el espacio correspondiente.");
            }
            
            String formatoCorreo = "^[\\w.-]+@[a-zA-Z\\d,-]+\\.[a-zA-Z]{2,6}$";
            
            if (!correo.matches(formatoCorreo)) {
                throw new TransferenciaException("El correo que ingrese no se encuentra en un formato valido.");
            }
            
        } catch (Exception e) {
            throw new TransferenciaException("ERROR: " + e.getMessage());
        }
    }
    
    @Override
    public boolean validarCuentaPaypal(PaypalDTO paypal) throws TransferenciaException {
        try {
            List<PaypalDTO> cuentasPaypal = paypals;
            for (PaypalDTO paypalComparacion : cuentasPaypal) {
                if (paypal == paypalComparacion) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            throw new TransferenciaException("ERROR: " + e.getMessage());
        }
    }
    
    @Override
    public boolean validarMercado(CuentaMercadoDTO mercadoPago) {
        Optional<CuentaMercadoDTO> cuentaMercadoEncontrada = mercados.stream().filter(p -> p.equals(mercadoPago)).findFirst();
        if (!cuentaMercadoEncontrada.isPresent()) {
            return false;
        }
        CuentaMercadoDTO cuentaMercado = cuentaMercadoEncontrada.get();

        if (cuentaMercado == null) {
            return false;
        }
        if (cuentaMercado.getTitular() == null || cuentaMercado.getTitular().isEmpty() || cuentaMercado.getTitular().isBlank()) {
            return false;
        }

        if (cuentaMercado.getClienteID() == null) {
            return false;
        }

        if (cuentaMercado.getCorreo() == null || cuentaMercado.getCorreo().isEmpty()) {
            return false;
        }

        return true;

    }

    @Override
    public boolean validarTarjeta(TarjetaDTO tarjeta) {
        if (tarjeta == null) {
            return false;
        }

        if (tarjeta.getTitular() == null || tarjeta.getNumeroTarjeta() == null || tarjeta.getFechaVencimiento() == null || tarjeta.getCvv() == null) {
            return false;
        }

        if (tarjeta.getTitular().isBlank()) {
            return false;
        }

        if (tarjeta.getFechaVencimiento().before(new Date())) {
            return false;
        }

        String cvv = String.valueOf(tarjeta.getCvv());
        if (cvv.length() < 3 || cvv.length() > 4) {
            return false;
        }

        if (!tarjeta.getNumeroTarjeta().matches("\\d+")) {
            return false;
        }

        int longitudNumeroTarjeta = tarjeta.getNumeroTarjeta().length();
        if (longitudNumeroTarjeta < 13 || longitudNumeroTarjeta > 16) {
            return false;
        }

        return true;
    }

    @Override
    public boolean consultarEstadoPago(PagoDTO pago) {
        if (pago == null || pago.getEstado() == null) {
            return false;
        }

        String estado = pago.getEstado().toUpperCase();

        return estado.equals("EXITOSO");
    }
}
