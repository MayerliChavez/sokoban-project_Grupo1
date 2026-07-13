package ec.edu.epn.sokoban.model.historial;

/**
 * La definicion inicial de un nivel y su estado de victoria son encapsulados.
 */
public class Nivel {
    private boolean completado;
    private String[][] mapaDatos;



    /**
     * Un nivel es inicializado como no completado.
     *
     * @param mapaDatos matriz inicial de diseno del nivel
     */
    public Nivel(String[][] mapaDatos) {
        this(mapaDatos, false);
    }

    /**
     * Un nivel es inicializado con su estado de victoria.
     *
     * @param mapaDatos matriz inicial de diseno del nivel
     * @param completado estado inicial de victoria del nivel
     */
    public Nivel(String[][] mapaDatos, boolean completado) {
        this.mapaDatos = copiarMatriz(mapaDatos);
        this.completado = completado;
    }

    /**
     * El estado de victoria es retornado.
     *
     * @return true si el nivel fue completado; false en caso contrario
     */
    public boolean isCompletado() {
        return completado;
    }

    /**
     * El estado de victoria es actualizado.
     *
     * @param completado nuevo estado de victoria
     */
    public void setCompletado(boolean completado) {
        this.completado = completado;
    }

    /**
     * Una copia de la matriz inicial de diseno es retornada.
     *
     * @return copia de la matriz inicial de diseno del nivel
     */
    public String[][] getMapaDatos() {
        return copiarMatriz(mapaDatos);
    }



    /**
     * El nivel fue marcado como completado mediante una actualizacion atomica.
     */
    public void marcarComoCompletado() {
        this.completado = true;
    }

    private String[][] copiarMatriz(String[][] origen) {
        if (origen == null) {
            return new String[0][0];
        }

        String[][] copia = new String[origen.length][];
        for (int fila = 0; fila < origen.length; fila++) {
            if (origen[fila] == null) {
                copia[fila] = new String[0];
            } else {
                copia[fila] = new String[origen[fila].length];
                System.arraycopy(origen[fila], 0, copia[fila], 0, origen[fila].length);
            }
        }
        return copia;
    }
}
