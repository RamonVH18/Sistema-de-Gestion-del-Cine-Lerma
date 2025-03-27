/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestionPagos;

import DTOs.CuentaMercadoDTO;
import DTOs.PagoDTO;
import DTOs.PaypalDTO;
import DTOs.TarjetaDTO;
import Excepciones.PagoException;
import Excepciones.ValidarCuentaException;
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
            CuentaMercadoDTO cuenta1 = new CuentaMercadoDTO("Jaime Lerma", "lerma@gmail.com", 101, 300.2);
            CuentaMercadoDTO cuenta2 = new CuentaMercadoDTO("Joseson master", "Giga@gmail.com", 696, 170.5);
            mercados.add(cuenta1);
            mercados.add(cuenta2);
        }
        return mercados;
    }

    @Override
    public final List<PaypalDTO> agregarCuentasPaypal() {
        if (paypals.isEmpty()) {

            PaypalDTO cuenta1 = new PaypalDTO("sonic15622@gmail.com", "camello", 100.0);
            PaypalDTO cuenta2 = new PaypalDTO("abrahama@gmail.com", "perrito", 200.6);
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

            TarjetaDTO cuenta1 = new TarjetaDTO("987654321123456", "ramoncito", 789, fechaVencimiento1, 421.9); // 05/26
            TarjetaDTO cuenta2 = new TarjetaDTO("123456789675321", "jaimico", 123, fechaVencimiento2, 20.4); // 02/29
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
    public boolean procesarPagoPaypal(PaypalDTO paypal, PagoDTO pago) throws PagoException{
        boolean pagoExitoso = false;
        Date fechaHoy = new Date();

        if (pago.getMonto() == null || pago.getMonto() <= 0) {
            throw new PagoException("Error: El monto debe ser mayor a 0");
        }
        
        if (!pago.getFechaHora().equals(fechaHoy)) {
            throw new PagoException("Error: La fecha del pago debe ser la del dia de hoy");
        }

        System.out.println("Procesando pago de " + pago.getMonto() + "con Paypal");
        System.out.println("Correo: " + paypal.getCorreo());
        
        if (pago.getMonto() > paypal.getSaldo()) {
            throw new PagoException("Error: El saldo de la cuenta de mercadoPago es insuficiente");
        }else {
            pagoExitoso = true;
        }

        if (pagoExitoso) {
            pago.setEstado("EXITOSO");
            System.out.println("Pago realizando con exito, " + pago.getEstado());
        } else {
            pago.setEstado("FALLIDO");
            System.out.println("Pago no realizado, " + pago.getEstado());
        }
        return pagoExitoso;

    }

    @Override
    public boolean procesarPagoMercado(CuentaMercadoDTO mercadoPago, PagoDTO pago) throws PagoException{
        boolean pagoExitoso = false;
        Date fechaHoy = new Date();

        if (pago.getMonto() == null || pago.getMonto() <= 0) {
            throw new PagoException("Error: El monto debe ser mayor a 0");
        }

        if (!pago.getFechaHora().equals(fechaHoy)) {
            throw new PagoException("Error: La fecha del pago debe ser la del dia de hoy");
        }

        System.out.println("Procesando pago de " + pago.getMonto() + "con MercadoPago");
        System.out.println("Correo: " + mercadoPago.getCorreo());
        
        if (pago.getMonto() > mercadoPago.getSaldo()) {
            throw new PagoException("Error: El saldo de la cuenta de mercadoPago es insuficiente");
        }else {
            pagoExitoso = true;
        }

        if (pagoExitoso) {
            pago.setEstado("EXITOSO");
            System.out.println("Pago realizando con exito, " + pago.getEstado());
        } else {
            pago.setEstado("FALLIDO");
            System.out.println("Pago no realizado, " + pago.getEstado());
        }
        return pagoExitoso;

    }

    @Override
    public boolean procesarPagoTarjeta(TarjetaDTO tarjeta, PagoDTO pago) throws PagoException{
        boolean pagoExitoso = false;
        Date fechaHoy = new Date();

        if (pago.getMonto() == null || pago.getMonto() <= 0) {
            throw new PagoException("Error: El monto debe ser mayor a 0");
        }

        if (!pago.getFechaHora().equals(fechaHoy)) {
            throw new PagoException("Error: La fecha del pago debe ser la del dia de hoy");
        }

        System.out.println("Procesando pago de: $" + pago.getMonto() + "con tarjeta");
        System.out.println("Titular: " + tarjeta.getTitular());
        
        if (pago.getMonto() > tarjeta.getSaldo()) {
            throw new PagoException("Error: El saldo de la cuenta de mercadoPago es insuficiente");
        }else {
            pagoExitoso = true;
        }


        if (pagoExitoso) {
            pago.setEstado("EXITOSO");
            System.out.println("Pago realizando con exito, " + pago.getEstado());
        } else {
            pago.setEstado("FALLIDO");
            System.out.println("Pago no realizado, " + pago.getEstado());
        }
        
        return pagoExitoso;
    }
    
    //Metodo para validar una cuenta de paypal, este metodo recibe un PaypalDTO y lo que hace es buscar este dto dentro de la lista de cuentas existentes, en caso de encontrarla entonces se definira como una cuenta real, una vez se verifica que si existe se hacen algunas validaciones para la cuenta
    //Si se pasan todas las validaciones entonces se regresa un valor true para indicar que la cuenta es valida
    @Override
    public PaypalDTO validarCuentaPaypal(PaypalDTO paypal) throws ValidarCuentaException {
        Optional<PaypalDTO> cuentaPaypalEncontrada = paypals.stream().filter(p -> p.equals(paypal)).findFirst();
        if (!cuentaPaypalEncontrada.isPresent()) {
            return null;
        }
        PaypalDTO cuentaPaypal = cuentaPaypalEncontrada.get();

        if (cuentaPaypal == null) {
            throw new ValidarCuentaException("Error: La cuenta no existe");
        }

        if (cuentaPaypal.getCorreo() == null || cuentaPaypal.getCorreo().isEmpty() || cuentaPaypal.getCorreo().isBlank()) {
            throw new ValidarCuentaException("Error: La cuenta no tiene correo");
        }

        if (cuentaPaypal.getContrasenia() == null || cuentaPaypal.getContrasenia().isEmpty() || cuentaPaypal.getContrasenia().isBlank()) {
            throw new ValidarCuentaException("Error: La cuenta no tiene contrasenia");
        }

        return cuentaPaypal;
    }
    
    //Metodo para validar una cuenta de mercadoPago, este metodo recibe un CuentaMercadoDTO y lo que hace es buscar este dto dentro de la lista de cuentas existentes, en caso de encontrarla entonces se definira como una cuenta real, una vez se verifica que si existe se hacen algunas validaciones para la cuenta
    //Si se pasan todas las validaciones entonces se regresa un valor true para indicar que la cuenta es valida
    @Override
    public CuentaMercadoDTO validarMercado(CuentaMercadoDTO mercadoPago) throws ValidarCuentaException {
        Optional<CuentaMercadoDTO> cuentaMercadoEncontrada = mercados.stream().filter(p -> p.equals(mercadoPago)).findFirst();
        if (!cuentaMercadoEncontrada.isPresent()) {
            return null;
        }
        CuentaMercadoDTO cuentaMercado = cuentaMercadoEncontrada.get();

        if (cuentaMercado == null) {
            throw new ValidarCuentaException("Error: La cuenta no existe");
        }
        if (cuentaMercado.getTitular() == null || cuentaMercado.getTitular().isEmpty() || cuentaMercado.getTitular().isBlank()) {
            throw new ValidarCuentaException("Error: La cuenta no tiene un titular");
        }

        if (cuentaMercado.getClienteID() == null) {
            throw new ValidarCuentaException("Error: La cuenta no tiene un ID");
        }

        if (cuentaMercado.getCorreo() == null || cuentaMercado.getCorreo().isEmpty()) {
            throw new ValidarCuentaException("Error: La cuenta no tiene un correo");
        }

        return cuentaMercado;

    }
    
    //Metodo para validar una tarjeta, este metodo recibe un TarjetaDTO y lo que hace es buscar este dto dentro de la lista de cuentas existentes, en caso de encontrarla entonces se definira como una cuenta real, una vez se verifica que si existe se hacen algunas validaciones para la cuenta
    //Si se pasan todas las validaciones entonces se regresa un valor true para indicar que la cuenta es valida
    @Override
    public TarjetaDTO validarTarjeta(TarjetaDTO tarjeta) throws ValidarCuentaException {
        Optional<TarjetaDTO> tarjetaEncontrada = tarjetas.stream().filter(p -> p.equals(tarjeta)).findFirst();
        if (!tarjetaEncontrada.isPresent()) {
            return null;
        }
        TarjetaDTO cuentaTarjeta= tarjetaEncontrada.get();

        if (cuentaTarjeta == null) {
            throw new ValidarCuentaException("Error: La cuenta no existe");
        }

        if (cuentaTarjeta.getTitular() == null || cuentaTarjeta.getNumeroTarjeta() == null || cuentaTarjeta.getFechaVencimiento() == null || cuentaTarjeta.getCvv() == null) {
            throw new ValidarCuentaException("Error: La tarjeta no tiene numero de tarjeta");
        }

        if (cuentaTarjeta.getTitular().isBlank() || cuentaTarjeta.getNumeroTarjeta().isBlank()) {
            throw new ValidarCuentaException("Error: La cuenta no existe");
        }

        if (cuentaTarjeta.getFechaVencimiento().before(new Date())) {
            throw new ValidarCuentaException("Error: La fecha de vencimiento no puede ser antes de la fecha actual del sistema");
        }
        
        //Revisa si el cvv de la tarjeta es de 3 digitos
        String cvv = String.valueOf(cuentaTarjeta.getCvv());
        if (cvv.length() < 3 || cvv.length() > 4) {
            throw new ValidarCuentaException("Error: El cvv de la tarjeta tiene menos de 3 digitos o mas de 4");
        }

        if (!tarjeta.getNumeroTarjeta().matches("\\d+")) {
            throw new ValidarCuentaException("Error: El numero de la tarjeta no es valido");
        }

        int longitudNumeroTarjeta = tarjeta.getNumeroTarjeta().length();
        if (longitudNumeroTarjeta < 15 || longitudNumeroTarjeta > 16) {
            throw new ValidarCuentaException("Error: La longitud del numero de la tarjeta no es correcta");
        }

        return cuentaTarjeta;
    }

    @Override
    public boolean consultarEstadoPago(PagoDTO pago) throws PagoException{
        if (pago == null || pago.getEstado() == null) {
            return false;
        }

        String estado = pago.getEstado().toUpperCase();

        return estado.equals("EXITOSO");
    }

    @Override
    public void actualizarSaldoMercado(CuentaMercadoDTO mercadoPago, PagoDTO pago) {
        Optional<CuentaMercadoDTO> cuentaMercadoEncontrada = mercados.stream().filter(p -> p.equals(mercadoPago)).findFirst();
        CuentaMercadoDTO cuentaMercado = cuentaMercadoEncontrada.get();
        cuentaMercado.setSaldo(cuentaMercado.getSaldo() - pago.getMonto());
        
    }

    @Override
    public void actualizarSaldoPaypal(PaypalDTO paypal, PagoDTO pago) {
        Optional<PaypalDTO> cuentaPaypalEncontrada = paypals.stream().filter(p -> p.equals(paypal)).findFirst();
        PaypalDTO cuentaPaypal = cuentaPaypalEncontrada.get();
        cuentaPaypal.setSaldo(cuentaPaypal.getSaldo() - pago.getMonto());
           
    }

    @Override
    public void actualizarSaldoTarjeta(TarjetaDTO tarjeta, PagoDTO pago) {
        Optional<TarjetaDTO> tarjetaEncontrada = tarjetas.stream().filter(p -> p.equals(tarjeta)).findFirst();
        TarjetaDTO cuentaTarjeta= tarjetaEncontrada.get();
        cuentaTarjeta.setSaldo(cuentaTarjeta.getSaldo() - pago.getMonto());
    }

}
