package com.fantastic_knight.levelMaker;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.*;
import java.util.Scanner;

import static com.fantastic_knight.Game.*;

public class LevelMaker extends Pane {

    Color color = Color.BLACK;

    Platform[][] platforms = new Platform[12][40];
    String fileNameExport = "default";
    String fileNameLoad = "default";

    ChoiceBox<String> choiceBoxLevels;

    public LevelMaker(){
        Pane paneLevelMaker = new Pane();
        paneLevelMaker.setPrefSize(1500,800);

        // PANE MAKER
        Pane paneMaker = new Pane();
        paneMaker.setPrefSize(1200,800);

        // REMPLISSAGE DE RECTANGLES BLANCS
        for(int i = 0; i < 12; i++){
            for (int j = 0; j < 40; j++){
                Platform platform = new Platform();
                platform.setFill(Color.WHITE); platform.setWidth(100); platform.setHeight(20);
                platform.setLayoutX(i * 100); platform.setLayoutY(j * 20);
                platforms[i][j] = platform;
                paneMaker.getChildren().add(platforms[i][j]);
            }
        }

        // EVENTS MOUSE
        paneMaker.setOnMouseClicked(e -> drawPlatform(e,platforms));
        paneMaker.setOnMouseDragged(e -> drawPlatform(e,platforms));


        // PANE CHOOSE
        Pane paneChoose = new Pane();
        paneChoose.setPrefSize(300,800); paneChoose.setLayoutX(1200);
        paneChoose.setStyle("-fx-background-color: red");

        // FILE NAME
        TextField textFieldFileName = new TextField(fileNameExport); textFieldFileName.setLayoutX(100); textFieldFileName.setLayoutY(20);
        textFieldFileName.setOnKeyReleased(e -> fileNameExport = textFieldFileName.getText());

        // EXPORT
        Button buttonExport = new Button("EXPORT");
        buttonExport.setLayoutX(100); buttonExport.setLayoutY(50);
        buttonExport.setOnAction(e -> export());

        // CHOICE LEVEL
        ObservableList<String> levels = FXCollections.observableArrayList(findAllLevels());
        choiceBoxLevels = new ChoiceBox<>(levels);
        choiceBoxLevels.setValue(choiceBoxLevels.getItems().get(0));
        choiceBoxLevels.setLayoutX(100); choiceBoxLevels.setLayoutY(120);
        choiceBoxLevels.setOnAction(e -> fileNameLoad = choiceBoxLevels.getValue());

        // LOAD
        Button buttonLoad = new Button("LOAD");
        buttonLoad.setLayoutX(100); buttonLoad.setLayoutY(150);
        buttonLoad.setOnAction(e -> load(paneMaker));

        // TOGGLE BUTTON
        ToggleButton toggleButton = new ToggleButton("Spikes");
        toggleButton.setLayoutX(150); toggleButton.setLayoutY(200);
        toggleButton.setOnAction(e -> spikesActive());

        // CLEAR
        Button buttonClear = new Button("CLEAR");
        buttonClear.setLayoutX(100); buttonClear.setLayoutY(250);
        buttonClear.setOnAction(e -> clear());

        // MENU
        Button buttonMenu = new Button("MENU");
        buttonMenu.setLayoutX(100); buttonMenu.setLayoutY(350);
        buttonMenu.setOnAction(e -> menu());

        // AJOUTS
        paneChoose.getChildren().addAll(buttonExport,buttonLoad,toggleButton,buttonClear,buttonMenu,textFieldFileName,choiceBoxLevels);
        paneLevelMaker.getChildren().addAll(paneMaker,paneChoose);
        getChildren().add(paneLevelMaker);
    }

    /**
     * Efface tous les rectangles
     */
    void clear() {
        for (Platform[] platform : platforms){
            for (Platform p : platform){
                p.setFill(Color.WHITE);
            }
        }
    }

    /**
     * Retourne au menu
     */
    void menu() {
        levels = findAllLevels();
        primaryStage.setWidth(1200);
        primaryStage.setScene(scene_menu);
    }

    /**
     * Active les piques
     */
    void spikesActive(){
        if (color.equals(Color.BLACK)){
            color = Color.RED;
        } else {
            color = Color.BLACK;
        }
    }

    /**
     * Dessine une plateforme
     * @param e : Mouse Event
     * @param platforms : Tableau des plateformes
     */
    void drawPlatform(MouseEvent e, Platform[][] platforms){
        int x = (int) (Math.round(e.getX() * 10)/1000);
        int y = (int) (Math.round(e.getY() * 10)/200);
        if ((x < 12 && x >= 0) && (y < 40 && y >= 0)){
            if (e.getButton() == MouseButton.PRIMARY){
                platforms[x][y].setxCoordonnee(x);
                platforms[x][y].setyCoordonnee(y);
                platforms[x][y].setFill(color);
            } else if (e.getButton() == MouseButton.SECONDARY){
                platforms[x][y].setxCoordonnee(x);
                platforms[x][y].setyCoordonnee(y);
                platforms[x][y].setFill(Color.WHITE);
            }
        }
    }

    /**
     * Exporter un niveau
     */
    void export(){
        try {

            PrintWriter writer = new PrintWriter("src/main/java/com/fantastic_knight/save/" + fileNameExport +".sav");
            for(int i = 0; i < 12; i++){
                for (int j = 0; j < 40; j++){
                    writer.print(platforms[i][j].toString() + " ");
                }
                writer.println();
            }
            writer.close();

            // UPDATE CHOICEBOX
            choiceBoxLevels.setItems(FXCollections.observableArrayList(findAllLevels()));
            choiceBoxLevels.setValue(choiceBoxLevels.getItems().get(0));

            System.out.println("Fichier " + fileNameExport + " sauvegardé.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Charger un niveau
     * @param paneMaker : Pane sur lequel afficher le niveau
     */
    void load(Pane paneMaker){
        clear();
        try {
            FileInputStream file = new FileInputStream("src/main/java/com/fantastic_knight/save/"+ fileNameLoad +".sav");
            Scanner scanner = new Scanner(file);

            final String ESPACE = " ";
            final String VIRGULE = ",";

            // Parcours du tableau
            for(int i = 0; i < 12; i++){
                String[] mots = scanner.nextLine().split(ESPACE);
                for (int j = 0; j < 40; j++){
                    String[] coordonnees = mots[j].split(VIRGULE);

                    // Lecture des valeurs
                    int x = Integer.parseInt(coordonnees[0]);
                    int y = Integer.parseInt(coordonnees[1]);
                    Color color = Color.valueOf(coordonnees[2]);

                    // Creation d'une plateforme
                    Platform platform = new Platform();
                    platform.setFill(color);

                    platform.setxCoordonnee(x);
                    platform.setyCoordonnee(y);
                    platform.setWidth(100);
                    platform.setHeight(20);
                    platform.setLayoutX(x * 100);
                    platform.setLayoutY(y * 20);
                    platforms[x][y] = platform;
                    paneMaker.getChildren().add(platform);
                }
            }

            scanner.close();

            System.out.println("Fichier " + fileNameLoad + " chargé");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
