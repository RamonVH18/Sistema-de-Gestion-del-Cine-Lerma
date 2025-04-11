/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Excepciones.PersistenciaException;
import Interfaces.IAsientoFuncionDAO;
import Interfaces.IFuncionDAO;
import entidades.AsientoFuncion;
import entidades.Funcion;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ramon Valencia
 */
public class AsientoFuncionDAO implements IAsientoFuncionDAO{
    
    private static AsientoFuncionDAO instance;
    private final IFuncionDAO funcionDAO = FuncionDAO.getInstanceDAO();
    List<AsientoFuncion> asientos = new ArrayList<>();
    
    private AsientoFuncionDAO() {
        
    }
    
    public static AsientoFuncionDAO getInstance() {
        if (instance == null) {
            instance = new AsientoFuncionDAO();
        }
        return instance;
    } 
    
    public List<AsientoFuncion> asientosHarcodeados() throws PersistenciaException {
        
        if (asientos.isEmpty()) {
            List<Funcion> funciones = funcionDAO.mostrarFuncionesActivas();
            for (int i = 0; i < funciones.size(); i++) {
                Funcion funcion = funciones.get(i);
                for (int s = 1; s < 26; s++) {
                    String numero = String.valueOf(s + 1);
                    int id = (i*25) + s;
                    long idL = id;
                    AsientoFuncion asiento = new AsientoFuncion(idL, funcion, funcion.getSala(), true, null);
                    asientos.add(asiento);
                }
            }
        }
        return asientos;
    }

    @Override
    public List<AsientoFuncion> mostrarAsientosFunciones(Funcion funcion) throws PersistenciaException {
        List<AsientoFuncion> asientosFuncion = new ArrayList<>();
        for (int i = 1; i < asientos.size(); i++) {
            AsientoFuncion asiento = asientos.get(i);
            if (asiento.getFuncion() == funcion) {
                asientosFuncion.add(asiento);
            }
        }
        return asientosFuncion;
    }

    @Override
    public Boolean ocuparAsiento(AsientoFuncion asiento) throws PersistenciaException {
        
        int indice = asientos.indexOf(asiento);
        
        asiento.setDisponibilidad(Boolean.FALSE);
        
        asientos.set(indice, asiento);
        
        return true;
    }
    
       
}
