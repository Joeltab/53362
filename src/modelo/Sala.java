package modelo;

import java.io.Serializable;

public class Sala implements Serializable {
    // TP2 - Ejercicio 1: implements Serializable
    // Sala está agregada a EventoUniversitario, por lo tanto también debe ser
    // serializable para que la persistencia del evento no falle.

    private int id;
    private String nombre;

    public Sala(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            return;
        }
        this.nombre = nombre;
    }
}
