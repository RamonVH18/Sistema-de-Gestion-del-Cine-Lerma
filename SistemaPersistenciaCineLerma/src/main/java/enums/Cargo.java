/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package enums;

/**
 *
 * @author isaac
 */
public enum Cargo {
    // la descripcion para usarla en la presentacion y que se vea bonito
    PALOMITERO("Palomitero"),
    REVISION_BOLETOS("Revisión de Boletos"),
    CAJERO("Cajero"),
    LIMPIEZA("Personal de Limpieza"),
    SEGURIDAD("Personal de Seguridad"),
    PROYECCIONISTA("Proyeccionista"),
    TECNICO_SONIDO("Técnico de Sonido"),
    MANTENIMIENTO("Personal de Mantenimiento"),
    JEFE_PISO("Jefe de Piso"),
    JEFE_CAJA("Jefe de Caja"),
    JEFE_ALIMENTOS("Jefe de Alimentos"),
    GERENTE("Gerente");

    private final String descripcion;

    Cargo(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
