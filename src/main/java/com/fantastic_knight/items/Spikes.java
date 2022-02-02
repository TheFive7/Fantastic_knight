package com.fantastic_knight.items;

import com.fantastic_knight.model.Model;
import com.fantastic_knight.model.Timer;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Spikes extends Item {
    Timer timer = new Timer(1500, this);

    public Spikes(Model m) {
        super(m);
        type = "spike";
        width = 100;
        height = 20;
        shape = new Rectangle(width, height);
        shape.setX(xPosition);
        shape.setY(yPosition);
        image = new Image("file:src/main/resources/com/fantastic_knight/items/spike.png");
        shape.setFill(new ImagePattern(image));
    }

    /**
     * Update spike
     */
    @Override
    public void update() {
        playerHitbox.setX(this.player.getxPosition());
        playerHitbox.setY(this.player.getyPosition());

        Shape inter = Shape.intersect(playerHitbox, shape);
        Bounds b = inter.getBoundsInParent();
        if (b.getWidth() != -1) {
            Thread t = new Thread(timer);
            if (isActive) {
                if (model.player.isLife()) {
                    model.player.setLife(false);
                    model.player.getModel().shield.setFill(new ImagePattern(new Image("file:src/main/resources/com/fantastic_knight/assets/shield_empty.png")));
                    t.start();
                } else {
                    model.player.reset();
                }
            }
        }
    }
}
