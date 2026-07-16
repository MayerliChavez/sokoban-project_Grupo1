package ec.edu.epn.sokoban.model.interfaces;

import ec.edu.epn.sokoban.model.escenario.Pared;
import ec.edu.epn.sokoban.model.escenario.Suelo;
import ec.edu.epn.sokoban.model.escenario.Caja;
import ec.edu.epn.sokoban.model.escenario.Meta;
import ec.edu.epn.sokoban.model.escenario.Personaje;

/**
 * Interfaz genérica para la delegación del dibujo (Visitor Pattern).
 * Permite separar completamente la visualización gráfica (JavaFX) de las clases de dominio (Modelo).
 *
 * @param <T> El tipo de contenedor visual de la UI (por ejemplo, StackPane en JavaFX).
 */
public interface Dibujador<T> {
    void dibujarPared(Pared pared, T contenedor, int tamCelda);
    void dibujarCaja(Caja caja, T contenedor, int tamCelda);
    void dibujarPersonaje(Personaje personaje, T contenedor, int tamCelda);
    void dibujarMeta(Meta meta, T contenedor, int tamCelda);
    void dibujarSuelo(Suelo suelo, T contenedor, int tamCelda);
}
