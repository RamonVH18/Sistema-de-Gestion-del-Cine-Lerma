/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilitades;

import java.awt.Image;
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
import java.util.Date;
import javax.swing.ImageIcon;

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
}
