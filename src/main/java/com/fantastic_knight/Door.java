package com.fantastic_knight;

import com.fantastic_knight.model.Model;
import javafx.geometry.Bounds;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Door extends Rectangle {
    double width = 50;
    double height = 100;
    Model model;

    public Door(Model model) {
        super();
        this.model = model;
        setWidth(width);
        setHeight(height);
    }

    public void update(){
        double x = model.player.getxPosition();
        double y = model.player.getyPosition();
        Rectangle joueur = new Rectangle(model.player.getWidth(),model.player.getHeight());
        joueur.setX(x);
        joueur.setY(y);

        Shape inter = Shape.intersect(joueur,this);
        Bounds b = inter.getBoundsInParent();
        if (b.getWidth() != -1) {
            if (!model.player.isWin()){
                model.player.setWin(true);
            }
        }
    }
}
