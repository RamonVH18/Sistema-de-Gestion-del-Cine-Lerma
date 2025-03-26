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
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author Ramon Valencia
 */
public class Utilerias {

    public Utilerias() {

    }

    public ImageIcon crearImagen(String url, int ancho, int altura) {
        URL imageUrl = getClass().getClassLoader().getResource(url);
        ImageIcon imagen = new ImageIcon(imageUrl);
        Image scaledImage = imagen.getImage().getScaledInstance(
                ancho,
                altura,
                Image.SCALE_SMOOTH
        );
        return imagen = new ImageIcon(scaledImage);
    }

    public String traducirDia(DayOfWeek dia) {
        String nuevoDia = new String();
        switch (dia) {
            case dia.MONDAY:
                nuevoDia = "Lun";
                break;
            case dia.TUESDAY:
                nuevoDia = "Mar";
                break;
            case dia.WEDNESDAY:
                nuevoDia = "Mie";
                break;
            case dia.THURSDAY:
                nuevoDia = "Jue";
                break;
            case dia.FRIDAY:
                nuevoDia = "Vie";
                break;
            case dia.SATURDAY:
                nuevoDia = "Sab";
                break;
            case dia.SUNDAY:
                nuevoDia = "Dom";
                break;

        }
        return nuevoDia;
    }

    public String traducirMes(Month mes) {
        String nuevoMes = new String();
        switch (mes) {
            case JANUARY:
                nuevoMes = "Enero";
                break;
            case FEBRUARY:
                nuevoMes = "Febrero";
                break;
            case MARCH:
                nuevoMes = "Marzo";
                break;
            case APRIL:
                nuevoMes = "Abril";
                break;
            case MAY:
                nuevoMes = "Mayo";
                break;
            case JUNE:
                nuevoMes = "Junio";
                break;
            case JULY:
                nuevoMes = "Julio";
                break;
            case AUGUST:
                nuevoMes = "Agosto";
                break;
            case SEPTEMBER:
                nuevoMes = "Septiembre";
                break;
            case OCTOBER:
                nuevoMes = "Octobre";
                break;
            case NOVEMBER:
                nuevoMes = "Noviembre";
                break;
            case DECEMBER:
                nuevoMes = "Diciembre";
                break;
        }
        return nuevoMes;
    }

    public LocalDate convertirDateALocalDate(Date fecha) {
        if (fecha == null) {
            return null; // Manejo del caso nulo
        }
        return fecha.toInstant()
                .atZone(ZoneId.systemDefault()) // Usa la zona horaria por defecto del sistema
                .toLocalDate();
    }

    public Date convertirLocalDateADate(LocalDate localDate) {
        if (localDate == null) {
            return null; // Manejo del caso nulo
        }
        return Date.from(
                localDate.atStartOfDay(ZoneId.systemDefault()) // Convierte LocalDate al inicio del día con zona horaria
                        .toInstant()
        );
    }

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

    public String convertirImagenABase64(String rutaImagen) {
        try {
            BufferedImage imagen = ImageIO.read(new File(rutaImagen));

            // Convertir la imagen a RGB (esto asegura que la imagen sea compatible con JPEG)
            BufferedImage imagenRGB = new BufferedImage(imagen.getWidth(), imagen.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = imagenRGB.createGraphics();
            g2d.drawImage(imagen, 0, 0, null);
            g2d.dispose();

            // Comprimir la imagen a JPEG con calidad reducida
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
            ImageWriter writer = writers.next();
            ImageWriteParam param = writer.getDefaultWriteParam();
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(0.5f); // Comprimir a calidad 50% (ajustar según sea necesario)

            ImageOutputStream ios = ImageIO.createImageOutputStream(baos);
            writer.setOutput(ios);
            writer.write(null, new IIOImage(imagenRGB, null, null), param);
            ios.close();

            byte[] bytesImagen = baos.toByteArray();
            return Base64.getEncoder().encodeToString(bytesImagen);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    // Generar un código QR desde el texto proporcionado
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

    // Generar un código QR desde una imagen en Base64
    public ImageIcon obtenerQRDesdeImagen(String rutaImagen, int tamañoQR) {
        String base64Imagen = convertirImagenABase64(rutaImagen);
        if (base64Imagen == null) {
            System.out.println("Error al convertir la imagen a Base64.");
            return null;
        }
        return generarCodigoQR(base64Imagen, tamañoQR);
    }
}
