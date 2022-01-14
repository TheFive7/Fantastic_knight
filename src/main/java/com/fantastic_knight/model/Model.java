package com.fantastic_knight.model;

import com.fantastic_knight.Door;
import com.fantastic_knight.Sprite;
import com.fantastic_knight.player.Player;
import javafx.scene.control.Label;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

public class Model {

    public final static int STATE_INITIAL = 1;
    final static int STATE_PLAY = 2;

    // WINDOW
    public final int width;
    public final int height;
    public final List<Shape> obstacles; // obstacles in each scene
    public final List<Item> items;      // items in the game

    // Objets
    public final Player player;
    public final Label labelWin;

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
    public Door door;

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

        labelWin = new Label("T'as gagné BG");
        labelWin.setOpacity(0);
    }

    /**
     * Start the game
     */
    public void startGame() {
        state = STATE_PLAY;
        sprites.add(player);
    }

    /**
     * Reset the game
     */
    public void reset() {
        sprites.clear();
        items.clear();
        obstacles.clear();
        player.reset();
    }

    /**
     * Update
     */
    public void update() {
        for (Sprite s : sprites) {
            s.update();
        }
        for (Item i : items) {
            i.update();
        }
    }
}
