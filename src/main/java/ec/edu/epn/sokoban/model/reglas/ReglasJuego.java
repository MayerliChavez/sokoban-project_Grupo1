package ec.edu.epn.sokoban.model.reglas;

import ec.edu.epn.sokoban.model.Direccion;
import ec.edu.epn.sokoban.model.escenario.Casilla;
import ec.edu.epn.sokoban.model.escenario.Meta;
import ec.edu.epn.sokoban.model.escenario.Tablero;
import ec.edu.epn.sokoban.model.escenario.Caja;
import java.util.List;

public class ReglasJuego {

    public ReglasJuego() {
    }

    public boolean validarMovimiento(Casilla origen, Direccion d, Tablero t) {
        if (origen == null || d == null || t == null) {
            return false;
        }

        int filaDestino = origen.getFila() + d.getDeltaFila();
        int columnaDestino = origen.getColumna() + d.getDeltaColumna();

        if (filaDestino < 0 || filaDestino >= t.getFilas()
                || columnaDestino < 0 || columnaDestino >= t.getColumnas()) {
            return false;
        }

        Casilla destino = t.obtenerCasilla(filaDestino, columnaDestino);

        if (destino == null) {
            return false;
        }

        if (destino instanceof Caja) {
            int filaTrasCaja = filaDestino + d.getDeltaFila();
            int columnaTrasCaja = columnaDestino + d.getDeltaColumna();

            if (filaTrasCaja < 0 || filaTrasCaja >= t.getFilas()
                    || columnaTrasCaja < 0 || columnaTrasCaja >= t.getColumnas()) {
                return false;
            }

            Casilla casillaTrasCaja = t.obtenerCasilla(filaTrasCaja, columnaTrasCaja);

            if (casillaTrasCaja == null) {
                return false;
            }

            return casillaTrasCaja.esTransitable();
        }

        return destino.esTransitable();
    }

    public boolean verificarVictoria(List<Meta> metas) {
        if (metas == null || metas.isEmpty()) {
            return false;
        }

        for (Meta meta : metas) {
            if (!meta.isSatisfecha()) {
                return false;
            }
        }

        return true;
    }
}

