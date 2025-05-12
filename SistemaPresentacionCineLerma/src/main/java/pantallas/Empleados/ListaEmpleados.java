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

    // Columnas para la tabla principal
    private final String[] NOMBRES_COLUMNAS_PRINCIPALES = {
            "ID", "Nombre", "A. Paterno", "A. Materno", "Cargo", "Sueldo", "Correo E.", "Teléfono"
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
        comboFiltroCargo = new JComboBox<>();
        // Es mejor que el ComboBox sea de tipo Cargo
        // comboFiltroCargo.addItem(null); // Para "Todos" o manejarlo con un item específico
        // Por ahora, asumimos que quieres filtrar por un cargo específico o todos si no hay selección
        // O puedes añadir una opción "TODOS CARGOS" explícitamente
        comboFiltroCargo.addItem(null); // Representa "Todos los Cargos"
        for (Cargo cargo : Cargo.values()) {
            comboFiltroCargo.addItem(cargo); // Añade el objeto Cargo directamente
        }
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

        // --- Tabla de Empleados ---
        tableModel = new DefaultTableModel(NOMBRES_COLUMNAS_PRINCIPALES, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaEmpleados = new JTable(tableModel);
        tablaEmpleados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaEmpleados.setAutoCreateRowSorter(true);
        
        // Ocultar columna ID si no se quiere mostrar, pero es útil para obtener el ID
        // tablaEmpleados.getColumnModel().getColumn(0).setMinWidth(0);
        // tablaEmpleados.getColumnModel().getColumn(0).setMaxWidth(0);
        // tablaEmpleados.getColumnModel().getColumn(0).setWidth(0);


        JScrollPane scrollPane = new JScrollPane(tablaEmpleados);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void cargarEmpleadosActivos() {
        try {
            List<EmpleadoDTO> empleados = empleadoBO.obtenerTodosLosEmpleadosActivos();
            mostrarEmpleadosEnTabla(empleados);
            txtFiltroNombre.setText("");
            comboFiltroCargo.setSelectedItem(null); // O el índice de tu opción "Todos"
        } catch (PersistenciaException ex) {
            mostrarError("Error al cargar empleados: " + ex.getMessage());
        }
    }

    private void filtrarEmpleados() {
        // El objeto seleccionado en JComboBox<Cargo> es directamente un Cargo o null
        Cargo cargoSeleccionado = (Cargo) comboFiltroCargo.getSelectedItem();
        String nombreFiltro = txtFiltroNombre.getText().trim().toLowerCase(); // Convertir a minúsculas para búsqueda insensible

        try {
            List<EmpleadoDTO> todosLosActivos = empleadoBO.obtenerTodosLosEmpleadosActivos();
            List<EmpleadoDTO> resultadosFiltrados = new ArrayList<>();

            for (EmpleadoDTO emp : todosLosActivos) {
                boolean pasaFiltroCargo = (cargoSeleccionado == null) || (emp.getCargo() == cargoSeleccionado);
                boolean pasaFiltroNombre = nombreFiltro.isEmpty() ||
                                           emp.getNombre().toLowerCase().contains(nombreFiltro) ||
                                           emp.getApellidoP().toLowerCase().contains(nombreFiltro) ||
                                           emp.getApellidoM().toLowerCase().contains(nombreFiltro);

                if (pasaFiltroCargo && pasaFiltroNombre) {
                    resultadosFiltrados.add(emp);
                }
            }
            mostrarEmpleadosEnTabla(resultadosFiltrados);
        } catch (PersistenciaException ex) {
            mostrarError("Error al filtrar empleados: " + ex.getMessage());
        }
    }

    private void mostrarEmpleadosEnTabla(List<EmpleadoDTO> empleados) {
        empleadosActuales = empleados;
        tableModel.setRowCount(0);

        if (empleados == null) return;

        for (EmpleadoDTO emp : empleados) {
            tableModel.addRow(new Object[]{
                    emp.getId(), // Guardamos el ID para referencia, se puede ocultar la columna
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

    public EmpleadoDTO getEmpleadoSeleccionado() {
        int filaSeleccionada = tablaEmpleados.getSelectedRow();
        if (filaSeleccionada != -1) {
            int indiceModelo = tablaEmpleados.convertRowIndexToModel(filaSeleccionada);
            // El ID está en la primera columna del modelo (índice 0)
            ObjectId empleadoId = (ObjectId) tableModel.getValueAt(indiceModelo, 0);
            
            // Buscar en la lista empleadosActuales por este ID
            for(EmpleadoDTO emp : empleadosActuales){
                if(emp.getId().equals(empleadoId)){
                    return emp;
                }
            }
        }
        return null;
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
    
    

