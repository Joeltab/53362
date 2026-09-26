package modelo.actividades;

// TP2 - Ejercicio 2
// A propósito, Charla NO implementa Certificable: el enunciado pide certificar
// talleres y cursos, pero no charlas. Al no implementar la interfaz, una Charla
// queda naturalmente excluida cuando App filtra actividades con "instanceof
// Certificable", sin necesidad de preguntar por su clase concreta.
public class Charla extends Actividad {

    private String disertante;

    public Charla(int id, String titulo, int cupo, String disertante) {
        super(id, titulo, cupo);
        this.disertante = disertante;
    }

    public String getDisertante() {
        return disertante;
    }

    public void setDisertante(String disertante) {
        if (disertante == null || disertante.isBlank()) {
            return;
        }
        this.disertante = disertante;
    }

    // POLIMORFISMO

    @Override
    public double calcularCostoMateriales() {
        return 0.0;
    }

    @Override
    public String getTipo() {
        // TP2 - Ejercicio 1: se usa getClass().getName() (nombre calificado, con
        // paquete) en lugar de getSimpleName() como en el TP1, para que la consola
        // evidencie que la clase ahora vive dentro de un paquete (modelo.actividades.Charla).
        return this.getClass().getName();
    }

}
