package com.fantastic_knight.levelMaker;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;

public class LevelMaker extends Application {

    int[][] coordonnees = new int[100][2];
    Platform[][] platforms = new Platform[12][40];

    @Override
    public void start(Stage stage) {
        Pane paneLevelMaker = new Pane();
        paneLevelMaker.setPrefSize(1500,800);

        // PANE MAKER
        Pane paneMaker = new Pane();
        paneMaker.setPrefSize(1200,800);

        for(int i = 0; i < 12; i++){
            for (int j = 0; j < 40; j++){
                Platform platform = new Platform();
                platform.setFill(Color.WHITE); platform.setWidth(100); platform.setHeight(20);
                platform.setLayoutX(i * 100); platform.setLayoutY(j * 20);
                platforms[i][j] = platform;
                paneMaker.getChildren().add(platforms[i][j]);
            }
        }

        paneMaker.setOnMouseClicked(e -> drawPlatform(e,platforms));
        paneMaker.setOnMouseDragged(e -> drawPlatform(e,platforms));


        // PANE CHOOSE
        Pane paneChoose = new Pane();
        paneChoose.setPrefSize(300,800); paneChoose.setLayoutX(1200);
        paneChoose.setStyle("-fx-background-color: red");

        // EXPORT
        Button buttonExport = new Button("EXPORT");
        buttonExport.setLayoutX(100); buttonExport.setLayoutY(50);
        buttonExport.setOnAction(e -> export());

        // LOAD
        Button buttonLoad = new Button("LOAD");
        buttonLoad.setLayoutX(100); buttonLoad.setLayoutY(150);
        buttonLoad.setOnAction(e -> load(paneMaker));

        paneChoose.getChildren().addAll(buttonExport,buttonLoad);

        paneLevelMaker.getChildren().addAll(paneMaker,paneChoose);

        Scene scene = new Scene(paneLevelMaker,1500,800);

        stage.setTitle("Fantastic Knight Level Maker");
        stage.setScene(scene);
        stage.show();
    }

    void drawPlatform(MouseEvent e, Platform[][] platforms){
        int x = (int) (Math.round(e.getX() * 10)/1000);
        int y = (int) (Math.round(e.getY() * 10)/200);
        if ((x < 12 && x >= 0) && (y < 40 && y >= 0)){
            if (e.getButton() == MouseButton.PRIMARY){
                platforms[x][y].setxCoordonnee(x);
                platforms[x][y].setyCoordonnee(y);
                platforms[x][y].setFill(Color.BLACK);
            } else if (e.getButton() == MouseButton.SECONDARY){
                platforms[x][y].setxCoordonnee(x);
                platforms[x][y].setyCoordonnee(y);
                platforms[x][y].setFill(Color.WHITE);
            }
        }
    }

    void export(){
        int compteur = 0;
        boolean isEmpty = true;

        for(int i = 0; i < 12; i++){
            for (int j = 0; j < 40; j++){
                if (platforms[i][j].getFill().equals(Color.BLACK)){
                    coordonnees[compteur][0] = platforms[i][j].getxCoordonnee() * 100;
                    coordonnees[compteur][1] = platforms[i][j].getyCoordonnee() * 20;
                    isEmpty = false;
                    compteur ++;
                }
            }
        }

        if (!isEmpty){
            try {
                FileOutputStream file = new FileOutputStream("src/main/java/com/fantastic_knight/save/level_1.txt");
                ObjectOutputStream output = new ObjectOutputStream(file);
                output.writeObject(coordonnees);
                System.out.println("Fichier sauvegardé.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //TODO: LOAD
    void load(Pane paneMaker){
        try {
            FileInputStream fileStream = new FileInputStream("src/main/java/com/fantastic_knight/save/level_1.txt");
            ObjectInputStream input = new ObjectInputStream(fileStream);
            int[][] coordonneesLoad = (int[][]) input.readObject();

            // Load
            for(int[] tab : coordonneesLoad){
                if (tab[0] != 0 && tab[1] != 0){
                    int x = tab[0] / 100;
                    int y = tab[1] / 100;
                    Platform platform = new Platform();
                    platform.setFill(Color.BLACK);
                    platform.setxCoordonnee(x);
                    platform.setyCoordonnee(y);
                    platform.setWidth(100); platform.setHeight(20);
                    platform.setLayoutX(x * 100); platform.setLayoutY(y * 20);
                    platforms[x][y] = platform;
                    paneMaker.getChildren().add(platform);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
