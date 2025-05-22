package entidades;

import java.util.Arrays;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

/**
 * Representa una película en el sistema.
 *
 * Esta clase se usa para mapear documentos en la base de datos MongoDB a
 * objetos Java. Contiene información básica de una película como título,
 * género, duración, clasificación, sinopsis y estado.
 *
 * El campo {@BsonId idPelicula} es usado como identificador único en MongoDB.
 *
 * @author Daniel M
 */
public class Pelicula {

    /**
     * Identificador único de la película en la base de datos MongoDB.
     */
    @BsonId
    private ObjectId idPelicula;

    /**
     * Imagen de la película, almacenada como arreglo de bytes (puede
     * representar un póster, miniatura, etc).
     */
    private byte[] imagen;

    /**
     * Título de la película.
     */
    private String titulo;

    /**
     * Género al que pertenece la película (ej. acción, drama, comedia, etc.).
     */
    private String genero;

    /**
     * Duración de la película en minutos.
     */
    private Integer duracion;

    /**
     * Clasificación de edad de la película (ej. A, B, C, B15).
     */
    private String clasificacion;

    /**
     * Breve descripción o resumen de la trama de la película.
     */
    private String sinopsis;

    /**
     * Indica si la película está activa (disponible para mostrar en cartelera,
     * por ejemplo).
     */
    private Boolean activo;

    /**
     * Constructor vacío. Requerido por MongoDB para que lo detecte como POJO.
     */
    public Pelicula() {
    }

    /**
     * Constructor completo con todos los campos incluyendo el ID.
     *
     * @param idPelicula Identificador único.
     * @param imagen Imagen en bytes.
     * @param titulo Título de la película.
     * @param genero Género de la película.
     * @param duracion Duración en minutos.
     * @param clasificacion Clasificación por edad.
     * @param sinopsis Sinopsis de la película.
     * @param estado Estado activo/inactivo.
     */
    public Pelicula(ObjectId idPelicula, byte[] imagen, String titulo, String genero, Integer duracion, String clasificacion, String sinopsis, Boolean estado) {
        this.idPelicula = idPelicula;
        this.imagen = imagen;
        this.titulo = titulo;
        this.genero = genero;
        this.duracion = duracion;
        this.clasificacion = clasificacion;
        this.sinopsis = sinopsis;
        this.activo = estado;
    }

    /**
     * Constructor sin ID (para crear nuevas películas antes de guardarlas en la
     * BD).
     *
     * @param imagen Imagen en bytes.
     * @param titulo Título de la película.
     * @param genero Género de la película.
     * @param duracion Duración en minutos.
     * @param clasificacion Clasificación por edad.
     * @param sinopsis Sinopsis de la película.
     * @param estado Estado activo/inactivo.
     */
    public Pelicula(byte[] imagen, String titulo, String genero, Integer duracion, String clasificacion, String sinopsis, Boolean estado) {
        this.imagen = imagen;
        this.titulo = titulo;
        this.genero = genero;
        this.duracion = duracion;
        this.clasificacion = clasificacion;
        this.sinopsis = sinopsis;
        this.activo = estado;
    }

    /**
     * Obtiene el identificador de la película.
     *
     * @return ID en formato {@link ObjectId}.
     */
    public ObjectId getIdPelicula() {
        return idPelicula;
    }

    /**
     * Establece el identificador de la película.
     *
     * @param idPelicula ID en formato {@link ObjectId}.
     */
    public void setIdPelicula(ObjectId idPelicula) {
        this.idPelicula = idPelicula;
    }

    /**
     * Obtiene el ID de la película como cadena de texto (para que la capa de
     * negocio no trabaje con objectId).
     *
     * @return ID como cadena (ObjectId hexadecimal de 24 caracteres).
     */
    public String getIdPeliculaString() {
        return idPelicula.toString();
    }

    /**
     * Establece el ID de la película a un ObjectId a partir de un String.
     *
     * @param idImportado ID en formato String.
     */
    public void setIdPeliculaString(String idImportado) {
        if (idImportado != null && !idImportado.isEmpty()) {
            this.idPelicula = new ObjectId(idImportado);
        } else {
            this.idPelicula = null;
        }
    }

    /**
     * Obtiene la imagen de la película como arreglo de bytes.
     *
     * @return Imagen en bytes.
     */
    public byte[] getImagen() {
        return imagen;
    }

    /**
     * Establece la imagen de la película.
     *
     * @param imagen Imagen en bytes.
     */
    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    /**
     * Obtiene el título de la película.
     *
     * @return Título.
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Establece el título de la película.
     *
     * @param titulo Título.
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Obtiene el género de la película.
     *
     * @return Género.
     */
    public String getGenero() {
        return genero;
    }

    /**
     * Establece el género de la película.
     *
     * @param genero Género.
     */
    public void setGenero(String genero) {
        this.genero = genero;
    }

    /**
     * Obtiene la duración de la película en minutos.
     *
     * @return Duración.
     */
    public Integer getDuracion() {
        return duracion;
    }

    /**
     * Establece la duración de la película.
     *
     * @param duracion Duración en minutos.
     */
    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    /**
     * Obtiene la clasificación por edad de la película.
     *
     * @return Clasificación.
     */
    public String getClasificacion() {
        return clasificacion;
    }

    /**
     * Establece la clasificación por edad de la película.
     *
     * @param clasificacion Clasificación.
     */
    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    /**
     * Obtiene la sinopsis de la película.
     *
     * @return Sinopsis.
     */
    public String getSinopsis() {
        return sinopsis;
    }

    /**
     * Establece la sinopsis de la película.
     *
     * @param sinopsis Sinopsis.
     */
    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    /**
     * Obtiene el estado de disponibilidad de la película.
     *
     * @return {@code true} si está activa, {@code false} si no.
     */
    public Boolean getActivo() {
        return activo;
    }

    /**
     * Establece si la película está activa o no.
     *
     * @param activo Estado activo/inactivo.
     */
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    /**
     * Representación en texto de la película, útil para depuración.
     *
     * @return Cadena que representa los valores de la película.
     */
    @Override
    public String toString() {
        return "Pelicula{"
                + "id=" + idPelicula
                + ", imagen=" + Arrays.toString(imagen)
                + ", titulo=" + titulo
                + ", genero=" + genero
                + ", duracion=" + duracion
                + ", clasificacion=" + clasificacion
                + ", sinopsis=" + sinopsis
                + ", estado=" + activo
                + '}';
    }
}
