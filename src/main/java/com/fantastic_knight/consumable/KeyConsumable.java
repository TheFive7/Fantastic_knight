package com.fantastic_knight.consumable;

import com.fantastic_knight.model.Model;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class KeyConsumable extends Consumable {

    public KeyConsumable (Model model) {
        super(model);
        type = "key";
        width = 40;
        height = 40;
        shape = new Rectangle(width, height);
        shape.setX(xPosition);
        shape.setY(yPosition);
        image = new Image("file:src/main/resources/com/fantastic_knight/assets/key.png");
        shape.setFill(new ImagePattern(image));
    }

    @Override
    public void update() {
        playerHitbox.setX(model.player.getxPosition());
        playerHitbox.setY(model.player.getyPosition());

        if (isActive){
            Shape inter = Shape.intersect(playerHitbox, shape);
            Bounds b = inter.getBoundsInLocal();
            if (!model.isKey) {
                if (b.getWidth() != -1) {
                    isActive = false;
                    model.isKey = true;
                    getShape().setFill(new ImagePattern(new Image("file:src/main/resources/com/fantastic_knight/assets/anything.png")));
                }
            }
        }
    }

}
