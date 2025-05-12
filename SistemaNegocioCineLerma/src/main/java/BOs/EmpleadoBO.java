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

    

    private void validarEmpleadoDTO(EmpleadoDTO dto, boolean esNuevo) throws ValidacionEmpleadoException {
        if (dto == null) {
            throw new ValidacionEmpleadoException("Los datos del empleado (DTO) no pueden ser nulos.");
        }

        // Si es una actualización (esNuevo = false), el DTO podría o no llevar el ID.
        // La verificación del ID para actualización se hará en el método público del BO que recibe el ID.
        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
            throw new ValidacionEmpleadoException("El nombre del empleado no puede ser nulo o vacío.");
        }
        // ... (resto de tus validaciones usando dto.get...()) ...
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
    public EmpleadoDTO registrarNuevoEmpleado(EmpleadoDTO empleadoDTO) throws ValidacionEmpleadoException, RegistrarEmpleadoException, PersistenciaException {
        // El DTO viene SIN sueldo desde la UI en este caso
        // La validacion de sueldo en validarEmpleadoDTO se aplicará después de asignarlo

        if (empleadoDTO.getCargo() == null) {
            throw new ValidacionEmpleadoException("El cargo es obligatorio para registrar un empleado y asignar sueldo.");
        }

        // 1. Asignar el sueldo basado en el cargo
        double sueldoAsignado = 0;
        try {
            sueldoAsignado = obtenerSueldoParaCargo(empleadoDTO.getCargo());
        } catch (ObtenerSueldoEmpleadoException ex) {
            Logger.getLogger(EmpleadoBO.class.getName()).log(Level.SEVERE, null, ex);
        }
        empleadoDTO.setSueldo(sueldoAsignado); // Ahora el DTO tiene el sueldo

        // 2. Validar el DTO completo (que ahora incluye el sueldo asignado)
        validarEmpleadoDTO(empleadoDTO, true);

        // 3. Verificar unicidad de correo (como antes)
        if (empleadoDAO.existeEmpleadoConEseCorreo(empleadoDTO.getCorreoE()) == true) {
            throw new RegistrarEmpleadoException("El correo electrónico '" + empleadoDTO.getCorreoE() + "' ya está registrado.");
        }

        // 4. Convertir DTO a Entidad y registrar
        Empleado empleadoEntidad = empleadoMapper.convertirDTOAEntidad(empleadoDTO);
        // El constructor del DTO ya se encarga de activo y fechaRegistro, y estos pasan a la entidad.

        boolean exito = empleadoDAO.registrarEmpleado(empleadoEntidad);
        if (exito) {
            return empleadoMapper.convertirEntidadADTO(empleadoEntidad); // Devuelve DTO con ID y sueldo asignado
        } else {
            throw new RegistrarEmpleadoException("El empleado no pudo ser registrado por una razón desconocida.");
        }
    }

    @Override
    // La firma para actualizar es mejor si pasas el ID y el DTO con los nuevos datos
    public EmpleadoDTO actualizarInformacionEmpleado(ObjectId empleadoId, EmpleadoDTO datosNuevosDTO) throws ValidacionEmpleadoException, ActualizarEmpleadoException, PersistenciaException {
        if (empleadoId == null) {
            throw new ValidacionEmpleadoException("El ID del empleado es necesario para la actualización.");
        }
        validarEmpleadoDTO(datosNuevosDTO, false); // false porque es una actualización de datos existentes

        Empleado entidadExistente = empleadoDAO.obtenerEmpleadoPorIdInterno(empleadoId);
        if (entidadExistente == null) {
            throw new ActualizarEmpleadoException("No se encontró un empleado con el ID: " + empleadoId + ".");
        }
        if (!entidadExistente.isActivo()) {
            throw new ActualizarEmpleadoException("No se puede actualizar un empleado inactivo. ID: " + empleadoId);
        }

        // Verificar unicidad de correo si cambió
        if (!entidadExistente.getCorreoE().equalsIgnoreCase(datosNuevosDTO.getCorreoE().trim())) {
            if (empleadoDAO.consultarPorCorreoActivoExcluyendoId(datosNuevosDTO.getCorreoE().trim(), empleadoId) != null) {
                throw new ActualizarEmpleadoException("El nuevo correo electrónico '" + datosNuevosDTO.getCorreoE().trim() + "' ya está en uso por otro empleado.");
            }
        }

        // Actualizar la entidad existente con los datos del DTO
        empleadoMapper.actualizarEntidadConDTO(entidadExistente, datosNuevosDTO);

        boolean exito = empleadoDAO.actualizarEmpleado(entidadExistente);
        if (exito) {
            return  empleadoMapper.convertirEntidadADTO(entidadExistente);
        } else {
            throw new ActualizarEmpleadoException("El empleado con ID: " + empleadoId + " no pudo ser actualizado (DAO retornó false).");
        }
    }

    
    @Override
    public boolean despedirEmpleado(ObjectId empleadoId) throws DespedirEmpleadoException, PersistenciaException {

        if (empleadoId == null) {
            throw new DespedirEmpleadoException("El ID del empleado no puede ser nulo para la baja.");
        }
        Empleado existente = empleadoDAO.obtenerEmpleadoPorIdInterno(empleadoId);
        if (existente == null) {
            throw new DespedirEmpleadoException("No se encontró un empleado con el ID: " + empleadoId + ".");
        }
        if (!existente.isActivo()) {
            throw new DespedirEmpleadoException("El empleado con ID: " + empleadoId + " ya se encuentra inactivo.");
        }
        boolean exito = empleadoDAO.despedirEmpleado(empleadoId);
        if (!exito) {
            throw new DespedirEmpleadoException("No se pudo marcar como inactivo al empleado con ID: " + empleadoId + " (DAO retornó false).");
        }
        return true;
    }

    @Override
    public EmpleadoDTO buscarEmpleadoActivoPorId(ObjectId empleadoId) throws BuscarEmpleadoException, PersistenciaException {
        if (empleadoId == null) {
            throw new BuscarEmpleadoException("El ID del empleado para búsqueda no puede ser nulo.");
        }
        Empleado entidad = empleadoDAO.obtenerEmpleadoActivoPorId(empleadoId);
        return  empleadoMapper.convertirEntidadADTO(entidad); // Puede retornar null si la entidad es null
    }

    @Override
    public List<EmpleadoDTO> obtenerTodosLosEmpleadosActivos() throws PersistenciaException {
        List<Empleado> listaEntidades = empleadoDAO.obtenerTodosLosEmpleadosActivos();
        List<EmpleadoDTO> listaDTOs = new ArrayList<>();
        for (Empleado entidad : listaEntidades) {
            listaDTOs.add( empleadoMapper.convertirEntidadADTO(entidad));
        }
        return listaDTOs;
    }

    @Override
    public List<EmpleadoDTO> obtenerEmpleadosActivosPorCargo(Cargo cargo) throws ValidacionEmpleadoException, PersistenciaException {
        if (cargo == null) {
            throw new ValidacionEmpleadoException("El cargo para la búsqueda no puede ser nulo.");
        }
        List<Empleado> listaEntidades = empleadoDAO.obtenerEmpleadosActivosPorCargo(cargo);
        List<EmpleadoDTO> listaDTOs = new ArrayList<>();
        for (Empleado entidad : listaEntidades) {
            listaDTOs.add( empleadoMapper.convertirEntidadADTO(entidad));
        }
        return listaDTOs;
    }

    @Override
    public boolean actualizarCargoEmpleado(ObjectId empleadoId, Cargo nuevoCargo) throws ValidacionEmpleadoException, ActualizarEmpleadoException, PersistenciaException {

        if (empleadoId == null) {
            throw new ValidacionEmpleadoException("El ID del empleado no puede ser nulo para actualizar cargo.");
        }
        if (nuevoCargo == null) {
            throw new ValidacionEmpleadoException("El nuevo cargo no puede ser nulo.");
        }
        Empleado existente = empleadoDAO.obtenerEmpleadoPorIdInterno(empleadoId);
        if (existente == null) {
            throw new ActualizarEmpleadoException("No se encontró un empleado con el ID: " + empleadoId + ".");
        }
        if (!existente.isActivo()) {
            throw new ActualizarEmpleadoException("No se puede actualizar el cargo de un empleado inactivo. ID: " + empleadoId);
        }
        boolean exito = empleadoDAO.actualizarCargoEmpleado(empleadoId, nuevoCargo);
        if (!exito) {
            throw new ActualizarEmpleadoException("No se pudo actualizar el cargo del empleado con ID: " + empleadoId + " (DAO retornó false).");
        }
        return true;
    }

    @Override
    public boolean actualizarSueldoEmpleado(ObjectId empleadoId, double nuevoSueldo) throws ValidacionEmpleadoException, ActualizarEmpleadoException, PersistenciaException {
     
        if (empleadoId == null) {
            throw new ValidacionEmpleadoException("El ID del empleado no puede ser nulo para actualizar sueldo.");
        }
        if (nuevoSueldo <= 0) {
            throw new ValidacionEmpleadoException("El nuevo sueldo debe ser un valor positivo.");
        }
        if (nuevoSueldo < 1000 || nuevoSueldo > 200000) {
            throw new ValidacionEmpleadoException("El sueldo está fuera de los rangos permitidos (1,000 - 200,000).");
        }
        Empleado existente = empleadoDAO.obtenerEmpleadoPorIdInterno(empleadoId);
        if (existente == null) {
            throw new ActualizarEmpleadoException("No se encontró un empleado con el ID: " + empleadoId + ".");
        }
        if (!existente.isActivo()) {
            throw new ActualizarEmpleadoException("No se puede actualizar el sueldo de un empleado inactivo. ID: " + empleadoId);
        }
        boolean exito = empleadoDAO.actualizarSueldoIndividual(empleadoId, nuevoSueldo);
        if (!exito) {
            throw new ActualizarEmpleadoException("No se pudo actualizar el sueldo del empleado con ID: " + empleadoId + " (DAO retornó false).");
        }
        return true;
    }

    @Override
    public long actualizarSueldoGeneralPorCargo(Cargo cargo, double nuevoSueldo) throws ValidacionEmpleadoException, PersistenciaException {
        
        if (cargo == null) {
            throw new ValidacionEmpleadoException("El cargo no puede ser nulo para actualizar sueldos.");
        }
        if (nuevoSueldo <= 0) {
            throw new ValidacionEmpleadoException("El nuevo sueldo debe ser un valor positivo.");
        }
        if (nuevoSueldo < 1000 || nuevoSueldo > 200000) {
            throw new ValidacionEmpleadoException("El sueldo general para el cargo está fuera de los rangos permitidos (1,000 - 200,000).");
        }
        return empleadoDAO.actualizarSueldoPorCargo(cargo, nuevoSueldo);
    }
}
