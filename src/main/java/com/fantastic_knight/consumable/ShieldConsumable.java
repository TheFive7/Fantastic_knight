package com.fantastic_knight.consumable;

import com.fantastic_knight.items.FlameTrap;
import com.fantastic_knight.items.Item;
import com.fantastic_knight.model.Model;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class ShieldConsumable extends Consumable {

    public ShieldConsumable(Model model) {
        super(model);
        type = "shield";
        width = 50;
        height = 50;
        shape = new Rectangle(width, height);
        shape.setX(xPosition);
        shape.setY(yPosition);
        image = new Image("file:src/main/resources/com/fantastic_knight/assets/shield.png");
        shape.setFill(new ImagePattern(image));
    }

    @Override
    public void update() {
        playerHitbox.setX(model.player.getxPosition());
        playerHitbox.setY(model.player.getyPosition());

        if (isActive){
            Shape inter = Shape.intersect(playerHitbox, shape);
            Bounds b = inter.getBoundsInLocal();
            if (b.getWidth() != -1) {
                isActive = false;
                model.player.setLife(true);
                getShape().setFill(new ImagePattern(new Image("file:src/main/resources/com/fantastic_knight/assets/anything.png")));
            }
        }
    }
}
