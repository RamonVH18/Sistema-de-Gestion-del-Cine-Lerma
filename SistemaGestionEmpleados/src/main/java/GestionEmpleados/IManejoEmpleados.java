/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package GestionEmpleados;

import DTOs.EmpleadoDTO;
import Excepciones.ManejoActualizacionDeCargoException;
import Excepciones.ManejoActualizacionEmpleadoException;
import Excepciones.ManejoActualizacionSueldoException;
import Excepciones.Empleados.DespedirEmpleadoException;
import Excepciones.Empleados.ValidacionEmpleadoException;
import Excepciones.ManejoDespedirEmpleadoException;
import Excepciones.ManejoObtenerEmpleadoException;
import Excepciones.ManejoObtenerEmpleadoPorCargoException;
import Excepciones.PersistenciaException;
import Excepciones.ManejoRegistrarNuevoEmpleadoException;
import Excepciones.ManejoValidacionEmpleadoIdException;
import Excepciones.ManejoValidarEmpleadoException;
import Excepciones.ManejoValidarActualizacionSueldoDeCargoException;
import enums.Cargo;
import java.util.List;

/**
 *
 * @author isaac
 */
public interface IManejoEmpleados {

     public EmpleadoDTO registrarNuevoEmpleado(EmpleadoDTO empleadoDTO) throws ManejoValidarEmpleadoException, ManejoRegistrarNuevoEmpleadoException;

    public EmpleadoDTO actualizarInformacionEmpleado(String empleadoId, EmpleadoDTO datosNuevosDTO) throws ManejoValidacionEmpleadoIdException, ManejoActualizacionEmpleadoException, ManejoValidarEmpleadoException;

    public boolean despedirEmpleado(String empleadoIdString) throws ManejoDespedirEmpleadoException, ManejoValidacionEmpleadoIdException, ManejoValidarEmpleadoException;

    public EmpleadoDTO buscarEmpleadoActivoPorId(String empleadoIdString) throws ManejoObtenerEmpleadoException, ManejoValidacionEmpleadoIdException;

    public List<EmpleadoDTO> obtenerEmpleadosActivosPorCargo(Cargo cargo) throws ManejoObtenerEmpleadoPorCargoException, ManejoValidarEmpleadoException;
    
    public boolean actualizarCargoEmpleado(String empleadoIdString, Cargo nuevoCargo) throws ManejoValidarEmpleadoException, ManejoActualizacionDeCargoException, ManejoValidacionEmpleadoIdException;
    
    public boolean actualizarSueldoEmpleadoIndividual(String empleadoIdString, double nuevoSueldo) throws ManejoActualizacionSueldoException, ManejoValidacionEmpleadoIdException, ManejoValidarEmpleadoException;
    
    public List<EmpleadoDTO> obtenerTodosLosEmpleadosActivos() throws ManejoObtenerEmpleadoException;
    
     public long actualizarSueldoGeneralPorCargo(Cargo cargo, double nuevoSueldo) throws ManejoValidarActualizacionSueldoDeCargoException, ManejoValidarEmpleadoException;
}
