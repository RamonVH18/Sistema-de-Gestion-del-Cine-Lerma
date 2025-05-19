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
    public EmpleadoDTO registrarNuevoEmpleado(EmpleadoDTO empleadoDTO) throws ValidacionEmpleadoException, RegistrarEmpleadoException, PersistenciaException {
        
        if (empleadoDTO.getCargo() == null) { // Esta verificación aún es crucial aquí antes de obtenerSueldoParaCargo
            throw new ValidacionEmpleadoException("El cargo es obligatorio para registrar un empleado y asignar sueldo.");
        }
        
        double sueldoAsignado = 0;
        try {
            sueldoAsignado = obtenerSueldoParaCargo(empleadoDTO.getCargo());
        } catch (ObtenerSueldoEmpleadoException ex) { // Debes tener esta excepción definida
            // Logger.getLogger(EmpleadoBO.class.getName()).log(Level.SEVERE, null, ex); // Ya lo tienes
            throw new ValidacionEmpleadoException("No se pudo determinar el sueldo para el cargo: " + ex.getMessage(), ex);
        }
        empleadoDTO.setSueldo(sueldoAsignado);

        // 2. Validar el DTO completo con sueldo usando la lógica interna del BO.
        //    Esto puede incluir chequeos de rango de sueldo que ManejoEmpleados ya hizo,
        //    pero es una doble verificación o podría haber reglas más complejas aquí.
        validarEmpleadoDTO(empleadoDTO, true); // true para esNuevo

        // 3. Verificar unicidad de correo (lógica de negocio que requiere DAO)
        if (empleadoDAO.existeEmpleadoConEseCorreo(empleadoDTO.getCorreoE())) { 
            throw new RegistrarEmpleadoException("El correo electrónico '" + empleadoDTO.getCorreoE() + "' ya está registrado.");
        }

        Empleado empleadoEntidad = empleadoMapper.convertirDTOAEntidad(empleadoDTO);
        boolean exito = empleadoDAO.registrarEmpleado(empleadoEntidad);
        if (exito) {
            return empleadoMapper.convertirEntidadADTO(empleadoEntidad);
        } else {
            throw new RegistrarEmpleadoException("El empleado no pudo ser registrado.");
        }
    }

    @Override
    public EmpleadoDTO actualizarInformacionEmpleado(String empleadoId, EmpleadoDTO datosNuevosDTO) throws ValidacionEmpleadoException, ActualizarEmpleadoException, PersistenciaException {
       
        
        ObjectId empleadoIdObject = empleadoMapper.toObjectId(empleadoId); // convertimos a objectId
        
        
        validarEmpleadoDTO(datosNuevosDTO, false); // false porque es una actualización de datos existentes

        Empleado entidadExistente = empleadoDAO.obtenerEmpleadoPorIdInterno(empleadoIdObject); // usar el metodo del dao para ver si existe

        if (entidadExistente == null) {
            throw new ActualizarEmpleadoException("No se encontró un empleado con el ID: " + empleadoId + ".");
        }

        if (!entidadExistente.isActivo()) {
            throw new ActualizarEmpleadoException("No se puede actualizar un empleado inactivo. ID: " + empleadoId);
        }

        // Verificar unicidad de correo si cambió
        if (!entidadExistente.getCorreoE().equalsIgnoreCase(datosNuevosDTO.getCorreoE().trim())) {
            if (empleadoDAO.consultarPorCorreoActivoExcluyendoId(datosNuevosDTO.getCorreoE().trim(), empleadoIdObject) != null) {
                throw new ActualizarEmpleadoException("El nuevo correo electrónico '" + datosNuevosDTO.getCorreoE().trim() + "' ya está en uso por otro empleado.");
            }

            entidadExistente.setCorreoE(datosNuevosDTO.getCorreoE().trim()); // actualizar si es diferente y validado
        }

        // aplicamos en estos pq los otros campos ( Cargo, Sueldo ) se actualizan con otros metodos
        entidadExistente.setNombre(datosNuevosDTO.getNombre());
        entidadExistente.setApellidoP(datosNuevosDTO.getApellidoP());
        entidadExistente.setApellidoM(datosNuevosDTO.getApellidoM());
        entidadExistente.setCalle(datosNuevosDTO.getCalle());
        entidadExistente.setColonia(datosNuevosDTO.getColonia());
        entidadExistente.setNumExterior(datosNuevosDTO.getNumExterior());
        // Solo estos campos, ya que lo demas se usan otros metodos para actualiar

        boolean exito = empleadoDAO.actualizarEmpleado(entidadExistente); // DAO actualiza la entidad
        if (exito) {
            return empleadoMapper.convertirEntidadADTO(entidadExistente);
        } else {
            throw new ActualizarEmpleadoException("El empleado con ID: " + empleadoId + " no pudo ser actualizado.");
        }
    }

    @Override
    public boolean despedirEmpleado(String empleadoIdString) throws DespedirEmpleadoException, PersistenciaException {

       // validamos en el ManejoEmpleados
        ObjectId empleadoIdObject = empleadoMapper.toObjectId(empleadoIdString); // convertimos id a object

        Empleado existente = empleadoDAO.obtenerEmpleadoPorIdInterno(empleadoIdObject);
        
        if (existente == null) {
            throw new DespedirEmpleadoException("No se encontró un empleado con el ID: " + empleadoIdString + ".");
        }
        if (!existente.isActivo()) {
            throw new DespedirEmpleadoException("El empleado con ID: " + empleadoIdString + " ya se encuentra inactivo.");
        }

        boolean exito = empleadoDAO.despedirEmpleado(empleadoIdObject); // DAO usa ObjectId
        if (!exito) {
            throw new DespedirEmpleadoException("No se pudo marcar como inactivo al empleado con ID: " + empleadoIdString);
        }
        return true;
    }

    @Override
    public EmpleadoDTO buscarEmpleadoActivoPorId(String empleadoIdString) throws BuscarEmpleadoException, PersistenciaException {
       
        // validamos en el ManejoEmpleados
        
        ObjectId empleadoIdObject = empleadoMapper.toObjectId(empleadoIdString); // convertimos a Object 


        Empleado entidad = empleadoDAO.obtenerEmpleadoActivoPorId(empleadoIdObject); // obtenemos empleado por id
        return empleadoMapper.convertirEntidadADTO(entidad); // se devuelve
    }

    @Override
    public List<EmpleadoDTO> obtenerEmpleadosActivosPorCargo(Cargo cargo) throws ValidacionEmpleadoException, PersistenciaException {

       // validamos en el ManejoEmpleados
        List<Empleado> listaEntidades = empleadoDAO.obtenerEmpleadosActivosPorCargo(cargo);
        List<EmpleadoDTO> listaDTOs = new ArrayList<>();
        for (Empleado entidad : listaEntidades) {
            listaDTOs.add(empleadoMapper.convertirEntidadADTO(entidad));
        }
        return listaDTOs;
    }

    @Override
    public boolean actualizarCargoEmpleado(String empleadoIdString, Cargo nuevoCargo) throws ValidacionEmpleadoException, ActualizarEmpleadoException, PersistenciaException {

       
        // conversion a objectId
        ObjectId empleadoIdObject = empleadoMapper.toObjectId(empleadoIdString);
        
        // llamamos al dao para obtener el empleado
        Empleado existente = empleadoDAO.obtenerEmpleadoPorIdInterno(empleadoIdObject);

        if (existente == null) {
            throw new ActualizarEmpleadoException("No se encontró un empleado con el ID: " + empleadoIdObject + ".");
        }
        if (!existente.isActivo()) {
            throw new ActualizarEmpleadoException("No se puede actualizar el cargo de un empleado inactivo. ID: " + empleadoIdObject);
        }
        
        boolean exito = empleadoDAO.actualizarCargoEmpleado(empleadoIdObject, nuevoCargo);
        if (!exito) {
            throw new ActualizarEmpleadoException("No se pudo actualizar el cargo del empleado con ID: " + empleadoIdObject + " (DAO retornó false).");
        }
        return true;
    }

    @Override
    public boolean actualizarSueldoEmpleado(String empleadoIdString, double nuevoSueldo) throws ValidacionEmpleadoException, ActualizarEmpleadoException, PersistenciaException {

        // las validaciones se hacen el el manejoEmpleados

        ObjectId empleadoIdObject = empleadoMapper.toObjectId(empleadoIdString);// convertimos el id a ObjectId


        Empleado existente = empleadoDAO.obtenerEmpleadoPorIdInterno(empleadoIdObject);
        
        if (existente == null) {
            throw new ActualizarEmpleadoException("No se encontró un empleado con el ID: " + empleadoIdObject + ".");
        }
        
        if (!existente.isActivo()) {
            throw new ActualizarEmpleadoException("No se puede actualizar el sueldo de un empleado inactivo. ID: " + empleadoIdObject);
        }
        boolean exito = empleadoDAO.actualizarSueldoIndividual(empleadoIdObject, nuevoSueldo);
        if (!exito) {
            throw new ActualizarEmpleadoException("No se pudo actualizar el sueldo del empleado con ID: " + empleadoIdObject + " (DAO retornó false).");
        }
        return true;
    }

    @Override
    public long actualizarSueldoGeneralPorCargo(Cargo cargo, double nuevoSueldo) throws ValidacionEmpleadoException, PersistenciaException {

        // solo llamamos pq lo demas lo hace la gestion
        return empleadoDAO.actualizarSueldoPorCargo(cargo, nuevoSueldo);
        
    }

    @Override
    public List<EmpleadoDTO> obtenerTodosLosEmpleadosActivos() throws PersistenciaException {
        
        List<Empleado> listaEntidades = empleadoDAO.obtenerTodosLosEmpleadosActivos();
        List<EmpleadoDTO> listaDTOs = new ArrayList<>();
        
        if (listaEntidades != null) {
            for (Empleado entidad : listaEntidades) {
                listaDTOs.add(empleadoMapper.convertirEntidadADTO(entidad));
            }
        }
        return listaDTOs;
    }
}
