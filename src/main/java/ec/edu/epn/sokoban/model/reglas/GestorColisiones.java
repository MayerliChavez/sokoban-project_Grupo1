package ec.edu.epn.sokoban.model.reglas;

import ec.edu.epn.sokoban.Direccion;
import ec.edu.epn.sokoban.model.escenario.*;


public class GestorColisiones {

    public GestorColisiones() {
    }

    /**
     * Analiza si mover un elemento desde 'c' en dirección 'd' causa una colisión inamovible
     * (pared, límite del mapa, o caja que no se puede empujar).
     */
    public boolean verificarColision(Tablero t, Casilla c, Direccion d) {
        if (t == null || c == null || d == null) {
            return true;
        }

        int filaDestino = c.getFila() + d.getDeltaFila();
        int columnaDestino = c.getColumna() + d.getDeltaColumna();

        if (filaDestino < 0 || filaDestino >= t.getFilas()
                || columnaDestino < 0 || columnaDestino >= t.getColumnas()) {
            return true; // fuera del tablero
        }

        Casilla destino = t.obtenerCasilla(filaDestino, columnaDestino);

        if (destino instanceof Pared) {
            return true;
        }

        if (destino instanceof Caja) {
            int filaTrasCaja = filaDestino + d.getDeltaFila();
            int columnaTrasCaja = columnaDestino + d.getDeltaColumna();

            if (filaTrasCaja < 0 || filaTrasCaja >= t.getFilas()
                    || columnaTrasCaja < 0 || columnaTrasCaja >= t.getColumnas()) {
                return true;
            }

            Casilla trasCaja = t.obtenerCasilla(filaTrasCaja, columnaTrasCaja);
            if (trasCaja == null || trasCaja instanceof Pared || trasCaja instanceof Caja) {
                return true; // caja bloqueada, no se puede empujar
            }
        }

        return false;
    }

    /**
     * Ejecuta el empuje de la caja 'd' hacia 'dir'. Retorna true si el empuje se realizó.
     */
    public boolean resolverEmpuje(Tablero t, Casilla c, Caja d, Direccion dir) {
        if (t == null || d == null || dir == null) {
            return false;
        }

        int filaCaja = d.getFila();
        int columnaCaja = d.getColumna();
        int filaDestino = filaCaja + dir.getDeltaFila();
        int columnaDestino = columnaCaja + dir.getDeltaColumna();

        if (filaDestino < 0 || filaDestino >= t.getFilas()
                || columnaDestino < 0 || columnaDestino >= t.getColumnas()) {
            return false;
        }

        Casilla destino = t.obtenerCasilla(filaDestino, columnaDestino);
        if (destino == null || !destino.esTransitable()) {
            return false;
        }

        boolean destinoEsMeta = destino instanceof Meta;

        // Colocar la caja en la nueva posición
        Caja cajaMovida = new Caja(filaDestino, columnaDestino);
        cajaMovida.setEnMeta(destinoEsMeta);
        if (destinoEsMeta) {
            ((Meta) destino).setSatisfecha(true);
        }
        t.actualizarCasilla(filaDestino, columnaDestino, cajaMovida);

        // Restaurar la casilla que la caja deja libre
        Casilla casillaLiberada = d.isEnMeta()
                ? new Meta(filaCaja, columnaCaja)
                : new SueloComun(filaCaja, columnaCaja);
        t.actualizarCasilla(filaCaja, columnaCaja, casillaLiberada);

        return true;
    }
}
