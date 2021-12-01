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
    public static Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;

        model = new Model();
        view = new View(model);
        Controller controller = new Controller(model, view);

        // FXML
        FXMLLoader fxmlLoaderMenu = new FXMLLoader(getClass().getResource("menu.fxml"));
        Parent root_menu = fxmlLoaderMenu.load();

        // SCENE PRINCIPALE
        Scene scene_menu = new Scene(root_menu, 1200, 800);

        stage.setTitle("Platformer");
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene_menu);
        stage.show();
    }
}
