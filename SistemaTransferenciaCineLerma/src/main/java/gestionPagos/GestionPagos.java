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
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Abraham Coronel Bringas
 */
public class GestionPagos implements IGestionPagos {
    //Instancia de la clase
    private static GestionPagos instancia;

    //Se definen las listas de cuentas hardcodeadas
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
    
    //En esta parte las fechas de vencimiento de las tarjetas son objetos tipo DATE y en este caso estas deberan tener el formato (MM/AA) estrictamente, por lo que hay que formatearlas
    //pues el usuario ingresara la fecha de vencimiento en ese formato EJ: 01/26
    @Override
    public final List<TarjetaDTO> agregarCuentasTarjeta() {
        if (tarjetas.isEmpty()) {
            
            // Fecha de vencimiento: Mayo 2026 (MM/AAAA = 05/2026)
            // Primero tenemos que crear un objeto YearMonth, un formato que incluye solamente año y mes 
            YearMonth fechaVencimiento1s = YearMonth.of(2026, 5); // Año 2026, mes 5 (Mayo)

            // Convertir a Date 
            Date fechaVencimiento1 = Date.from(
                    fechaVencimiento1s.atEndOfMonth() // Obtiene el último día del mes (31 de mayo)
                            .atStartOfDay() // Hora 00:00
                            .atZone(ZoneId.systemDefault()) // Zona horaria del sistema
                            .toInstant());
            
            //Se repite lo mismo pero con una fecha de vencimiento nueva, esta es para la segunda tarjeta que se creara
            YearMonth fechaVencimiento2s = YearMonth.of(2029, 2); // Año 2029, mes 2 (Febrero)

            // Convertir a Date (se usa el último día del mes para la fecha completa)
            Date fechaVencimiento2 = Date.from(
                    fechaVencimiento2s.atEndOfMonth() // Obtiene el último día del mes (31 de mayo)
                            .atStartOfDay() // Hora 00:00
                            .atZone(ZoneId.systemDefault()) // Zona horaria del sistema
                            .toInstant());

            TarjetaDTO cuenta1 = new TarjetaDTO("987654321123456", "ramoncito", 789, fechaVencimiento1); // 05/26
            TarjetaDTO cuenta2 = new TarjetaDTO("123456789675321", "jaimico", 123, fechaVencimiento2); // 02/29
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
    
    //Metodo para validar una cuenta de paypal, este metodo recibe un PaypalDTO y lo que hace es buscar este dto dentro de la lista de cuentas existentes, en caso de encontrarla entonces se definira como una cuenta real, una vez se verifica que si existe se hacen algunas validaciones para la cuenta
    //Si se pasan todas las validaciones entonces se regresa un valor true para indicar que la cuenta es valida
    @Override
    public boolean validarCuentaPaypal(PaypalDTO paypal) {
        Optional<PaypalDTO> cuentaPaypalEncontrada = paypals.stream().filter(p -> p.equals(paypal)).findFirst();
        if (!cuentaPaypalEncontrada.isPresent()) {
            return false;
        }
        PaypalDTO cuentaPaypal = cuentaPaypalEncontrada.get();

        if (cuentaPaypal == null) {
            return false;
        }

        if (cuentaPaypal.getCorreo() == null || cuentaPaypal.getCorreo().isEmpty() || cuentaPaypal.getCorreo().isBlank()) {
            return false;
        }

        if (cuentaPaypal.getContrasenia() == null || cuentaPaypal.getContrasenia().isEmpty() || cuentaPaypal.getContrasenia().isBlank()) {
            return false;
        }

        return true;
    }
    
    //Metodo para validar una cuenta de mercadoPago, este metodo recibe un CuentaMercadoDTO y lo que hace es buscar este dto dentro de la lista de cuentas existentes, en caso de encontrarla entonces se definira como una cuenta real, una vez se verifica que si existe se hacen algunas validaciones para la cuenta
    //Si se pasan todas las validaciones entonces se regresa un valor true para indicar que la cuenta es valida
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
    
    //Metodo para validar una tarjeta, este metodo recibe un TarjetaDTO y lo que hace es buscar este dto dentro de la lista de cuentas existentes, en caso de encontrarla entonces se definira como una cuenta real, una vez se verifica que si existe se hacen algunas validaciones para la cuenta
    //Si se pasan todas las validaciones entonces se regresa un valor true para indicar que la cuenta es valida
    @Override
    public boolean validarTarjeta(TarjetaDTO tarjeta) {
        Optional<TarjetaDTO> tarjetaEncontrada = tarjetas.stream().filter(p -> p.equals(tarjeta)).findFirst();
        if (!tarjetaEncontrada.isPresent()) {
            return false;
        }

        if (tarjeta == null) {
            return false;
        }

        if (tarjeta.getTitular() == null || tarjeta.getNumeroTarjeta() == null || tarjeta.getFechaVencimiento() == null || tarjeta.getCvv() == null) {
            return false;
        }

        if (tarjeta.getTitular().isBlank() || tarjeta.getNumeroTarjeta().isBlank()) {
            return false;
        }

        if (tarjeta.getFechaVencimiento().before(new Date())) {
            return false;
        }
        
        //Revisa si el cvv de la tarjeta es de 3 digitos
        String cvv = String.valueOf(tarjeta.getCvv());
        if (cvv.length() < 3 || cvv.length() > 4) {
            return false;
        }

        if (!tarjeta.getNumeroTarjeta().matches("\\d+")) {
            return false;
        }

        int longitudNumeroTarjeta = tarjeta.getNumeroTarjeta().length();
        if (longitudNumeroTarjeta < 15 || longitudNumeroTarjeta > 16) {
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
