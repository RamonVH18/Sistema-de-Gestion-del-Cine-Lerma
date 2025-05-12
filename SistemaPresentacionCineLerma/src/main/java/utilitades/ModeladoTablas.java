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
 * Clase con metodos usados para la creacion y configuracion de tabla en las pantallas
 * @author Ramon Valencia
 */
public class ModeladoTablas {
    /**
     * Constructor vacio
     */
    public ModeladoTablas() {
    }
    /**
     * Metodo estatico para la creacion de una tabla sencilla, unicamente se ocupa saber el nombre de las columnas y los datos que va a llevar
     * @param columnas
     * @param datos
     * @param tamañoFuente
     * @param tamañoEncabezado
     * @return 
     */
    public static JTable creacionTablaSencilla(String[] columnas, Object[][] datos, Integer tamañoFuente, Integer tamañoEncabezado) {
        
        // Se crea el modelo de la tabla en base los datos y al nombre de las columnas
        DefaultTableModel modeloTabla = new DefaultTableModel(datos, columnas);
        
        // Se crea un JTable normal con la funcionalidad de que las celdas no sean editables
        JTable tablaEstadisticas = new JTable(modeloTabla) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Evita que cualquier celda sea editable
            }
        };
        // Se obtiene el encabezado de la tabla para poder manipularlo mas facilmente
        JTableHeader encabezado = tablaEstadisticas.getTableHeader();
        
        
        encabezado.setReorderingAllowed(false); // Configuracion para que no se pueda reordenar la columnas con el mouse
        tablaEstadisticas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Configuracion para que solo se pueda seleccionar una fila a la vez
        encabezado.setResizingAllowed(false); // Configuracion para que no se le pueda cambiar el tamaño de las columnas con el mouse
        
        // Se cambia el tamaño de letra del encabezado
        encabezado.setFont(new Font(
                encabezado.getFont().getFontName(),
                encabezado.getFont().getStyle(),
                tamañoFuente)
        );
        
        encabezado.setPreferredSize(new Dimension(encabezado.getWidth(), tamañoEncabezado)); // Configuracion del tamaño del encabezado
        
        return tablaEstadisticas;
        
    }
    
    // Metodo para configurar el tamaño de las columnas
    public static void ajusteTamañoColumnas(JTable tabla, Map tamañoColumnas) {
        List<Integer> llaves = new ArrayList<>(tamañoColumnas.keySet()); // Se crea un arrayList que contenga todas las llaves del mapa
        TableColumnModel modeloColumna = tabla.getColumnModel(); // Se obtiene el modelo de la tabla
        
        // For usado para la configuracion de cada columna de la tabla
        for (int i = 0; i < tamañoColumnas.size(); i++) {
            Integer llave = llaves.get(i); // Se obtiene llave por llave
            Integer tamaño = (Integer) tamañoColumnas.get(llave); //En base a la llave se obtiene el tamaño
            TableColumn columna = modeloColumna.getColumn(llave); // En base a la llave se decide que columna es la que se va a modificar
            columna.setPreferredWidth(tamaño); // Configuracion del tamaño de la columna
            columna.setCellRenderer(new CenterRenderer()); // Se centra el texto de la columna usando la clase CenterRenderer
        }
    }
}

/**
 * Clase CenterRenderer que sen encarga de centrar los datos de una columna
 * @author Ramon Valencia 
 */
class CenterRenderer extends DefaultTableCellRenderer {
    public CenterRenderer() {
        setHorizontalAlignment(SwingConstants.CENTER); // Alinear al centro
    }
}