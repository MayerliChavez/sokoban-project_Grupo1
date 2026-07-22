package ec.edu.epn.sokoban.model.persistencia;

import ec.edu.epn.sokoban.model.historial.Nivel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del Grupo 5 (ver divisionGrupos.md): carga niveles empaquetados como
 * recursos del classpath y persiste/lee el progreso del usuario en un archivo de texto plano.
 * Antes de esta implementación, {@code guardarProgreso}, {@code cargarProgreso} y
 * {@code cargarNivelDesdeRecursos} eran cuerpos vacíos (no-ops) que retornaban listas vacías o {@code null}.
 */
public class GestorPersistencia {
    private static final String RUTA_RECURSOS_NIVELES = "/niveles/";

    private String archivoRuta;

    public GestorPersistencia(String archivoRuta) {
        this.archivoRuta = archivoRuta;
    }



    /**
     * Guarda el progreso del usuario en {@code archivoRuta}.
     * Como {@link Nivel} no tiene un campo id propio, el id de cada nivel se deriva de su
     * posición 1-based en {@code niveles} (índice {@code i} -> id {@code i + 1}), coincidiendo
     * con la nomenclatura secuencial de archivos ({@code N.txt}) descrita en creacionNiveles.md.
     * Recorre la lista, filtra los niveles con {@code isCompletado() == true} y escribe sus ids
     * (uno por línea, UTF-8) mediante {@code Files.write}. Antes de esta implementación el método
     * no hacía nada (no-op).
     */
    public void guardarProgreso(List<Nivel> niveles) {
        List<String> idsCompletados = new ArrayList<>();
        for (int i = 0; i < niveles.size(); i++) {
            if (niveles.get(i).isCompletado()) {
                idsCompletados.add(String.valueOf(i + 1));
            }
        }
        try {
            Files.write(Paths.get(archivoRuta), idsCompletados, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo guardar el progreso en " + archivoRuta, e);
        }
    }

    /**
     * Lee el progreso previamente guardado por {@code guardarProgreso} desde {@code archivoRuta}.
     * Si el archivo aún no existe (primera ejecución del usuario), retorna una lista vacía sin
     * lanzar error. Si existe, lee línea por línea con {@code Files.readAllLines}, ignora líneas
     * vacías y convierte cada valor a {@code Integer} (proceso inverso exacto de
     * {@code guardarProgreso}). Antes de esta implementación siempre retornaba {@code new ArrayList<>()}.
     * 
     */
    public List<Integer> cargarProgreso() {
        List<Integer> idsCompletados = new ArrayList<>();
        Path ruta = Paths.get(archivoRuta);
        if (!Files.exists(ruta)) {
            return idsCompletados;
        }
        try {
            for (String linea : Files.readAllLines(ruta, StandardCharsets.UTF_8)) {
                String valor = linea.trim();
                if (!valor.isEmpty()) {
                    idsCompletados.add(Integer.parseInt(valor));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("No se pudo leer el progreso de " + archivoRuta, e);
        }
        return idsCompletados;
    }

    /**
     * Carga el nivel {@code nivelId} desde el recurso empaquetado {@code /niveles/<nivelId>.txt}
     * usando {@code getClass().getResourceAsStream(recurso)}, lo cual funciona tanto ejecutando
     * desde el IDE como empaquetado en un {@code .jar} (a diferencia de leer del filesystem).
     * Si el recurso no existe retorna {@code null}: esa es la señal de "no hay más niveles" para
     * quien recorra la secuencia 1..N dinámicamente (ver creacionNiveles.md, sección 2).
     * Si existe, lee todas sus líneas, calcula el ancho máximo ({@code columnas}) y arma
     * {@code mapaDatos} (un {@code String[][]} de una sola letra por celda), rellenando con
     * {@code " "} las celdas de líneas más cortas para homogeneizar el tamaño de fila. Retorna
     * un {@link Nivel} nuevo construido con esa matriz. Antes de esta implementación siempre
     * retornaba {@code null}.
     */
    public Nivel cargarNivelDesdeRecursos(int nivelId) {
        String recurso = RUTA_RECURSOS_NIVELES + nivelId + ".txt";
        try (InputStream in = getClass().getResourceAsStream(recurso)) {
            if (in == null) {
                return null;
            }
            List<String> lineas = new ArrayList<>();
            try (BufferedReader lector = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
                String linea;
                while ((linea = lector.readLine()) != null) {
                    lineas.add(linea);
                }
            }
            int columnas = 0;
            for (String linea : lineas) {
                columnas = Math.max(columnas, linea.length());
            }
            String[][] mapaDatos = new String[lineas.size()][columnas];
            for (int f = 0; f < lineas.size(); f++) {
                String linea = lineas.get(f);
                for (int c = 0; c < columnas; c++) {
                    mapaDatos[f][c] = c < linea.length() ? String.valueOf(linea.charAt(c)) : " ";
                }
            }
            return new Nivel(mapaDatos);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo leer el nivel " + nivelId, e);
        }
    }
}
