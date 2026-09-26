package modelo.actividades;

import modelo.Estudiante;
import modelo.certificacion.Certificable;

// TP2 - Ejercicio 2

public class Curso extends Actividad implements Certificable {

    private int nivel;

    public Curso(int id, String titulo, int cupo, int nivel) {
        super(id, titulo, cupo);
        this.nivel = nivel;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    // POLIMORFISMO

    @Override
    public double calcularCostoMateriales() {
        // Regla de costo por nivel: nivel 1 -> $1000, nivel 2 -> $2000, nivel 3 -> $3000,
        // cualquier otro nivel -> $0.
        switch (nivel) {
            case 1:
                return 1000.0;
            case 2:
                return 2000.0;
            case 3:
                return 3000.0;
            default:
                return 0.0;
        }
    }

    @Override
    public String getTipo() {
        // TP2 - Ejercicio 1: nombre calificado con paquete (modelo.actividades.Curso)
        return this.getClass().getName();
    }

    // TP2 - Ejercicio 2
    @Override
    public String generarCertificado(Estudiante estudiante) {
        return "Certificado emitido por " + ENTIDAD_EMISORA
                + ": se deja constancia de que " + estudiante.getNombre()
                + " participó en el curso \"" + getTitulo() + "\" (nivel " + nivel + ").";
    }
}
