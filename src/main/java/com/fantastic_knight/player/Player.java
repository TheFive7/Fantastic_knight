package com.fantastic_knight.player;

import com.fantastic_knight.animation.AnimationImage;
import com.fantastic_knight.items.Item;
import com.fantastic_knight.items.SwordPlayer;
import com.fantastic_knight.model.*;
import com.fantastic_knight.objects.Protection;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.fantastic_knight.controller.MenuController.isMultiplayerOn;
import static com.fantastic_knight.model.Model.factor;

public class Player extends Sprite {
    final Rectangle shape;
    final Rectangle shapeNextPos;
    final double width;
    final double height;
    final boolean animated;
    String name;
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
    private boolean canDash = true;
    Protection protection;
    boolean isSword;
    boolean isAttack;

    // ATH
    Shield shield;
    Heart heart;
    Sword sword;
    SwordPlayer swordPlayer;
    Key key;

    // 0 RIGHT; 180 LEFT

    public Player(Model model, String name) {
        super(model);
        this.model = model;
        this.name = name;

        imageRight = new Image[3];
        imageLeft = new Image[3];
        chooseSkin();

        width = imageRight[0].getWidth();
        height = imageRight[0].getHeight();

        shape = new Rectangle(width, height);
        shapeNextPos = new Rectangle(width, height);
        shapeNextPos.setOpacity(0);
        invincibility = false;
        xPosition = 0;
        yPosition = model.height * factor - height;
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
        isAttack = false;
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

        if (this.equals(model.player1)) model.swordPlayer.orientationSword("left");
        if (this.equals(model.player2)) model.swordPlayer2.orientationSword("left");

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

        if (this.equals(model.player1)) model.swordPlayer.orientationSword("right");
        if (this.equals(model.player2)) model.swordPlayer2.orientationSword("right");
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
        yPosition = model.height * factor - height;
        xVelocity = 5;
        yVelocity = 0;
        angle = 0;
        life = true;
        shape.setX(xPosition);
        shape.setY(yPosition);
        state = State.IDLE;
        isSword = false;
        shield.setFill(new ImagePattern(new Image("file:src/main/resources/com/fantastic_knight/assets/shield.png")));
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
        if(state == State.JUMP || state == State.FALL || !canDash) return;
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
            swordPlayer.setActive(!swordPlayer.getActive());
            if (angle == 180){
                swordPlayer.orientationSword("left");
            } else if (angle == 0){
                swordPlayer.orientationSword("right");
            }
        }
    }

    /**
     * Actualisation du joueur
     */
    @Override
    public void update() {

        // Bouge pas
        if (state == State.IDLE) {
            animation.getTimer().stop();
        } else { // moving or falling

            // Si il marche
            if (state == State.WALK || state == State.DASH) {
                animation.getTimer().start();
            }

            testJump();
            testWalk();

            // tombe ou saute
            if (state == State.FALL || state == State.JUMP) {
                if (yPosition >= model.height * factor) state = State.IDLE;
                else yVelocity++;
            }

            testCollision();

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
     */
    public void testJump() {
        if (state == State.JUMP) {
            double y = yPosition + yVelocity;
            shapeNextPos.setX(xPosition);
            shapeNextPos.setY(y);

            // Détection de collision
            boolean isFloor = false;
            for (Shape s : model.obstacles) {
                Shape inter = Shape.intersect(shapeNextPos, s);
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
     *
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
            heart.setActive(false);
            shield.setFill(new ImagePattern(new Image("file:src/main/resources/com/fantastic_knight/assets/shield_empty.png")));

            // Sounds.mediaPlayerHurt = new MediaPlayer(Sounds.hurtSound);
            Sounds.mediaPlayerHurt.stop();
            Sounds.mediaPlayerHurt.play();
        } else {
            shield.setFill(new ImagePattern(new Image("file:src/main/resources/com/fantastic_knight/assets/shield.png")));
        }
        this.life = life;
    }

    public void setHeart(Heart heart){
        this.heart = heart;
    }

    public void setShield(Shield shield){
        this.shield = shield;
    }

    public void setSword(Sword sword){
        this.sword = sword;
    }

    public Sword getSword(){
        return this.sword;
    }

    public void setSwordPlayer(SwordPlayer swordPlayer){
        this.swordPlayer = swordPlayer;
    }

    public Key getKey(){
        return this.key;
    }

    public void setKey(Key key){
        this.key = key;
    }

    public Rectangle getShape() {
        return shapeNextPos;
    }

    public Rectangle getFirstShape() {
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

    public void setCanDash(boolean canDash) {
        this.canDash = canDash;
    }

    public void setSword(boolean isSword){
        this.isSword = isSword;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void chooseSkin() {
        switch (name) {
            case "Red Knight" -> {
                imageRight[0] = new Image("file:src/main/resources/com/fantastic_knight/player/Knight_stand_right.png");
                imageRight[1] = new Image("file:src/main/resources/com/fantastic_knight/player/Knight_walk_right.png");
                imageRight[2] = new Image("file:src/main/resources/com/fantastic_knight/player/Knight_walk1_right.png");
                imageLeft[0] = new Image("file:src/main/resources/com/fantastic_knight/player/Knight_stand_left.png");
                imageLeft[1] = new Image("file:src/main/resources/com/fantastic_knight/player/Knight_walk_left.png");
                imageLeft[2] = new Image("file:src/main/resources/com/fantastic_knight/player/Knight_walk1_left.png");
            }
            case "Grey Knight" -> {
                imageRight[0] = new Image("file:src/main/resources/com/fantastic_knight/player/perso_droite_idle.png");
                imageRight[1] = new Image("file:src/main/resources/com/fantastic_knight/player/perso_droite_walk.png");
                imageRight[2] = new Image("file:src/main/resources/com/fantastic_knight/player/perso_droite_idle.png");
                imageLeft[0] = new Image("file:src/main/resources/com/fantastic_knight/player/perso_gauche_idle.png");
                imageLeft[1] = new Image("file:src/main/resources/com/fantastic_knight/player/perso_gauche_walk.png");
                imageLeft[2] = new Image("file:src/main/resources/com/fantastic_knight/player/perso_gauche_idle.png");
            }
            case "Platine Knight" -> {
                imageRight[0] = new Image("file:src/main/resources/com/fantastic_knight/player/Knight_stand_right_platine.png");
                imageRight[1] = new Image("file:src/main/resources/com/fantastic_knight/player/Knight_walk_right_platine.png");
                imageRight[2] = new Image("file:src/main/resources/com/fantastic_knight/player/Knight_walk1_right_platine.png");
                imageLeft[0] = new Image("file:src/main/resources/com/fantastic_knight/player/Knight_stand_left_platine.png");
                imageLeft[1] = new Image("file:src/main/resources/com/fantastic_knight/player/Knight_walk_left_platine.png");
                imageLeft[2] = new Image("file:src/main/resources/com/fantastic_knight/player/Knight_walk1_left_platine.png");
            }
            case "Gold Knight" -> {
                imageRight[0] = new Image("file:src/main/resources/com/fantastic_knight/player/Knight_stand_right_gold.png");
                imageRight[1] = new Image("file:src/main/resources/com/fantastic_knight/player/Knight_walk_right_gold.png");
                imageRight[2] = new Image("file:src/main/resources/com/fantastic_knight/player/Knight_walk1_right_gold.png");
                imageLeft[0] = new Image("file:src/main/resources/com/fantastic_knight/player/Knight_stand_left_gold.png");
                imageLeft[1] = new Image("file:src/main/resources/com/fantastic_knight/player/Knight_walk_left_gold.png");
                imageLeft[2] = new Image("file:src/main/resources/com/fantastic_knight/player/Knight_walk1_left_gold.png");
            }
        }
    }
}
