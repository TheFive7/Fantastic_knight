package com.fantastic_knight.model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Shield extends Rectangle {
    double width, height;
    double xpos, ypos;
    Image image;
    public Shield() {
        width = 100;
        height = 100;
        setWidth(width);
        setHeight(height);
        xpos = 1100;
        ypos = 0;
        setX(xpos);
        setY(ypos);
        image = new Image("file:src/main/resources/com/fantastic_knight/shield.png");
        setFill(new ImagePattern(image));
    }
}
