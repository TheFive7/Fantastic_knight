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
import static com.fantastic_knight.model.Model.findAllLevels;

public class RankingMenu extends Pane {
    public String[] tabPseudo = new String[100];
    public static List<String> data = new ArrayList<>();


    public RankingMenu() {
        // size
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

        List<Double> temps = new ArrayList<>();
        String[] tab;
        ArrayList<String> levels = (ArrayList<String>) findAllLevels();
        HashMap<String, ArrayList<String>> data2 = new HashMap<>();
        levels.forEach(l -> data2.put(l,new ArrayList<>()));
        // trier les temps
        try {
            data.clear();
            FileInputStream file = new FileInputStream("src/main/java/com/fantastic_knight/time/"+ fileNameTime +".txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNext()) {
                tab = scanner.nextLine().split(",");
                double tmp = Double.parseDouble(tab[1]);
                //data2.get(tab[2]).add(tab[0] + "," + tab[1]);
                data.add(tab[0] + "," + tab[1] + "," + tab[2]);
                temps.add(tmp);
            }
            Collections.sort(temps);

/*            for (String s : data){
                String[] value = s.split(",");
                double tmp = Double.parseDouble(value[1]);
                String name = value[0];

                for (double d : temps){
                    if (d == tmp){
                        tabPseudo[temps.indexOf(d)] = name;
                    }
                }
            }*/

            for (double t : temps) {
                for (String s : data) {
                    String[] value = s.split(",");
                    double tmp = Double.parseDouble(value[1]);
                    if (tmp == t)
                        data2.get(value[2]).add(value[0] + "," + value[1]);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        data2.forEach((k,v) -> {
            Label title = new Label();
            title.setText(k);
            title.setStyle("-fx-font-weight: bold");
            title.setLayoutX(400);
            title.setLayoutY(350+50*5*levels.indexOf(k));
            title.setFont(new Font(54));
            title.setTextFill(Color.BLACK);
            getChildren().add(title);
            for (int i=0; i<v.size();i++) {
                if (i==4)
                    break;
                Label label = new Label();
                label.setText((i+1) + " : " + v.get(i));
                label.setStyle("-fx-font-weight: bold");
                label.setLayoutX(400);
                label.setLayoutY(400 + 250 * levels.indexOf(k) + 50*i);
                label.setFont(new Font(44));
                label.setTextFill(Color.BLACK);
                getChildren().add(label);
            }
        });

    }

    public static void register(double temps, String level){
        FileInputStream file;
        try {
            data.clear();
            file = new FileInputStream("src/main/java/com/fantastic_knight/time/time.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String[] tab = scanner.nextLine().split(",");
                data.add(tab[0] + "," + tab[1] + "," + tab[2]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            PrintWriter writer = new PrintWriter("src/main/java/com/fantastic_knight/time/time.txt");
            for (String datum : data) {
                writer.print(datum);
                writer.println();
            }
            if (MenuController.globalPseudo == null){MenuController.globalPseudo = "Unknown";}
            writer.print(MenuController.globalPseudo + "," + temps + "," + level);
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
