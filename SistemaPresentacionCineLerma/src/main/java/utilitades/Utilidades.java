/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilitades;

import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 *
 * @author Ramon Valencia
 */
public class Utilidades {
    
    public Utilidades() {
        
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
}
