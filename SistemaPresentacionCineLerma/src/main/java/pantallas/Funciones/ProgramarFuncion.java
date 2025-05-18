/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pantallas.Funciones;

import DTOs.FuncionDTO;
import DTOs.SalaViejaDTO;
import control.ControlDeNavegacion;
import control.IControl;
import java.time.LocalDateTime;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import utilitades.ModeladoTablas;

/**
 *
 * @author Abraham Coronel Bringas
 */
public class ProgramarFuncion extends javax.swing.JFrame {

    IControl control = ControlDeNavegacion.getInstancia();
    private ConsultarFunciones frameAnterior;
    private String nombrePelicula;

    public ProgramarFuncion(ConsultarFunciones frameAnterior, String nombrePelicula) {
        this.frameAnterior = frameAnterior;
        this.nombrePelicula = nombrePelicula;
        initComponents();
    }

    private void mostrarDialogoSalas() {
        List<SalaViejaDTO> salas = control.consultarSalas("");

        if (salas != null && !salas.isEmpty()) {
            String[] columnas = {"Numero de Sala", "Estado", "Asientos"};
            Object[][] datos = new Object[salas.size()][3];

            for (int i = 0; i < salas.size(); i++) {
                SalaViejaDTO sala = salas.get(i);
                datos[i][0] = sala.getNumSala();
                datos[i][1] = sala.getEstado();
                datos[i][2] = sala.getNumAsientos();
            }

            JTable tablaSalas = ModeladoTablas.creacionTablaSencilla(
                    columnas, datos, 14, 30
            );

            JDialog dialog = new JDialog(this, "Seleccionar Sala", true);
            dialog.setSize(500, 300);
            dialog.add(new JScrollPane(tablaSalas));

            tablaSalas.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    int row = tablaSalas.getSelectedRow();
                    if (row >= 0) {
                        salaSeleccionada.setText(tablaSalas.getValueAt(row, 0).toString());
                        dialog.dispose();
                    }
                }
            });

            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "No hay salas disponibles", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validarCampos() {
        if (salaSeleccionada.getText().isEmpty()
                || FechaHoraFuncion.getDateTimeStrict() == null
                || precioBoleto.getText().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Complete todos los campos obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            Double.parseDouble(precioBoleto.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Precio invalido", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private void registrarFuncion() {
        if (!validarCampos()) {
            return;
        }

        try {
            String numSala = salaSeleccionada.getText();
            LocalDateTime fechaHora = FechaHoraFuncion.getDateTimeStrict();
            double precio = Double.parseDouble(precioBoleto.getText());

            // Crear DTO con la película válida
            FuncionDTO funcionDTO = new FuncionDTO(
                    numSala,
                    nombrePelicula, // Nombre de la película seleccionada
                    fechaHora,
                    precio
            );

            FuncionDTO funcionRegistrada = control.registrarFuncion(funcionDTO);

            if (funcionRegistrada != null) {
                JOptionPane.showMessageDialog(this,
                        "Función registrada exitosamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
                frameAnterior.cargarTablaFunciones();
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Error al registrar la función",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Formato de precio inválido",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelTitulo = new javax.swing.JLabel();
        btnConfirmar = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        precioBoleto = new javax.swing.JTextField();
        salaSeleccionada = new javax.swing.JTextField();
        btnBuscarSalas = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        btnBuscarEmpleados = new javax.swing.JButton();
        FechaHoraFuncion = new com.github.lgooddatepicker.components.DateTimePicker();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(640, 830));

        labelTitulo.setText("PROGRAMAR FUNCION");
        labelTitulo.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 48)); // NOI18N

        btnConfirmar.setText("Confirmar>");
        btnConfirmar.setBackground(new java.awt.Color(162, 132, 94));
        btnConfirmar.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        btnConfirmar.setForeground(new java.awt.Color(255, 255, 255));
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });

        btnVolver.setText("<Volver");
        btnVolver.setBackground(new java.awt.Color(162, 132, 94));
        btnVolver.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        btnVolver.setForeground(new java.awt.Color(255, 255, 255));
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });

        jLabel1.setText("Seleccione una sala disponible");
        jLabel1.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 18)); // NOI18N

        jLabel2.setText("Seleccione una fecha y hora para la funcion");
        jLabel2.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 18)); // NOI18N

        precioBoleto.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 14)); // NOI18N
        precioBoleto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                precioBoletoActionPerformed(evt);
            }
        });

        salaSeleccionada.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 14)); // NOI18N

        btnBuscarSalas.setText("Buscar salas");
        btnBuscarSalas.setBackground(new java.awt.Color(162, 132, 94));
        btnBuscarSalas.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 18)); // NOI18N
        btnBuscarSalas.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscarSalas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarSalasActionPerformed(evt);
            }
        });

        jLabel5.setText("Seleccione precio del boleto");
        jLabel5.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 18)); // NOI18N

        jLabel3.setText("Seleccione un empleado asignado a la funcion(Opcional)");
        jLabel3.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 18)); // NOI18N

        jTextField1.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 14)); // NOI18N

        btnBuscarEmpleados.setText("Buscar Empleados");
        btnBuscarEmpleados.setBackground(new java.awt.Color(162, 132, 94));
        btnBuscarEmpleados.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 18)); // NOI18N
        btnBuscarEmpleados.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(btnVolver)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnConfirmar)
                .addGap(55, 55, 55))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(labelTitulo))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(160, 160, 160)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(salaSeleccionada)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(198, 198, 198)
                        .addComponent(btnBuscarSalas))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addComponent(jLabel3))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(174, 174, 174)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(189, 189, 189)
                        .addComponent(btnBuscarEmpleados))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(179, 179, 179)
                        .addComponent(precioBoleto, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(165, 165, 165)
                        .addComponent(jLabel5))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(122, 122, 122)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(FechaHoraFuncion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))))
                .addContainerGap(92, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(labelTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(salaSeleccionada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnBuscarSalas)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(28, 28, 28)
                .addComponent(FechaHoraFuncion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(precioBoleto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnBuscarEmpleados)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVolver)
                    .addComponent(btnConfirmar))
                .addGap(23, 23, 23))
        );

        btnBuscarSalas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarSalasActionPerformed(evt);
            }
        });

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        registrarFuncion();
    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        if (frameAnterior != null) {
            frameAnterior.setVisible(true);
        }
        this.dispose();
    }//GEN-LAST:event_btnVolverActionPerformed

    private void precioBoletoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_precioBoletoActionPerformed

    }//GEN-LAST:event_precioBoletoActionPerformed

    private void btnBuscarSalasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarSalasActionPerformed
        mostrarDialogoSalas();
    }//GEN-LAST:event_btnBuscarSalasActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.github.lgooddatepicker.components.DateTimePicker FechaHoraFuncion;
    private javax.swing.JButton btnBuscarEmpleados;
    private javax.swing.JButton btnBuscarSalas;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JButton btnVolver;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel labelTitulo;
    private javax.swing.JTextField precioBoleto;
    private javax.swing.JTextField salaSeleccionada;
    // End of variables declaration//GEN-END:variables
}
