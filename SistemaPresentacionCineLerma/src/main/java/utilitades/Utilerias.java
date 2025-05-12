/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilitades;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import static java.time.Month.APRIL;
import static java.time.Month.AUGUST;
import static java.time.Month.DECEMBER;
import static java.time.Month.FEBRUARY;
import static java.time.Month.JANUARY;
import static java.time.Month.JULY;
import static java.time.Month.JUNE;
import static java.time.Month.MARCH;
import static java.time.Month.MAY;
import static java.time.Month.NOVEMBER;
import static java.time.Month.OCTOBER;
import static java.time.Month.SEPTEMBER;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.Hashtable;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

/**
 * Clase con diferentes metodos usados para la configuracion de diversas pantallas
 * @author Ramon Valencia
 */
public class Utilerias {
    /**
     * Constructor vacio
     */
    public Utilerias() {
    }
    /**
     * Metodo para cargar una imagen que esta guardad en el sistema
     * NOTA: Este metodo debe de diferenciarce del metodo para crear las peliculas, 
     * ya que las imagenes que crea este metodo no estan guardadas en ninguna base de datos
     * @param url
     * @param ancho
     * @param altura
     * @return 
     */
    public ImageIcon crearImagen(String url, int ancho, int altura) {
        URL imageUrl = getClass().getClassLoader().getResource(url); // Se obtiene la url de la imagen
        ImageIcon imagen = new ImageIcon(imageUrl);
        // Se define la escala de la imagen
        Image scaledImage = imagen.getImage().getScaledInstance(
                ancho,
                altura,
                Image.SCALE_SMOOTH
        );
        imagen = new ImageIcon(scaledImage);
        
        return imagen;
    }
    
    /**
     * Metodo para traducir un los dias del objeto DayOfWeek 
     * @param dia
     * @return 
     */
    public String traducirDia(DayOfWeek dia) {
        String nuevoDia = new String();
        switch (dia) {
            case MONDAY -> nuevoDia = "Lun";
            case TUESDAY -> nuevoDia = "Mar";
            case WEDNESDAY -> nuevoDia = "Mie";
            case THURSDAY -> nuevoDia = "Jue";
            case FRIDAY -> nuevoDia = "Vie";
            case SATURDAY -> nuevoDia = "Sab";
            case SUNDAY -> nuevoDia = "Dom";
        }
        return nuevoDia;
    }

    /**
     * Metodo para traducir el nombre de los mese del objeto Month
     * @param mes
     * @return 
     */
    public String traducirMes(Month mes) {
        String nuevoMes = new String();
        switch (mes) {
            case JANUARY -> nuevoMes = "Enero";
            case FEBRUARY -> nuevoMes = "Febrero";
            case MARCH -> nuevoMes = "Marzo";
            case APRIL -> nuevoMes = "Abril";
            case MAY -> nuevoMes = "Mayo";
            case JUNE -> nuevoMes = "Junio";
            case JULY -> nuevoMes = "Julio";
            case AUGUST -> nuevoMes = "Agosto";
            case SEPTEMBER -> nuevoMes = "Septiembre";
            case OCTOBER -> nuevoMes = "Octobre";
            case NOVEMBER -> nuevoMes = "Noviembre";
            case DECEMBER -> nuevoMes = "Diciembre";
        }
        return nuevoMes;
    }
    /**
     * Metodo para convertir un objeto de tipo Date a un objeto de tipo LocalDate
     * @param fecha
     * @return 
     */
    public LocalDate convertirDateALocalDate(Date fecha) {
        if (fecha == null) {
            return null; // Manejo del caso nulo
        }
        return fecha.toInstant()
                .atZone(ZoneId.systemDefault()) // Usa la zona horaria por defecto del sistema
                .toLocalDate();
    }

    /**
     * Metodo para convertir un objeto de tipo LocalDate a un objeto de tipo Date
     * @param localDate
     * @return 
     */
    public Date convertirLocalDateADate(LocalDate localDate) {
        if (localDate == null) {
            return null; // Manejo del caso nulo
        }
        return Date.from(
                localDate.atStartOfDay(ZoneId.systemDefault()) // Convierte LocalDate al inicio del día con zona horaria
                        .toInstant()
        );
    }
    
    /**
     * Metodo para sacarle captura de pantalla a un JFrame
     * @param frame
     * @param rutaArchivo
     * @return 
     */
    public String sacarCapturaJFrame(JFrame frame, String rutaArchivo) {
        try {
            Rectangle rect = frame.getBounds();
            BufferedImage image = new BufferedImage(rect.width, rect.height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = image.createGraphics();
            frame.paintAll(g2d);  // <-- Asegura que el frame se dibuje en la imagen
            g2d.dispose();
            frame.paint(image.getGraphics());
            //C:\Users\PC\Documents\GitHub\Sistema-de-Gestion-del-Cine-Lerma\SistemaPresentacionCineLerma\src\main\resources\img
            File file = new File(rutaArchivo);
            ImageIO.write(image, "png", file);

            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Metodo para convertir una imagen a base 64
     * @param rutaImagen
     * @param nuevoAncho
     * @param nuevoAlto
     * @return 
     */
    public String convertirImagenABase64(String rutaImagen, int nuevoAncho, int nuevoAlto) {
        try {
            BufferedImage imagen = ImageIO.read(new File(rutaImagen));

            // Reducir resolución
            BufferedImage imagenRedimensionada = escalarImagen(imagen, nuevoAncho, nuevoAlto);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(imagenRedimensionada, "jpg", baos);
            byte[] bytesImagen = baos.toByteArray();
            return Base64.getEncoder().encodeToString(bytesImagen);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Generar un código QR desde el texto proporcionado
     * @param texto
     * @param tamaño
     * @return 
     */
    public ImageIcon generarCodigoQR(String texto, int tamaño) {
        try {
            QRCodeWriter qrWriter = new QRCodeWriter();
            Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
            hints.put(EncodeHintType.QR_VERSION, 40); // Versión 40 (máxima)

            // Generación del código QR
            BitMatrix bitMatrix = qrWriter.encode(texto, BarcodeFormat.QR_CODE, tamaño, tamaño, hints);

            // Convertir la matriz en una imagen
            BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

            // Escalar la imagen generada a un tamaño adecuado (opcional)
            return new ImageIcon(qrImage.getScaledInstance(tamaño, tamaño, Image.SCALE_SMOOTH));
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Generar un código QR desde una imagen en Base64
     * @param rutaImagen
     * @param tamañoQR
     * @return 
     */ 
    public ImageIcon obtenerQRDesdeImagen(String rutaImagen, int tamañoQR) {
        String base64Imagen = convertirImagenABase64(rutaImagen, 10, 10);
        if (base64Imagen == null) {
            System.out.println("Error al convertir la imagen a Base64.");
            return null;
        }
        return generarCodigoQR(base64Imagen, tamañoQR);
    }
    /**
     * Metodo para escalar una imagen
     * @param original
     * @param ancho
     * @param alto
     * @return 
     */
    private BufferedImage escalarImagen(BufferedImage original, int ancho, int alto) {
        Image imagenEscalada = original.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        BufferedImage bufferedEscalada = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedEscalada.createGraphics();
        g2d.drawImage(imagenEscalada, 0, 0, null);
        g2d.dispose();
        return bufferedEscalada;
    }
    
    /**
     * Metodo para añadirle la configuracion basica de los frames a una pantalla
     * @param frame
     * @param titulo 
     */
    public void configurarFrameBase(JFrame frame, String titulo) {
        // Se ajusta el tamaño base del frame
        Dimension tamañoFrame = new Dimension(640, 830);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Configuracion de que cierre al salir
        frame.setLocationRelativeTo(null); // Se centra el frame en la pantalla
        frame.setLayout(new BorderLayout()); 
        frame.setPreferredSize(tamañoFrame); // Se le ajusta el tamaño deseado al frame
        frame.setSize(tamañoFrame); 
        frame.setTitle(titulo); // Se añade el titulo que llevara en el borde de la pestaña
        JLabel labelTitulo = new JLabel();
        
        JPanel panelNorte = new JPanel();
        JPanel panelSur = new JPanel();
        configuracionNorte(panelNorte); // Metodo para configurar el panel de arriba
        configuracionSur(panelSur); // Metodo para configurar panel de abajo
        
        panelNorte.add(labelTitulo);
        
        frame.add(panelSur, BorderLayout.SOUTH);
        frame.add(panelNorte, BorderLayout.NORTH);
        panelNorte.setOpaque(false);
        
        configurarTitulo(labelTitulo, titulo);
        
    }
    /**
     * Metodo para configurar el titulo de la ventana
     * Aqui tambien se configura el panel que ira en la parte superior del frame
     * @param tituloVentana
     * @param titulo 
     */
    private void configurarTitulo(JLabel tituloVentana, String titulo) {
        tituloVentana.setSize(new Dimension(125, 55));
        tituloVentana.setText(titulo);
        tituloVentana.setHorizontalAlignment(SwingConstants.CENTER);
        tituloVentana.setVerticalAlignment(SwingConstants.CENTER);
        tituloVentana.setFont(new Font("Regular", Font.BOLD, 32));
        
    }
    /**
     * Metodo que se encarga de configurar la parte superior de un JFrame
     * @param panelNorte 
     */
    private void configuracionNorte(JPanel panelNorte){
        Border border = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK);
        panelNorte.setPreferredSize(new Dimension(650, 70));
        panelNorte.setBorder(border);
    }
    /**
     * Metodo que se encarga de configurar la parte inferior de un JFrame 
     * @param panelSur 
     */
    private void configuracionSur(JPanel panelSur) {
        Border border = BorderFactory.createMatteBorder(2, 0, 0, 0, Color.BLACK);
        panelSur.setPreferredSize(new Dimension(650, 70));
        panelSur.setBorder(border);
        
        JButton btnVolver = new JButton("<- Volver");
        btnVolver.setPreferredSize(new Dimension(100, 45));
        btnVolver.setBackground(new Color(162,132,94));
        
        panelSur.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelSur.add(btnVolver);
    }
}
