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

    public Empleado obtenerEmpleadoActivoPorId(String empleadoIdString) throws DAOObtenerEmpleadoException, DAOValidacionEmpleadoException;

    public Empleado obtenerEmpleadoPorIdInterno(String empleadoIdString) throws DAOObtenerEmpleadoException, DAOValidacionEmpleadoException;

    public boolean actualizarEmpleado(Empleado empleado) throws DAOActualizarEmpleadoException, DAOValidacionEmpleadoException;

    public boolean despedirEmpleado(String empleadoIdString) throws DAODespedirEmpleadoException, DAOValidacionEmpleadoException;

    public List<Empleado> obtenerEmpleadosActivosPorCargo(Cargo cargo) throws DAOObtenerEmpleadoException, DAOValidacionEmpleadoException;

    public boolean actualizarCargoEmpleado(String empleadoIdString, Cargo nuevoCargo) throws DAOActualizarEmpleadoException, DAOValidacionEmpleadoException;

    public boolean actualizarSueldoIndividual(String empleadoIdString, Double nuevoSueldo) throws DAOActualizarEmpleadoException,DAOValidacionEmpleadoException;

    public long actualizarSueldoPorCargo(Cargo cargo, Double nuevoSueldo) throws DAOActualizarEmpleadoException, DAOValidacionEmpleadoException;
    
    public boolean existeEmpleadoConEseCorreo(String correoE) throws DAOObtenerEmpleadoException, DAOValidacionEmpleadoException;

    public Empleado consultarPorCorreoActivoExcluyendoId(String correoE, String excluirEmpleadoIdString) throws DAOObtenerEmpleadoException, DAOValidacionEmpleadoException;

}
