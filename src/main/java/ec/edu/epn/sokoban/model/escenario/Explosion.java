package ec.edu.epn.sokoban.model.escenario;

import ec.edu.epn.sokoban.model.interfaces.Accion;

/**
 * Accion que representa la detonacion de una caja explosiva. Destruye las
 * paredes ortogonalmente adyacentes a la casilla donde detona (las
 * reemplaza por Suelo) y libera la posicion propia de esa casilla usando
 * el mecanismo ya existente de Tablero (restaurarCasillaBase), que respeta
 * automaticamente si la posicion era una meta.
 *
 * Se registra en el GestorAcciones de una Caja normal (sin necesidad de una
 * subclase); GestorColisiones no conoce esta clase en absoluto. Cuando el
 * empuje de una caja falla (queda bloqueada), GestorColisiones le da a la
 * caja la oportunidad de reaccionar llamando de forma generica a
 * getGestorAcciones().ejecutarAcciones(...); si la caja tiene una Explosion
 * registrada, esta se ejecuta y libera la posicion de la caja, lo cual
 * GestorColisiones detecta simplemente observando que la caja ya no esta
 * en esa celda.
 */
public class Explosion implements Accion {

    private boolean detonada;

    @Override
    public void iniciarAccion(Casilla casillaActual, Tablero tablero, Casilla entidad) {
        if (this.detonada || casillaActual == null || tablero == null) {
            return;
        }

        int fila = casillaActual.getFila();
        int columna = casillaActual.getColumna();

        int[][] direcciones = { {-1, 0}, {1, 0}, {0, -1}, {0, 1} };

        for (int[] d : direcciones) {
            int f = fila + d[0];
            int c = columna + d[1];

            if (!tablero.estaDentroDelTablero(f, c)) {
                continue;
            }

            Casilla adyacente = tablero.obtenerCasilla(f, c);
            if (adyacente instanceof Pared) {
                tablero.reemplazarTerrenoPermanente(f, c, new Suelo(f, c));
            }
        }

        tablero.restaurarCasillaBase(fila, columna);
        this.detonada = true;
    }

}