package com.fantastic_knight.controller;

import com.fantastic_knight.levelMaker.LevelMaker;
import javafx.scene.Scene;

import static com.fantastic_knight.Game.primaryStage;

public class MenuController {
    public static Scene scene_levels;
    public static Scene scene_levelMaker;

    /**
     * Bouton PLAY
     */
    public void play() {
        LevelsMenu levelsMenu = new LevelsMenu();
        scene_levels = new Scene(levelsMenu, 1200, 800);
        primaryStage.setScene(scene_levels);
    }

    /**
     * Bouton PLAY du LevelMaker
     */
    public void playLevelMaker(){
        scene_levelMaker = new Scene(new LevelMaker(), 1200, 800);
        primaryStage.setWidth(1500);
        primaryStage.setScene(scene_levelMaker);
    }

    public void quit(){
        System.exit(0);
    }
}
