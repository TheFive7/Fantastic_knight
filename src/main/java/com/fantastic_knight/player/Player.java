package com.fantastic_knight.player;

import com.fantastic_knight.animation.AnimationImage;
import com.fantastic_knight.items.Item;
import com.fantastic_knight.model.Sprite;
import com.fantastic_knight.model.State;
import com.fantastic_knight.model.Model;
import com.fantastic_knight.model.Timer;
import com.fantastic_knight.objects.Protection;
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
    boolean invincibility;
    boolean win;
    double angle;
    State state;
    int lastMove;
    Model model;
    AnimationImage animation;
    final double vDash = 10;
    Protection protection;
    boolean isSword;

    // 0 RIGHT; 180 LEFT

    public Player(Model model) {
        super(model);
        this.model = model;

        imageRight = new Image[3];
        imageLeft = new Image[3];
        imageRight[0] = new Image("file:src/main/resources/com/fantastic_knight/player/Knight_stand_right.png");
        imageRight[1] = new Image("file:src/main/resources/com/fantastic_knight/player/Knight_walk_right.png");
        imageRight[2] = new Image("file:src/main/resources/com/fantastic_knight/player/Knight_walk1_right.png");
        imageLeft[0] = new Image("file:src/main/resources/com/fantastic_knight/player/Knight_stand_left.png");
        imageLeft[1] = new Image("file:src/main/resources/com/fantastic_knight/player/Knight_walk_left.png");
        imageLeft[2] = new Image("file:src/main/resources/com/fantastic_knight/player/Knight_walk1_left.png");
        width = imageRight[0].getWidth();
        height = imageRight[0].getHeight();

        shape = new Rectangle(width, height);
        life = true;
        invincibility = false;
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
        protection = new Protection(this);
        isSword = false;
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

        model.swordPlayer.orientationSword("left");

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

        model.swordPlayer.orientationSword("right");
    }

    /**
     * Stop le personnage
     */
    public void stop() {
        xVelocity = 0;
        yVelocity = 0;
        if (lastMove == 1) angle = 180;
        else angle = 0;
        state = State.IDLE;
    }

    /**
     * Reset le personnage
     */
    public void reset() {
        setWin(false);
        xPosition = 0;
        yPosition = model.height - height;
        xVelocity = 5;
        yVelocity = 0;
        angle = 0;
        life = true;
        shape.setX(xPosition);
        shape.setY(yPosition);
        state = State.IDLE;
        model.shield.setFill(new ImagePattern(new Image("file:src/main/resources/com/fantastic_knight/assets/shield.png")));
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
        if (state == State.JUMP || state == State.IDLE || state == State.DASH) return;
        if (lastMove == 1) angle = 180;
        else if (lastMove == 2) angle = 0;
        xVelocity = 5;
        yVelocity = -vSaut;
        state = State.JUMP;
    }

    /**
     * Move dash (high speed movement of the player)
     */
    public void dash(){
        if(state == State.JUMP || state == State.FALL) return;
        Timer timer = new Timer(300,this);
        xVelocity = vDash;
        yVelocity = 0;
        if(lastMove==1) animation.setImages(imageLeft);
        else animation.setImages(imageRight);
        state = State.DASH;
        Thread t = new Thread(timer);
        t.start();
    }

    /**
     * Draw the sword
     */
    public void sword(){
        if (isSword){
            model.swordPlayer.setActive(!model.swordPlayer.getActive());
            if (angle == 180){
                model.swordPlayer.orientationSword("left");
            } else if (angle == 0){
                model.swordPlayer.orientationSword("right");
            }
        }
    }

    /**
     * Actualisation du joueur
     */
    @Override
    public void update() {
        Rectangle shape1 = new Rectangle(width, height);

        // Bouge pas
        if (state == State.IDLE) {
            animation.getTimer().stop();
        } else { // moving or falling

            // Si il marche
            if (state == State.WALK || state == State.DASH) {
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
                model.chrono.terminate();
            }

            model.swordPlayer.update();
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
        if (state == State.WALK || state == State.DASH) {
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

    public void deployProtection(){
        if (state != State.JUMP){
            getProtection().toggleActive();
            if (getProtection().getActive()){
                model.obstacles.add(protection);
                protection.setOpacity(100);
                protection.setCenterX(getxPosition() + getWidth() / 2);
                protection.setCenterY(getyPosition() + getHeight() / 2);
                protection.setRadius(getHeight() / 2 + 10);
            } else {
                model.obstacles.remove(protection);
                protection.setOpacity(0);
            }
        }
    }

    public void setInvincivility(boolean invincibility){
        if (invincibility){
            for (Item i : model.items) {
                if (!i.getType().equals("item")){
                    Timer timer = new Timer(5000,i);
                    Thread t = new Thread(timer);
                    t.start();
                }
            }
        }
    }

    public void setLife(boolean life) {
        if (!life) {
            model.heart.setActive(false);
            getModel().shield.setFill(new ImagePattern(new Image("file:src/main/resources/com/fantastic_knight/assets/shield_empty.png")));
        } else {
            getModel().shield.setFill(new ImagePattern(new Image("file:src/main/resources/com/fantastic_knight/assets/shield.png")));
        }
        this.life = life;
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

    public void setState(State state){
        this.state = state;
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

    public boolean isInvincibility() {
        return invincibility;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public Protection getProtection() {
        return protection;
    }

    public void setSword(boolean isSword){
        this.isSword = isSword;
    }
}
