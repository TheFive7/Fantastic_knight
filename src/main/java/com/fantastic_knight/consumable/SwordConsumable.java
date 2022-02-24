package com.fantastic_knight.consumable;

import com.fantastic_knight.model.Model;
import com.fantastic_knight.model.Sword;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class SwordConsumable extends Consumable {

    public SwordConsumable(Model model) {
        super(model);
        type = "sword";
        width = 40;
        height = 40;
        shape = new Rectangle(width, height);
        shape.setX(xPosition);
        shape.setY(yPosition);
        image = new Image("file:src/main/resources/com/fantastic_knight/consumables/sword.png");
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
                model.sword.setFill(new ImagePattern(new Image("file:src/main/resources/com/fantastic_knight/consumables/sword.png")));
                getShape().setFill(new ImagePattern(new Image("file:src/main/resources/com/fantastic_knight/assets/anything.png")));
                model.player.setSword(true);
            }
        }
    }
}
