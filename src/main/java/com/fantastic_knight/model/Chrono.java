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
                sleep(10);
                time += 0.01;
                time = (double)Math.round(time * 100) / 100;
            }
            catch(InterruptedException ignored){}
        }
    }

    public void terminate(){
        running = false;
    }

    public void restart(){
        running = true;
    }

    public String affiche() {
        return this.time + "";
    }

    public double getTime(){return time;}

    public void setTime(double time){
        this.time = time;
    }
}
