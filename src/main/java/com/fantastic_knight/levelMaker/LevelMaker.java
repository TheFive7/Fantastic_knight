package com.fantastic_knight.levelMaker;

import com.fantastic_knight.Game;
import com.fantastic_knight.model.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.fantastic_knight.Game.*;
import static com.fantastic_knight.levelMaker.LevelMakerConstructor.setCheckActive;
import static com.fantastic_knight.model.Model.factor;
import static com.fantastic_knight.model.Model.findAllLevels;

public class LevelMaker extends Pane {

    List<Boolean> booleans = new ArrayList<>(){
        {
            add(true); add(false);
            add(false); add(false);
            add(false); add(false);
            add(false); add(false);
            add(false); add(false);
            add(false); add(false);
        }
    };

    public static Image currentImg = new Image("file:src/main/resources/com/fantastic_knight/assets/platform.png");

    Platform[][] platforms = new Platform[12][40 * factor];
    String fileNameExport = "default";
    String fileNameLoad = "default";

    ChoiceBox<String> choiceBoxLevels;

    public LevelMaker(){
        Pane paneLevelMaker = new Pane();
        paneLevelMaker.setPrefSize(1500,800);

        // PANE MAKER
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefSize(1200,800);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVvalue(scrollPane.getVmax());
        scrollPane.setStyle("-fx-border-color: black");

        Pane paneMaker = new Pane();
        paneMaker.setPrefSize(1200,800 * factor);

        scrollPane.setContent(paneMaker);

        // Background
        paneMaker.setStyle("-fx-background-image: url('"+ Game.class.getResource("assets/bg.png")+"')");

        // REMPLISSAGE DE RECTANGLES
        for(int i = 0; i < 12; i++){
            for (int j = 0; j < 40 * factor; j++){
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
        TextField textFieldFileName = new TextField(fileNameExport); textFieldFileName.setLayoutX(150);
        textFieldFileName.setLayoutY(15); textFieldFileName.setPrefSize(120,55);
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
            fileNameExport = textFieldFileName.getText();
            load(paneMaker);
        });

        // CHOICE ITEMS

        ObservableList<String> options = FXCollections.observableArrayList();
        String chemin = "file:src/main/resources/com/fantastic_knight/";
        options.addAll(
                chemin + "assets" + "/" + "platform" + ".png",
                chemin + "items" + "/" + "spike" + ".png",
                chemin + "items" + "/" + "slime" + ".png",
                chemin + "assets" + "/" + "door" + ".png",
                chemin + "items" + "/" + "arrowTrap_left" + ".png",
                chemin + "items" + "/" + "fire_platform" + ".png",
                chemin + "items" + "/" + "button" + ".png",
                chemin + "assets" + "/" + "shield" + ".png",
                chemin + "assets" + "/" + "key" + ".png",
                chemin + "consumables" + "/" + "halo" + ".png",
                chemin + "consumables" + "/" + "sword" + ".png",
                chemin + "player" + "/" + "Knight_stand_right_gold" + ".png"
        );

        ComboBox<String> comboBox = new ComboBox<>(options);
        comboBox.setLayoutX(50); comboBox.setLayoutY(200);
        comboBox.setCellFactory(c -> new StatusListCell());
        comboBox.setButtonCell(new StatusListCell());
        comboBox.setValue(comboBox.getItems().get(0));

        comboBox.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-border-width: 5px;" +
                "-fx-border-color: black;"
        );

        comboBox.setOnAction(e -> {
            switch (comboBox.getSelectionModel().getSelectedIndex()) {
                case 0 -> {
                    booleans.set(0, setCheckActive(booleans.get(0), "assets", "platform"));
                    setAllFalse(0);
                }
                case 1 -> {
                    booleans.set(1, setCheckActive(booleans.get(1), "items", "spike"));
                    setAllFalse(1);
                }
                case 2 -> {
                    booleans.set(2, setCheckActive(booleans.get(2), "items", "slime"));
                    setAllFalse(2);
                }
                case 3 -> {
                    booleans.set(3, setCheckActive(booleans.get(3), "assets", "door"));
                    setAllFalse(3);
                }
                case 4 -> {
                    booleans.set(4, setCheckActive(booleans.get(4), "items", "arrowTrap_left"));
                    setAllFalse(4);
                }
                case 5 -> {
                    booleans.set(5, setCheckActive(booleans.get(5), "items", "fire_platform"));
                    setAllFalse(5);
                }
                case 6 -> {
                    booleans.set(6, setCheckActive(booleans.get(6), "items", "button"));
                    setAllFalse(6);
                }
                case 7 -> {
                    booleans.set(7, setCheckActive(booleans.get(7), "assets", "shield"));
                    setAllFalse(7);
                }
                case 8 -> {
                    booleans.set(8, setCheckActive(booleans.get(8), "assets", "key"));
                    setAllFalse(8);
                }
                case 9 -> {
                    booleans.set(9, setCheckActive(booleans.get(9), "consumables", "halo"));
                    setAllFalse(9);
                }
                case 10 -> {
                    booleans.set(10, setCheckActive(booleans.get(10), "consumables", "sword"));
                    setAllFalse(10);
                }
                case 11 -> {
                    booleans.set(11, setCheckActive(booleans.get(11), "player", "Knight_stand_right_gold"));
                    setAllFalse(11);
                }
            }
        });

        // CLEAR
        Button buttonClear = new Button();
        buttonClear.setPrefSize(150,160);
        buttonClear.setStyle("-fx-background-image: url('"+ Game.class.getResource("icons/clear.png")+"');-fx-background-color: transparent; -fx-background-repeat: no-repeat");
        buttonClear.setLayoutX(105); buttonClear.setLayoutY(650);
        buttonClear.setOnAction(e -> clear());

        // MENU
        Button buttonMenu = new Button();
        buttonMenu.setPrefSize(75,80);
        buttonMenu.setStyle("-fx-background-image: url('"+ Game.class.getResource("icons/back.png")+"');-fx-background-color: transparent; -fx-background-repeat: no-repeat");
        buttonMenu.setLayoutX(235); buttonMenu.setLayoutY(725);
        buttonMenu.setOnAction(e -> menu());

        // AJOUTS
        paneChoose.getChildren().addAll(fileName,exportFile,buttonExport,buttonClear,buttonMenu,textFieldFileName,choiceBoxLevels,comboBox);
        paneLevelMaker.getChildren().addAll(scrollPane,paneChoose);
//        paneLevelMaker.getChildren().addAll(paneMaker,paneChoose);
        getChildren().add(paneLevelMaker);

        load(paneMaker);
    }

    void setAllFalse(int index){
        for (Boolean b : booleans){
            booleans.set(booleans.indexOf(b), false);
        }
        booleans.set(index, true);
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
        Model.levels = findAllLevels();
        primaryStage.setWidth(1200);
        primaryStage.setScene(scene_menu);
    }

    /**
     * Dessine une plateforme
     * @param e : Mouse Event
     * @param platforms : Tableau des plateformes
     */
    void drawPlatform(MouseEvent e, Platform[][] platforms){
        int x = (int) (Math.round(e.getX() * 10)/1000);
        int y = (int) (Math.round(e.getY() * 10)/200);
        if ((x < 12 && x >= 0) && (y < 40 * factor && y >= 0)){
            if (e.getButton() == MouseButton.PRIMARY){
                platforms[x][y].setxCoordonnee(x);
                platforms[x][y].setyCoordonnee(y);
                platforms[x][y].setOpacity(100);
                platforms[x][y].setWidth(100);
                platforms[x][y].setHeight(20);
                platforms[x][y].setLayoutX(x * 100);
                platforms[x][y].setLayoutY(y * 20);

                // Spikes & Platform
                if(booleans.get(1))platforms[x][y].setType("spike");
                else if (booleans.get(2))platforms[x][y].setType("slime");
                else if (booleans.get(5))platforms[x][y].setType("flameTrap");
                else {platforms[x][y].setType("platform");}

                // Door
                if (booleans.get(3)){
                    platforms[x][y].setType("door");
                    platforms[x][y].setWidth(70);
                    platforms[x][y].setHeight(100);
                    platforms[x][y].setLayoutX(x * 100 + 15);
                }

                // ArrowTrap
                if (booleans.get(4)){
                    platforms[x][y].setType("arrowTrap");
                    platforms[x][y].setWidth(50);
                    platforms[x][y].setHeight(50);
                    platforms[x][y].setLayoutX(x * 100 + 25);
                    platforms[x][y].setLayoutY(y * 20 - 10);
                }

                // Button
                if (booleans.get(6)){
                    platforms[x][y].setType("button");
                    platforms[x][y].setWidth(50);
                    platforms[x][y].setHeight(20);
                    platforms[x][y].setLayoutX(x * 100 + 25);
                    platforms[x][y].setLayoutY(y * 20);
                }

                // Shield
                if (booleans.get(7)){
                    platforms[x][y].setType("shield");
                    platforms[x][y].setWidth(40);
                    platforms[x][y].setHeight(40);
                    platforms[x][y].setLayoutX(x * 100 + 30);
                    platforms[x][y].setLayoutY(y * 20);
                }

                // Key
                if (booleans.get(8)){
                    platforms[x][y].setType("key");
                    platforms[x][y].setWidth(40);
                    platforms[x][y].setHeight(40);
                    platforms[x][y].setLayoutX(x * 100 + 30);
                    platforms[x][y].setLayoutY(y * 20);
                }

                // Halo
                if (booleans.get(9)){
                    platforms[x][y].setType("halo");
                    platforms[x][y].setWidth(40);
                    platforms[x][y].setHeight(40);
                    platforms[x][y].setLayoutX(x * 100 + 30);
                    platforms[x][y].setLayoutY(y * 20);
                }

                // Sword
                if (booleans.get(10)){
                    platforms[x][y].setType("sword");
                    platforms[x][y].setWidth(40);
                    platforms[x][y].setHeight(40);
                    platforms[x][y].setLayoutX(x * 100 + 30);
                    platforms[x][y].setLayoutY(y * 20);
                }

                // Ennemy
                if (booleans.get(11)){
                    platforms[x][y].setType("ennemy");
                    platforms[x][y].setWidth(40);
                    platforms[x][y].setHeight(70);
                    platforms[x][y].setLayoutX(x * 100 + 30);
                    platforms[x][y].setLayoutY(y * 20 + 10);
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
                for (int j = 0; j < 40 * factor; j++){
                    writer.print(platforms[i][j].toString() + " ");
                    // System.out.println(platforms[i][j].toString());
                }
                writer.println();
            }
            writer.close();

            // System.out.println("Fichier " + fileNameExport + " sauvegardé.");
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
                for (int j = 0; j < 40 * factor; j++){
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
                            platform.setLayoutY(y * 20 - 10);
                        }
                        case "flameTrap" -> {
                            platform.setFill(new ImagePattern(new Image("file:src/main/resources/com/fantastic_knight/items/fire_platform.png")));
                            platform.setOpacity(100);
                            platform.setType("flameTrap");
                        }
                        case "button" -> {
                            platform.setFill(new ImagePattern(new Image("file:src/main/resources/com/fantastic_knight/items/button.png")));
                            platform.setOpacity(100);
                            platform.setType("button");
                            platform.setWidth(50);
                            platform.setHeight(20);
                            platform.setLayoutX(x * 100 + 25);
                        }
                        case "shield" -> {
                            platform.setFill(new ImagePattern(new Image("file:src/main/resources/com/fantastic_knight/assets/shield.png")));
                            platform.setOpacity(100);
                            platform.setType("shield");
                            platform.setWidth(40);
                            platform.setHeight(40);
                            platform.setLayoutX(x * 100 + 30);
                        }

                        case "key" -> {
                            platform.setFill(new ImagePattern(new Image("file:src/main/resources/com/fantastic_knight/assets/key.png")));
                            platform.setOpacity(100);
                            platform.setType("key");
                            platform.setWidth(40);
                            platform.setHeight(40);
                            platform.setLayoutX(x * 100 + 30);
                        }

                        case "halo" -> {
                            platform.setFill(new ImagePattern(new Image("file:src/main/resources/com/fantastic_knight/consumables/halo.png")));
                            platform.setOpacity(100);
                            platform.setType("halo");
                            platform.setWidth(40);
                            platform.setHeight(40);
                            platform.setLayoutX(x * 100 + 30);
                        }
                        case "sword" -> {
                            platform.setFill(new ImagePattern(new Image("file:src/main/resources/com/fantastic_knight/assets/sword.png")));
                            platform.setOpacity(100);
                            platform.setType("sword");
                            platform.setWidth(40);
                            platform.setHeight(40);
                            platform.setLayoutX(x * 100 + 30);
                        }
                        case "ennemy" -> {
                            platform.setFill(new ImagePattern(new Image("file:src/main/resources/com/fantastic_knight/player/Knight_stand_right_gold.png")));
                            platform.setOpacity(100);
                            platform.setType("ennemy");
                            platform.setWidth(40);
                            platform.setHeight(70);
                            platform.setLayoutX(x * 100 + 30);
                            platform.setLayoutY(y * 20 + 10);
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

            // System.out.println("Fichier " + fileNameLoad + " chargé");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class StatusListCell extends ListCell<String> {
    protected void updateItem(String item, boolean empty){
        super.updateItem(item, empty);
        setGraphic(null);
        setText(null);
        if (item != null){
            ImageView imageView = new ImageView(new Image(item));

            imageView.setFitWidth(100);
            imageView.setFitHeight(20);

            if (item.contains("door")){
                imageView.setFitWidth(70);
                imageView.setFitHeight(100);
            } else if (item.contains("arrowTrap_left")){
                imageView.setFitWidth(50);
                imageView.setFitHeight(50);
            } else if (item.contains("button")) {
                imageView.setFitWidth(50);
                imageView.setFitHeight(20);
            } else if (item.contains("key")) {
                imageView.setFitWidth(32);
                imageView.setFitHeight(32);
            } else if (item.contains("consumables") || item.contains("shield")){
                imageView.setFitWidth(80);
                imageView.setFitHeight(80);
            } else if (item.contains("player")) {
                imageView.setFitWidth(40);
                imageView.setFitHeight(70);
            }
            setGraphic(imageView);
        }
    }
}
