package com.fantastic_knight.items;

import com.fantastic_knight.model.Model;
import com.fantastic_knight.player.Player;
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
        for (Player player : model.sprites) {
            playerHitbox.setX(player.getxPosition());
            playerHitbox.setY(player.getyPosition());

            Shape inter = Shape.intersect(playerHitbox, shape);
            Bounds b = inter.getBoundsInLocal();
            if (b.getWidth() != -1) {
                player.setxVelocity(1);
                player.setyVelocity(1);
            }
        }
    }
}
