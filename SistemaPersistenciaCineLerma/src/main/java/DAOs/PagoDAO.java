/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Interfaces.IPagoDAO;
import entidades.Funcion;
import entidades.Pago;
import entidades.Pelicula;
import entidades.Sala;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sonic
 */
public class PagoDAO implements IPagoDAO {

    private static PagoDAO instance;
    private List<Pago> pagos = new ArrayList<>();

    public PagoDAO() {
        inicializarObjetos();
    }

    public static PagoDAO getInstanceDAO() {
        if (instance == null) {
            instance = new PagoDAO();
        }

        return instance;
    }

    private void inicializarObjetos() {
        //Objetos necesarios simulados
        Pelicula pelicula = new Pelicula();
        pelicula.setDuracion(120);

        LocalDateTime fechaHoraInicio = LocalDateTime.now();
        Funcion funcion = new Funcion(new Sala(), pelicula, fechaHoraInicio, 10.0);

        // Calcular monto para 3 boletos
        Double montoTotal = calcularMontoTotal(3, funcion);

        //Una vez que se calculo el monto podemos asignar un id, una fecha y un pago siempre que se registre su estado sera true
        Pago pago = new Pago(1L, montoTotal, LocalDateTime.now(), true);

        pagos.add(pago);

    }

    @Override
    public Pago registrarPagoExitoso(Pago pago) {
        // Una vez que se registra el pago entonces se le asignara un id
        Long idPago = (long) (pagos.size() + 1); //Asignamos un id simulado segun los pagos registrados

        Double monto = pago.getMonto();

        // Establecer fecha/hora actual y estado exitoso (true)
        LocalDateTime fechaHora = LocalDateTime.now();
        boolean estado = true;

        Pago pagoRegistrado = new Pago(idPago, monto, fechaHora, estado);

        return pagoRegistrado; //Se regresa el pago registrado ya con un estado, fecha e id asignados
    }

    @Override
    public Double calcularMontoTotal(Integer boletos, Funcion funcion) {
        // Obtener el precio de la funcion y calcular el monto total
        Double montoTotal = funcion.getPrecio() * boletos;

        return montoTotal;
    }

}
