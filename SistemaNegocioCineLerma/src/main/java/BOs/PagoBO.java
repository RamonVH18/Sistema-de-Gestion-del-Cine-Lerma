/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BOs;

import DAOs.PagoDAO;
import DTOs.FuncionDTO;
import DTOs.PagoDTO;
import Excepciones.pagos.PagoValidacionException;
import Mappers.FuncionMapper;
import Mappers.PagoMapper;
import entidades.Funcion;
import entidades.Pago;
import java.time.LocalDateTime;

/**
 *
 * @author sonic
 */
public class PagoBO {

    private static PagoBO instance;
    private PagoDAO pagoDAO;

    private PagoBO() {
        // Inicializar el DAO
        pagoDAO = PagoDAO.getInstanceDAO();
    }

    // Método para obtener la instancia única
    public static PagoBO getInstanceBO() {
        if (instance == null) {
            instance = new PagoBO();
        }
        return instance;
    }

    public Double calcularMontoTotal(Integer boletos, FuncionDTO funcion) throws PagoValidacionException {
        // Cantidad de boletos
        if (boletos == null || boletos <= 0) {
            throw new PagoValidacionException("La cantidad de boletos debe ser mayor a cero");
        }

        // validar que la funcion no sea nula
        if (funcion == null) {
            throw new PagoValidacionException("La funcion no puede ser nula");
        }

        // validar el precio de la funcion, aunque siempre deberia ser valido
        if (funcion.getPrecio() == 0.0 || funcion.getPrecio() <= 0.0) {
            throw new PagoValidacionException("Precio de función invalido");
        }

        // validar el estado de la funcion, si es false entonces lanzara excepcion
        if (!funcion.getEstado()) {
            throw new PagoValidacionException("La función no está disponible");
        }

        // Validación 5: Fecha de función válida
        if (funcion.getFechaHora().isBefore(LocalDateTime.now())) {
            throw new PagoValidacionException("La funcion es pasada");
        }
        
        Funcion funcionEntidad = FuncionMapper.toFuncionEntidad(funcion);
        
        Double montoTotal = pagoDAO.calcularMontoTotal(boletos, funcionEntidad);

        return montoTotal;
    }

    public Pago registrarPagoExitoso(PagoDTO pago) throws PagoValidacionException {
        // Validar si el pago no es nulo
        if (pago == null) {
            throw new PagoValidacionException("El pago no puede ser nulo");
        }

        // Validar que el monto del pago no sea 0, menor o nulo
        if (pago.getMonto() == null || pago.getMonto() <= 0) {
            throw new PagoValidacionException("Monto de pago invalido");
        }

        // validar que la fecha no sea futura, que nunca deberia serlo
        if (pago.getFechaHora() != null && pago.getFechaHora().isAfter(LocalDateTime.now())) {
            throw new PagoValidacionException("Fecha de pago inválida");
        }
        
        Pago pagoRegistrar = PagoMapper.toPagoEntidad(pago);
        Pago pagoRegistrado = pagoDAO.registrarPagoExitoso(pagoRegistrar);

        return pagoRegistrado;
    }

}
