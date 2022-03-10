package com.fantastic_knight.menu;

import com.fantastic_knight.Game;
import javafx.scene.control.Button;

public class ButtonLevel extends Button {
    public ButtonLevel(String name, double x, double y) {
        super();
        setText(name);
        setLayoutX(x);
        setLayoutY(y);
        setPrefSize(300, 300);
        String css = """
                -fx-border-color: transparent;
                -fx-border-width: 0;
                -fx-background-color: transparent;
                -fx-font: 22 Arial;
                -fx-font-weight: bold;
                -fx-background-repeat: no-repeat;
                -fx-background-size: 300 300;
                """;
        setStyle(css);
        setStyle(getStyle() + "-fx-background-image: url('" + Game.class.getResource("icons/flag.png") + "')");
        setOpacity(100);
    }
}
