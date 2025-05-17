/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilitades;

//import DTOs.ReporteUsuarioDTO;
//import com.itextpdf.text.BaseColor;
//import com.itextpdf.text.Document;
//import com.itextpdf.text.Element;
//import com.itextpdf.text.Font;
//import com.itextpdf.text.FontFactory;
//import com.itextpdf.text.Paragraph;
//import com.itextpdf.text.Phrase;
//import com.itextpdf.text.pdf.PdfPCell;
//import com.itextpdf.text.pdf.PdfPTable;
//import com.itextpdf.text.pdf.PdfWriter;
//import java.io.FileOutputStream;
//import java.text.SimpleDateFormat;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.List;
//
///**
// *
// * @author sonic
// */
//public class CreacionReportes {
//    public static void generarReporteUsuariosPDF(List<ReporteUsuarioDTO> usuarios, String filePath) throws Exception {
//        Document document = new Document() {
//        };
//        PdfWriter.getInstance(document, new FileOutputStream(filePath));
//        document.open();
//
//        // Título del documento
//        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
//        Paragraph title = new Paragraph("Reporte de usuarios", titleFont);
//        title.setAlignment(Element.ALIGN_CENTER);
//        title.setSpacingAfter(20f);
//        document.add(title);
//
//        // Fecha de generacion
//        Font dateFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
//        Paragraph date = new Paragraph("Generado el: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), dateFont);
//        date.setAlignment(Element.ALIGN_RIGHT);
//        date.setSpacingAfter(20f);
//        document.add(date);
//
//        // Tabla de usuarios
//        PdfPTable table = new PdfPTable(7); 
//        table.setWidthPercentage(100);
//        table.setSpacingBefore(10f);
//        table.setSpacingAfter(10f);
//
//        // Encabezados de la tabla
//        addTableHeaderUsuarios(table);
//
//        // Contenido de la tabla
//        for (ReporteUsuarioDTO usuario : usuarios) {
//            addUsuarioRow(table, usuario);
//        }
//
//        document.add(table);
//        document.close();
//    }
//
//
//    /**
//     * Agrega los encabezados de la tabla de usuarios al documento PDF.
//     *
//     * @param table Objeto PdfPTable al que se agregarán los encabezados
//     */
//    private static void addTableHeaderUsuarios(PdfPTable table) {
//        String[] headers = {"Nombre completo", "Telefono", "Correo", "Rol", "Estado", "Fecha de nacimiento", "Edad"};
//        for (String header : headers) {
//            PdfPCell cell = new PdfPCell();
//            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
//            cell.setBorderWidth(1);
//            cell.setPhrase(new Phrase(header));
//            table.addCell(cell);
//        }
//    }
//
//    /**
//     * Agrega una fila con la información de un usuario a la tabla del PDF.
//     *
//     * @param table Objeto PdfPTable al que se agregará la fila
//     * @param comanda Objeto ComandaDTO con la información a mostrar en la fila
//     */
//    private static void addUsuarioRow(PdfPTable table, ReporteUsuarioDTO usuario) {
//        // Folio
//        table.addCell(usuario.getNombreCompleto());
//        
//        table.addCell(usuario.getTelefono());
//        
//        table.addCell(usuario.getCorreo());
//        
//        table.addCell(usuario.getRol().toString());
//        
//        table.addCell(usuario.getEstado().toString());
//        
//        table.addCell(usuario.getFechaNacimiento() != null
//                ? usuario.getFechaNacimiento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
//                : "Sin fecha");
//
//        table.addCell(String.valueOf(usuario.getEdad()));
//    }
//}
