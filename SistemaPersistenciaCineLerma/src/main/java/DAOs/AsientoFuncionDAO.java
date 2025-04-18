/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Excepciones.PersistenciaException;
import Interfaces.IAsientoFuncionDAO;
import Interfaces.IFuncionDAO;
import entidades.Asiento;
import entidades.AsientoFuncion;
import entidades.Funcion;
import entidades.Sala;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ramon Valencia
 */
public class AsientoFuncionDAO implements IAsientoFuncionDAO {

    private static AsientoFuncionDAO instance;
    private final IFuncionDAO funcionDAO = FuncionDAO.getInstanceDAO();
    List<AsientoFuncion> asientosFuncion = new ArrayList<>();

    private AsientoFuncionDAO() {
    }

    public static AsientoFuncionDAO getInstance() {
        if (instance == null) {
            instance = new AsientoFuncionDAO();
        }
        return instance;
    }

    private List<AsientoFuncion> asientosHarcodeados() throws PersistenciaException {
        if (asientosFuncion.isEmpty()) {
            List<Funcion> funciones = funcionDAO.mostrarFuncionesActivas();
            for (int i = 0; i < funciones.size(); i++) {
                Funcion funcion = funciones.get(i);
                Sala sala = funcion.getSala();
                
                List<Asiento> asientoNuevos = new ArrayList<>();
                
                for (int s = 0; s < 20; s++) {
                    int id = (i * 25) + s;
                    long idL = id;
                    String numero = String.valueOf(idL);
                    
                    Asiento asiento = new Asiento(idL, numero, sala);
                    asientoNuevos.add(asiento);
                }
                
                sala.setAsientos(asientoNuevos);
                List<Asiento> asientos = sala.getAsientos();
                for (int a = 0; a < asientos.size(); a++) {
                    Asiento asiento = asientos.get(a);
                    int id = (i * 25) + a;
                    long idL = id;
                    AsientoFuncion asientoF = new AsientoFuncion(idL, funcion, asiento, true, null);
                    asientosFuncion.add(asientoF);
                }
            }
        }
        return asientosFuncion;
    }

    @Override
    public List<AsientoFuncion> mostrarAsientosFunciones(Funcion funcion) throws PersistenciaException {

        List<AsientoFuncion> asientosFuncion = asientosHarcodeados();
        for (int i = 1; i < asientosFuncion.size(); i++) {
            AsientoFuncion asiento = asientosFuncion.get(i);
            if (asiento.getFuncion() == funcion) {
                asientosFuncion.add(asiento);
            }
        }
        return asientosFuncion;
    }

    @Override
    public Boolean ocuparAsiento(AsientoFuncion asiento) throws PersistenciaException {
        asientosHarcodeados();
        int indice = asientosFuncion.indexOf(asiento);

        asiento.setDisponibilidad(Boolean.FALSE);

        asientosFuncion.set(indice, asiento);

        return true;
    }

    @Override
    public List<AsientoFuncion> mostrarAsientosDisponibles(Funcion funcion) throws PersistenciaException {
        asientosHarcodeados();
        List<AsientoFuncion> asientosFuncion = asientosHarcodeados();
        List<AsientoFuncion> asientos = new ArrayList<>();
        for (int i = 1; i < asientosFuncion.size(); i++) {
            AsientoFuncion asiento = asientosFuncion.get(i);
            if (asiento.getFuncion().getIdFuncion() == funcion.getIdFuncion() && asiento.getDisponibilidad() == true) {
                asientos.add(asiento);
            }
        }
        return asientos;
    }

}
