package com.fantastic_knight.model;

import static java.lang.Thread.sleep;

public class Chrono implements Runnable {
    private double time;
    private boolean running;

    public Chrono(){
        this.running = false;
        this.time = 0;
    }

    @Override
    public void run(){
        running = true;

        while (running) {
            try{
                sleep(100);
                time += 0.1;
                time = (double)Math.round(time * 100) / 100;
            }
            catch(InterruptedException ignored){}
        }
    }

    public void terminate(){
        running = false;
    }

    public String affiche() {
        return this.time + "";
    }
}
