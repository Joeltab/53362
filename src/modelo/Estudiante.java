package modelo;

import java.io.Serializable;

public class Estudiante implements Serializable {
    // TP2 - Ejercicio 1: implements Serializable
    // Al serializar un EventoUniversitario, la serialización viaja en cascada por todo
    // el grafo de objetos alcanzable desde él (sala, actividades, inscripciones,
    // estudiantes). Por eso todas las clases compuestas/agregadas/asociadas deben
    // ser serializables, entre ellas Estudiante.

    private String legajo;
    private String nombre;

    public Estudiante(String legajo, String nombre) {
        this.legajo = legajo;
        this.nombre = nombre;
    }

    public String getLegajo() {
        return legajo;
    }

    public void setLegajo(String legajo) {
        this.legajo = legajo;
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
