package ec.edu.epn.sokoban.model.escenario;

import ec.edu.epn.sokoban.Direccion;
import ec.edu.epn.sokoban.model.interfaces.Dibujador;

import ec.edu.epn.sokoban.model.reglas.GestorColisiones;

/**
 * La clase Caja fue definida como una casilla no transitable y empujable.
 * La verificación de empuje fue integrada directamente en esta clase,
 * eliminando la dependencia sobre la interfaz {@code Empujable},
 * tal como fue especificado en el diagrama UML revisado.
 */
public class Caja extends Casilla {
    private boolean enMeta;

    /**
     * Una caja es inicializada fuera de una meta.
     *
     * @param fila fila asignada a la caja
     * @param columna columna asignada a la caja
     */
    public Caja(int fila, int columna) {
        super(fila, columna);
        this.enMeta = false;
    }

    /**
     * Una caja es inicializada con su estado de meta.
     *
     * @param fila fila asignada a la caja
     * @param columna columna asignada a la caja
     * @param enMeta estado de ubicacion sobre una meta
     */
    public Caja(int fila, int columna, boolean enMeta) {
        super(fila, columna);
        this.enMeta = enMeta;
    }

    /**
     * Mueve la caja en el tablero en la dirección dada, actualizando sus coordenadas internas
     * y actualizando las celdas afectadas del tablero.
     *
     * @param d la dirección del movimiento
     * @param t el tablero sobre el cual se mueve
     * @param gestorColisiones gestor de colisiones opcional
     * @return true si el movimiento fue ejecutado, false en caso contrario
     */
    @Override
    public boolean mover(Direccion d, Tablero t, GestorColisiones gestorColisiones) {
        if (d == null || t == null) {
            return false;
        }

        int filaOrigen = getFila();
        int columnaOrigen = getColumna();
        int filaDestino = filaOrigen + d.getDeltaFila();
        int columnaDestino = columnaOrigen + d.getDeltaColumna();

        Casilla destino = t.obtenerCasilla(filaDestino, columnaDestino);
        boolean posteriorEsMeta = destino instanceof Meta;

        // Cambiar estado de enMeta en la caja
        setEnMeta(posteriorEsMeta);

        // Mutar coordenadas internas
        setFila(filaDestino);
        setColumna(columnaDestino);

        t.actualizarCasilla(filaDestino, columnaDestino, this);
        return true;
    }

    public boolean mover(Direccion d, Tablero t) {
        return mover(d, t, null);
    }

    /**
     * El estado de ubicacion sobre una meta es retornado.
     *
     * @return true si la caja se encuentra en una meta; false en caso contrario
     */
    public boolean isEnMeta() {
        return enMeta;
    }

    /**
     * El estado de ubicacion sobre una meta es actualizado.
     *
     * @param enMeta nuevo estado de ubicacion sobre una meta
     */
    public void setEnMeta(boolean enMeta) {
        this.enMeta = enMeta;
    }

    /**
     * La condición de empujabilidad fue confirmada como invariablemente verdadera.
     * Este método fue retenido como comportamiento propio de la clase,
     * sin dependencia de ninguna interfaz externa.
     *
     * @return {@code true} de forma incondicional, dado que toda caja fue diseñada para ser empujable.
     */
    public boolean esEmpujable() {
        return true;
    }

    @Override
    public <T> void dibujar(Dibujador<T> dibujador, T contenedor, int tamCelda) {
        dibujador.dibujarCaja(this, contenedor, tamCelda);
    }
}
