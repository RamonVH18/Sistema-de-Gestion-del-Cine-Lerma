/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestionPagos;

import DTOs.CuentaMercadoDTO;
import DTOs.PagoDTO;
import DTOs.PaypalDTO;
import DTOs.TarjetaDTO;
import EstrategiasPago.MercadoPagoEstrategia;
import EstrategiasPago.PaypalEstrategia;
import EstrategiasPago.TarjetaEstrategia;
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
/**
 * Clase que gestiona los pagos a traves de diferentes metodos (PayPal,
 * MercadoPago, Tarjeta). Implementa la interfaz IGestionPagos.
 */
public class GestionPagos implements IGestionPagos {

    //Instancia de la clase
    private static GestionPagos instancia;

    //Se definen las listas de cuentas hardcodeadas
    private List<CuentaMercadoDTO> mercados = new ArrayList<>();
    private List<PaypalDTO> paypals = new ArrayList<>();
    private List<TarjetaDTO> tarjetas = new ArrayList<>();

    /**
     * Constructor de la clase. Inicializa las listas de cuentas.
     */
    public GestionPagos() {
        mercados = agregarCuentasMercado();
        tarjetas = agregarCuentasTarjeta();
        paypals = agregarCuentasPaypal();
    }

    //METODOS PARA LLENAR LAS LISTAS DE CUENTAS
    /**
     * Agrega cuentas de Mercado a la lista si está vacía.
     *
     * @return la lista de cuentas de Mercado.
     */
    @Override
    /**
     * Agrega cuentas de PayPal a la lista si esta vacia.
     *
     * @return la lista de cuentas de PayPal.
     */
    public final List<CuentaMercadoDTO> agregarCuentasMercado() {
        if (mercados.isEmpty()) {
            CuentaMercadoDTO cuenta1 = new CuentaMercadoDTO("Jaime Lerma", "lerma@gmail.com", 101, 300.2);
            CuentaMercadoDTO cuenta2 = new CuentaMercadoDTO("Joseson master", "Giga@gmail.com", 696, 170.5);
            mercados.add(cuenta1);
            mercados.add(cuenta2);
        }
        return mercados;
    }

    /**
     * Agrega cuentas de tarjeta a la lista si esta vacia.
     *
     * @return la lista de cuentas de tarjeta.
     */
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
    /**
     * Agrega cuentas de tarjeta a la lista si esta vacia.
     *
     * @return la lista de cuentas de tarjeta.
     */
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
    /**
     * Obtiene la instancia unica de la clase GestionPagos.
     *
     * @return la instancia de GestionPagos.
     */
    public static GestionPagos getInstancia() {
        if (instancia == null) {
            instancia = new GestionPagos();
        }
        return instancia;
    }

    //METODOS DE VALIDACIONES
    @Override
    //Este metodo valida el pago de paypal, regresa un valor booleano si se pasa toda la validacion exitosamente
    //Estos metodos de validacion se encargan de revisar los pagos y las cuentas
    /**
     * Procesa un pago a través de PayPal, validando el monto y la fecha.
     *
     * @param paypal la cuenta de PayPal desde la que se realiza el pago.
     * @param pago el objeto PagoDTO que contiene la informacion del pago.
     * @return true si el pago se procesa exitosamente, false en caso contrario.
     * @throws PagoException si hay un error en el pago.
     */
    public boolean procesarPagoPaypal(PaypalDTO paypal, PagoDTO pago) throws PagoException, ValidarCuentaException {
        IEstrategiaPago estrategia = new PaypalEstrategia(paypal);
        return ejecutarEstrategia(estrategia, pago);

    }

    private Boolean ejecutarEstrategia(IEstrategiaPago estrategia, PagoDTO pago)
            throws PagoException, ValidarCuentaException {

        Boolean exito = estrategia.procesarPago(pago);
        if (exito) {
            estrategia.actualizarSaldo(pago);
            pago.setEstado(true); // Estado Exitoso
        } else {
            pago.setEstado(false); // Estado Fallido
        }
        return exito;
    }

    @Override
    //Este metodo valida el pago de mercado, regresa un valor booleano si se pasa toda la validacion exitosamente
    /**
     * Procesa un pago a través de MercadoPago, validando el monto y la fecha.
     *
     * @param mercadoPago la cuenta de MercadoPago desde la que se realiza el
     * pago.
     * @param pago el objeto PagoDTO que contiene la informacion del pago.
     * @return true si el pago se procesa exitosamente, false en caso contrario.
     * @throws PagoException si hay un error en el pago.
     */
    public boolean procesarPagoMercado(CuentaMercadoDTO mercadoPago, PagoDTO pago) throws PagoException, ValidarCuentaException {
        IEstrategiaPago estrategia = new MercadoPagoEstrategia(mercadoPago);
        return ejecutarEstrategia(estrategia, pago);

    }

    @Override
    //Este metodo valida el pago de mercado, regresa un valor booleano si se pasa toda la validacion exitosamente
    /**
     * Procesa un pago a través de una tarjeta, validando el monto y la fecha.
     *
     * @param tarjeta la cuenta de tarjeta desde la que se realiza el pago.
     * @param pago el objeto PagoDTO que contiene la informacion del pago.
     * @return true si el pago se procesa exitosamente, false en caso contrario.
     * @throws PagoException si hay un error en el procesamiento del pago.
     */
    public boolean procesarPagoTarjeta(TarjetaDTO tarjeta, PagoDTO pago) throws PagoException, ValidarCuentaException {
        IEstrategiaPago estrategia = new TarjetaEstrategia(tarjeta);
        return ejecutarEstrategia(estrategia, pago);
    }

    //Metodo para validar una cuenta de paypal, este metodo recibe un PaypalDTO y lo que hace es buscar este dto dentro de la lista de cuentas existentes, en caso de encontrarla entonces se definira como una cuenta real, una vez se verifica que si existe se hacen algunas validaciones para la cuenta
    //Si se pasan todas las validaciones entonces se regresa el DTO encontrado en la lista (una cuenta existente).
    /**
     * Valida una cuenta de PayPal, verificando su existencia y validez.
     *
     * @param paypal el objeto PaypalDTO que representa la cuenta a validar.
     * @return el objeto PaypalDTO si la cuenta es valida.
     * @throws ValidarCuentaException si la cuenta no es válida.
     */
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
    //Si se pasan todas las validaciones entonces se regresa el DTO encontrado en la lista (una cuenta existente).
    /**
     * Valida una cuenta de MercadoPago. Este metodo recibe un objeto
     * CuentaMercadoDTO y busca este DTO dentro de la lista de cuentas
     * existentes. Si se encuentra, se realizan validaciones adicionales y se
     * devuelve el DTO encontrado.
     *
     * @param mercadoPago el objeto CuentaMercadoDTO que representa la cuenta a
     * validar.
     * @return el objeto CuentaMercadoDTO si la cuenta es valida.
     * @throws ValidarCuentaException si la cuenta no es válida o no cumple con
     * los requisitos.
     */
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
    //Si se pasan todas las validaciones entonces se regresa el DTO encontrado en la lista (una cuenta existente).
    /**
     * Valida una tarjeta. Este método recibe un objeto TarjetaDTO y busca este
     * DTO dentro de la lista de cuentas existentes. Si se encuentra, se
     * realizan validaciones adicionales y se devuelve el DTO encontrado.
     *
     * @param tarjeta el objeto TarjetaDTO que representa la tarjeta a validar.
     * @return el objeto TarjetaDTO si la tarjeta es válida.
     * @throws ValidarCuentaException si la tarjeta no es válida o no cumple con
     * los requisitos.
     */
    @Override
    public TarjetaDTO validarTarjeta(TarjetaDTO tarjeta) throws ValidarCuentaException {
        Optional<TarjetaDTO> tarjetaEncontrada = tarjetas.stream().filter(p -> p.equals(tarjeta)).findFirst();
        if (!tarjetaEncontrada.isPresent()) {
            return null;
        }
        TarjetaDTO cuentaTarjeta = tarjetaEncontrada.get();

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

    //Este metodo se utiliza para verificar si el pago de la transaccion fue exitoso (aunque no se utilizo) (no fue necesario)
    /**
     * Consulta el estado de un pago. Este método verifica si el objeto PagoDTO
     * es nulo y si su estado es nulo.
     *
     * @param pago el objeto PagoDTO que representa el pago a consultar.
     * @return true si el estado del pago es "EXITOSO"; false en caso contrario.
     * @throws PagoException si el objeto pago es nulo.
     */
    @Override
    public boolean consultarEstadoPago(PagoDTO pago) throws PagoException {
        if (pago == null || pago.getEstado() == null) {
            throw new PagoException("Error: El pago no tiene estado definido");
        }
        return pago.getEstado();
    }

    //METODOS PARA ACTUALIZAR LOS SALDOS DE LAS CUENTAS
    //La estructura de estos metodos es simple, primero recibe un DTO de una cuenta y dicha cuenta se busca en la lista de cuentas correspondiente,
    //en este caso se supone que las validaciones necesarias ya se hicieron anteriormente, es decir que la cuenta y el pago que reciben estos metodos
    //ya estan validados, actualizar el saldo de las cuentas es el ultimo paso de las transacciones
    //Como el dto que recibe este metodo ya esta validado siempre encontrara la cuenta correspondiente en la lista, la cuenta encontrada sera la que manipularemos
    //y para actualizar su saldo vamos a restarle el monto del pago al saldo de la cuenta
    /**
     * Actualiza el saldo de una cuenta de MercadoPago. Este metodo busca la
     * cuenta en la lista de cuentas y resta el monto del pago al saldo de la
     * cuenta.
     *
     * @param mercadoPago el objeto CuentaMercadoDTO que representa la cuenta a
     * actualizar.
     * @param pago el objeto PagoDTO que contiene la información del pago.
     */
    @Override
    public void actualizarSaldoMercado(CuentaMercadoDTO mercadoPago, PagoDTO pago) {
        Optional<CuentaMercadoDTO> cuentaMercadoEncontrada = mercados.stream().filter(p -> p.equals(mercadoPago)).findFirst();
        CuentaMercadoDTO cuentaMercado = cuentaMercadoEncontrada.get();
        cuentaMercado.setSaldo(cuentaMercado.getSaldo() - pago.getMonto());

    }

    /**
     * Actualiza el saldo de una cuenta de PayPal. Este método busca la cuenta
     * en la lista de cuentas y resta el monto del pago al saldo de la cuenta.
     *
     * @param paypal el objeto PaypalDTO que representa la cuenta a actualizar.
     * @param pago el objeto PagoDTO que contiene la información del pago.
     */
    @Override
    public void actualizarSaldoPaypal(PaypalDTO paypal, PagoDTO pago) {
        Optional<PaypalDTO> cuentaPaypalEncontrada = paypals.stream().filter(p -> p.equals(paypal)).findFirst();
        PaypalDTO cuentaPaypal = cuentaPaypalEncontrada.get();
        cuentaPaypal.setSaldo(cuentaPaypal.getSaldo() - pago.getMonto());

    }

    /**
     * Actualiza el saldo de una tarjeta. Este metodo busca la tarjeta en la
     * lista de cuentas y resta el monto del pago al saldo de la tarjeta.
     *
     * @param tarjeta el objeto TarjetaDTO que representa la tarjeta a
     * actualizar.
     * @param pago el objeto PagoDTO que contiene la información del pago.
     */
    @Override
    public void actualizarSaldoTarjeta(TarjetaDTO tarjeta, PagoDTO pago) {
        Optional<TarjetaDTO> tarjetaEncontrada = tarjetas.stream().filter(p -> p.equals(tarjeta)).findFirst();
        TarjetaDTO cuentaTarjeta = tarjetaEncontrada.get();
        cuentaTarjeta.setSaldo(cuentaTarjeta.getSaldo() - pago.getMonto());
    }

}
