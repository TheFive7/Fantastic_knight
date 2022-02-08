package com.fantastic_knight.controller;

import com.fantastic_knight.model.Model;
import com.fantastic_knight.view.View;
import javafx.animation.AnimationTimer;

public class Controller {
    protected final Model model;
    protected final View view;
    protected final ControllerKeyboard keyboard;
    protected final AnimationTimer animator;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        keyboard = new ControllerKeyboard(model, view, this);

        // animation loop
        animator = new AnimationTimer() {

            @Override
            public void handle(long arg0) {
                if (model.lastFrame != -1) {
                    if ((arg0 - model.lastFrame) < 10000000) return;
                }
                model.lastFrame = arg0;
                model.update();
            }
        };
    }

    /**
     * Start the game
     */
    public void startGame() {
        model.startGame();
        view.startGame();
        view.root.setFocusTraversable(true);
        view.root.requestFocus();
        animator.start();
    }

    public void movePersoLeft() {
        model.player.moveLeft();
    }

    public void movePersoRight() {
        model.player.moveRight();
    }

    public void jumpPerso() {
        model.player.jump();
    }

    public void stopPerso() {
        model.player.stop();
    }

    public void resetPerso() {
        model.player.reset();
    }

    public void dash() {
        model.player.dash();
    }

    public void deployProtection() {model.player.deployProtection();}

    public void sword() {model.player.sword();}
}
