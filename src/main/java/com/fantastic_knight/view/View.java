package com.fantastic_knight.view;

import com.fantastic_knight.model.Model;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import static com.fantastic_knight.Game.primaryStage;
import static com.fantastic_knight.controller.MenuController.scene_levels;

public class View {

    Model model;
    public Group root;
    public Button buttonLevel1;
    public Button buttonLevel2;
    ViewLevel currentLevel;

    // PANE
    Pane paneIntro, paneGame;

    public View(Model model){
        this.model = model;
        root = new Group();
        menu();
        game();
    }

    public void menu() {
        paneIntro = new Pane();
        paneIntro.setPrefSize(model.width,model.height);

        buttonLevel1 = new Button("Level 1");
        buttonLevel2 = new Button("Level 2");

        buttonLevel1.setLayoutX(0); buttonLevel1.setLayoutY(20);
        buttonLevel2.setLayoutX(0); buttonLevel2.setLayoutY(50);

        paneIntro.getChildren().addAll(buttonLevel1,buttonLevel2);

        root.getChildren().add(paneIntro);
    }

    public void game() {
        paneGame = new Pane();
        paneGame.setPrefSize(model.width,model.height);
    }

    public void startGame() {
        // remove intro pane from the scene
        root.getChildren().clear();
        // add game panel to the scene
        if (model.level == 1) {
            currentLevel = new ViewLevel1(model, paneGame);
        } else if (model.level == 2) {
            currentLevel = new ViewLevel2(model, paneGame);
        }
        root.getChildren().add(paneGame);
    }

    public void returnMenu() {
        model.state = Model.STATE_INITIAL;
        model.reset();
        primaryStage.setScene(scene_levels);
    }
}
