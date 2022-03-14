package com.fantastic_knight.controller;

import com.fantastic_knight.model.Model;
import com.fantastic_knight.view.View;
import javafx.animation.AnimationTimer;

import static com.fantastic_knight.controller.MenuController.isMultiplayerOn;
import static com.fantastic_knight.model.Model.factor;

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
                view.update();
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
        model.player1.moveLeft();
    }

    public void movePersoRight() {
        model.player1.moveRight();
    }

    public void jumpPerso() {
        model.player1.jump();
    }

    public void stopPerso() {
        model.player1.stop();
    }

    public void dash() {
        model.player1.dash();
    }

    public void deployProtection() {model.player1.deployProtection();}

    public void sword() {model.player1.sword();}

    public void resetPerso() {
        model.player1.reset();
        if (isMultiplayerOn) model.player2.reset();
    }

    // PLAYER 2
    public void movePersoLeft_2() {
        model.player2.moveLeft();
    }

    public void movePersoRight_2() {
        model.player2.moveRight();
    }

    public void jumpPerso_2() {
        model.player2.jump();
    }

    public void stopPerso_2() {
        model.player2.stop();
    }

    public void dash_2() {
        model.player2.dash();
    }

    public void deployProtection_2() {model.player2.deployProtection();}

    public void sword_2() {model.player2.sword();}
}
