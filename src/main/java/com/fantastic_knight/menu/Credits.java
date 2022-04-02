package com.fantastic_knight.menu;

import com.fantastic_knight.model.Sounds;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import static com.fantastic_knight.controller.MenuController.returnMenu;

public class Credits extends Pane {
    public ScrollPane sp;

    public Credits() {
        // SOUNDS
        Sounds.mediaPlayerBackgroundMusic.stop();
        Sounds.mediaPlayerCredit.play();

        // SCROLLPANE
        sp = new ScrollPane();
        Image credits = new Image("file:src/main/resources/com/fantastic_knight/menu/credits.png");
        sp.setContent(new ImageView(credits));
        sp.setPrefHeight(800);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        setHeight(1600);
        defile();

        // Button return menu
        Button buttonMenu = new Button();
        buttonMenu.setLayoutX(1115);
        buttonMenu.setLayoutY(697);
        buttonMenu.setOnAction(e -> {
            Sounds.mediaPlayerCredit.stop();
            returnMenu();
        });
        ImageView imageView = new ImageView(new Image("file:src/main/resources/com/fantastic_knight/icons/back.png"));
        buttonMenu.setGraphic(imageView);
        buttonMenu.setPrefSize(40, 40);
        buttonMenu.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,null,null)));

        getChildren().addAll(sp,buttonMenu);
    }

    public void generiqueCredits(ScrollPane sp){
        sp.setVvalue(sp.getVvalue() + 0.001);
    }

    public void defile() {
        Timeline t = new Timeline(new KeyFrame(Duration.millis(1000/60.0), ex -> generiqueCredits(sp)));
        t.setCycleCount(50000000);
        t.play();
    }
}
