/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilitades;

import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;


/**
 *
 * @author Ramon Valencia
 */
public class ModeladoTablas {

    public ModeladoTablas() {
    }
    
    public static JTable creacionTablaEstadisticas(String[] columnas, Object[][] datos) {
        
        DefaultTableModel modeloTabla = new DefaultTableModel(datos, columnas);
        
        JTable tablaEstadisticas = new JTable(modeloTabla) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Evita que cualquier celda sea editable
            }
        };
        
        JTableHeader encabezado = tablaEstadisticas.getTableHeader();

        encabezado.setReorderingAllowed(false);
        tablaEstadisticas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        encabezado.setResizingAllowed(false);
        
        encabezado.setFont(new Font(
                encabezado.getFont().getFontName(),
                encabezado.getFont().getStyle(),
                14)
        );
        
        encabezado.setPreferredSize(new Dimension(encabezado.getWidth(), 40));
        
        return tablaEstadisticas;
        
    }
    
    public static void ajusteTamañoColumnas(JTable tabla, Map tamañoColumnas) {
        List<Integer> llaves = new ArrayList<>(tamañoColumnas.keySet());
        TableColumnModel modeloColumna = tabla.getColumnModel();
        
        for (int i = 0; i < tamañoColumnas.size(); i++) {
            Integer llave = llaves.get(i);
            Integer tamaño = (Integer) tamañoColumnas.get(llave);
            TableColumn columna = modeloColumna.getColumn(llave);
            columna.setPreferredWidth(tamaño);
            columna.setCellRenderer(new CenterRenderer());
        }
    }
}

class CenterRenderer extends DefaultTableCellRenderer {
    public CenterRenderer() {
        setHorizontalAlignment(SwingConstants.CENTER); // Alinear al centro
    }
}