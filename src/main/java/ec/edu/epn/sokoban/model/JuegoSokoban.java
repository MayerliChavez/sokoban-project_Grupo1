package ec.edu.epn.sokoban.model;

import ec.edu.epn.sokoban.model.escenario.Caja;
import ec.edu.epn.sokoban.model.escenario.Casilla;
import ec.edu.epn.sokoban.model.escenario.Personaje;
import ec.edu.epn.sokoban.model.escenario.Tablero;
import ec.edu.epn.sokoban.model.factory.FabricaNiveles;
import ec.edu.epn.sokoban.model.historial.HistorialMovimientos;
import ec.edu.epn.sokoban.model.historial.Nivel;
import ec.edu.epn.sokoban.model.historial.PartidaMomento;
import ec.edu.epn.sokoban.model.persistencia.GestorPersistencia;
import ec.edu.epn.sokoban.model.reglas.ReglasJuego;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JuegoSokoban {
    private List<Nivel> nivelesDisponibles;
    private Nivel nivelActual;
    private Tablero tableroActual;
    private HistorialMovimientos historial;
    private GestorPersistencia persistencia;

    // =========================================================================
    // 1. Constructor e Inicialización
    // =========================================================================

    public JuegoSokoban(List<Nivel> nivelesDisponibles) {
        this.nivelesDisponibles = nivelesDisponibles != null ? nivelesDisponibles : new ArrayList<>();
        this.historial = new HistorialMovimientos();
        this.persistencia = new GestorPersistencia("progress.txt");
    }

    // =========================================================================
    // 2. Métodos ejecutados durante la inicialización y selección de nivel
    // =========================================================================

    public void seleccionarNivel(Nivel nivel) {
        if (nivel == null || nivel.isCompletado()) {
            return;
        }

        FabricaNiveles fabrica = new FabricaNiveles();
        this.nivelActual = nivel;
        this.tableroActual = fabrica.construirTablero(nivel);
        if (this.nivelActual.getReglasJuego() != null) {
            this.nivelActual.getReglasJuego().asociarTablero(this.tableroActual);
        }
        this.historial.vaciarHistorial();
    }

    public void reiniciarNivelActual() {
        if (nivelActual != null) {
            seleccionarNivel(nivelActual);
        }
    }

    public void agregarNivel(Nivel n) {
        if (n != null) {
            this.nivelesDisponibles.add(n);
        }
    }

    // =========================================================================
    // 3. Métodos ejecutados durante la partida y consultas de estado
    // =========================================================================

    public List<Nivel> getNivelesDisponibles() {
        return nivelesDisponibles;
    }

    public Nivel getNivelActual() {
        return nivelActual;
    }

    public Tablero getTableroActual() {
        return tableroActual;
    }

    public ReglasJuego getReglasJuego() {
        return nivelActual != null ? nivelActual.getReglasJuego() : null;
    }

    public GestorPersistencia getPersistencia() {
        return persistencia;
    }

    public HistorialMovimientos getHistorial() {
        return historial;
    }

    // Depende de la implementación de PartidaMomento.restaurarEnTablero().
    public void deshacerUltimaAccion() {
        if (tableroActual == null) {
            return;
        }

        PartidaMomento ultimoEstado = historial.extraerUltimoEstado();

        if (ultimoEstado != null) {
            ultimoEstado.restaurarEnTablero(tableroActual);
        }
    }

    public PartidaMomento capturarEstadoActual() {
        Map<Caja, Casilla> posicionesCajas = new HashMap<>();
        Casilla posicionJugador = null;

        for (int f = 0; f < tableroActual.getFilas(); f++) {
            for (int c = 0; c < tableroActual.getColumnas(); c++) {
                Casilla casilla = tableroActual.obtenerCasilla(f, c);

                if (casilla instanceof Caja) {
                    posicionesCajas.put((Caja) casilla, casilla);
                }

                if (casilla instanceof Personaje) {
                    posicionJugador = casilla;
                }
            }
        }

        return new PartidaMomento(posicionesCajas, posicionJugador);
    }

    public void registrarVictoria() {
        if (nivelActual != null && nivelActual.getReglasJuego() != null && nivelActual.getReglasJuego().verificarVictoria()) {
            nivelActual.marcarComoCompletado();
            persistencia.guardarProgreso(nivelesDisponibles);
        }
    }
}
