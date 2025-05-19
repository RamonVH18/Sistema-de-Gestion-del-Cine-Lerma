/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pruebas;

import DAOs.AsientoFuncionDAO;
import DAOs.FuncionDAO;
import DAOs.SalaDAO;
import DTOs.GananciaSalaDTO;
import Excepciones.AsientoFuncion.FalloCreacionAsientosFuncionException;
import Excepciones.Funciones.FuncionSalaOcupadaException;
import Excepciones.salas.BuscarSalaException;
import Excepciones.salas.ErrorCalculoEstadisticasSalaException;
import Interfaces.IAsientoFuncionDAO;
import Interfaces.IFuncionDAO;
import Interfaces.ISalaDAO;
import java.util.List;

/**
 *
 * @author Ramon Valencia
 */
public class pruebasAsientoFuncionDAO {
    private static final IFuncionDAO funcionDAO = FuncionDAO.getInstanceDAO();
    private static final ISalaDAO salaDAO = SalaDAO.getInstanceDAO(); 
    private static final IAsientoFuncionDAO asientoFuncionDAO = AsientoFuncionDAO.getInstanceDAO();
    
    public static void main(String[] args) throws BuscarSalaException, FuncionSalaOcupadaException, FalloCreacionAsientosFuncionException, ErrorCalculoEstadisticasSalaException {
        
        List<GananciaSalaDTO> asientos = asientoFuncionDAO.obtenerEstadisticasDeSalas();
        
//            Sala sala = salaDAO.buscarSala("C1");
//            Pelicula pelicula = new Pelicula("Cholos Empotrados", "Jaime", "Trans", 120, "El Abraham es gay", Boolean.TRUE);
//            Funcion funcion = new Funcion(sala, pelicula, LocalDateTime.now(), 120.00);
//            
//            funcion = funcionDAO.registrarFuncion(funcion);
//            
//            List<AsientoFuncion> asientosFuncion = new ArrayList<>();
//            List<Asiento> asientosSala = sala.getAsientos();
//            Asiento asiento;
//            for (int i = 0; i < sala.getNumAsientos(); i++) {
//                asiento = asientosSala.get(i);
//                AsientoFuncion nuevoAsiento = new AsientoFuncion(funcion, asiento.getNumero(), Boolean.TRUE, null);
//                asientosFuncion.add(nuevoAsiento);
//            }
//            
//            asientoFuncionDAO.agregarAsientosFuncion(asientosFuncion);
        
        
    }
    
    
}
