/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Excepciones.PersistenciaException;
import Excepciones.empleados.DAOActualizarEmpleadoException;
import Excepciones.empleados.DAODespedirEmpleadoException;
import Excepciones.empleados.DAOObtenerEmpleadoException;
import Excepciones.empleados.DAORegistrarEmpleadoException;
import Excepciones.empleados.DAOValidacionEmpleadoException;
import entidades.Empleado;
import enums.Cargo;
import java.util.List;
import org.bson.types.ObjectId;

/**
 *
 * @author isaac
 */
public interface IEmpleadoDAO {

    public boolean registrarEmpleado(Empleado empleado) throws DAORegistrarEmpleadoException;

    public List<Empleado> obtenerTodosLosEmpleadosActivos() throws DAOObtenerEmpleadoException;

    public Empleado obtenerEmpleadoActivoPorId(ObjectId empleadoId) throws DAOObtenerEmpleadoException;

    public Empleado obtenerEmpleadoPorIdInterno(ObjectId empleadoId) throws DAOObtenerEmpleadoException;

    public boolean actualizarEmpleado(Empleado empleado) throws DAOActualizarEmpleadoException, DAOValidacionEmpleadoException;

    public boolean despedirEmpleado(ObjectId empleadoId) throws DAODespedirEmpleadoException, DAOValidacionEmpleadoException;

    public List<Empleado> obtenerEmpleadosActivosPorCargo(Cargo cargo) throws DAOObtenerEmpleadoException;

    public boolean actualizarCargoEmpleado(ObjectId empleadoId, Cargo nuevoCargo) throws DAOActualizarEmpleadoException, DAOValidacionEmpleadoException;

    public boolean actualizarSueldoIndividual(ObjectId empleadoId, double nuevoSueldo) throws DAOActualizarEmpleadoException,DAOValidacionEmpleadoException;

    public long actualizarSueldoPorCargo(Cargo cargo, double nuevoSueldo) throws DAOActualizarEmpleadoException, DAOValidacionEmpleadoException;

    public Empleado consultarPorCorreoActivoExcluyendoId(String correoE, ObjectId excluirEmpleadoId) throws DAOObtenerEmpleadoException;

}
