package com.fantastic_knight.player;

import com.fantastic_knight.animation.AnimationImage;
import com.fantastic_knight.model.Chrono;
import com.fantastic_knight.model.Model;
import com.fantastic_knight.model.State;
import com.fantastic_knight.model.Timer;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

import static com.fantastic_knight.controller.MenuController.isMultiplayerOn;
import static com.fantastic_knight.model.Model.factor;

public class Ennemy extends Sprite {
    Rectangle shape;
    Rectangle shapeNextPos;
    final double width;
    final double height;
    double xPosition;
    double yPosition;
    double xVelocity; // pixel/s
    double yVelocity; // pixel/s
    int lastMove;
    boolean life;
    double angle;
    State state;
    final Image[] imageRight;
    final Image[] imageLeft;
    AnimationImage animation;
    Timer timer = new Timer(500, this);
    Chrono chrono = new Chrono();
    Thread thread = new Thread(chrono);
    boolean isActive;
    int timeRound;

    public Ennemy(Model model){
        super(model);
        // Image
        imageRight = new Image[3];
        imageLeft = new Image[3];
        imageRight[0] = new Image("file:src/main/resources/com/fantastic_knight/player/Knight_stand_right_gold.png");
        imageRight[1] = new Image("file:src/main/resources/com/fantastic_knight/player/Knight_walk_right_gold.png");
        imageRight[2] = new Image("file:src/main/resources/com/fantastic_knight/player/Knight_walk1_right_gold.png");
        imageLeft[0] = new Image("file:src/main/resources/com/fantastic_knight/player/Knight_stand_left_gold.png");
        imageLeft[1] = new Image("file:src/main/resources/com/fantastic_knight/player/Knight_walk_left_gold.png");
        imageLeft[2] = new Image("file:src/main/resources/com/fantastic_knight/player/Knight_walk1_left_gold.png");

        // SIZE
        width = imageRight[0].getWidth();
        height = imageRight[0].getHeight();
        shape = new Rectangle(width, height);
        shape.setFill(new ImagePattern(imageRight[0]));
        shapeNextPos = new Rectangle(width, height);
        shapeNextPos.setOpacity(0);

        // POSITION
        xPosition = 700;
        yPosition = model.height * factor - height;
        shape.setX(xPosition);
        shape.setY(yPosition);

        // SPEED
        xVelocity = 1;
        yVelocity = 0;

        // OTHER
        animation = new AnimationImage(imageRight, shape, 10);
        state = State.IDLE;
        angle = 0;
        life = true;
        isActive = true;
        timeRound = 3;
        thread.start();
    }

    /**
     * Bouge à gauche
     */
    public void moveLeft() {
        if (state == State.FALL) return;
        animation.setImages(imageLeft);
        xVelocity = 1;
        yVelocity = 0;
        angle = 180;
        lastMove = 1;
        state = State.WALK;
    }

    /**
     * Bouge à droite
     */
    public void moveRight() {
        if (state == State.FALL) return;
        // Image
        animation.setImages(imageRight);
        xVelocity = 1;
        yVelocity = 0;
        angle = 0;
        lastMove = 2;
        state = State.WALK;
    }

    /**
     * Stop l'ennemi
     */
    public void stop() {
        xVelocity = 0;
        yVelocity = 0;
        if (lastMove == 1) angle = 180;
        else angle = 0;
        state = State.IDLE;
    }

    /**
     * Reset l'ennemi
     */
    public void reset() {
        xPosition = 700;
        yPosition = model.height * factor - height;
        xVelocity = 1;
        yVelocity = 0;
        angle = 0;
        life = true;
        isActive = true;
        shape.setX(xPosition);
        shape.setY(yPosition);
        state = State.IDLE;
        Thread thread = new Thread(chrono);
        thread.start();
        animation.getTimer().start();
    }

    /**
     * Retourne la nouvelle abscisse
     * @return : La nouvelle abscisse
     */
    private double getNewX() {
        double x = getxPosition();
        x += Math.cos(Math.toRadians(angle)) * xVelocity;
        return x;
    }

    /**
     * Retourne la nouvelle ordonnée
     * @return : Nouvelle ordonnéee
     */
    private double getNewY() {
        double y = getyPosition();
        y += Math.sin(Math.toRadians(angle)) * yVelocity;
        return y;
    }

    @Override
    public void update() {
        if (life) {
            // Collision
            for (Player player : model.players) {
                Shape inter = Shape.intersect(player.getShape(), shape);
                Bounds b = inter.getBoundsInParent();
                if (b.getWidth() != -1) {
                    if (!player.swordPlayer.getActive()) {
                        Thread t = new Thread(timer);
                        if (isActive) {
                            if (player.isLife()) {
                                player.setLife(false);
                                t.start();
                            } else {
                                player.reset();
                            }
                        }
                    } else {
                        isActive = false;
                        life = false;
                        xPosition = -1000;
                        animation.getTimer().stop();
                        chrono.terminate();
                    }
                }
            }

            // SIMPLE AI
            if (chrono.getTime() < timeRound) {
                moveRight();
            } else if (chrono.getTime() >= timeRound && chrono.getTime() <= timeRound*2){
                moveLeft();
            } else {
                chrono.setTime(0);
            }

            // Bouge pas
            if (state == State.IDLE) {
                animation.getTimer().stop();
            } else { // moving or falling

                // Si il marche
                if (state == State.WALK || state == State.DASH) {
                    animation.getTimer().start();
                }

                testWalk();

                // tombe ou saute
                if (state == State.FALL || state == State.JUMP) {
                    if (yPosition >= model.height * factor) state = State.IDLE;
                    else yVelocity++;
                }

                testCollision();
            }
        }
    }

    /**
     * Marche
     */
    public void testWalk() {
        if (state == State.WALK || state == State.DASH) {
            // NO FLOOR DETECTION for actual position: just move perso one pixel down
            shapeNextPos.setX(xPosition);
            shapeNextPos.setY(yPosition + 1);

            // Détection de collision
            boolean isFloor = false;
            for (Shape s : model.obstacles) {
                Shape inter = Shape.intersect(shapeNextPos, s);
                Bounds b = inter.getBoundsInLocal();
                if (b.getWidth() != -1) {
                    isFloor = true;
                }
            }

            // No floor => change angle & state to falling
            if (!isFloor) {
                state = State.FALL;
                angle = 90;
            }
        }
    }

    /**
     * Collision
     */
    public void testCollision() {
        double x = getNewX();
        double y = getNewY();

        // COLLISION DETECTION
        shapeNextPos.setX(x);
        shapeNextPos.setY(y);

        // Which obstacle in collision with Player
        List<Shape> lst = new ArrayList<>();
        for (Shape s : model.obstacles) {
            Shape inter = Shape.intersect(shapeNextPos, s);
            Bounds b = inter.getBoundsInLocal();
            if (b.getWidth() != -1) {
                lst.add(s);
            }
        }

        // list not empty => collision
        // search for the minimal move to get a collision
        if (!lst.isEmpty()) {
            for (int i = 1; i <= xVelocity; i++) {
                xVelocity = i;
                yVelocity = i;
                x = getNewX();
                y = getNewY();
                shapeNextPos.setX(x);
                shapeNextPos.setY(y);
                boolean collide = false;
                for (Shape s : lst) {
                    Shape inter = Shape.intersect(shapeNextPos, s);
                    Bounds b = inter.getBoundsInLocal();
                    if (b.getWidth() != -1) {
                        collide = true;
                    }
                }

                // Si collision
                if (collide) {
                    state = State.IDLE; // stop moving
                    xVelocity = i - 1;
                    yVelocity = i - 1;
                    x = getNewX();
                    y = getNewY();
                    xVelocity = 0;
                    yVelocity = 0;
                    break;
                }
            }
            setxVelocity(xVelocity);
            setyVelocity(yVelocity);
        }
        // RENDER
        setxPosition(x);
        setyPosition(y);
        shape.setX(getxPosition());
        shape.setY(getyPosition());
    }

    public void setxVelocity(double xVelocity) {
        this.xVelocity = xVelocity;
    }

    public void setyVelocity(double yVelocity) {
        this.yVelocity = yVelocity;
    }

    public double getxPosition() {
        return xPosition;
    }

    public void setxPosition(double x) {
        this.xPosition = x;
    }

    public double getyPosition() {
        return yPosition;
    }

    public void setyPosition(double y) {
        this.yPosition = y;
    }

    public void setActive(boolean bool) {
        this.isActive = bool;
    }

    public Rectangle getShape() {
        return shape;
    }

    public Rectangle getFirstShape() {
        return shapeNextPos;
    }
}
