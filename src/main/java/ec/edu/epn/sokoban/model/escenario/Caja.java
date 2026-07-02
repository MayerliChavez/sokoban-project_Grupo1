package ec.edu.epn.sokoban.model.escenario;

/**
 * Una caja del escenario es representada como una casilla no transitable.
 */
public class Caja extends Casilla {
    private boolean enMeta;

    /**
     * Una caja sin coordenadas explicitas es inicializada.
     */
    public Caja() {
    }

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
     * El estado de transitabilidad es retornado como bloqueado.
     *
     * @return false porque la caja bloquea el paso libre
     */
    @Override
    public boolean esTransitable() {
        return false;
    }
}
