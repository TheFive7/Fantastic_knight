package com.fantastic_knight.levelMaker;

import com.fantastic_knight.Game;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import static com.fantastic_knight.Game.*;

public class LevelMaker extends Pane {

    final Platform[][] platforms = new Platform[12][40];
    final ChoiceBox<String> choiceBoxLevels;
    boolean isSpike = false;
    Image currentImg = new Image("file:src/main/resources/com/fantastic_knight/platform.png");
    String fileNameExport = "default";
    String fileNameLoad = "default";

    public LevelMaker() {
        Pane paneLevelMaker = new Pane();
        paneLevelMaker.setPrefSize(1500, 800);

        // PANE MAKER
        Pane paneMaker = new Pane();
        paneMaker.setPrefSize(1200, 800);

        // Background
        paneMaker.setStyle("-fx-background-image: url('" + Game.class.getResource("bg.png") + "')");

        // REMPLISSAGE DE RECTANGLES
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 40; j++) {
                Platform platform = new Platform();
                platform.setWidth(100);
                platform.setHeight(20);
                platform.setLayoutX(i * 100);
                platform.setLayoutY(j * 20);
                platforms[i][j] = platform;
                paneMaker.getChildren().add(platforms[i][j]);
            }
        }
        clear();

        // EVENTS MOUSE
        paneMaker.setOnMouseClicked(e -> drawPlatform(e, platforms));
        paneMaker.setOnMouseDragged(e -> drawPlatform(e, platforms));


        // PANE CHOOSE
        Pane paneChoose = new Pane();
        paneChoose.setPrefSize(300, 800);
        paneChoose.setLayoutX(1200);
        paneChoose.setStyle("-fx-background-image: url('" + Game.class.getResource("levels.png") + "')");

        // FILE NAME
        Label fileName = new Label("File name :");
        fileName.setLayoutX(10);
        fileName.setLayoutY(25);
        fileName.setFont(new Font(20));
        TextField textFieldFileName = new TextField(fileNameExport);
        textFieldFileName.setLayoutX(130);
        textFieldFileName.setLayoutY(15);
        textFieldFileName.setPrefSize(120, 55);
        textFieldFileName.setStyle("-fx-background-image: url('" + Game.class.getResource("paper.png") + "');-fx-background-color: transparent");
        textFieldFileName.setOnKeyReleased(e -> fileNameExport = textFieldFileName.getText());

        // EXPORT
        Label exportFile = new Label("Save the file :");
        exportFile.setLayoutX(10);
        exportFile.setLayoutY(75);
        exportFile.setFont(new Font(20));
        Button buttonExport = new Button("EXPORT");
        buttonExport.setLayoutX(155);
        buttonExport.setLayoutY(77.5);
        buttonExport.setOnAction(e -> export());

        // CHOICE LEVEL
        ObservableList<String> levels = FXCollections.observableArrayList(findAllLevels());
        choiceBoxLevels = new ChoiceBox<>(levels);
        choiceBoxLevels.setValue(choiceBoxLevels.getItems().get(0));
        choiceBoxLevels.setLayoutX(50);
        choiceBoxLevels.setLayoutY(150);
        choiceBoxLevels.setOnAction(e -> fileNameLoad = choiceBoxLevels.getValue());

        // LOAD
        Button buttonLoad = new Button("LOAD");
        buttonLoad.setLayoutX(200);
        buttonLoad.setLayoutY(150);
        buttonLoad.setOnAction(e -> load(paneMaker));

        // TOGGLE BUTTON
        Label spikesImage = new Label();
        spikesImage.setStyle("-fx-background-image: url('" + Game.class.getResource("spike.png") + "');-fx-background-color: transparent");
        spikesImage.setLayoutX(100);
        spikesImage.setLayoutY(300);
        ToggleButton toggleButton = new ToggleButton("Spikes");
        toggleButton.setLayoutX(150);
        toggleButton.setLayoutY(300);
        toggleButton.setOnAction(e -> spikesActive());

        // CLEAR
        Button buttonClear = new Button();
        buttonClear.setPrefSize(150, 160);
        buttonClear.setStyle("-fx-background-image: url('" + Game.class.getResource("clear.png") + "');-fx-background-color: transparent; -fx-background-repeat: no-repeat");
        buttonClear.setLayoutX(105);
        buttonClear.setLayoutY(550);
        buttonClear.setOnAction(e -> clear());

        // MENU
        Button buttonMenu = new Button();
        buttonMenu.setPrefSize(75, 80);
        buttonMenu.setStyle("-fx-background-image: url('" + Game.class.getResource("back.png") + "');-fx-background-color: transparent; -fx-background-repeat: no-repeat");
        buttonMenu.setLayoutX(235);
        buttonMenu.setLayoutY(725);
        buttonMenu.setOnAction(e -> menu());

        // AJOUTS
        paneChoose.getChildren().addAll(fileName, exportFile, spikesImage, buttonExport, buttonLoad, toggleButton, buttonClear, buttonMenu, textFieldFileName, choiceBoxLevels);
        paneLevelMaker.getChildren().addAll(paneMaker, paneChoose);
        getChildren().add(paneLevelMaker);
    }

    /**
     *  Erease all rectangles
     */
    void clear() {
        for (Platform[] platform : platforms) {
            for (Platform p : platform) {
                p.setType("void");
                p.setOpacity(0);
                p.setxCoordonnee(0);
                p.setyCoordonnee(0);
            }
        }
    }

    /**
     * back to the menu
     */
    void menu() {
        levels = findAllLevels();
        primaryStage.setWidth(1200);
        primaryStage.setScene(scene_menu);
    }

    /**
     * Activate the spike
     */
    void spikesActive() {
        if (!isSpike) {
            isSpike = true;
            currentImg = new Image("file:src/main/resources/com/fantastic_knight/items/spike.png");
        } else {
            isSpike = false;
            currentImg = new Image("file:src/main/resources/com/fantastic_knight/platform.png");
        }
    }

    /**
     * Draw platform
     *
     * @param e         : Mouse Event
     * @param platforms : array of plateforms
     */
    void drawPlatform(MouseEvent e, Platform[][] platforms) {
        int x = (int) (Math.round(e.getX() * 10) / 1000);
        int y = (int) (Math.round(e.getY() * 10) / 200);
        if ((x < 12 && x >= 0) && (y < 40 && y >= 0)) {
            if (e.getButton() == MouseButton.PRIMARY) {
                platforms[x][y].setxCoordonnee(x);
                platforms[x][y].setyCoordonnee(y);
                platforms[x][y].setOpacity(100);

                if (isSpike) {
                    platforms[x][y].setType("spike");
                } else {
                    platforms[x][y].setType("platform");
                }

                platforms[x][y].setFill(new ImagePattern(currentImg));
            } else if (e.getButton() == MouseButton.SECONDARY) {
                platforms[x][y].setxCoordonnee(0);
                platforms[x][y].setyCoordonnee(0);
                platforms[x][y].setOpacity(0);
                platforms[x][y].setType("void");
            }
        }
    }

    /**
     * Exporter level
     */
    void export() {
        try {

            PrintWriter writer = new PrintWriter("src/main/java/com/fantastic_knight/save/" + fileNameExport + ".sav");
            for (int i = 0; i < 12; i++) {
                for (int j = 0; j < 40; j++) {
                    writer.print(platforms[i][j].toString() + " ");
                }
                writer.println();
            }
            writer.close();

            // UPDATE CHOICEBOX
            choiceBoxLevels.setItems(FXCollections.observableArrayList(findAllLevels()));
            choiceBoxLevels.setValue(choiceBoxLevels.getItems().get(0));

            //System.out.println("Fichier " + fileNameExport + " sauvegardé.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * load level
     *
     * @param paneMaker : Pane on which to display the level
     */
    void load(Pane paneMaker) {
        clear();
        try {
            FileInputStream file = new FileInputStream("src/main/java/com/fantastic_knight/save/" + fileNameLoad + ".sav");
            Scanner scanner = new Scanner(file);

            final String ESPACE = " ";
            final String VIRGULE = ",";

            // Parcours du tableau
            for (int i = 0; i < 12; i++) {
                String[] mots = scanner.nextLine().split(ESPACE);
                for (int j = 0; j < 40; j++) {
                    String[] coordonnees = mots[j].split(VIRGULE);

                    // Lecture des valeurs
                    int x = Integer.parseInt(coordonnees[0]);
                    int y = Integer.parseInt(coordonnees[1]);
                    String type = coordonnees[2];

                    // Creation d'une plateforme
                    Platform platform = new Platform();
                    if (type.equals("platform")) {
                        platform.setFill(new ImagePattern(new Image("file:src/main/resources/com/fantastic_knight/platform.png")));
                        platform.setOpacity(100);
                    } else if (type.equals("spike")) {
                        platform.setFill(new ImagePattern(new Image("file:src/main/resources/com/fantastic_knight/items/spike.png")));
                        platform.setOpacity(100);
                    } else {
                        platform.setOpacity(0);
                    }

                    platform.setType(type);
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

            //System.out.println("Fichier " + fileNameLoad + " chargé");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
