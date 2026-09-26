package modelo.certificacion;

import modelo.Estudiante;

// TP2 - Ejercicio 2

public interface Certificable {

    String ENTIDAD_EMISORA = "UTN - FRM";

    String generarCertificado(Estudiante estudiante);
}
