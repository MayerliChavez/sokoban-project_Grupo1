package ec.edu.epn.sokoban.view.estilos;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

public class ComponentesUI {
    private ComponentesUI() {}

    public static Label crearTituloPrincipal(String texto) {
        Label titulo = new Label(texto);
        titulo.setAlignment(Pos.CENTER);
        titulo.setStyle(
                "-fx-text-fill: #F6B43A;" +
                        "-fx-font-size: 42px;" +
                        "-fx-font-weight: bold;"
        );
        titulo.setEffect(crearSombra());

        return titulo;
    }

    public static Label crearSubtitulo(String texto){
        Label subtitulo = new Label(texto);
        subtitulo.setStyle(
                "-fx-text-fill: #F0E8D8;" +
                        "-fx-font-size: 18px;"
        );
        return subtitulo;
    }

    public static Button crearBotonPrincipal(String texto){
        Button boton = new Button(texto);
        boton.setPrefWidth(280);
        boton.setPrefHeight(55);
        boton.setFocusTraversable(false);
        boton.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #F6B43A, #A86A18);" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 18px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 12;" +
                        "-fx-border-radius: 12;" +
                        "-fx-border-color: rgba(255,255,255,0.25);" +
                        "-fx-border-width: 2;" +
                        "-fx-cursor: hand;"
        );

        boton.setEffect(crearSombra());
        boton.setOnMouseEntered(e ->
                boton.setStyle(
                        "-fx-background-color: linear-gradient(to bottom,#FFD36B,#D48A23);" +
                                "-fx-text-fill:white;" +
                                "-fx-font-size:18px;" +
                                "-fx-font-weight:bold;" +
                                "-fx-background-radius:12;" +
                                "-fx-border-radius:12;" +
                                "-fx-border-color:rgba(255,255,255,0.35);" +
                                "-fx-border-width:2;" +
                                "-fx-cursor:hand;"
                )
        );

        boton.setOnMouseExited(e ->
                boton.setStyle(
                        "-fx-background-color: linear-gradient(to bottom,#F6B43A,#A86A18);" +
                                "-fx-text-fill:white;" +
                                "-fx-font-size:18px;" +
                                "-fx-font-weight:bold;" +
                                "-fx-background-radius:12;" +
                                "-fx-border-radius:12;" +
                                "-fx-border-color:rgba(255,255,255,0.25);" +
                                "-fx-border-width:2;" +
                                "-fx-cursor:hand;"
                )
        );

        return boton;
    }

    public static DropShadow crearSombra(){
        DropShadow sombra = new DropShadow();
        sombra.setRadius(12);
        sombra.setOffsetY(4);
        sombra.setColor(Color.rgb(0,0,0,0.55));

        return sombra;
    }

    public static Button crearTarjetaNivel(int nivel, boolean completado) {
        //Crea la tarjeta de presentacion
        String estado = completado ? "✔ Completado" : "● Sin completar";
        Button boton = new Button(
                "NIVEL " + nivel +
                        "\n\nGrupo " + nivel +
                        "\n\n" + estado
        );

        //Tamaño
        boton.setPrefWidth(220);
        boton.setPrefHeight(170);

        //Ajuste del texto
        boton.setFocusTraversable(false);
        boton.setWrapText(true);
        boton.setAlignment(Pos.CENTER);

        if (completado) {
            boton.setDisable(true);
            boton.setStyle(
                    "-fx-background-color: linear-gradient(to bottom, #2B2B2B, #1B1B1B);" +
                            "-fx-text-fill: #8E8E8E;" +
                            "-fx-font-size: 20px;" +
                            "-fx-font-weight: bold;" +
                            "-fx-background-radius: 18;" +
                            "-fx-border-radius: 18;" +
                            "-fx-border-color: #4F4F4F;" +
                            "-fx-border-width: 3;" +
                            "-fx-cursor: not-allowed;" +
                            "-fx-opacity: 0.75;"
            );
        } else {
            boton.setStyle(
                    "-fx-background-color: linear-gradient(to bottom, #3A2717, #17100B);" +
                            "-fx-text-fill: #F6B43A;" +
                            "-fx-font-size: 20px;" +
                            "-fx-font-weight: bold;" +
                            "-fx-background-radius: 18;" +
                            "-fx-border-radius: 18;" +
                            "-fx-border-color: #7A4A1D;" +
                            "-fx-border-width: 3;" +
                            "-fx-cursor: hand;"
            );

            boton.setOnMouseEntered(e ->
                    boton.setStyle(
                            "-fx-background-color: linear-gradient(to bottom, #5A3D22, #24160A);" +
                                    "-fx-text-fill: #FFD36B;" +
                                    "-fx-font-size: 24px;" +
                                    "-fx-font-weight: bold;" +
                                    "-fx-background-radius: 18;" +
                                    "-fx-border-radius: 18;" +
                                    "-fx-border-color: #D49A37;" +
                                    "-fx-border-width: 3;" +
                                    "-fx-cursor: hand;"
                    )
            );

            boton.setOnMouseExited(e ->
                    boton.setStyle(
                            "-fx-background-color: linear-gradient(to bottom, #3A2717, #17100B);" +
                                    "-fx-text-fill: #F6B43A;" +
                                    "-fx-font-size: 20px;" + // Cambiado a 20px para ser consistente con el tamaño por defecto
                                    "-fx-font-weight: bold;" +
                                    "-fx-background-radius: 18;" +
                                    "-fx-border-radius: 18;" +
                                    "-fx-border-color: #7A4A1D;" +
                                    "-fx-border-width: 3;" +
                                    "-fx-cursor: hand;"
                    )
            );
        }

        boton.setEffect(crearSombra());

        return boton;
    }
}
