package ec.edu.epn.sokoban.model.reglas;

import ec.edu.epn.sokoban.model.escenario.Caja;
import ec.edu.epn.sokoban.model.escenario.Personaje;
import ec.edu.epn.sokoban.model.escenario.Tablero;

/**
 * Valida y coordina exclusivamente las colisiones entre el personaje, las
 * cajas y el tablero. No conoce tipos concretos de terreno ni direcciones.
 */
public final class GestorColisiones {

    public boolean procesarMovimiento(
            Tablero tablero,
            Personaje personaje,
            int filaDestino,
            int columnaDestino) {
        if (tablero == null || personaje == null || tablero.getPersonaje() != personaje) {
            return false;
        }

        int deltaFila = filaDestino - personaje.getFila();
        int deltaColumna = columnaDestino - personaje.getColumna();
        if (Math.abs(deltaFila) + Math.abs(deltaColumna) != 1
                || !tablero.estaDentroDelTablero(filaDestino, columnaDestino)) {
            return false;
        }

        Caja caja = tablero.obtenerCaja(filaDestino, columnaDestino);
        if (caja == null) {
            if (!tablero.esCeldaTransitable(filaDestino, columnaDestino)) {
                return false;
            }
            tablero.actualizarCasilla(filaDestino, columnaDestino, personaje);
            return true;
        }

        int filaDestinoCaja = filaDestino + deltaFila;
        int columnaDestinoCaja = columnaDestino + deltaColumna;
        if (!tablero.estaDentroDelTablero(filaDestinoCaja, columnaDestinoCaja)
                || !tablero.esCeldaTransitable(filaDestinoCaja, columnaDestinoCaja)
                || tablero.obtenerCaja(filaDestinoCaja, columnaDestinoCaja) != null) {
            return false;
        }

        tablero.actualizarCasilla(filaDestinoCaja, columnaDestinoCaja, caja);
        tablero.actualizarCasilla(filaDestino, columnaDestino, personaje);
        return true;
    }
}
