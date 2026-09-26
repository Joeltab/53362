package excepciones;

/*
 * TP2 - Ejercicio 1
 * Excepción propia y CHEQUEADA (extiende Exception, no RuntimeException).
 * Representa una condición anticipable del negocio: se intenta inscribir
 * a un estudiante en una actividad que ya alcanzó su cupo máximo.
 * Al ser chequeada, el compilador obliga a quien invoque un método que
 * pueda lanzarla a atraparla (try-catch) o a declararla (throws).
 */
public class CupoExcedidoException extends Exception {

    public CupoExcedidoException(String mensaje) {
        super(mensaje);
    }

}
