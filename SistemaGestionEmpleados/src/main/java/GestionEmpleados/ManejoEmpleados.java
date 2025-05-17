/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GestionEmpleados;

import BOs.EmpleadoBO;
import DTOs.EmpleadoDTO;
import Excepciones.ActualizacionDeCargoException;
import Excepciones.ActualizacionEmpleadoException;
import Excepciones.ActualizacionSueldoException;
import Excepciones.Empleados.ActualizarEmpleadoException;
import Excepciones.Empleados.BuscarEmpleadoException;
import Excepciones.Empleados.DespedirEmpleadoException;
import Excepciones.Empleados.ObtenerSueldoEmpleadoException;
import Excepciones.Empleados.RegistrarEmpleadoException;
import Excepciones.Empleados.ValidacionEmpleadoException;
import Excepciones.ObtenerEmpleadoException;
import Excepciones.ObtenerEmpleadoPorCargoException;
import Excepciones.PersistenciaException;
import Excepciones.RegistrarNuevoEmpleadoException;
import Excepciones.ValidacionEmpleadoIdException;
import Excepciones.ValidarEmpleadoException;
import Excepciones.validarActualizacionSueldoDeCargoException;
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
    private void validarDatosEmpleadoDTO(EmpleadoDTO dto, boolean esNuevo) throws ValidacionEmpleadoException {
        if (dto == null) {
            throw new ValidacionEmpleadoException("Los datos del empleado (DTO) no pueden ser nulos.");
        }

        // Si es una actualización (esNuevo = false), el DTO podría o no llevar el ID.
        // La verificación del ID para actualización se hará en el método público del BO que recibe el ID.
        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
            throw new ValidacionEmpleadoException("El nombre del empleado no puede ser nulo o vacío.");
        }

        if (dto.getNombre().length() < 3 || dto.getNombre().length() > 50) {
            throw new ValidacionEmpleadoException("El nombre del empleado debe tener entre 3 y 50 caracteres.");
        }
        if (dto.getApellidoP() == null || dto.getApellidoP().trim().isEmpty()) {
            throw new ValidacionEmpleadoException("El apellido paterno no puede ser nulo o vacío.");
        }
        if (dto.getApellidoP().length() < 3 || dto.getApellidoP().length() > 50) {
            throw new ValidacionEmpleadoException("El apellido paterno debe tener entre 3 y 50 caracteres.");
        }
        if (dto.getApellidoM() == null || dto.getApellidoM().trim().isEmpty()) {
            throw new ValidacionEmpleadoException("El apellido materno no puede ser nulo o vacío.");
        }
        if (dto.getApellidoM().length() < 3 || dto.getApellidoM().length() > 50) {
            throw new ValidacionEmpleadoException("El apellido materno debe tener entre 3 y 50 caracteres.");
        }
        if (dto.getCorreoE() == null || dto.getCorreoE().trim().isEmpty()) {
            throw new ValidacionEmpleadoException("El correo electrónico no puede ser nulo o vacío.");
        }
        if (!esCorreoValido(dto.getCorreoE())) {
            throw new ValidacionEmpleadoException("El formato del correo electrónico es inválido.");
        }
        if (dto.getTelefono() == null || dto.getTelefono().trim().isEmpty()) {
            throw new ValidacionEmpleadoException("El teléfono no puede ser nulo o vacío.");
        }
        if (!esTelefonoValido(dto.getTelefono())) {
            throw new ValidacionEmpleadoException("El número de teléfono debe contener 10 dígitos numéricos.");
        }
        if (dto.getFechaNacimiento() == null) {
            throw new ValidacionEmpleadoException("La fecha de nacimiento es obligatoria.");
        }
        int edad = Period.between(dto.getFechaNacimiento().toLocalDate(), LocalDate.now()).getYears();
        if (edad <= 15) {
            throw new ValidacionEmpleadoException("El empleado debe ser mayor de 18 años.");
        }
        if (edad > 70) {
            throw new ValidacionEmpleadoException("La edad del empleado excede el límite razonable (70 años).");
        }
        if (dto.getCargo() == null) {
            throw new ValidacionEmpleadoException("El cargo del empleado es obligatorio.");
        }

        if (dto.getCalle() == null || dto.getCalle().trim().isEmpty()) {
            throw new ValidacionEmpleadoException("La calle en la dirección es obligatoria.");
        }
        if (dto.getColonia() == null || dto.getColonia().trim().isEmpty()) {
            throw new ValidacionEmpleadoException("La colonia en la dirección es obligatoria.");
        }
        if (dto.getNumExterior() == null || dto.getNumExterior().trim().isEmpty()) {
            throw new ValidacionEmpleadoException("El número exterior en la dirección es obligatorio.");
        }

        if (esNuevo == false) {
            // Solo validamos el sueldo si NO es un empleado nuevo (esNuevo == false),
            // lo que implicaría una actualización donde el sueldo podría estar presente en el DTO.
            // O si en el futuro, para registrar, decides que el sueldo debe venir y ser validado aquí.
            // POR AHORA, para el registro, el BO asigna y luego valida el sueldo.

            if (dto.getSueldo() <= 0) {
                throw new ValidacionEmpleadoException("El sueldo del empleado debe ser un valor positivo.");
            }
            if (dto.getSueldo() < 1000 || dto.getSueldo() > 200000) {
                throw new ValidacionEmpleadoException("El sueldo está fuera de los rangos permitidos (1,000 - 200,000).");
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
    
    @Override
    public long actualizarSueldoGeneralPorCargo(Cargo cargo, double nuevoSueldo) throws validarActualizacionSueldoDeCargoException {

        // 1. Validaciones en la capa de Manejo (como indicaste)
        if (cargo == null) {
            throw new validarActualizacionSueldoDeCargoException("El cargo no puede ser nulo para actualizar sueldos.");
        }
        if (nuevoSueldo <= 0) {
            throw new validarActualizacionSueldoDeCargoException("El nuevo sueldo debe ser un valor positivo.");
        }
        // Esta validación de rango es una regla de negocio, perfecta para esta capa.
        if (nuevoSueldo < 1000 || nuevoSueldo > 200000) {
            throw new validarActualizacionSueldoDeCargoException("El sueldo general para el cargo está fuera de los rangos permitidos (1,000 - 200,000).");
        }

        // 2. Llamada al método del BO
        try {

            return empleadoBO.actualizarSueldoGeneralPorCargo(cargo, nuevoSueldo);

        } catch (ValidacionEmpleadoException e) {
            // excepciones 
            throw new validarActualizacionSueldoDeCargoException("Error de validación en la lógica de negocio de empleados: " + e.getMessage());
        } catch (PersistenciaException e) {

            throw new validarActualizacionSueldoDeCargoException("Error al actualizar sueldos en la base de datos: " + e.getMessage(), e);
        } catch (Exception e) {
            // Captura cualquier otra excepción inesperada del BO
            throw new validarActualizacionSueldoDeCargoException("Error inesperado durante la actualización de sueldos: " + e.getMessage(), e);
        }
    }

    // Método helper para obtener el sueldo base según el cargo
    private double obtenerSueldoParaCargo(Cargo cargo) throws ObtenerSueldoEmpleadoException {
        if (cargo == null) {

            throw new IllegalArgumentException("El cargo no puede ser nulo para determinar el sueldo.");
        }
        switch (cargo) {
            case PALOMITERO:
                return 3000.00;
            case REVISION_BOLETOS:
                return 3000.00;
            case CAJERO:
                return 3500.00;
            case LIMPIEZA:
                return 3000.00;
            case SEGURIDAD:
                return 4000.00;
            case PROYECCIONISTA:
                return 3500.00;
            case TECNICO_SONIDO:
                return 4000.00;
            case MANTENIMIENTO:
                return 4500.00;
            case JEFE_PISO:
                return 6500.00;
            case JEFE_CAJA:
                return 5500.00;
            case JEFE_ALIMENTOS:
                return 6500.00;
            case GERENTE:
                return 12000.00;
            default:
                throw new ObtenerSueldoEmpleadoException("Cargo no reconocido para asignación de sueldo: " + cargo);
        }
    }

    private void validarEmpleadoID(String empleadoIdString) throws ValidacionEmpleadoIdException {

        if (empleadoIdString == null || empleadoIdString.trim().isEmpty()) {
            throw new ValidacionEmpleadoIdException("El ID del empleado no puede ser nulo o vacío.");
        }
        if (!ObjectId.isValid(empleadoIdString)) {
            throw new ValidacionEmpleadoIdException("El formato del ID del empleado ('" + empleadoIdString + "') no es válido.");
        }
    }

    @Override
    public EmpleadoDTO registrarNuevoEmpleado(EmpleadoDTO empleadoDTO) throws ValidacionEmpleadoException, RegistrarNuevoEmpleadoException, PersistenciaException {

        validarDatosEmpleadoDTO(empleadoDTO, true);

        // llamamos al BO que se ancarga de asignar sueldo 
        // 1. Asignar el sueldo basado en el cargo
        try {
            return empleadoBO.registrarNuevoEmpleado(empleadoDTO);
        } catch (ValidacionEmpleadoException | RegistrarEmpleadoException e) {
            throw new RegistrarNuevoEmpleadoException(e.getMessage());
        } catch (PersistenciaException e) {
            throw new RegistrarNuevoEmpleadoException("Error de persistencia al registrar empleado: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RegistrarNuevoEmpleadoException("Error inesperado al registrar empleado: " + e.getMessage(), e);
        }
    }

    @Override
    public EmpleadoDTO actualizarInformacionEmpleado(String empleadoId, EmpleadoDTO datosNuevosDTO) throws ValidacionEmpleadoIdException, ActualizacionEmpleadoException, ValidacionEmpleadoException {

        validarEmpleadoID(empleadoId); // llamamos al metodo privado de validacion de id
        validarDatosEmpleadoDTO(datosNuevosDTO, false); // false para indicar actualización
        // (se valida el sueldo si viene en el DTO)
        // El BO se encarga de verificar existencia, estado activo, unicidad de correo si cambia, etc.
        try {
            return empleadoBO.actualizarInformacionEmpleado(empleadoId, datosNuevosDTO);
        } catch (ValidacionEmpleadoException | ActualizarEmpleadoException e) {
            throw new ValidacionEmpleadoIdException(e.getMessage());
        } catch (PersistenciaException e) {
            throw new ActualizacionEmpleadoException("Error de persistencia al actualizar empleado: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new ActualizacionEmpleadoException("Error inesperado al actualizar empleado: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean despedirEmpleado(String empleadoIdString) throws DespedirEmpleadoException, ValidarEmpleadoException, ValidacionEmpleadoIdException {

        validarEmpleadoID(empleadoIdString);
        // El BO se encarga de verificar existencia y estado activo.
        try {
            return empleadoBO.despedirEmpleado(empleadoIdString);
        } catch (DespedirEmpleadoException e) {
            throw new ValidarEmpleadoException(e.getMessage()); // Puede ser error de validación (ej. ya inactivo)
        } catch (PersistenciaException e) {
            throw new DespedirEmpleadoException("Error de persistencia al despedir empleado: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new DespedirEmpleadoException("Error inesperado al despedir empleado: " + e.getMessage(), e);
        }
    }

    @Override
    public EmpleadoDTO buscarEmpleadoActivoPorId(String empleadoIdString) throws ValidarEmpleadoException, ObtenerEmpleadoException, ValidacionEmpleadoIdException {

        validarEmpleadoID(empleadoIdString);
        try {
            EmpleadoDTO dto = empleadoBO.buscarEmpleadoActivoPorId(empleadoIdString);
            if (dto == null) { // El BO podría devolver null si no lo encuentra o si el ID no es válido tras conversión.
                throw new ValidarEmpleadoException("No se encontró un empleado activo con el ID proporcionado o el ID es inválido.");
            }
            return dto;
        } catch (BuscarEmpleadoException e) {
            throw new ObtenerEmpleadoException("Error al buscar empleado: " + e.getMessage(), e);
        } catch (PersistenciaException e) {
            throw new ObtenerEmpleadoException("Error de persistencia al buscar empleado: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new ObtenerEmpleadoException("Error inesperado al buscar empleado: " + e.getMessage(), e);
        }
    }

    @Override
    public List<EmpleadoDTO> obtenerEmpleadosActivosPorCargo(Cargo cargo) throws ValidacionEmpleadoIdException, ObtenerEmpleadoPorCargoException {

        if (cargo == null) {
            throw new ObtenerEmpleadoPorCargoException("El cargo para la búsqueda no puede ser nulo.");
        }
        try {
            return empleadoBO.obtenerEmpleadosActivosPorCargo(cargo);
        } catch (ValidacionEmpleadoException e) { // Si el BO aún tuviera alguna validación para cargo.
            throw new ValidacionEmpleadoIdException(e.getMessage());
        } catch (PersistenciaException e) {
            throw new ObtenerEmpleadoPorCargoException("Error de persistencia al obtener empleados por cargo: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new ObtenerEmpleadoPorCargoException("Error inesperado al obtener empleados por cargo: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean actualizarCargoEmpleado(String empleadoIdString, Cargo nuevoCargo) throws ValidacionEmpleadoException, ActualizacionDeCargoException, ValidacionEmpleadoIdException {

        validarEmpleadoID(empleadoIdString);

        if (nuevoCargo == null) {
            throw new ValidacionEmpleadoException("El nuevo cargo no puede ser nulo.");
        }
        // El BO se encarga de verificar existencia y estado activo del empleado.
        try {
            return empleadoBO.actualizarCargoEmpleado(empleadoIdString, nuevoCargo);
        } catch (ValidacionEmpleadoException | ActualizarEmpleadoException e) {
            throw new ActualizacionDeCargoException(e.getMessage());
        } catch (PersistenciaException e) {
            throw new ActualizacionDeCargoException("Error de persistencia al actualizar cargo: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new ActualizacionDeCargoException("Error inesperado al actualizar cargo: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean actualizarSueldoEmpleadoIndividual(String empleadoIdString, double nuevoSueldo) throws ValidarEmpleadoException, ActualizacionSueldoException, ValidacionEmpleadoIdException {

        validarEmpleadoID(empleadoIdString);

        if (nuevoSueldo <= 0) {
            throw new ValidarEmpleadoException("El nuevo sueldo debe ser un valor positivo.");
        }
        if (nuevoSueldo < 1000 || nuevoSueldo > 200000) { // Rango de tu EmpleadoBO
            throw new ValidarEmpleadoException("El sueldo está fuera de los rangos permitidos (1,000 - 200,000).");
        }
        // El BO se encarga de verificar existencia y estado activo del empleado.
        try {
            return empleadoBO.actualizarSueldoEmpleado(empleadoIdString, nuevoSueldo); // Nombre del método en tu BO
        } catch (ValidacionEmpleadoException | ActualizarEmpleadoException e) {
            throw new ActualizacionSueldoException(e.getMessage());
        } catch (PersistenciaException e) {
            throw new ActualizacionSueldoException("Error de persistencia al actualizar sueldo: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new ActualizacionSueldoException("Error inesperado al actualizar sueldo: " + e.getMessage(), e);
        }
    }

    @Override
    public List<EmpleadoDTO> obtenerTodosLosEmpleadosActivos() throws ObtenerEmpleadoException {
        try {
            return empleadoBO.obtenerTodosLosEmpleadosActivos();
        } catch (PersistenciaException e) {
            throw new ObtenerEmpleadoException("Error de persistencia al obtener todos los empleados activos: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new ObtenerEmpleadoException("Error inesperado al obtener todos los empleados activos: " + e.getMessage(), e);
        }
    }
}
