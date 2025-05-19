/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GestionEmpleados;

import BOs.EmpleadoBO;
import DTOs.EmpleadoDTO;
import Excepciones.ManejoActualizacionDeCargoException;
import Excepciones.ManejoActualizacionEmpleadoException;
import Excepciones.ManejoActualizacionSueldoException;
import Excepciones.Empleados.ActualizarEmpleadoException;
import Excepciones.Empleados.BuscarEmpleadoException;
import Excepciones.Empleados.DespedirEmpleadoException;
import Excepciones.Empleados.ObtenerSueldoEmpleadoException;
import Excepciones.Empleados.RegistrarEmpleadoException;
import Excepciones.Empleados.ValidacionEmpleadoException;
import Excepciones.ManejoDespedirEmpleadoException;
import Excepciones.ManejoObtenerEmpleadoException;
import Excepciones.ManejoObtenerEmpleadoPorCargoException;
import Excepciones.PersistenciaException;
import Excepciones.ManejoRegistrarNuevoEmpleadoException;
import Excepciones.ManejoValidacionEmpleadoIdException;
import Excepciones.ManejoValidarEmpleadoException;
import Excepciones.ManejoValidarActualizacionSueldoDeCargoException;
import Interfaces.IEmpleadoBO;
import entidades.Empleado;
import enums.Cargo;
import static enums.Cargo.CAJERO;
import static enums.Cargo.GERENTE;
import static enums.Cargo.JEFE_ALIMENTOS;
import static enums.Cargo.JEFE_CAJA;
import static enums.Cargo.JEFE_PISO;
import static enums.Cargo.LIMPIEZA;
import static enums.Cargo.MANTENIMIENTO;
import static enums.Cargo.PALOMITERO;
import static enums.Cargo.PROYECCIONISTA;
import static enums.Cargo.REVISION_BOLETOS;
import static enums.Cargo.SEGURIDAD;
import static enums.Cargo.TECNICO_SONIDO;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bson.types.ObjectId;

/**
 *
 * @author isaac
 */
public class ManejoEmpleados implements IManejoEmpleados {

    private IEmpleadoBO empleadoBO = new EmpleadoBO();
    private static ManejoEmpleados instanceManejoEmpleados;

    private ManejoEmpleados() {

        this.empleadoBO = new EmpleadoBO();
    }

    public static ManejoEmpleados getInstance() {
        if (instanceManejoEmpleados == null) { // para la primera vez que se llama
            instanceManejoEmpleados = new ManejoEmpleados();
        }
        return instanceManejoEmpleados;
    }

    // ========================== METODOS PRIVADOS DE VALIDACIONES ===========================================================
    private void validarDatosEmpleadoDTO(EmpleadoDTO dto, boolean esNuevo) throws ManejoValidarEmpleadoException {
        if (dto == null) {
            throw new ManejoValidarEmpleadoException("Los datos del empleado (DTO) no pueden ser nulos.");
        }

        // Si es una actualización (esNuevo = false), el DTO podría o no llevar el ID.
        // La verificación del ID para actualización se hará en el método público del BO que recibe el ID.
        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
            throw new ManejoValidarEmpleadoException("El nombre del empleado no puede ser nulo o vacío.");
        }

        if (dto.getNombre().length() < 3 || dto.getNombre().length() > 50) {
            throw new ManejoValidarEmpleadoException("El nombre del empleado debe tener entre 3 y 50 caracteres.");
        }
        if (dto.getApellidoP() == null || dto.getApellidoP().trim().isEmpty()) {
            throw new ManejoValidarEmpleadoException("El apellido paterno no puede ser nulo o vacío.");
        }
        if (dto.getApellidoP().length() < 3 || dto.getApellidoP().length() > 50) {
            throw new ManejoValidarEmpleadoException("El apellido paterno debe tener entre 3 y 50 caracteres.");
        }
        if (dto.getApellidoM() == null || dto.getApellidoM().trim().isEmpty()) {
            throw new ManejoValidarEmpleadoException("El apellido materno no puede ser nulo o vacío.");
        }
        if (dto.getApellidoM().length() < 3 || dto.getApellidoM().length() > 50) {
            throw new ManejoValidarEmpleadoException("El apellido materno debe tener entre 3 y 50 caracteres.");
        }
        if (dto.getCorreoE() == null || dto.getCorreoE().trim().isEmpty()) {
            throw new ManejoValidarEmpleadoException("El correo electrónico no puede ser nulo o vacío.");
        }
        if (!esCorreoValido(dto.getCorreoE())) {
            throw new ManejoValidarEmpleadoException("El formato del correo electrónico es inválido.");
        }
        if (dto.getTelefono() == null || dto.getTelefono().trim().isEmpty()) {
            throw new ManejoValidarEmpleadoException("El teléfono no puede ser nulo o vacío.");
        }
        if (!esTelefonoValido(dto.getTelefono())) {
            throw new ManejoValidarEmpleadoException("El número de teléfono debe contener 10 dígitos numéricos.");
        }
        if (dto.getFechaNacimiento() == null) {
            throw new ManejoValidarEmpleadoException("La fecha de nacimiento es obligatoria.");
        }
        int edad = Period.between(dto.getFechaNacimiento().toLocalDate(), LocalDate.now()).getYears();
        if (edad <= 15) {
            throw new ManejoValidarEmpleadoException("El empleado debe ser mayor de 18 años.");
        }
        if (edad > 70) {
            throw new ManejoValidarEmpleadoException("La edad del empleado excede el límite razonable (70 años).");
        }
        if (dto.getCargo() == null) {
            throw new ManejoValidarEmpleadoException("El cargo del empleado es obligatorio.");
        }

        if (dto.getCalle() == null || dto.getCalle().trim().isEmpty()) {
            throw new ManejoValidarEmpleadoException("La calle en la dirección es obligatoria.");
        }
        if (dto.getColonia() == null || dto.getColonia().trim().isEmpty()) {
            throw new ManejoValidarEmpleadoException("La colonia en la dirección es obligatoria.");
        }
        if (dto.getNumExterior() == null || dto.getNumExterior().trim().isEmpty()) {
            throw new ManejoValidarEmpleadoException("El número exterior en la dirección es obligatorio.");
        }

        if (esNuevo == false) {
            // Solo validamos el sueldo si NO es un empleado nuevo (esNuevo == false),
            // lo que implicaría una actualización donde el sueldo podría estar presente en el DTO.
            // O si en el futuro, para registrar, decides que el sueldo debe venir y ser validado aquí.
            // POR AHORA, para el registro, el BO asigna y luego valida el sueldo.

            if (dto.getSueldo() <= 0) {
                throw new ManejoValidarEmpleadoException("El sueldo del empleado debe ser un valor positivo.");
            }
            if (dto.getSueldo() < 1000 || dto.getSueldo() > 200000) {
                throw new ManejoValidarEmpleadoException("El sueldo está fuera de los rangos permitidos (1,000 - 200,000).");
            }
        }

    }

    private boolean esCorreoValido(String correo) {
        if (correo == null) {
            return false;
        }
        String regex = "^[\\w-\\.\\+]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(correo);
        return matcher.matches();
    }

    private boolean esTelefonoValido(String telefono) {

        if (telefono == null) {
            return false;
        }
        String regex = "^\\d{10}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(telefono);
        return matcher.matches();
    }

    private void validarEmpleadoID(String empleadoIdString) throws ManejoValidacionEmpleadoIdException {

        if (empleadoIdString == null || empleadoIdString.trim().isEmpty()) {
            throw new ManejoValidacionEmpleadoIdException("El ID del empleado no puede ser nulo o vacío.");
        }
        if (!ObjectId.isValid(empleadoIdString)) {
            throw new ManejoValidacionEmpleadoIdException("El formato del ID del empleado ('" + empleadoIdString + "') no es válido.");
        }
    }

    @Override
    public EmpleadoDTO registrarNuevoEmpleado(EmpleadoDTO empleadoDTO) throws ManejoValidarEmpleadoException, ManejoRegistrarNuevoEmpleadoException {
        // Validación de datos de entrada en esta capa
        validarDatosEmpleadoDTO(empleadoDTO, true); // true para indicar que es un nuevo empleado

        try {
            // Llamada al BO. El BO se encarga de asignar sueldo inicial y validaciones adicionales.
            return empleadoBO.registrarNuevoEmpleado(empleadoDTO);
        } catch (ValidacionEmpleadoException e) { // Excepción de validación del BO
            // Convertir a la excepción de esta capa ManejoEmpleados
            throw new ManejoValidarEmpleadoException("Error de validación al procesar el registro: " + e.getMessage(), e);
        } catch (RegistrarEmpleadoException e) { // Excepción de registro del BO
            throw new ManejoRegistrarNuevoEmpleadoException("Error al registrar el nuevo empleado: " + e.getMessage(), e);
        } catch (BuscarEmpleadoException e) { // Si el BO.registrarNuevoEmpleado lanza esto (ej. por chequeo de correo)
            throw new ManejoRegistrarNuevoEmpleadoException("Error durante la verificación para el registro: " + e.getMessage(), e);
        } catch (Exception e) { // Captura genérica para errores inesperados del BO
            throw new ManejoRegistrarNuevoEmpleadoException("Error inesperado al registrar empleado: " + e.getMessage(), e);
        }
    }

    @Override
    public EmpleadoDTO actualizarInformacionEmpleado(String empleadoId, EmpleadoDTO datosNuevosDTO) throws ManejoValidacionEmpleadoIdException, ManejoActualizacionEmpleadoException, ManejoValidarEmpleadoException {

        validarEmpleadoID(empleadoId); // llamamos al metodo privado de validacion de id
        validarDatosEmpleadoDTO(datosNuevosDTO, false); // false para indicar actualización
        // (se valida el sueldo si viene en el DTO)
        // El BO se encarga de verificar existencia, estado activo, unicidad de correo si cambia, etc.
        try {
            return empleadoBO.actualizarInformacionEmpleado(empleadoId, datosNuevosDTO);
        } catch (Exception e) {
            throw new ManejoActualizacionEmpleadoException("Error inesperado al actualizar empleado: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean despedirEmpleado(String empleadoIdString) throws ManejoDespedirEmpleadoException, ManejoValidacionEmpleadoIdException, ManejoValidarEmpleadoException {

        validarEmpleadoID(empleadoIdString);

        try {
            return empleadoBO.despedirEmpleado(empleadoIdString);
        } catch (ValidacionEmpleadoException e) { // Del BO (ej. ID no válido pasado al DAO, aunque ya lo validamos aquí)
            throw new ManejoValidarEmpleadoException("Error de validación al procesar el despido: " + e.getMessage(), e);
        } catch (DespedirEmpleadoException e) { // Del BO
            throw new ManejoDespedirEmpleadoException("Error al despedir al empleado: " + e.getMessage(), e);
        } catch (BuscarEmpleadoException e) { // Del BO (si no encuentra el empleado para despedir)
            throw new ManejoDespedirEmpleadoException("Error al localizar empleado para despido: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new ManejoDespedirEmpleadoException("Error inesperado al despedir empleado: " + e.getMessage(), e);
        }
    }

    @Override
    public EmpleadoDTO buscarEmpleadoActivoPorId(String empleadoIdString) throws ManejoObtenerEmpleadoException, ManejoValidacionEmpleadoIdException {

        validarEmpleadoID(empleadoIdString);

        try {
            EmpleadoDTO dto = empleadoBO.buscarEmpleadoActivoPorId(empleadoIdString);
            // El BO ahora debería lanzar BuscarEmpleadoException si no lo encuentra,
            // en lugar de devolver null, para ser consistente.
            // Si el BO pudiera devolver null, el chequeo if (dto == null) sería necesario aquí.
            return dto;
        } catch (BuscarEmpleadoException e) { // Del BO
            throw new ManejoObtenerEmpleadoException("Error al buscar empleado: " + e.getMessage(), e);
        } catch (ValidacionEmpleadoException e) { // Del BO (si el ID era válido aquí pero el BO lo rechaza por otra razón)
            throw new ManejoValidacionEmpleadoIdException("Error de validación al buscar empleado: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new ManejoObtenerEmpleadoException("Error inesperado al buscar empleado: " + e.getMessage(), e);
        }
    }

    @Override
    public List<EmpleadoDTO> obtenerEmpleadosActivosPorCargo(Cargo cargo) throws ManejoObtenerEmpleadoPorCargoException, ManejoValidarEmpleadoException { // ManejoValidarEmpleadoException si cargo es null
        if (cargo == null) {
            // Esta validación es propia de esta capa.
            throw new ManejoValidarEmpleadoException("El cargo para la búsqueda no puede ser nulo.");
        }
        try {
            return empleadoBO.obtenerEmpleadosActivosPorCargo(cargo);
        } catch (BuscarEmpleadoException e) { // Del BO
            throw new ManejoObtenerEmpleadoPorCargoException("Error al obtener empleados por cargo: " + e.getMessage(), e);
        } catch (ValidacionEmpleadoException e) { // Del BO (si valida el cargo y falla)
            throw new ManejoValidarEmpleadoException("Error de validación al obtener empleados por cargo: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new ManejoObtenerEmpleadoPorCargoException("Error inesperado al obtener empleados por cargo: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean actualizarCargoEmpleado(String empleadoIdString, Cargo nuevoCargo) throws ManejoActualizacionDeCargoException, ManejoValidacionEmpleadoIdException, ManejoValidarEmpleadoException {
        validarEmpleadoID(empleadoIdString);
        if (nuevoCargo == null) {
            throw new ManejoValidarEmpleadoException("El nuevo cargo no puede ser nulo.");
        }

        try {
            return empleadoBO.actualizarCargoEmpleado(empleadoIdString, nuevoCargo);
        } catch (ValidacionEmpleadoException e) { // Del BO
            throw new ManejoValidarEmpleadoException("Error de validación al actualizar cargo: " + e.getMessage(), e);
        } catch (ActualizarEmpleadoException e) { // Del BO
            throw new ManejoActualizacionDeCargoException("Error al actualizar el cargo del empleado: " + e.getMessage(), e);
        } catch (BuscarEmpleadoException e) { // Del BO (si no encuentra al empleado)
            throw new ManejoActualizacionDeCargoException("Error al localizar empleado para actualizar cargo: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new ManejoActualizacionDeCargoException("Error inesperado al actualizar cargo: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean actualizarSueldoEmpleadoIndividual(String empleadoIdString, double nuevoSueldo) throws ManejoActualizacionSueldoException, ManejoValidacionEmpleadoIdException, ManejoValidarEmpleadoException {
        validarEmpleadoID(empleadoIdString);
        // Validaciones de negocio para el nuevo sueldo en esta capa
        if (nuevoSueldo <= 0) {
            throw new ManejoValidarEmpleadoException("El nuevo sueldo debe ser un valor positivo.");
        }
        if (nuevoSueldo < 1000 || nuevoSueldo > 200000) {
            throw new ManejoValidarEmpleadoException("El sueldo está fuera de los rangos permitidos (1,000 - 200,000).");
        }

        try {
            return empleadoBO.actualizarSueldoEmpleado(empleadoIdString, nuevoSueldo);
        } catch (ValidacionEmpleadoException e) { // Del BO
            throw new ManejoValidarEmpleadoException("Error de validación al actualizar sueldo: " + e.getMessage(), e);
        } catch (ActualizarEmpleadoException e) { // Del BO
            throw new ManejoActualizacionSueldoException("Error al actualizar el sueldo del empleado: " + e.getMessage(), e);
        } catch (BuscarEmpleadoException e) { // Del BO (si no encuentra al empleado)
            throw new ManejoActualizacionSueldoException("Error al localizar empleado para actualizar sueldo: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new ManejoActualizacionSueldoException("Error inesperado al actualizar sueldo: " + e.getMessage(), e);
        }
    }

    @Override
    public List<EmpleadoDTO> obtenerTodosLosEmpleadosActivos() throws ManejoObtenerEmpleadoException {
        try {
            return empleadoBO.obtenerTodosLosEmpleadosActivos();
        } catch (Exception e) {
            throw new ManejoObtenerEmpleadoException("Error inesperado al obtener todos los empleados activos: " + e.getMessage(), e);
        }
    }

    @Override
    public long actualizarSueldoGeneralPorCargo(Cargo cargo, double nuevoSueldo) throws ManejoValidarActualizacionSueldoDeCargoException, ManejoValidarEmpleadoException { // ManejoValidarEmpleadoException por las validaciones de nuevoSueldo
        if (cargo == null) {
            throw new ManejoValidarActualizacionSueldoDeCargoException("El cargo no puede ser nulo para actualizar sueldos.");
        }
        if (nuevoSueldo <= 0) {
            throw new ManejoValidarEmpleadoException("El nuevo sueldo debe ser un valor positivo.");
        }
        if (nuevoSueldo < 1000 || nuevoSueldo > 200000) {
            throw new ManejoValidarEmpleadoException("El sueldo general para el cargo está fuera de los rangos permitidos (1,000 - 200,000).");
        }

        try {
            return empleadoBO.actualizarSueldoGeneralPorCargo(cargo, nuevoSueldo);
        } catch (ValidacionEmpleadoException e) { // Del BO
            // Esta excepción del BO podría ser por validación del cargo o del sueldo que se pasó al BO.
            // La convertimos a la de esta capa.
            throw new ManejoValidarActualizacionSueldoDeCargoException("Error de validación al procesar la actualización de sueldo por cargo: " + e.getMessage(), e);
        } catch (ActualizarEmpleadoException e) { // Del BO
            // Si el BO lanza ActualizarEmpleadoException para esta operación.
            throw new ManejoValidarActualizacionSueldoDeCargoException("Error al actualizar sueldos por cargo: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new ManejoValidarActualizacionSueldoDeCargoException("Error inesperado durante la actualización de sueldos por cargo: " + e.getMessage(), e);
        }
    }

}
