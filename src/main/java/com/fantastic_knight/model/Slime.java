package com.fantastic_knight.model;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Slime extends Item {
    public Slime(Model m) {
        super(m);
        width = 100;
        height = 20;
        shape = new Rectangle(width, height);
        shape.setX(0);
        shape.setY(0);
        image = new Image("file:src/main/resources/com/fantastic_knight/items/slime.png");
        shape.setFill(new ImagePattern(image));
    }

    /**
     * update slime
     */
    @Override
    public void update() {
        playerHitbox.setX(this.player.getxPosition());
        playerHitbox.setY(this.player.getyPosition());

        Shape inter = Shape.intersect(playerHitbox, shape);
        Bounds b = inter.getBoundsInParent();
        if (b.getWidth() != -1) {
            model.player.setIsTouchingSlime(true);
            model.player.setxVelocity(1);
            return;
        }
        model.player.setIsTouchingSlime(false);
    }
}