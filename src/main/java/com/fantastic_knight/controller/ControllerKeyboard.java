package com.fantastic_knight.controller;

import com.fantastic_knight.model.State;
import com.fantastic_knight.model.Model;
import com.fantastic_knight.view.View;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import static com.fantastic_knight.controller.MenuController.isMultiplayerOn;

public class ControllerKeyboard implements EventHandler<KeyEvent> {

    protected final Model model;
    protected final View view;
    protected final Controller control;

    public ControllerKeyboard(Model model, View view, Controller control) {
        this.model = model;
        this.view = view;
        this.control = control;

        view.root.setFocusTraversable(true);
        view.root.requestFocus();
        view.root.setOnKeyPressed(this);

        view.scrollPane.setOnMouseClicked(e -> view.root.requestFocus());

        // Keys
        view.root.setOnKeyReleased(event -> {
            if (model.state == Model.STATE_INITIAL) return;

            // PLAYER 1
            // Left
            if (event.getCode() == KeyCode.Q && model.player1.getAngle() == 180 && model.player1.getState() == State.WALK) {
                control.stopPerso();
                // Right
            } else if (event.getCode() == KeyCode.D && model.player1.getAngle() == 0 && model.player1.getState() == State.WALK) {
                control.stopPerso();
            }

            // PLAYER 2
            if (isMultiplayerOn) {
                // Left
                if (event.getCode() == KeyCode.LEFT && model.player2.getAngle() == 180 && model.player2.getState() == State.WALK) {
                    control.stopPerso_2();
                    // Right
                } else if (event.getCode() == KeyCode.RIGHT && model.player2.getAngle() == 0 && model.player2.getState() == State.WALK) {
                    control.stopPerso_2();
                }
            }
        });
    }

    public void handle(KeyEvent arg0) {

        if (model.state == Model.STATE_INITIAL) return;

        // System.out.println("keycode = "+arg0.getCode());

        // PLAYER 1
        if (arg0.getCode() == KeyCode.Q) {
            control.movePersoLeft();
        } else if (arg0.getCode() == KeyCode.D) {
            control.movePersoRight();
        } else if (arg0.getCode() == KeyCode.R) {
            control.resetPerso();
        } else if (arg0.getCode() == KeyCode.M) {
            view.returnMenu();
        } else if (arg0.getCode() == KeyCode.SPACE) {
            control.jumpPerso();
        } else if (arg0.getCode() == KeyCode.E) {
            control.dash();
        } else if (arg0.getCode() == KeyCode.F) {
            control.deployProtection();
        } else if (arg0.getCode() == KeyCode.ALT) {
            control.sword();
        }

        // PLAYER 2
        if (isMultiplayerOn){
            if (arg0.getCode() == KeyCode.LEFT) {
                control.movePersoLeft_2();
            } else if (arg0.getCode() == KeyCode.RIGHT) {
                control.movePersoRight_2();
            } else if (arg0.getCode() == KeyCode.UP) {
                control.jumpPerso_2();
            } else if (arg0.getCode() == KeyCode.DOWN) {
                control.dash_2();
            } else if (arg0.getCode() == KeyCode.CONTROL) {
                control.deployProtection_2();
            } else if (arg0.getCode() == KeyCode.SHIFT) {
                control.sword_2();
            }
        }
    }
}
