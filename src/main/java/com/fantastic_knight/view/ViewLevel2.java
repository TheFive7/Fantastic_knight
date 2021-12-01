package com.fantastic_knight.view;

import com.fantastic_knight.model.Model;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class ViewLevel2 extends ViewLevel {

    public ViewLevel2(Model model, Pane pane) {
	super(model,pane);
    }

    public void init() {
        model.player.getShape().setFill(Color.WHITE);
        pane.getChildren().addAll(model.player.getShape());
        pane.setStyle("-fx-background-color: black;");
    }
}
