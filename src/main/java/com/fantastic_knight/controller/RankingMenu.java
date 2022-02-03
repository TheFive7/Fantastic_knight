package com.fantastic_knight.controller;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.*;
import java.util.*;

import static com.fantastic_knight.Game.primaryStage;
import static com.fantastic_knight.Game.scene_menu;

public class RankingMenu extends Pane {
    public String[] tabPseudo = new String[100];
    public static List<String> data = new ArrayList<>();


    public RankingMenu() {
        // size
        setHeight(800);
        setWidth(1200);

        // Background
        ImageView bgImage = new ImageView(new Image("file:src/main/resources/com/fantastic_knight/menu/levels.png"));

        // button return menu
        Button buttonMenu = new Button();
        buttonMenu.setLayoutX(1128);
        buttonMenu.setLayoutY(697);
        buttonMenu.setOnAction(e -> returnMenu());
        buttonMenu.setOpacity(0);
        buttonMenu.setPrefSize(60, 85.3);

        // classement
        Label classement = new Label();
        classement.setText("Classement : ");
        classement.setLayoutX(400);
        classement.setLayoutY(300);
        classement.setFont(new Font(58));
        classement.setTextFill(Color.WHITE);
        classement.setStyle("-fx-font-weight: bold");
        getChildren().addAll(bgImage, buttonMenu, classement);


        String fileNameTime = "time";

        int compteur = 0;
        List<Double> temps = new ArrayList<>();
        String[] tabTime;

        // trier les temps
        try {
            data.clear();
            // for(int i=0; i<tabPseudo.length; i++){tabPseudo[i] = null;}
            FileInputStream file = new FileInputStream("src/main/java/com/fantastic_knight/time/"+ fileNameTime +".txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                tabTime = scanner.nextLine().split(",");
                double tmp = Double.parseDouble(tabTime[1]);

                data.add(tabTime[0] + "," + tabTime[1]);
                temps.add(tmp);
                Collections.sort(temps);
                System.out.println(tabTime[0] + " " + tabTime[1]);

                // c'est ici que ça merde
                int index = temps.indexOf(tmp);
                tabPseudo[index] = tabTime[0];

                compteur ++;

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // affichage classement
        for(int i = 0; i < 5; i++){
            Label label = new Label();
            label.setText((i+1) + "e   " + tabPseudo[i] + " : " + temps.get(i));
            label.setStyle("-fx-font-weight: bold");
            label.setLayoutX(400);
            label.setLayoutY(400+50*i);
            label.setFont(new Font(44));
            label.setTextFill(Color.BLACK);
            getChildren().add(label);
        }
    }

    public static void register(double temps){
        try {
            System.out.println(temps);
            PrintWriter writer = new PrintWriter("src/main/java/com/fantastic_knight/time/time.txt");
            for(int i = 0; i < data.size(); i++){
                writer.print(data.get(i));
                writer.println();
            }
            writer.print(MenuController.globalPseudo + "," + temps);
            writer.println();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void returnMenu() {
        primaryStage.setScene(scene_menu);
    }
}