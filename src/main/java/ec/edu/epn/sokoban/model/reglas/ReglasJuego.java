package ec.edu.epn.sokoban.model.reglas;

import ec.edu.epn.sokoban.model.escenario.Tablero;

/** Reglas generales asociadas al tablero de la partida actual. */
public final class ReglasJuego {
    private final GestorColisiones gestorColisiones;
    private Tablero tablero;

    public ReglasJuego() {
        this.gestorColisiones = new GestorColisiones();
    }

    public void asociarTablero(Tablero tablero) {
        this.tablero = tablero;
    }

    public GestorColisiones getGestorColisiones() {
        return gestorColisiones;
    }

    /** Itera las metas del tablero asociado y comprueba que todas contengan caja. */
    public boolean verificarVictoria() {
        if (tablero == null) {
            return false;
        }

        boolean existeMeta = false;
        for (int fila = 0; fila < tablero.getFilas(); fila++) {
            for (int columna = 0; columna < tablero.getColumnas(); columna++) {
                if (tablero.esCeldaMeta(fila, columna)) {
                    existeMeta = true;
                    if (!tablero.esCeldaCaja(fila, columna)) {
                        return false;
                    }
                }
            }
        }
        return existeMeta;
    }

}
