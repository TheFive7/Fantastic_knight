package com.fantastic_knight.model;

import com.fantastic_knight.Sprite;
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

    public Item(Model model) { super(model); }

    public Rectangle getShape() { return shape; }

    public void setShape(Rectangle shape) {
        this.shape = shape;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public double getxPosition() {
        return xPosition;
    }

    public void setxPosition(double xPosition) {
        this.xPosition = xPosition;
    }

    public double getyPosition() {
        return yPosition;
    }

    public void setyPosition(double yPosition) {
        this.yPosition = yPosition;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isAnimated() {
        return animated;
    }

    public void setAnimated(boolean animated) {
        this.animated = animated;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
