package com.fantastic_knight.model;

import com.fantastic_knight.consumable.Consumable;
import com.fantastic_knight.items.Item;
import com.fantastic_knight.player.Player;
import javafx.scene.control.Label;
import javafx.scene.shape.Shape;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Model {

    public final static int STATE_INITIAL = 1;
    final static int STATE_PLAY = 2;
    public Chrono chrono;
    public Thread thread;

    // WINDOW
    public final int width;
    public final int height;
    public final List<Shape> obstacles; // obstacles in each scene
    public final List<Item> items;      // items in the game
    public final List<Consumable> consumables;      // consumables in the game

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

    // PLAYER
    public Sword swordPlayer;

    // ATH
    public Shield shield;
    public Sword sword;
    public Heart heart;

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
        consumables = new ArrayList<>();
    }

    /**
     * Start the game
     */
    public void startGame() {
        state = STATE_PLAY;
        sprites.add(player);
        chrono = new Chrono();
        thread = new Thread(chrono);
        thread.start();
    }

    /**
     * Reset the game
     */
    public void reset() {
        sprites.clear();
        items.clear();
        consumables.clear();
        obstacles.clear();
        player.reset();
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
        for (Consumable c : consumables) {
            c.update();
        }
        door.update();
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
