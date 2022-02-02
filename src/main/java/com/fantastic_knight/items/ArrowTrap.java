package com.fantastic_knight.items;

import com.fantastic_knight.model.Model;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class ArrowTrap extends Item {

    Arrow arrow;

    public ArrowTrap(Model model) {
        super(model);
        type = "arrowTrap";
        width = 50;
        height = 50;
        shape = new Rectangle(width, height);
        shape.setX(xPosition);
        shape.setY(yPosition);
        image = new Image("file:src/main/resources/com/fantastic_knight/items/arrowTrap.png");
        shape.setFill(new ImagePattern(image));

        arrow = new Arrow(model);
        arrow.getShape().setX(700);
        arrow.getShape().setY(model.height - getShape().getHeight() / 2);
    }

    @Override
    public void update() {
        for (Shape shape : model.obstacles){
            // Parmi tous les obstacles sauf le lanceur
            if (!shape.equals(getShape())){
                // Si la fleche touche un obstacle
                Shape inter = Shape.intersect(shape, arrow.getShape());
                Bounds b = inter.getBoundsInLocal();
                if (b.getWidth() != -1) {
                    arrow.getShape().setX(700);
                    arrow.getShape().setY(model.height - getShape().getHeight() / 2);
                    arrow.setActive(true);
                }
            }
        }
        arrow.update();
    }

    public Arrow getArrow() {
        return arrow;
    }
}

