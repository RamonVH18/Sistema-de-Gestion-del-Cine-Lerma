/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.time.LocalDateTime;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

/**
 * Representa una función de cine que asocia una película, una sala, horario y
 * precio. Se utiliza para gestionar las proyecciones en el sistema, incluyendo
 * detalles de reservas y empleados responsables.
 *
 * @author Abraham Coronel Bringas
 */
public class Funcion {

    @BsonId
    private ObjectId idFuncion; // Identificador único en MongoDB

    private Sala sala; // Sala asignada para la funcion

    private Pelicula pelicula; // Película que se proyectará

    private LocalDateTime fechaHora; // Fecha y hora de inicio de la funcion

    private Double precio; // Precio de la entrada para esta funcion

    private String idEmpleado; // ID del empleado responsable de la funcion

    /**
     * Constructor vacío para serialización de MongoDB.
     */
    public Funcion() {
    }

    /**
     * Constructor completo con todos los atributos, incluyendo empleado
     * responsable.
     *
     * @param idFuncion Identificador único de la funcion.
     * @param sala Objeto Sala asignado.
     * @param pelicula Objeto Pelicula a proyectar.
     * @param fechaHora Fecha y hora de inicio.
     * @param precio Precio de la entrada.
     * @param idEmpleado ID del empleado que registra la funcion.
     */
    public Funcion(ObjectId idFuncion, Sala sala, Pelicula pelicula, LocalDateTime fechaHora, Double precio, String idEmpleado) {
        this.idFuncion = idFuncion;
        this.sala = sala;
        this.pelicula = pelicula;
        this.fechaHora = fechaHora;
        this.precio = precio;
        this.idEmpleado = idEmpleado;
    }

    /**
     * Constructor sin ID, util para nuevas funciones antes de ser persistidas.
     *
     * @param sala Objeto Sala asignado.
     * @param pelicula Objeto Pelicula a proyectar.
     * @param fechaHora Fecha y hora de inicio.
     * @param precio Precio de la entrada.
     * @param idEmpleado ID del empleado responsable.
     */
    public Funcion(Sala sala, Pelicula pelicula, LocalDateTime fechaHora, Double precio, String idEmpleado) {
        this.sala = sala;
        this.pelicula = pelicula;
        this.fechaHora = fechaHora;
        this.precio = precio;
        this.idEmpleado = idEmpleado;
    }

    public Funcion(ObjectId idFuncion, Sala sala, Pelicula pelicula, LocalDateTime fechaHora, Double precio) {
        this.idFuncion = idFuncion;
        this.sala = sala;
        this.pelicula = pelicula;
        this.fechaHora = fechaHora;
        this.precio = precio;
    }

    public Funcion(Sala sala, Pelicula pelicula, LocalDateTime fechaHora, Double precio) {
        this.sala = sala;
        this.pelicula = pelicula;
        this.fechaHora = fechaHora;
        this.precio = precio;
    }

    /**
     * @return ID único de la función en formato ObjectId (MongoDB).
     */
    public ObjectId getIdFuncion() {
        return idFuncion;
    }

    /**
     * @param idFuncion Establece el ID único de la función.
     */
    public void setIdFuncion(ObjectId idFuncion) {
        this.idFuncion = idFuncion;
    }

    /**
     * @return Sala asignada para la proyección.
     */
    public Sala getSala() {
        return sala;
    }

    /**
     * @param sala Establece la sala de proyección.
     */
    public void setSala(Sala sala) {
        this.sala = sala;
    }

    /**
     * @return Película que se proyectará en la función.
     */
    public Pelicula getPelicula() {
        return pelicula;
    }

    /**
     * @param pelicula Establece la película a proyectar.
     */
    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    /**
     * @return Fecha y hora de inicio de la función.
     */
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    /**
     * @param fechaHora Establece la fecha y hora de inicio.
     */
    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    /**
     * @return ID de la función en formato String (útil para APIs y UI).
     */
    public String getIdString() {
        return idFuncion.toString();
    }

    /**
     * Convierte un String en ObjectId para asignar el ID.
     *
     * @param idImportado ID en formato String (ej: "507f1f77bcf86cd799439011").
     */
    public void setIdString(String idImportado) {
        if (idImportado != null && !idImportado.isEmpty()) {
            this.idFuncion = new ObjectId(idImportado);
        } else {
            this.idFuncion = null;
        }
    }

    /**
     * @return Precio de la entrada para esta función.
     */
    public Double getPrecio() {
        return precio;
    }

    /**
     * @param precio Establece el precio de la entrada.
     */
    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    /**
     * @return ID del empleado responsable de registrar la función.
     */
    public String getIdEmpleado() {
        return idEmpleado;
    }

    /**
     * @param idEmpleado Establece el ID del empleado responsable.
     */
    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    /**
     * Representación en String de la función.
     *
     * @return Detalles completos de la función, incluyendo ID, sala, película,
     * horario, precio y empleado.
     */
    @Override
    public String toString() {
        return "Funcion{" + "idFuncion=" + idFuncion + ", sala=" + sala + ", pelicula=" + pelicula + ", fechaHora=" + fechaHora + ", precio=" + precio + ", idEmpleado=" + idEmpleado + '}';
    }

}
