package com.fantastic_knight.controller;

import com.fantastic_knight.model.Model;
import javafx.scene.control.Button;

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

class ButtonLevel extends Button {
    public ButtonLevel(double x, double y){
        super();
        setLayoutX(x); setLayoutY(y);
        setWidth(200);
        setHeight(100);
        setStyle("-fx-background-color: #444444 ");
        setStyle("-fx-border: 3px solid #000000 ");
        setStyle("-fx-border-radius: 40px ");
        setStyle("-fx-box-shadow: 0px 4px 5px rgba(0, 0, 0, 0.25) ");
        setOpacity(0);
    }
}
