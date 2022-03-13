package com.fantastic_knight.items;

import com.fantastic_knight.model.Model;
import com.fantastic_knight.model.Timer;
import com.fantastic_knight.player.Player;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Button extends Item {

    Timer timer = new Timer(1000, this);

    public Button(Model model) {
        super(model);
        type = "button";
        width = 50;
        height = 20;
        shape = new Rectangle(width, height);
        shape.setX(xPosition);
        shape.setY(yPosition);
        image = new Image("file:src/main/resources/com/fantastic_knight/items/button.png");
        shape.setFill(new ImagePattern(image));
    }

    @Override
    public void update() {
        for (Player player : model.players) {
            playerHitbox.setX(player.getxPosition());
            playerHitbox.setY(player.getyPosition());

            if (isActive) {
                Shape inter = Shape.intersect(playerHitbox, shape);
                Bounds b = inter.getBoundsInLocal();
                if (b.getWidth() != -1) {
                    for (Item item : model.items) {
                        if (!(item.type == null)) {
                            if (item.type.equals("flameTrap")) {
                                ((FlameTrap) item).pressButton();
                                Thread t = new Thread(timer);
                                t.start();
                            }
                        }
                    }
                }
            }
        }
    }
}
