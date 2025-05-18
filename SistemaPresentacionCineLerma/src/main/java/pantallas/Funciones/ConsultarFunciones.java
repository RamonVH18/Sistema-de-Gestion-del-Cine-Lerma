/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pantallas.Funciones;

import DTOs.FuncionDTO;
import GestionFunciones.IManejoFunciones;
import control.ControlDeNavegacion;
import control.IControl;
import java.awt.BorderLayout;
import java.awt.Color;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import utilitades.ModeladoTablas;

/**
 *
 * @author Abraham Coronel Bringas
 */
public class ConsultarFunciones extends javax.swing.JFrame {

    private JTable tabla;

    private IManejoFunciones manejoFunciones;
    private String nombrePelicula;
    IControl control = ControlDeNavegacion.getInstancia();

    public ConsultarFunciones(String nombrePelicula, IManejoFunciones manejoFunciones) {
        initComponents();
        this.manejoFunciones = manejoFunciones;
        this.nombrePelicula = nombrePelicula;
        labelNombrePelicula.setText("Funciones de la pelicula: " + nombrePelicula);
        cargarTablaFunciones();
    }

    public void cargarTablaFunciones() {
        try {
            List<FuncionDTO> funciones = manejoFunciones.buscarFunciones(nombrePelicula, null);

            String[] columnas = {"ID", "Sala", "Fecha y Hora", "Hora Termino", "Precio", "Empleado"};
            Object[][] datos = new Object[funciones.size()][6];

            for (int i = 0; i < funciones.size(); i++) {
                FuncionDTO funcion = funciones.get(i);
                LocalDateTime horaTermino = control.calcularHoraTerminoFuncion(funcion.getIdFuncion());

                datos[i][0] = funcion.getIdFuncion();
                datos[i][1] = funcion.getNumSala();
                datos[i][2] = funcion.getFechaHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                datos[i][3] = horaTermino.format(DateTimeFormatter.ofPattern("HH:mm"));
                datos[i][4] = String.format("$%.2f", funcion.getPrecio());

                String idEmpleado = funcion.getIdEmpleado();
                datos[i][5] = (idEmpleado == null || idEmpleado.isEmpty()) ? "No asignado" : idEmpleado;
            }

            tabla = ModeladoTablas.creacionTablaSencilla(columnas, datos, 14, 30);

            tabla.setShowGrid(true);
            tabla.setGridColor(new java.awt.Color(200, 200, 200));
            tabla.setRowHeight(25);

            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);

            for (int i = 0; i < tabla.getColumnCount(); i++) {
                tabla.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }

            JTableHeader header = tabla.getTableHeader();
            header.setDefaultRenderer(centerRenderer);
            header.setBackground(new java.awt.Color(162, 132, 94)); // Color del encabezado
            header.setForeground(Color.WHITE);
            header.setFont(new java.awt.Font("Tw Cen MT", 1, 14)); // Fuente del encabezado

            tabla.getColumnModel().getColumn(0).setMinWidth(0);
            tabla.getColumnModel().getColumn(0).setMaxWidth(0);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(80);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(200);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(100);
            tabla.getColumnModel().getColumn(4).setPreferredWidth(100);
            tabla.getColumnModel().getColumn(5).setPreferredWidth(120);

            panelTablaFunciones.removeAll();
            panelTablaFunciones.setLayout(new BorderLayout());
            panelTablaFunciones.add(new JScrollPane(tabla), BorderLayout.CENTER);

            revalidate();
            repaint();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar funciones: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void eliminarFuncionSeleccionada() {
        int filaSeleccionada = tabla.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione una funcion de la tabla",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // Confirmacion antes de eliminar
        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de eliminar esta funcion?",
                "Confirmar eliminacion",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            String idFuncion = (String) tabla.getValueAt(filaSeleccionada, 0);

            FuncionDTO funcionDTO = new FuncionDTO();
            funcionDTO.setIdFuncion(idFuncion);

            boolean eliminada = control.eliminarFuncion(funcionDTO);

            if (eliminada) {
                JOptionPane.showMessageDialog(
                        this,
                        "Función eliminada correctamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE
                );
                cargarTablaFunciones();

            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "No se pudo eliminar la funcion",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error al eliminar: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
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
        labelNombrePelicula = new javax.swing.JLabel();
        btnProgramar = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        panelTablaFunciones = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(640, 830));

        labelTitulo.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 48)); // NOI18N
        labelTitulo.setText("LISTAS FUNCIONES");

        labelNombrePelicula.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        labelNombrePelicula.setText("Funciones de la pelicula: ");

        btnProgramar.setBackground(new java.awt.Color(162, 132, 94));
        btnProgramar.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        btnProgramar.setForeground(new java.awt.Color(255, 255, 255));
        btnProgramar.setText("Programar funcion");
        btnProgramar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProgramarActionPerformed(evt);
            }
        });

        btnVolver.setBackground(new java.awt.Color(162, 132, 94));
        btnVolver.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        btnVolver.setForeground(new java.awt.Color(255, 255, 255));
        btnVolver.setText("<Volver");
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });

        btnEliminar.setBackground(new java.awt.Color(162, 132, 94));
        btnEliminar.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar.setText("Eliminar funcion");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelTablaFuncionesLayout = new javax.swing.GroupLayout(panelTablaFunciones);
        panelTablaFunciones.setLayout(panelTablaFuncionesLayout);
        panelTablaFuncionesLayout.setHorizontalGroup(
            panelTablaFuncionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 467, Short.MAX_VALUE)
        );
        panelTablaFuncionesLayout.setVerticalGroup(
            panelTablaFuncionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 348, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(218, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelNombrePelicula)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnProgramar)
                        .addGap(100, 100, 100)
                        .addComponent(btnEliminar)))
                .addGap(70, 70, 70))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(176, 176, 176)
                        .addComponent(labelTitulo))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(btnVolver))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addComponent(panelTablaFunciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(labelTitulo)
                .addGap(18, 18, 18)
                .addComponent(labelNombrePelicula)
                .addGap(18, 18, 18)
                .addComponent(panelTablaFunciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnProgramar)
                    .addComponent(btnEliminar))
                .addGap(32, 32, 32)
                .addComponent(btnVolver)
                .addGap(24, 24, 24))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnProgramarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProgramarActionPerformed
        control.mostrarProgramarFuncion(this, nombrePelicula);
    }//GEN-LAST:event_btnProgramarActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnVolverActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        eliminarFuncionSeleccionada();
    }//GEN-LAST:event_btnEliminarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnProgramar;
    private javax.swing.JButton btnVolver;
    private javax.swing.JLabel labelNombrePelicula;
    private javax.swing.JLabel labelTitulo;
    private javax.swing.JPanel panelTablaFunciones;
    // End of variables declaration//GEN-END:variables
}
