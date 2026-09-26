package modelo.actividades;

import modelo.Estudiante;
import modelo.certificacion.Certificable;

// TP2 - Ejercicio 2
// Taller extiende Actividad (hereda su implementación y estado común) E implementa
// Certificable (adquiere además el "tipo" certificable). Java no permite herencia
// múltiple de clases, pero sí que una clase implemente varias interfaces: por eso
// esta combinación se llama "pseudo-herencia múltiple de tipos" (se hereda un único
// cuerpo de implementación, pero se participa de varios tipos).
public class Taller extends Actividad implements Certificable {

    private boolean requiereNotebook;

    public Taller(int id, String titulo, int cupo, boolean requiereNotebook) {
        super(id, titulo, cupo);
        this.requiereNotebook = requiereNotebook;
    }

    public boolean getRequiereNotebook() {
        return requiereNotebook;
    }

    public void setRequiereNotebook(boolean requiereNotebook) {
        this.requiereNotebook = requiereNotebook;
    }

    // POLIMORFISMO

    @Override
    public double calcularCostoMateriales() {
        if (requiereNotebook) {
            return 5000.0;
        }
        return 2000.0;
    }

    @Override
    public String getTipo() {
        // TP2 - Ejercicio 1: nombre calificado con paquete (modelo.actividades.Taller)
        return this.getClass().getName();
    }

    // TP2 - Ejercicio 2
    @Override
    public String generarCertificado(Estudiante estudiante) {
        return "Certificado emitido por " + ENTIDAD_EMISORA
                + ": se deja constancia de que " + estudiante.getNombre()
                + " participó en el taller \"" + getTitulo() + "\".";
    }
}
