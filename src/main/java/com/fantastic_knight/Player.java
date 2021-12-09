package com.fantastic_knight;

import com.fantastic_knight.model.Model;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

public class Player extends Sprite {
    Rectangle shape;
    double xPosition;
    double yPosition;
    double xVelocity; // pixel/s
    double yVelocity; // pixel/s
    double width;
    double height;
    boolean life;
    boolean win;
    boolean animated;
    double angle;
    String name;
    State state;
    Image image;
    double vSaut = 18;
    int lastMove;
    double lastX = xPosition;

    // 0 RIGHT; 180 LEFT

    public Player(Model model) {
        super(model);
        width = 20;
        height = 50;
        shape = new Rectangle(width,height,Color.BLACK);
        xPosition = 25;
        yPosition = 25;
        xVelocity = 5;
        yVelocity = 0;
        angle = 0;
        shape.setX(xPosition);
        shape.setY(yPosition);
        state = State.IDLE;
    }

    public void moveLeft() {
        if(state==State.JUMP || state==State.FALL) return;
        xVelocity = 5;
        yVelocity = 0;
        angle = 180;
        lastMove = 1;
        state = State.WALK;
        System.out.println("etat walkL");
    }

    public void moveRight() {
        if(state==State.JUMP || state==State.FALL) return;
        xVelocity = 5;
        yVelocity = 0;
        angle = 0;
        lastMove = 2;
        state = State.WALK;
        System.out.println("etat walkR");
    }

    public void stop() {
        xVelocity = 0;
        yVelocity = 0;
        angle = 0;
        state = State.IDLE;
    }

    public void reset() {
        xPosition = 25;
        yPosition = 25;
        xVelocity = 2;
        yVelocity = 0;
        angle = 0;
        shape.setX(xPosition);
        shape.setY(yPosition);
        state = State.IDLE;
    }

    private double getNewX() {
        double x = getxPosition();
        x += Math.cos(Math.toRadians(angle))* xVelocity;
        return x;
    }

    private double getNewY() {
        double y = getyPosition();
        y += Math.sin(Math.toRadians(angle))* yVelocity;
        return y;
    }

    public void jump() {
        if(state==State.JUMP || state==State.IDLE) return;
        if(lastMove==1) angle=180;
        else if(lastMove==2) angle=0;
        xVelocity=5;
        yVelocity = - vSaut;
        state = State.JUMP;
        System.out.println("etat jump");
    }

    @Override
    public void update() {

        double x=0;
        double y=0;

        Rectangle shape1 = new Rectangle(width,height,Color.BLACK);
        if (state == State.IDLE) {
            return;
        } else { // moving or falling

            testJump(shape1,y);
            testWalk(shape1);

            if (state == State.FALL || state == State.JUMP){
                if(yPosition>=model.height) state = State.IDLE;
                else yVelocity ++;
            }

            testCollision(shape1,x,y);
        }
    }

    public void testJump(Rectangle shape1,double y){
        if(state==State.JUMP){
            y = yPosition + yVelocity;
            shape1.setX(xPosition);
            shape1.setY(y);
            boolean isFloor = false;
            for(Shape s : model.obstacles) {
                Shape inter = Shape.intersect(shape1,s);
                Bounds b = inter.getBoundsInLocal();
                if (b.getWidth() != -1) {
                    isFloor = true;
                }
            }
            if(isFloor){
                state = State.FALL;
                System.out.println("etat fall");
                angle = 90;
            }
            if (!isFloor) {
                yPosition += yVelocity;
            }
        }
    }

    public void testWalk(Rectangle shape1){
        if (state == State.WALK) {
            // NO FLOOR DETECTION for actual position: just move perso one pixel down
            shape1.setX(xPosition);
            shape1.setY(yPosition+1);
            boolean isFloor = false;
            for(Shape s : model.obstacles) {
                Shape inter = Shape.intersect(shape1,s);
                Bounds b = inter.getBoundsInLocal();
                if (b.getWidth() != -1) {
                    isFloor = true;
                }
            }

            // No floor => change angle & state to falling
            if (!isFloor) {
                state = State.FALL;
                System.out.println("etat fall");
                angle = 90;
            }
        }
    }

    public void testCollision(Rectangle shape1, double x, double y){
        x = getNewX();
        y = getNewY();

        // COLLISION DETECTION
        shape1.setX(x);
        shape1.setY(y);

        // Which obstacle in collision with Player
        List<Shape> lst = new ArrayList<>();
        for(Shape s : model.obstacles) {
            Shape inter = Shape.intersect(shape1,s);
            Bounds b = inter.getBoundsInLocal();
            if (b.getWidth() != -1) {
                lst.add(s);
            }
        }

        // list not empty => collision
        // search for the minimal move to get a collision
        if (!lst.isEmpty()) {
            for(int i=1;i<=xVelocity;i++) {
                xVelocity = i;
                yVelocity = i;
                x = getNewX();
                y = getNewY();
                shape1.setX(x);
                shape1.setY(y);
                boolean collide = false;
                for(Shape s : lst) {
                    Shape inter = Shape.intersect(shape1,s);
                    Bounds b = inter.getBoundsInParent();
                    if (b.getWidth() != -1) {
                        collide = true;
                    }
                }

                if (collide) {
                    state = State.IDLE; // stop moving
                    System.out.println("etat idle");
                    xVelocity = i-1;
                    yVelocity = i-1;
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
}