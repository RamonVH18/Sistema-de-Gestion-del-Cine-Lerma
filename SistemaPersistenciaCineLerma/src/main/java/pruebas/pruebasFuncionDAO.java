/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pruebas;

import DAOs.FuncionDAO;
import Interfaces.IFuncionDAO;
import entidades.Funcion;
import java.util.List;

/**
 *
 * @author Daniel M
 */
public class pruebasFuncionDAO {

    
    public static void main(String[] args) {
        IFuncionDAO funcionDAO = FuncionDAO.getInstanceDAO();
//        List<Funcion> funciones = funcionDAO.mostrarFuncionesActivas();
//        for (Funcion funcion : funciones) {
//            System.out.println(funcion);
//        }
    }
    
}
