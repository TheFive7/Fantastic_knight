package com.fantastic_knight.view;

import com.fantastic_knight.controller.RankingMenu;
import com.fantastic_knight.model.Model;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

import java.util.List;

import static com.fantastic_knight.Game.levels;
import static com.fantastic_knight.Game.primaryStage;
import static com.fantastic_knight.controller.MenuController.scene_levels;
import static com.fantastic_knight.model.Model.factor;
import static com.fantastic_knight.model.Model.findAllLevels;

public class View {

    public final Pane root;
    final Model model;
    ViewLevel currentLevel;

    // PANE
    Pane paneGame;
    public ScrollPane scrollPane;

    public View(Model model) {
        this.model = model;
        root = new Pane();
        game();
    }

    /**
     * Sets up the game view
     */
    public void game() {
        scrollPane = new ScrollPane();
        scrollPane.setPrefSize(1200,800);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVvalue(scrollPane.getVmax());
        scrollPane.setStyle("-fx-border-color: black");
        scrollPane.setContent(root);
    }

    /**
     * Start the game (the requested level)
     */
    public void startGame() {
        root.getChildren().clear();

        paneGame = new Pane();
        paneGame.setPrefSize(model.width, model.height * factor);
        for (int i = 0; i < levels.size(); i++) {
            if (model.level - 1 == i) {
                currentLevel = new ViewLevel(model, paneGame, i);
                break;
            }
        }
        root.getChildren().add(paneGame);
    }

    /**
     * Return to menu
     */
    public void returnMenu() {
        if (model.player.isWin()){
            double tempsChrono = model.chrono.getTime();
            List<String> levels = findAllLevels();
            RankingMenu.register(tempsChrono, levels.get(model.level - 1));
        }

        model.state = Model.STATE_INITIAL;
        model.reset();
        primaryStage.setScene(scene_levels);
    }
}
