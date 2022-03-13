package com.fantastic_knight.items;

import com.fantastic_knight.model.Model;
import com.fantastic_knight.model.Timer;
import com.fantastic_knight.player.Player;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Arrow extends Item {

    double xVelocity;
    int factor;
    Timer timer = new Timer(2000, this);

    public Arrow(Model model) {
        super(model);
        type = "arrow";
        width = 50;
        height = 7;

        factor = -1;
        image = new Image("file:src/main/resources/com/fantastic_knight/items/arrow.png");

        xVelocity = 5 * factor;
        shape = new Rectangle(width, height);
        shape.setX(xPosition);
        shape.setY(yPosition);
        shape.setFill(new ImagePattern(image));
    }

    @Override
    public void update() {
        for (Player player : model.players) {
            playerHitbox.setX(player.getxPosition());
            playerHitbox.setY(player.getyPosition());

            getShape().setX(getShape().getX() + xVelocity);

            if (isActive) {
                Shape inter = Shape.intersect(playerHitbox, shape);
                Bounds b = inter.getBoundsInLocal();
                if (b.getWidth() != -1) {
                    if (player.isLife()) {
                        player.setLife(false);
                        Thread t = new Thread(timer);
                        t.start();
                    } else {
                        player.reset();
                    }
                }
            }
        }
    }
}
