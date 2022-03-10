package com.fantastic_knight.menu;

import com.fantastic_knight.Game;
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

        // browse levels
        int i = 0;
        int j = 0;
        int compteur = 1;
        for (String levelName : levels) {
            // Print and creation
            ButtonLevel buttonLevel = new ButtonLevel(levelName, 60 + i * 250, 180 + j * 300);
            int finalCompteur = compteur;
            buttonLevel.setOnAction(e -> selectLevel(finalCompteur));
            getChildren().addAll(buttonLevel);
            compteur++;
            i++;

            // If 4 levels in a line, we start a new line
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

