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

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Game extends Application {
    public static View view;
    public static Model model;
    public static Controller controller;
    public static Stage primaryStage;
    public static Scene scene;
    public static Scene scene_menu;
    public static List<String> levels = findAllLevels();

    public static void main(String[] args) {
        launch(args);
    }

    /*
     * TODO
     *  Do some Levels
     *  Do Door to finish level
     *  Implement Door in LevelMaker
     *  Do scrolling
     *  Do graphics door
     *  Organize code
     */

    /**
     * Trouve tous les niveaux créés dans le dossier /save
     *
     * @return : La liste des niveaux créés
     */
    public static List<String> findAllLevels() {
        File folder = new File("src/main/java/com/fantastic_knight/save");
        List<String> levels = new ArrayList<>();
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            if (!file.isDirectory()) {
                // SÉPARER LE NOM DU FICHIER AVEC LE POINT
                final String POINT = "\\.";
                levels.add(file.getName().split(POINT)[0]);
            } else {
                findAllLevels();
            }
        }
        return levels;
    }

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println(levels);
        primaryStage = stage;

        model = new Model();
        view = new View(model);
        controller = new Controller(model, view);

        scene = new Scene(view.root, model.width, model.height);

        // FXML
        FXMLLoader fxmlLoaderMenu = new FXMLLoader(getClass().getResource("menu.fxml"));
        Parent root_menu = fxmlLoaderMenu.load();

        // SCENE PRINCIPALE
        scene_menu = new Scene(root_menu, 1200, 800);

        stage.setTitle("Fantastic Knight");
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene_menu);
        stage.show();
    }
}
