package com.fantastic_knight.model;

import com.fantastic_knight.items.Item;

public class Timer implements Runnable {
    final int time;
    boolean bool;
    Item item;

    public Timer(int time) {
        this.time = time;
        this.bool = false;
        item = null;
    }

    public Timer(int time, Item item) {
        this.time = time;
        this.item = item;
        bool = false;
    }

    /**
     * Start timer
     */
    public void run() {
        try {
            bool = false; item.setActive(bool); // disable
            Thread.sleep(time);
            bool = true; item.setActive(bool); // able
            Thread.currentThread().interrupt();
            return;
        } catch (InterruptedException e) {
            System.err.println("Error with Timer");
        }
    }

    public void setBool(boolean bool) {
        bool = this.bool;
    }

    public void setItem(Item item){
        this.item = item;
    }
}
