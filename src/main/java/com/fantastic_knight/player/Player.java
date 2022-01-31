package com.fantastic_knight.player;

import com.fantastic_knight.animation.AnimationImage;
import com.fantastic_knight.model.Sprite;
import com.fantastic_knight.State;
import com.fantastic_knight.model.Model;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

public class Player extends Sprite {
    final Rectangle shape;
    final double width;
    final double height;
    final boolean animated;
    final String name;
    final Image[] imageRight;
    final Image[] imageLeft;
    final double vSaut = 18;
    double xPosition;
    double yPosition;
    double xVelocity; // pixel/s
    double yVelocity; // pixel/s
    boolean life;
    boolean win;
    double angle;
    State state;
    int lastMove;
    Model model;
    AnimationImage animation;

    // 0 RIGHT; 180 LEFT

    public Player(Model model) {
        super(model);
        this.model = model;

        imageRight = new Image[2];
        imageLeft = new Image[2];
        imageRight[0] = new Image("file:src/main/resources/com/fantastic_knight/player/perso_droite_idle.png");
        imageRight[1] = new Image("file:src/main/resources/com/fantastic_knight/player/perso_droite_walk.png");
        imageLeft[0] = new Image("file:src/main/resources/com/fantastic_knight/player/perso_gauche_idle.png");
        imageLeft[1] = new Image("file:src/main/resources/com/fantastic_knight/player/perso_gauche_walk.png");
        width = imageRight[0].getWidth();
        height = imageRight[0].getHeight();

        shape = new Rectangle(width, height);
        life = true;
        xPosition = 0;
        yPosition = model.height - height;
        xVelocity = 5;
        yVelocity = 0;
        angle = 0;
        shape.setX(xPosition);
        shape.setY(yPosition);
        state = State.IDLE;
        life = true;
        shape.setFill(new ImagePattern(imageRight[0]));
        animation = new AnimationImage(imageRight, shape, 10);
        animated = true;
        win = false;
        name = "Knight Red";
    }


    /**
     * Bouge à gauche
     */
    public void moveLeft() {
        if (state == State.JUMP || state == State.FALL) return;

        // Image
        animation.setImages(imageLeft);

        xVelocity = 5;
        yVelocity = 0;
        angle = 180;
        lastMove = 1;
        state = State.WALK;
    }

    /**
     * Bouge à droite
     */
    public void moveRight() {
        if (state == State.JUMP || state == State.FALL) return;

        // Image
        animation.setImages(imageRight);

        xVelocity = 5;
        yVelocity = 0;
        angle = 0;
        lastMove = 2;
        state = State.WALK;
    }

    /**
     * Stop le personnage
     */
    public void stop() {
        xVelocity = 0;
        yVelocity = 0;
        angle = 0;
        state = State.IDLE;
    }

    /**
     * Reset le personnage
     */
    public void reset() {
        xPosition = 0;
        yPosition = model.height - height;
        xVelocity = 2;
        yVelocity = 0;
        angle = 0;
        life = true;
        shape.setX(xPosition);
        shape.setY(yPosition);
        state = State.IDLE;
        model.shield.setFill(new ImagePattern(new Image("file:src/main/resources/com/fantastic_knight/shield.png")));
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

    /**
     * Fais sauter le personnage
     */
    public void jump() {
        if (state == State.JUMP || state == State.IDLE) return;
        if (lastMove == 1) angle = 180;
        else if (lastMove == 2) angle = 0;
        xVelocity = 5;
        yVelocity = -vSaut;
        state = State.JUMP;
    }

    /**
     * Actualisation du joueur
     */
    @Override
    public void update() {

        double x = 0;
        double y = 0;

        Rectangle shape1 = new Rectangle(width, height);

        // Bouge pas
        if (state == State.IDLE) {
            animation.getTimer().stop();
        } else { // moving or falling

            // Si il marche
            if (state == State.WALK) {
                animation.getTimer().start();
            }

            testJump(shape1);
            testWalk(shape1);

            // tombe ou saute
            if (state == State.FALL || state == State.JUMP) {
                if (yPosition >= model.height) state = State.IDLE;
                else yVelocity++;
            }

            testCollision(shape1);

            // Win
            if(isWin()){
                setWin(false);
                model.labelWin.setOpacity(100);
            }
        }
    }

    /**
     * Sauter
     *
     * @param shape1 : Rectangle pour savoir la prochaine position
     */
    public void testJump(Rectangle shape1) {
        if (state == State.JUMP) {
            double y = yPosition + yVelocity;
            shape1.setX(xPosition);
            shape1.setY(y);

            // Détection de collision
            boolean isFloor = false;
            for (Shape s : model.obstacles) {
                Shape inter = Shape.intersect(shape1, s);
                Bounds b = inter.getBoundsInLocal();
                if (b.getWidth() != -1) {
                    isFloor = true;
                }
            }

            // Si il y a le sol
            if (isFloor) {
                state = State.FALL;
                angle = 90;
            } else {
                yPosition += yVelocity;
            }
        }
    }

    /**
     * Marche
     *
     * @param shape1 : Rectangle pour savoir la prochaine position
     */
    public void testWalk(Rectangle shape1) {
        if (state == State.WALK) {
            // NO FLOOR DETECTION for actual position: just move perso one pixel down
            shape1.setX(xPosition);
            shape1.setY(yPosition + 1);

            // Détection de collision
            boolean isFloor = false;
            for (Shape s : model.obstacles) {
                Shape inter = Shape.intersect(shape1, s);
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
     *
     * @param shape1 : Rectangle pour savoir la prochaine position
     */
    public void testCollision(Rectangle shape1) {
        double x = getNewX();
        double y = getNewY();

        // COLLISION DETECTION
        shape1.setX(x);
        shape1.setY(y);

        // Which obstacle in collision with Player
        List<Shape> lst = new ArrayList<>();
        for (Shape s : model.obstacles) {
            Shape inter = Shape.intersect(shape1, s);
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
                shape1.setX(x);
                shape1.setY(y);
                boolean collide = false;
                for (Shape s : lst) {
                    Shape inter = Shape.intersect(shape1, s);
                    Bounds b = inter.getBoundsInParent();
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

    public Rectangle getShape() {
        return shape;
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

    public void setxVelocity(double xVelocity) {
        this.xVelocity = xVelocity;
    }

    public void setyVelocity(double yVelocity) {
        this.yVelocity = yVelocity;
    }

    public double getAngle() {
        return angle;
    }

    public State getState() {
        return state;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public boolean isLife() {
        return life;
    }

    public void setLife(boolean life) {
        this.life = life;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public AnimationImage getAnimation() {
        return animation;
    }

    public void setAnimation(AnimationImage animation) {
        this.animation = animation;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }
}
