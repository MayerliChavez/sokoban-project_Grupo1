package ec.edu.epn.sokoban.model.escenario;

import ec.edu.epn.sokoban.model.interfaces.Transitable;
import ec.edu.epn.sokoban.model.interfaces.Dibujador;

/**
 * Representa una casilla meta.
 */
public class Meta extends Casilla implements Transitable {

    public Meta(int f, int c) {
        super(f, c);
    }

    @Override
    public boolean esTransitable() {
        return true;
    }

    @Override
    public <T> void dibujar(Dibujador<T> dibujador, T contenedor, int tamCelda) {
        dibujador.dibujarMeta(this, contenedor, tamCelda);
    }
}
