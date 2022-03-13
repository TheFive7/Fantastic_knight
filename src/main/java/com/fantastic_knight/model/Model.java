package com.fantastic_knight.model;

import com.fantastic_knight.consumable.Consumable;
import com.fantastic_knight.items.Door;
import com.fantastic_knight.items.Item;
import com.fantastic_knight.items.SwordPlayer;
import com.fantastic_knight.player.Ennemy;
import com.fantastic_knight.player.Player;
import com.fantastic_knight.player.Sprite;
import javafx.scene.control.Label;
import javafx.scene.shape.Shape;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.fantastic_knight.controller.MenuController.isMultiplayerOn;

public class Model {

    public final static int STATE_INITIAL = 1;
    final static int STATE_PLAY = 2;
    public Chrono chrono;
    public Thread thread;

    // WINDOW
    public final int width;
    public final int height;
    public static int factor = 1;

    // Objets
    public final Player player1;
    public final Player player2;
    public final Label labelWin = new Label("You WIN ! Press 'M' to return Menu.");

    // LISTS
    public final List<Player> players;
    public final List<Ennemy> ennemies;
    public final List<Shape> obstacles; // obstacles in each scene
    public final List<Item> items;      // items in the game
    public final List<Consumable> consumables;      // consumables in the game

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
    public SwordPlayer swordPlayer;
    public SwordPlayer swordPlayer2;

    // ENNEMIES
    public Ennemy ennemy;

    // ATH PLAYER 1
    public Shield shield;
    public Sword sword;
    public Heart heart;
    public Key key;

    // ATH PLAYER 2
    public Shield shield2;
    public Sword sword2;
    public Heart heart2;
    public Key key2;

    // EXIT
    public boolean isKey = false;
    public Door door;

    // SOUNDS
    public double volume = 20.0;
    // public Media backgroundMusic = new Media(new File("src/main/resources/com/fantastic_knight/sounds/fantasy.wav").toURI().toString());
    // public MediaPlayer mediaPlayerBackgroundMusic = new MediaPlayer(backgroundMusic);

    public Model() {
        state = STATE_INITIAL;
        level = 1;
        width = 1200;
        height = 800;
        lastFrame = -1;
        player1 = new Player(this, "Red Knight");
        player2 = new Player(this, "Platine Knight");
        ennemy = new Ennemy(this);
        door = new Door(this);
        obstacles = new ArrayList<>();
        players = new ArrayList<>();
        ennemies = new ArrayList<>();
        items = new ArrayList<>();
        consumables = new ArrayList<>();

        // mediaPlayerBackgroundMusic.play();
    }

    /**
     * Start the game
     */
    public void startGame() {
        state = STATE_PLAY;
        players.add(player1);
        if (isMultiplayerOn) players.add(player2);
        ennemies.add(ennemy);
        chrono = new Chrono();
        thread = new Thread(chrono);
        thread.start();
    }

    /**
     * Reset the game
     */
    public void reset() {
        player1.reset();
        if (isMultiplayerOn) player2.reset();
        ennemy.reset();
        players.clear();
        items.clear();
        consumables.clear();
        obstacles.clear();
        ennemies.clear();
    }

    /**
     * Update
     */
    public void update() {
        for (Item i : items) {
            i.update();
        }
        for (Player p : players) {
            p.update();
        }
        for (Consumable c : consumables) {
            c.update();
        }
        for (Ennemy e : ennemies) {
            e.update();
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
