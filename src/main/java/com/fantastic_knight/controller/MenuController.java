package com.fantastic_knight.controller;

import com.fantastic_knight.Game;
import com.fantastic_knight.levelMaker.LevelMaker;
import com.fantastic_knight.menu.LevelsMenu;
import com.fantastic_knight.options.OptionsMenu;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static com.fantastic_knight.Game.*;

public class MenuController {
    public static Scene scene_levels;
    public static Scene scene_levelMaker;
    public static Scene scene_options;
    public static String globalPseudo;
    public boolean isMultiplayerOn = false;

    @FXML
    TextField varPseudo;

    @FXML
    ImageView iconMultiplayer;

    /**
     * Bouton PLAY
     */
    public void play() {
        LevelsMenu levelsMenu = new LevelsMenu();
        scene_levels = new Scene(levelsMenu, 1200, 800);
        primaryStage.setScene(scene_levels);
    }

    /**
     * Bouton Options
     */
    public void options() {
        OptionsMenu optionsMenu = new OptionsMenu();
        scene_options = new Scene(optionsMenu, 1200,800);
        primaryStage.setScene(scene_options);
    }

    /**
     * Bouton PLAY LevelMaker
     */
    public void playLevelMaker() {
        scene_levelMaker = new Scene(new LevelMaker(), 1200, 800);
        primaryStage.setWidth(1500);
        primaryStage.setScene(scene_levelMaker);
    }

    public void displayCredits(){
        primaryStage.setScene(scene_credits);
    }

    /**
     * Bouton RANKING
     */
    public void ranking() {
        RankingMenu rankingMenu = new RankingMenu();
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setContent(rankingMenu);
        Scene scene_ranking = new Scene(scrollPane, model.width, model.height);
        primaryStage.setScene(scene_ranking);
    }

    /**
     * Bouton Multiplayer
     */
    public void multiplayer() {
        if (isMultiplayerOn){
            isMultiplayerOn = false;
            Image image = new Image(""+Game.class.getResource("icons/off.png"));
            iconMultiplayer.setImage(image);
        } else {
            isMultiplayerOn = true;
            Image image = new Image(""+Game.class.getResource("icons/on.png"));
            iconMultiplayer.setImage(image);
        }
    }

    /**
     * récupère le pseudo du textfield
     */
    public void pseudo(){
        globalPseudo = varPseudo.getText();
    }

    public void quit() {
        System.exit(0);
    }
}
