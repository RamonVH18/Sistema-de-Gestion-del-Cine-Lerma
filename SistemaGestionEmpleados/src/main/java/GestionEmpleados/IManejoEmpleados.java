/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package GestionEmpleados;

import DTOs.EmpleadoDTO;
import Excepciones.ActualizacionDeCargoException;
import Excepciones.ActualizacionEmpleadoException;
import Excepciones.ActualizacionSueldoException;
import Excepciones.Empleados.DespedirEmpleadoException;
import Excepciones.Empleados.ValidacionEmpleadoException;
import Excepciones.ObtenerEmpleadoException;
import Excepciones.ObtenerEmpleadoPorCargoException;
import Excepciones.PersistenciaException;
import Excepciones.RegistrarNuevoEmpleadoException;
import Excepciones.ValidacionEmpleadoIdException;
import Excepciones.ValidarEmpleadoException;
import Excepciones.validarActualizacionSueldoDeCargoException;
import enums.Cargo;
import java.util.List;

/**
 *
 * @author isaac
 */
public interface IManejoEmpleados {

    public EmpleadoDTO registrarNuevoEmpleado(EmpleadoDTO empleadoDTO) throws ValidarEmpleadoException, RegistrarNuevoEmpleadoException;

    public EmpleadoDTO actualizarInformacionEmpleado(String empleadoId, EmpleadoDTO datosNuevosDTO) throws ValidacionEmpleadoIdException, ActualizacionEmpleadoException, ValidarEmpleadoException;

    public boolean despedirEmpleado(String empleadoIdString) throws DespedirEmpleadoException, ValidarEmpleadoException, ValidacionEmpleadoIdException;

    public EmpleadoDTO buscarEmpleadoActivoPorId(String empleadoIdString) throws ValidarEmpleadoException, ObtenerEmpleadoException, ValidacionEmpleadoIdException;

    public List<EmpleadoDTO> obtenerEmpleadosActivosPorCargo(Cargo cargo) throws ValidacionEmpleadoIdException, ObtenerEmpleadoPorCargoException;
    
    public boolean actualizarCargoEmpleado(String empleadoIdString, Cargo nuevoCargo) throws ValidarEmpleadoException, ActualizacionDeCargoException, ValidacionEmpleadoIdException;
    
    public boolean actualizarSueldoEmpleadoIndividual(String empleadoIdString, double nuevoSueldo) throws ValidarEmpleadoException, ActualizacionSueldoException, ValidacionEmpleadoIdException;
    
    public List<EmpleadoDTO> obtenerTodosLosEmpleadosActivos() throws ObtenerEmpleadoException;
    
    public long actualizarSueldoGeneralPorCargo(Cargo cargo, double nuevoSueldo) throws validarActualizacionSueldoDeCargoException;
}
