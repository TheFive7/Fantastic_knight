package com.fantastic_knight.model;

import com.fantastic_knight.Player;
import com.fantastic_knight.Sprite;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

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

    // Murs
    public Shape northWall;
    public Shape southWall;
    public Shape eastWall;
    public Shape westWall;

    // Objets
    public Player player;


    // Obstacles
    public Rectangle r1;
    public Rectangle r2;
    public Rectangle r3;

    public Model() {
        state = STATE_INITIAL;
        level = 1;
        width = 1200;
        height = 800;
        lastFrame = -1;
        player = new Player(this);
        obstacles = new ArrayList<>();
        sprites = new ArrayList<>();
    }

    public void startGame() {
        state = STATE_PLAY;
        sprites.add(player);
    }

    public void reset() {
        sprites.clear();
        player.reset();
        obstacles.clear();
    }

    public void update() {
        for(Sprite s: sprites) {
            s.update();
        }
    }
}
