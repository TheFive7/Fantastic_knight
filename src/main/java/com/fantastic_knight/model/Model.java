package com.fantastic_knight.model;

import com.fantastic_knight.Door;
import com.fantastic_knight.player.Player;
import com.fantastic_knight.Sprite;
import javafx.scene.control.Label;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

import static com.fantastic_knight.Game.primaryStage;
import static com.fantastic_knight.controller.MenuController.scene_levels;

public class Model {

    // STATE
    public int state;
    public final static int STATE_INITIAL = 1;
    final static int STATE_PLAY = 2;

    // LEVEL
    public int level;

    // WINDOW
    public int width;
    public int height;
    public long lastFrame;

    // SPRITES
    List<Sprite> sprites;
    public List<Shape> obstacles; // obstacles in each scene
    public List<Item> items;      // items in the game

    // Murs
    public Shape northWall;
    public Shape southWall;
    public Shape eastWall;
    public Shape westWall;

    // Objets
    public Player player;

    // Ath
    public Shield shield;

    // Exit
    public Door door;
    public Label labelWin;

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

        labelWin = new Label("T'as gagné BG"); labelWin.setOpacity(0);
    }

    /**
     * Lance le jeu
     */
    public void startGame() {
        state = STATE_PLAY;
        sprites.add(player);
    }

    /**
     * Reset le jeu
     */
    public void reset() {
        sprites.clear();
        items.clear();
        obstacles.clear();
        player.reset();
    }

    /**
     * Actualisation
     */
    public void update() {
        for(Sprite s: sprites) {
            s.update();
        }
        for(Item i: items) {
            i.update();
        }
    }
}
