package ec.edu.epn.sokoban.model.escenario;

import ec.edu.epn.sokoban.model.interfaces.Dibujador;

/**
 * Clase abstracta que representa el componente base del escenario.
 * Es completamente independiente del framework de interfaz gráfica (MVC).
 */
public abstract class Casilla {
    private int fila;
    private int columna;

    public Casilla() {}

    public Casilla(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    /**
     * Delega la responsabilidad de dibujo a través de la interfaz genérica Dibujador (Visitor).
     *
     * @param <T>       El tipo de componente contenedor visual (e.g. StackPane en JavaFX).
     * @param dibujador Instancia de dibujador encargada de aplicar los estilos y sprites.
     * @param contenedor El panel físico o celda de la interfaz gráfica correspondiente.
     * @param tamCelda  El tamaño asignado a la celda en píxeles.
     */
    public abstract <T> void dibujar(Dibujador<T> dibujador, T contenedor, int tamCelda);
}
