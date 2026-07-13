package ec.edu.epn.sokoban.model.escenario;

import ec.edu.epn.sokoban.Direccion;
import ec.edu.epn.sokoban.model.reglas.ManejadorColision;

/**
 * El personaje del escenario es representado como una casilla no transitable.
 */
public class Personaje extends Casilla {

    /**
     * Un personaje es inicializado con coordenadas dentro del escenario.
     *
     * @param fila    fila asignada al personaje
     * @param columna columna asignada al personaje
     */
    public Personaje(int fila, int columna) {
        super(fila, columna);
    }

    /**
     * Mueve el personaje en el tablero en la dirección dada, actualizando sus
     * coordenadas internas
     * y actualizando las celdas afectadas del tablero.
     *
     * @param d la dirección del movimiento
     * @param t el tablero sobre el cual se mueve
     * @return true si el movimiento fue exitoso, false en caso contrario
     */
    public boolean mover(Direccion d, Tablero t) {
        if (d == null || t == null) {
            return false;
        }

        int filaOrigen = getFila();
        int columnaOrigen = getColumna();
        int filaDestino = filaOrigen + d.getDeltaFila();
        int columnaDestino = columnaOrigen + d.getDeltaColumna();

        if (!t.esTransitable(filaDestino, columnaDestino)) {
            return false;
        }

        Casilla casillaLiberada = t.esMeta(filaOrigen, columnaOrigen)
                ? new Meta(filaOrigen, columnaOrigen)
                : new SueloComun(filaOrigen, columnaOrigen);

        t.actualizarCasilla(filaOrigen, columnaOrigen, casillaLiberada);

        setFila(filaDestino);
        setColumna(columnaDestino);

        t.actualizarCasilla(filaDestino, columnaDestino, this);
        return true;
    }

    /**
     * El estado de transitabilidad es retornado como bloqueado.
     *
     * @return false porque el personaje bloquea el paso libre
     */
    @Override
    public boolean esTransitable() {
        return false;
    }

    /**
     * Inicia la acción de movimiento del personaje delegando en la cadena de gestores de colisión.
     *
     * @param d la dirección del movimiento
     * @param t el tablero sobre el cual se mueve
     * @param cadenaColisiones la cadena de gestores de colisiones
     * @return true si el movimiento fue exitoso, false en caso contrario
     */
    public boolean mover(Direccion d, Tablero t, ManejadorColision cadenaColisiones) {
        if (d == null || t == null || cadenaColisiones == null) {
            return false;
        }
        return cadenaColisiones.procesarMovimiento(t, this, d);
    }
}
