package com.fantastic_knight.model;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Slime extends Item {
    public Slime(Model m) {
        super(m);
        type = "slime";
        width = 100;
        height = 20;
        shape = new Rectangle(width, height);
        xPosition = 0;
        yPosition = 0;
        shape.setX(xPosition);
        shape.setY(yPosition);
        image = new Image("file:src/main/resources/com/fantastic_knight/items/slime.png");
        shape.setFill(new ImagePattern(image));
    }

    /**
     * Update slime
     */
    @Override
    public void update() {
        playerHitbox.setX(this.player.getxPosition());
        playerHitbox.setY(this.player.getyPosition());

        Shape inter = Shape.intersect(playerHitbox, shape);
        Bounds b = inter.getBoundsInLocal();
        if (b.getWidth() != -1) {
            model.player.setxVelocity(1);
            model.player.setyVelocity(1);
        }
    }
}
