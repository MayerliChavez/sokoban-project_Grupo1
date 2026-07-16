package ec.edu.epn.sokoban.model.reglas;

import ec.edu.epn.sokoban.Direccion;
import ec.edu.epn.sokoban.model.escenario.*;
import ec.edu.epn.sokoban.model.interfaces.Transitable;
import ec.edu.epn.sokoban.model.interfaces.Empujable;

/**
 * Gestor único y centralizado para la física y lógica de colisiones.
 * Resuelve las colisiones de Personaje vs Pared, Personaje vs Caja, Caja vs Pared,
 * Caja vs Meta, y Personaje vs Casilla.
 */
public class GestorColisiones {

    /**
     * Procesa el intento de movimiento de un personaje en una dirección dada,
     * evaluando todas las posibles colisiones.
     *
     * @param tablero   El tablero de juego.
     * @param personaje El personaje que intenta moverse.
     * @param dir       La dirección del movimiento.
     * @return true si el movimiento se realizó con éxito, false en caso contrario.
     */
    public boolean procesarMovimiento(Tablero tablero, Personaje personaje, Direccion dir) {
        if (tablero == null || personaje == null || dir == null) {
            return false;
        }

        int filaDestino = personaje.getFila() + dir.getDeltaFila();
        int columnaDestino = personaje.getColumna() + dir.getDeltaColumna();

        // Control de los límites del tablero (Personaje vs límites)
        if (filaDestino < 0 || filaDestino >= tablero.getFilas() ||
            columnaDestino < 0 || columnaDestino >= tablero.getColumnas()) {
            return false;
        }

        Casilla destino = tablero.obtenerCasilla(filaDestino, columnaDestino);

        // 1. Personaje vs pared: Si la casilla destino no es transitable y no es empujable, actúa como pared
        boolean destinoEsTransitable = (destino instanceof Transitable && ((Transitable) destino).esTransitable());
        boolean destinoEsEmpujable = (destino instanceof Empujable && ((Empujable) destino).esEmpujable());

        if (destino != null && !destinoEsTransitable && !destinoEsEmpujable) {
            return false;
        }

        // 2. Personaje vs caja: Si la casilla destino es empujable
        if (destinoEsEmpujable && destino instanceof Caja caja) {
            // Si la caja ya está en una meta, según la regla original de juego no se puede empujar más
            if (tablero.esMeta(filaDestino, columnaDestino)) {
                return false;
            }

            int filaDestinoCaja = filaDestino + dir.getDeltaFila();
            int columnaDestinoCaja = columnaDestino + dir.getDeltaColumna();

            // Control de los límites tras la caja (Caja vs límites)
            if (filaDestinoCaja < 0 || filaDestinoCaja >= tablero.getFilas() ||
                columnaDestinoCaja < 0 || columnaDestinoCaja >= tablero.getColumnas()) {
                return false;
            }

            Casilla casillaDestinoCaja = tablero.obtenerCasilla(filaDestinoCaja, columnaDestinoCaja);
            boolean posteriorEsTransitable = (casillaDestinoCaja instanceof Transitable && ((Transitable) casillaDestinoCaja).esTransitable());

            // 3. Caja vs pared: La casilla tras la caja debe ser transitable para poder empujarla
            if (casillaDestinoCaja == null || !posteriorEsTransitable) {
                return false;
            }

            // 4. Caja vs meta: Mueve la caja a la casilla destino (su comportamiento interno actualiza su estado en meta)
            caja.mover(dir, tablero);

            // Liberar la casilla original de la caja
            Casilla casillaLiberada = new Suelo(filaDestino, columnaDestino);
            tablero.actualizarCasilla(filaDestino, columnaDestino, casillaLiberada);

            // Mover personaje a la casilla liberada
            personaje.mover(dir, tablero);

            return true;
        }

        // 5. Personaje vs casilla (transitable): Si el destino es transitable (suelo o meta libre)
        if (destinoEsTransitable) {
            return personaje.mover(dir, tablero);
        }

        return false;
    }
}
