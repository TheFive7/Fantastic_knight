package com.fantastic_knight.model;

import com.fantastic_knight.items.Item;
import com.fantastic_knight.player.Player;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Heart extends Item {

    Model model;
    Player player;

    public Heart(Model model, Player player) {
        super(model);
        this.model = model;
        this.player = player;
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
            Timer timer = new Timer(1000, this);
            Thread t = new Thread(timer);

            // Display Heart
            getShape().setX(player.getFirstShape().getX() - 32);
            getShape().setY(player.getFirstShape().getY() - 32);
            getShape().setFill(new ImagePattern(new Image("file:src/main/resources/com/fantastic_knight/assets/heart_burst.png")));
            t.start();
        } else {
            // Remove heart
            getShape().setFill(new ImagePattern(new Image("file:src/main/resources/com/fantastic_knight/assets/anything.png")));
        }
    }
}
