/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

/**
 *
 * @author isaac
 */
public interface GestorObservadores  {

    public void agregarObservador(Long idFuncion, ObservadorFuncion observador);

    public void eliminarObservador(Long idFuncion, ObservadorFuncion observador);

    public void notificarObservadores(Long idFuncion, String tipoEvento, String mensaje);
    
}
