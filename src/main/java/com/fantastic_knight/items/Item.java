package com.fantastic_knight.items;

import com.fantastic_knight.model.Model;
import com.fantastic_knight.player.Player;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public abstract class Item {
    Model model;
    String type;
    Rectangle shape;
    double xPosition;
    double yPosition;
    double width;
    double height;
    boolean isActive;
    Image image;
    Player player;
    Rectangle playerHitbox;

    public Item(Model model){
        this.model = model;
        isActive = true;
        xPosition = 0;
        yPosition = 0;
        player = model.player;
        playerHitbox = new Rectangle(this.player.getWidth(), this.player.getHeight());
    }

    public abstract void update();

    public void setShape(Rectangle shape) {
        this.shape = shape;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
