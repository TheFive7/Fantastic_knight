package com.fantastic_knight.items;

import com.fantastic_knight.model.Model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class FlameTrap extends Item {

    Flame[] flames = new Flame[5];

    public FlameTrap(Model model) {
        super(model);
        type = "flameTrap";
        width = 100;
        height = 20;
        shape = new Rectangle(width, height);
        shape.setX(xPosition);
        shape.setY(yPosition);
        image = new Image("file:src/main/resources/com/fantastic_knight/items/fire_platform.png");
        shape.setFill(new ImagePattern(image));
    }

    @Override
    public void update() {
        for (Flame flame : flames){
            flame.update();
        }
    }

    public void initFlames(){
        for (int i = 0; i < flames.length; i++) {
            flames[i] = new Flame(model);
            flames[i].getShape().setX(getShape().getLayoutX() + i * 20);
            flames[i].getShape().setY(getShape().getLayoutY() - flames[i].getShape().getHeight());
        }
    }

    public void pressButton(){
        isActive = !isActive;

        for (Flame flame : flames) {
            flame.setActive(!flame.getActive());

            if (!flame.isActive) {
                flame.images[0] = new Image("file:src/main/resources/com/fantastic_knight/assets/anything.png");
                flame.images[1] = new Image("file:src/main/resources/com/fantastic_knight/assets/anything.png");
                flame.images[2] = new Image("file:src/main/resources/com/fantastic_knight/assets/anything.png");
                flame.shape.setFill(new ImagePattern(flame.images[0]));
            } else {
                flame.images[0] = new Image("file:src/main/resources/com/fantastic_knight/items/fire/fire_column_medium_7.png");
                flame.images[1] = new Image("file:src/main/resources/com/fantastic_knight/items/fire/fire_column_medium_8.png");
                flame.images[2] = new Image("file:src/main/resources/com/fantastic_knight/items/fire/fire_column_medium_9.png");
                flame.shape.setFill(new ImagePattern(flame.images[0]));
            }
            flame.animation.toggleTimer();
        }
    }

    public Flame[] getFlame() {
        return flames;
    }
}

