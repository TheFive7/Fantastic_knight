package com.fantastic_knight.view;

import com.fantastic_knight.Game;
import com.fantastic_knight.items.ArrowTrap;
import com.fantastic_knight.items.Slime;
import com.fantastic_knight.items.Spikes;
import com.fantastic_knight.levelMaker.Platform;
import com.fantastic_knight.model.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.util.Scanner;

public class ViewLevel {

    final Model model;
    final Pane pane;

    public ViewLevel(Model model, Pane pane, int levelNumber) {

        this.model = model;
        this.pane = pane;
        pane.getChildren().clear();

        // Background
        pane.setStyle("-fx-background-image: url('" + Game.class.getResource("assets/bg.png") + "')");

        // Define obstacle for outside the view.
        model.northWall = new Rectangle(0, -50, model.width, 50);
        model.southWall = new Rectangle(0, model.height, model.width, 50);
        model.westWall = new Rectangle(-50, -50, 50, model.height + 100);
        model.eastWall = new Rectangle(model.width, -50, 50, model.height + 100);
        model.obstacles.add(model.northWall);
        model.obstacles.add(model.southWall);
        model.obstacles.add(model.eastWall);
        model.obstacles.add(model.westWall);

        // Autocall the init method to set up the level.
        loadLevel(Game.levels.get(levelNumber));

        init();
    }

    // to initialize levels
    public void init() {
        // Shield
        model.shield = new Shield();
        pane.getChildren().add(model.shield);

        // Heart
        model.heart = new Heart(model);
        model.items.add(model.heart);
        pane.getChildren().add(model.heart.getShape());

        // Label
        model.labelWin.setOpacity(0);
        model.labelWin.setFont(new Font(50));
        model.labelWin.setTextFill(Color.WHITE);
        model.labelWin.setStyle("-fx-background-color: black; -fx-background-radius: 20");
        model.labelWin.setLayoutY(model.height/2.0);
        model.labelWin.setLayoutX(200);
        pane.getChildren().add(model.labelWin);

        // Player
        pane.getChildren().add(model.player.getShape());

/*        // ArrowTrap
        ArrowTrap arrowTrap = new ArrowTrap(model);
        arrowTrap.getShape().setX(700);
        arrowTrap.getShape().setY(model.height - arrowTrap.getShape().getHeight());
        model.obstacles.add(arrowTrap.getShape());
        model.items.add(arrowTrap);
        pane.getChildren().add(arrowTrap.getShape());
        pane.getChildren().add(arrowTrap.getArrow().getShape());*/
    }

    /**
     * Load requested leveandé
     * @param nameLevel : requested level name
     */
    public void loadLevel(String nameLevel) {
        try {
            FileInputStream file = new FileInputStream("src/main/java/com/fantastic_knight/save/" + nameLevel + ".sav");
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
                            model.obstacles.add(platform);
                        }
                        case "spike" -> {
                            platform.setFill(new ImagePattern(new Image("file:src/main/resources/com/fantastic_knight/items/spike.png")));
                            platform.setOpacity(100);
                            Spikes spike = new Spikes(model);
                            spike.setShape(platform);
                            model.items.add(spike);
                        }
                        case "slime" -> {
                            platform.setFill(new ImagePattern(new Image("file:src/main/resources/com/fantastic_knight/items/slime.png")));
                            platform.setOpacity(100);
                            Slime slime = new Slime(model);
                            slime.setShape(platform);
                            model.items.add(slime);
                        }
                        case "door" -> {
                            Door door = model.door;
                            door.setX(platform.getxCoordonnee() * 100 + 15);
                            door.setY(platform.getyCoordonnee() * 20);
                            door.setWidth(70);
                            door.setHeight(100);
                            door.setFill(new ImagePattern(new Image("file:src/main/resources/com/fantastic_knight/assets/door.png")));
                            pane.getChildren().add(door);
                        }
                        case "arrowTrap" -> {
                            ArrowTrap arrowTrap = new ArrowTrap(model);
                            arrowTrap.getShape().setX(platform.getxCoordonnee() * 100 + 25);
                            arrowTrap.getShape().setY(platform.getyCoordonnee() * 20);
                            arrowTrap.getShape().setWidth(50);
                            arrowTrap.getShape().setHeight(50);
                            arrowTrap.initArrow();
                            arrowTrap.getShape().setFill(new ImagePattern(new Image("file:src/main/resources/com/fantastic_knight/items/arrowTrap.png")));
                            model.obstacles.add(arrowTrap.getShape());
                            model.items.add(arrowTrap);
                            pane.getChildren().add(arrowTrap.getArrow().getShape());
                            pane.getChildren().add(arrowTrap.getShape());
                        }
                        default -> platform.setOpacity(0);
                    }

                    platform.setType(type);
                    if (!type.equals("door")) {
                        if (!type.equals("arrowTrap")){
                            pane.getChildren().add(platform);
                        }
                    }
                }
            }

            scanner.close();
            //System.out.println("Fichier " + nameLevel + " chargé");

        } catch (Exception e) {
            System.err.println("Error with Level Load");
        }
    }

}
