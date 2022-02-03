package com.fantastic_knight.items;

import com.fantastic_knight.animation.AnimationImage;
import com.fantastic_knight.model.Model;
import com.fantastic_knight.model.Timer;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Flame extends Item {

    Timer timer = new Timer(2000, this);
    AnimationImage animation;
    Image[] images = new Image[3];

    public Flame(Model model) {
        super(model);
        type = "flame";
        width = 20;
        height = 80;
        shape = new Rectangle(width, height);
        shape.setX(xPosition);
        shape.setY(yPosition);

        images[0] = new Image("file:src/main/resources/com/fantastic_knight/items/fire/fire_column_medium_7.png");
        images[1] = new Image("file:src/main/resources/com/fantastic_knight/items/fire/fire_column_medium_8.png");
        images[2] = new Image("file:src/main/resources/com/fantastic_knight/items/fire/fire_column_medium_9.png");
        animation = new AnimationImage(images, shape, 10);

        shape.setFill(new ImagePattern(images[0]));
    }

    @Override
    public void update() {
        playerHitbox.setX(model.player.getxPosition());
        playerHitbox.setY(model.player.getyPosition());

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
