package com.fantastic_knight.model;

import com.fantastic_knight.items.Item;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Heart extends Item {

    Model model;

    public Heart(Model model) {
        super(model);
        this.model = model;
        setShape(new Rectangle(32, 32));
        getShape().setX(0);
        getShape().setY(0);
        Image image = new Image("file:src/main/resources/com/fantastic_knight/assets/anything.png");
        getShape().setFill(new ImagePattern(image));
        setActive(true);
    }

    @Override
    public void update() {
        if (!getActive()) {
            Timer timer = new Timer(3000, this);
            Thread t = new Thread(timer);

            // Display Heart
            getShape().setX(model.player.getShape().getX() - 32);
            getShape().setY(model.player.getShape().getY() - 32);
            getShape().setFill(new ImagePattern(new Image("file:src/main/resources/com/fantastic_knight/assets/heart_burst.png")));
            t.start();
        } else {
            // Remove heart
            getShape().setFill(new ImagePattern(new Image("file:src/main/resources/com/fantastic_knight/assets/anything.png")));
        }
    }
}
