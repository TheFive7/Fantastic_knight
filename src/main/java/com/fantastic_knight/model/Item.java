package com.fantastic_knight.model;

import com.fantastic_knight.player.Player;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

abstract class Item extends Sprite {
    String type;
    Rectangle shape;
    double xPosition;
    double yPosition;
    double width;
    double height;
    boolean isActive = true;
    boolean animated;
    Image image;
    public Player player = model.player;
    Rectangle playerHitbox = new Rectangle(this.player.getWidth(), this.player.getHeight());

    public Item(Model model) {
        super(model);
    }

    public void setShape(Rectangle shape) {
        this.shape = shape;
    }

    public Rectangle getShape() {
        return shape;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
