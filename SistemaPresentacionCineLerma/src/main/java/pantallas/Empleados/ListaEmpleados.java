/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pantallas.Empleados;

import BOs.EmpleadoBO;
import DTOs.EmpleadoDTO;
import Excepciones.PersistenciaException;
import enums.Cargo;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.bson.types.ObjectId;

/**
 *
 * @author isaac
 */
public class ListaEmpleados extends JPanel {

    private final EmpleadoBO empleadoBO;
    private JComboBox<Cargo> comboFiltroCargo;
    private JTextField txtFiltroNombre;
    private JButton btnFiltrar;
    private JButton btnMostrarTodos;
    private JTable tablaEmpleados;
    private DefaultTableModel tableModel;
    private List<EmpleadoDTO> empleadosActuales = new ArrayList<>();

    //columnas para los nombres de cada atributo
    private final String[] NOMBRES_COLUMNAS_VISIBLES = {
        "Nombre", "A. Paterno", "A. Materno", "Cargo", "Sueldo", "Correo E.", "Teléfono"
    };
    

    public ListaEmpleados(EmpleadoBO empleadoBO) {
        this.empleadoBO = empleadoBO;
        initComponents();
        cargarEmpleadosActivos();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        // --- Panel de Filtros ---
        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelFiltros.add(new JLabel("Filtrar por Cargo:"));
        comboFiltroCargo = new JComboBox<>(); // Será JComboBox<Cargo>
        DefaultComboBoxModel<Cargo> cargoModel = new DefaultComboBoxModel<>();
        cargoModel.addElement(null); // Opción para "Todos"
        for (Cargo cargo : Cargo.values()) {
            cargoModel.addElement(cargo);
        }
        comboFiltroCargo.setModel(cargoModel);
        panelFiltros.add(comboFiltroCargo);

        panelFiltros.add(new JLabel("Nombre:"));
        txtFiltroNombre = new JTextField(15);
        panelFiltros.add(txtFiltroNombre);

        btnFiltrar = new JButton("Filtrar");
        btnFiltrar.addActionListener(e -> filtrarEmpleados());
        panelFiltros.add(btnFiltrar);

        btnMostrarTodos = new JButton("Mostrar Todos");
        btnMostrarTodos.addActionListener(e -> cargarEmpleadosActivos());
        panelFiltros.add(btnMostrarTodos);
        add(panelFiltros, BorderLayout.NORTH);

        // --- Tabla de Empleados, ahora sin id ---
        tableModel = new DefaultTableModel(new String[0], 0) { // Inicialmente sin columnas o filas
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        // Añadir la columna ID al PRINCIPIO del modelo, pero no la mostraremos
        tableModel.addColumn("ID_Interno"); // Columna para el ID (String)
        for (String nombreColumna : NOMBRES_COLUMNAS_VISIBLES) {
            tableModel.addColumn(nombreColumna);
        }

        tablaEmpleados = new JTable(tableModel);
        tablaEmpleados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaEmpleados.setAutoCreateRowSorter(true);

        // Aqui se oculta el id, para que no sea vea
        // Columna 0 es "ID_Interno"
        TableColumn idColumna = tablaEmpleados.getColumnModel().getColumn(0);
        idColumna.setMinWidth(0);
        idColumna.setMaxWidth(0);
        idColumna.setWidth(0);
        idColumna.setPreferredWidth(0);
       

        JScrollPane scrollPane = new JScrollPane(tablaEmpleados);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void cargarEmpleadosActivos() {
        try {
            List<EmpleadoDTO> empleados = empleadoBO.obtenerTodosLosEmpleadosActivos();
            mostrarEmpleadosEnTabla(empleados);
            txtFiltroNombre.setText("");
            comboFiltroCargo.setSelectedItem(null);
        } catch (PersistenciaException ex) {
            mostrarError("Error al cargar empleados: " + ex.getMessage());
        }
    }

    private void filtrarEmpleados() {
        Cargo cargoSeleccionado = (Cargo) comboFiltroCargo.getSelectedItem();
        String nombreFiltro = txtFiltroNombre.getText().trim().toLowerCase();

        try {
            List<EmpleadoDTO> todosLosActivos = empleadoBO.obtenerTodosLosEmpleadosActivos(); // Podrías optimizar esto
            List<EmpleadoDTO> resultadosFiltrados = new ArrayList<>();

            for (EmpleadoDTO emp : todosLosActivos) {
                boolean pasaFiltroCargo = (cargoSeleccionado == null) || (emp.getCargo() == cargoSeleccionado);
                boolean pasaFiltroNombre = nombreFiltro.isEmpty()
                        || (emp.getNombre() != null && emp.getNombre().toLowerCase().contains(nombreFiltro))
                        || (emp.getApellidoP() != null && emp.getApellidoP().toLowerCase().contains(nombreFiltro))
                        || (emp.getApellidoM() != null && emp.getApellidoM().toLowerCase().contains(nombreFiltro));

                if (pasaFiltroCargo && pasaFiltroNombre) {
                    resultadosFiltrados.add(emp);
                }
            }
            mostrarEmpleadosEnTabla(resultadosFiltrados);
        } catch (PersistenciaException ex) { // Ajustar si tu BO lanza ValidacionEmpleadoException también
            mostrarError("Error al filtrar empleados: " + ex.getMessage());
        }
    }

    private void mostrarEmpleadosEnTabla(List<EmpleadoDTO> empleados) {
        empleadosActuales = empleados; // Guardar la lista completa actual de DTOs
        tableModel.setRowCount(0); // Limpiar tabla antes de llenar

        if (empleados == null) {
            return;
        }

        for (EmpleadoDTO emp : empleados) {
            tableModel.addRow(new Object[]{
                emp.getId(), // ID (String) en la columna 0 (oculta)
                emp.getNombre(),
                emp.getApellidoP(),
                emp.getApellidoM(),
                emp.getCargo() != null ? emp.getCargo().getDescripcion() : "N/A",
                String.format("$%.2f", emp.getSueldo()),
                emp.getCorreoE(),
                emp.getTelefono()
            });
        }
    }

    /**
     * Obtiene el EmpleadoDTO completo del empleado seleccionado en la tabla.
     *
     * @return El EmpleadoDTO seleccionado, o null si no hay selección.
     */
    public EmpleadoDTO getEmpleadoSeleccionado() {
        int filaVisualSeleccionada = tablaEmpleados.getSelectedRow();
        if (filaVisualSeleccionada != -1) {
            // Convertir el índice de la fila visual al índice del modelo (importante si la tabla se ordena)
            int indiceModelo = tablaEmpleados.convertRowIndexToModel(filaVisualSeleccionada);

            // Obtener el ID (String) de la columna 0 (oculta) del modelo
            String empleadoIdStr = (String) tableModel.getValueAt(indiceModelo, 0);

            // Buscar el DTO completo en nuestra lista 'empleadosActuales' usando el ID (String)
            if (empleadoIdStr != null && empleadosActuales != null) {
                for (EmpleadoDTO empDTO : empleadosActuales) {
                    if (empDTO.getId() != null && empDTO.getId().equals(empleadoIdStr)) {
                        return empDTO; // Devuelve el DTO completo
                    }
                }
            }
        }
        return null; // No se encontró o no hay selección
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
