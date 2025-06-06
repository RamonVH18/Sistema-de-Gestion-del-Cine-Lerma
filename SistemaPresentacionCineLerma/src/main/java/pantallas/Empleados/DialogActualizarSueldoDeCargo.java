/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package pantallas.Empleados;

import BOs.EmpleadoBO;
import DTOs.EmpleadoDTO;
import Excepciones.Empleados.ValidacionEmpleadoException;
import Excepciones.PersistenciaException;
import GestionEmpleados.IManejoEmpleados;
import GestionEmpleados.ManejoEmpleados;
import control.ControlDeNavegacion;
import control.IControl;
import enums.Cargo;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.bson.types.ObjectId;

/**
 *
 * @author isaac
 */
public class DialogActualizarSueldoDeCargo extends javax.swing.JDialog {

   private IControl control = ControlDeNavegacion.getInstancia();
   private SueldoOpciones frameAnterior;

    /**
     * Creates new form DialogActualizarSueldoDeCargo
     */
    public DialogActualizarSueldoDeCargo(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.frameAnterior = (SueldoOpciones) parent;
        initComponents();
        configurarDialog();
        poblarComboboxCargo();
    }

    private void configurarDialog() {
        pack();
        this.setLocationRelativeTo(getParent());
    }

    private void poblarComboboxCargo() {
        DefaultComboBoxModel<Cargo> cargoModel = new DefaultComboBoxModel<>();

        for (Cargo cargoEnum : Cargo.values()) {
            cargoModel.addElement(cargoEnum);
        }
        comboboxCargo.setModel(cargoModel);

        if (cargoModel.getSize() > 0) {
            comboboxCargo.setSelectedIndex(0); // selecciona el primer cargo por defecto
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelTitulo = new javax.swing.JLabel();
        comboboxCargo = new javax.swing.JComboBox<>();
        labelCargoSeleccionar = new javax.swing.JLabel();
        labelNuevoSueldo = new javax.swing.JLabel();
        txtNuevoSueldo = new javax.swing.JTextField();
        btnAceptar = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        labelTitulo.setFont(new java.awt.Font("Segoe UI Black", 0, 36)); // NOI18N
        labelTitulo.setText("Actualizar sueldo de cargo");

        comboboxCargo.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        comboboxCargo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboboxCargoActionPerformed(evt);
            }
        });

        labelCargoSeleccionar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        labelCargoSeleccionar.setText("Cargo a actualizar");

        labelNuevoSueldo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        labelNuevoSueldo.setText("Nuevo sueldo");

        txtNuevoSueldo.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        txtNuevoSueldo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNuevoSueldoActionPerformed(evt);
            }
        });

        btnAceptar.setBackground(new java.awt.Color(162, 132, 94));
        btnAceptar.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        btnAceptar.setForeground(new java.awt.Color(255, 255, 255));
        btnAceptar.setText("Aceptar");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        btnVolver.setBackground(new java.awt.Color(162, 132, 94));
        btnVolver.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        btnVolver.setForeground(new java.awt.Color(255, 255, 255));
        btnVolver.setText("Volver");
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(comboboxCargo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(labelCargoSeleccionar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelNuevoSueldo)
                    .addComponent(txtNuevoSueldo, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(66, 66, 66))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelTitulo)
                        .addGap(26, 26, 26))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnVolver)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAceptar)
                        .addGap(17, 17, 17))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelTitulo)
                .addGap(160, 160, 160)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelCargoSeleccionar)
                    .addComponent(labelNuevoSueldo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboboxCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNuevoSueldo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 246, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAceptar)
                    .addComponent(btnVolver))
                .addGap(30, 30, 30))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNuevoSueldoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNuevoSueldoActionPerformed


    }//GEN-LAST:event_txtNuevoSueldoActionPerformed

    private void comboboxCargoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboboxCargoActionPerformed


    }//GEN-LAST:event_comboboxCargoActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed

        Cargo cargoSeleccionado = (Cargo) comboboxCargo.getSelectedItem(); // Asumiendo que tu JComboBox se llama así
        String nuevoSueldoStr = txtNuevoSueldo.getText().trim(); // Asumiendo que tu JTextField se llama así

        // validaciones simples, si cargo esta vacio
        if (cargoSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un cargo.", "Error de Selección", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // si sueldo esta vacio
        if (nuevoSueldoStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese el nuevo sueldo general.", "Entrada Requerida", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double nuevoSueldo;
        try {
            nuevoSueldo = Double.parseDouble(nuevoSueldoStr); // convertimos a double nuestro string del nuevo sueldo
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un valor numérico válido para el sueldo.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            return;
        }

        //Confirmacion si quieres actualizar
        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de que desea actualizar el sueldo a $" + String.format("%.2f", nuevoSueldo)
                + " para todos los empleados activos con el cargo '" + cargoSeleccionado.toString() + "'?", // Usamos toString() del Cargo para mostrarlo
                "Confirmar Actualización Masiva",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirmacion != JOptionPane.YES_OPTION) {
            return; // El usuario canceló
        }

        // llaammos a control para actualizar
        control.controlActualizarSueldoGeneralPorCargo(cargoSeleccionado, nuevoSueldo);


    }//GEN-LAST:event_btnAceptarActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        
        this.dispose();
        SueldoOpciones sueldoOp = new SueldoOpciones();
        sueldoOp.setVisible(true);
        
        
    }//GEN-LAST:event_btnVolverActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnVolver;
    private javax.swing.JComboBox<Cargo> comboboxCargo;
    private javax.swing.JLabel labelCargoSeleccionar;
    private javax.swing.JLabel labelNuevoSueldo;
    private javax.swing.JLabel labelTitulo;
    private javax.swing.JTextField txtNuevoSueldo;
    // End of variables declaration//GEN-END:variables
}
