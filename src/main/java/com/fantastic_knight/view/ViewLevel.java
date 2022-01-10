package com.fantastic_knight.view;

import com.fantastic_knight.Game;
import com.fantastic_knight.levelMaker.Platform;
import com.fantastic_knight.model.Model;
import com.fantastic_knight.model.Shield;
import com.fantastic_knight.model.Spikes;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.util.Objects;
import java.util.Scanner;

public class ViewLevel {

    Model model;
    Pane pane;
    
    public ViewLevel(Model model, Pane pane, int levelNumber) {

		this.model = model;
		this.pane = pane;
		pane.getChildren().clear();

		// Background
		pane.setStyle("-fx-background-image: url('"+ Game.class.getResource("bg.png")+"')");

		// Define obstacle for outside the view.
		model.northWall = new Rectangle(0,-50,model.width,50);
		model.southWall = new Rectangle(0,model.height,model.width,50);
		model.westWall = new Rectangle(-50,-50,50,model.height+100);
		model.eastWall = new Rectangle(model.width,-50,50,model.height+100);
		model.obstacles.add(model.northWall);
		model.obstacles.add(model.southWall);
		model.obstacles.add(model.eastWall);
		model.obstacles.add(model.westWall);

		model.shield = new Shield();

		// Autocall the init method to setup the level.
		loadLevel(Game.levels.get(levelNumber));

		pane.getChildren().add(model.shield);

		init();
    }

    // to initialize levels
    public void init() {
		pane.getChildren().addAll(model.player.getShape());
	}

	/**
	 * Charge le niveau demandé
	 * @param nameLevel : Nom du niveau demandé
	 */
	public void loadLevel(String nameLevel){
		System.out.println(nameLevel);

		try {
			FileInputStream file = new FileInputStream("src/main/java/com/fantastic_knight/save/"+ nameLevel +".sav");
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
					if (type.equals("platform")){
						platform.setFill(new ImagePattern(new Image("file:src/main/resources/com/fantastic_knight/platform.png")));
						model.obstacles.add(platform);
						platform.setOpacity(100);
					} else if(type.equals("spike")){
						platform.setFill(new ImagePattern(new Image("file:src/main/resources/com/fantastic_knight/items/spike.png")));
						Spikes spike = new Spikes(model); spike.setShape(platform);
						model.items.add(spike);
						platform.setOpacity(100);
					} else {
						platform.setOpacity(0);
					}

					platform.setxCoordonnee(x);
					platform.setyCoordonnee(y);
					platform.setWidth(100);
					platform.setHeight(20);
					platform.setLayoutX(x * 100);
					platform.setLayoutY(y * 20);
					pane.getChildren().add(platform);
				}
			}

			scanner.close();
			System.out.println("Fichier " + nameLevel + " chargé");

		} catch (Exception e) {
			System.err.println("Error with Level Load");
		}
	}

}
