package com.fantastic_knight.items;

import com.fantastic_knight.model.Model;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class SwordPlayer extends Item {

    Image imageRight = new Image("file:src/main/resources/com/fantastic_knight/items/sword_right.png");
    Image imageLeft = new Image("file:src/main/resources/com/fantastic_knight/items/sword_left.png");
    ImagePattern imagePatternRight = new ImagePattern(imageRight);
    ImagePattern imagePatternLeft = new ImagePattern(imageLeft);

    public SwordPlayer(Model model) {
        super(model);
        type = "sword";
        width = 70;
        height = 70;
        shape = new Rectangle(width, height);
        shape.setX(xPosition);
        shape.setY(yPosition);
        isActive = false;
        image = new Image("file:src/main/resources/com/fantastic_knight/assets/anything.png");
        shape.setFill(new ImagePattern(image));
    }

    @Override
    public void update() {
        if (isActive) {
            getShape().setY(model.player.getyPosition());
            if (model.player.getAngle() == 0) {
                getShape().setX(model.player.getxPosition() + 10);
            } else if (model.player.getAngle() == 180) {
                getShape().setX(model.player.getxPosition() - 40);
            }
        }
    }

    /**
     * Turn sword in the right direction
     * @param direction : Direction of the sword
     */
    public void orientationSword(String direction){
        getShape().setY(model.player.getyPosition());
        if (getActive()){
            if (direction.equals("right")) {
                getShape().setFill(imagePatternRight);
                getShape().setX(model.player.getxPosition() + 10);
            } else {
                getShape().setFill(imagePatternLeft);
                getShape().setX(model.player.getxPosition() - 40);
            }
        } else {
            getShape().setFill(new ImagePattern(new Image("file:src/main/resources/com/fantastic_knight/assets/anything.png")));
        }
    }
}
