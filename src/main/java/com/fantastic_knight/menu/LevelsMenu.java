package com.fantastic_knight.menu;

import com.fantastic_knight.model.Model;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import static com.fantastic_knight.Game.*;
import static com.fantastic_knight.model.Model.levels;

public class LevelsMenu extends Pane {

    public ScrollPane scrollPane = new ScrollPane();

    public LevelsMenu() {

        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        // Size
        setPrefSize(1200,1600);

        // Background
        ImageView bgImage = new ImageView(new Image("file:src/main/resources/com/fantastic_knight/menu/levels.png"));

        // Button return menu
        Button buttonMenu = new Button();
        buttonMenu.setLayoutX(1115);
        buttonMenu.setLayoutY(697);
        buttonMenu.setOnAction(e -> returnMenu());
        ImageView imageView = new ImageView(new Image("file:src/main/resources/com/fantastic_knight/icons/back.png"));
        buttonMenu.setGraphic(imageView);
        buttonMenu.setPrefSize(40, 40);
        buttonMenu.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,null,null)));

        getChildren().addAll(bgImage, buttonMenu);

        // Browse levels
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
        scrollPane.setContent(this);
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

