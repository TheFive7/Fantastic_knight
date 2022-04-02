package com.fantastic_knight.items;

import com.fantastic_knight.model.Model;
import com.fantastic_knight.model.Timer;
import com.fantastic_knight.player.Player;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Spikes extends Item {
    Timer timer = new Timer(500, this);

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

    @Override
    public void update() {
        for (Player player : model.players) {
            playerHitbox.setX(player.getxPosition());
            playerHitbox.setY(player.getyPosition());

            Shape inter = Shape.intersect(playerHitbox, shape);
            Bounds b = inter.getBoundsInParent();
            if (b.getWidth() != -1) {
                Thread t = new Thread(timer);
                if (isActive) {
                    if (player.isLife()) {
                        player.setLife(false);
                        t.start();
                    } else {
                        player.reset();
                    }
                }
            }
        }
    }
}
