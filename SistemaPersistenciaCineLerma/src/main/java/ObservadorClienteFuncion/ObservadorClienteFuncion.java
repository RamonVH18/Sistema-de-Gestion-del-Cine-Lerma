/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ObservadorClienteFuncion;

import entidades.Cliente;
import Interfaces.ObservadorFuncion;

/**
 *
 * @author isaac
 */
public class ObservadorClienteFuncion implements ObservadorFuncion {

    private final Cliente cliente;

    public ObservadorClienteFuncion(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public void actualizar(String idFuncion, String tipoEvento, String mensaje) {

        String notificacion = String.format("Notificación para %s (%s): %s - %s",
                cliente.getNombre(),
                cliente.getCorreoElectronico(),
                tipoEvento,
                mensaje);

        System.out.println(notificacion);

    }

    public Cliente getCliente() {
        return cliente;
    }

    // el equals para comparar
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        ObservadorClienteFuncion other = (ObservadorClienteFuncion) obj;
        return cliente.getIdUsuario().equals(other.cliente.getIdUsuario());
    }

    @Override
    public int hashCode() {
        return cliente.getIdUsuario().hashCode();
    }
}
