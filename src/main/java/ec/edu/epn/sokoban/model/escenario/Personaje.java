package ec.edu.epn.sokoban.model.escenario;

import ec.edu.epn.sokoban.Direccion;
import ec.edu.epn.sokoban.model.reglas.GestorColisiones;
import ec.edu.epn.sokoban.model.interfaces.Dibujador;

/**
 * El personaje del escenario.
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
     * coordenadas internas y actualizando las celdas afectadas del tablero.
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
                : new Suelo(filaOrigen, columnaOrigen);

        t.actualizarCasilla(filaOrigen, columnaOrigen, casillaLiberada);

        setFila(filaDestino);
        setColumna(columnaDestino);

        t.actualizarCasilla(filaDestino, columnaDestino, this);
        return true;
    }

    /**
     * Inicia la acción de movimiento del personaje usando el gestor de colisión único.
     *
     * @param d                la dirección del movimiento
     * @param t                el tablero sobre el cual se mueve
     * @param gestorColisiones el gestor de colisiones único
     * @return true si el movimiento fue exitoso, false en caso contrario
     */
    public boolean mover(Direccion d, Tablero t, GestorColisiones gestorColisiones) {
        if (d == null || t == null || gestorColisiones == null) {
            return false;
        }
        return gestorColisiones.procesarMovimiento(t, this, d);
    }

    @Override
    public <T> void dibujar(Dibujador<T> dibujador, T contenedor, int tamCelda) {
        dibujador.dibujarPersonaje(this, contenedor, tamCelda);
    }
}
