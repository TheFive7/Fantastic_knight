package com.fantastic_knight.model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Sword extends Rectangle {
    final double width;
    final double height;
    final double xpos;
    final double ypos;
    Image image;

    public Sword() {
        width = 75;
        height = 75;
        setWidth(width);
        setHeight(height);
        xpos = 75;
        ypos = 0;
        setX(xpos);
        setY(ypos);
        image = new Image("file:src/main/resources/com/fantastic_knight/assets/no_sword.png");
        setFill(new ImagePattern(image));
    }

    public void setImage(Image image){
        this.image = image;
    }
}
