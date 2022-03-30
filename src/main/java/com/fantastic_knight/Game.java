package com.fantastic_knight;

import com.fantastic_knight.controller.Controller;
import com.fantastic_knight.model.Model;
import com.fantastic_knight.view.View;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Game extends Application {
    public static View view;
    public static Model model;
    public static Controller controller;
    public static Stage primaryStage;
    public static Scene scene_game;
    public static Scene scene_menu;
    public static Scene scene_credits;

    public static void main(String[] args) {
        launch(args);
    }

    /*------------------------------------------------
    TODO
     ***** BUGS *****
     o Bug : Halo (on se prend la flèche)
     o Bug : épée (elle sort desfois comme ça)
     o Bug : level 3 (message pas au niveau du perso)
     o Bug : Click sur le classement réduit tout
     *
     ***** A FAIRE *****
     o Ajouter le chrono pour pouvoir le voir
     o Faire un .jar du projet
     o Classement à réagencer
     x Options à réagencer
     x Enlever le bouton roue dentée inutile
     *
     ***** OPTIONNEL *****
     o Système de rang pour débloquer des niveaux et des skins
     o Améliorer l'IA de l'ennemi
     o Ajouter la fausse plateforme et la pierre
     o Ajouter l'animation de la mort de l'ennemi
     o Ajouter l'animation du slash de l'épée
    ---------------------------------------------------*/

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;

        model = new Model();
        view = new View(model);
        controller = new Controller(model, view);

        // FXML
        FXMLLoader fxmlLoaderMenu = new FXMLLoader(getClass().getResource("menu/menu.fxml"));
        Parent root_menu = fxmlLoaderMenu.load();

        FXMLLoader fxmlLoaderCredits = new FXMLLoader(getClass().getResource("menu/credits.fxml"));
        Parent root_credits = fxmlLoaderCredits.load();

        // SCENES PRINCIPALES
        scene_menu = new Scene(root_menu, model.width, model.height);
        scene_game = new Scene(view.scrollPane, model.width, model.height);
        scene_credits = new Scene(root_credits, model.width, model.height);

        stage.setTitle("Fantastic Knight");
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene_menu);
        stage.show();
    }
}
