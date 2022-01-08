package com.fantastic_knight.controller;

import com.fantastic_knight.model.Model;
import com.fantastic_knight.view.View;
import javafx.animation.AnimationTimer;

public class Controller {
    protected Model model;
    protected View view;
    protected ControllerKeyboard keyboard;

    protected AnimationTimer animator;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        keyboard = new ControllerKeyboard(model,view,this);

        // Boucle d'animation
        animator = new AnimationTimer(){

            @Override
            public void handle(long arg0) {
                if (model.lastFrame == -1) {
                    model.lastFrame = arg0;
                } else {
                    if ((arg0-model.lastFrame) < 10000000) return;
                    model.lastFrame = arg0;
                }
                model.update();
            }
        };
    }

    /**
     * Permet de commencer le jeu
     */
    public void startGame() {
        model.startGame();
        view.startGame();
        view.root.setFocusTraversable(true);
        view.root.requestFocus();
        animator.start();
    }

    public void movePersoLeft() {model.player.moveLeft();}
    public void movePersoRight() {model.player.moveRight();}
    public void jumpPerso() {model.player.jump();}
    public void stopPerso() {model.player.stop();}
    public void resetPerso() {model.player.reset();}
}
