/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package enums;

/**
 *
 * @author Ramon Valencia
 */
public class EstadoSalaFactory {

    public static EstadoSala obtenerEstadoSala(String estadoSala) {
        switch (estadoSala) {
            case ("ACTIVA") -> {
                return EstadoSala.ACTIVA;
            }
            case ("INACTIVA") -> {
                return EstadoSala.INACTIVA;
            } 
            case ("MANTENIMIENTO") -> {
                return EstadoSala.MANTENIMIENTO;
            }
            default -> { 
                return null;
            }
        }
    }
}
