package com.fantastic_knight.view;

import com.fantastic_knight.model.Model;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static com.fantastic_knight.Game.levels;
import static com.fantastic_knight.Game.primaryStage;
import static com.fantastic_knight.controller.MenuController.scene_levels;

public class View {

    public final Group root;
    final Model model;
    ViewLevel currentLevel;

    // PANE
    Pane paneGame;
    ScrollPane scrollPane;

    public View(Model model) {
        this.model = model;
        root = new Group();
        game();
    }

    /**
     * Sets up the game view
     */
    public void game() {
        paneGame = new Pane();
        scrollPane = new ScrollPane();
        paneGame.setPrefSize(model.width, model.height*2);
        scrollPane.setPrefSize(1200,800);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVvalue(scrollPane.getVmax());
        scrollPane.setContent(paneGame);
    }

    /**
     * Start the game (the requested level)
     */
    public void startGame() {
        root.getChildren().clear();

        for (int i = 0; i < levels.size(); i++) {
            if (model.level - 1 == i) {
                currentLevel = new ViewLevel(model, paneGame, i);
                break;
            }
        }
        root.getChildren().add(scrollPane);
    }

    /**
     * Return to menu
     */
    public void returnMenu() {
        model.state = Model.STATE_INITIAL;
        model.reset();
        primaryStage.setScene(scene_levels);
    }

    public Pane getPaneGame() {
        return paneGame;
    }

    public ScrollPane getScrollPane() {
        return scrollPane;
    }
}
