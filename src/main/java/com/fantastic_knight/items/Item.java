package com.fantastic_knight.items;

import com.fantastic_knight.model.Model;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;

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
    Rectangle playerHitbox;

    public Item(Model model){
        this.model = model;
        type = "item";
        isActive = true;
        xPosition = 0;
        yPosition = 0;
        playerHitbox = model.player1.getShape();
    }

    public abstract void update();

    public void setShape(Rectangle shape) {
        this.shape = shape;
    }

    public Rectangle getShape(){return this.shape;}

    public Image getImage() {
        return image;
    }

    public boolean getActive() {
        return isActive;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getType(){return type;}
}
