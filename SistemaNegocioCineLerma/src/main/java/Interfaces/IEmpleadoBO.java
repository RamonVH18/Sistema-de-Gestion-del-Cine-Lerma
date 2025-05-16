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
    
    
    public EmpleadoDTO registrarNuevoEmpleado(EmpleadoDTO empleadoDTO) throws ValidacionEmpleadoException, RegistrarEmpleadoException, PersistenciaException;
    
    public EmpleadoDTO actualizarInformacionEmpleado(String empleadoId, EmpleadoDTO datosNuevosDTO) throws ValidacionEmpleadoException, ActualizarEmpleadoException, PersistenciaException;
    
    public boolean despedirEmpleado(String empleadoId) throws DespedirEmpleadoException, PersistenciaException;
    
    public EmpleadoDTO buscarEmpleadoActivoPorId(String empleadoId) throws BuscarEmpleadoException, PersistenciaException;
    
    public List<EmpleadoDTO> obtenerTodosLosEmpleadosActivos() throws PersistenciaException;
    
     public List<EmpleadoDTO> obtenerEmpleadosActivosPorCargo(Cargo cargo) throws ValidacionEmpleadoException, PersistenciaException;
    
    public boolean actualizarCargoEmpleado(String empleadoId, Cargo nuevoCargo) throws ValidacionEmpleadoException, ActualizarEmpleadoException, PersistenciaException;
    
    public boolean actualizarSueldoEmpleado(String empleadoId, double nuevoSueldo) throws ValidacionEmpleadoException, ActualizarEmpleadoException, PersistenciaException;
    
    public long actualizarSueldoGeneralPorCargo(Cargo cargo, double nuevoSueldo) throws ValidacionEmpleadoException, PersistenciaException;
    
}
