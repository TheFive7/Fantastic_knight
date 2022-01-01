package com.fantastic_knight.levelMaker;

import javafx.scene.shape.Rectangle;

public class Platform extends Rectangle {
    private int xCoordonnee = 0;
    private int yCoordonnee = 0;

    public Platform(){super();}

    public int getxCoordonnee() {
        return xCoordonnee;
    }

    public void setxCoordonnee(int xCoordonnee) {
        this.xCoordonnee = xCoordonnee;
    }

    public int getyCoordonnee() {
        return yCoordonnee;
    }

    public void setyCoordonnee(int yCoordonnee) {
        this.yCoordonnee = yCoordonnee;
    }

    @Override
    public String toString() {
        return xCoordonnee + "," + yCoordonnee + "," + getFill();
    }
}
