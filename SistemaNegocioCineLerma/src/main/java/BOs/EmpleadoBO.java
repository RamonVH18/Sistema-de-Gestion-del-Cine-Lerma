/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BOs;

import DAOs.EmpleadoDAO;
import DTOs.EmpleadoDTO;
import Excepciones.Empleados.ActualizarEmpleadoException;
import Excepciones.Empleados.BuscarEmpleadoException;
import Excepciones.Empleados.DespedirEmpleadoException;
import Excepciones.Empleados.EliminarEmpleadoException;
import Excepciones.Empleados.ObtenerSueldoEmpleadoException;
import Excepciones.Empleados.RegistrarEmpleadoException;
import Excepciones.Empleados.ValidacionEmpleadoException;
import Excepciones.PersistenciaException;
import Excepciones.empleados.DAOActualizarEmpleadoException;
import Excepciones.empleados.DAODespedirEmpleadoException;
import Excepciones.empleados.DAOObtenerEmpleadoException;
import Excepciones.empleados.DAORegistrarEmpleadoException;
import Excepciones.empleados.DAOValidacionEmpleadoException;
import Interfaces.IEmpleadoBO;
import Mappers.EmpleadoMapper;
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
public class EmpleadoBO implements IEmpleadoBO {

    private final EmpleadoDAO empleadoDAO;
    private final EmpleadoMapper empleadoMapper;

    //constructor
    public EmpleadoBO() {
        // obtenemos la instancia 
        this.empleadoDAO = EmpleadoDAO.getInstance();
        this.empleadoMapper = new EmpleadoMapper();
    }

    // ========================= METODOS PRIVADOS DE VALIDACION ===================================================
    // 
    private void validarEmpleadoDTO(EmpleadoDTO dto, boolean esNuevo) throws ValidacionEmpleadoException {
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
        if (edad < 18) {
            throw new ValidacionEmpleadoException("El empleado debe ser mayor de 18 años.");
        }
        if (edad > 70) {
            throw new ValidacionEmpleadoException("La edad del empleado excede el límite razonable (70 años).");
        }
        if (dto.getCargo() == null) {
            throw new ValidacionEmpleadoException("El cargo del empleado es obligatorio.");
        }

        if (dto.getSueldo() <= 0) {
            throw new ValidacionEmpleadoException("El sueldo del empleado debe ser un valor positivo.");
        }
        if (dto.getSueldo() < 1000 || dto.getSueldo() > 200000) {
            throw new ValidacionEmpleadoException("El sueldo está fuera de los rangos permitidos (1,000 - 200,000).");
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

    @Override
    public EmpleadoDTO registrarNuevoEmpleado(EmpleadoDTO empleadoDTO) throws ValidacionEmpleadoException, RegistrarEmpleadoException, BuscarEmpleadoException {
        if (empleadoDTO.getCargo() == null) {
            throw new ValidacionEmpleadoException("El cargo es obligatorio para registrar un empleado y asignar sueldo.");
        }
        double sueldoAsignado;
        try {
            sueldoAsignado = obtenerSueldoParaCargo(empleadoDTO.getCargo());
        } catch (ObtenerSueldoEmpleadoException ex) { // Excepción de lógica de negocio BO
            throw new ValidacionEmpleadoException("No se pudo determinar el sueldo para el cargo: " + ex.getMessage(), ex);
        }
        empleadoDTO.setSueldo(sueldoAsignado);

        validarEmpleadoDTO(empleadoDTO, true); // true para nuevo empleado

        try {
            if (empleadoDAO.existeEmpleadoConEseCorreo(empleadoDTO.getCorreoE())) { // Lanza DAOObtenerEmpleadoException
                throw new ValidacionEmpleadoException("El correo electrónico '" + empleadoDTO.getCorreoE() + "' ya está registrado.");
            }
        } catch (DAOObtenerEmpleadoException e) {
            throw new BuscarEmpleadoException("Error al verificar la existencia del correo: " + e.getMessage(), e);
        }

        Empleado empleadoEntidad = empleadoMapper.convertirDTOAEntidad(empleadoDTO);
        try {
            boolean exito = empleadoDAO.registrarEmpleado(empleadoEntidad); // Lanza DAORegistrarEmpleadoException
            if (exito) {
                return empleadoMapper.convertirEntidadADTO(empleadoEntidad);
            } else {
                // Este caso es menos probable si el DAO lanza excepción en fallo.
                throw new RegistrarEmpleadoException("El empleado no pudo ser registrado (DAO retornó false).");
            }
        } catch (DAORegistrarEmpleadoException e) {
            throw new RegistrarEmpleadoException("Error de persistencia al registrar empleado: " + e.getMessage(), e);
        }
    }

    @Override
    public EmpleadoDTO actualizarInformacionEmpleado(String empleadoId, EmpleadoDTO datosNuevosDTO)  throws ValidacionEmpleadoException, ActualizarEmpleadoException, BuscarEmpleadoException {
        if (empleadoId == null || empleadoId.trim().isEmpty()) {
            throw new ValidacionEmpleadoException("El ID del empleado es requerido para la actualización.");
        }
        ObjectId empleadoIdObject;
        try {
            empleadoIdObject = empleadoMapper.toObjectId(empleadoId);
        } catch (IllegalArgumentException e) {
            throw new ValidacionEmpleadoException("Formato de ID de empleado inválido: " + empleadoId, e);
        }

        // No validar cargo ni sueldo aquí, ya que se actualizan por métodos específicos.
        // Adaptar validarEmpleadoDTO para que no exija cargo/sueldo en actualización, o validar campos específicos.
        // Por ahora, validarEmpleadoDTO(datosNuevosDTO, false) los validará si están presentes.
        validarEmpleadoDTO(datosNuevosDTO, false);

        Empleado entidadExistente;
        try {
            entidadExistente = empleadoDAO.obtenerEmpleadoPorIdInterno(empleadoIdObject); // Lanza DAOObtenerEmpleadoException
            if (entidadExistente == null) {
                throw new BuscarEmpleadoException("No se encontró un empleado con el ID: " + empleadoId + " para actualizar.");
            }
        } catch (DAOObtenerEmpleadoException e) {
            throw new BuscarEmpleadoException("Error al buscar empleado para actualizar: " + e.getMessage(), e);
        }

        if (!entidadExistente.isActivo()) {
            throw new ActualizarEmpleadoException("No se puede actualizar un empleado inactivo. ID: " + empleadoId);
        }

        // Verificar unicidad de correo si cambió
        if (!entidadExistente.getCorreoE().equalsIgnoreCase(datosNuevosDTO.getCorreoE().trim())) {
            try {
                if (empleadoDAO.consultarPorCorreoActivoExcluyendoId(datosNuevosDTO.getCorreoE().trim(), empleadoIdObject) != null) { // Lanza DAOObtenerEmpleadoException
                    throw new ValidacionEmpleadoException("El nuevo correo electrónico '" + datosNuevosDTO.getCorreoE().trim() + "' ya está en uso por otro empleado.");
                }
            } catch (DAOObtenerEmpleadoException e) {
                throw new BuscarEmpleadoException("Error al verificar unicidad de correo para actualizar: " + e.getMessage(), e);
            }
            entidadExistente.setCorreoE(datosNuevosDTO.getCorreoE().trim());
        }

        entidadExistente.setNombre(datosNuevosDTO.getNombre());
        entidadExistente.setApellidoP(datosNuevosDTO.getApellidoP());
        entidadExistente.setApellidoM(datosNuevosDTO.getApellidoM());
        entidadExistente.setTelefono(datosNuevosDTO.getTelefono());
        entidadExistente.setFechaNacimiento(datosNuevosDTO.getFechaNacimiento());
        entidadExistente.setCalle(datosNuevosDTO.getCalle());
        entidadExistente.setColonia(datosNuevosDTO.getColonia());
        entidadExistente.setNumExterior(datosNuevosDTO.getNumExterior());

        try {
            boolean exito = empleadoDAO.actualizarEmpleado(entidadExistente); // Lanza DAOActualizarEmpleadoException, DAOValidacionEmpleadoException
            if (exito) {
                return empleadoMapper.convertirEntidadADTO(entidadExistente);
            } else {
                throw new ActualizarEmpleadoException("El empleado con ID: " + empleadoId + " no pudo ser actualizado (DAO retornó false).");
            }
        } catch (DAOValidacionEmpleadoException e) {
            throw new ValidacionEmpleadoException("Error de validación en persistencia al actualizar empleado: " + e.getMessage(), e);
        } catch (DAOActualizarEmpleadoException e) {
            throw new ActualizarEmpleadoException("Error de persistencia al actualizar empleado: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean despedirEmpleado(String empleadoIdString) throws DespedirEmpleadoException, BuscarEmpleadoException, ValidacionEmpleadoException {
        if (empleadoIdString == null || empleadoIdString.trim().isEmpty()) {
            throw new ValidacionEmpleadoException("El ID del empleado es requerido para el despido.");
        }
        ObjectId empleadoIdObject;
        try {
            empleadoIdObject = empleadoMapper.toObjectId(empleadoIdString);
        } catch (IllegalArgumentException e) {
            throw new ValidacionEmpleadoException("Formato de ID de empleado inválido: " + empleadoIdString, e);
        }

        Empleado existente;
        try {
            existente = empleadoDAO.obtenerEmpleadoPorIdInterno(empleadoIdObject); // Lanza DAOObtenerEmpleadoException
            if (existente == null) {
                throw new BuscarEmpleadoException("No se encontró un empleado con el ID: " + empleadoIdString + " para despedir.");
            }
        } catch (DAOObtenerEmpleadoException e) {
            throw new BuscarEmpleadoException("Error al buscar empleado para despedir: " + e.getMessage(), e);
        }

        if (!existente.isActivo()) {
            throw new DespedirEmpleadoException("El empleado con ID: " + empleadoIdString + " ya se encuentra inactivo.");
        }

        try {
            boolean exito = empleadoDAO.despedirEmpleado(empleadoIdObject); // Lanza DAODespedirEmpleadoException, DAOValidacionEmpleadoException
            if (!exito) {
                // Si el DAO retorna false pero no encontró al empleado activo para modificar (ya despedido por otra transacción, por ejemplo)
                // Los chequeos previos deberían cubrir la mayoría de los casos.
                // Este throw se daría si el DAO, por alguna razón, no pudo modificar y no lanzó excepción.
                throw new DespedirEmpleadoException("No se pudo marcar como inactivo al empleado con ID: " + empleadoIdString + " (DAO retornó false).");
            }
            return true;
        } catch (DAOValidacionEmpleadoException e) {
            throw new ValidacionEmpleadoException("Error de validación en persistencia al despedir empleado: " + e.getMessage(), e);
        } catch (DAODespedirEmpleadoException e) {
            throw new DespedirEmpleadoException("Error de persistencia al despedir empleado: " + e.getMessage(), e);
        }
    }

    @Override
    public EmpleadoDTO buscarEmpleadoActivoPorId(String empleadoIdString)   throws BuscarEmpleadoException, ValidacionEmpleadoException {
        if (empleadoIdString == null || empleadoIdString.trim().isEmpty()) {
            throw new ValidacionEmpleadoException("El ID del empleado es requerido para la búsqueda.");
        }
        ObjectId empleadoIdObject;
        try {
            empleadoIdObject = empleadoMapper.toObjectId(empleadoIdString);
        } catch (IllegalArgumentException e) {
            throw new ValidacionEmpleadoException("Formato de ID de empleado inválido: " + empleadoIdString, e);
        }

        try {
            Empleado entidad = empleadoDAO.obtenerEmpleadoActivoPorId(empleadoIdObject); // Lanza DAOObtenerEmpleadoException
            if (entidad == null) {
                // Este caso podría ser cubierto por la excepción del DAO si find().first() es null y luego se intenta acceder.
                // O, si el DAO devuelve null sin error, el BO lo maneja.
                throw new BuscarEmpleadoException("No se encontró un empleado activo con el ID: " + empleadoIdString + ".");
            }
            return empleadoMapper.convertirEntidadADTO(entidad);
        } catch (DAOObtenerEmpleadoException e) {
            throw new BuscarEmpleadoException("Error al buscar empleado activo por ID: " + e.getMessage(), e);
        }
    }

    @Override
    public List<EmpleadoDTO> obtenerEmpleadosActivosPorCargo(Cargo cargo)  throws ValidacionEmpleadoException, BuscarEmpleadoException {
        if (cargo == null) {
            throw new ValidacionEmpleadoException("El cargo es requerido para obtener empleados.");
        }
        try {
            List<Empleado> listaEntidades = empleadoDAO.obtenerEmpleadosActivosPorCargo(cargo); // Lanza DAOObtenerEmpleadoException
            List<EmpleadoDTO> listaDTOs = new ArrayList<>();
            for (Empleado entidad : listaEntidades) {
                listaDTOs.add(empleadoMapper.convertirEntidadADTO(entidad));
            }
            return listaDTOs;
        } catch (DAOObtenerEmpleadoException e) {
            throw new BuscarEmpleadoException("Error al obtener empleados activos por cargo: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean actualizarCargoEmpleado(String empleadoIdString, Cargo nuevoCargo) throws ValidacionEmpleadoException, ActualizarEmpleadoException, BuscarEmpleadoException {
        if (empleadoIdString == null || empleadoIdString.trim().isEmpty()) {
            throw new ValidacionEmpleadoException("El ID del empleado es requerido para actualizar el cargo.");
        }
        if (nuevoCargo == null) {
            throw new ValidacionEmpleadoException("El nuevo cargo no puede ser nulo.");
        }
        ObjectId empleadoIdObject;
        try {
            empleadoIdObject = empleadoMapper.toObjectId(empleadoIdString);
        } catch (IllegalArgumentException e) {
            throw new ValidacionEmpleadoException("Formato de ID de empleado inválido: " + empleadoIdString, e);
        }

        Empleado existente;
        try {
            existente = empleadoDAO.obtenerEmpleadoPorIdInterno(empleadoIdObject); // Lanza DAOObtenerEmpleadoException
            if (existente == null) {
                throw new BuscarEmpleadoException("No se encontró un empleado con el ID: " + empleadoIdObject + " para actualizar cargo.");
            }
        } catch (DAOObtenerEmpleadoException e) {
            throw new BuscarEmpleadoException("Error al buscar empleado para actualizar cargo: " + e.getMessage(), e);
        }

        if (!existente.isActivo()) {
            throw new ActualizarEmpleadoException("No se puede actualizar el cargo de un empleado inactivo. ID: " + empleadoIdObject);
        }
        if (existente.getCargo() == nuevoCargo) {
            throw new ValidacionEmpleadoException("El empleado ya tiene asignado el cargo de " + nuevoCargo + ".");
        }

        try {
            boolean exito = empleadoDAO.actualizarCargoEmpleado(empleadoIdObject, nuevoCargo); // Lanza DAOActualizarEmpleadoException, DAOValidacionEmpleadoException
            if (!exito) {
                throw new ActualizarEmpleadoException("No se pudo actualizar el cargo del empleado con ID: " + empleadoIdObject + " (DAO retornó false).");
            }
            return true;
        } catch (DAOValidacionEmpleadoException e) {
            throw new ValidacionEmpleadoException("Error de validación en persistencia al actualizar cargo: " + e.getMessage(), e);
        } catch (DAOActualizarEmpleadoException e) { // Asumiendo que actualizarCargoEmpleado lanza esta genérica
            throw new ActualizarEmpleadoException("Error de persistencia al actualizar cargo: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean actualizarSueldoEmpleado(String empleadoIdString, double nuevoSueldo) throws ValidacionEmpleadoException, ActualizarEmpleadoException, BuscarEmpleadoException {
        if (empleadoIdString == null || empleadoIdString.trim().isEmpty()) {
            throw new ValidacionEmpleadoException("El ID del empleado es requerido para actualizar el sueldo.");
        }
        // Validaciones de negocio para el nuevo sueldo
        if (nuevoSueldo <= 0) {
            throw new ValidacionEmpleadoException("El nuevo sueldo debe ser un valor positivo.");
        }
        if (nuevoSueldo < 1000 || nuevoSueldo > 200000) { // Mantener rangos consistentes
            throw new ValidacionEmpleadoException("El nuevo sueldo está fuera de los rangos permitidos (1,000 - 200,000).");
        }

        ObjectId empleadoIdObject;
        try {
            empleadoIdObject = empleadoMapper.toObjectId(empleadoIdString);
        } catch (IllegalArgumentException e) {
            throw new ValidacionEmpleadoException("Formato de ID de empleado inválido: " + empleadoIdString, e);
        }

        Empleado existente;
        try {
            existente = empleadoDAO.obtenerEmpleadoPorIdInterno(empleadoIdObject); // Lanza DAOObtenerEmpleadoException
            if (existente == null) {
                throw new BuscarEmpleadoException("No se encontró un empleado con el ID: " + empleadoIdObject + " para actualizar sueldo.");
            }
        } catch (DAOObtenerEmpleadoException e) {
            throw new BuscarEmpleadoException("Error al buscar empleado para actualizar sueldo: " + e.getMessage(), e);
        }

        if (!existente.isActivo()) {
            throw new ActualizarEmpleadoException("No se puede actualizar el sueldo de un empleado inactivo. ID: " + empleadoIdObject);
        }
        if (existente.getSueldo() == nuevoSueldo) {
            throw new ValidacionEmpleadoException("El empleado ya tiene asignado ese sueldo.");
        }

        try {
            // El DAO tiene actualizarSueldoIndividual que lanza DAOActualizarEmpleadoException y DAOValidacionEmpleadoException
            // Pero tú tienes DAOActualizarSueldoException, ajusta según corresponda en el DAO y aquí.
            // Asumiendo que el método del DAO se llama actualizarSueldoIndividual y lanza DAOActualizarEmpleadoException.
            boolean exito = empleadoDAO.actualizarSueldoIndividual(empleadoIdObject, nuevoSueldo);
            if (!exito) {
                throw new ActualizarEmpleadoException("No se pudo actualizar el sueldo del empleado con ID: " + empleadoIdObject + " (DAO retornó false).");
            }
            return true;
        } catch (DAOValidacionEmpleadoException e) {
            throw new ValidacionEmpleadoException("Error de validación en persistencia al actualizar sueldo: " + e.getMessage(), e);
        } catch (DAOActualizarEmpleadoException e) { // O DAOActualizarSueldoException si tu DAO la usa para este método
            throw new ActualizarEmpleadoException("Error de persistencia al actualizar sueldo: " + e.getMessage(), e);
        }
    }

    @Override
    public long actualizarSueldoGeneralPorCargo(Cargo cargo, double nuevoSueldo)  throws ValidacionEmpleadoException, ActualizarEmpleadoException {
        if (cargo == null) {
            throw new ValidacionEmpleadoException("El cargo es requerido para la actualización general de sueldos.");
        }
        if (nuevoSueldo <= 0) {
            throw new ValidacionEmpleadoException("El nuevo sueldo general debe ser un valor positivo.");
        }
        if (nuevoSueldo < 1000 || nuevoSueldo > 200000) {
            throw new ValidacionEmpleadoException("El nuevo sueldo general está fuera de los rangos permitidos (1,000 - 200,000).");
        }

        try {
            // El DAO tiene actualizarSueldoPorCargo que lanza DAOActualizarEmpleadoException y DAOValidacionEmpleadoException
            // Considera si deberías usar DAOActualizarSueldoException aquí también.
            return empleadoDAO.actualizarSueldoPorCargo(cargo, nuevoSueldo);
        } catch (DAOValidacionEmpleadoException e) {
            throw new ValidacionEmpleadoException("Error de validación en persistencia al actualizar sueldos por cargo: " + e.getMessage(), e);
        } catch (DAOActualizarEmpleadoException e) { // O DAOActualizarSueldoException
            throw new ActualizarEmpleadoException("Error de persistencia al actualizar sueldos por cargo: " + e.getMessage(), e);
        }
    }

    @Override
    public List<EmpleadoDTO> obtenerTodosLosEmpleadosActivos() throws BuscarEmpleadoException {
        try {
            List<Empleado> listaEntidades = empleadoDAO.obtenerTodosLosEmpleadosActivos(); // Lanza DAOObtenerEmpleadoException
            List<EmpleadoDTO> listaDTOs = new ArrayList<>();
            for (Empleado entidad : listaEntidades) {
                listaDTOs.add(empleadoMapper.convertirEntidadADTO(entidad));
            }
            return listaDTOs;
        } catch (DAOObtenerEmpleadoException e) {
            throw new BuscarEmpleadoException("Error al obtener todos los empleados activos: " + e.getMessage(), e);
        }
    }
}
