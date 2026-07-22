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
     * Inicia la acción de movimiento del personaje usando el gestor de colisión único.
     *
     * @param d                la dirección del movimiento
     * @param t                el tablero sobre el cual se mueve
     * @param gestorColisiones el gestor de colisiones único
     * @return true si el movimiento fue exitoso, false en caso contrario
     */
    @Override
    public boolean mover(Direccion d, Tablero t, GestorColisiones gestorColisiones) {
        if (d == null || t == null || gestorColisiones == null) {
            return false;
        }
        int filaDestino = getFila() + d.getDeltaFila();
        int columnaDestino = getColumna() + d.getDeltaColumna();
        return gestorColisiones.procesarMovimiento(t, this, filaDestino, columnaDestino);
    }

    @Override
    public <T> void dibujar(Dibujador<T> dibujador, T contenedor, int tamCelda) {
        dibujador.dibujarPersonaje(this, contenedor, tamCelda);
    }
}
