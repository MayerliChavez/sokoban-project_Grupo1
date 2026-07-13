package ec.edu.epn.sokoban.view;

import ec.edu.epn.sokoban.model.escenario.Caja;
import ec.edu.epn.sokoban.model.escenario.Casilla;
import ec.edu.epn.sokoban.model.escenario.Meta;
import ec.edu.epn.sokoban.model.escenario.Pared;
import ec.edu.epn.sokoban.model.escenario.Personaje;
import ec.edu.epn.sokoban.model.escenario.SueloComun;
import ec.edu.epn.sokoban.model.escenario.Tablero;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class PanelTablero extends GridPane {

    private Tablero tablero;

    private static final int ANCHO_MAX_TABLERO = 760;
    private static final int ALTO_MAX_TABLERO = 560;

    private static final int TAM_CELDA_MAX = 64;
    private static final int TAM_CELDA_MIN = 32;

    private static final int ESPACIO_ENTRE_CELDAS = 1;

    private int tamCelda = TAM_CELDA_MAX;

    private final Map<String, Image> sprites = new HashMap<>();

    public PanelTablero(Tablero tablero) {
        this.tablero = tablero;
        configurarPanel();
        cargarSprites();
        calcularTamanoCelda();
        dibujarTablero();
    }

    public void actualizarTablero(Tablero nuevoTablero) {
        this.tablero = nuevoTablero;
        calcularTamanoCelda();
        dibujarTablero();
    }

    private void configurarPanel() {
        setAlignment(Pos.CENTER);
        setHgap(ESPACIO_ENTRE_CELDAS);
        setVgap(ESPACIO_ENTRE_CELDAS);
        setPadding(new Insets(18));

        setStyle(
                "-fx-background-color: linear-gradient(to bottom, #2B2F33, #151719);" +
                        "-fx-border-color: #4A4F55;" +
                        "-fx-border-width: 5;" +
                        "-fx-border-radius: 18;" +
                        "-fx-background-radius: 18;"
        );

        DropShadow sombraExterior = new DropShadow();
        sombraExterior.setRadius(24);
        sombraExterior.setOffsetY(6);
        sombraExterior.setColor(Color.rgb(0, 0, 0, 0.65));

        setEffect(sombraExterior);
    }

    private void calcularTamanoCelda() {
        if (tablero == null) {
            tamCelda = TAM_CELDA_MAX;
            return;
        }

        int filas = tablero.getFilas();
        int columnas = tablero.getColumnas();

        if (filas <= 0 || columnas <= 0) {
            tamCelda = TAM_CELDA_MAX;
            return;
        }

        int tamPorAncho = ANCHO_MAX_TABLERO / columnas;
        int tamPorAlto = ALTO_MAX_TABLERO / filas;

        tamCelda = Math.min(tamPorAncho, tamPorAlto);

        if (tamCelda > TAM_CELDA_MAX) {
            tamCelda = TAM_CELDA_MAX;
        }

        if (tamCelda < TAM_CELDA_MIN) {
            tamCelda = TAM_CELDA_MIN;
        }
    }

    private void cargarSprites() {
        sprites.put("PARED", cargarImagen("/images/wall.png"));
        sprites.put("SUELO", cargarImagen("/images/floor.png"));
        sprites.put("META", cargarImagen("/images/goal.png"));
        sprites.put("CAJA", cargarImagen("/images/box.png"));
        sprites.put("JUGADOR", cargarImagen("/images/player.png"));
    }

    private Image cargarImagen(String ruta) {
        InputStream stream = getClass().getResourceAsStream(ruta);

        if (stream == null) {
            System.out.println("No se encontró la imagen: " + ruta);
            return null;
        }

        return new Image(stream);
    }

    private void dibujarTablero() {
        getChildren().clear();

        if (tablero == null) {
            return;
        }

        for (int fila = 0; fila < tablero.getFilas(); fila++) {
            for (int columna = 0; columna < tablero.getColumnas(); columna++) {
                Casilla casilla = tablero.obtenerCasilla(fila, columna);
                StackPane celdaVisual = crearCeldaVisual(casilla);
                add(celdaVisual, columna, fila);
            }
        }
    }

    private StackPane crearCeldaVisual(Casilla casilla) {
        StackPane celda = new StackPane();
        celda.setAlignment(Pos.CENTER);
        celda.setPrefSize(tamCelda, tamCelda);
        celda.setMinSize(tamCelda, tamCelda);
        celda.setMaxSize(tamCelda, tamCelda);

        agregarSueloBase(celda);

        if (casilla instanceof Pared) {
            celda.getChildren().clear();
            agregarSprite(celda, "PARED", Color.web("#3A3A3A"));

        } else if (casilla instanceof Caja) {
            if (((Caja) casilla).isEnMeta()) {
                agregarSprite(celda, "META", Color.web("#F4D35E"));
            }
            agregarSprite(celda, "CAJA", Color.web("#B8793B"));

        } else if (casilla instanceof Personaje) {
            if (tablero.esMeta(casilla.getFila(), casilla.getColumna())) {
                agregarSprite(celda, "META", Color.web("#F4D35E"));
            }
            agregarSprite(celda, "JUGADOR", Color.web("#4DA6FF"));

        } else if (casilla instanceof Meta) {
            agregarSprite(celda, "META", Color.web("#F4D35E"));

        } else if (casilla instanceof SueloComun) {
            // Solo queda el suelo base.
        }

        return celda;
    }

    private void agregarSueloBase(StackPane celda) {
        Image spriteSuelo = sprites.get("SUELO");

        if (spriteSuelo != null) {
            ImageView suelo = crearImageView(spriteSuelo, tamCelda);
            celda.getChildren().add(suelo);
        } else {
            celda.getChildren().add(crearFondoRespaldo(Color.web("#D9C58A")));
        }

        Rectangle bordeCelda = new Rectangle(tamCelda, tamCelda);
        bordeCelda.setFill(Color.TRANSPARENT);
        bordeCelda.setStroke(Color.rgb(0, 0, 0, 0.35));
        bordeCelda.setStrokeWidth(1);

        celda.getChildren().add(bordeCelda);
    }

    private void agregarSprite(StackPane celda, String claveSprite, Color colorRespaldo) {
        Image sprite = sprites.get(claveSprite);

        if (sprite != null) {
            double tamanioSprite = obtenerTamanoSprite(claveSprite);

            ImageView imageView = crearImageView(sprite, tamanioSprite);

            if (!claveSprite.equals("PARED")) {
                DropShadow sombra = new DropShadow();
                sombra.setRadius(6);
                sombra.setOffsetY(3);
                sombra.setColor(Color.rgb(0, 0, 0, 0.35));
                imageView.setEffect(sombra);
            }

            celda.getChildren().add(imageView);
        } else {
            celda.getChildren().add(crearFondoRespaldo(colorRespaldo));
        }
    }

    private double obtenerTamanoSprite(String claveSprite) {
        return switch (claveSprite) {
            case "CAJA" -> tamCelda * 0.78;
            case "JUGADOR" -> tamCelda * 0.88;
            case "META" -> tamCelda * 0.97;
            default -> tamCelda;
        };
    }

    private ImageView crearImageView(Image imagen, double tamanio) {
        ImageView imageView = new ImageView(imagen);
        imageView.setFitWidth(tamanio);
        imageView.setFitHeight(tamanio);
        imageView.setPreserveRatio(false);
        imageView.setSmooth(false);
        imageView.setCache(true);
        return imageView;
    }

    private Rectangle crearFondoRespaldo(Color color) {
        Rectangle rect = new Rectangle(tamCelda, tamCelda);
        rect.setFill(color);
        rect.setStroke(Color.rgb(0, 0, 0, 0.4));

        InnerShadow sombraInterna = new InnerShadow();
        sombraInterna.setRadius(4);
        sombraInterna.setColor(Color.rgb(0, 0, 0, 0.25));
        rect.setEffect(sombraInterna);

        return rect;
    }
}