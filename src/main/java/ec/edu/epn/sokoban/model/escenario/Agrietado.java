package ec.edu.epn.sokoban.model.escenario;

import ec.edu.epn.sokoban.model.interfaces.Accion;

import java.util.HashSet;
import java.util.Set;

/**
 * Suelo que pasa de intacto a agrietado y luego a roto antes de reiniciar el nivel.
 */
public class Agrietado implements Accion {
    private static boolean reinicioSolicitado;
    private static final Set<String> suelosRotos = new HashSet<>();
    private boolean agrietado;
    private boolean roto;

    @Override
    public void iniciarAccion(Casilla casillaActual, Tablero tablero, Casilla entidad) {
        if (!(entidad instanceof Personaje) && !(entidad instanceof Caja)) {
            return;
        }

        if (roto) {
            reinicioSolicitado = true;
        } else if (agrietado) {
            roto = true;
            suelosRotos.add(clave(casillaActual.getFila(), casillaActual.getColumna()));
        } else {
            agrietado = true;
        }
    }

    public static boolean isReinicioSolicitado() {
        return reinicioSolicitado;
    }

    public static boolean estaRoto(int fila, int columna) {
        return suelosRotos.contains(clave(fila, columna));
    }

    public static void limpiarSolicitudReinicio() {
        reinicioSolicitado = false;
        suelosRotos.clear();
    }

    private static String clave(int fila, int columna) {
        return fila + ":" + columna;
    }

    @Override
    public String getSpriteKey() {
        if (roto) {
            return "AGRIETADO_ROTO";
        }
        return agrietado ? "AGRIETADO" : null;
    }
}
