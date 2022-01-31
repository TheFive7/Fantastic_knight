package com.fantastic_knight.model;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Door extends Rectangle {
    final double width = 70;
    final double height = 100;
    final Model model;
    Image image;

    public Door(Model model) {
        super();
        this.model = model;
        setWidth(width);
        setHeight(height);
        image = new Image("file:src/main/resources/com/fantastic_knight/door.png");
        setFill(new ImagePattern(image));
    }

    public void update() {
        double x = model.player.getxPosition();
        double y = model.player.getyPosition();
        Rectangle joueur = new Rectangle(model.player.getWidth(), model.player.getHeight());
        joueur.setX(x);
        joueur.setY(y);

        Shape inter = Shape.intersect(joueur, this);
        Bounds b = inter.getBoundsInParent();
        if (b.getWidth() != -1) {
            if (!model.player.isWin()) {
                model.player.setWin(true);
            }
        }
    }

    public Model getModel() {
        return model;
    }
}
