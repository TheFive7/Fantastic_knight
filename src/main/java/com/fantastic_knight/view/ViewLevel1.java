package com.fantastic_knight.view;

import com.fantastic_knight.Game;
import com.fantastic_knight.model.Model;
import javafx.scene.image.Image;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class ViewLevel1 extends ViewLevel {

    public ViewLevel1(Model model, Pane pane) {
	super(model,pane);
    }

    public void init() {
        // Background
        pane.setStyle("-fx-background-image: url('"+ Game.class.getResource("bg.png")+"')");

        // Obstacles
        model.r1 = new Rectangle(0,150,300,20); model.r1.setFill(new ImagePattern(new Image("file:src/main/resources/com/fantastic_knight/shield.png")));
        model.r2 = new Rectangle(150,300,300,20); model.r2.setFill(Color.YELLOW);
        model.r3 = new Rectangle(300,450,300,20); model.r3.setFill(Color.BLUE);
        model.obstacles.add(model.r1);
        model.obstacles.add(model.r2);
        model.obstacles.add(model.r3);

        // SPIKES
        model.spikes.getShape().setX(500);
        model.spikes.getShape().setY(model.height - model.spikes.getShape().getHeight());
        model.items.add(model.spikes);

        pane.getChildren().addAll(model.spikes.getShape(),model.player.getShape(),model.r1,model.r2,model.r3);


    }
}
