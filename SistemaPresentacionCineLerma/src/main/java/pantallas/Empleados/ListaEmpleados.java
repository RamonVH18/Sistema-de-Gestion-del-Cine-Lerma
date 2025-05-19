/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pantallas.Empleados;

import BOs.EmpleadoBO;
import DTOs.EmpleadoDTO;
import Excepciones.ManejoObtenerEmpleadoException;
import Excepciones.ManejoObtenerEmpleadoPorCargoException;
import Excepciones.ManejoValidacionEmpleadoIdException;
import Excepciones.PersistenciaException;
import GestionEmpleados.IManejoEmpleados;
import GestionEmpleados.ManejoEmpleados;
import enums.Cargo;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
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

    // Cambiamos EmpleadoBO por IManejoEmpleados
    private final IManejoEmpleados manejoEmpleados;
    private JComboBox<Cargo> comboFiltroCargo;
    private JTextField txtFiltroNombre;
    private JButton btnFiltrar;
    private JButton btnMostrarTodos;
    private JTable tablaEmpleados;
    private DefaultTableModel tableModel;
    private List<EmpleadoDTO> empleadosActualesEnTabla = new ArrayList<>(); // Para getEmpleadoSeleccionado

    private final String[] NOMBRES_COLUMNAS_VISIBLES = {
        "Nombre", "A. Paterno", "A. Materno", "Cargo", "Sueldo", "Correo E.", "Teléfono"
    };

    public ListaEmpleados() { // Constructor ya no recibe EmpleadoBO
        // Obtenemos la instancia de ManejoEmpleados (Singleton)
        this.manejoEmpleados = ManejoEmpleados.getInstance();
        initComponents();
        cargarEmpleadosActivos(); // Carga inicial
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelFiltros.add(new JLabel("Filtrar por Cargo:"));
        comboFiltroCargo = new JComboBox<>();
        DefaultComboBoxModel<Cargo> cargoModel = new DefaultComboBoxModel<>();
        cargoModel.addElement(null); // Opción para "Todos los Cargos"
        for (Cargo cargo : Cargo.values()) { // Asumiendo que Cargo es un enum
            cargoModel.addElement(cargo);
        }
        comboFiltroCargo.setModel(cargoModel);
        panelFiltros.add(comboFiltroCargo);

        panelFiltros.add(new JLabel("Nombre:"));
        txtFiltroNombre = new JTextField(15);
        panelFiltros.add(txtFiltroNombre);

        btnFiltrar = new JButton("Filtrar");
        btnFiltrar.addActionListener(e -> {
            try {
                filtrarEmpleados();
            } catch (ManejoValidacionEmpleadoIdException ex) {
                Logger.getLogger(ListaEmpleados.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ManejoObtenerEmpleadoPorCargoException ex) {
                Logger.getLogger(ListaEmpleados.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        panelFiltros.add(btnFiltrar);

        btnMostrarTodos = new JButton("Mostrar Todos");
        btnMostrarTodos.addActionListener(e -> cargarEmpleadosActivos());
        panelFiltros.add(btnMostrarTodos);
        add(panelFiltros, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[0], 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModel.addColumn("ID_Interno"); // Columna 0 para el ID (oculta)
        for (String nombreColumna : NOMBRES_COLUMNAS_VISIBLES) {
            tableModel.addColumn(nombreColumna);
        }

        tablaEmpleados = new JTable(tableModel);
        tablaEmpleados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaEmpleados.setAutoCreateRowSorter(true);

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
            // Llamamos al método de ManejoEmpleados
            List<EmpleadoDTO> empleados = manejoEmpleados.obtenerTodosLosEmpleadosActivos();
            mostrarEmpleadosEnTabla(empleados);
            txtFiltroNombre.setText("");
            comboFiltroCargo.setSelectedItem(null); // Restablece el ComboBox
        } catch (ManejoObtenerEmpleadoException ex) { // Capturamos la excepción de la capa de Manejo
            mostrarError("Error al cargar empleados: " + ex.getMessage());
            // Para depuración:
            if (ex.getCause() != null) {
                System.err.println("Causa original (cargarEmpleadosActivos): " + ex.getCause().getMessage());
            }
        }
    }

    private void filtrarEmpleados() throws ManejoValidacionEmpleadoIdException, ManejoObtenerEmpleadoPorCargoException {
        Cargo cargoSeleccionado = (Cargo) comboFiltroCargo.getSelectedItem();
        String nombreFiltro = txtFiltroNombre.getText().trim().toLowerCase();
        List<EmpleadoDTO> resultadosFiltrados;

        try {
            if (cargoSeleccionado != null && nombreFiltro.isEmpty()) {
                // Filtrar solo por cargo usando el método específico del BO (vía Manejo)
                resultadosFiltrados = manejoEmpleados.obtenerEmpleadosActivosPorCargo(cargoSeleccionado);
            } else {
                // Obtener todos y filtrar en cliente si hay filtro de nombre o ambos filtros
                List<EmpleadoDTO> baseParaFiltrar;
                if (cargoSeleccionado != null) {
                    baseParaFiltrar = manejoEmpleados.obtenerEmpleadosActivosPorCargo(cargoSeleccionado);
                } else {
                    baseParaFiltrar = manejoEmpleados.obtenerTodosLosEmpleadosActivos();
                }

                // Filtrado por nombre en el cliente (Java Stream API para eficiencia)
                if (!nombreFiltro.isEmpty()) {
                    final String filtroBusqueda = nombreFiltro; // Necesario para lambda
                    resultadosFiltrados = baseParaFiltrar.stream()
                        .filter(emp -> (emp.getNombre() != null && emp.getNombre().toLowerCase().contains(filtroBusqueda)) ||
                                       (emp.getApellidoP() != null && emp.getApellidoP().toLowerCase().contains(filtroBusqueda)) ||
                                       (emp.getApellidoM() != null && emp.getApellidoM().toLowerCase().contains(filtroBusqueda)))
                        .collect(Collectors.toList());
                } else {
                    resultadosFiltrados = baseParaFiltrar; // No hay filtro de nombre, mostrar todos los del cargo (o todos)
                }
            }
            mostrarEmpleadosEnTabla(resultadosFiltrados);

        } catch (ManejoObtenerEmpleadoException vex) { // Por ej. si cargo es nulo y el método lo valida así
             mostrarError("Error de validación al filtrar: " + vex.getMessage());
        } catch (Exception ex) {
            mostrarError("Error al filtrar empleados: " + ex.getMessage());
            if (ex.getCause() != null) {
                System.err.println("Causa original (filtrarEmpleados): " + ex.getCause().getMessage());
            }
        }
    }

    private void mostrarEmpleadosEnTabla(List<EmpleadoDTO> empleados) {
        empleadosActualesEnTabla = (empleados != null) ? empleados : new ArrayList<>();
        tableModel.setRowCount(0); // Limpiar tabla

        if (empleados == null) return;

        for (EmpleadoDTO emp : empleados) {
            tableModel.addRow(new Object[]{
                emp.getId(), // ID oculto
                emp.getNombre(),
                emp.getApellidoP(),
                emp.getApellidoM(),
                emp.getCargo() != null ? emp.getCargo().toString() : "N/A", // O getDescripcion() si lo tienes
                String.format("$%.2f", emp.getSueldo()),
                emp.getCorreoE(),
                emp.getTelefono()
            });
        }
    }

    public EmpleadoDTO getEmpleadoSeleccionado() {
        int filaVisualSeleccionada = tablaEmpleados.getSelectedRow();
        if (filaVisualSeleccionada != -1) {
            int indiceModelo = tablaEmpleados.convertRowIndexToModel(filaVisualSeleccionada);
            String empleadoIdStr = (String) tableModel.getValueAt(indiceModelo, 0); // Columna 0 es ID

            // Buscar en la lista que se usó para poblar la tabla
            for (EmpleadoDTO empDTO : empleadosActualesEnTabla) {
                if (empDTO.getId() != null && empDTO.getId().equals(empleadoIdStr)) {
                    return empDTO;
                }
            }
        }
        return null;
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}