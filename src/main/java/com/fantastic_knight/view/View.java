package com.fantastic_knight.view;

import com.fantastic_knight.model.Model;
import javafx.scene.Group;
import javafx.scene.layout.Pane;

import static com.fantastic_knight.Game.levels;
import static com.fantastic_knight.Game.primaryStage;
import static com.fantastic_knight.controller.MenuController.scene_levels;

public class View {

    public final Group root;
    final Model model;
    ViewLevel currentLevel;

    // PANE
    Pane paneGame;

    public View(Model model) {
        this.model = model;
        root = new Group();
        game();
    }


    /**
     * Met en place la vue du jeu
     */
    public void game() {
        paneGame = new Pane();
        paneGame.setPrefSize(model.width, model.height);
    }

    /**
     * Démarre le jeu (le niveau demandé)
     */
    public void startGame() {
        root.getChildren().clear();

        for (int i = 0; i < levels.size(); i++) {
            if (model.level - 1 == i) {
                currentLevel = new ViewLevel(model, paneGame, i);
                break;
            }
        }
        root.getChildren().add(paneGame);
    }

    /**
     * Retourne au menu
     */
    public void returnMenu() {
        model.state = Model.STATE_INITIAL;
        model.reset();
        primaryStage.setScene(scene_levels);
    }
}
