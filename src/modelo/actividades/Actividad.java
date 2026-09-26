package modelo.actividades;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import excepciones.CupoExcedidoException;
import modelo.Estudiante;
import modelo.Inscripcion;

public abstract class Actividad implements Serializable {
    // TP2 - Ejercicio 1: implements Serializable
    // Actividad compone a EventoUniversitario; al serializar el evento se serializan
    // en cascada todas sus actividades (y, por herencia, también sus subclases
    // Charla, Taller y Curso).

    private int id;
    private String titulo;
    private int cupoMaximo;
    private List<Inscripcion> inscripciones;
    public static final int CUPO_MINIMO;
    static {
        CUPO_MINIMO = 2;
        System.out.println("Inicializador estático: se cargó la clase Actividad");
    }

    public Actividad(int id, String titulo, int cupo) {
        this.id = id;
        this.titulo = titulo;
        if (cupo < CUPO_MINIMO) {
            // No es una situación de error: se resuelve fijando el cupo en el mínimo,
            // por eso no se lanza ninguna excepción aquí.
            this.cupoMaximo = CUPO_MINIMO;
        } else {
            this.cupoMaximo = cupo;
        }
        this.inscripciones = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        if (titulo == null || titulo.isBlank()) {
            return;
        }
        this.titulo = titulo;
    }

    public int getCupoMaximo() {
        return cupoMaximo;
    }

    public void setCupoMaximo(int cupo) {
        if (cupo < CUPO_MINIMO) {
            this.cupoMaximo = CUPO_MINIMO;
        } else {
            this.cupoMaximo = cupo;
        }
    }

    // TP2 - Ejercicio 1
    // Se agrega "throws CupoExcedidoException" a la firma (declaración) y se lanza
    // la excepción con "throw" cuando el cupo está lleno: es una condición anticipable
    // que el método no puede resolver por sí mismo, así que se delega a quien invoque
    // inscribir() (la clase App) mediante una excepción CHEQUEADA.
    public Inscripcion inscribir(Estudiante estudiante) throws CupoExcedidoException {
        if (inscripciones.size() >= cupoMaximo) {
            throw new CupoExcedidoException("No se puede inscribir al estudiante "
                    + estudiante.getNombre() + ". Cupo máximo alcanzado.");
        }
        Inscripcion inscripcion = new Inscripcion(this, estudiante, LocalDate.now(), "REGISTRADA");
        inscripciones.add(inscripcion);
        return inscripcion;
    }

    public List<Inscripcion> getInscripciones() {
        return inscripciones;
    }

    public void mostrarInscripciones() {
        if (inscripciones.isEmpty()) {
            System.out.println("Sin inscripciones registradas");
        } else {
            System.out.println("Inscripciones registradas: ");
            for (Inscripcion inscripcion : inscripciones) {
                System.out.println(" " + inscripcion.getFecha() + " - " + inscripcion.getEstado()
                        + " - " + inscripcion.getEstudiante().getNombre()
                        + " (Legajo: " + inscripcion.getEstudiante().getLegajo() + ")");
            }
        }
    }

    // Métodos abstractos (TP1 - Ejercicio 3): cada subtipo concreto de actividad
    // (Charla, Taller, Curso) define su propio costo de materiales y su propio tipo.
    public abstract double calcularCostoMateriales();

    public abstract String getTipo();

    public final void mostrarIdentificacion() {
        System.out.println("[" + id + "] " + getTipo() + ": " + titulo + " - Cupo: " + cupoMaximo);
    }

}
