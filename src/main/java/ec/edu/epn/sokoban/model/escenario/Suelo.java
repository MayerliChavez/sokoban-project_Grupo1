package ec.edu.epn.sokoban.model.escenario;

import ec.edu.epn.sokoban.model.interfaces.Transitable;
import ec.edu.epn.sokoban.model.interfaces.Dibujador;

/**
 * Clase que representa el terreno base transitable.
 */
public class Suelo extends Casilla implements Transitable {
    public Suelo(int f, int c) {
        super(f, c);
    }

    @Override
    public boolean verificarTransitabilidad() {
        return true;
    }

    @Override
    public <T> void dibujar(Dibujador<T> dibujador, T contenedor, int tamCelda) {
        dibujador.dibujarSuelo(this, contenedor, tamCelda);
    }
}
