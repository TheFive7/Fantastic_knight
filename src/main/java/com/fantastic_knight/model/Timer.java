package com.fantastic_knight.model;

import com.fantastic_knight.items.Item;
import com.fantastic_knight.player.Ennemy;
import com.fantastic_knight.player.Player;

public class Timer implements Runnable {
    final int time;
    boolean bool;
    Item item;
    Player player;
    Ennemy ennemy;

    public Timer(int time) {
        this.time = time;
        this.bool = false;
        item = null;
        player = null;
    }

    public Timer(int time, Item item) {
        this.time = time;
        this.item = item;
        bool = false;
        player = null;
    }

    public Timer(int time, Player player) {
        this.time = time;
        this.bool = false;
        item = null;
        this.player = player;
    }

    public Timer(int time, Ennemy ennemy) {
        this.time = time;
        this.bool = false;
        item = null;
        this.ennemy = ennemy;
    }

    /**
     * Start timer
     */
    public void run() {
        try {
            bool = false; // disable
            if(item!=null) item.setActive(bool);
            if(player!=null) player.setState(State.DASH);
            if (ennemy!=null) ennemy.setActive(bool);
            Thread.sleep(time);
            bool = true; // unable
            if(item!=null) item.setActive(bool);
            if (ennemy!=null) ennemy.setActive(bool);
            if(player!=null) {
                player.setState(State.IDLE);
                player.setCanDash(false);
                Thread.sleep(1500);
                player.setCanDash(true);
            }
            Thread.currentThread().interrupt();
            return;
        } catch (InterruptedException e) {
            System.err.println("Error with Timer");
        }
    }
}
