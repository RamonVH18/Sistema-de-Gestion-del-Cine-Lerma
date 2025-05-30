/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pantallas.Usuarios;

import DTOs.AdministradorDTO;
import DTOs.ClienteDTO;
import Excepciones.EncontrarUsuarioException;
import control.ControlDeNavegacion;
import control.IControl;
import enums.EstadoUsuario;
import enums.Rol;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author sonic
 */
/**
 * Clase que representa la interfaz gráfica para el registro de un nuevo usuario, ya sea cliente o administrador.
 */
public class RegistrarUsuario extends javax.swing.JFrame {

    private final IControl control = ControlDeNavegacion.getInstancia(); //obtener instancia del control de navegacion

    /**
     * Creates new form RegistrarUsuario
     */
    public RegistrarUsuario() {
        initComponents(); //inicializar componentes
        ocultarTodosLosCampos(); //llamar al metodo auxiliar para ocultar todos los campos y labels, esto por que 
                                 //al llamar a este frame estara vacio hasta que se elija el rol del usuario que se quiere registrar
        contrasenaField.setText(""); //vaciar el campo de la contrasena

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPasswordField1 = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        nombrelbl = new javax.swing.JLabel();
        paternolbl = new javax.swing.JLabel();
        correolbl = new javax.swing.JLabel();
        contrasenalbl = new javax.swing.JLabel();
        CPlbl = new javax.swing.JLabel();
        usuariolbl = new javax.swing.JLabel();
        domiciliolbl = new javax.swing.JLabel();
        callelbl = new javax.swing.JLabel();
        telefonolbl = new javax.swing.JLabel();
        maternolbl = new javax.swing.JLabel();
        btnConfirmar = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();
        nombreField = new javax.swing.JTextField();
        NumDomicilioFIeld = new javax.swing.JTextField();
        usuarioField = new javax.swing.JTextField();
        CPField = new javax.swing.JTextField();
        CalleField = new javax.swing.JTextField();
        telefonoField = new javax.swing.JTextField();
        correoField = new javax.swing.JTextField();
        apellidoPaternoField = new javax.swing.JTextField();
        apellidoMaternoField = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        RFCField = new javax.swing.JTextField();
        RFClbl = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        nacimientolbl = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        comboRol = new javax.swing.JComboBox<>();
        contrasenaField = new javax.swing.JPasswordField();

        jPasswordField1.setText("jPasswordField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(680, 820));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("Registrar Usuario");

        nombrelbl.setText("Nombres");

        paternolbl.setText("Apellido Paterno");

        correolbl.setText("Correo Electronico");

        contrasenalbl.setText("Contraseña");

        CPlbl.setText("CP");

        usuariolbl.setText("Nombre de Usuario");

        domiciliolbl.setText("Numero de Domicilio");

        callelbl.setText("Calle");

        telefonolbl.setText("Telefono");

        maternolbl.setText("Apellido Materno");

        btnConfirmar.setBackground(new java.awt.Color(162, 132, 94));
        btnConfirmar.setText("Confirmar");
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });

        btnVolver.setBackground(new java.awt.Color(162, 132, 94));
        btnVolver.setText("< Volver");
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });

        usuarioField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usuarioFieldActionPerformed(evt);
            }
        });

        jLabel12.setText("ROL");

        RFCField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RFCFieldActionPerformed(evt);
            }
        });

        RFClbl.setText("RFC");

        nacimientolbl.setText("Fecha de nacimiento");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        comboRol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CLIENTE", "ADMINISTRADOR", " " }));
        comboRol.setSelectedIndex(2);
        comboRol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboRolActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(178, 178, 178)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(58, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(250, 250, 250))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addComponent(comboRol, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(108, 108, 108)
                                .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(25, 25, 25))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(nombrelbl, javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(domiciliolbl, javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(callelbl, javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(telefonolbl, javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(maternolbl, javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(nombreField, javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(NumDomicilioFIeld, javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(CalleField, javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(telefonoField, javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(apellidoMaternoField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE))
                                            .addGap(88, 88, 88))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(RFClbl)
                                            .addGap(306, 306, 306)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(RFCField, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(88, 88, 88)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(CPField, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
                                    .addComponent(CPlbl)
                                    .addComponent(usuariolbl)
                                    .addComponent(contrasenalbl)
                                    .addComponent(correolbl)
                                    .addComponent(paternolbl)
                                    .addComponent(usuarioField, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
                                    .addComponent(correoField)
                                    .addComponent(apellidoPaternoField)
                                    .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(nacimientolbl)
                                    .addComponent(contrasenaField))))
                        .addGap(56, 56, 56))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nombrelbl)
                    .addComponent(paternolbl, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(nombreField, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .addComponent(apellidoPaternoField))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(correolbl)
                    .addComponent(maternolbl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(correoField, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                    .addComponent(apellidoMaternoField))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(contrasenalbl)
                    .addComponent(telefonolbl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(telefonoField, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                    .addComponent(contrasenaField))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(callelbl)
                    .addComponent(usuariolbl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(usuarioField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CalleField, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(domiciliolbl)
                    .addComponent(CPlbl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(CPField)
                    .addComponent(NumDomicilioFIeld, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RFClbl)
                    .addComponent(nacimientolbl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(RFCField, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE))
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboRol, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnVolver, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        // TODO add your handling code here:
        //Al presionar el boton de confirmar entonces ocurrira lo siguiente:
        
        //Se obtiene el objeto Rol segun lo que se haya seleciconado en el comboBox para llamar a diferentes metodos del control y construir al cliente o al admin y tambien
        //para asignarselo al cliente o administrador que se quiere registrar
        String rolSeleccionadoTexto = (String) comboRol.getSelectedItem();
        Rol rolSeleccionado = Rol.valueOf(rolSeleccionadoTexto.toUpperCase());

        //Si el usuario no eligio una fecha de nacimiento aparecera una ventana que indique que es obligatorio poner una fecha de nacimiento para el usuario
        if (jDateChooser1.getDate() == null) {
            JOptionPane.showMessageDialog(this, "La fecha de nacimiento es obligatoria.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        //Si el rol seleciconado fue cliente entonces se llamara al metodo auxiliar para construir el DTO de cliente con todos los datos ingresados por el usuario
        if (Rol.CLIENTE.equals(rolSeleccionado)) {
            
            //se crea un nuevo objeto ClienteDTO que sera mapeado por el metodo construirClienteDTO
            ClienteDTO clienteRegistrar = construirClienteDTO(rolSeleccionado);

            //Una vez que se construyo el clienteDTO que se va a registrar entonces se llama al metodo del control para realizar la operacion de registro
            //del cliente
            ClienteDTO clienteRegistrado = control.registrarCliente(clienteRegistrar);

            //Si el registro fue exitoso se mostrara una ventana con un mensaje de exito y se cerrara la pantalla
            //para devolver al usuario a la pantalla de iniciar sesion
            if (clienteRegistrado != null) {
                JOptionPane.showMessageDialog(this, "Cliente registrado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
                control.mostrarIniciarSesion();
            }

        }

        //Si el rol seleciconado fue administrador entonces se llamara al metodo auxiliar para construir el DTO de administrador con todos los datos ingresados por el usuario
        if (Rol.ADMINISTRADOR.equals(rolSeleccionado)) {
            
            //se crea un nuevo objeto AdministradorDTO que sera mapeado por el metodo construirAdminDTO
            AdministradorDTO adminRegistrar = construirAdminDTO(rolSeleccionado);

            //Una vez que se construyo el administradorDTO que se va a registrar entonces se llama al metodo del control para realizar la operacion de registro
            //del admin
            AdministradorDTO administradorRegistrado = control.registrarAdministrador(adminRegistrar);

            //Si el registro fue exitoso se mostrara una ventana con un mensaje de exito y se cerrara la pantalla
            //para devolver al usuario a la pantalla de iniciar sesion
            if (administradorRegistrado != null) {
                JOptionPane.showMessageDialog(this, "Administrador registrado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
                control.mostrarIniciarSesion();
            }
        }

    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        // TODO add your handling code here:
        control.mostrarIniciarSesion();
        this.dispose();
    }//GEN-LAST:event_btnVolverActionPerformed

    private void RFCFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RFCFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RFCFieldActionPerformed

    private void usuarioFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usuarioFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usuarioFieldActionPerformed

    private void comboRolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboRolActionPerformed
        //El comboBox comboRol es importante por que contiene los dos tipos de usuario que se pueden registrar, cliente y administrador
        
        //El rol del usuario se obtiene del comboBox
        String rolSeleccionado = (String) comboRol.getSelectedItem();

        if (comboRol.getItemCount() > 2) {
            comboRol.removeItemAt(2); //esto se utiliza para que el objeto seleciconado del comboBox por default sea uno vacio o null
        }
        
        //Por cada vez que el usuario elija uno de los dos roles en el comboBox se limpiaran todos los campos  para posteriormente mostrar
        //los campos correspondientes al rol que haya elegido
        limpiarCampos();

        // Mostrar campos comunes para ambos roles, independientemente del rol que se haya seleccionado se cargaran siempre los campos comunes del usuario
        mostrarCamposComunes();

        //Si el rol que se selecciono es "CLIENTE" entonces se cargaran tambien los campos exclusivos de un cliente asi como los comunes del usuario
        if ("CLIENTE".equals(rolSeleccionado)) {
            mostrarCamposCliente();
        //Si el rol que se selecciono es "ADMINISTRADOR" entonces se cargaran tambien los campos exclusivos de un administrador asi como los comunes del usuario
        } else if ("ADMINISTRADOR".equals(rolSeleccionado)) {
            mostrarCamposAdministrador();
        }

        this.revalidate();
        this.repaint();
    }

    //Metodo auxiliar uqe muestra los campos comunes para hacer el registro de un usuario, estos campos son los del Usuario, atributos que poseen tanto el cliente y el admin
    private void mostrarCamposComunes() {
        // Mostrar campos comunes a ambos roles
        nombrelbl.setVisible(true);
        paternolbl.setVisible(true);
        maternolbl.setVisible(true);
        correolbl.setVisible(true);
        contrasenalbl.setVisible(true);
        usuariolbl.setVisible(true);
        telefonolbl.setVisible(true);
        nacimientolbl.setVisible(true);

        nombreField.setVisible(true);
        apellidoPaternoField.setVisible(true);
        apellidoMaternoField.setVisible(true);
        correoField.setVisible(true);
        contrasenaField.setVisible(true);
        usuarioField.setVisible(true);
        telefonoField.setVisible(true);
        jDateChooser1.setVisible(true);
    }

    //Metodo que muestra los campos exclusivos de un cliente para su registro, estos son los campos de direccion
    private void mostrarCamposCliente() {
        // Mostrar campos específicos de cliente
        callelbl.setVisible(true);
        domiciliolbl.setVisible(true);
        CPlbl.setVisible(true);

        CalleField.setVisible(true);
        NumDomicilioFIeld.setVisible(true);
        CPField.setVisible(true);

        // Ocultar campos de administrador
        RFClbl.setVisible(false);
        RFCField.setVisible(false);
        RFCField.setText("");
    }

    //Metodo que muestra los campos exclusivos de un administrador para su registro, este es el campo de RFC
    private void mostrarCamposAdministrador() {
        // Mostrar campos específicos de administrador
        RFClbl.setVisible(true);
        RFCField.setVisible(true);

        // Ocultar campos de cliente
        callelbl.setVisible(false);
        domiciliolbl.setVisible(false);
        CPlbl.setVisible(false);
        CalleField.setVisible(false);
        NumDomicilioFIeld.setVisible(false);
        CPField.setVisible(false);
        CalleField.setText("");
        NumDomicilioFIeld.setText("");
        CPField.setText("");
    }//GEN-LAST:event_comboRolActionPerformed


    //Metodo auxiliar que oculta todos los fields y labels de la pantalla
    private void ocultarTodosLosCampos() {
        // Ocultar todos los labels
        nombrelbl.setVisible(false);
        paternolbl.setVisible(false);
        correolbl.setVisible(false);
        contrasenalbl.setVisible(false);
        CPlbl.setVisible(false);
        usuariolbl.setVisible(false);
        domiciliolbl.setVisible(false);
        callelbl.setVisible(false);
        telefonolbl.setVisible(false);
        maternolbl.setVisible(false);
        RFClbl.setVisible(false);
        nacimientolbl.setVisible(false);

        // Ocultar todos los fields
        nombreField.setVisible(false);
        apellidoPaternoField.setVisible(false);
        apellidoMaternoField.setVisible(false);
        correoField.setVisible(false);
        contrasenaField.setVisible(false);
        usuarioField.setVisible(false);
        telefonoField.setVisible(false);
        CalleField.setVisible(false);
        NumDomicilioFIeld.setVisible(false);
        CPField.setVisible(false);
        RFCField.setVisible(false);
        jDateChooser1.setVisible(false);

        // Limpiar todos los campos
        limpiarCampos();
    }

    //metodo auxiliar que limpia todos los fields
    private void limpiarCampos() {
        nombreField.setText("");
        apellidoPaternoField.setText("");
        apellidoMaternoField.setText("");
        correoField.setText("");
        contrasenaField.setText("");
        usuarioField.setText("");
        telefonoField.setText("");
        CalleField.setText("");
        NumDomicilioFIeld.setText("");
        CPField.setText("");
        RFCField.setText("");
        jDateChooser1.setDate(null);
    }

    //Metodo que construye un objteto AdministradorDTO segun los datos ingresados en los fields por el usuario que buscar registrar un administrador
    private AdministradorDTO construirAdminDTO(Rol rolSeleccionado) {
        AdministradorDTO adminRegistrar = new AdministradorDTO();

            //setear nombres
            adminRegistrar.setNombre(nombreField.getText().trim());
            adminRegistrar.setApellidoPaterno(apellidoPaternoField.getText().trim());
            adminRegistrar.setApellidoMaterno(apellidoMaternoField.getText().trim());

            adminRegistrar.setCorreoElectronico(correoField.getText().trim());
            adminRegistrar.setTelefono(telefonoField.getText().trim());
            adminRegistrar.setRol(rolSeleccionado);
            adminRegistrar.setContraseña(new String(contrasenaField.getPassword()));
            adminRegistrar.setNombreUsuario(usuarioField.getText().trim());
            adminRegistrar.setEstado(EstadoUsuario.ACTIVO);

            //convertir y setear fehca de nacimiento
            LocalDateTime nacimiento = jDateChooser1.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            adminRegistrar.setFechaNacimiento(nacimiento);

            //setear RFC
            adminRegistrar.setRFC(RFCField.getText().trim());
            
            return adminRegistrar;
    }
    
    //Metodo que construye un objteto ClienteDTO segun los datos ingresados en los fields por el usuario que buscar registrar un administrador
    private ClienteDTO construirClienteDTO(Rol rolSeleccionado) {
        ClienteDTO clienteRegistrar = new ClienteDTO();

            //setear nombres
            clienteRegistrar.setNombre(nombreField.getText().trim());
            clienteRegistrar.setApellidoPaterno(apellidoPaternoField.getText().trim());
            clienteRegistrar.setApellidoMaterno(apellidoMaternoField.getText().trim());

            clienteRegistrar.setCorreoElectronico(correoField.getText().trim());
            clienteRegistrar.setTelefono(telefonoField.getText().trim());
            clienteRegistrar.setRol(rolSeleccionado);
            clienteRegistrar.setContraseña(new String(contrasenaField.getPassword()));
            clienteRegistrar.setNombreUsuario(usuarioField.getText().trim());
            clienteRegistrar.setEstado(EstadoUsuario.ACTIVO);

            //convertir y setear fehca de nacimiento
            LocalDateTime nacimiento = jDateChooser1.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            clienteRegistrar.setFechaNacimiento(nacimiento);

            //setear direccion
            clienteRegistrar.setCalle(CalleField.getText().trim());
            clienteRegistrar.setCP(CPField.getText().trim());
            clienteRegistrar.setNumero(NumDomicilioFIeld.getText().trim());
            
            return clienteRegistrar;
        
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField CPField;
    private javax.swing.JLabel CPlbl;
    private javax.swing.JTextField CalleField;
    private javax.swing.JTextField NumDomicilioFIeld;
    private javax.swing.JTextField RFCField;
    private javax.swing.JLabel RFClbl;
    private javax.swing.JTextField apellidoMaternoField;
    private javax.swing.JTextField apellidoPaternoField;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JButton btnVolver;
    private javax.swing.JLabel callelbl;
    private javax.swing.JComboBox<String> comboRol;
    private javax.swing.JPasswordField contrasenaField;
    private javax.swing.JLabel contrasenalbl;
    private javax.swing.JTextField correoField;
    private javax.swing.JLabel correolbl;
    private javax.swing.JLabel domiciliolbl;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JLabel maternolbl;
    private javax.swing.JLabel nacimientolbl;
    private javax.swing.JTextField nombreField;
    private javax.swing.JLabel nombrelbl;
    private javax.swing.JLabel paternolbl;
    private javax.swing.JTextField telefonoField;
    private javax.swing.JLabel telefonolbl;
    private javax.swing.JTextField usuarioField;
    private javax.swing.JLabel usuariolbl;
    // End of variables declaration//GEN-END:variables
}
