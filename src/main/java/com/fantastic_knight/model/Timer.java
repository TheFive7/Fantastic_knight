package com.fantastic_knight.model;

import com.fantastic_knight.items.Item;
import com.fantastic_knight.player.Player;

public class Timer implements Runnable {
    final int time;
    boolean bool;
    Item item;
    Player player;

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

    /**
     * Start timer
     */
    public void run() {
        try {
            bool = false;// disable
            if(item!=null) item.setActive(bool);
            if(player!=null) player.setState(State.DASH);
            Thread.sleep(time);
            bool = true;// able
            if(item!=null) item.setActive(bool);
            if(player!=null) player.setState(State.IDLE);
            Thread.currentThread().interrupt();
            return;
        } catch (InterruptedException e) {
            System.err.println("Error with Timer");
        }
    }
}
