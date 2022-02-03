package com.fantastic_knight.controller;

import com.fantastic_knight.levelMaker.LevelMaker;
import com.fantastic_knight.menu.LevelsMenu;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

import static com.fantastic_knight.Game.*;


public class MenuController {
    public static Scene scene_levels;
    public static Scene scene_levelMaker;
    public static String globalPseudo;

    @FXML
    TextField varPseudo;

    /**
     * Bouton PLAY
     */
    public void play() {
        LevelsMenu levelsMenu = new LevelsMenu();
        scene_levels = new Scene(levelsMenu, 1200, 800);
        primaryStage.setScene(scene_levels);
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
        Scene scene_ranking = new Scene(rankingMenu, model.width, model.height);
        primaryStage.setScene(scene_ranking);
    }

    /**
     * récuprère le pseudo du textfield
     */
    public void pseudo(){
        String pseudo = varPseudo.getText();
        globalPseudo = pseudo;

        System.out.println(pseudo);
    }

    public void quit() {
        System.exit(0);
    }
}
