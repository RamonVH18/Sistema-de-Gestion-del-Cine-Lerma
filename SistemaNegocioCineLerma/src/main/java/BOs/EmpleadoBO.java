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
import Excepciones.empleados.DAOActualizarSueldoException;
import Excepciones.empleados.DAODespedirEmpleadoException;
import Excepciones.empleados.DAOObtenerEmpleadoException;
import Excepciones.empleados.DAORegistrarEmpleadoException;
import Excepciones.empleados.DAOValidacionEmpleadoException;
import Interfaces.IEmpleadoBO;
import Interfaces.IEmpleadoDAO;
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
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author isaac
 */
public class EmpleadoBO implements IEmpleadoBO {

    private final IEmpleadoDAO empleadoDAO;
    private final EmpleadoMapper empleadoMapper;

    public EmpleadoBO() {
        this.empleadoDAO = EmpleadoDAO.getInstance();
        this.empleadoMapper = new EmpleadoMapper();
    }
    
    
    
    
    
    /**
     * Valida un ID de empleado en formato String (nulidad y vacío).
     * La validación del formato específico de ObjectId la realiza el DAO.
     *
     * @param empleadoIdString El ID del empleado como String.
     * @param contextoOperacion Descripción breve de la operación para mensajes de error.
     * @throws ValidacionEmpleadoException Si el ID es nulo o está vacío.
     */
    private void validarIdStringComoParametro(String empleadoIdString, String contextoOperacion) throws ValidacionEmpleadoException {
        if (empleadoIdString == null || empleadoIdString.trim().isEmpty()) {
            throw new ValidacionEmpleadoException("El ID del empleado para " + contextoOperacion + " no puede ser nulo o vacío.");
        }
        // La validación de si el string es un ObjectId válido ahora la hará el DAO
        // antes de intentar la conversión new ObjectId(empleadoIdString).
    }
    
    
    /**
     * Valida los datos contenidos en un EmpleadoDTO.
     *
     * @param dto El EmpleadoDTO a validar.
     * @param esNuevo true si es para un nuevo empleado (afecta validación de ID y sueldo), false si es para actualización.
     * @throws ValidacionEmpleadoException Si algún dato es inválido según las reglas de negocio.
     */
    private void validarEmpleadoDTO(EmpleadoDTO dto, boolean esNuevo) throws ValidacionEmpleadoException {
        if (dto == null) {
            throw new ValidacionEmpleadoException("Los datos del empleado (DTO) no pueden ser nulos.");
        }
        if (esNuevo && dto.getId() != null && !dto.getId().trim().isEmpty()) {
            throw new ValidacionEmpleadoException("Un nuevo empleado (DTO) no debe tener un ID predefinido.");
        }
        // Si es una actualización, el ID principal viene como parámetro del método del BO.
        // El ID en el DTO, si está presente, podría ser ignorado o validado contra el parámetro.
        // Por simplicidad, asumimos que el ID del DTO no es la fuente principal para operaciones de actualización por ID.

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

        LocalDateTime fechaNacDto = dto.getFechaNacimiento(); // Asumiendo DTO usa LocalDateTime
        int edad = Period.between(fechaNacDto.toLocalDate(), LocalDate.now()).getYears();

        if (edad < 18) {
            throw new ValidacionEmpleadoException("El empleado debe ser mayor de 18 años.");
        }
        if (edad > 70) {
            throw new ValidacionEmpleadoException("La edad del empleado excede el límite razonable (70 años).");
        }
        if (dto.getCargo() == null) {
            throw new ValidacionEmpleadoException("El cargo del empleado es obligatorio.");
        }

        if (dto.getSueldo() != null) {
            if (dto.getSueldo() <= 0) {
                throw new ValidacionEmpleadoException("El sueldo del empleado debe ser un valor positivo.");
            }
            if (dto.getSueldo() < 1000 || dto.getSueldo() > 200000) {
                throw new ValidacionEmpleadoException("El sueldo está fuera de los rangos permitidos (1,000 - 200,000).");
            }
        } else if (esNuevo) {
            // Esto está bien, el sueldo se asigna en registrarNuevoEmpleado ANTES de esta validación.
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
    
    
    /**
     * Valida el formato de una dirección de correo electrónico.
     * @param correo El correo a validar.
     * @return true si el formato es válido, false en caso contrario.
     */
    private boolean esCorreoValido(String correo) {
        if (correo == null) {
            return false;
        }
        String regex = "^[\\w-\\.\\+]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return Pattern.matches(regex, correo);
    }   
    
    
    /**
     * Valida el formato de un número de teléfono (10 dígitos numéricos).
     * @param telefono El teléfono a validar.
     * @return true si el formato es válido, false en caso contrario.
     */
    private boolean esTelefonoValido(String telefono) {
        if (telefono == null) {
            return false;
        }
        String regex = "^\\d{10}$";
        return Pattern.matches(regex, telefono);
    }
    
    
    
    /**
     * Obtiene el sueldo base para un cargo específico.
     * @param cargo El cargo del empleado.
     * @return El sueldo base correspondiente al cargo.
     * @throws ObtenerSueldoEmpleadoException Si el cargo es nulo o no reconocido.
     */
    private double obtenerSueldoParaCargo(Cargo cargo) throws ObtenerSueldoEmpleadoException {
        if (cargo == null) {
            throw new ObtenerSueldoEmpleadoException("El cargo no puede ser nulo para determinar el sueldo.");
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
    
    
    
    /**
     * Registra un nuevo empleado en el sistema después de realizar validaciones
     * y asignar un sueldo basado en el cargo.
     * El ID del empleado se genera automáticamente durante el proceso.
     *
     * @param empleadoDTO El objeto de transferencia de datos (DTO) con la información del empleado a registrar.
     * Se espera que el campo ID de este DTO sea nulo o vacío.
     * @return El EmpleadoDTO del empleado recién registrado, incluyendo su ID generado y el sueldo asignado.
     * @throws ValidacionEmpleadoException Si los datos del empleado en el DTO no cumplen con las reglas de negocio
     * (ej. campos vacíos, formato incorrecto, edad inválida, correo ya existente).
     * @throws RegistrarEmpleadoException Si ocurre un error durante el proceso de persistencia del nuevo empleado.
     * @throws BuscarEmpleadoException Si ocurre un error al consultar la base de datos para verificar la existencia
     * previa del correo electrónico.
     */
    @Override
    public EmpleadoDTO registrarNuevoEmpleado(EmpleadoDTO empleadoDTO)
            throws ValidacionEmpleadoException, RegistrarEmpleadoException, BuscarEmpleadoException {
        if (empleadoDTO.getId() != null && !empleadoDTO.getId().trim().isEmpty()) {
            throw new ValidacionEmpleadoException("Un nuevo empleado no debe tener un ID predefinido.");
        }
        if (empleadoDTO.getCargo() == null) {
            throw new ValidacionEmpleadoException("El cargo es obligatorio para registrar y asignar sueldo.");
        }
        double sueldoAsignado;
        try {
            sueldoAsignado = obtenerSueldoParaCargo(empleadoDTO.getCargo());
        } catch (ObtenerSueldoEmpleadoException ex) {
            throw new ValidacionEmpleadoException("No se pudo determinar el sueldo para el cargo: " + ex.getMessage(), ex);
        }
        empleadoDTO.setSueldo(sueldoAsignado); // Asignar sueldo antes de validar DTO

        validarEmpleadoDTO(empleadoDTO, true);

        try {
            // El DAO.existeEmpleadoConEseCorreo ahora espera String
            if (empleadoDAO.existeEmpleadoConEseCorreo(empleadoDTO.getCorreoE())) {
                throw new ValidacionEmpleadoException("El correo electrónico '" + empleadoDTO.getCorreoE() + "' ya está registrado.");
            }
        } catch (DAOObtenerEmpleadoException e) { // Si hay error de BD
            throw new BuscarEmpleadoException("Error al verificar la existencia del correo: " + e.getMessage(), e);
        } catch (DAOValidacionEmpleadoException e) { // Si el DAO valida el String del correo y falla (improbable si es solo string)
            throw new ValidacionEmpleadoException("Error de formato de correo en persistencia: " + e.getMessage(), e);
        }

        Empleado empleadoEntidad = empleadoMapper.convertirDTOAEntidad(empleadoDTO);
        // El mapper llama a entidad.setIdString(dto.getId()), que es null.
        // El DAO.registrarEmpleado(entidad) se encarga de generar el ObjectId en la entidad.

        try {
            boolean exito = empleadoDAO.registrarEmpleado(empleadoEntidad); // DAO toma la entidad
            if (exito) {
                return empleadoMapper.convertirEntidadADTO(empleadoEntidad);
            } else {
                throw new RegistrarEmpleadoException("El empleado no pudo ser registrado (DAO indicó fallo).");
            }
        } catch (DAORegistrarEmpleadoException e) {
            throw new RegistrarEmpleadoException("Error de persistencia al registrar empleado: " + e.getMessage(), e);
        }
    }
    
    
    /**
     * Actualiza la información general de un empleado existente.
     * No actualiza el cargo ni el sueldo, los cuales se manejan por métodos dedicados.
     * El empleado debe existir y estar activo para ser actualizado.
     * Si el correo electrónico se modifica, se verifica su unicidad.
     *
     * @param empleadoIdString El ID (como String) del empleado cuya información se va a actualizar.
     * @param datosNuevosDTO El DTO con los nuevos datos para el empleado. El ID en este DTO
     * se ignora o se usa para consistencia interna, el ID de operación es empleadoIdString.
     * @return El EmpleadoDTO con la información actualizada del empleado.
     * @throws ValidacionEmpleadoException Si el ID proporcionado es inválido, o si los datos en el DTO
     * no cumplen con las reglas de negocio (ej. nuevo correo ya en uso).
     * @throws ActualizarEmpleadoException Si el empleado está inactivo, o si ocurre un error durante el proceso
     * de persistencia de la actualización.
     * @throws BuscarEmpleadoException Si no se encuentra el empleado con el ID especificado, o si ocurre un
     * error al consultar la base de datos durante las verificaciones.
     */
    @Override
    public EmpleadoDTO actualizarInformacionEmpleado(String empleadoIdString, EmpleadoDTO datosNuevosDTO)
            throws ValidacionEmpleadoException, ActualizarEmpleadoException, BuscarEmpleadoException {
        validarIdStringComoParametro(empleadoIdString, "actualizar información");

        // El ID de la operación es empleadoIdString. El DTO no debería ser la fuente del ID aquí.
        // Seteamos el ID en el DTO para que validarEmpleadoDTO sea consistente si lo usa.
        datosNuevosDTO.setId(empleadoIdString);
        validarEmpleadoDTO(datosNuevosDTO, false);

        Empleado entidadExistente;
        try {
            // TU IEmpleadoDAO.obtenerEmpleadoPorIdInterno AHORA DEBE ACEPTAR STRING ID
            entidadExistente = empleadoDAO.obtenerEmpleadoPorIdInterno(empleadoIdString);
            if (entidadExistente == null) {
                throw new BuscarEmpleadoException("No se encontró un empleado con el ID: " + empleadoIdString + " para actualizar.");
            }
        } catch (DAOObtenerEmpleadoException e) {
            throw new BuscarEmpleadoException("Error al buscar empleado para actualizar: " + e.getMessage(), e);
        } catch (DAOValidacionEmpleadoException e) { // Captura si el DAO rechaza el formato del String ID
            throw new ValidacionEmpleadoException("ID para actualizar no válido según persistencia: " + e.getMessage(), e);
        }

        if (!entidadExistente.isActivo()) {
            throw new ActualizarEmpleadoException("No se puede actualizar un empleado inactivo. ID: " + empleadoIdString);
        }

        if (!entidadExistente.getCorreoE().equalsIgnoreCase(datosNuevosDTO.getCorreoE().trim())) {
            try {
                // TU IEmpleadoDAO.consultarPorCorreoActivoExcluyendoId AHORA DEBE ACEPTAR STRING IDs
                if (empleadoDAO.consultarPorCorreoActivoExcluyendoId(datosNuevosDTO.getCorreoE().trim(), empleadoIdString) != null) {
                    throw new ValidacionEmpleadoException("El nuevo correo electrónico '" + datosNuevosDTO.getCorreoE().trim() + "' ya está en uso.");
                }
            } catch (DAOObtenerEmpleadoException e) {
                throw new BuscarEmpleadoException("Error al verificar unicidad de correo para actualizar: " + e.getMessage(), e);
            } catch (DAOValidacionEmpleadoException e) {
                throw new ValidacionEmpleadoException("Error de validación de datos al verificar correo (persistencia): " + e.getMessage(), e);
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
        // El ID (ObjectId) de entidadExistente no cambia. Cargo y sueldo por métodos separados.

        try {
            // DAO.actualizarEmpleado toma la entidad (con su ObjectId interno)
            boolean exito = empleadoDAO.actualizarEmpleado(entidadExistente);
            if (exito) {
                return empleadoMapper.convertirEntidadADTO(entidadExistente);
            } else {
                throw new ActualizarEmpleadoException("El empleado con ID: " + empleadoIdString + " no pudo ser actualizado (DAO indicó fallo).");
            }
        } catch (DAOValidacionEmpleadoException e) { // Si el DAO valida la entidad y falla (ej. ObjectId interno es null)
            throw new ValidacionEmpleadoException("Error de validación en persistencia al actualizar empleado: " + e.getMessage(), e);
        } catch (DAOActualizarEmpleadoException e) {
            throw new ActualizarEmpleadoException("Error de persistencia al actualizar empleado: " + e.getMessage(), e);
        }
    }
    
    
    
    /**
     * Realiza el despido lógico de un empleado, marcándolo como inactivo.
     * El empleado debe existir y estar activo para ser despedido.
     *
     * @param empleadoIdString El ID (como String) del empleado a despedir.
     * @return true si el empleado fue marcado como inactivo exitosamente.
     * @throws DespedirEmpleadoException Si el empleado ya está inactivo, o si ocurre un error
     * durante el proceso de persistencia del despido.
     * @throws BuscarEmpleadoException Si no se encuentra el empleado con el ID especificado o si hay un
     * error al consultar la base de datos.
     * @throws ValidacionEmpleadoException Si el ID proporcionado es inválido.
     */
    @Override
    public boolean despedirEmpleado(String empleadoIdString)
            throws DespedirEmpleadoException, BuscarEmpleadoException, ValidacionEmpleadoException {
        validarIdStringComoParametro(empleadoIdString, "despedir");

        // Es buena práctica verificar si existe y está activo ANTES de intentar la operación
        Empleado existente;
        try {
            // TU IEmpleadoDAO.obtenerEmpleadoPorIdInterno AHORA DEBE ACEPTAR STRING ID
            existente = empleadoDAO.obtenerEmpleadoPorIdInterno(empleadoIdString);
            if (existente == null) {
                throw new BuscarEmpleadoException("No se encontró un empleado con el ID: " + empleadoIdString + " para despedir.");
            }
        } catch (DAOObtenerEmpleadoException e) {
            throw new BuscarEmpleadoException("Error al buscar empleado para despedir: " + e.getMessage(), e);
        } catch (DAOValidacionEmpleadoException e) {
            throw new ValidacionEmpleadoException("ID para despedir no válido según persistencia: " + e.getMessage(), e);
        }

        if (!existente.isActivo()) {
            throw new DespedirEmpleadoException("El empleado con ID: " + empleadoIdString + " ya se encuentra inactivo.");
        }

        try {
            // TU IEmpleadoDAO.despedirEmpleado AHORA DEBE ACEPTAR STRING ID
            boolean exito = empleadoDAO.despedirEmpleado(empleadoIdString);
            if (!exito) {
                throw new DespedirEmpleadoException("No se pudo marcar como inactivo al empleado con ID: " + empleadoIdString + " (DAO indicó fallo).");
            }
            return true;
        } catch (DAOValidacionEmpleadoException e) { // Si el DAO valida el String ID y falla
            throw new ValidacionEmpleadoException("Error de validación en persistencia al despedir: " + e.getMessage(), e);
        } catch (DAODespedirEmpleadoException e) {
            throw new DespedirEmpleadoException("Error de persistencia al despedir: " + e.getMessage(), e);
        }
    }
    
    
    
    /**
     * Busca un empleado activo por su ID.
     *
     * @param empleadoIdString El ID (como String) del empleado a buscar.
     * @return El EmpleadoDTO del empleado si se encuentra y está activo.
     * @throws BuscarEmpleadoException Si no se encuentra un empleado activo con el ID especificado,
     * o si ocurre un error al consultar la base de datos.
     * @throws ValidacionEmpleadoException Si el ID proporcionado es inválido.
     */
    @Override
    public EmpleadoDTO buscarEmpleadoActivoPorId(String empleadoIdString)
            throws BuscarEmpleadoException, ValidacionEmpleadoException {
        validarIdStringComoParametro(empleadoIdString, "buscar por ID");

        try {
            // TU IEmpleadoDAO.obtenerEmpleadoActivoPorId AHORA DEBE ACEPTAR STRING ID
            Empleado entidad = empleadoDAO.obtenerEmpleadoActivoPorId(empleadoIdString);
            if (entidad == null) {
                throw new BuscarEmpleadoException("No se encontró un empleado activo con el ID: " + empleadoIdString + ".");
            }
            return empleadoMapper.convertirEntidadADTO(entidad);
        } catch (DAOObtenerEmpleadoException e) {
            throw new BuscarEmpleadoException("Error al buscar empleado activo por ID: " + e.getMessage(), e);
        } catch (DAOValidacionEmpleadoException e) { // Si el DAO rechaza el formato del String ID
            throw new ValidacionEmpleadoException("ID para buscar no válido según persistencia: " + e.getMessage(), e);
        }
    }
    
    
    
    /**
     * Obtiene una lista de todos los empleados activos que pertenecen a un cargo específico.
     *
     * @param cargo El cargo por el cual filtrar los empleados.
     * @return Una lista de EmpleadoDTO de los empleados activos con el cargo especificado.
     * Devuelve una lista vacía si no se encuentran coincidencias.
     * @throws ValidacionEmpleadoException Si el cargo proporcionado es nulo o inválido según la persistencia.
     * @throws BuscarEmpleadoException Si ocurre un error al consultar la base de datos.
     */
    @Override
    public List<EmpleadoDTO> obtenerEmpleadosActivosPorCargo(Cargo cargo) throws ValidacionEmpleadoException, BuscarEmpleadoException { 

        // 1. Validación en la capa BO (ya la tenías y está bien)
        if (cargo == null) {
            throw new ValidacionEmpleadoException("El cargo no puede ser nulo para buscar empleados.");
        }

        try {
            // 2. Llamada al DAO
            List<Empleado> listaEntidades = empleadoDAO.obtenerEmpleadosActivosPorCargo(cargo);
            
            List<EmpleadoDTO> listaDTOs = new ArrayList<>();
            for (Empleado entidad : listaEntidades) {
                listaDTOs.add(empleadoMapper.convertirEntidadADTO(entidad));
            }
            return listaDTOs;

        } catch (DAOObtenerEmpleadoException e) {

            throw new BuscarEmpleadoException("Error al obtener empleados activos por cargo desde la persistencia: " + e.getMessage(), e);
        } catch (DAOValidacionEmpleadoException e) {
            throw new ValidacionEmpleadoException("Error de validación de datos en la capa de persistencia al buscar por cargo: " + e.getMessage(), e);
        }
    }
    
    /**
     * Actualiza el cargo de un empleado existente y activo.
     *
     * @param empleadoIdString El ID (como String) del empleado cuyo cargo se actualizará.
     * @param nuevoCargo El nuevo cargo a asignar.
     * @return true si la actualización del cargo fue exitosa.
     * @throws ValidacionEmpleadoException Si el ID o el nuevo cargo son inválidos, o si el empleado ya tiene ese cargo.
     * @throws ActualizarEmpleadoException Si el empleado está inactivo, o si ocurre un error durante la
     * persistencia de la actualización del cargo.
     * @throws BuscarEmpleadoException Si no se encuentra el empleado con el ID especificado o si hay un error
     * al consultar la base de datos.
     */
    @Override
    public boolean actualizarCargoEmpleado(String empleadoIdString, Cargo nuevoCargo) throws ValidacionEmpleadoException, ActualizarEmpleadoException, BuscarEmpleadoException {
        validarIdStringComoParametro(empleadoIdString, "actualizar cargo");
        if (nuevoCargo == null) {
            throw new ValidacionEmpleadoException("El nuevo cargo no puede ser nulo.");
        }

        EmpleadoDTO empleadoActualDTO;
        try {
            // Usamos el método del BO que ya hemos definido.
            empleadoActualDTO = this.buscarEmpleadoActivoPorId(empleadoIdString); // Lanza Buscar si no activo/existe
        } catch (BuscarEmpleadoException e) {
            throw new BuscarEmpleadoException("No se puede actualizar cargo, empleado no encontrado o inactivo: " + e.getMessage(), e);
        } // La ValidacionEmpleadoException del ID ya la maneja buscarEmpleadoActivoPorId.

        if (empleadoActualDTO.getCargo() == nuevoCargo) {
            throw new ValidacionEmpleadoException("El empleado ya tiene asignado el cargo de " + nuevoCargo + ".");
        }

        try {
            // TU IEmpleadoDAO.actualizarCargoEmpleado AHORA DEBE ACEPTAR STRING ID
            boolean exito = empleadoDAO.actualizarCargoEmpleado(empleadoIdString, nuevoCargo);
            if (!exito) {
                throw new ActualizarEmpleadoException("No se pudo actualizar el cargo del empleado con ID: " + empleadoIdString + " (DAO indicó fallo).");
            }
            return true;
        } catch (DAOValidacionEmpleadoException e) { // Si el DAO valida String ID o Cargo y falla
            throw new ValidacionEmpleadoException("Error de validación en persistencia al actualizar cargo: " + e.getMessage(), e);
        } catch (DAOActualizarEmpleadoException e) {
            throw new ActualizarEmpleadoException("Error de persistencia al actualizar cargo: " + e.getMessage(), e);
        }
    }
    
    
    /**
     * Actualiza el sueldo de un empleado individual existente y activo.
     *
     * @param empleadoIdString El ID (como String) del empleado cuyo sueldo se actualizará.
     * @param nuevoSueldo El nuevo sueldo a asignar. Debe ser un valor positivo dentro de los rangos permitidos.
     * @return true si la actualización del sueldo fue exitosa.
     * @throws ValidacionEmpleadoException Si el ID es inválido, o si el nuevo sueldo no cumple con las
     * reglas de negocio (ej. negativo, fuera de rango, igual al actual).
     * @throws ActualizarEmpleadoException Si el empleado está inactivo, o si ocurre un error durante la
     * persistencia de la actualización del sueldo.
     * @throws BuscarEmpleadoException Si no se encuentra el empleado con el ID especificado o si hay un error
     * al consultar la base de datos.
     */
    @Override
    public boolean actualizarSueldoEmpleado(String empleadoIdString, double nuevoSueldo)
            throws ValidacionEmpleadoException, ActualizarEmpleadoException, BuscarEmpleadoException {
        validarIdStringComoParametro(empleadoIdString, "actualizar sueldo");

        if (nuevoSueldo <= 0) {
            throw new ValidacionEmpleadoException("El nuevo sueldo debe ser un valor positivo.");
        }
        if (nuevoSueldo < 1000 || nuevoSueldo > 200000) {
            throw new ValidacionEmpleadoException("El nuevo sueldo está fuera de los rangos permitidos (1,000 - 200,000).");
        }

        EmpleadoDTO empleadoActualDTO;
        try {
            empleadoActualDTO = this.buscarEmpleadoActivoPorId(empleadoIdString);
        } catch (BuscarEmpleadoException e) {
            throw new BuscarEmpleadoException("No se puede actualizar sueldo, empleado no encontrado o inactivo: " + e.getMessage(), e);
        }

        if (empleadoActualDTO.getSueldo() != null && empleadoActualDTO.getSueldo() == nuevoSueldo) {
            throw new ValidacionEmpleadoException("El empleado ya tiene asignado ese sueldo.");
        }

        try {
            // TU IEmpleadoDAO.actualizarSueldoIndividual AHORA DEBE ACEPTAR STRING ID
            boolean exito = empleadoDAO.actualizarSueldoIndividual(empleadoIdString, nuevoSueldo);
            if (!exito) {
                throw new ActualizarEmpleadoException("No se pudo actualizar el sueldo del empleado con ID: " + empleadoIdString + " (DAO indicó fallo).");
            }
            return true;
        } catch (DAOValidacionEmpleadoException e) {
            throw new ValidacionEmpleadoException("Error de validación en persistencia al actualizar sueldo: " + e.getMessage(), e);
        } // Si tu DAO lanza esta específica
        catch (DAOActualizarEmpleadoException eGen) { // Si tu DAO lanza la genérica
            throw new ActualizarEmpleadoException("Error de persistencia al actualizar sueldo: " + eGen.getMessage(), eGen);
        }
    }
    
    
    /**
     * Actualiza el sueldo de todos los empleados activos que tienen un cargo específico.
     *
     * @param cargo El cargo de los empleados cuyo sueldo se actualizará.
     * @param nuevoSueldo El nuevo sueldo a asignar. Debe ser un valor positivo dentro de los rangos permitidos.
     * @return El número de empleados cuyos sueldos fueron actualizados.
     * @throws ValidacionEmpleadoException Si el cargo es nulo, o si el nuevo sueldo no cumple con las
     * reglas de negocio.
     * @throws ActualizarEmpleadoException Si ocurre un error durante la persistencia de la actualización masiva de sueldos.
     */
    @Override
    public long actualizarSueldoGeneralPorCargo(Cargo cargo, double nuevoSueldo)
            throws ValidacionEmpleadoException, ActualizarEmpleadoException {
        if (cargo == null) {
            throw new ValidacionEmpleadoException("El cargo no puede ser nulo para la actualización general de sueldos.");
        }
        if (nuevoSueldo <= 0) {
            throw new ValidacionEmpleadoException("El nuevo sueldo general debe ser un valor positivo.");
        }
        if (nuevoSueldo < 1000 || nuevoSueldo > 200000) {
            throw new ValidacionEmpleadoException("El nuevo sueldo general está fuera de los rangos permitidos (1,000 - 200,000).");
        }

        try {
            // Este método DAO ya tomaba Cargo.
            return empleadoDAO.actualizarSueldoPorCargo(cargo, nuevoSueldo);
        } catch (DAOValidacionEmpleadoException e) {
            throw new ValidacionEmpleadoException("Error de validación en persistencia al actualizar sueldos por cargo: " + e.getMessage(), e);
        } // Si tu DAO lanza esta específica
        catch (DAOActualizarEmpleadoException eGen) { // Si tu DAO lanza la genérica
            throw new ActualizarEmpleadoException("Error de persistencia al actualizar sueldos por cargo: " + eGen.getMessage(), eGen);
        }
    }
    
    
    /**
     * Obtiene una lista de todos los empleados que se encuentran activos.
     *
     * @return Una lista de EmpleadoDTO de todos los empleados activos.
     * Devuelve una lista vacía si no hay empleados activos.
     * @throws BuscarEmpleadoException Si ocurre un error al consultar la base de datos.
     */
    @Override
    public List<EmpleadoDTO> obtenerTodosLosEmpleadosActivos() throws BuscarEmpleadoException {
        try {
            // Este método DAO no toma ID.
            List<Empleado> listaEntidades = empleadoDAO.obtenerTodosLosEmpleadosActivos();
            return listaEntidades.stream()
                    .map(empleadoMapper::convertirEntidadADTO)
                    .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        } catch (DAOObtenerEmpleadoException e) {
            throw new BuscarEmpleadoException("Error al obtener todos los empleados activos: " + e.getMessage(), e);
        }
    }

}
