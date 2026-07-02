package ec.edu.epn.sokoban.model.historial;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * El historial de estados de partida es gestionado mediante una estructura LIFO.
 */
public class HistorialMovimientos {
    private int movimientosContador;
    private Deque<PartidaMomento> historial;

    /**
     * Un historial vacio de movimientos es inicializado.
     */
    public HistorialMovimientos() {
        this.historial = new ArrayDeque<>();
        this.movimientosContador = 0;
    }

    /**
     * El contador de movimientos registrados es retornado.
     *
     * @return cantidad de movimientos registrados
     */
    public int getMovimientosContador() {
        return movimientosContador;
    }

    /**
     * El contador de movimientos registrados es actualizado.
     *
     * @param movimientosContador nuevo valor del contador
     */
    public void setMovimientosContador(int movimientosContador) {
        this.movimientosContador = Math.max(0, movimientosContador);
    }

    /**
     * El estado actual del tablero fue registrado en la estructura.
     *
     * @param e estado de partida que sera almacenado
     */
    public void registrarEstado(PartidaMomento e) {
        if (e == null) {
            return;
        }

        historial.push(e);
        movimientosContador++;
    }

    /**
     * El ultimo movimiento fue extraido y el contador fue actualizado.
     *
     * @return ultimo estado registrado o null si el historial se encuentra vacio
     */
    public PartidaMomento extraerUltimoEstado() {
        if (historial.isEmpty()) {
            return null;
        }

        movimientosContador = Math.max(0, movimientosContador - 1);
        return historial.pop();
    }

    /**
     * La estructura de historial fue vaciada y el contador fue reiniciado.
     */
    public void vaciarHistorial() {
        historial.clear();
        movimientosContador = 0;
    }
}
