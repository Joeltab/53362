import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.io.FileNotFoundException;
import java.io.IOException;

import excepciones.CupoExcedidoException;
import modelo.Estudiante;
import modelo.EventoUniversitario;
import modelo.Inscripcion;
import modelo.Sala;
import modelo.actividades.Actividad;
import modelo.actividades.Charla;
import modelo.actividades.Curso;
import modelo.actividades.Taller;
import modelo.certificacion.Certificable;

public class Main {
    public static void main(String[] args) {
        // Se fuerza la salida por consola a UTF-8 para que tildes y "ñ" se muestren
        // correctamente sin importar el charset por defecto del sistema operativo.
        System.setOut(new java.io.PrintStream(System.out, true, java.nio.charset.StandardCharsets.UTF_8));

        Scanner leer = new Scanner(System.in, java.nio.charset.StandardCharsets.UTF_8);
        String titulo;
        double costoBase;
        boolean gratuito = true;
        String id = "";
        int idsala = 0;
        String respuesta;
        boolean continuar = false;

        // Registro de estudiantes (TP2 - Ejercicio 2, inciso a)
        List<Estudiante> estudiantes = new ArrayList<>();
        System.out.println("REGISTRO DE ESTUDIANTES");
        System.out.println("=====================");
        do {
            System.out.println("Ingrese legajo del estudiante: ");
            String legajo = leer.nextLine();
            System.out.println("Ingrese nombre y apellido del estudiante: ");
            String nomyape = leer.nextLine();
            Estudiante estudiante = new Estudiante(legajo, nomyape);
            estudiantes.add(estudiante);
            System.out.println("Desea crear otro estudiante S/N?");
            respuesta = leer.nextLine().trim().toLowerCase();
            continuar = respuesta.equals("s") || respuesta.equals("si") || respuesta.equals("sí");
        } while (continuar);

        System.out.println("REGISTRO DE EVENTOS");
        System.out.println("=====================");
        do {
            // Creación del evento (TP2 - Ejercicio 2, inciso b)
            System.out.println("Ingrese un título para el evento: ");
            titulo = leer.nextLine();
            System.out.println("Ingrese el costo base: ");
            costoBase = leer.nextDouble();
            leer.nextLine();
            System.out.println("El evento tendra costo para los participantes S/N?");
            respuesta = leer.nextLine().trim().toLowerCase();
            gratuito = !(respuesta.equals("s") || respuesta.equals("si") || respuesta.equals("sí"));

            EventoUniversitario evento = new EventoUniversitario(id, titulo, costoBase, gratuito);

            // Asignación de sala (TP2 - Ejercicio 2, inciso c)
            System.out.println("Ingrese el nombre de la sala: ");
            String nombresala = leer.nextLine();
            Sala sala = new Sala(idsala, nombresala);
            evento.asignarSala(sala);
            idsala++;

            // Registro de actividades, incluyendo el nuevo tipo Curso
            // (TP2 - Ejercicio 2, inciso d)
            System.out.println("REGISTRO DE ACTIVIDADES");
            System.out.println("=====================");
            int idActividad = 1;
            do {
                System.out.println("Ingrese el titulo de la actividad: ");
                String tituloActividad = leer.nextLine();
                System.out.println("Ingrese el cupo máximo de la actividad: ");
                int cupoMaximo = leer.nextInt();
                leer.nextLine();

                System.out.println("La actividad es una charla, un taller o un curso?");
                String tipo = leer.nextLine().trim().toLowerCase();

                evento.crearActividad(idActividad, tituloActividad, cupoMaximo, tipo, leer);

                System.out.println("Desea crear otra actividad S/N?");
                respuesta = leer.nextLine().trim().toLowerCase();
                continuar = respuesta.equals("s") || respuesta.equals("si") || respuesta.equals("sí");
                idActividad++;
            } while (continuar);

            // Inscripción de estudiantes (TP2 - Ejercicio 2, inciso e)
            // y manejo de CupoExcedidoException (TP2 - Ejercicio 1, inciso a)
            try {
                System.out.println("INSCRIPCIÓN DE ESTUDIANTES");
                System.out.println("=====================");
                do {
                    System.out.println("Ingrese legajo del estudiante a inscribir: ");
                    String legajo = leer.nextLine();
                    System.out.println("Ingrese id de la actividad: ");
                    int idActividadElegida = leer.nextInt();
                    leer.nextLine();

                    for (Estudiante estudiante : estudiantes) {
                        if (estudiante.getLegajo().equals(legajo)) {
                            Actividad actividadElegida = evento.getActividades().get(idActividadElegida - 1);
                            Inscripcion inscripcion = actividadElegida.inscribir(estudiante);
                        }
                    }
                    System.out.println("Desea generar otra inscripción? S/N");
                    respuesta = leer.nextLine().trim().toLowerCase();
                    continuar = respuesta.equals("s") || respuesta.equals("si") || respuesta.equals("sí");
                } while (continuar);
            } catch (CupoExcedidoException e) {
                System.out.println("Error al inscribirse: " + e.getMessage());
            }

            // Persistencia por serialización (TP2 - Ejercicio 1, incisos b, c, d, e, f)
            try {
                evento.persistirEvento();
                EventoUniversitario copiaDesdeArchivo = evento.recuperarEvento(evento.getId());
                System.out.println("--- Evento original ---");
                evento.mostrarDatos();
                System.out.println("--- Evento recuperado desde archivo (caso exitoso) ---");
                copiaDesdeArchivo.mostrarDatos();
            } catch (FileNotFoundException e) {
                System.out.println("Error 01: No se encontró el archivo del evento: " + e.getMessage());
            } catch (ClassNotFoundException e) {
                System.out.println("No fue posible reconstruir el objeto almacenado: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("Se produjo un error de entrada/salida: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Ocurrió un error inesperado durante la persistencia: " + e.getMessage());
            } finally {
                System.out.println("Flujo de persistencia (persistir/recuperar) finalizado.");
            }

            // Caso fallido controlado por excepción: se intenta recuperar un evento con
            // un id inexistente para evidenciar el catch de FileNotFoundException.
            try {
                evento.recuperarEvento("ID-INEXISTENTE");
            } catch (FileNotFoundException e) {
                System.out.println("Error 01: No se encontró el archivo del evento: " + e.getMessage());
            } catch (ClassNotFoundException e) {
                System.out.println("No fue posible reconstruir el objeto almacenado: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("Se produjo un error de entrada/salida: " + e.getMessage());
            }

            // Emisión de certificados (TP2 - Ejercicio 2, incisos f y g)
            System.out.println("EMISIÓN DE CERTIFICADOS");
            System.out.println("=====================");
            for (Actividad actividad : evento.getActividades()) {
                if (actividad instanceof Certificable certificable) {
                    System.out.println("CERTIFICADOS EMITIDOS PARA LA ACTIVIDAD " + actividad.getTitulo());
                    for (Inscripcion inscripcion : actividad.getInscripciones()) {
                        String certificado = certificable.generarCertificado(inscripcion.getEstudiante());
                        System.out.println(certificado);
                    }
                }
            }

            // Filtrado tipado con generics acotados (TP2 - Ejercicio 3, incisos d, e, g)
            System.out.println("FILTRADO DE ACTIVIDADES POR TIPO");
            System.out.println("=====================");
            List<Charla> charlas = evento.filtrarActividadesPorTipo(Charla.class);
            List<Taller> talleres = evento.filtrarActividadesPorTipo(Taller.class);
            List<Curso> cursos = evento.filtrarActividadesPorTipo(Curso.class);
            System.out.println("Charlas encontradas: " + charlas.size());
            System.out.println("Talleres encontrados: " + talleres.size());
            System.out.println("Cursos encontrados: " + cursos.size());

            // Costo de materiales con wildcard (TP2 - Ejercicio 3, inciso f)
            System.out.println("Costo de materiales de charlas: $" + evento.calcularCostoMateriales(charlas));
            System.out.println("Costo de materiales de talleres: $" + evento.calcularCostoMateriales(talleres));
            System.out.println("Costo de materiales de cursos: $" + evento.calcularCostoMateriales(cursos));
            System.out.println("Costo de materiales de todas las actividades: $" + evento.calcularCostoMateriales(evento.getActividades()));
            System.out.println("Desea crear otro evento S/N?");
            respuesta = leer.nextLine().trim().toLowerCase();
            continuar = respuesta.equals("s") || respuesta.equals("si") || respuesta.equals("sí");
        } while (continuar);
        System.out.println("TOTAL DE EVENTOS CREADOS: " + EventoUniversitario.getCantidadEventos());
    }
}
