/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pantallas.Salas;

import DTOs.AsientoFuncionDTO;
import DTOs.FuncionDTO;
import DTOs.PeliculaDTO;
import DTOs.SalaViejaDTO;
import control.ControlDeNavegacion;
import control.IControl;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import utilitades.Utilerias;

/**
 *
 * @author Ramon Valencia
 */
public class ConsultarAsientosReservados extends javax.swing.JFrame {

    private final Utilerias utilerias = new Utilerias(); // Objeto Utilerias para poder utilizar los metodos de la clase
    private final IControl control = ControlDeNavegacion.getInstancia(); // Instancia de la clase control de navegacion

    private final FuncionDTO funcionSelecionada;
    private final PeliculaDTO peliculaFuncion;
    private final SalaViejaDTO salaFuncion;
    private final List<AsientoFuncionDTO> asientosDisponibles;

    private final Integer anchoImagen = 200;
    private final Integer alturaImagen = 300;

    private final Font fuenteLabel1 = new Font("Tw Cen MT Condensed", Font.BOLD, 20);
    private final Font fuenteLabel2 = new Font("Tw Cen MT Condensed", Font.PLAIN, 20);

    /**
     * Creates new form ConsultarAsientosReservados
     *
     * @param funcionSelecionada
     */
    public ConsultarAsientosReservados(FuncionDTO funcionSelecionada) {
        this.funcionSelecionada = funcionSelecionada;
        utilerias.configurarFrameBase(this, "CONSULTAR ASIENTOS");
        peliculaFuncion = control.buscarPeliculaPorTitulo(funcionSelecionada.getNombrePelicula());
        salaFuncion = control.consultarSala(funcionSelecionada.getNumSala());
        asientosDisponibles = control.cargarListaAsientos(funcionSelecionada, true);

        configurarConsultarAsientosReservados();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelImagen = new javax.swing.JLabel();
        labelHorario1 = new javax.swing.JLabel();
        labelHorario2 = new javax.swing.JLabel();
        labelSala1 = new javax.swing.JLabel();
        labelSala2 = new javax.swing.JLabel();
        labelPelicula1 = new javax.swing.JLabel();
        labelPelicula2 = new javax.swing.JLabel();
        labelTotalAsientos1 = new javax.swing.JLabel();
        labelTotalAsientos2 = new javax.swing.JLabel();
        labelDisponibles = new javax.swing.JLabel();
        labelOcupados = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        labelImagen.setText("jLabel1");

        labelHorario1.setText("jLabel2");

        labelHorario2.setText("jLabel3");

        labelSala1.setText("jLabel4");

        labelSala2.setText("jLabel5");

        labelPelicula1.setText("jLabel6");

        labelPelicula2.setText("jLabel7");

        labelTotalAsientos1.setText("jLabel8");

        labelTotalAsientos2.setText("jLabel9");

        labelDisponibles.setText("jLabel10");

        labelOcupados.setText("jLabel11");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelImagen)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 270, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelOcupados)
                    .addComponent(labelDisponibles)
                    .addComponent(labelTotalAsientos2)
                    .addComponent(labelTotalAsientos1)
                    .addComponent(labelPelicula2)
                    .addComponent(labelPelicula1)
                    .addComponent(labelSala2)
                    .addComponent(labelSala1)
                    .addComponent(labelHorario2)
                    .addComponent(labelHorario1))
                .addGap(44, 44, 44))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(labelHorario1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelHorario2)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelSala1)
                    .addComponent(labelImagen))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelSala2)
                .addGap(18, 18, 18)
                .addComponent(labelPelicula1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelPelicula2)
                .addGap(18, 18, 18)
                .addComponent(labelTotalAsientos1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelTotalAsientos2)
                .addGap(18, 18, 18)
                .addComponent(labelDisponibles)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelOcupados)
                .addContainerGap(185, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * Metodo para configurar el JFrame completo, se llaman a todos los metodos necesarios para la configuracion
     */
    private void configurarConsultarAsientosReservados() {
        JPanel panelCentral = new JPanel();
        configurarPanelImagen(panelCentral);
        configurarPanelDatos(panelCentral);
        configurarBotonVolver();
        add(panelCentral, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel labelDisponibles;
    private javax.swing.JLabel labelHorario1;
    private javax.swing.JLabel labelHorario2;
    private javax.swing.JLabel labelImagen;
    private javax.swing.JLabel labelOcupados;
    private javax.swing.JLabel labelPelicula1;
    private javax.swing.JLabel labelPelicula2;
    private javax.swing.JLabel labelSala1;
    private javax.swing.JLabel labelSala2;
    private javax.swing.JLabel labelTotalAsientos1;
    private javax.swing.JLabel labelTotalAsientos2;
    // End of variables declaration//GEN-END:variables
    /**
     * Metodo para configurar el panel de la imagen
     * @param panelCentral 
     */
    private void configurarPanelImagen(JPanel panelCentral) {
        try {
            JPanel panelImagen = new JPanel();
            ImageIcon imagenPelicula = utilerias.crearImagen(peliculaFuncion.getImagen(), anchoImagen, alturaImagen);
            labelImagen = new JLabel(imagenPelicula);
            panelImagen.add(labelImagen);
            panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.X_AXIS));
            panelCentral.add(panelImagen);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "ERROR: Hubo al cargar la pagina. Intentar mas al rato", "¡ERROR!", JOptionPane.ERROR_MESSAGE);
            control.mostrarMenuSalas(this);
            dispose();
        }
    }
    /**
     * Metodo para configurar el panel donde van los datos de la funcion elegida
     * @param panelCentral 
     */
    private void configurarPanelDatos(JPanel panelCentral) {
        JPanel panelDatos = new JPanel();
        panelDatos.setLayout(new BoxLayout(panelDatos, BoxLayout.Y_AXIS));

        Integer numeroAsientos = salaFuncion.getNumAsientos();
        panelDatos.add(Box.createVerticalStrut(50));

        // Labels de horario
        labelHorario1 = new JLabel("HORARIO:");
        labelHorario2 = new JLabel(configurarHoraFuncion());
        configuracionLabel(panelDatos, labelHorario1, labelHorario2);

// Labels de sala
        labelSala1 = new JLabel("SALA:");
        labelSala2 = new JLabel(funcionSelecionada.getNumSala());
        configuracionLabel(panelDatos, labelSala1, labelSala2);
        
        labelPelicula1 = new JLabel("PELICULA:");
        labelPelicula2 = new JLabel(funcionSelecionada.getNombrePelicula());
        configuracionLabel(panelDatos, labelPelicula1, labelPelicula2);

// Labels de total de asientos
        labelTotalAsientos1 = new JLabel("TOTAL ASIENTOS:");

        labelTotalAsientos2 = new JLabel(numeroAsientos.toString());
        configuracionLabel(panelDatos, labelTotalAsientos1, labelTotalAsientos2);

// Labels de ocupados y disponibles
        labelDisponibles = new JLabel("Asientos disponibles: " + asientosDisponibles.size());
        labelOcupados = new JLabel("Asientos ocupados: " + (numeroAsientos - asientosDisponibles.size()));
        configuracionLabel(panelDatos, labelOcupados, labelDisponibles);
        
        panelDatos.add(Box.createVerticalGlue());
        panelCentral.add(panelDatos);

    }
    /**
     * Metodo para la configuracion de los diferentes label
     * @param panelDatos
     * @param label1
     * @param label2 
     */
    private void configuracionLabel(JPanel panelDatos, JLabel label1, JLabel label2) {
        panelDatos.add(label1);
        panelDatos.add(label2);
        label1.setFont(fuenteLabel1);
        label2.setFont(fuenteLabel2);
    }
    /**
     * Metodo para configurar la hora de funcion
     * @return 
     */
    private String configurarHoraFuncion() {
        LocalDateTime fecha = funcionSelecionada.getFechaHora();
        String minutosFormateados = fecha.getMinute() < 10
                ? "0" + fecha.getMinute()
                : String.valueOf(fecha.getMinute());
        String horaFuncion = fecha.getHour() + ":" + minutosFormateados;

        String diaTexto = utilerias.traducirDia(fecha.getDayOfWeek()) + ", "
                + fecha.getDayOfMonth() + " de "
                + utilerias.traducirMes(fecha.getMonth());

        return diaTexto + " " + horaFuncion;
    }

    /**
     * Metodo para configurar el obtener el boton volver del frameBase y
     * configurarlo para que nos regrese a la ventana anterior
     */
    private void configurarBotonVolver() {
        Container frame = this.getContentPane();
        JPanel panel = (JPanel) frame.getComponent(0);
        JButton btnVolver = (JButton) panel.getComponent(0);

        btnVolver.addActionListener((ActionEvent e) -> {
            control.mostrarConsultarFuncionesSalas(this);
        });

    }
}
