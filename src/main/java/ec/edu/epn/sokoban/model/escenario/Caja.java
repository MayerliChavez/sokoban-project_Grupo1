package ec.edu.epn.sokoban.model.escenario;

import ec.edu.epn.sokoban.model.interfaces.Dibujador;

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
