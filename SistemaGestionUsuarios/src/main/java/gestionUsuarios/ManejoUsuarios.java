/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestionUsuarios;

import BOs.AdministradorBO;
import BOs.ClienteBO;
import BOs.UsuarioBO;
import DTOs.AdministradorDTO;
import DTOs.ClienteDTO;
import DTOs.CompraDTO;
import DTOs.UsuarioDTO;
import Excepciones.ActualizarUsuarioException;
import Excepciones.CargarHistorialException;
import Excepciones.EncontrarUsuarioException;
import Excepciones.RegistrarUsuarioException;
import Excepciones.Usuarios.ActualizarClienteExceptionBO;
import Excepciones.Usuarios.ActualizarUsuarioExceptionBO;
import Excepciones.Usuarios.CargarHistorialExceptionBO;
import Excepciones.Usuarios.EncontrarAdminExceptionBO;
import Excepciones.Usuarios.EncontrarClienteExceptionBO;
import Excepciones.Usuarios.ObtenerUsuariosExceptionBO;
import Excepciones.Usuarios.RegistrarAdminExceptionBO;
import Excepciones.Usuarios.RegistrarClienteExceptionBO;
import Excepciones.ValidarUsuarioException;
import Excepciones.usuarios.ObtenerUsuariosException;
import Interfaces.IAdministradorBO;
import Interfaces.IClienteBO;
import Interfaces.IUsuarioBO;
import enums.EstadoUsuario;
import enums.Rol;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author sonic
 */
/**
 * Clase de subsistema que gestiona las operaciones relacionadas con usuarios,
 * administradores y clientes.
 */
public class ManejoUsuarios implements IManejoUsuarios {

    private IUsuarioBO usuarioBO = UsuarioBO.getInstanceBO(); // Instancia de usuarioBO
    private IAdministradorBO adminBO = AdministradorBO.getInstanceBO(); //instancia de administradorBO
    private IClienteBO clienteBO = ClienteBO.getInstanceBO(); //instancia de clienteBO

    private static ManejoUsuarios instanceManejoUsuarios; //instancia unica

    private ManejoUsuarios() {
    }

    /**
     * Método para obtener la instancia única de ManejoUsuarios.
     *
     * @return la instancia de ManejoUsuarios
     */
    public static ManejoUsuarios getInstance() {
        if (instanceManejoUsuarios == null) { // para la primera vez que se llama
            instanceManejoUsuarios = new ManejoUsuarios();
        }
        return instanceManejoUsuarios;
    }

    /////////////////////////////////
    //-------------------------------------------------METODOS DE USUARIOS ---------------------------------------------------------------------------------------
    /**
     * Muestra la lista de usuarios registrados en el sistema.
     *
     * Este método utiliza el metodo de usaurioBO para obtener la lista de
     * usuarios en el sistema como usuarioDTO, no tiene verificaciones extra Si
     * ocurre un error durante el proceso, se lanza una excepción
     * ObtenerUsuariosException.
     *
     * @return una lista de objetos UsuarioDTO que representan a los usuarios
     * registrados
     * @throws ObtenerUsuariosException si ocurre un error al obtener la lista
     * de usuarios
     */
    @Override
    public List<UsuarioDTO> mostrarListaUsuarios() throws ObtenerUsuariosException {
        try {
            return usuarioBO.mostrarListaUsuariosBO();

        } catch (ObtenerUsuariosExceptionBO e) {
            throw new ObtenerUsuariosException("No se pudo obtener el cliente: " + e.getMessage(), e);
        }
    }

    /**
     * Valida las credenciales de un usuario, busca a un usuario segun las
     * credenciales recibidas para verificar si el usuario con dichas
     * credenciales existe o no.
     *
     * Este método verifica que el nombre de usuario y la contraseña no sean
     * nulos ni vacíos, y luego intenta obtener el usuario llamando al metodo de
     * obtenerUsuario de UsuarioBO. Si el usuario no existe o está bloqueado, se
     * lanzan excepciones adecuadas. Si ocurre un error durante el proceso, se
     * lanza una excepción ValidarUsuarioException.
     *
     * @param nombreUsuario el nombre de usuario a validar
     * @param contrasena la contraseña del usuario a validar
     * @return el objeto UsuarioDTO que representa al usuario validado
     * @throws ValidarUsuarioException si las credenciales son inválidas o el
     * usuario está bloqueado
     */
    @Override
    public UsuarioDTO validarUsuario(String nombreUsuario, String contrasena) throws ValidarUsuarioException {
        try {
            // Verifica que el nombre de usuario y la contraseña no sean nulos ni vacíos
            if (nombreUsuario == null || nombreUsuario == "" || nombreUsuario.trim().isEmpty() || contrasena == null || contrasena == "" || contrasena.trim().isEmpty()) {
                throw new ValidarUsuarioException("Por favor ingrese los datos de inicio de sesion correctamente");
            }

            // Llama al metodo obtenerUsuario de usuarioBO para encontrar el usuario con las credenciales correspondientes y verificar si existe
            UsuarioDTO usuarioEncontrado = usuarioBO.obtenerUsuarioBO(nombreUsuario, contrasena);

            if (usuarioEncontrado == null) {
                throw new ValidarUsuarioException("El usuario no existe");
            }

            if (usuarioEncontrado.getEstado() == EstadoUsuario.BLOQUEADO) {
                throw new ValidarUsuarioException("El usuario esta bloqueado");
            }

            // Devuelve el usuario validado
            return usuarioEncontrado;

        } catch (ObtenerUsuariosExceptionBO e) {
            throw new ValidarUsuarioException("El administrador no es valido: " + e.getMessage());
        }
    }

    /**
     * Intenta bloquear a un usuario en el sistema.
     *
     * Este método realiza varias validaciones antes de proceder con el bloqueo:
     *
     * Verifica que el usuarioDTO no sea nulo. Verifica que el usuario no tenga
     * el rol de administrador, pues no se pueden bloquear administradores
     * Verifica que el usuario no se encuentre ya en un estado diferente a
     * ACTIVO, en dicho caso no se podria bloquear
     *
     * Si todas las validaciones son exitosas, llama al metodo de usuarioBO para
     * bloquear el usuario
     *
     * @param usuario El objeto UsuarioDTO que representa al usuario que se
     * desea bloquear. No debe ser nulo.
     * @return true si el usuario fue bloqueado exitosamente
     * @throws ActualizarUsuarioException Si ocurre un error durante el proceso
     * de validación o si el BO lanza una excepción al intentar bloquear al
     * usuario. La excepción contendrá un mensaje descriptivo del error.
     */
    @Override
    public Boolean bloquearUsuario(UsuarioDTO usuario) throws ActualizarUsuarioException {
        try {

            //Validar que el usuario no sea nulo
            if (usuario == null) {
                throw new ActualizarUsuarioException("El usuario no se pudo encontrar");
            }

            //Se verifica que el rol del usuario sea un cliente, si no no se puede bloquear
            if (usuario.getRol() == Rol.ADMINISTRADOR) {
                throw new ActualizarUsuarioException("No se pueden bloquear administradores");
            }

            //Se verifica que el usuario que se quiere bloquear este activo, no se puede bloquear a un usuario ya bloqueado
            if (usuario.getEstado() != EstadoUsuario.ACTIVO) {
                throw new ActualizarUsuarioException("El usuario seleccionado ya esta bloqueado");
            }

            // Si todas las validaciones pasan, se procede a llamar a la capa de lógica de negocio (BO) para bloquear al usuario.
            return usuarioBO.bloquearUsuarioBO(usuario);

        } catch (ActualizarUsuarioExceptionBO e) {
            throw new ActualizarUsuarioException("No se pudo bloquear el usuario: " + e.getMessage(), e);
        }
    }

    /**
     * Intenta desbloquear a un usuario en el sistema.
     *
     * Este método realiza varias validaciones antes de proceder con el bloqueo:
     *
     * Verifica que el usuarioDTO no sea nulo. Verifica que el usuario no este
     * activo, el usuario debe estar previamente en estado BLOQUEADO pra poder
     * ser desbloqueado
     *
     * Si todas las validaciones son exitosas, llama al metodo de usuarioBO para
     * desbloquear el usuario
     *
     * @param usuario El objeto UsuarioDTO que representa al usuario que se
     * desea desbloquear. No debe ser nulo.
     * @return true si el usuario fue desbloqueado exitosamente
     * @throws ActualizarUsuarioException Si ocurre un error durante el proceso
     * de validación o si el BO lanza una excepción al intentar desbloquear al
     * usuario. La excepción contendrá un mensaje descriptivo del error.
     */
    @Override
    public Boolean desbloquearUsuario(UsuarioDTO usuario) throws ActualizarUsuarioException {
        try {

            if (usuario == null) {
                throw new ActualizarUsuarioException("El usuario no se pudo encontrar");
            }

            if (usuario.getEstado() == EstadoUsuario.ACTIVO) {
                throw new ActualizarUsuarioException("El usuario seleccionado no esta bloqueado");
            }

            return usuarioBO.desbloquearUsuarioBO(usuario);

        } catch (ActualizarUsuarioExceptionBO e) {
            throw new ActualizarUsuarioException("No se pudo desbloquear el cliente: " + e.getMessage(), e);
        }
    }

    //METODOS QUE SIRVEN PARA FILTRAR LA LISTA DE USUARIOS
    /**
     * Obtiene una lista filtrada de usuarios según diversos criterios. Este
     * método permite filtrar usuarios por su estado, rol, rango de fechas y
     * coincidencia parcial con un nombre de usuario.
     *
     * @param estado El estado del usuario (ACTIVO, INACTIVO, etc.) por el que
     * filtrar, o null para no aplicar este filtro
     * @param rol El rol del usuario (ADMIN, CLIENTE, etc.) por el que filtrar,
     * o null para no aplicar este filtro
     * @param fechaInicio Fecha mínima de registro para incluir usuarios, o null
     * para no establecer fecha mínima
     * @param fechaFin Fecha máxima de registro para incluir usuarios, o null
     * para no establecer fecha máxima
     * @param usuario Cadena que debe estar contenida en el nombre de usuario, o
     * null/cadena vacía para no filtrar por nombre
     * @return Lista de usuarios (como DTOs) que cumplen con todos los criterios
     * de filtrado aplicados
     * @throws ObtenerUsuariosException Si ocurre un error durante el proceso de
     * filtrado
     */
    @Override
    public List<UsuarioDTO> mostrarListaUsuariosFiltrada(EstadoUsuario estado, Rol rol, LocalDateTime fechaInicio, LocalDateTime fechaFin, String usuario) throws ObtenerUsuariosException {
        try {

            //llama al metodo de usuarioBO para obtener la lista de usuarioDTO filtrada por todos o pos algunos filtros
            List<UsuarioDTO> listaFiltrada = usuarioBO.mostrarListaUsuariosFiltradaBO(estado, rol, fechaInicio, fechaFin, usuario);

            return listaFiltrada;

        } catch (ObtenerUsuariosExceptionBO e) {
            throw new ObtenerUsuariosException("La lista no se pudo filtrar: " + e.getMessage());
        }
    }

    ///////////////////////////////////////////
    //-------------------------------------------------METODOS DE CLIENTES ---------------------------------------------------------------------------------------
    /**
     * Método auxiliar para validar los datos de un usuario. Verifica que todos
     * los campos obligatorios del usuario estén presentes y válidos. Se utiliza
     * durante los procesos de registro y actualización de clientes o
     * administradores.
     *
     * La validación incluye: Presencia de datos básicos obligatorios (nombre,
     * apellido, etc.) Validación del formato del número telefónico (10 dígitos)
     * Validación del formato del correo electrónico Verificación de la edad
     * mínima del usuario
     *
     * @param usuario El objeto UsuarioDTO cuyos datos se van a validar
     * @throws ValidarUsuarioException Si alguno de los datos no cumple con los
     * criterios de validación. El mensaje de la excepción especifica qué
     * validación ha fallado.
     */
    private void validarDatosUsuario(UsuarioDTO usuario) throws ValidarUsuarioException {

        //validar que el nombre del usuario no sea nulo ni vacio
        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
            throw new ValidarUsuarioException("El nombre del cliente es obligatorio");
        }

        //validar que el apellido paterno no sea nulo ni vacio
        if (usuario.getApellidoPaterno() == null || usuario.getApellidoPaterno().trim().isEmpty()) {
            throw new ValidarUsuarioException("El apellido paterno del cliente es obligatorio");
        }
        //se decicio no agregar una validacion para el apellido materno de los usuarios

        //Validacion para que el nombre de usuario no pueda ser nulo o vacio
        if (usuario.getNombreUsuario() == null || usuario.getNombreUsuario().trim().isEmpty()) {
            throw new ValidarUsuarioException("El nombre de usuario es obligatorio");
        }

        //validar que la contraseña del usuario no pueda ser nula o vacia
        if (usuario.getContraseña() == null || usuario.getContraseña().trim().isEmpty()) {
            throw new ValidarUsuarioException("La contraseña es obligatoria");
        }

        //validar que el correo no sea nulo o vacio
        if (usuario.getCorreoElectronico() == null || usuario.getCorreoElectronico().trim().isEmpty()) {
            throw new ValidarUsuarioException("El correo electrónico es obligatorio");
        }

        //validar que el numero de telefono no sea nulo o vacio
        if (usuario.getTelefono() == null || usuario.getTelefono().trim().isEmpty()) {
            throw new ValidarUsuarioException("El telefono es obligatorio");
        }

        //validar que el telefono no tenga una longitud mayor a 10 digitos
        if (usuario.getTelefono().length() > 10) {
            throw new ValidarUsuarioException("El telefono no puede contener mas de 10 numeros");
        }

        //validar que el numero de telefono del cliente no tenga ni mas ni menos de 10 digitos
        if (usuario.getTelefono().length() != 10) {
            throw new ValidarUsuarioException("El telefono debe tener una longitud de 10 numeros");
        }

        //validar el formato del numero de telefono, este debe contener solo numeros del 0 al 9
        if (!usuario.getTelefono().matches("^[0-9]+$")) {
            throw new ValidarUsuarioException("El formato del telefono ingresado no es valido");
        }

        //validar el formato del correo electronico, debe tener nombre, un @ y el dominio + .com
        if (!usuario.getCorreoElectronico().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new ValidarUsuarioException("El formato del correo electrónico no es valido");
        }

        //validar que la fecha de nacimiento no sea nula
        if (usuario.getFechaNacimiento() == null) {
            throw new ValidarUsuarioException("La fecha de nacimiento es obligatoria");
        }

        //validar que la fecha de nacimiento corresponda a un usuario con una edad de minimo 13 años o mayor
        if (usuario.getFechaNacimiento().isAfter(LocalDateTime.now().minusYears(13))) {
            throw new ValidarUsuarioException("El usuario debe tener al menos 13 años");
        }

    }

    /**
     * Método auxiliar para validar los datos específicos de un cliente.
     * Verifica que todos los campos obligatorios relacionados con la dirección
     * del cliente estén presentes y tengan formatos válidos.
     *
     * La validación incluye:
     *
     * Presencia del Código Postal y validación de su formato Presencia de la
     * calle en la dirección Presencia del número de domicilio y validación de
     * su formato adecuado
     *
     *
     * Este método complementa la validación general de usuario y debe llamarse
     * después de validar los datos comunes a todos los usuarios.
     *
     * @param cliente El objeto ClienteDTO cuyos datos específicos se van a
     * validar
     * @throws ValidarUsuarioException Si alguno de los datos no cumple con los
     * criterios de validación. El mensaje de la excepción especifica qué
     * validación ha fallado.
     * @throws EncontrarUsuarioException Si ocurre un error al verificar la
     * información del cliente
     */
    private void validarDatosCliente(ClienteDTO cliente) throws ValidarUsuarioException, EncontrarUsuarioException {

        //validar que el codigo postal del cliente no sea nulo ni vacio
        if (cliente.getCP() == null || cliente.getCP().trim().isEmpty()) {
            throw new ValidarUsuarioException("El CP del cliente es obligatorio");
        }

        //validar que el codigo postal del cliente solamente se componga de numeros del 0 al 9
        if (!cliente.getCP().matches("^[0-9]+$")) {
            throw new ValidarUsuarioException("El CP del cliente no es valido");
        }

        //validar que la calle del cliente no sea nula ni vacia
        if (cliente.getCalle() == null || cliente.getCalle().trim().isEmpty()) {
            throw new ValidarUsuarioException("La calle del cliente es obligatoria");
        }

        //validar que el numero de domicilio del cliente no sea nulo ni vacio
        if (cliente.getNumero() == null || cliente.getNumero().trim().isEmpty()) {
            throw new ValidarUsuarioException("El numero de domicilio del cliente es obligatorio");
        }

        //validar que el formato del numero de domicilio del cliente sea correcto (solo numeros del 0 al 9)
        if (!cliente.getNumero().matches("^[0-9]+$")) {
            throw new ValidarUsuarioException("El numero de domicilio del cliente no es valido");
        }

    }

    /**
     * Método auxiliar para validar los datos específicos de un administrador.
     * Verifica que todos los campos obligatorios propios de un administrador
     * estén presentes y tengan formatos válidos.
     *
     * Actualmente, solo se valida que el rfc no sea nulo y su formato
     *
     * Este método complementa la validación general de usuario y debe llamarse
     * después de validar los datos comunes a todos los usuarios.
     *
     * @param admin El objeto AdministradorDTO cuyos datos específicos se van a
     * validar
     * @throws ValidarUsuarioException Si el RFC está ausente o vacío. El
     * mensaje de la excepción especifica el motivo del fallo.
     */
    private void validarDatosAdministrador(AdministradorDTO admin) throws ValidarUsuarioException {

        if (admin.getRFC() == null || admin.getRFC().trim().isEmpty()) {
            throw new ValidarUsuarioException("El RFC del administrador es obligatorio");
        }
        
        //validar que la fecha de nacimiento corresponda a un usuario con una edad de minimo 18 años o mayor
        if (admin.getFechaNacimiento().isAfter(LocalDateTime.now().minusYears(18))) {
            throw new ValidarUsuarioException("El usuario debe tener al menos 18 años");
        }

        //valida que el RFC tenga un formato valido: 
        //4 letras 
        //6 digitos que representan la fecha de nacimiento
        //3 caracteres cualquiera
        if (!admin.getRFC().trim().toUpperCase().matches("^[A-ZÑ&]{4}\\d{6}.{3}$")) {
            throw new ValidarUsuarioException("El RFC ingresado no es válido. Debe cumplir con el siguiente formato:\n"
                    + "- 4 letras.\n"
                    + "- 6 dígitos correspondientes a la fecha de nacimiento.\n"
                    + "- 3 caracteres.\n"
                    + "Ejemplo: GODE561231GR8"
            );
        }

    }

    /**
     * Registra un nuevo cliente en el sistema. Este método realiza las
     * siguientes operaciones:
     *
     * Verifica que el objeto cliente no sea nulo Valida los datos generales del
     * usuario llamando al metodo validarDatosUsuario Valida los datos
     * específicos del cliente utilizando el metodo validarDatosCliente si se
     * pasan las validaciones correctamente se llama al metodo de
     * registrarCliente en clienteDAO para realizar la operacion
     *
     * El proceso integra validaciones tanto de datos generales de usuario como
     * específicos de cliente antes de realizar el registro efectivo en el
     * sistema.
     *
     * @param cliente El objeto ClienteDTO con la información del cliente a
     * registrar
     * @return ClienteDTO con la información del cliente registrado, incluyendo
     * posibles datos generados durante el proceso de registro
     * @throws RegistrarUsuarioException Si ocurre algún error durante el
     * proceso de registro, ya sea por datos inválidos, cliente duplicado u
     * otros problemas en la capa de negocio. El mensaje de la excepción
     * contiene detalles específicos sobre el error ocurrido.
     */
    @Override
    public ClienteDTO registrarCliente(ClienteDTO cliente) throws RegistrarUsuarioException {
        try {

            if (cliente == null) {
                throw new RegistrarUsuarioException("El cliente no puede ser null");
            }

            //llama al metodo de validarDatosUsuario con el cliente que se quiere registrar para realizar todas las validaciones
            //necesarias en los datos del cliente antes de registrarlo
            validarDatosUsuario(cliente);

            //llama al metodo de validarDatosCliente con el cliente que se quiere registrar para realizar todas las validaciones
            //necesarias en los datos de los atributos del cliente antes de registrarlo
            validarDatosCliente(cliente);

            return clienteBO.registrarClienteBO(cliente); //llamar a clienteBO para registar el cliente ya validado

        } catch (RegistrarClienteExceptionBO e) {
            throw new RegistrarUsuarioException("No se pudo registrar el cliente: " + e.getMessage());
        } catch (ValidarUsuarioException e) {
            throw new RegistrarUsuarioException("Error al registrar el cliente: " + e.getMessage());
        } catch (EncontrarUsuarioException e) {
            throw new RegistrarUsuarioException("Error al registrar el cliente: " + e.getMessage());
        }
    }

    /**
     * Actualiza la información de un cliente existente en el sistema. Este
     * método realiza las siguientes operaciones:
     *
     * Valida los datos generales del usuario llamando al metodo
     * validarDatosUsuario Valida los datos específicos del cliente utilizando
     * el metodo validarDatosCliente si se pasan las validaciones correctamente
     * se llama al metodo de registrarCliente en clienteDAO para realizar la
     * operacion
     *
     * A diferencia del método de registro, este método asume que el objeto
     * cliente no es nulo y que contiene un identificador válido de un cliente
     * existente en el sistema.
     *
     * @param cliente El objeto ClienteDTO con la información actualizada del
     * cliente. Debe incluir el identificador del cliente a actualizar.
     * @return ClienteDTO con la información actualizada del cliente, tal como
     * ha quedado almacenada en el sistema después de la actualización
     * @throws ActualizarUsuarioException Si ocurre algún error durante el
     * proceso de actualización, ya sea por datos inválidos, cliente no
     * encontrado u otros problemas en la capa de negocio. El mensaje de la
     * excepción contiene detalles específicos sobre el error ocurrido.
     */
    @Override
    public ClienteDTO actualizarCliente(ClienteDTO cliente) throws ActualizarUsuarioException {
        try {

            //llama al metodo de validarDatosUsuario con el cliente que se quiere registrar para realizar todas las validaciones
            //necesarias en los datos del cliente antes de actualizarlo
            validarDatosUsuario(cliente);

            //llama al metodo de validarDatosCliente con el cliente que se quiere registrar para realizar todas las validaciones
            //necesarias en los datos de los atributos del cliente antes de actualizarlo
            validarDatosCliente(cliente);

            return clienteBO.actualizarClienteBO(cliente); //devolver el cliente actualizado

        } catch (ValidarUsuarioException e) {
            throw new ActualizarUsuarioException("No se pudo actualizar el cliente: " + e.getMessage());
        } catch (ActualizarClienteExceptionBO e) {
            throw new ActualizarUsuarioException("No se pudo actualizar el cliente: " + e.getMessage(), e);
        } catch (EncontrarUsuarioException e) {
            throw new ActualizarUsuarioException("No se pudo actualizar el cliente: " + e.getMessage());
        }
    }

    /**
     * Busca y recupera la información de un cliente a partir de sus
     * credenciales de acceso. Este método se utiliza principalmente para
     * procesos de autenticación y login. Realiza las siguientes operaciones:
     *
     * Valida que el nombre de usuario y la contraseña no sean nulos ni vacíos,
     * Verifica que se haya encontrado un cliente con las credenciales
     * proporcionadas
     *
     * @param nombreUsuario El nombre de usuario del cliente a buscar
     * @param contrasena La contraseña asociada al nombre de usuario
     * @return ClienteDTO con toda la información del cliente encontrado
     * @throws EncontrarUsuarioException Si ocurre alguno de los siguientes
     * casos: El nombre de usuario o la contraseña son nulos o vacíos No existe
     * un cliente con las credenciales proporcionadas Ocurre un error en la capa
     * de negocio durante la búsqueda
     */
    @Override
    public ClienteDTO obtenerCliente(String nombreUsuario, String contrasena) throws EncontrarUsuarioException {
        try {
            // Validación de parámetros de entrada (credenciales)
            if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
                throw new EncontrarUsuarioException("El nombre de usuario es obligatorio");
            }

            if (contrasena == null || contrasena.trim().isEmpty()) {
                throw new EncontrarUsuarioException("La contrasena es obligatoria");
            }

            //llama al metodo de clienteBO para obtener el cliente segun las credenciales ingresadas por el usuario
            ClienteDTO clienteEncontrado = clienteBO.obtenerClienteBO(nombreUsuario, contrasena);

            //si no se obtiene un cliente al buscarlo entonces se lanza una excepcion
            if (clienteEncontrado == null) {
                throw new EncontrarUsuarioException("No se encontro el administrador");
            }

            return clienteEncontrado; //se devuelve el cliente encontrado

        } catch (EncontrarClienteExceptionBO e) {
            throw new EncontrarUsuarioException("No se pudo obtener el cliente: " + e.getMessage(), e);
        }
    }

    /**
     * Recupera el historial completo de compras realizadas por un cliente
     * específico.
     *
     * el metodo llama al metodo de cargarHistorialCompras de un cliente
     * especifico de clienteBO
     *
     * El objeto cliente proporcionado no es nulo El cliente contiene un
     * identificador válido en el sistema
     *
     *
     * Si no existen compras asociadas al cliente, se devuelve una lista vacía
     * en lugar de null.
     *
     * @param cliente El objeto ClienteDTO que identifica al cliente cuyo
     * historial se desea consultar. Debe contener al menos el identificador
     * único del cliente.
     * @return Lista de objetos CompraDTO representando todas las compras
     * realizadas por el cliente. Retorna una lista vacía si el cliente no tiene
     * compras registradas.
     * @throws CargarHistorialException Si ocurre algún error durante la
     * recuperación del historial, ya sea porque el cliente no existe, por
     * problemas de conexión con el almacenamiento de datos, o cualquier otra
     * excepción generada en la capa de negocio. El mensaje de la excepción y su
     * causa proporcionan detalles sobre el error específico.
     */
    @Override
    public List<CompraDTO> cargarHistorialCompras(ClienteDTO cliente) throws CargarHistorialException {
        try {
            return clienteBO.cargarHistorialComprasBO(cliente);

        } catch (CargarHistorialExceptionBO e) {
            throw new CargarHistorialException("No se pudo obtener el cliente: " + e.getMessage(), e);
        }
    }

    //////////////////////////////////////////////
    //-------------------------------------------------METODOS DE ADMINISTRADOR ---------------------------------------------------------------------------------------
    /**
     * Registra un nuevo administrador en el sistema. Este método realiza las
     * siguientes operaciones:
     *
     * Verifica que el objeto administrador no sea nulo Valida los datos generales del
     * usuario llamando al metodo validarDatosUsuario Valida los datos
     * específicos del administrador utilizando el metodo validarDatosAdministrador si se
     * pasan las validaciones correctamente se llama al metodo de
     * registrarAdministrador en adminstradorDAO para realizar la operacion
     *
     * El proceso integra validaciones tanto de datos generales de usuario como
     * específicos de cliente antes de realizar el registro efectivo en el
     * sistema.
     *
     * @param administrador El objeto AdministradorDTO con la información del administrador a
     * registrar
     * @return AdministradorDTO con la información del cliente registrado, incluyendo
     * posibles datos generados durante el proceso de registro
     * @throws RegistrarUsuarioException Si ocurre algún error durante el
     * proceso de registro, ya sea por datos inválidos, cliente duplicado u
     * otros problemas en la capa de negocio. El mensaje de la excepción
     * contiene detalles específicos sobre el error ocurrido.
     */
    @Override
    public AdministradorDTO registrarAdministrador(AdministradorDTO administrador) throws RegistrarUsuarioException {
        try {

            // Validación que no sea nulo el administrador que se quiere registrar
            if (administrador == null) {
                throw new RegistrarUsuarioException("El administrador no puede ser null");
            }

            //llama al metodo de validarDatosUsuario con el administrador que se quiere registrar para realizar todas las validaciones
            //necesarias en los datos del administrador antes de registrarlo
            validarDatosUsuario(administrador);
            
            //llama al metodo de validarDatosAdministrador con el administrador que se quiere registrar para realizar todas las validaciones
            //necesarias en los datos de los atributos del administrador antes de actualizarlo
            validarDatosAdministrador(administrador);

            //Se llama al metodo de adminDAO para realizar la operacion
            return adminBO.registrarAdministradorBO(administrador);

        } catch (ValidarUsuarioException e) {
            throw new RegistrarUsuarioException("El administrador ingresado no cumple con la siguiente validacion: " + e.getMessage());
        } catch (RegistrarAdminExceptionBO e) {
            throw new RegistrarUsuarioException("No se pudo registrar el administrador (subsistema): " + e.getMessage(), e);
        }
    }

    /**
     * Busca y recupera la información de un administrador a partir de sus
     * credenciales de acceso. Este método se utiliza principalmente para
     * procesos de autenticación y login. Realiza las siguientes operaciones:
     *
     * Valida que el nombre de usuario y la contraseña no sean nulos ni vacíos,
     * Verifica que se haya encontrado un administrador con las credenciales
     * proporcionadas
     *
     * @param nombreUsuario El nombre de usuario del cliente a buscar
     * @param contrasena La contraseña asociada al nombre de usuario
     * @return AdministradorDTO con toda la información del cliente encontrado
     * @throws EncontrarUsuarioException Si ocurre alguno de los siguientes
     * casos: El nombre de usuario o la contraseña son nulos o vacíos No existe
     * un cliente con las credenciales proporcionadas Ocurre un error en la capa
     * de negocio durante la búsqueda
     */
    @Override
    public AdministradorDTO obtenerAdministrador(String nombreUsuario, String contrasena) throws EncontrarUsuarioException {

        try {
            // Validación de parámetros de entrada (credenciales)
            if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
                throw new EncontrarUsuarioException("El nombre de usuario es obligatorio");
            }

            if (contrasena == null || contrasena.trim().isEmpty()) {
                throw new EncontrarUsuarioException("La contrasena es obligatoria");
            }

            //llama al metodo de adminBO para obtener el administrador segun las credenciales ingresadas por el usuario
            AdministradorDTO adminEncontrado = adminBO.obtenerAdministradorBO(nombreUsuario, contrasena);
            if (adminEncontrado == null) {
                throw new EncontrarUsuarioException("No se encontro el administrador");
            }

            return adminEncontrado; //se devuelve el administrador encontrado

        } catch (EncontrarAdminExceptionBO e) {
            throw new EncontrarUsuarioException("No se pudo obtener el administrador: " + e.getMessage(), e);
        }
    }

}
