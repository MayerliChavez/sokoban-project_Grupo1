package ec.edu.epn.sokoban.model.escenario;

import ec.edu.epn.sokoban.model.interfaces.Dibujador;

/**
 * Representa una pared física común y corriente.
 */
public class Pared extends Casilla {
    public Pared(int f, int c) {
        super(f, c);
    }

    @Override
    public <T> void dibujar(Dibujador<T> dibujador, T contenedor, int tamCelda) {
        dibujador.dibujarPared(this, contenedor, tamCelda);
    }
}
