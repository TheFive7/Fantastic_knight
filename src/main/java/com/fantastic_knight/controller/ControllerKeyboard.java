package com.fantastic_knight.controller;

import com.fantastic_knight.model.State;
import com.fantastic_knight.model.Model;
import com.fantastic_knight.view.View;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

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
            // Left
            if (event.getCode() == KeyCode.Q && model.player.getAngle() == 180 && model.player.getState() == State.WALK) {
                control.stopPerso();
                // Right
            } else if (event.getCode() == KeyCode.D && model.player.getAngle() == 0 && model.player.getState() == State.WALK) {
                control.stopPerso();
            }
        });
    }

    public void handle(KeyEvent arg0) {

        if (model.state == Model.STATE_INITIAL) return;

        // System.out.println("keycode = "+arg0.getCode());

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
        } else if (arg0.getCode() == KeyCode.ESCAPE) {
            control.returnMenu();
        }
    }
}
