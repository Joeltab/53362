package modelo;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import modelo.actividades.Actividad;
import modelo.actividades.Charla;
import modelo.actividades.Curso;
import modelo.actividades.Taller;

public class EventoUniversitario implements Serializable {
    // TP2 - Ejercicio 1: implements Serializable
    // Es la clase raíz que se persiste con persistirEvento(); para que la serialización
    // en cascada funcione, todo lo que cuelga de ella (Sala, Actividad y sus subtipos,
    // Inscripcion, Estudiante) también debe implementar Serializable.

    private final String id;
    private String titulo;
    private double costoBase;
    private boolean gratuito;

    private Sala sala;
    private List<Actividad> actividades;

    private static int cantidadEventos;
    static {
        cantidadEventos = 0;
        System.out.println("Inicializador estático: se cargo la clase EventoUniversitario.");
    }

    public EventoUniversitario(String id, String titulo, double costoBase, boolean gratuito) {
        cantidadEventos++;
        this.id = "EVT-" + cantidadEventos;
        if (gratuito) {
            this.costoBase = 0;
        } else {
            this.costoBase = costoBase;
        }
        setTitulo(titulo);

        this.actividades = new ArrayList<>(); //Relación de composición
    }

    public EventoUniversitario(EventoUniversitario otro) {
        this(otro.id + "-COPIA", otro.titulo, otro.costoBase, otro.gratuito);
    }

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        if (titulo != null && !titulo.isEmpty()) {
            this.titulo = titulo;
        }
    }

    public double calcularCostoEstimado() {
        if (gratuito) {
            return 0;
        }
        //Añadimos el costo de todas las actividades
        double costoTotal = costoBase;
        for (Actividad actividad : actividades) {
            costoTotal += actividad.calcularCostoMateriales();
        }
        return costoTotal * 1.21;
    }

    public static int getCantidadEventos() {
        return cantidadEventos;
    }

    public void mostrarDatos() {
        System.out.println("==============================================================");
        System.out.println("Evento Codigo: " + id);
        System.out.println("Titulo: " + titulo);
        System.out.println("Costo: " + this.calcularCostoEstimado());
        System.out.print("Sala: ");
        if (sala != null) {
            System.out.println(sala.getNombre());
        } else {
            System.out.println("Sin sala");
        }
        System.out.println("------------------------------------------------------------");
        System.out.println("Actividades: ");
        for (Actividad actividad : actividades) {
            actividad.mostrarIdentificacion();
            actividad.mostrarInscripciones();
        }
        System.out.println("------------------------------------------------------------");
        System.out.println("==============================================================");
    }

    //Métodos del Ejercicio 2 (TP1)

    public Sala getSala() {
        return sala;
    }

    public void asignarSala(Sala sala) {
        this.sala = sala;
    }

    public List<Actividad> getActividades() {
        return Collections.unmodifiableList(actividades);
    }

    //Métodos del ejercicio 3 (TP1) - actualizado en TP2 Ejercicio 2 para incorporar Curso

    public void crearActividad(int id, String titulo, int cupo, String tipoActividad, Scanner leer) {
        // Ajuste menor de implementación: se recibe el mismo Scanner que ya usa App/Main
        // en lugar de instanciar uno nuevo sobre System.in. Dos Scanner distintos leyendo
        // del mismo flujo estándar de entrada pueden perder o duplicar líneas por el
        // manejo interno de buffers de cada uno.
        switch (tipoActividad) {
            case "charla":
                System.out.println("Ingrese el nombre del disertante para la charla");
                String disertante = leer.nextLine();
                Actividad charla = new Charla(id, titulo, cupo, disertante);
                this.actividades.add(charla);
                break;
            case "taller":
                System.out.println("Requiere uso de Notebooks? S/N");
                String respuesta = leer.nextLine().trim().toLowerCase();
                boolean notebook = false;
                if (respuesta.equals("s") || respuesta.equals("si") || respuesta.equals("sí")) {
                    notebook = true;
                }
                Actividad taller = new Taller(id, titulo, cupo, notebook);
                this.actividades.add(taller);
                break;
            // TP2 - Ejercicio 2: nuevo tipo de actividad Curso. La jerarquía "escala"
            // sin tocar lo existente: solo hizo falta crear la clase Curso y agregar
            // este caso; calcularCostoEstimado, mostrarDatos, persistencia e
            // inscripciones siguen funcionando sin modificación porque operan sobre
            // el tipo Actividad.
            case "curso":
                System.out.println("Ingrese el nivel del curso (1, 2 o 3): ");
                int nivel = leer.nextInt();
                leer.nextLine();
                Actividad curso = new Curso(id, titulo, cupo, nivel);
                this.actividades.add(curso);
                break;
            default:
                System.out.println("No se encontró el tipo de actividad");
        }

    }

    //Métodos TP2 Ejercicio 1: persistencia por serialización de objetos

    public boolean persistirEvento() throws IOException {
        String nombreArchivo = "evento_" + this.id + ".dat";
        // try-with-resources: garantiza el cierre del flujo aun ante una excepción,
        // cumpliendo el mismo rol que exigiría un finally con close() explícito.
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nombreArchivo))) {
            oos.writeObject(this);
            return true;
        }
    }

    public EventoUniversitario recuperarEvento(String id) throws IOException, ClassNotFoundException {
        String nombreArchivo = "evento_" + id + ".dat";
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nombreArchivo))) {
            // readObject() devuelve Object: se requiere casteo a EventoUniversitario.
            // Puede lanzar ClassNotFoundException si la clase no está disponible al
            // reconstruir el objeto, por eso la firma declara ambas excepciones.
            return (EventoUniversitario) ois.readObject();
        }
    }

    //TP2 - Ejercicio 3
    public <T extends Actividad> List<T> filtrarActividadesPorTipo(Class<T> tipo) {
        List<T> resultado = new ArrayList<>();
        for (Actividad actividad : actividades) {
            if (tipo.isInstance(actividad)) {
                resultado.add(tipo.cast(actividad));
            }
        }
        return resultado;
    }


    //TP2 - Ejercicio 3

    public double calcularCostoMateriales(List<? extends Actividad> actividades) {
        double total = 0.0;
        for (Actividad actividad : actividades) {
            total += actividad.calcularCostoMateriales();
        }
        return total;
    }

}
