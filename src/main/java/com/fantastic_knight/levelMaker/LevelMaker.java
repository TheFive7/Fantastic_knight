package com.fantastic_knight.levelMaker;

import com.fantastic_knight.Game;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;

import java.io.*;
import java.util.Scanner;

import static com.fantastic_knight.Game.*;
import static com.fantastic_knight.model.Model.findAllLevels;

public class LevelMaker extends Pane {

    boolean isSpike = false;
    boolean isDoor = false;
    boolean isSlime = false;
    boolean isArrowTrap = false;
    Image currentImg = new Image("file:src/main/resources/com/fantastic_knight/assets/platform.png");

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

        // Background
        paneMaker.setStyle("-fx-background-image: url('"+ Game.class.getResource("assets/bg.png")+"')");

        // REMPLISSAGE DE RECTANGLES
        for(int i = 0; i < 12; i++){
            for (int j = 0; j < 40; j++){
                Platform platform = new Platform();
                platform.setWidth(100); platform.setHeight(20);
                platform.setLayoutX(i * 100); platform.setLayoutY(j * 20);
                platforms[i][j] = platform;
                paneMaker.getChildren().add(platforms[i][j]);
            }
        }
        clear();

        // EVENTS MOUSE
        paneMaker.setOnMouseClicked(e -> drawPlatform(e,platforms));
        paneMaker.setOnMouseDragged(e -> drawPlatform(e,platforms));

        // PANE CHOOSE
        Pane paneChoose = new Pane();
        paneChoose.setPrefSize(300,800); paneChoose.setLayoutX(1200);
        paneChoose.setStyle("-fx-background-image: url('"+ Game.class.getResource("menu/levels.png")+"')");

        // FILE NAME
        Label fileName = new Label("File name :"); fileName.setLayoutX(10); fileName.setLayoutY(25); fileName.setFont(new Font(20));
        fileName.setStyle("-fx-font-weight: bold");
        TextField textFieldFileName = new TextField(fileNameExport); textFieldFileName.setLayoutX(150); textFieldFileName.setLayoutY(15); textFieldFileName.setPrefSize(120,55);
        textFieldFileName.setStyle("-fx-background-image: url('"+ Game.class.getResource("icons/paper.png")+"');-fx-background-color: transparent");
        textFieldFileName.setOnKeyReleased(e -> fileNameExport = textFieldFileName.getText());

        // EXPORT
        Label exportFile = new Label("Save the file :"); exportFile.setLayoutX(10); exportFile.setLayoutY(75); exportFile.setFont(new Font(20));
        exportFile.setStyle("-fx-font-weight: bold");
        Button buttonExport = new Button("EXPORT");
        buttonExport.setLayoutX(185); buttonExport.setLayoutY(77.5);
        buttonExport.setOnAction(e -> export());

        // CHOICE LEVEL
        ObservableList<String> levels = FXCollections.observableArrayList(findAllLevels());
        choiceBoxLevels = new ChoiceBox<>(levels);
        choiceBoxLevels.setValue(choiceBoxLevels.getItems().get(0));
        choiceBoxLevels.setLayoutX(50); choiceBoxLevels.setLayoutY(150);
        choiceBoxLevels.setOnAction(e -> {
            fileNameLoad = choiceBoxLevels.getValue();
            textFieldFileName.setText(fileNameLoad);
        });

        // LOAD
        Button buttonLoad = new Button("LOAD");
        buttonLoad.setLayoutX(200); buttonLoad.setLayoutY(150);
        buttonLoad.setOnAction(e -> load(paneMaker));

        // TOGGLE BUTTON
        Label spikesImage = new Label(""); spikesImage.setStyle("-fx-background-color: transparent;-fx-background-image: url('"+ Game.class.getResource("items/spike.png")+"')");
        spikesImage.setPrefSize(100,25);
        spikesImage.setLayoutX(50); spikesImage.setLayoutY(305);
        ToggleButton toggleButton = new ToggleButton();
        toggleButton.setLayoutX(175); toggleButton.setLayoutY(305); toggleButton.setPrefSize(100,45);
        toggleButton.setStyle("-fx-background-image: url('"+ Game.class.getResource("icons/off.png")+"');-fx-background-color: transparent;-fx-background-repeat: no-repeat");
        toggleButton.setOnAction(e -> spikesActive(toggleButton));

        //SLIME
        CheckBox slimeCheck = new CheckBox("SLIME");
        slimeCheck.setLayoutX(50); slimeCheck.setLayoutY(500);
        slimeCheck.setOnAction(e -> slimeActive());

        // DOOR
        CheckBox doorCheck = new CheckBox("DOOR");
        doorCheck.setLayoutX(50); doorCheck.setLayoutY(350);
        doorCheck.setOnAction(e -> doorActive());

        // ARROW TRAP
        CheckBox arrowTrapCheck = new CheckBox("ARROW TRAP");
        arrowTrapCheck.setLayoutX(50); arrowTrapCheck.setLayoutY(400);
        arrowTrapCheck.setOnAction(e -> arrowTrapActive());

        // CLEAR
        Button buttonClear = new Button();
        buttonClear.setPrefSize(150,160);
        buttonClear.setStyle("-fx-background-image: url('"+ Game.class.getResource("icons/clear.png")+"');-fx-background-color: transparent; -fx-background-repeat: no-repeat");
        buttonClear.setLayoutX(105); buttonClear.setLayoutY(550);
        buttonClear.setOnAction(e -> clear());

        // MENU
        Button buttonMenu = new Button();
        buttonMenu.setPrefSize(75,80);
        buttonMenu.setStyle("-fx-background-image: url('"+ Game.class.getResource("icons/back.png")+"');-fx-background-color: transparent; -fx-background-repeat: no-repeat");
        buttonMenu.setLayoutX(235); buttonMenu.setLayoutY(725);
        buttonMenu.setOnAction(e -> menu());

        // AJOUTS
        paneChoose.getChildren().addAll(fileName,exportFile,spikesImage,buttonExport,buttonLoad,toggleButton,
                slimeCheck,doorCheck,arrowTrapCheck,buttonClear,buttonMenu,textFieldFileName,choiceBoxLevels);
        paneLevelMaker.getChildren().addAll(paneMaker,paneChoose);
        getChildren().add(paneLevelMaker);
    }

    /**
     * Efface tous les rectangles
     */
    void clear() {
        for (Platform[] platform : platforms){
            for (Platform p : platform){
                p.setType("void");
                p.setOpacity(0);
                p.setxCoordonnee(0);
                p.setyCoordonnee(0);
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
    void spikesActive(ToggleButton button){
        if (!isSpike){
            isSpike = true;
            currentImg = new Image("file:src/main/resources/com/fantastic_knight/items/spike.png");
            button.setStyle("-fx-background-image: url('"+ Game.class.getResource("on.png")+"');-fx-background-color: transparent;-fx-background-repeat: no-repeat");
        } else {
            isSpike = false;
            currentImg = new Image("file:src/main/resources/com/fantastic_knight/assets/platform.png");
            button.setStyle("-fx-background-image: url('"+ Game.class.getResource("off.png")+"');-fx-background-color: transparent;-fx-background-repeat: no-repeat");
        }
    }

    private void slimeActive() {
        if (!isSlime){
            isSlime = true;
            currentImg = new Image("file:src/main/resources/com/fantastic_knight/items/slime.png");
        } else {
            isSlime = false;
            currentImg = new Image("file:src/main/resources/com/fantastic_knight/assets/platform.png");
        }
    }

    public void doorActive(){
        if (!isDoor){
            isDoor = true;
            currentImg = new Image("file:src/main/resources/com/fantastic_knight/assets/door.png");
        } else {
            isDoor = false;
            currentImg = new Image("file:src/main/resources/com/fantastic_knight/assets/platform.png");
        }
    }

    public void arrowTrapActive(){
        if (!isArrowTrap){
            isArrowTrap = true;
            currentImg = new Image("file:src/main/resources/com/fantastic_knight/items/arrowTrap_left.png");
        } else {
            isArrowTrap = false;
            currentImg = new Image("file:src/main/resources/com/fantastic_knight/assets/platform.png");
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
                platforms[x][y].setOpacity(100);
                platforms[x][y].setWidth(100);
                platforms[x][y].setHeight(20);
                platforms[x][y].setLayoutX(x * 100);
                platforms[x][y].setLayoutY(y * 20);

                // Spikes & Platform
                if(isSpike)platforms[x][y].setType("spike");
                else if (isSlime)platforms[x][y].setType("slime");
                else {platforms[x][y].setType("platform");}

                // Door
                if (isDoor){
                    platforms[x][y].setType("door");
                    platforms[x][y].setWidth(70);
                    platforms[x][y].setHeight(100);
                    platforms[x][y].setLayoutX(x * 100 + 15);
                }

                // ArrowTrap
                if (isArrowTrap){
                    platforms[x][y].setType("arrowTrap");
                    platforms[x][y].setWidth(50);
                    platforms[x][y].setHeight(50);
                    platforms[x][y].setLayoutX(x * 100 + 25);
                    platforms[x][y].setLayoutY(y * 20 - 10);
                }

                platforms[x][y].setFill(new ImagePattern(currentImg));
            } else if (e.getButton() == MouseButton.SECONDARY){
                platforms[x][y].setxCoordonnee(0);
                platforms[x][y].setyCoordonnee(0);
                platforms[x][y].setOpacity(0);
                platforms[x][y].setType("void");
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
                    System.out.println(platforms[i][j].toString());
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
                    String type = coordonnees[2];

                    // Creation d'une plateforme
                    Platform platform = new Platform();
                    platform.setWidth(100);
                    platform.setHeight(20);
                    platform.setxCoordonnee(x);
                    platform.setyCoordonnee(y);
                    platform.setLayoutX(x * 100);
                    platform.setLayoutY(y * 20);

                    switch (type) {
                        case "platform" -> {
                            platform.setFill(new ImagePattern(new Image("file:src/main/resources/com/fantastic_knight/assets/platform.png")));
                            platform.setOpacity(100);
                            platform.setType("platform");
                        }
                        case "spike" -> {
                            platform.setFill(new ImagePattern(new Image("file:src/main/resources/com/fantastic_knight/items/spike.png")));
                            platform.setOpacity(100);
                            platform.setType("spike");
                        }
                        case "slime" -> {
                            platform.setFill(new ImagePattern(new Image("file:src/main/resources/com/fantastic_knight/items/slime.png")));
                            platform.setOpacity(100);
                            platform.setType("slime");
                        }
                        case "door" -> {
                            platform.setFill(new ImagePattern(new Image("file:src/main/resources/com/fantastic_knight/assets/door.png")));
                            platform.setOpacity(100);
                            platform.setType("door");
                            platform.setWidth(70);
                            platform.setHeight(100);
                            platform.setLayoutX(x * 100 + 15);
                        }
                        case "arrowTrap" -> {
                            platform.setFill(new ImagePattern(new Image("file:src/main/resources/com/fantastic_knight/items/arrowTrap_left.png")));
                            platform.setOpacity(100);
                            platform.setType("arrowTrap");
                            platform.setWidth(50);
                            platform.setHeight(50);
                            platform.setLayoutX(x * 100 + 25);
                        }
                        default -> {
                            platform.setOpacity(0);
                            platform.setType("void");
                        }
                    }
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
