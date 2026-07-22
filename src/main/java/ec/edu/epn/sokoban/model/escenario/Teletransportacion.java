package ec.edu.epn.sokoban.model.escenario;

import ec.edu.epn.sokoban.model.interfaces.Accion;

public class Teletransportacion implements Accion {

    private final int filaDestino;
    private final int columnaDestino;

    public Teletransportacion(int filaDestino, int columnaDestino) {
        this.filaDestino = filaDestino;
        this.columnaDestino = columnaDestino;
    }

    /**
     *
     * @param casillaActual la casilla portal sobre la que la entidad fue
     *                      posicionada
     * @param tablero       el tablero sobre el que la teletransportación fue
     *                      ejecutada
     * @param entidad       la entidad que pisó el portal; solo actúa si es una
     *                      Caja
     */
    @Override
    public void iniciarAccion(Casilla casillaActual, Tablero tablero, Casilla entidad) {
        // La regla de negocio fue aplicada: solo las cajas son teletransportadas.
        if (!(entidad instanceof Caja)) {
            return;
        }

        if (tablero == null || !tablero.estaDentroDelTablero(filaDestino, columnaDestino)) {
            return;
        }

        Caja caja = (Caja) entidad;

        tablero.actualizarCasilla(filaDestino, columnaDestino, caja);
    }

    @Override
    public boolean puedeIngresarEntidad(Tablero tablero, Casilla entidad) {
        if (entidad instanceof Caja) {
            int fB = this.filaDestino;
            int cB = this.columnaDestino;
            if (tablero == null
                    || !tablero.estaDentroDelTablero(fB, cB)
                    || !tablero.esCeldaTransitable(fB, cB)
                    || tablero.obtenerCaja(fB, cB) != null
                    || (tablero.getPersonaje() != null
                            && tablero.getPersonaje().getFila() == fB
                            && tablero.getPersonaje().getColumna() == cB)) {
                return false;
            }
        }
        return true;
    }

    public int getFilaDestino() {
        return filaDestino;
    }

    public int getColumnaDestino() {
        return columnaDestino;
    }

    /**
     * Retorna el identificador del sprite para esta acción.
     *
     * @return "PORTAL"
     */
    @Override
    public String getSpriteKey() {
        return "PORTAL";
    }
}