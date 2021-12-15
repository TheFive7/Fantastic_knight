package com.fantastic_knight.model;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Spikes extends Item{
    public Spikes(Model m){
        super(m);
        width = 100;
        height = 20;
        shape = new Rectangle(width,height, Color.PURPLE);
        xPosition = 400;
        yPosition = 430;
        shape.setX(xPosition);
        shape.setY(yPosition);
        isActive = true;
    }

    @Override
    public void update() {
        // importer player en mettant ses attributs de position en static pour récup ses attributs et travailler avec sa position
        // comment récup coords player ?
        // importer player en mettant ses attributs de position en static pour récup ses attributs et travailler avec sa position
        // collision ? oui -> meurt
        double x = model.player.getxPosition();
        double y = model.player.getyPosition();
        Rectangle joueur = new Rectangle(model.player.getWidth(),model.player.getHeight());
        joueur.setX(x);
        joueur.setY(y);


        Shape inter = Shape.intersect(joueur,shape);
        Bounds b = inter.getBoundsInParent();
        if (b.getWidth() != -1) {
            Thread t = new Thread(new Chrono(this,model.player,"spikes"));
            if (isActive) t.start();
        }
    }
}