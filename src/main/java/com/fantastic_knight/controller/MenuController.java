package com.fantastic_knight.controller;

import com.fantastic_knight.Game;
import com.fantastic_knight.levelMaker.LevelMaker;
import com.fantastic_knight.menu.Credits;
import com.fantastic_knight.menu.LevelsMenu;
import com.fantastic_knight.menu.RankingMenu;
import com.fantastic_knight.model.Sounds;
import com.fantastic_knight.menu.OptionsMenu;
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
    public static boolean isMultiplayerOn = false;

    @FXML
    TextField varPseudo;

    @FXML
    ImageView iconMultiplayer;

    /**
     * Play button
     */
    public void play() {
        LevelsMenu levelsMenu = new LevelsMenu();
        scene_levels = new Scene(levelsMenu.scrollPane, 1200, 800);
        primaryStage.setScene(scene_levels);
    }

    /**
     * Options button
     */
    public void options() {
        OptionsMenu optionsMenu = new OptionsMenu();
        scene_options = new Scene(optionsMenu, 1200,800);
        primaryStage.setScene(scene_options);
    }

    /**
     * LevelMaker button
     */
    public void playLevelMaker() {
        scene_levelMaker = new Scene(new LevelMaker(), 1200, 800);
        primaryStage.setWidth(1500);
        primaryStage.setScene(scene_levelMaker);
    }

    /**
     * Display crédits
     */
    public void displayCredits(){
        Credits credits = new Credits();
        scene_credits = new Scene(credits,1200,800);
        primaryStage.setScene(scene_credits);
    }

    /**
     * Ranking button
     */
    public void ranking() {
        RankingMenu rankingMenu = new RankingMenu();
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPannable(false);
        scrollPane.setPrefSize(1200,800);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVvalue(scrollPane.getVmax());
        scrollPane.setContent(rankingMenu);
        Scene scene_ranking = new Scene(scrollPane, model.width, model.height);
        primaryStage.setScene(scene_ranking);
    }

    /**
     * Multiplayer button
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
     * Get pseudo
     */
    public void pseudo(){
        globalPseudo = varPseudo.getText();
    }

    /**
     * Return to the menu
     */
    public static void returnMenu() {
        Sounds.mediaPlayerBackgroundMusic.play();
        primaryStage.setScene(scene_menu);
    }

    public void quit() {
        System.exit(0);
    }
}
