/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pantallas.Empleados;

import BOs.EmpleadoBO;
import DTOs.EmpleadoDTO;
import GestionEmpleados.IManejoEmpleados;
import GestionEmpleados.ManejoEmpleados;
import control.ControlDeNavegacion;
import control.IControl;

/**
 *
 * @author isaac
 */
public class MenuEmpleados extends javax.swing.JFrame {
    
    private IControl control = ControlDeNavegacion.getInstancia();

    /**
     * Creates new form MenuEmpleados
     */
    public MenuEmpleados() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelMenu = new javax.swing.JLabel();
        btnRegistrar = new javax.swing.JButton();
        btnDespedir = new javax.swing.JButton();
        btnGestionCargo = new javax.swing.JButton();
        btnGestionarSueldos = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        btnActualizarEmpleado = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(519, 758));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        labelMenu.setFont(new java.awt.Font("Segoe UI Black", 0, 36)); // NOI18N
        labelMenu.setText("Administracion de empleados");
        getContentPane().add(labelMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, -1, -1));

        btnRegistrar.setBackground(new java.awt.Color(162, 132, 94));
        btnRegistrar.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btnRegistrar.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrar.setText("Registrar empleado");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });
        getContentPane().add(btnRegistrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 120, -1, -1));

        btnDespedir.setBackground(new java.awt.Color(162, 132, 94));
        btnDespedir.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btnDespedir.setForeground(new java.awt.Color(255, 255, 255));
        btnDespedir.setText("Despedir empleado");
        btnDespedir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDespedirActionPerformed(evt);
            }
        });
        getContentPane().add(btnDespedir, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 350, -1, -1));

        btnGestionCargo.setBackground(new java.awt.Color(162, 132, 94));
        btnGestionCargo.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btnGestionCargo.setForeground(new java.awt.Color(255, 255, 255));
        btnGestionCargo.setText("Gestionar cargos");
        btnGestionCargo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGestionCargoActionPerformed(evt);
            }
        });
        getContentPane().add(btnGestionCargo, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 460, 220, -1));

        btnGestionarSueldos.setBackground(new java.awt.Color(162, 132, 94));
        btnGestionarSueldos.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btnGestionarSueldos.setForeground(new java.awt.Color(255, 255, 255));
        btnGestionarSueldos.setText("Gestionar sueldos");
        btnGestionarSueldos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGestionarSueldosActionPerformed(evt);
            }
        });
        getContentPane().add(btnGestionarSueldos, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 570, -1, -1));

        btnSalir.setBackground(new java.awt.Color(162, 132, 94));
        btnSalir.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btnSalir.setForeground(new java.awt.Color(255, 255, 255));
        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        getContentPane().add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 670, -1, -1));

        btnActualizarEmpleado.setBackground(new java.awt.Color(162, 132, 94));
        btnActualizarEmpleado.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btnActualizarEmpleado.setForeground(new java.awt.Color(255, 255, 255));
        btnActualizarEmpleado.setText("Actualizar datos");
        btnActualizarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarEmpleadoActionPerformed(evt);
            }
        });
        getContentPane().add(btnActualizarEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 230, 230, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        
       control.mostrarRegistrarEmpleado(this);
        
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        
        this.dispose();
       control.mostrarMenuAdministrador(this);
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnDespedirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDespedirActionPerformed
        
        control.mostrarDespedirEmpleado(this);
        
    }//GEN-LAST:event_btnDespedirActionPerformed

    private void btnGestionCargoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGestionCargoActionPerformed
        
        // primero vamos a abrir el Dialog DialogSeleccionarEmpleado para seleccionar el empleado a actualizar su cargo
       control.iniciarFlujoActualizarCargo(this);
        
    }//GEN-LAST:event_btnGestionCargoActionPerformed

    private void btnGestionarSueldosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGestionarSueldosActionPerformed
        
        control.mostrarFrameSueldoOpciones(this);
    }//GEN-LAST:event_btnGestionarSueldosActionPerformed

    private void btnActualizarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarEmpleadoActionPerformed
            
        control.mostrarActualizarEmpleado(this);
        
    }//GEN-LAST:event_btnActualizarEmpleadoActionPerformed

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizarEmpleado;
    private javax.swing.JButton btnDespedir;
    private javax.swing.JButton btnGestionCargo;
    private javax.swing.JButton btnGestionarSueldos;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel labelMenu;
    // End of variables declaration//GEN-END:variables
}
