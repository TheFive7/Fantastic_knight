package com.fantastic_knight.view;

import com.fantastic_knight.menu.RankingMenu;
import com.fantastic_knight.model.Model;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

import java.util.List;

import static com.fantastic_knight.Game.primaryStage;
import static com.fantastic_knight.controller.MenuController.isMultiplayerOn;
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
     * Update scrolling
     */
    public void update() {
        scrollPane.setVvalue((model.player1.getyPosition() + model.player1.getHeight()) / (model.height * factor));

        // SI PLAYER 2 > PLAYER 1
        if (isMultiplayerOn){
            if (model.player2.getyPosition() < model.player1.getyPosition()) {
                scrollPane.setVvalue((model.player2.getyPosition() + model.player2.getHeight()) / (model.height * factor));
            }
        }

        // PLAYER 1
        model.shield.setY((model.player1.getyPosition() + model.player1.getHeight() - model.height * scrollPane.getVvalue()));
        model.sword.setY((model.player1.getyPosition() + model.player1.getHeight() - model.height * scrollPane.getVvalue()));
        model.key.setY((model.player1.getyPosition() + model.player1.getHeight() - model.height * scrollPane.getVvalue()));

        // PLAYER 2
        if (isMultiplayerOn) {
            model.shield2.setY((model.player1.getyPosition() + model.player1.getHeight() - model.height * scrollPane.getVvalue()));
            model.sword2.setY((model.player1.getyPosition() + model.player1.getHeight() - model.height * scrollPane.getVvalue()));
            model.key2.setY((model.player1.getyPosition() + model.player1.getHeight() - model.height * scrollPane.getVvalue()));
        }

        // SI PLAYER 2 > PLAYER 1
        if (isMultiplayerOn){
            if (model.player2.getyPosition() < model.player1.getyPosition()) {
                model.shield.setY((model.player2.getyPosition() + model.player2.getHeight() - model.height * scrollPane.getVvalue()));
                model.sword.setY((model.player2.getyPosition() + model.player2.getHeight() - model.height * scrollPane.getVvalue()));
                model.key.setY((model.player2.getyPosition() + model.player2.getHeight() - model.height * scrollPane.getVvalue()));
                model.shield2.setY((model.player2.getyPosition() + model.player2.getHeight() - model.height * scrollPane.getVvalue()));
                model.sword2.setY((model.player2.getyPosition() + model.player2.getHeight() - model.height * scrollPane.getVvalue()));
                model.key2.setY((model.player2.getyPosition() + model.player2.getHeight() - model.height * scrollPane.getVvalue()));
            }
        }
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
        for (int i = 0; i < Model.levels.size(); i++) {
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
        if (model.player1.isWin()){
            double tempsChrono = model.chrono.getTime();
            List<String> levels = findAllLevels();
            RankingMenu.register(tempsChrono, levels.get(model.level - 1));
        }
        if (isMultiplayerOn){
            if (model.player2.isWin()){
                double tempsChrono = model.chrono.getTime();
                List<String> levels = findAllLevels();
                RankingMenu.register(tempsChrono, levels.get(model.level - 1));
            }
        }
        model.state = Model.STATE_INITIAL;
        model.reset();
        primaryStage.setScene(scene_levels);
    }
}
