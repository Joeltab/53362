# TP2 - Programacion Orientada a Objetos en Java

Trabajo Practico 2 de Paradigmas de Programacion (UTN FRM). Hecho hasta el ejercicio 3.

## Que hice en cada ejercicio

**Ejercicio 1:** organice las clases en paquetes (modelo, modelo.actividades, excepciones). Cree la excepcion CupoExcedidoException que salta cuando se intenta inscribir a alguien y ya no hay cupo. Tambien agregue Serializable a las clases para poder guardar y leer un evento en un archivo (persistirEvento / recuperarEvento en EventoUniversitario). En el Main atrapo las excepciones de FileNotFoundException, ClassNotFoundException e IOException por separado para que cada error muestre un mensaje distinto.

**Ejercicio 2:** agregue la interfaz Certificable (paquete modelo.certificacion) para poder emitir certificados. Taller y Curso la implementan, Charla no (las charlas no dan certificado). Tambien agregue Curso como actividad nueva. En el Main recorro las actividades y con instanceof me fijo cuales son Certificable para generarles el certificado.

**Ejercicio 3:** agregue dos metodos en EventoUniversitario usando generics:
- filtrarActividadesPorTipo(Class<T> tipo) -> devuelve la lista de actividades filtrada por tipo (Charla, Taller o Curso)
- calcularCostoMateriales(List<? extends Actividad> actividades) -> suma el costo de materiales de cualquier lista de actividades (uso wildcard porque sino List<Taller> no entra donde pide List<Actividad>)

El ejercicio 4 (hilos y clase anidada) no esta hecho, quedo pendiente.

## Estructura

```
src/
  Main.java
  excepciones/
    CupoExcedidoException.java
  modelo/
    EventoUniversitario.java
    Sala.java
    Estudiante.java
    Inscripcion.java
    certificacion/
      Certificable.java
    actividades/
      Actividad.java
      Charla.java
      Taller.java
      Curso.java
```

## Como probarlo

Abrir la carpeta en IntelliJ, correr Main.java y seguir lo que pide por consola (primero pide cargar estudiantes, despues el evento, la sala, las actividades y las inscripciones).

Probado con Java 21, corre sin tirar ninguna excepcion.
