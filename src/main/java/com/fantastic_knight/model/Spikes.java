package com.fantastic_knight.model;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Spikes extends Item {
    public Spikes(Model m) {
        super(m);
        width = 100;
        height = 20;
        shape = new Rectangle(width, height);
        xPosition = 0;
        yPosition = 0;
        shape.setX(xPosition);
        shape.setY(yPosition);
        isActive = true;
        image = new Image("file:src/main/resources/com/fantastic_knight/items/spike.png");
        shape.setFill(new ImagePattern(image));
    }

    /**
     * update spike
     */
    @Override
    public void update() {
        double x = model.player.getxPosition();
        double y = model.player.getyPosition();
        Rectangle joueur = new Rectangle(model.player.getWidth(), model.player.getHeight());
        joueur.setX(x);
        joueur.setY(y);

        Shape inter = Shape.intersect(joueur, shape);
        Bounds b = inter.getBoundsInParent();
        if (b.getWidth() != -1) {
            Thread t = new Thread(new Chrono(this, model.player, "spikes"));
            if (isActive) t.start();
            isActive = false;
        }
    }
}