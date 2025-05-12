/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;


/**
 * Clase Asiento que referencia a un Asiento de una sala de cine
 * @author Ramon Valencia 
 */
public class Asiento {
    
    private String numero; // Numero del asiento

    /**
     * Constructor vacio necesario para MongoDB
     */
    public Asiento() {
    }
    /**
     * Constructor con todos los metodos
     * @param numero 
     */
    public Asiento(String numero) {
        this.numero = numero;
    }
    /**
     * Metodo para obtener el numero del asiento
     * @return 
     */
    public String getNumero() {
        return numero;
    }
    /** 
     * Metodo para guardar el numero del asiento
     * @param numero 
     */
    public void setNumero(String numero) {
        this.numero = numero;
    }
    /**
     * ToString de Asiento
     * @return 
     */
    @Override
    public String toString() {
        return "Asiento{"
                + ", numero=" + numero
                + '}';
    }
}
