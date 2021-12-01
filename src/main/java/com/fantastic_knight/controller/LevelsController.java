package com.fantastic_knight.controller;

import com.fantastic_knight.model.Model;
import static com.fantastic_knight.Game.*;
import static com.fantastic_knight.Game.primaryStage;

public class LevelsController {

    public void selectLevel1(){
        if (model.state == Model.STATE_INITIAL) {
            primaryStage.setScene(scene);
            model.level = 1;
            System.out.println("Level " + model.level);
            controller.startGame();
        }
    }

    public void selectLevel2(){
        if (model.state == Model.STATE_INITIAL) {
            primaryStage.setScene(scene);
            model.level = 2;
            System.out.println("Level "+ model.level);
            controller.startGame();
        }
    }

    public void returnMenu(){
        primaryStage.setScene(scene_menu);
    }
}
