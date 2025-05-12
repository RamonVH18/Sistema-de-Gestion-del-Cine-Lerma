/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Excepciones.PersistenciaException;
import entidades.Empleado;
import enums.Cargo;
import java.util.List;
import org.bson.types.ObjectId;

/**
 *
 * @author isaac
 */
public interface IEmpleadoDAO {
    
    public boolean registrarEmpleado(Empleado empleado) throws PersistenciaException;
    
    public Empleado obtenerEmpleadoActivoPorId(ObjectId empleadoId) throws PersistenciaException;
    
    public List<Empleado> obtenerTodosLosEmpleadosActivos() throws PersistenciaException;

    public Empleado obtenerEmpleadoPorIdInterno(ObjectId empleadoId) throws PersistenciaException;

    public boolean actualizarEmpleado(Empleado empleado) throws PersistenciaException;

    public boolean despedirEmpleado(ObjectId empleadoId) throws PersistenciaException;

    public List<Empleado> obtenerEmpleadosActivosPorCargo(Cargo cargo) throws PersistenciaException;

    public boolean actualizarCargoEmpleado(ObjectId empleadoId, Cargo nuevoCargo) throws PersistenciaException;

    public boolean actualizarSueldoIndividual(ObjectId empleadoId, double nuevoSueldo) throws PersistenciaException;

    public long actualizarSueldoPorCargo(Cargo cargo, double nuevoSueldo) throws PersistenciaException;
    
    public Empleado consultarPorCorreoActivoExcluyendoId(String correoE, ObjectId excluirEmpleadoId) throws PersistenciaException;

}
