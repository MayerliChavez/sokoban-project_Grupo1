package ec.edu.epn.sokoban.model.reglas;

import ec.edu.epn.sokoban.model.escenario.Caja;
import ec.edu.epn.sokoban.model.escenario.Casilla;
import ec.edu.epn.sokoban.model.escenario.GestorAcciones;
import ec.edu.epn.sokoban.model.escenario.Personaje;
import ec.edu.epn.sokoban.model.escenario.Tablero;
import ec.edu.epn.sokoban.model.escenario.Teletransportacion;

/**
 * Las colisiones entre el personaje, las cajas y el tablero fueron validadas
 * y coordinadas exclusivamente por esta clase. El conocimiento de tipos
 * concretos
 * de terreno fue descartado; la transitabilidad fue verificada mediante
 * polimorfismo
 * a través de la interfaz {@code Transitable}.
 *
 * <p>
 * Tras cada movimiento válido, las acciones dinámicas registradas en el
 * {@link GestorAcciones} fueron detonadas de forma automática, tal como fue
 * especificado en el diagrama UML revisado.
 * </p>
 */
public final class GestorColisiones {

    /**
     * Un gestor de colisiones fue inicializado.
     */
    public GestorColisiones() {
    }

    /**
     * El movimiento del personaje fue procesado y validado contra el estado del
     * tablero.
     *
     * <p>
     * La transitabilidad de la casilla destino fue verificada mediante
     * polimorfismo,
     * consultando si la casilla implementa la interfaz {@code Transitable}, sin
     * depender
     * de comprobaciones por tipo concreto de terreno.
     * </p>
     *
     * <p>
     * Tras un movimiento exitoso, las acciones dinámicas registradas en el
     * {@link GestorAcciones} fueron detonadas automáticamente mediante
     * {@link GestorAcciones#ejecutarAcciones()}.
     * </p>
     *
     * @param tablero        el tablero sobre el que el movimiento fue procesado
     * @param personaje      el personaje cuyo desplazamiento fue validado
     * @param filaDestino    la fila de la casilla destino
     * @param columnaDestino la columna de la casilla destino
     * @return {@code true} si el movimiento fue ejecutado con éxito; {@code false}
     *         en caso contrario
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
            // La transitabilidad fue verificada mediante polimorfismo (instanceof
            // Transitable).
            if (!tablero.esCeldaTransitable(filaDestino, columnaDestino)) {
                return false;
            }
            
            // La casilla de destino fue extraída antes de actualizar el tablero
            Casilla casillaDestino = tablero.obtenerCasilla(filaDestino, columnaDestino);
            
            tablero.actualizarCasilla(filaDestino, columnaDestino, personaje);
            
            // Las acciones correspondientes a la casilla son ejecutadas
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

        // Si la casilla destino de la caja es un portal, validamos que su pareja esté libre y transitable
        if (tablero.esPortal(filaDestinoCaja, columnaDestinoCaja)) {
            Teletransportacion tele = tablero.obtenerTeletransportacion(filaDestinoCaja, columnaDestinoCaja);
            if (tele != null) {
                int fB = tele.getFilaDestino();
                int cB = tele.getColumnaDestino();
                if (!tablero.estaDentroDelTablero(fB, cB)
                        || !tablero.esCeldaTransitable(fB, cB)
                        || tablero.obtenerCaja(fB, cB) != null
                        || (tablero.getPersonaje() != null 
                            && tablero.getPersonaje().getFila() == fB 
                            && tablero.getPersonaje().getColumna() == cB)) {
                    return false; // Se bloquea el movimiento como si fuera una pared
                }
            }
        }

        // La casilla de destino de la caja fue extraída antes de actualizar el tablero
        Casilla casillaDestinoCaja = tablero.obtenerCasilla(filaDestinoCaja, columnaDestinoCaja);

        tablero.actualizarCasilla(filaDestinoCaja, columnaDestinoCaja, caja);
        tablero.actualizarCasilla(filaDestino, columnaDestino, personaje);
        
        // Las acciones correspondientes a la casilla son ejecutadas
        casillaDestinoCaja.getGestorAcciones().ejecutarAcciones(casillaDestinoCaja, tablero, caja);
        return true;
    }
}
