package com.fantastic_knight.model;

import com.fantastic_knight.items.Item;
import com.fantastic_knight.player.Player;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Shape;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Model {

    public final static int STATE_INITIAL = 1;
    final static int STATE_PLAY = 2;

    Chrono chrono = new Chrono();
    Thread thread = new Thread(chrono);

    // WINDOW
    public final int width;
    public final int height;
    public final List<Shape> obstacles; // obstacles in each scene
    public final List<Item> items;      // items in the game

    // Objets
    public final Player player;
    public final Label labelWin = new Label("You WIN ! Press 'M' to return Menu.");

    // SPRITES
    final List<Sprite> sprites;

    // STATE
    public int state;

    // LEVEL
    public int level;
    public long lastFrame;

    // WALLS
    public Shape northWall;
    public Shape southWall;
    public Shape eastWall;
    public Shape westWall;

    // ATH
    public Shield shield;

    // EXIT
    public Door door = new Door(this);

    public Model() {
        state = STATE_INITIAL;
        level = 1;
        width = 1200;
        height = 800;
        lastFrame = -1;
        player = new Player(this);
        obstacles = new ArrayList<>();
        sprites = new ArrayList<>();
        items = new ArrayList<>();

    }

    /**
     * Start the game
     */
    public void startGame() {
        state = STATE_PLAY;
        sprites.add(player);

        thread.start();
    }

    /**
     * Reset the game
     */
    public void reset() {
        sprites.clear();
        items.clear();
        obstacles.clear();
        player.reset();
        shield.setFill(new ImagePattern(new Image("file:src/main/resources/com/fantastic_knight/assets/shield.png")));
    }

    /**
     * Update
     */
    public void update() {
        for (Item i : items) {
            i.update();
        }
        for (Sprite s : sprites) {
            s.update();
        }
        door.update();
        System.out.println(chrono.affiche());
    }

    /**
     * Find all levels created in /levels
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
}
