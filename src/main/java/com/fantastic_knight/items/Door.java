package com.fantastic_knight.items;

import com.fantastic_knight.model.Model;
import com.fantastic_knight.player.Player;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Door extends Item {
    final double width = 70;
    final double height = 100;
    final Model model;
    Image image;

    public Door(Model model) {
        super(model);
        this.model = model;
        shape = new Rectangle(width, height);
        shape.setWidth(width);
        shape.setHeight(height);
        image = new Image("file:src/main/resources/com/fantastic_knight/assets/door.png");
        shape.setFill(new ImagePattern(image));
    }

    public void update() {
        for (Player player : model.sprites) {
            double x = player.getxPosition();
            double y = player.getyPosition();
            Rectangle joueur = new Rectangle(player.getWidth(), player.getHeight());
            joueur.setX(x);
            joueur.setY(y);

            Shape inter = Shape.intersect(joueur, shape);
            Bounds b = inter.getBoundsInParent();
            if (b.getWidth() != -1) {
                if (!player.isWin() && model.isKey) {
                    player.setWin(true);
                }
            }
        }
    }

    public Model getModel() {
        return model;
    }
}
