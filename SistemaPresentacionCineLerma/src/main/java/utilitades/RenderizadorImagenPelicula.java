package utilitades;

import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Clase de utilidad que permite cargar, escalar y renderizar imágenes en un
 * panel de forma centrada manteniendo la proporción de aspecto.
 *
 * Esta clase está diseñada para ser usada con imágenes que se representan como
 * arreglos de bytes, como las obtenidas desde bases de datos o desde un
 * selector de archivo.
 *
 * El método principal es renderizarImagen, que es el único método público. Los
 * demás métodos son auxiliares privados para mantener la clase limpia y
 * modular.
 *
 * @author Daniel M
 */
public class RenderizadorImagenPelicula {

    /**
     * Carga una imagen desde un arreglo de bytes, la escala al tamaño del panel
     * manteniendo su proporción de aspecto y la muestra centrada en el panel.
     *
     * @param panel JPanel donde se mostrará la imagen
     * @param imagenBytes Arreglo de bytes que representa la imagen
     * @throws IOException Si ocurre un error al leer los bytes de la imagen
     */
    public static void renderizarImagen(JPanel panel, byte[] imagenBytes) throws IOException {
        if (panel == null || imagenBytes == null) {
            return;
        }

        BufferedImage imagen = ImageIO.read(new ByteArrayInputStream(imagenBytes));
        if (imagen == null) {
            return;
        }

        Image imagenEscalada = escalarImagen(imagen, panel.getWidth(), panel.getHeight());
        mostrarImagen(panel, imagenEscalada);
    }

    /**
     * Escala una imagen al tamaño dado del panel manteniendo la proporción de
     * aspecto original.
     *
     * @param imagen Imagen a escalar
     * @param panelWidth Ancho del panel destino
     * @param panelHeight Alto del panel destino
     * @return Imagen escalada
     */
    private static Image escalarImagen(BufferedImage imagen, int panelWidth, int panelHeight) {
        // Obtener el ancho original de la imagen
        int imgWidth = imagen.getWidth();

        // Obtener la altura original de la imagen
        int imgHeight = imagen.getHeight();

        // Calcular la proporción del panel (ancho dividido entre alto)
        double panelRatio = (double) panelWidth / panelHeight;

        // Calcular la proporción de la imagen (ancho dividido entre alto)
        double imgRatio = (double) imgWidth / imgHeight;

        // Variables para el nuevo tamaño de la imagen escalada
        int drawWidth, drawHeight;

        // Comparar las proporciones para decidir cómo escalar manteniendo el aspecto
        if (imgRatio > panelRatio) {
            // Si la imagen es más ancha que el panel, ajustamos al ancho del panel
            drawWidth = panelWidth;
            // Calculamos la altura proporcional para mantener el aspecto
            drawHeight = (int) (panelWidth / imgRatio);
        } else {
            // Si la imagen es más alta o igual al panel, ajustamos a la altura del panel
            drawHeight = panelHeight;
            // Calculamos el ancho proporcional para mantener el aspecto
            drawWidth = (int) (panelHeight * imgRatio);
        }

        // Escalamos la imagen al nuevo tamaño calculado con suavizado de alta calidad
        return imagen.getScaledInstance(drawWidth, drawHeight, Image.SCALE_SMOOTH);

    }

    /**
     * Muestra una imagen escalada dentro de un panel, centrada automáticamente
     * mediante un GridBagLayout.
     *
     * @param panel JPanel donde se mostrará la imagen
     * @param imagenEscalada Imagen escalada a mostrar
     */
    private static void mostrarImagen(JPanel panel, Image imagenEscalada) {
        panel.removeAll();
        panel.setLayout(new GridBagLayout());
        panel.add(new JLabel(new ImageIcon(imagenEscalada)));
        panel.revalidate();
        panel.repaint();
    }
}
