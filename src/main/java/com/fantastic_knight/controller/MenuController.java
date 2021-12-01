package com.fantastic_knight.controller;

import javafx.scene.Scene;

import static com.fantastic_knight.Game.primaryStage;
import static com.fantastic_knight.Game.view;
import static com.fantastic_knight.Game.model;

public class MenuController {

    public void play() {
        Scene scene = new Scene(view.root, model.width, model.height);
        primaryStage.setScene(scene);
    }
}
