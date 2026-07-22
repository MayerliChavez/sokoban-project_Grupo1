package ec.edu.epn.sokoban.model.reglas;

import ec.edu.epn.sokoban.model.escenario.Caja;
import ec.edu.epn.sokoban.model.escenario.Casilla;
import ec.edu.epn.sokoban.model.escenario.Personaje;
import ec.edu.epn.sokoban.model.escenario.Tablero;

/**
 * Validador y coordinador central del movimiento y colisiones del juego.
 */
public final class GestorColisiones {

    public GestorColisiones() {
    }

    /**
     * Procesa y valida el movimiento del personaje y empuje de cajas sobre el tablero.
     *
     * @param tablero        tablero activo
     * @param personaje      personaje a desplazar
     * @param filaDestino    fila de la casilla destino
     * @param columnaDestino columna de la casilla destino
     * @return true si el movimiento se realizó con éxito; false en caso contrario
     */
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
            
            Casilla casillaDestino = tablero.obtenerCasilla(filaDestino, columnaDestino);
            if (!casillaDestino.getGestorAcciones().puedenIngresarAcciones(tablero, personaje)) {
                return false;
            }
            
            tablero.actualizarCasilla(filaDestino, columnaDestino, personaje);
            casillaDestino.getGestorAcciones().ejecutarAcciones(casillaDestino, tablero, personaje);
            return true;
        }

        int filaDestinoCaja = filaDestino + deltaFila;
        int columnaDestinoCaja = columnaDestino + deltaColumna;
        if (!tablero.estaDentroDelTablero(filaDestinoCaja, columnaDestinoCaja)
                || !tablero.esCeldaTransitable(filaDestinoCaja, columnaDestinoCaja)
                || tablero.obtenerCaja(filaDestinoCaja, columnaDestinoCaja) != null) {
            return false;
        }

        Casilla casillaDestinoCaja = tablero.obtenerCasilla(filaDestinoCaja, columnaDestinoCaja);
        if (!casillaDestinoCaja.getGestorAcciones().puedenIngresarAcciones(tablero, caja)) {
            return false;
        }

        tablero.actualizarCasilla(filaDestinoCaja, columnaDestinoCaja, caja);
        tablero.actualizarCasilla(filaDestino, columnaDestino, personaje);
        casillaDestinoCaja.getGestorAcciones().ejecutarAcciones(casillaDestinoCaja, tablero, caja);
        return true;
    }
}
