package com.fantastic_knight.ath;

import com.fantastic_knight.player.Player;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Protection extends Circle {
    boolean isActive = false;

    public Protection(Player player){
        super();
        setCenterX(player.getxPosition() + player.getWidth() / 2);
        setCenterY(player.getyPosition() + player.getHeight() / 2);
        setRadius(player.getHeight() / 2 + 10);
        setOpacity(0);
        setFill(Color.TRANSPARENT);
        setStroke(Color.DARKCYAN);
        setStrokeWidth(3);
    }

    public void toggleActive(){
        isActive = !isActive;
    }

    public boolean getActive(){
        return isActive;
    }
}
