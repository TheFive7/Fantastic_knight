package com.fantastic_knight.controller;

import com.fantastic_knight.model.Model;
import com.fantastic_knight.view.View;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ControllerAction implements EventHandler<ActionEvent> {

    protected Model model;
    protected View view;
    protected Controller control;

    public ControllerAction(Model model, View view, Controller control) {
        this.model = model;
        this.view = view;
        this.control = control;

        view.buttonLevel1.setOnAction(this);
        view.buttonLevel2.setOnAction(this);
    }

    public void handle(ActionEvent event) {
        // Boutons
        if (event.getSource() == view.buttonLevel1) {
            if (model.state == Model.STATE_INITIAL) {
                model.level = 1;
                System.out.println("Level " + model.level);
                control.startGame();
            }
        } else if (event.getSource() == view.buttonLevel2) {
            if (model.state == Model.STATE_INITIAL) {
                model.level = 2;
                System.out.println("Level "+ model.level);
                control.startGame();
            }
        }
    }    
}
