package com.fantastic_knight.items;

import com.fantastic_knight.model.Model;
import com.fantastic_knight.model.Timer;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Arrow extends Item {

    double xVelocity;
    int factor;
    Timer timer = new Timer(5000, this);

    public Arrow(Model model) {
        super(model);
        type = "arrow";
        width = 20;
        height = 7;
        factor = -1;
        xVelocity = 4 * factor;
        shape = new Rectangle(width, height);
        shape.setX(xPosition);
        shape.setY(yPosition);
        image = new Image("file:src/main/resources/com/fantastic_knight/items/arrow.png");
        shape.setFill(new ImagePattern(image));
    }

    @Override
    public void update() {
        playerHitbox.setX(model.player.getxPosition());
        playerHitbox.setY(model.player.getyPosition());

        getShape().setX(getShape().getX() + xVelocity);

        if (isActive){
            Shape inter = Shape.intersect(playerHitbox, shape);
            Bounds b = inter.getBoundsInLocal();
            if (b.getWidth() != -1) {
                if (model.player.isLife()) {
                    model.player.setLife(false);
                    Thread t = new Thread(timer);
                    t.start();
                } else {
                    model.player.reset();
                }
            }
        }
    }
}
