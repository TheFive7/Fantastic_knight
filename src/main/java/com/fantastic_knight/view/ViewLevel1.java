package com.fantastic_knight.view;

import com.fantastic_knight.model.Model;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class ViewLevel1 extends ViewLevel {

    public ViewLevel1(Model model, Pane pane) {
	super(model,pane);
    }

    public void init() {
        // Obstacles
        model.r1 = new Rectangle(0,150,300,20); model.r1.setFill(Color.RED);
        model.r2 = new Rectangle(150,300,300,20); model.r2.setFill(Color.YELLOW);
        model.r3 = new Rectangle(300,450,300,20); model.r3.setFill(Color.BLUE);
        model.obstacles.add(model.r1);
        model.obstacles.add(model.r2);
        model.obstacles.add(model.r3);
        model.items.add(model.spikes);

/*        model.spikes.getShape().setFill(Color.PURPLE);*/
        pane.getChildren().addAll(model.spikes.getShape(),model.player.getShape(),model.r1,model.r2,model.r3);
        pane.setStyle("-fx-background-color: white;");
    }
}
