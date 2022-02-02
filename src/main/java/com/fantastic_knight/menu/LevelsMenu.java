package com.fantastic_knight.menu;

import com.fantastic_knight.model.Model;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import static com.fantastic_knight.Game.*;

public class LevelsMenu extends Pane {

    public LevelsMenu() {
        // size
        setHeight(800);
        setWidth(1200);

        // Background
        ImageView bgImage = new ImageView(new Image("file:src/main/resources/com/fantastic_knight/menu/levels.png"));

        // button return menu
        Button buttonMenu = new Button();
        buttonMenu.setLayoutX(1128);
        buttonMenu.setLayoutY(697);
        buttonMenu.setOnAction(e -> returnMenu());
        buttonMenu.setOpacity(0);
        buttonMenu.setPrefSize(60, 85.3);

        getChildren().addAll(bgImage, buttonMenu);

        // brows levels
        int i = 0;
        int j = 0;
        int compteur = 1;
        for (String levelName : levels) {
            // Print and creation
            ButtonLevel buttonLevel = new ButtonLevel(levelName, 60 + j * 250, 219 + i * 130);
            int finalCompteur = compteur;
            buttonLevel.setOnAction(e -> selectLevel(finalCompteur));
            getChildren().add(buttonLevel);
            compteur++;
            i++;

            // If 4 levels in a column, we start a new column
            if (i == 4) {
                i = 0;
                j++;
            }
        }
    }

    /**
     * Select a level
     * @param i : level number
     */
    public void selectLevel(int i) {
        if (model.state == Model.STATE_INITIAL) {
            primaryStage.setScene(scene_game);
            model.level = i;
            controller.startGame();
        }
    }

    public void returnMenu() {
        primaryStage.setScene(scene_menu);
    }
}

class ButtonLevel extends Button {
    public ButtonLevel(String name, double x, double y) {
        super();
        setText(name);
        setLayoutX(x);
        setLayoutY(y);
        setPrefSize(200, 100);
        setHeight(100);
        setWidth(200);
        String css = """
                -fx-font: 22 Arial;
                -fx-base: #444444;
                -fx-background-radius: 40px;
                -fx-border-color:black;
                -fx-border-radius: 40;
                -fx-border-width: 3;
                """;
        setStyle(css);
        setOpacity(100);
    }
}
