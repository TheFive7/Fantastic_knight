package com.fantastic_knight.model;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

abstract class Item extends Sprite {
    Rectangle shape;
    String effect;
    double xPosition;
    double yPosition;
    double width;
    double height;
    boolean isActive;
    boolean animated;
    Image image;

    public Item(Model model) {
        super(model);
    }

    public void setShape(Rectangle shape) {
        this.shape = shape;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
