package com.fantastic_knight.controller;

import com.fantastic_knight.Game;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

import static com.fantastic_knight.Game.primaryStage;

public class MenuController {
    public static Scene scene_levels;

    public void play() {
        try {
            FXMLLoader fxmlLoaderMenu = new FXMLLoader(Game.class.getResource("levels.fxml"));
            Parent root_levels = fxmlLoaderMenu.load();
            scene_levels = new Scene(root_levels, 1200, 800);
            primaryStage.setScene(scene_levels);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void quit(){
        System.exit(0);
    }
}
