/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import DTOs.EmpleadoDTO;
import Excepciones.Empleados.ActualizarEmpleadoException;
import Excepciones.Empleados.BuscarEmpleadoException;
import Excepciones.Empleados.DespedirEmpleadoException;
import Excepciones.Empleados.RegistrarEmpleadoException;
import Excepciones.Empleados.ValidacionEmpleadoException;
import Excepciones.PersistenciaException;
import entidades.Empleado;
import enums.Cargo;
import java.util.List;
import org.bson.types.ObjectId;

/**
 *
 * @author isaac
 */
public interface IEmpleadoBO {

    public EmpleadoDTO registrarNuevoEmpleado(EmpleadoDTO empleadoDTO) throws ValidacionEmpleadoException, RegistrarEmpleadoException, BuscarEmpleadoException;

    public EmpleadoDTO actualizarInformacionEmpleado(String empleadoId, EmpleadoDTO datosNuevosDTO) throws ValidacionEmpleadoException, ActualizarEmpleadoException, BuscarEmpleadoException;

    public boolean despedirEmpleado(String empleadoIdString) throws DespedirEmpleadoException, BuscarEmpleadoException, ValidacionEmpleadoException;

    public EmpleadoDTO buscarEmpleadoActivoPorId(String empleadoIdString)   throws BuscarEmpleadoException, ValidacionEmpleadoException;

     public List<EmpleadoDTO> obtenerEmpleadosActivosPorCargo(Cargo cargo)  throws ValidacionEmpleadoException, BuscarEmpleadoException;

    public boolean actualizarCargoEmpleado(String empleadoIdString, Cargo nuevoCargo) throws ValidacionEmpleadoException, ActualizarEmpleadoException, BuscarEmpleadoException;

    public List<EmpleadoDTO> obtenerTodosLosEmpleadosActivos() throws BuscarEmpleadoException;
    
    public boolean actualizarSueldoEmpleado(String empleadoIdString, double nuevoSueldo) throws ValidacionEmpleadoException, ActualizarEmpleadoException, BuscarEmpleadoException;

    public long actualizarSueldoGeneralPorCargo(Cargo cargo, double nuevoSueldo)  throws ValidacionEmpleadoException, ActualizarEmpleadoException;

}
