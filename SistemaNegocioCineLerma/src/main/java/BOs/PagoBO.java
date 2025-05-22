/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BOs;

import DAOs.PagoDAO;
import DTOs.FuncionDTO;
import DTOs.PagoDTO;
import Excepciones.pagos.PagoValidacionException;
import Interfaces.mappers.IFuncionMapper;
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
    private IFuncionMapper funcionMapper = new FuncionMapper();

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

        Funcion funcionEntidad = funcionMapper.toFuncionEntidad(funcion);

        Double montoTotal = pagoDAO.calcularMontoTotal(boletos, funcionEntidad);

        return montoTotal;
    }

    public PagoDTO registrarPagoExitoso(PagoDTO pago) throws PagoValidacionException {

        Pago pagoRegistrar = PagoMapper.toPagoEntidad(pago);
        Pago pagoRegistrado = pagoDAO.registrarPagoExitoso(pagoRegistrar);

        return PagoMapper.toPagoDTO(pagoRegistrado);
    }

}
