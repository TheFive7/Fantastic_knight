package com.fantastic_knight.model;

public abstract class Sprite {

    protected final Model model;

    public Sprite(Model model) {
        this.model = model;
    }

    public abstract void update();
}
