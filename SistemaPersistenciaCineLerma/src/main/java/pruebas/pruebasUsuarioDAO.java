/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pruebas;

import DAOs.UsuarioDAO;
import Excepciones.PersistenciaException;
import Excepciones.usuarios.EncontrarUsuarioException;
import Excepciones.usuarios.RegistrarUsuarioException;
import entidades.Administrador;
import entidades.Cliente;
import entidades.Usuario;
import enums.EstadoUsuario;
import enums.Rol;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author sonic
 */
public class pruebasUsuarioDAO {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws RegistrarUsuarioException, EncontrarUsuarioException {
        try {
            UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();

            // Prueba de registro de Cliente
            Cliente cliente = new Cliente();
            cliente.setNombreDeUsuario("Sebas");
            cliente.setContrasenia("pipiski");
            cliente.setNombre("Sebastian");
            cliente.setApellidoPaterno("Borquez");
            cliente.setApellidoMaterno("Huerta");
            cliente.setCorreoElectronico("sonic15622@gmail.com");
            cliente.setFechaNacimiento(LocalDateTime.of(1990, 5, 15, 0, 0));
            cliente.setTelefono("6442595242");
            cliente.setRol(Rol.CLIENTE);
            cliente.setEstado(EstadoUsuario.ACTIVO);
            cliente.setCalle("Boulevard Real del Arco");
            cliente.setCP("85150");
            cliente.setNumero("2008");

            Usuario clienteRegistrado = usuarioDAO.registrarUsuario(cliente);
            System.out.println("Cliente registrado: " + clienteRegistrado.getNombreDeUsuario());

            // Prueba de registro de un administrador
            Administrador admin = new Administrador();
            admin.setNombreDeUsuario("jaimico");
            admin.setContrasenia("contrasena");
            admin.setNombre("Jaime");
            admin.setApellidoPaterno("el");
            admin.setApellidoMaterno("Mas pro");
            admin.setCorreoElectronico("jaime@yahoo.org");
            admin.setFechaNacimiento(LocalDateTime.of(1985, 10, 20, 0, 0));
            admin.setTelefono("644259421");
            admin.setRol(Rol.ADMINISTRADOR);
            admin.setEstado(EstadoUsuario.ACTIVO);
            admin.setRFC("JAIM851020ABC");

//            Administrador adminRegistrado = usuarioDAO.registrarAdministrador(admin);
//            System.out.println("Administrador registrado:" + adminRegistrado.getNombreDeUsuario());
//
//           
//            cliente.setTelefono("5557654321");
//            cliente.setCalle("Nueva Calle");
//
//            Usuario clienteActualizado = usuarioDAO.actualizarUsuario(cliente);
//            System.out.println("Cliente actualizado con exito: " + clienteActualizado.getNombreDeUsuario());
//
//            // Prueba de actualización de Administrador
//            admin.setCorreoElectronico("emailcambiado@gmail.com");
//
//            Usuario adminActualizado = usuarioDAO.actualizarUsuario(admin);
//            System.out.println("Administrador actualizado con éxito: " + adminActualizado.getNombreDeUsuario());
//            
//            Boolean usuarioEliminado = usuarioDAO.eliminarUsuario(clienteActualizado);
//            System.out.println(usuarioEliminado);

            System.out.println(usuarioDAO.obtenerUsuario("Sebas"));
            
            

        } catch (RegistrarUsuarioException e) {
            System.err.println("Error en la operación: " + e.getMessage());
            e.printStackTrace();
        } catch (EncontrarUsuarioException e) {
            System.err.println("Error en la operación: " + e.getMessage());
            e.printStackTrace();
        }
}
}

